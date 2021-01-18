package com.bookManagerment.service;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.bookManagerment.config.AlipayConfig;
import com.bookManagerment.config.BMSystemProperties;
import com.bookManagerment.entity.Reader;
import com.bookManagerment.enums.ExceptionEnum;
import com.bookManagerment.exception.LyException;
import com.bookManagerment.mapper.ReaderMapper;
import com.bookManagerment.utils.MailUtils;
import com.bookManagerment.utils.NumberUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class ReaderService {

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private Jedis jedis;

    @Autowired
    private BMSystemProperties bmProperties;

    @Autowired
    private MailUtils mailUtils;

    //根据id查询读者信息
    public Reader queryById(Integer rId) {
        return readerMapper.selectByPrimaryKey(rId);
    }

    //判断账户名是否存在
    public void accountNameExist(String accountName) {
        if (!CollectionUtils.isEmpty(readerMapper.select(new Reader(accountName)))) {    //账户名已存在
            throw new LyException(ExceptionEnum.READER_ACCOUNT_NAME_EXIST);
        }
    }

    //用户注册
    public void saveReader(Reader reader) {
        //根据用户的账户名获取邮箱的验证码
        String verifyCode = jedis.get(reader.getAccount());
        if (StringUtils.isBlank(verifyCode)) {    //验证码已失效，或邮箱地址错误！
            throw new LyException(ExceptionEnum.VERIFY_EXPIRY_OR_EMAIL_ERROR);
        }
        if (!reader.getVerifyCode().equals(verifyCode)) { //邮箱验证码不匹配
            throw new LyException(ExceptionEnum.VERIFY_CODE_NOT_MATCHING);
        }
        reader.builderReader();
        readerMapper.insert(reader);
    }

    //发送邮箱验证码
    public void verifyMailCode(String account, String email) {
        //获取4位数的验证码
        String verifyCode = new Random().nextInt(9999) + "";
        //将验证码根据 account存入redis中
        jedis.set(account, verifyCode);
        //设置验证码有效期
        jedis.expire(account, bmProperties.getEmailVerifyCodeMinute()*60);
        //发送邮箱验证码
        Integer validMinute = bmProperties.getEmailVerifyCodeMinute();
        String emailMsg = bmProperties.getMailReaderRegisterContentModel();
        emailMsg = emailMsg.replace("{emailVerifyCode}", verifyCode).replace("{emailVerifyCodeMinute}", validMinute + "");
        try {
            mailUtils.sendRegisterVerifyCode(email, emailMsg);
        } catch (MessagingException e) {
            e.printStackTrace();    //发送邮箱验证码失败
            throw new LyException(ExceptionEnum.SEND_REGISTER_VERIFY_CODE_FAIL);
        }
    }

    public void addBalance(Double money,HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String orderNo = NumberUtils.getOrderNo();
        model.setOutTradeNo(orderNo);
        //订单名称，必填
        model.setSubject("图书借阅系统充值");
        //支付金额
        model.setTotalAmount(money+"");
        //超时关闭该订单时间
        model.setTimeoutExpress(bmProperties.getOrderZFBTimeoutExpress()+"m");
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        alipayRequest.setBizModel(model);
        //获取当前用户信息
        Reader reader = (Reader)request.getSession().getAttribute(bmProperties.getReaderSessionName());
        //redis中存入 订单编号 ， 用户id,支付状态
        HashMap<String,String> map = new HashMap<>();
        map.put("rId",reader.getRId()+"");
        map.put("status","false");
        jedis.hmset(orderNo,map);
        //redis中支付订单失效时间
        Integer time = (bmProperties.getZFBTimeoutExpressAddTime()+bmProperties.getOrderZFBTimeoutExpress())*60;
        jedis.expire(orderNo,time);
        //请求
        try {
            String result  = alipayClient.pageExecute(alipayRequest).getBody();
            //输出
            response.getWriter().println(result);
        } catch (Exception e) {
            System.out.println("支付请求失败");
            e.printStackTrace();
        }
    }

    //同步通知
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void returnUrl(HttpServletRequest request) {
        try{
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //获取订单信息
            Map<String, String> map = jedis.hgetAll(out_trade_no);
            if(!CollectionUtils.isEmpty(map)){    //order 未失效
                if(map.get("status").equals("true")){   //订单已经支付
                    //根据rId查询用户信息
                    Reader reader = readerMapper.selectByPrimaryKey(Integer.parseInt(map.get("rId")));
                    //存入session
                    request.getSession().setAttribute(bmProperties.getReaderSessionName(),reader);
                    //redis中删除订单
                    jedis.expire(out_trade_no,-1);
                }
            }
            //——请在这里编写您的程序（以上代码仅作参考）——
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //异步通知
    @Transactional
    public void notify(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String> params = getParams(request.getParameterMap());
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
            if (signVerified) {//验证成功
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                //交易状态
                String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
                //交易金额
                Double total_amount = Double.parseDouble(new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8"));
                if (trade_status.equals("TRADE_FINISHED")) {
                } else if (trade_status.equals("TRADE_SUCCESS")) {
                   //获取订单信息
                    Map<String, String> map = jedis.hgetAll(out_trade_no);
                    if(!CollectionUtils.isEmpty(map)){  //订单未失效
                        if(map.get("status").equals("false")){   //订单未支付
                            //根据rId查询用户信息
                            Reader reader = readerMapper.selectByPrimaryKey(Integer.valueOf(map.get("rId")));
                            if(reader!=null){   //用户信息存在
                                //增加账户金额
                                reader.setBalance(reader.getBalance()+(int)(total_amount*100));
                                //持久化数据
                                readerMapper.updateByPrimaryKey(reader);
                                //设置订单已支付
                                map.replace("status","true");
                                //redis中设置订单已支付
                                jedis.hmset(out_trade_no,map);
                            }
                        }
                    }
                }
            } else {//验证失败
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    //获取参数
    private Map<String, String> getParams(Map<String, String[]> requestParams){
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }

    public Double refreshMoney(HttpServletRequest request) {
        Reader reader = (Reader) request.getSession().getAttribute(bmProperties.getReaderSessionName());
        Reader reader1 = readerMapper.selectByPrimaryKey(reader.getRId());
        if(reader.getBalance() != reader1.getBalance()){
            request.getSession().setAttribute(bmProperties.getReaderSessionName(),reader1);
        }
        return reader1.getBalance()/100.0;
    }
}

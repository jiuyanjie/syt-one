package com.bookManagerment.service;

import com.bookManagerment.config.BMSystemProperties;
import com.bookManagerment.entity.Manager;
import com.bookManagerment.entity.Reader;
import com.bookManagerment.enums.ExceptionEnum;
import com.bookManagerment.exception.LyException;
import com.bookManagerment.mapper.ManagerMapper;
import com.bookManagerment.mapper.ReaderMapper;
import com.bookManagerment.utils.CookieUtils;
import com.bookManagerment.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private BMSystemProperties bmProperties;

    //用户登录
    public String login(LoginVo loginVo, HttpServletResponse response, HttpServletRequest request) {
        //判断用户的类型
        String type = loginVo.getType();
        String url;
        if(type.equals("读者")){
            url = loginReader(loginVo,response,request);
        }else{  //管理员
            url = loginManager(loginVo,request);
        }
        return url;
    }

    //用户登录
    @Transactional
    public String loginReader(LoginVo loginVo, HttpServletResponse response,HttpServletRequest request){
        //根据用户名和密码查询
        Example example = new Example(Reader.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account",loginVo.getAccount())
                .andEqualTo("password",loginVo.getPassword());
        example.or().andEqualTo("email",loginVo.getAccount())
                .andEqualTo("password",loginVo.getPassword());
        List<Reader> readers = readerMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(readers)){   //不存在该用户，账号或密码错误！
            throw new LyException(ExceptionEnum.LOGIN_ACCOUNT_OR_PASSWORD);
        }
        //获取数据库中的读者信息
        Reader reader = readers.get(0);
        //判断是否有自动登录
        if(loginVo.getAutoLogin()){ //有自动登录
            Integer day = bmProperties.getReaderAutoLoginDay()*60*24;
            String path = request.getContextPath()+"/pages/reader/";
            //根据 账户名
            Cookie cookie = new Cookie(bmProperties.getAutoCookieName(),reader.getAccount());
            //设置路径
            cookie.setPath(path);
            //bm/login/autoLogin
            //设置有效期
            cookie.setMaxAge(day);
            //设置用户端存储
            response.addCookie(cookie);
        }
        //将用户的登录信息保存到 session 中
        saveReaderToSession(request,reader);
        return "pages/reader/bookDatalist.jsp";
    }

    //管理员登录
    public String loginManager(LoginVo loginVo,HttpServletRequest request){
        //根据用户名和密码到管理员表中查询
        Example example = new Example(Manager.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account",loginVo.getAccount());
        criteria.andEqualTo("password",loginVo.getPassword());
        List<Manager> managers = managerMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(managers)){
            throw new LyException(ExceptionEnum.LOGIN_ACCOUNT_OR_PASSWORD);
        }
        //设置session
        HttpSession session = request.getSession();
        session.setAttribute(bmProperties.getManagerSessionName(),managers.get(0));
        return "pages/manager/bookDatalist.jsp";
    }

    //读者自动登录
    @Transactional
    public Boolean autoLoginReader(HttpServletResponse response, HttpServletRequest request) {
        //获取账户名 （编码）
        String account = CookieUtils.getCookieValue(request, bmProperties.getAutoCookieName());
        if(StringUtils.isBlank(account)){ //空字符串  删除该cookie
            CookieUtils.deleteCookie(request,response, bmProperties.getAutoCookieName(),request.getContextPath()+ bmProperties.getCookiePath());   //用户端删除该cookie
            return false;
        }
        //查询账户是否存在
        List<Reader> readers = readerMapper.select(new Reader(account));
        if(CollectionUtils.isEmpty(readers)){   //未查询到 登录失败 用户账户不存在
            CookieUtils.deleteCookie(request,response, bmProperties.getAutoCookieName(),request.getContextPath()+ bmProperties.getCookiePath());   //用户端删除该cookie
            return false;
        }
        //将用户信息存入session中
        saveReaderToSession(request,readers.get(0));
        return true;
    }

    //将用户的信息保存到 session 中 并设置最后的登录时间
    private void saveReaderToSession(HttpServletRequest request,Reader reader){
        //将用户的登录信息保存到 session 中
        HttpSession session = request.getSession();
        session.setAttribute(bmProperties.getReaderSessionName(),reader);
    }
}

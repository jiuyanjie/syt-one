package com.bookManagerment.service;

import com.bookManagerment.entity.BorrowBooks;
import com.bookManagerment.entity.MailTemplate;
import com.bookManagerment.entity.Reader;
import com.bookManagerment.enums.ExceptionEnum;
import com.bookManagerment.exception.LyException;
import com.bookManagerment.mapper.BorrowBookMapper;
import com.bookManagerment.mapper.MailTemplateMapper;
import com.bookManagerment.mapper.ReaderMapper;
import com.bookManagerment.utils.DateUtils;
import com.bookManagerment.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class MailTemplateService {

    @Autowired
    private MailTemplateMapper templateMapper;

    @Autowired
    private BorrowBookMapper borrowBookMapper;

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private HttpServletRequest request;

    public MailTemplate getMailTemplate(Integer id){
        return templateMapper.selectByPrimaryKey(id);
    }

    public void modifyMailTemplate(Integer id,String template) {
        templateMapper.updateByPrimaryKeySelective(new MailTemplate().setMtId(id).setTemplate(template));
    }

    public void modifyMailDefaultTemplate(Integer id, String defaultTemplate) {
        templateMapper.updateByPrimaryKeySelective(new MailTemplate().setMtId(id).setDefaultTemplate(defaultTemplate));
    }

    //提醒还书
    public void remind(Integer bbId) {
        //查询借阅记录是否还存在
        BorrowBooks borrowBook = borrowBookMapper.queryBorrowBookByBbId(bbId);
        if(borrowBook == null){ //该图书已被归还
            throw new LyException(ExceptionEnum.BORROW_BOOKS_IS_RETURN);
        }
        //获取用户的id查询用户的邮箱
        Integer rId = borrowBook.getRId();
        Reader reader = readerMapper.selectByPrimaryKey(rId);
        if(reader == null){ //用户不存在
            throw new LyException(ExceptionEnum.SERVICE_BUSY);
        }
        MailTemplate mailTemplate = null;
        //实际借书天数（现在的时间-租借的时间）
        int readyDay =  DateUtils.differenceDay(new Date(),borrowBook.getBbTime());
        //最大借书天数 = 应该还书的时间-租借的时间
        int maxDay = DateUtils.differenceDay(borrowBook.getDueTime(),borrowBook.getBbTime());
        //逾期天数 = 实际借书天数 - 最大借书天数
        int overDay = readyDay - maxDay;
        //用户是否逾期
        if(overDay <=0 ){ //还未逾期
            //查询未逾期模板
            mailTemplate = templateMapper.selectByPrimaryKey(1);
            borrowBook.setRemainingDays(maxDay-readyDay);
        }else{
            mailTemplate = templateMapper.selectByPrimaryKey(2);
            borrowBook.setRemainingDays(overDay);
        }

        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        String url = basePath;

        String aDocument = "<a href=\""+url+"\">"+url+"</a>";
        //发送的邮箱内容
        String emailMsg = mailTemplate.getTemplate()
                .replace("{borrowBooksTime}",DateUtils.dateFormat("yyyy年MM月dd日",borrowBook.getBbTime()))
                .replace("{dueTime}",DateUtils.dateFormat("yyyy年MM月dd日",borrowBook.getDueTime()))
                .replace("{bookName}",borrowBook.getBName())
                .replace("{userName}",borrowBook.getRName())
                .replace("{remainingDay}",borrowBook.getRemainingDays()+"")
                .replace("{systemUrl}",aDocument);
        //发送邮箱提醒
        try {
            mailUtils.sendRemind(reader.getEmail(),emailMsg);
        } catch (MessagingException e) {    //发送邮箱还书提醒失败！
            e.printStackTrace();
            throw new LyException(ExceptionEnum.SEND_REMIND_FAIL);
        }
    }
}

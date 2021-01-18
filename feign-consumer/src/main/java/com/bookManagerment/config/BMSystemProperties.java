package com.bookManagerment.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@PropertySource("classpath:system.properties")
@Component("bmProperties")
public class BMSystemProperties {

    @Value("${readerLogin.autoLoginDay}")
    private Integer readerAutoLoginDay;

    @Value("${readerLogin.autoCookieName}")
    private String autoCookieName;

    @Value("${readerLogin.sessionName}")
    private String readerSessionName;

    @Value("${manager.sessionName}")
    private String managerSessionName;

    @Value("${reader.cookiePath}")
    private String cookiePath;

    @Value("${reader.register.emailVerifyCodeMinute}")
    private Integer emailVerifyCodeMinute;

    @Value("${mail.host}")
    private String mailHost;

    @Value("${mail.account}")
    private String mailAccount;

    @Value("${mail.password}")
    private String mailPassword;

    @Value("${mail.content}")
    private String mailContent;

    @Value("${mail.reader.register.title}")
    private String emailReaderRegisterTitle;

    @Value("${mail.reader.register.contentModel}")
    private String mailReaderRegisterContentModel;

    @Value("${mail.reader.remind.title}")
    private String mailReaderRemindTitle;

    @Value("${order.ZFBTimeoutExpress}")
    private Integer OrderZFBTimeoutExpress;

    @Value("${order.ZFBTimeoutExpressAddTime}")
    private Integer ZFBTimeoutExpressAddTime;

    @Value("${borrowBooksAccountLest}")
    private Integer borrowBooksAccountLest;

    @Value("${maxBorrowBooksDay}")
    private Integer maxBorrowBooksDay;
}

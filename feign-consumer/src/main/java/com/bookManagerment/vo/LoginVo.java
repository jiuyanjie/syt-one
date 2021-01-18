package com.bookManagerment.vo;

import lombok.Data;

/**
 * 接收用户登录的参数
 */
@Data
public class LoginVo {

    private String account;
    private String password;
    private String type;
    private Boolean autoLogin;

}

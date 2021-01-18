package com.bookManagerment.service;

import com.bookManagerment.config.BMSystemProperties;
import com.bookManagerment.mapper.ReaderMapper;
import com.bookManagerment.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class ExitService {

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private BMSystemProperties bmProperties;

    //用户退出
    public void exitReader(HttpServletRequest request, HttpServletResponse response) {
        //删除cookie
        CookieUtils.deleteCookie(request,response, bmProperties.getAutoCookieName(),
                request.getContextPath()+ bmProperties.getCookiePath());
        //删除session
        request.getSession().invalidate();
    }

    //管理员退出
    public void exitManager(HttpServletRequest request) {
        //删除session
        request.getSession().invalidate();
    }
}

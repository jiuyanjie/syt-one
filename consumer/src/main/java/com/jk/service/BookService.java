package com.jk.service;

import com.jk.dto.AppointExecution;
import com.jk.pojo.Appointment;
import com.jk.pojo.BookBean;
import com.jk.pojo.StudentBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private RestTemplate restTemplate;

    public List<BookBean> getList() {
        return restTemplate.getForObject("http://PROVIDER/book2/list",List.class);
    }

    public List<BookBean> getSomeList(String name) {
        return restTemplate.getForObject("http://PROVIDER/book2/getSomeList",List.class,name);
    }

    public BookBean getById(Long bookId) {
        return restTemplate.getForObject("http://PROVIDER/book2/getById",BookBean.class,bookId);
    }

    public StudentBean validateStu(Long studentId, Long password) {
        return restTemplate.getForObject("http://PROVIDER/book2/validateStu",StudentBean.class,studentId,password);
    }

    public AppointExecution appoint(Long bookId, Long studentId) {
        return restTemplate.getForObject("http://PROVIDER/book2/appoint",AppointExecution.class,bookId,studentId);
    }

    public List<Appointment> getAppointByStu(long studentId) {
        return restTemplate.getForObject("http://PROVIDER/book2/getAppointByStu",List.class,studentId);
    }
}

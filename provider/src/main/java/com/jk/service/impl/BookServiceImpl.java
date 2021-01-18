package com.jk.service.impl;

import com.jk.dao.AppointmentDao;
import com.jk.dao.BookDao;
import com.jk.dao.StudentDao;
import com.jk.dto.AppointExecution;
import com.jk.enums.AppointStateEnum;
import com.jk.exception.AppointException;
import com.jk.exception.NoNumberException;
import com.jk.exception.RepeatAppointException;
import com.jk.pojo.Appointment;
import com.jk.pojo.BookBean;
import com.jk.pojo.StudentBean;
import com.jk.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookDao bookDao;
    @Autowired
    private AppointmentDao appointmentDao;
    @Autowired
    private StudentDao studentDao;

    @Override
    public BookBean getById(long bookId) {
        return bookDao.queryByid(bookId);
    }

    @Override
    public List<BookBean> getList() {
        return bookDao.queryAll(0, 1000);
    }

    @Override
    public StudentBean validateStu(Long studentId, Long password) {
        return studentDao.quaryStudent(studentId, password);
    }

    @Override
    public List<BookBean> getSomeList(String name) {
        return bookDao.querySome(name);
    }

    @Override
    public List<Appointment> getAppointByStu(long studentId) {
        return appointmentDao.quaryAndReturn(studentId);
    }

    @Override
    @Transactional
    public AppointExecution appoint(long bookId, long studentId) {
        try{													  //返回成功预约的类型。
            int update=bookDao.reduceNumber(bookId);//减库存
            if(update<=0){//已经无库存！
                throw new NoNumberException("no number");
            }else{
                //执行预约操作
                int insert=appointmentDao.insertAppointment(bookId, studentId);
                if(insert<=0){//重复预约
                    throw new RepeatAppointException("repeat appoint");
                }else{//预约成功
                    return new AppointExecution(bookId, AppointStateEnum.SUCCESS);
                }

            }
        } catch (NoNumberException e1) {
            throw e1;
        } catch (RepeatAppointException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // 所有编译期异常转换为运行期异常
            throw new AppointException("appoint inner error:" + e.getMessage());
        }
    }
}

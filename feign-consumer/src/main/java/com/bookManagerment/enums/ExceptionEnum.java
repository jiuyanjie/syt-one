package com.bookManagerment.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnum {

    APPOINT_ACCOUNT_BALANCE_LESS(406,"抱歉，{r_name}用户，账户余额少于{balanceLess}元不能借书!"),
    GREATER_THAN_MAX_BORROW_BOOKS_DAY(406,"抱歉，最大借书天数为{maxBorrowBooksDay}天！"),
    ACCOUNT_BALANCE_LESS(406,"抱歉，账户余额少于{balanceLess}元不能借书，请您点击右上角头像选择充值!"),
    SORRY_BOOK_BORROW_ALL(406,"抱歉，该图书已全部借出！"),
    QUERY_BY_RANGE_NOT_FOUND(406,"该范围下未查询到数据！"),
    QUERY_BY_BOOK_TYPE_NOT_FOUND(406,"该分类下还未添加图书！"),
    VERIFY_EXPIRY_OR_EMAIL_ERROR(406,"验证码已失效，或邮箱地址错误！"),
    SEND_REMIND_FAIL(500,"发送邮箱还书提醒失败！"),
    VERIFY_CODE_NOT_MATCHING(406,"邮箱验证码不匹配！"),
    //图书
    SEARCH_BOOK_NOT_FOUND(404,"未搜索到符合条件的图书信息！"),
    BOOK_NOT_FOUND(404,"未查询到图书信息！"),
    ADD_BOOK_FAIL(500,"添加失败!"),
    UPDATE_BOOK_FAIL(500,"修改失败!"),
    DELETE_BOOK_FAIL(500,"删除失败!"),
    BOOK_EXIST(406,"图书编号已存在!"),
    BOOK_DELETED(406,"抱歉图书信息已经失效，图书已被其他管理人员删除，请刷新页面！"),
    THIS_BOOK_RENTED(406,"该图书正在被租借，删除失败！"),
    BOOKS_INCLUDE_RENTED(406,"删除的图书中有正在租借的图书，删除失败！"),
    RESERVE_BOOK_FULL(406,"抱歉，图书预定人数已满！"),

    //图书分类
    BOOK_TYPE_NOT_FOUND(404,"图书类别未查询到，请添加图书类别！"),
    ADD_BOOK_TYPE_FAIL(500,"添加失败！"),
    DELETE_BOOK_TYPE_FAIL(500,"删除失败！"),
    UPDATE_BOOK_TYPE_FAIL(500,"修改失败！"),
    BOOK_TYPE_EXIST(406,"分类名已存在！"),

    //借书记录
    SEARCH_BORROW_BOOK_NOT_FOUND(404,"未搜索到符合条件的借书记录！"),
    BORROW_BOOK_NOT_FOUND(404,"当前还未有借书记录"),
    BORROW_BOOK_BY_ID_NOT_FOUND(404,"该记录已失效，请刷新页面！"),

    //预 借书记录
    SEARCH_RESERVE_BOOK_NOT_FOUND(404,"未搜索到符合条件的预定记录！"),
    RESERVE_BOOK_NOT_FOUND(404,"暂时还未有预定记录！"),
    RESERVE_NOT_FOUND(404,"预借书记未找到！"),
    BOOK_GET_DOWN(406,"您的书籍已经成功领取，请在借阅中 菜单查看"),

    //预定借书
    BOOK_INFO_LOSE(404,"抱歉，该图书信息已失效，请刷新页面!"),


    //预 还书的记录
    RESERVE_GIVE_BACK_NOT_FOUND(404,"暂时没有归还记录！"),
    SEARCH_RESERVE_GIVE_BACK_NOT_FOUND(404,"未搜索到符合条件的归还记录！"),

    //还书
    GIVE_BACK_BOOK_RESERVE_GIVE_BACK_NOT_FOUND(404,"该借书记录已被其他管理人员归还,或不存在该记录！"),
    BORROW_BOOKS_NOT_FOUND(404,"该图书已被归还！"),
    GIVE_BACK_BOOK_ERROR(500,"服务繁忙，还书失败！"),

    //读者注册
    READER_ACCOUNT_NAME_EXIST(406,"账户名已存在！"),

    //用户登录
    LOGIN_ACCOUNT_OR_PASSWORD(406,"账号或密码错误！"),
    LOGIN_READER_IS_LOGIN(406,"抱歉该用户已登录！"),

    //用户借书
    BOOK_STATUS_FALSE(406,"抱歉，该图书信息正在维护中，暂时不可借阅该图书，请您刷新页面！"),

    //系统
    SERVICE_BUSY(500,"服务繁忙！"),

    //读者申请还书
    BORROW_BOOKS_IS_RETURN(406,"图书已归还！"),
    SEND_REGISTER_VERIFY_CODE_FAIL(500,"发送邮箱验证码失败！");

    ;
    private Integer code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

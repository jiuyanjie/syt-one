package com.jk.pojo;

public class BookBean {
    private long bookId;
    private String name;
    private Integer number;
    private String introd;


    public BookBean(){

    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getIntrod() {
        return introd;
    }

    public void setIntrod(String introd) {
        this.introd = introd;
    }
}

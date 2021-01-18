package com.bookManagerment.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class MailTemplate {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer mtId;
    private String template;
    private String name;
    private String defaultTemplate;

    public MailTemplate setMtId(Integer mtId) {
        this.mtId = mtId;
        return this;
    }

    public MailTemplate setTemplate(String template) {
        this.template = template;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MailTemplate setDefaultTemplate(String defaultTemplate) {
        this.defaultTemplate = defaultTemplate;
        return this;
    }
}

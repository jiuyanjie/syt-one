package com.bookManagerment.controller.mannager;

import com.bookManagerment.entity.MailSymbol;
import com.bookManagerment.entity.MailTemplate;
import com.bookManagerment.service.MailSymbolService;
import com.bookManagerment.service.MailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/manager/mail")
@RestController
public class MailController {

    @Autowired
    private MailSymbolService symbolService;

    @Autowired
    private MailTemplateService templateService;

    /**
     * 获取邮箱占位符
     * @return
     */
    @GetMapping("/mailSetBtn")
    public ResponseEntity<List<MailSymbol>> getMailSetBtn(){
        return ResponseEntity.ok(symbolService.getMailSymbol());
    }

    /**
     * 根据id获取邮箱模板
     * @param id
     * @return
     */
    @GetMapping("/mailTemplate/{id}")
    public ResponseEntity<MailTemplate> getMailTemplate(@PathVariable("id") Integer id){
        return ResponseEntity.ok(templateService.getMailTemplate(id));
    }

    /**
     * 根据id修改邮箱模板
     * @param id
     * @param template
     * @return
     */
    @PutMapping("/mailTemplate/{id}")
    public ResponseEntity<Void> modifyMailTemplate(@PathVariable("id") Integer id,
                                                           @RequestBody String template){
        templateService.modifyMailTemplate(id,template);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据id修改默认邮箱模板
     * @param id
     * @param defaultTemplate
     * @return
     */
    @PutMapping("/mailTemplate/default/{id}")
    public ResponseEntity<Void> modifyMailDefaultTemplate(@PathVariable("id") Integer id,
                                                   @RequestBody String defaultTemplate){
        templateService.modifyMailDefaultTemplate(id,defaultTemplate);
        return ResponseEntity.ok().build();
    }

    //提醒还书
    @PostMapping("/remind/{bbId}")
    public ResponseEntity<Void> remind(@PathVariable("bbId") Integer bbId){
        templateService.remind(bbId);
        return ResponseEntity.ok().build();
    }

}

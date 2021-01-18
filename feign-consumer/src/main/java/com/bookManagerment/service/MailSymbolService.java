package com.bookManagerment.service;

import com.bookManagerment.entity.MailSymbol;
import com.bookManagerment.mapper.MailSymbolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailSymbolService {

    @Autowired
    private MailSymbolMapper symbolMapper;

   public List<MailSymbol> getMailSymbol(){
       return symbolMapper.selectAll();
   }
}

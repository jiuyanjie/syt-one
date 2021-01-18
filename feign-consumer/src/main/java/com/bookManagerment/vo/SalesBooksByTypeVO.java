package com.bookManagerment.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SalesBooksByTypeVO {

    private String typeName;
    private List<String> id;
    private List<Integer> sales;

    public  SalesBooksByTypeVO(String typeName){
        this.typeName = typeName;
        this.id = new ArrayList<String>();
        this.sales = new ArrayList<Integer>();
    }

}

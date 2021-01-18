package com.bookManagerment.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 图书表
 */
@Data
@NoArgsConstructor
public class Books implements Cloneable{
    @Id
    @GeneratedValue(generator="JDBC")
    private Integer bId;
    private String number;  //图书的编号
    private Integer btId;   //图书类别ID
    private String bName; //图书名
    private String author; //作者
    private String publisher;//出版社
    private Integer total; //总数量
    private Integer rentalUnit; // 租金/分
    private Integer overDueUnit;    //逾期租金/分
    private Boolean status;         //图书的状态 ,true表示开启，false表示关闭

    @Transient
    private String tName;
    @Transient
    private Integer inLibraryTotal; //在馆图书总数
    @Transient
    private String totalAndInLibraryTotal;  // 图书总数/在馆数
    @Transient
    private String rentalUnitStr; // 租金/元
    @Transient
    private String overDueUnitStr; //逾期租金/元
    @Transient
    private Integer reserveNum;      //该图书预约借书的人数

    public Books(Integer btId) {
        this.btId = btId;
    }

    public Books(String numberStr) {
        this.number = numberStr;
    }

    public void buildModifyBook(Books booksDB,Books books) {
        if(this.inLibraryTotal == null || this.inLibraryTotal == 0) {
            this.inLibraryTotal = booksDB.getInLibraryTotal();
        }
        if(this.rentalUnit == null || this.rentalUnit == 0){
            this.rentalUnit = booksDB.rentalUnit;
        }
        if(this.reserveNum == null || this.reserveNum == 0){
            this.reserveNum = booksDB.getReserveNum();
        }
        if(this.status == null || this.status == null){
            this.status = booksDB.getStatus();
        }
        Integer num = booksDB.getInLibraryTotal() + (books.getTotal()-booksDB.getTotal());
        books.setInLibraryTotal(num);
    }

    public String getTotalAndInLibraryTotal() {
        if(this.totalAndInLibraryTotal == null){
           this.totalAndInLibraryTotal = this.total+"/"+this.inLibraryTotal;
        }
        return this.totalAndInLibraryTotal;
    }

    public String getRentalUnitStr() {
        if(StringUtils.isBlank(this.rentalUnitStr)&&(this.rentalUnit !=null)){
            this.rentalUnitStr = this.rentalUnit/100.0+"元/天";
        }
        return this.rentalUnitStr;
    }

    public String getOverDueUnitStr() {
        if(this.overDueUnitStr == null && this.overDueUnit != null){
            this.overDueUnitStr = this.overDueUnit/100.0+"元/天";
        }
        return this.overDueUnitStr;
    }

    @Override
    public Books clone()  {
        Books book = null;
        try {
            book = (Books) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return book;
    }

    public void buildAddBook(Books books) {
        books.setStatus(true);
        books.setInLibraryTotal(0);
        books.setReserveNum(0);
        books.setInLibraryTotal(books.getTotal());
    }

    @Override
    public String toString() {
        return "Books{" +
                "bId=" + bId +
                ", btId=" + btId +
                ", bName='" + bName + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", total=" + total +
                ", rentalUnit=" + rentalUnit +
                ", overDueUnit=" + overDueUnit +
                ", status=" + status +
                ", tName='" + tName + '\'' +
                ", inLibraryTotal=" + inLibraryTotal +
                ", totalAndInLibraryTotal='" + totalAndInLibraryTotal + '\'' +
                ", rentalUnitStr='" + rentalUnitStr + '\'' +
                ", overDueUnitStr='" + overDueUnitStr + '\'' +
                ", reserveNum=" + reserveNum +
                '}';
    }
}

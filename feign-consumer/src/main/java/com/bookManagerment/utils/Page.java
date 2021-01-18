package com.bookManagerment.utils;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class Page<T> {
    
	private int total;	//总记录数
	private int page;	//总页数
	private int size;	//每页的数量
    private List<T> rows;//当前页数据

	public Page(PageInfo pageInfo) {
		this.total = (int) pageInfo.getTotal();	//总记录数
		this.page = pageInfo.getPages();	//总页数
		this.size = pageInfo.getPageSize();	//每页的数量
		this.rows = pageInfo.getList();		//当前页数据
	}

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "Page{" +
				"total=" + total +
				", page=" + page +
				", size=" + size +
				", rows=" + rows +
				'}';
	}
}

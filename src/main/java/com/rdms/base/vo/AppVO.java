package com.rdms.base.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("appVO")
@Scope("prototype")
public class AppVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long total;
	private int limit;
	private int offset;
	
	private boolean success;
	private String msg;
	
	// return only one row
	private Object row;
	// return more than one row
	private List<Object> rows = new ArrayList<Object>();
	
	public AppVO() {}
	
	public void addRows(List<Object> rows) {
		if(rows != null && rows.size() > 0) {
			this.rows.addAll(rows);
		}
	}
	
	public void addRow(Object row) {
		if(row != null)
		this.rows.add(row);
	}
	

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getRow() {
		return row;
	}

	public void setRow(Object row) {
		this.row = row;
	}

	public List<Object> getRows() {
		return rows;
	}

	public void setRows(List<Object> rows) {
		this.rows = rows;
	}
	
}

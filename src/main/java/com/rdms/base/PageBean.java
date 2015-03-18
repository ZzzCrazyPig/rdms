package com.rdms.base;

import java.util.List;

public class PageBean<E> {

	private int limit;
	private int offset;
	private long totalCount;
	private List<E> beanList;
	
	public PageBean(int offset, int limit, long totalCount) {
		this.offset = offset;
		this.limit = limit;
		this.totalCount = totalCount;
	}
	
	
	public int getLimit() {
		return limit;
	}


	public int getOffset() {
		return offset;
	}


	public long getTotalCount() {
		return totalCount;
	}
	
	public void setBeanList(List<E> beanList) {
		this.beanList = beanList;
	}
	public List<E> getBeanList() {
		return beanList;
	}
}

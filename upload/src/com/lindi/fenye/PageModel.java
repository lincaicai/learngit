package com.lindi.fenye;
import java.util.List;

public class PageModel<E> {

	//结果集
	private List<E> list;
	//查询记录数
	private int totalRecords;
	//第几页
	private int pageNo;
	//每页多少条记录
	private int pageSize;
	//总页数
	public int getTotalPages()
	{
		return (totalRecords + pageSize - 1) / pageSize;
	}
	
	public int getToPage()
	{
		return 1;
	}
	
	public int getPreviousPage()
	{
		if(pageNo <= 1)
		{
			return 1;
		}
		return pageNo - 1;
	}
	
	public int getNextPage()
	{
		if(pageNo >= getBottomPage())
		{
			return getBottomPage();
		}
		return pageNo + 1;
	}
	
	public int getBottomPage()
	{
		return getTotalPages();
	}
	
	public List<E> getList()
	{
		return list;
	}
	
	public void setList(List<E> list)
	{
		this.list = list;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}

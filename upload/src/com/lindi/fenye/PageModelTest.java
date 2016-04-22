package com.lindi.fenye;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PageModelTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int pageNo = 1;
		int pageSize = 10;
		findUserList(pageNo,pageSize);

	}
	
	public PageModel<Person> findUserList(int pageNo,int pageSize)
	{
		PageModel<Person> pageModel = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "select * from person order by id desc limit ?,? ";
		conn = DBUtil.getUtil().getConnection();
		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, (pageNo - 1) * pageSize);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			List<Person> personList = new ArrayList<Person>();
			while(rs.next())
			{
				Person person = new Person();
				
				personList.add(person);
			}
			pageModel = new PageModel<Person>();
			pageModel.setList(personList);
			pageModel.setTotalRecords(getTotalRecords(conn));
			pageModel.setPageSize(pageSize);
			pageModel.setPageNo(pageNo);
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			try
			{
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		return pageModel;
	}
	private int getTotalRecords(Connection conn)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "select count(*) from person";
		conn = DBUtil.getUtil().getConnection();
		int count = 0;
		try
		{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				count = rs.getInt(1);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			try{
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		return count;
	}

}

package com.lindi.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String uname = request.getParameter("username");
		String pword = request.getParameter("password");
		
		List<UserBean> list = Db.getAll();
		for(UserBean u: list)
		{
			if(u.getUname().equals(uname) && u.getPword().equals(pword))
			{
				request.getSession().setAttribute("username", u.getUname());
				response.sendRedirect("index.jsp");
				return;
			}
			
			out.write("erererererer");
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

}
class Db
{
	public static List<UserBean> list = new ArrayList<UserBean>();
	//方便在没有创建对象的情况下来进行调用（方法/变量）。
	static{
		list.add(new UserBean("a","1"));
		list.add(new UserBean("b","1"));
		list.add(new UserBean("c","1"));
		
	}
	public static List<UserBean> getAll()
	{
		return list;
	}
}

package com.lindi.login;

public class UserBean {

	private String uname;
	
	private String pword;
	
	public UserBean(String uname,String pword)
	{
		this.uname = uname;
		this.pword = pword;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPword() {
		return pword;
	}

	public void setPword(String pword) {
		this.pword = pword;
	}
	
	
}

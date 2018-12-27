package com.yangql.site.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLogin {
	@NotNull(message="{validate.userlogin.userName}")
	@Size(min=1,message="{validate.userlogin.userName}")
	private String userName;
	
	@NotNull(message="{validate.userlogin.passWord}")
	@Size(min=1,message="{validate.userlogin.passWord}")
	private String passWord;
	
	public void setUserName(String name) {
		this.userName=name;
	}
	public void setPassWord(String pwd) {
		this.passWord=pwd;
	}
	public String getUserName() {
		return this.userName;
	}
	public String getPassWord() {
		return this.passWord;
	}
}

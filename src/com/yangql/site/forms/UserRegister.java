package com.yangql.site.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
 * 用户注册页面
 */
public class UserRegister {
	@NotNull(message="{validate.userregister.userName}")
	@Size(min=1,message="{validate.userregister.userName}")
	private String userName;
	
	@NotNull(message="{validate.userregister.passWord}")
	@Size.List({
		@Size(min=1,message="{validate.userregister.passWord}"),
		@Size(min=8,message="{validate.userregister.passWord.Size}"),
		})
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

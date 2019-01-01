package com.yangql.site.entities;

import java.text.ParseException;
import java.util.Date;

import com.yangql.site.DateUtil;

public class ChatMessage {
	private Long id;	
	private String groupName;
	private msgType type;
	private Date date;	//时间戳
	private String timeStamp;	//时间转换成字符版本
	private String content;
	private String username;

	public msgType getType() {
		return this.type;
	}

	public void setType(msgType type) {
		this.type = type;
	}

	public static enum msgType {
		STARTED, JOINED, ERROR, LEFT, TEXT
	}

	public String getContent() {
		return content;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public String getUsername() {
		return username;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTimeStamp(String date) {
		this.timeStamp = date;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	/*
	 * 创建一个ChatMessage
	 */
	public static ChatMessage createMsg(String username,String groupname,String content,msgType type) throws ParseException {
		ChatMessage message=new ChatMessage();
		message.setUsername(username);
		message.setGroupName(groupname);
		message.setContent(content);
		message.setType(type);
		Date date= new Date();
		message.setDate(date);
		message.setTimeStamp(DateUtil.formatDate(date));
		return message;
	}
}

package com.yangql.site.entities;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.yangql.site.DateUtil;
@SuppressWarnings("serial")
@Entity
@Table(name="ChatMessage")
public class ChatMessage implements Serializable{
	private long id;	
	private String groupName;
	private msgType type;
	private Date date;	//时间戳
	private String timeStamp;	//时间转换成字符版本
	private String content;
	private String username;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="MessageType")
	public msgType getType() {
		return this.type;
	}
	@Basic
	public String getContent() {
		return content;
	}
	@Basic
	@Column(name="StrTimeStamp")
	public String getTimeStamp() {
		return timeStamp;
	}
	@Basic
	public String getUsername() {
		return username;
	}
	@Basic
	public String getGroupName() {
		return groupName;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DateTimeStamp")
	public Date getDate() {
		return date;
	}
	public void setType(msgType type) {
		this.type = type;
	}

	public static enum msgType {
		STARTED, JOINED, ERROR, LEFT, TEXT
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
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		String r="";
		
		return r
				+this.getId()+"\n"
				+this.getGroupName()+"\n"
				+this.getUsername()+"\n"
				+this.getType()+"\n"
				+this.getDate()+"\n"
				+this.getTimeStamp()+"\n"
				+this.getContent();
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

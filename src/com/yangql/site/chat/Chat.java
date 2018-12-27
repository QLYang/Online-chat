package com.yangql.site.chat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Chat {
	private String groupName;
	private ArrayList<ChatMessage> messageList=new ArrayList<>();
	private ArrayList<String> usernameList=new ArrayList<>();
	
	public String getGroupName() {
		return groupName;
	}
	public ArrayList<ChatMessage> getMessageList() {
		return messageList;
	}
	public ArrayList<String> getUsernameList() {
		return usernameList;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public void setMessageList(ChatMessage msg) {
		this.messageList.add(msg);
	}
	public void setUsernameList(String name) {
		this.usernameList.add(name);
	}
	
	public static class ChatMessage {
		private msgType type;
		private String timeStamp;
		private String content;
		private String username;
		
		
		public msgType getType() {
			return this.type;
		}
		public void setType(msgType type) {
			this.type = type;
		}
		public static enum msgType
	    {
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
		public void setTimeStamp() {
			this.timeStamp = date();
		}
		public void setUsername(String username) {
			this.username = username;
		}
		
		/*
		 * 工具函数：创建时间戳
		 */
		private SimpleDateFormat formatter=new SimpleDateFormat("HH:mm:ss");
		
		private String date() {
			return this.formatter.format(new Date());
		}
	}
}

package com.yangql.site.chat;

import java.util.ArrayList;

import com.yangql.site.entities.ChatMessage;

public class Chat {
	private Long groupId;
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
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
		
}

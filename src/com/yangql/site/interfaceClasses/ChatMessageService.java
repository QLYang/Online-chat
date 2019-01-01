package com.yangql.site.interfaceClasses;

import java.util.Date;
import java.util.List;

import com.yangql.site.entities.ChatMessage;

public interface ChatMessageService {
	List<ChatMessage> getChatMessagesByGroupName(String groupName);//获得整个组的聊天记录
	List<String> getGroupNameListByUserName(String username);//该用户加入的小组列表，按日期排序
	void saveChatMessage(ChatMessage message);
	void deleteExpiredChatMessages(Date date);//删除过期的记录
	void deleteChatMessagesByGroupName(String groupname);//删除特定组的记录
}

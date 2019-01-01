package com.yangql.site.interfaceClasses;

import java.util.Date;
import java.util.List;

import com.yangql.site.entities.ChatMessage;

public interface ChatMessageRepository extends GenericRepository<Long,ChatMessage> {
	public List<ChatMessage> getChatMessagesByGroupName(String groupName);
	public List<String> getGroupNameListByUserName(String username);
	public void saveChatMessage(ChatMessage message);
	public void deleteExpiredChatMessages(Date date);
	public void deleteChatMessagesByGroupName(String groupname);
}

package com.yangql.site.services;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangql.site.entities.ChatMessage;
import com.yangql.site.interfaceClasses.ChatMessageRepository;
import com.yangql.site.interfaceClasses.ChatMessageService;
@Service
public class ChatMessageManager implements ChatMessageService {
	@Inject ChatMessageRepository chatMessageRepo;
	
	@Override
	@Transactional
	public List<ChatMessage> getChatMessagesByGroupName(String groupName) {
		return this.chatMessageRepo.getChatMessagesByGroupName(groupName);
	}

	@Override
	@Transactional
	public List<String> getGroupNameListByUserName(String username) {
		return this.chatMessageRepo.getGroupNameListByUserName(username);
	}

	@Override
	@Transactional
	public void saveChatMessage(ChatMessage message) {
		this.chatMessageRepo.saveChatMessage(message);
		
	}

	@Override
	@Transactional
	public void deleteExpiredChatMessages(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void deleteChatMessagesByGroupName(String groupname) {
		// TODO Auto-generated method stub
		
	}
	
	

}

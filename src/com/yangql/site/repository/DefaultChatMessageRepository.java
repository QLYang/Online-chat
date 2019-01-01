package com.yangql.site.repository;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yangql.site.entities.ChatMessage;
import com.yangql.site.interfaceClasses.ChatMessageRepository;
@Repository
public class DefaultChatMessageRepository extends GenericJPARepository<Long,ChatMessage> implements ChatMessageRepository {

	@SuppressWarnings("unchecked")
	@Override
	public List<ChatMessage> getChatMessagesByGroupName(String groupName) {
		return this.entityManager.createQuery("SELECT cm FROM ChatMessage cm WHERE cm.groupName=:groupname")
				.setParameter("groupname", groupName)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getGroupNameListByUserName(String username) {
		return this.entityManager.createQuery("SELECT DISTINCT (cm.groupName) FROM ChatMessage cm WHERE cm.username=:username")
				.setParameter("username", username)
				.getResultList();
	}

	@Override
	public void saveChatMessage(ChatMessage message) {
		this.add(message);
		
	}

	@Override
	public void deleteExpiredChatMessages(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteChatMessagesByGroupName(String groupname) {
		// TODO Auto-generated method stub
		
	}


}

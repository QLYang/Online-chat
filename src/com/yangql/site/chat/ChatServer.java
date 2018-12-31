package com.yangql.site.chat;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.HandshakeResponse;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.yangql.site.chat.Chat.ChatMessage;

@ServerEndpoint(value = "/chat/{groupId}/{userName}", configurator = ChatServer.EndpointConfigurator.class)
public class ChatServer {
	private static ObjectMapper mapper = new ObjectMapper();

	public static Map<Long, ChatGroup> activeGroupsQueue = new Hashtable<>();
	public static Map<Long, ChatGroup> pendingGroupsQueue = new Hashtable<>(); // 还没有关联到session

	private static final String HTTP_SESSION_PROPERTY = "com.yangql.site.ws.HTTP_SESSION";

	@OnOpen
	public void onOpen(Session session, @PathParam("groupId") long groupId, @PathParam("userName") String userName) {
		HttpSession httpSession = (HttpSession) session.getUserProperties().get(ChatServer.HTTP_SESSION_PROPERTY);
		try {
			if (httpSession == null || httpSession.getAttribute("userName") == null) {
				session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "您未登录!"));
				return;
			}

			List<String> actions = session.getRequestParameterMap().get("action");
			if (actions != null && actions.size() == 1) {
				String action = actions.get(0);
				if (action.equals("create")) {
					ChatGroup activeGroup = ChatServer.pendingGroupsQueue.remove(groupId);// 从等待队列取出
					assert (activeGroup != null);
					activeGroup.setGroupMember(userName, session);
					ChatServer.activeGroupsQueue.put(activeGroup.groupId, activeGroup);// 加入激活队列
				} else if (action.equals("join")) {
					ChatGroup group = ChatServer.activeGroupsQueue.get(groupId);
					group.setGroupMember(userName, session);// 加入session
					group.chat.setUsernameList(userName);// 成员名字保存在chat
					// 向其他成员发送消息：xx已加入
					this.sendSpecificMsg(group, userName, ChatMessage.msgType.JOINED);
				}
			}
		} catch (Exception e) {
			log(e);
		}
	}

	@OnMessage
	public void onMessage(Session session, String msg, @PathParam("groupId") long groupId) {
		ChatGroup group = ChatServer.activeGroupsQueue.get(groupId);
		try {
			ChatMessage message = mapper.readValue(msg, ChatMessage.class);
			message.setTimeStamp();// 设置时间戳
			group.chat.setMessageList(message);// 保存msg
			// 发送给组员
			sendJsonMsgToOther(group, message);
		} catch (Exception e) {
			log(e);
		}

	}

	@OnClose
	public void onClose(Session session, CloseReason reason, @PathParam("groupId") long groupId,
			@PathParam("userName") String userName) {
		if (reason.getCloseCode() == CloseReason.CloseCodes.NORMAL_CLOSURE) // 正常关闭
		{
			ChatGroup group = ChatServer.activeGroupsQueue.get(groupId);
			try {
				if (group.groupMembers.size() == 1) { // 最后一个组员,将该组移除
					session.close();
					ChatServer.activeGroupsQueue.remove(groupId);

				} else {
					session.close();
					group.groupMembers.remove(userName);// 移除该用户的session
					group.memNums--;
					this.sendSpecificMsg(group, userName, ChatMessage.msgType.LEFT);// 发送离开消息
				}
			} catch (Exception e) {
				log(e);
			}
		} else {//非正常关闭
			log(reason);
		}
	}

	@OnError
	public void onError(Session session, Throwable e, @PathParam("groupId") long groupId,
			@PathParam("userName") String username) {
		try {
			ChatGroup group = ChatServer.activeGroupsQueue.get(groupId);
			session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, e.toString()));
			if (group.groupMembers.size() == 1) { // 最后一个组员,将该组移除
				ChatServer.activeGroupsQueue.remove(groupId);

			} else {
				group.groupMembers.remove(username);// 移除该用户的session
				group.memNums--;
				this.sendSpecificMsg(group, username, ChatMessage.msgType.ERROR);// 发送消息
			}
		} catch (Exception e2) {
			log(e);
		} finally {
			try {
				session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, e.toString()));
			} catch (Exception ignore) {
			}
		}

	}

	/*
	 * 发送特定消息(LEFT/JOINED)
	 */
	private void sendSpecificMsg(ChatGroup group, String username, ChatMessage.msgType type) {
		ChatMessage message = new ChatMessage();
		message.setTimeStamp();
		message.setType(type);
		message.setUsername(username);
		if (type.equals(ChatMessage.msgType.LEFT)) {
			message.setContent(" 离开了小组.");
		} else if (type.equals(ChatMessage.msgType.JOINED)) {
			message.setContent(" 加入了小组.");
		} else if (type.equals(ChatMessage.msgType.ERROR)) {
			message.setContent(" 因为系统错误离开了小组.");
		}
		group.chat.setMessageList(message);// 保存
		this.sendJsonMsgToOther(group, message);// 发送

	}

	/*
	 * 向组内其他成员发送json消息（包括自己）
	 */
	private void sendJsonMsgToOther(ChatGroup group, ChatMessage msg) {
		for (Session userSession : group.getGroupMembers().values()) {
			this.sendJsonMessage(userSession, msg);
		}
	}

	/*
	 * 发送json
	 */
	private void sendJsonMessage(Session session, ChatMessage message) {
		try {
			session.getBasicRemote().sendText(mapper.writeValueAsString(message));
		} catch (IOException e) {
			System.out.println("SendObject error");
		}
	}

	/*
	 * 在控制台输出消息
	 */
	private void log(Object o) {
		System.out.println(o);
	}

	public static class ChatGroup {
		private Long groupId;
		private Map<String, Session> groupMembers = new Hashtable<>();
		private Chat chat;
		private Long memNums;
		
		public Long getMemNums() {
			return memNums;
		}
		public void setMemNums(Long memNums) {
			this.memNums = memNums;
		}
		public Chat getChat() {
			return chat;
		}

		public Long getGroupId() {
			return groupId;
		}

		public Map<String, Session> getGroupMembers() {
			return groupMembers;
		}

		public void setChat(Chat chat) {
			this.chat = chat;
		}

		public Long setGroupId() {
			this.groupId = this.getIdSequence();
			return this.groupId;
		}

		public void setGroupMember(String name, Session groupMember) {
			this.memNums++;
			this.groupMembers.put(name, groupMember);
		}

		/*
		 * 设置id
		 */
		private volatile static Long idSequence = 1L;

		private Long getIdSequence() {
			return idSequence++;
		}

	}

	private static ChatGroup createGroup(String groupName, String username) {
		Chat newChat = new Chat();
		newChat.setGroupName(groupName);
		newChat.setUsernameList(username);

		ChatGroup newGroup = new ChatServer.ChatGroup();
		newGroup.setChat(newChat);
		newGroup.setGroupId();
		newGroup.setMemNums(0L);
		return newGroup;
	}

	public static Long pendingGroups(String groupName, String username) {
		ChatGroup newGroup = createGroup(groupName, username);
		ChatServer.pendingGroupsQueue.put(newGroup.getGroupId(), newGroup);
		return newGroup.getGroupId();
	}

	public static class EndpointConfigurator extends ServerEndpointConfig.Configurator {
		@Override
		public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
			super.modifyHandshake(config, request, response);

			config.getUserProperties().put(ChatServer.HTTP_SESSION_PROPERTY, request.getHttpSession());
		}
	}
}

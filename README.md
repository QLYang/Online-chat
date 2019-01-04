## 介绍：一个多人在线聊天室<br>
	前端：用户的登录/注册/注销，加入/创建聊天组，显示历史记录（根据加入过的聊天组名称）
	后端:用户与聊天记录的数据库相关操作
### src/com/yangql中的文件夹及文件的内容：

	config：Spring Framework的相关配置

	site：

		ChatServerController.java:控制器

		DateUtil.java:处理java时间的工具

		filter:过滤器，用于过滤请求

		form:表单对象（登录/注册）

		chat:聊天功能相关,websocket服务端，消息定义等

		entities:实体定义@Entity,用于数据持久化

		service:服务定义@Service

		repository:仓库定义@Repository

		interfaceClasses:接口的定义

### 1.在Tomcat中运行项目，在浏览器中打开(http://localhost:8080/ChatServer)，进入如下界面：
![image](https://github.com/QLYang/Online-chat/raw/master/screenshot/main.png)
### 2.登录表单及验证：
![image](https://github.com/QLYang/Online-chat/raw/master/screenshot/validate.png)
### 3.创建新聊天组：
![image](https://github.com/QLYang/Online-chat/raw/master/screenshot/create_newgroup.png)
### 4.发送消息：
![image](https://github.com/QLYang/Online-chat/raw/master/screenshot/chat_message.png)
### 5.第二个用户加入聊天组：
![image](https://github.com/QLYang/Online-chat/raw/master/screenshot/second_user.png)
### 6.多个用户进行聊天：
![image](https://github.com/QLYang/Online-chat/raw/master/screenshot/multi_chat.png)
### 7.查看历史消息--选择曾经加入过的组：
![image](https://github.com/QLYang/Online-chat/raw/master/screenshot/history_group.png)
### 8.查看在该组的聊天记录：
![image](https://github.com/QLYang/Online-chat/raw/master/screenshot/history_message.png)


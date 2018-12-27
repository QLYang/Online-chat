package com.yangql.site;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.yangql.site.chat.Chat;
import com.yangql.site.chat.ChatServer;
import com.yangql.site.chat.ChatServer.ChatGroup;
import com.yangql.site.entities.User;
import com.yangql.site.forms.UserLogin;
import com.yangql.site.forms.UserRegister;
import com.yangql.site.interfaceClasses.UserService;

@Controller
public class ChatServerController {
	@Inject UserService userService;	//服务
	/*
	 * 主页
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String home(Map<String, Object>model,HttpSession session) {
		Boolean isLogin=false;
		if(session.getAttribute("userName")!=null)
			isLogin=true;	//用户已登录
		model.put("isLogin", isLogin);
		return "home";
	}
	/*
	 * 活跃房间列表
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Map<String, Object>model) {
		model.put("activeGroups", ChatServer.activeGroupsQueue);
		return "list";
	}
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public ModelAndView list(Map<String, Object>model,HttpSession session,ServletRequest request) {
		String action=((HttpServletRequest)request).getParameter("action");
		
		if("create".equalsIgnoreCase(action)) {    //创建房间
			String userName=session.getAttribute("userName").toString();
			String groupName=((HttpServletRequest)request).getParameter("groupName");
			
			Long groupId=ChatServer.pendingGroups(groupName,userName);
			model.put("groupId", groupId);
			model.put("action", "create");
			model.put("userName", userName);
			return new ModelAndView("chatGroup");
		}
		else if ("join".equalsIgnoreCase(action)) {    //加入房间
			String groupIdString=((HttpServletRequest)request).getParameter("groupId");
			Long groupIdLong=Long.parseLong(groupIdString);
			String userName=session.getAttribute("userName").toString();
			
			assert(ChatServer.activeGroupsQueue.get(groupIdLong)!=null);
			
			ChatGroup group=ChatServer.activeGroupsQueue.get(groupIdLong);//获得聊天组
			Chat chat=group.getChat();
			chat.setUsernameList(userName);//在chat中加入用户名
			
			model.put("groupId", groupIdLong);
			model.put("action", "join");
			model.put("userName", userName);
			return new ModelAndView("chatGroup");
		}
		return new ModelAndView(new RedirectView("/",true));
	}
	
	/*
	 * 注册逻辑
	 */
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String register(Map<String, Object>model,HttpSession session) {
		UserRegister userForm=new UserRegister();
		model.put("userRegister", userForm);	//表单模型
		return "register";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ModelAndView register(Map<String, Object>model,HttpSession session,@Valid UserRegister form,Errors errors){	
		if (errors.hasErrors()) {
			model.put("userRegister", form);
			return new ModelAndView("register");
		}
		//不能重名
		User userTmp=userService.getUserByName(form.getUserName());
		if (userTmp!=null) {
			model.put("userExist", true);
			return new ModelAndView("register");
		}
		System.out.println("here");
		User user=new User();
		user.setUserName(form.getUserName());
		user.setUserPassword(form.getPassWord());
		userService.saveUser(user);    
		System.out.println("here2");
		
		session.setAttribute("userName", user.getUserName());    //设置会话
		return new ModelAndView(new RedirectView("/",true));	//重定向到主页
	}
	/*
	 * 用户登录逻辑
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(Map<String, Object>model,HttpSession session) {
		UserLogin userForm=new UserLogin();
		model.put("userLogin", userForm);	//表单模型
		return "login";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView login(Map<String, Object>model,@Valid UserLogin form,Errors errors,HttpSession session) {
		if (errors.hasErrors()) {//表单验证
			model.put("userLogin", form);
			return new ModelAndView("login");
		}
		User user=userService.getUserByName(form.getUserName());
		if (user==null) {	//用户不存在
			model.put("userDontExist", true);
			return new ModelAndView("login");
		}
		if(!user.getUserPassword().equals(form.getPassWord())) {
			model.put("pwdIncorrect", true);    //密码错误
			return new ModelAndView("login");
		}
		session.setAttribute("userName", user.getUserName());	//注册成功
		model.put("isLogin", true);
		return new ModelAndView("home");
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public View logout(Map<String, Object>model,HttpSession session) {
		session.invalidate();
		return new RedirectView("/",true);
	}
}

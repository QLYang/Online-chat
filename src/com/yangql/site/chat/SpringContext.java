package com.yangql.site.chat;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.yangql.site.interfaceClasses.ChatMessageService;

@Component
public class SpringContext implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	private static ChatMessageService chatMessageService;
	/*
	 * 描述：打印Spring管理的bean名称
	 */
	public static void printBean() {
		String[] list=(String[]) SpringContext.applicationContext.getBeanDefinitionNames();
		for (String string : list) {
			System.out.println(string);
		}
	}
	/*
	 * 描述：从Spring Context中获得ChatMessageService Bean
	 */
	public static ChatMessageService getChatMessageManager() {
		assert(applicationContext!=null);
		SpringContext.chatMessageService=(ChatMessageService) SpringContext.applicationContext.getBean("chatMessageManager");
		return SpringContext.chatMessageService;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContext.applicationContext = applicationContext;
	}

}

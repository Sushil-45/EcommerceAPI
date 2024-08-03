package com.ecommerce.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext apx = null;

	public static ApplicationContext getApplicationContext() {
		return apx;
	}

	public static void setAppContext(ApplicationContext context) {
		apx = context;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		setAppContext(applicationContext);
	}

}

package org.occourts.escheatment.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

public class SpringBeanUtil implements ApplicationContextAware {
	private static Logger LOGGER = LoggerFactory.getLogger(SpringBeanUtil.class);

	private volatile static SpringBeanUtil instance;
	private ApplicationContext context;
	private Environment environment;

	private SpringBeanUtil() {
		super();
	}

	public static SpringBeanUtil getInstance() {
		//No need to synchronize since this is first called when Spring context is loaded
		if (instance == null) {
			instance = new SpringBeanUtil();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String s) {
		if (this.context == null) {
			LOGGER.error("Spring context is Null");
			return null;
		}
		return (T) this.context.getBean(s);
	}

	public String getProperty(String key) {
		return this.environment.getProperty(key);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
		this.environment = this.context.getBean(Environment.class);
	}

}

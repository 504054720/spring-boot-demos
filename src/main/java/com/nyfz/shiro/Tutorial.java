package com.nyfz.shiro;

import java.io.IOException;
import java.io.InputStream;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tutorial {
	private static final Logger LOGGER = LoggerFactory.getLogger(Tutorial.class);
	public static void main(String[] args) {
		LOGGER.info("My first apache shiro application!");
		//System.exit(0);
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		
		SecurityManager securityManager = factory.getInstance();
		
		
		//======================
		Ini ini = new Ini();
		//ini.load(new InputStream());
		
		SecurityUtils.setSecurityManager(securityManager);
		
		Subject currentSubject = SecurityUtils.getSubject();
		Session session = currentSubject.getSession();
		
		session.setAttribute("someKey", "someValue");
		
		String avalue = (String) session.getAttribute("someKey");
		
		if("someValue".equals(avalue)){
			LOGGER.info("Retrieved the corrent value :"+avalue);
		}
		
		if(!currentSubject.isAuthenticated()){
			UsernamePasswordToken token = new UsernamePasswordToken("guest", "guest");
			token.setRememberMe(true);
			currentSubject.login(token);
		}
		LOGGER.info("User["+currentSubject.getPrincipal()+"] ,logg in successfully!");
		
		if(currentSubject.hasRole("schwartz")){
			LOGGER.info("User["+currentSubject.getPrincipal()+"] has this role!");
		}else{
			LOGGER.info("User["+currentSubject.getPrincipal()+"] has not this role!");
		}
		
		if(currentSubject.isPermitted("lightsaber:wind")){
			LOGGER.info("User["+currentSubject.getPrincipal()+"] has this permitted!");
		}else{
			LOGGER.info("User["+currentSubject.getPrincipal()+"] has not this permitted!");
		}
		
		if(currentSubject.isPermitted("winnebago:drive:eagle5")){
			LOGGER.info("User["+currentSubject.getPrincipal()+"] has this permitted!");
		}else{
			LOGGER.info("User["+currentSubject.getPrincipal()+"] has not this permitted!");
		}
		currentSubject.logout();
		System.exit(0);
	}

}

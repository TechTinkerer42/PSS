package org.ets.ereg.session.facade.shared.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.broadleafcommerce.common.email.domain.EmailTarget;
import org.broadleafcommerce.common.email.service.EmailService;
import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.ets.ereg.common.business.service.ApplicationConfigurationService;

import org.ets.ereg.domain.interfaces.model.common.ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service("etsEmailService")
public class ETSEmailServiceImpl implements ETSEmailService, InitializingBean {

	@Resource(name="blEmailService")
    protected EmailService blEmailService;
	
	@Resource(name = "applicationConfigurationService")
	private ApplicationConfigurationService applicationConfigurationService;
	
	private static Logger log = LoggerFactory.getLogger(ETSEmailServiceImpl.class);
	
	private boolean isEmailEnabled = true;
	
	@Resource(name="blMailSender")
	private JavaMailSenderImpl javaMailSenderImpl;
	
	String fromAddress;

	public void sendTemplateEmail(String emailAddress, EmailInfo emailInfo,  HashMap<String,Object> props) {
		if(isEmailEnabled) {
			try{
				emailInfo.setFromAddress(fromAddress);
				blEmailService.sendTemplateEmail(emailAddress, emailInfo, props);
			} catch(Exception e) {
				log.error("Error sending email", e);
			}
		}
	}

    public void sendTemplateEmail(EmailTarget emailTarget, EmailInfo emailInfo, HashMap<String,Object> props) {
    	if(isEmailEnabled) {
			try{
				emailInfo.setFromAddress(fromAddress);
				blEmailService.sendTemplateEmail(emailTarget, emailInfo, props);
			} catch(Exception e) {
				log.error("Error sending email", e);
			}
		}
    }

    public void sendBasicEmail(EmailInfo emailInfo, EmailTarget emailTarget, HashMap<String,Object> props) {
    	if(isEmailEnabled) {
			try{
				emailInfo.setFromAddress(fromAddress);
				blEmailService.sendBasicEmail(emailInfo, emailTarget, props);
			} catch(Exception e) {
				log.error("Error sending email", e);
			}
		}
    }
    
	@Override
	public void afterPropertiesSet() throws Exception {
		isEmailEnabled = Boolean.valueOf(applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.MAIL_ENABLED));
		javaMailSenderImpl.setHost(applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.SMTP_HOST));
		javaMailSenderImpl.setPort(Integer.parseInt(applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.SMTP_PORT)));
		javaMailSenderImpl.setProtocol(applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.SMTP_PROTOCOL));
		javaMailSenderImpl.setUsername(applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.SMTP_USERNAME));
		javaMailSenderImpl.setPassword(applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.SMTP_PASSWORD));
		String mailProperties = applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.MAIL_PROPERTIES);
		if(null != mailProperties && mailProperties.length() > 0){
			for(String property: mailProperties.split(",")){
				int index = property.indexOf("=");
				if(index > 0){
					String key = property.substring(0, index);
					String value = property.substring(index+1);
					javaMailSenderImpl.getJavaMailProperties().setProperty(key, value);
				}
			}
		}
		fromAddress = applicationConfigurationService.findApplicationConfigurationValue(ApplicationConfiguration.MAIL_FROM);
	}

}

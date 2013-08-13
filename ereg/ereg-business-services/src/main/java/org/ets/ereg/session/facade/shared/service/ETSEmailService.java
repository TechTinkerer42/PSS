package org.ets.ereg.session.facade.shared.service;

import java.util.HashMap;

import org.broadleafcommerce.common.email.domain.EmailTarget;
import org.broadleafcommerce.common.email.service.info.EmailInfo;

public interface ETSEmailService {
	public void sendTemplateEmail(String emailAddress, EmailInfo emailInfo,  HashMap<String,Object> props);

    public void sendTemplateEmail(EmailTarget emailTarget, EmailInfo emailInfo, HashMap<String,Object> props);

    public void sendBasicEmail(EmailInfo emailInfo, EmailTarget emailTarget, HashMap<String,Object> props);

}

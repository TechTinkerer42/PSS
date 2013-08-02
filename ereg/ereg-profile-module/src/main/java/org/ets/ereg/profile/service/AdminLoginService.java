package org.ets.ereg.profile.service;

import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.springframework.security.core.Authentication;

public interface AdminLoginService {

    Authentication adminLogin(ETSAdminUser adminUser);

    Authentication adminLogin(String username, String clearTextPassword);

}

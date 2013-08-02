package org.ets.ereg.profile.service.impl;


import javax.annotation.Resource;

import org.ets.ereg.domain.interfaces.model.profile.ETSAdminUser;
import org.ets.ereg.profile.service.AdminLoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

    public class AdminLoginServiceImpl implements AdminLoginService {

        @Resource(name="adminAuthenticationManager")
        private AuthenticationManager authenticationManager;

        @Resource(name="adminUserDetailsService")
        private UserDetailsService userDetailsService;

        @Override
        public Authentication adminLogin(ETSAdminUser adminUser) {
            return adminLogin(adminUser.getLogin(), adminUser.getUnencodedPassword());
        }

        @Override
        public Authentication adminLogin(String username, String clearTextPassword) {
            UserDetails principal = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, clearTextPassword, principal.getAuthorities());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        }

    }

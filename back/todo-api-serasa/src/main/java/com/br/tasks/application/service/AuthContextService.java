package com.br.tasks.application.service;

import com.br.tasks.domain.model.AccountProfileDO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthContextService {

    public String getAuthenticatedEmail() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var profile = (AccountProfileDO) auth.getPrincipal();
        return profile.getEmail();
    }

    public Long getAuthenticatedUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var profile = (AccountProfileDO) auth.getPrincipal();
        return profile.getId();
    }
}

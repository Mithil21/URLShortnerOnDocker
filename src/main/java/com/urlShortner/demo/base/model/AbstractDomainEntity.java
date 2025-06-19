package com.urlShortner.demo.base.model;

import com.urlShortner.demo.security.SecurityConfig;
import com.urlShortner.demo.user.Users;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.ZonedDateTime;

public abstract class AbstractDomainEntity {

    public String createdBy;

    public ZonedDateTime createdDate;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void assignDefault(){
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.createdBy = user.getUsername();
        this.createdDate = ZonedDateTime.now();
    }
}

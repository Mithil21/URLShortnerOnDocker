package com.urlShortner.demo.audit.model;

import com.urlShortner.demo.base.model.AbstractDomainEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "url_audit")
public class URLAudit extends AbstractDomainEntity {

    private String shortCode;

    private Integer numberOfClicks;

    private String originalUrl;
}

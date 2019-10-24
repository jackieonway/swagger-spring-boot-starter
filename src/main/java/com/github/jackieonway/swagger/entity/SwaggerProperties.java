/**
 * Jackie.
 * Copyright (c)) 2019 - 2019 All Right Reserved
 */
package com.github.jackieonway.swagger.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author Jackie
 * @version $id: SwaggerProperties.java v 0.1 2019-10-22 15:18 Jackie Exp $$
 */
@ConfigurationProperties(prefix = "spring.jackieonway.swagger")
public class SwaggerProperties {

    /**
     *  swagger api info
     */
    @NestedConfigurationProperty
    private SwaagerApiInfo apiInfo;

    /**
     *  swagger scan packages
     */
    private String scanPackages;

    /**
     * Enable or disable swagger : true enable
      */
    private Boolean enable = true;

    public SwaagerApiInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(SwaagerApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

    public String getScanPackages() {
        return scanPackages;
    }

    public void setScanPackages(String scanPackages) {
        this.scanPackages = scanPackages;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}

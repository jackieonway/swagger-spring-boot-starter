package com.github.jackieonway.swagger.entity;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

public class SwaagerApiInfo {
    /**
     * swagger 标题
     */
    private String title;
    /**
     * swagger 描述
     */
    private String description;
    /**
     * swagger 服务团队网址
     */
    private String termsOfServiceUrl;
    /**
     * swagger 联系信息
     */
    @NestedConfigurationProperty
    private SwaggerContact contact;
    /**
     * swagger 证书名称
     */
    private String license;
    /**
     * swagger 证书地址
     */
    private String licenseUrl;
    /**
     * swagger 接口版本信息
     */
    private String version;

    private List<SwaggerParameter> params;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public SwaggerContact getContact() {
        return contact;
    }

    public void setContact(SwaggerContact contact) {
        this.contact = contact;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<SwaggerParameter> getParams() {
        return params;
    }

    public void setParams(List<SwaggerParameter> params) {
        this.params = params;
    }
}
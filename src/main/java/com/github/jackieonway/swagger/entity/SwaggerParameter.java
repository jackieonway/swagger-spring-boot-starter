/**
 * Jackie.
 * Copyright (c)) 2019 - 2019 All Right Reserved
 */
package com.github.jackieonway.swagger.entity;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author Jackie
 * @version $id: SwaggerParameter.java v 0.1 2019-10-22 16:43 Jackie Exp $$
 */
public class SwaggerParameter {
    /**
     * swagger 参数名称
     */
    private String name;
    /**
     * swagger 描述
     */
    private String description;
    /**
     * swagger 参数默认值
     */
    private String defaultValue;
    /**
     * swagger 是否必须
     */
    private Boolean required;
    /**
     * swagger 参数依赖类型
     */
    @NestedConfigurationProperty
    private SwaggerModelReference modelRef;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }


    public SwaggerModelReference getModelRef() {
        return modelRef;
    }

    public void setModelRef(SwaggerModelReference modelRef) {
        this.modelRef = modelRef;
    }
}

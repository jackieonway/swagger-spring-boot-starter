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
     * swagger param name
     */
    private String name;

    /**
     * swagger param type such as "header" etc
     */
    private String paramType;

    /**
     * swagger param description
     */
    private String description;
    /**
     * swagger param default value
     */
    private String defaultValue;
    /**
     * swagger param is required
     */
    private Boolean required;
    /**
     * swagger param model reference
     */
    @NestedConfigurationProperty
    private SwaggerModelReference modelRef;

    public String getName() {
        return name;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
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

/**
 * Jackie.
 * Copyright (c)) 2019 - 2019 All Right Reserved
 */
package com.github.jackieonway.swagger.entity;

/**
 * @author Jackie
 * @version $id: SwaggerContact.java v 0.1 2019-10-22 16:43 Jackie Exp $$
 */
public class SwaggerContact {

    /**
     * 作者
     */
    private String name;

    /**
     *  服务网址
     */
    private String url;

    /**
     * 联系邮箱
     */
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

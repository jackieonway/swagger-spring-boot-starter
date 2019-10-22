/**
 * Jackie.
 * Copyright (c)) 2019 - 2019 All Right Reserved
 */
package com.github.jackieonway.swagger.configuration;

import com.github.jackieonway.swagger.annotation.EnableSwagger;
import com.github.jackieonway.swagger.entity.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Jackie
 * @version $id: SwaggerAutoConfiguration.java v 0.1 2019-10-22 15:17 Jackie Exp $$
 */
@Configuration
//@ConditionalOnBean(annotation = EnableSwagger.class)
@EnableConfigurationProperties(SwaggerProperties.class)
@EnableSwagger2
public class SwaggerAutoConfiguration {

    @Autowired
    private SwaggerProperties swaggerProperties;


    @Bean
    public Docket swaggerRestApi() {
        //在配置好的配置类中增加此段代码即可
        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        docket = docket.apiInfo(apiInfo());
        ApiSelectorBuilder select = docket.select();
        if (!StringUtils.isEmpty(swaggerProperties.getScanPackages())) {
            select.apis(RequestHandlerSelectors.basePackage(swaggerProperties.getScanPackages()));
        }else {
            select.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
        }
        select.paths(PathSelectors.any());
        select.build();
        List<SwaggerParameter> parameters = swaggerProperties.getApiInfo().getParams();
        if (CollectionUtils.isEmpty(parameters)){
            return docket;
        }
        List<Parameter> parameterList = new ArrayList<>();
        parameters.forEach(param -> {
            ParameterBuilder parameterBuilder = new ParameterBuilder();
            if (!StringUtils.isEmpty(param.getName())){
                parameterBuilder.name(param.getName());
            }
            if (!StringUtils.isEmpty(param.getDefaultValue())){
                parameterBuilder.defaultValue(param.getDefaultValue());
            }
            if (!StringUtils.isEmpty(param.getDescription())){
                parameterBuilder.description(param.getDescription());
            }
            if (!StringUtils.isEmpty(param.getRequired())){
                parameterBuilder.required(param.getRequired());
            }
            if (Objects.nonNull(param.getModelRef())){
                SwaggerModelReference modelRef = param.getModelRef();
                if (!StringUtils.isEmpty(modelRef.getType())){
                    ModelRef modelRef1 = new ModelRef(modelRef.getType());
                    parameterBuilder.modelRef(modelRef1);
                }
            }
            parameterList.add(parameterBuilder.build());
        });
        docket.globalOperationParameters(parameterList);
        return docket;
    }

    private ApiInfo apiInfo(){
        SwaagerApiInfo apiInfo = swaggerProperties.getApiInfo();
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        if (!StringUtils.isEmpty(apiInfo.getTitle())){
            apiInfoBuilder.title(apiInfo.getTitle());
        }
        if (!StringUtils.isEmpty(apiInfo.getDescription())){
            apiInfoBuilder.description(apiInfo.getDescription());
        }
        if (!StringUtils.isEmpty(apiInfo.getTermsOfServiceUrl())){
            apiInfoBuilder.termsOfServiceUrl(apiInfo.getTermsOfServiceUrl());
        }
        if (!StringUtils.isEmpty(apiInfo.getLicense())){
            apiInfoBuilder.license(apiInfo.getLicense());
        }
        if (!StringUtils.isEmpty(apiInfo.getLicenseUrl())){
            apiInfoBuilder.licenseUrl(apiInfo.getLicenseUrl());
        }
        if (!StringUtils.isEmpty(apiInfo.getVersion())){
            apiInfoBuilder.version(apiInfo.getVersion());
        }
        SwaggerContact contact = apiInfo.getContact();
        if (Objects.nonNull(contact)){
            boolean name = StringUtils.isEmpty(contact.getName());
            boolean url = StringUtils.isEmpty(contact.getUrl());
            boolean email = StringUtils.isEmpty(contact.getEmail());
            Contact contact1 = new Contact(name?"":contact.getName(),url?"":contact.getUrl(),email?"":contact.getEmail());
            apiInfoBuilder.contact(contact1);
        }
        return apiInfoBuilder.build();
    }
}

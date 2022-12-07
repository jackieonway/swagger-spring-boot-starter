/**
 * Jackie.
 * Copyright (c)) 2019 - 2019 All Right Reserved
 */
package com.github.jackieonway.swagger.configuration;

import com.github.jackieonway.swagger.entity.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Jackie
 * @version $id: SwaggerAutoConfiguration.java v 0.1 2019-10-22 15:17 Jackie Exp $$
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@EnableOpenApi
public class SwaggerAutoConfiguration {

    @Resource
    private SwaggerProperties swaggerProperties;


    @Bean
    public Docket swaggerRestApi() {
        //在配置好的配置类中增加此段代码即可
        Docket docket = new Docket(DocumentationType.OAS_30);

        docket = docket.apiInfo(apiInfo()).enable(swaggerProperties.getEnable());
        ApiSelectorBuilder select = docket.select();
        if (!ObjectUtils.isEmpty(swaggerProperties.getScanPackages())) {
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
        List<RequestParameter> parameterList = new ArrayList<>();
        parameters.forEach(param -> {
            RequestParameterBuilder requestParameterBuilder = new RequestParameterBuilder();
            if (!ObjectUtils.isEmpty(param.getName())){
                requestParameterBuilder.name(param.getName());
            }
            ExampleBuilder exampleBuilder = new ExampleBuilder();
            if (!ObjectUtils.isEmpty(param.getDefaultValue())){
                exampleBuilder.value(param.getDefaultValue());
            }
            if (!ObjectUtils.isEmpty(param.getDescription())){
                requestParameterBuilder.description(param.getDescription());
            }
            if (!ObjectUtils.isEmpty(param.getRequired())){
                requestParameterBuilder.required(param.getRequired());
            }
            if (!ObjectUtils.isEmpty(param.getParamType())){
                requestParameterBuilder.in(param.getParamType());
            }
            if (!ObjectUtils.isEmpty(param.getModelRef())){
                SwaggerModelReference modelRef = param.getModelRef();
                if (!ObjectUtils.isEmpty(modelRef.getType())){
                    exampleBuilder.mediaType(modelRef.getType());
                    requestParameterBuilder.example(exampleBuilder.build());
                }
            }
            parameterList.add(requestParameterBuilder.build());
        });
        docket.globalRequestParameters(parameterList);
        return docket;
    }

    private ApiInfo apiInfo(){
        SwaagerApiInfo apiInfo = swaggerProperties.getApiInfo();
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        if (StringUtils.hasText(apiInfo.getTitle())){
            apiInfoBuilder.title(apiInfo.getTitle());
        }
        if (StringUtils.hasText(apiInfo.getDescription())){
            apiInfoBuilder.description(apiInfo.getDescription());
        }
        if (StringUtils.hasText(apiInfo.getTermsOfServiceUrl())){
            apiInfoBuilder.termsOfServiceUrl(apiInfo.getTermsOfServiceUrl());
        }
        if (StringUtils.hasText(apiInfo.getLicense())){
            apiInfoBuilder.license(apiInfo.getLicense());
        }
        if (StringUtils.hasText(apiInfo.getLicenseUrl())){
            apiInfoBuilder.licenseUrl(apiInfo.getLicenseUrl());
        }
        if (StringUtils.hasText(apiInfo.getVersion())){
            apiInfoBuilder.version(apiInfo.getVersion());
        }
        SwaggerContact contact = apiInfo.getContact();
        if (Objects.nonNull(contact)){
            boolean name = StringUtils.hasText(contact.getName());
            boolean url = StringUtils.hasText(contact.getUrl());
            boolean email = StringUtils.hasText(contact.getEmail());
            Contact contact1 = new Contact(
                    name ? contact.getName() : "",
                    url ? contact.getUrl() : "",
                    email ? contact.getEmail(): "");
            apiInfoBuilder.contact(contact1);
        }
        return apiInfoBuilder.build();
    }

    @Bean
    @ConditionalOnClass(CorsConfiguration.class)
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(
            WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier,
            ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes,
            CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties, Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes,
                corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath),
                shouldRegisterLinksMapping, null);
    }

    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath)
                || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }
}

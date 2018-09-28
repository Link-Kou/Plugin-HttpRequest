package com.plugin.httprequest.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class SpaceNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("httpProcessor", new SpaceBeanDefinitionParser());
    }

}

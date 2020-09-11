package com.linkkou.httprequest.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lk
 */
public class SpaceBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class getBeanClass(Element element) {
        return HTTPBeanProcessor.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        NodeList nodeList = element.getChildNodes();
        List<String> list = new ArrayList<>();
        for (int i = 0 ;i< nodeList.getLength();i++){
            Node node = nodeList.item(i);
            if(node.getNodeType() == 1){
                list.add(node.getTextContent());
            }
        }
        String[] strings = list.toArray(new String[list.size()]);
        bean.addPropertyValue("prefix",strings);
    }
}

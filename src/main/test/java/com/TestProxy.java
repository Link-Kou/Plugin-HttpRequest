package com;


import com.plugin.httprequest.processor.HTTPProxyInstanceTest;
import com.proxyinstance.TestInterface;
import org.junit.Before;
import org.junit.Test;

//@RunWith(SpringJUnit4ClassRunner.class)
public class TestProxy {

    TestInterface testInterface = (TestInterface) new HTTPProxyInstanceTest(TestInterface.class,"").getProxy();


    @Before
    public void init() {

    }

    @Test
    public void testdemo() {

        testInterface.add();
    }
}

package com;

import com.api.AuthHttpServer;
import com.api.MsgPushServer;
import com.api.tmsfindallprovince;
import com.model.ResFixedTokenServes;
import com.model.ResUserInfoServers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.plugin.httprequest.retrofitesfactory.converter.cookie.HeadCookies;
import com.plugin.httprequest.HTTPResponse;


/**
 * @author LK
 * @date 2018-03-28 15:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/config/spring/spring-mvc.xml"})
public class MsgPushServerTest {

	@Autowired
	private MsgPushServer msgPushServer;

	@Autowired
	private AuthHttpServer authHttpServer;

	@Autowired
	private tmsfindallprovince tmsfindallprovince;

	@Before
	public void init() {

	}

	@Test
	public void shouldAddModule() {
		msgPushServer.sendCode("15858832864", "测试", "djoutuserserver");
	}

	@Test
	public void requestAuthServer() {
		HTTPResponse<ResFixedTokenServes> reqFixedTokenServes = authHttpServer.exchangetoken("","asd=asd");
		if (reqFixedTokenServes.isSuccessful()) {
			ResFixedTokenServes resFixedTokenServes = reqFixedTokenServes.getModel();
			if (resFixedTokenServes.isSuccess() && resFixedTokenServes.getToken() != null) {
				HTTPResponse<ResUserInfoServers> innerUserModel = authHttpServer.getUserByToken(resFixedTokenServes.getToken());
				if (innerUserModel.isSuccessful()) {
					ResUserInfoServers resUserInfoServers = innerUserModel.getModel();
					if (resUserInfoServers.getUser() != null) {

					}
				}
			}
		}
	}

	@Test
	public void requestAuthServer2() {
		/*authHttpServer.exchangetoken("27970cfe-33db-423a-9b77-5f98d3265555","asd=asd")
				.isChainModeCall((e) -> {
					//if (e.isSuccess() && e.getToken() != null) {
						authHttpServer.getUserByToken(e.getToken()).isChainModeCall((f) -> {
							if (f.getUser() != null) {
								return null;
							}
							return null;
						});
					//}
					return null;
				});*/
	}

	@Test
	public void requestAuthServer3(){
		//"token=12132;devTokenId=123456"
		HeadCookies headCookie = new HeadCookies((x)->{
			 x.put("123","33")
					 .put("123","123");
		});
		//tmsfindallprovince.exchangetoken(headCookie,headCookie,"asd");
		tmsfindallprovince.exchangetoken2("sd");
	}
}
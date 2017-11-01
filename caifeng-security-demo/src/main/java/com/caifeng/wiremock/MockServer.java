package com.caifeng.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.removeAllMappings;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

public class MockServer {

	public static void main(String[] args) {
		//此处项目依赖版本问题，产生错误
		configureFor(8040);
		removeAllMappings();
		
		stubFor(get(urlPathEqualTo("/order/1"))
				.willReturn(aResponse().withBody("{\"id\":1}").withStatus(200)));
	}
}

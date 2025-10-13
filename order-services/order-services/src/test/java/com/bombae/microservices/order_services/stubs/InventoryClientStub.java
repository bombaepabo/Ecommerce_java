package com.bombae.microservices.order_services.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
public class InventoryClientStub {
    public static void stubInventoryCall(String skuCode,Integer quantity){
        stubFor(get(urlEqualTo("/api/inventory?sku_code=" + skuCode + "&quantity=" +quantity))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type","application/json")
                        .withBody("true")));

    }
}

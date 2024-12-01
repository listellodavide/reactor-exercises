package com.adiwave.reactorexercises.common;

import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

public abstract class AbstractHttpClient {

    private static final String BASE_URL = "http://localhost:7070/";

    protected final HttpClient httpClient;

    public AbstractHttpClient() {
        var loopResources = LoopResources.create("adiwave", 4, true); // kind of manager of event loop resources
        this.httpClient = HttpClient.create().runOn(loopResources).baseUrl(BASE_URL);
    }
}

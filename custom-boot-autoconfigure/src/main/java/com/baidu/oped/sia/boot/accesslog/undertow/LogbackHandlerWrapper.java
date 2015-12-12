package com.baidu.oped.sia.boot.accesslog.undertow;

import io.undertow.server.ExchangeCompletionListener;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.accesslog.AccessLogReceiver;

/**
 * Created by mason on 12/12/15.
 */
public class LogbackHandlerWrapper implements HttpHandler {

    private final HttpHandler next;
    private final AccessLogReceiver accessLogReceiver;
    private final ExchangeCompletionListener exchangeCompletionListener = new AccessLogCompletionListener();

    public LogbackHandlerWrapper(HttpHandler next, AccessLogReceiver accessLogReceiver) {
        this.next = next;
        this.accessLogReceiver = accessLogReceiver;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.addExchangeCompleteListener(exchangeCompletionListener);
        next.handleRequest(exchange);
    }

    private class AccessLogCompletionListener implements ExchangeCompletionListener {
        @Override
        public void exchangeEvent(HttpServerExchange exchange, NextListener nextListener) {
            try {
                accessLogReceiver.logMessage("");
            } finally {
                nextListener.proceed();
            }
        }
    }


}

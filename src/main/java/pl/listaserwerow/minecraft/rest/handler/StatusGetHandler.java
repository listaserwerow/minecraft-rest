package pl.listaserwerow.minecraft.rest.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import pl.listaserwerow.minecraft.rest.PathHandlerProvider;

public class StatusGetHandler
        implements HttpHandler
{
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception
    {
        exchange.getResponseHeaders().add(PathHandlerProvider.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send("{\"status\": \"ONLINE\"}");
    }
}

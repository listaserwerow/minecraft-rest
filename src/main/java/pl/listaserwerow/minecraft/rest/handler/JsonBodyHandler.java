package pl.listaserwerow.minecraft.rest.handler;

import com.networknt.utility.StringUtils;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import java.nio.charset.StandardCharsets;

public abstract class JsonBodyHandler
        implements HttpHandler
{
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception
    {
        if (exchange.isInIoThread())
        {
            exchange.dispatch(this);
            return;
        }

        exchange.startBlocking();
        String body = StringUtils.inputStreamToString(exchange.getInputStream(), StandardCharsets.UTF_8);

        this.handleRequest(exchange, body);
    }

    public abstract void handleRequest(HttpServerExchange exchange, String body) throws Exception;
}

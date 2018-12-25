package pl.listaserwerow.minecraft.rest;

import com.networknt.handler.HandlerProvider;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.util.HttpString;
import io.undertow.util.Methods;
import pl.listaserwerow.minecraft.rest.handler.ExecuteCommandPostHandler;
import pl.listaserwerow.minecraft.rest.handler.PlayerOnlineGetHandler;
import pl.listaserwerow.minecraft.rest.handler.StatusGetHandler;

public class PathHandlerProvider
        implements HandlerProvider
{
    public static final HttpString CONTENT_TYPE = new HttpString("Content-Type");

    @Override
    public HttpHandler getHandler()
    {
        // @formatter:off
        return Handlers.routing()
                .add(Methods.GET, "/status", new StatusGetHandler())
                .add(Methods.GET, "/player/{name}", new PlayerOnlineGetHandler())
                .add(Methods.POST, "/execute/command", new ExecuteCommandPostHandler());
        // @formatter:on
    }
}

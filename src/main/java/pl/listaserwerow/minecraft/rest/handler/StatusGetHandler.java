package pl.listaserwerow.minecraft.rest.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.bukkit.Bukkit;
import pl.listaserwerow.minecraft.MinecraftRest;
import pl.listaserwerow.minecraft.rest.PathHandlerProvider;
import pl.listaserwerow.minecraft.rest.model.StatusModel;

public class StatusGetHandler
        implements HttpHandler
{
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception
    {
        exchange.getResponseHeaders().add(PathHandlerProvider.CONTENT_TYPE, "application/json");

        StatusModel statusModel = new StatusModel();
        statusModel.setOnline(Bukkit.getOnlinePlayers().size());
        statusModel.setMaxOnline(Bukkit.getMaxPlayers());

        exchange.getResponseSender().send(MinecraftRest.objectMapper().writeValueAsString(statusModel));
    }
}

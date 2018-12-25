package pl.listaserwerow.minecraft.rest.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.BadRequestException;
import org.bukkit.Bukkit;
import pl.listaserwerow.minecraft.MinecraftRest;
import pl.listaserwerow.minecraft.rest.model.PlayerModel;

import java.util.Deque;

public class PlayerOnlineGetHandler
        implements HttpHandler
{
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception
    {
        Deque<String> nameParams = exchange.getQueryParameters().get("name");
        if (nameParams == null || nameParams.size() == 0)
        {
            throw new BadRequestException();
        }
        String name = nameParams.element();
        if (name == null || name.isEmpty())
        {
            throw new BadRequestException();
        }

        PlayerModel playerModel = new PlayerModel();
        playerModel.setOnline(Bukkit.getPlayerExact(name) != null);

        exchange.getResponseSender().send(MinecraftRest.objectMapper().writeValueAsString(playerModel));
    }
}

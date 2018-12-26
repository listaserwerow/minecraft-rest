package pl.listaserwerow.minecraft;

import io.undertow.Undertow;
import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import pl.listaserwerow.minecraft.rest.PathHandlerProvider;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinecraftRestPlugin
        extends JavaPlugin
{
    private Undertow httpServer;

    @Override
    public void onDisable()
    {
        this.httpServer.stop();
    }

    @Override
    public void onEnable()
    {
        MinecraftRest.setPlugin(this);

        PathHandlerProvider handlerProvider = new PathHandlerProvider();

        // @formatter:off
        Undertow.Builder httpBuilder = Undertow.builder()
                .setHandler(handlerProvider.getHandler());
        // @formatter:on

        FileConfiguration config = this.getConfig();

        List<Map<String, Object>> listeners = (List<Map<String, Object>>) config.getList("listeners");
        if (listeners != null)
        {
            for (Map<String, Object> listener : listeners)
            {
                httpBuilder.addHttpListener((int) listener.get("port"), (String) listener.get("host"));
            }
        }
        if (listeners == null || listeners.size() <= 0)
        {
            listeners = new ArrayList<>();
            Map<String, Object> listenerMap = new HashMap<>();
            listenerMap.put("host", "0.0.0.0");
            listenerMap.put("port", 8080);
            listeners.add(listenerMap);

            config.set("listeners", listeners);
            //TODO find open port
        }

        List<Map<String, Object>> tokens = (List<Map<String, Object>>) config.getList("tokens");
        if (tokens != null)
        {
            for (Map<String, Object> tokenMap : tokens)
            {
                Token token = new Token((String) tokenMap.get("name"), (String) tokenMap.get("token"));
                MinecraftRest.getTokens().put(token.getName(), token);
            }
        }
        if (MinecraftRest.getTokens().size() <= 0)
        {
            Token token = new Token("default", RandomStringUtils.randomAlphanumeric(32));
            MinecraftRest.getTokens().put(token.getName(), token);
        }
        this.saveConfig();

        this.httpServer = httpBuilder.build();
        this.httpServer.start();

        this.httpServer.getListenerInfo().forEach(listenerInfo ->
        {
            InetSocketAddress socketAddress = (InetSocketAddress) listenerInfo.getAddress();
            this.getLogger()
                .info("Listen HTTP on " + socketAddress.getAddress().getHostAddress() + ":" + socketAddress.getPort());
        });
    }

    public void saveConfig()
    {
        FileConfiguration config = this.getConfig();

        List<Map<String, Object>> tokens = new ArrayList<>();
        MinecraftRest.getTokens().forEach((name, token) ->
        {
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("name", token.getName());
            tokenMap.put("token", token.getToken());
            tokens.add(tokenMap);
        });
        config.set("tokens", tokens);

        try
        {
            config.save(new File(this.getDataFolder(), "config.yml"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

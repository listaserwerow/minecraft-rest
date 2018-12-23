package pl.listaserwerow.minecraft;

import io.undertow.Undertow;
import org.bukkit.plugin.java.JavaPlugin;
import pl.listaserwerow.minecraft.rest.PathHandlerProvider;

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
        PathHandlerProvider handlerProvider = new PathHandlerProvider();

        // @formatter:off
        this.httpServer = Undertow.builder()
                                  .addHttpListener(8080, "0.0.0.0")
                                  .setHandler(handlerProvider.getHandler())
                                  .build();
        // @formatter:on

        this.httpServer.start();

        System.out.println("after");
    }
}

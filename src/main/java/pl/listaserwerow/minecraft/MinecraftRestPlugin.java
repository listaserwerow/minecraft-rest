package pl.listaserwerow.minecraft;

import io.undertow.Undertow;
import org.bukkit.plugin.java.JavaPlugin;
import pl.listaserwerow.minecraft.rest.PathHandlerProvider;

public class MinecraftRestPlugin
        extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        PathHandlerProvider handlerProvider = new PathHandlerProvider();
        Undertow server = Undertow.builder()
                                  .addHttpListener(8080, "0.0.0.0")
                                  .setHandler(handlerProvider.getHandler())
                                  .build();
        server.start();

        System.out.println("after");
    }
}

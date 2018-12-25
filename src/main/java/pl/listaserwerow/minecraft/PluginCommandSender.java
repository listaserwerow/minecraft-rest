package pl.listaserwerow.minecraft;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PluginCommandSender
        implements RemoteConsoleCommandSender
{
    private List<String> returnMessages = new ArrayList<>();

    public List<String> getReturnMessages()
    {
        return returnMessages;
    }

    @Override
    public boolean isPermissionSet(String s)
    {
        return true;
    }

    @Override
    public boolean isPermissionSet(Permission permission)
    {
        return true;
    }

    @Override
    public boolean hasPermission(String s)
    {
        return true;
    }

    @Override
    public boolean hasPermission(Permission permission)
    {
        return true;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b)
    {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin)
    {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b, int i)
    {
        return null;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int i)
    {
        return null;
    }    @Override
    public void sendMessage(String s)
    {
        this.returnMessages.add(s);
    }

    @Override
    public void removeAttachment(PermissionAttachment permissionAttachment)
    {

    }

    @Override
    public void recalculatePermissions()
    {

    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions()
    {
        return null;
    }

    @Override
    public boolean isOp()
    {
        return true;
    }

    @Override
    public void setOp(boolean b)
    {

    }



    @Override
    public Server getServer()
    {
        return Bukkit.getServer();
    }

    @Override
    public String getName()
    {
        return "minecraft-rest";
    }

    @Override
    public Spigot spigot()
    {
        return new Spigot() {
            @Override
            public void sendMessage(BaseComponent component)
            {
                PluginCommandSender.this.sendMessage(component.toLegacyText());
            }

            @Override
            public void sendMessage(BaseComponent... components)
            {
                for (BaseComponent component : components)
                {
                    this.sendMessage(component);
                }
            }
        };
    }

    @Override
    public void sendMessage(String[] strings)
    {
        for (String string : strings)
        {
            this.sendMessage(string);
        }
    }
}

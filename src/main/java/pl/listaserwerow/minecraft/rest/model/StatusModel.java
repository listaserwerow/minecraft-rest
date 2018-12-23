package pl.listaserwerow.minecraft.rest.model;

import pl.listaserwerow.minecraft.MinecraftRest;

public class StatusModel
{
    private int online;
    private int maxOnline;

    public String getStatus()
    {
        return "ONLINE";
    }

    public String getVersion()
    {
        return MinecraftRest.getVersion();
    }

    public int getOnline()
    {
        return online;
    }

    public void setOnline(int online)
    {
        this.online = online;
    }

    public int getMaxOnline()
    {
        return maxOnline;
    }

    public void setMaxOnline(int maxOnline)
    {
        this.maxOnline = maxOnline;
    }
}

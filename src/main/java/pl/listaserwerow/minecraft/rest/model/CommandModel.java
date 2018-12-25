package pl.listaserwerow.minecraft.rest.model;

import java.util.ArrayList;
import java.util.List;

public class CommandModel
{
    private List<String> commands = new ArrayList<>();

    public List<String> getCommands()
    {
        return commands;
    }

    public void setCommands(List<String> commands)
    {
        this.commands = commands;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("CommandModel{");
        sb.append("commands='").append(commands).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

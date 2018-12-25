package pl.listaserwerow.minecraft.rest.model;

import java.util.ArrayList;
import java.util.List;

public class ExecuteCommandReturnModel
{
    private boolean execute;
    private List<String> messages = new ArrayList<>();

    public boolean isExecute()
    {
        return execute;
    }

    public void setExecute(boolean execute)
    {
        this.execute = execute;
    }

    public List<String> getMessages()
    {
        return messages;
    }
}

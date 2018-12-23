package pl.listaserwerow.minecraft;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MinecraftRest
{
    private static ObjectMapper objectMapper;

    public static String getVersion()
    {
        return "0.1";
    }

    public static ObjectMapper objectMapper()
    {
        if (objectMapper != null)
        {
            return objectMapper;
        }
        objectMapper = new ObjectMapper();

        return objectMapper;
    }
}

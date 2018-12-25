package pl.listaserwerow.minecraft;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class MinecraftRest
{
    private static ObjectMapper       objectMapper;
    private static Map<String, Token> tokens = new HashMap<>();

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

    public static Map<String, Token> getTokens()
    {
        return tokens;
    }
}

package pl.listaserwerow.minecraft.rest.handler;

import com.fasterxml.jackson.databind.JsonNode;
import io.undertow.server.HttpServerExchange;
import org.bukkit.Bukkit;
import pl.listaserwerow.minecraft.MinecraftRest;
import pl.listaserwerow.minecraft.PluginCommandSender;
import pl.listaserwerow.minecraft.Token;
import pl.listaserwerow.minecraft.rest.HttpStatuses;
import pl.listaserwerow.minecraft.rest.model.CommandModel;
import pl.listaserwerow.minecraft.rest.model.ExecuteCommandReturnModel;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExecuteCommandPostHandler
        extends JsonBodyHandler
{
    @Override
    public void handleRequest(HttpServerExchange exchange, String body) throws Exception
    {
        JsonNode node = MinecraftRest.objectMapper().readTree(body);
        if (!node.hasNonNull("data") || !node.hasNonNull("signature"))
        {
            exchange.setStatusCode(HttpStatuses.BAD_REQUEST);
            return;
        }
        String service = node.hasNonNull("service") ? node.get("service").asText("default") : "default";
        Token token = MinecraftRest.getTokens().get(service);
        if (token == null)
        {
            token = MinecraftRest.getTokens().get("default");
        }
        if (token == null)
        {
            exchange.setStatusCode(HttpStatuses.SERVICE_UNAVAILABLE);
            return;
        }
        String signature = createSignature(token.getToken(),
                MinecraftRest.objectMapper().writeValueAsString(node.get("data")));
        if (!signature.equals(node.get("signature").asText()))
        {
            System.out.println(signature);
            exchange.setStatusCode(HttpStatuses.UNAUTHORIZED);
            return;
        }

        CommandModel data = MinecraftRest.objectMapper().treeToValue(node.get("data"), CommandModel.class);
        if (data == null || data.getCommands() == null)
        {
            exchange.setStatusCode(HttpStatuses.BAD_REQUEST);
            return;
        }

        Map<String, ExecuteCommandReturnModel> commands = new LinkedHashMap<>();
        for (String command : data.getCommands())
        {
            PluginCommandSender commandSender = new PluginCommandSender();
            boolean execute = Bukkit.dispatchCommand(commandSender, command);

            ExecuteCommandReturnModel commandReturnModel = new ExecuteCommandReturnModel();
            commandReturnModel.setExecute(execute);
            commandReturnModel.getMessages().addAll(commandSender.getReturnMessages());

            commands.put(command, commandReturnModel);
        }
        exchange.getResponseSender().send(MinecraftRest.objectMapper().writeValueAsString(commands));
    }

    public static String createSignature(String token, String message)
            throws java.security.InvalidKeyException, NoSuchAlgorithmException
    {
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(token.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256HMAC.init(secretKey);

        return bytesToHex(sha256HMAC.doFinal(message.getBytes()));
    }

    private static String bytesToHex(byte[] hashInBytes)
    {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes)
        {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

# Minecraft-Rest API

Plugin do serwera minecraft, który udostępnia API umożliwiające zarządzanie serwerem.

API działa na protokole HTTP/2

___
Użycie API:

##### GET `/status`
- nie wymagania zabezpieczenia
- parametry jakie zwraca serwer:
  - status (zawsze ONLINE)
  - version (zwraca wersje pluginu)
  - online (liczbe osób na serwerze)
  - maxOnline (maksymalną liczbe osób na serwerze)
- informacja zwrotna:
```json
{
    "online": 0,
    "maxOnline": 20,
    "version": "0.1",
    "status": "ONLINE"
}
```
___

##### GET `/player/{name}`
- nie wymaga zabezpieczenia
- parametry, które trzeba wysłać do serwera
  - name (nazwa gracza, którego chcemy sprawdzić)
- parametry jakie zwraca serwer:
  - online (czy gracz jest online true/false)
- informacja zwrotna:
```json
{
    "online": false
}
```
___
##### POST `/execute/command`
- wymaga zabezpieczenia
- informacje przesyłane do serwera
  - commands - komendy, które chcemy wykonać na serwerze
- przykładowy json, który wysyłamy do serwera:
  - token - `ueVcL38ATpEroIIX4YoUvt4wr1TWGOeM`
```json
{
    "service": "default",
    "data": {
        "commands": ["help", "plugins", "ban", "ban xdddd"]
    }, 
    "signature": "e513a67beb79af0233a572fce909dd7511121f562b3c461f2885a64aeb522b54"
}
```
- przykładowy json, który zwraca serwer:
```json
{
    "help": {
        "execute": true,
        "messages": [
            "§e--------- §fHelp: Index (1/9) §e---------------------",
            "§7Use /help [n] to get page n of help.",
            "§6Aliases: §fLists command aliases",
            "§6Bukkit: §fAll commands for Bukkit",
            "§6Minecraft: §fAll commands for Minecraft",
            "§6/advancement: §fA Mojang provided command.",
            "§6/ban: §fA Mojang provided command.",
            "§6/ban-ip: §fA Mojang provided command.",
            "§6/banlist: §fA Mojang provided command.",
            "§6/blockdata: §fA Mojang provided command."
        ]
    },	
    "plugins": {
        "execute": true,
        "messages": [
            "Plugins (1): §aminecraft-rest"
        ]
    },
    "ban": {
        "execute": true,
        "messages": []
    },
    "ban xdddd": {
        "execute": true,
        "messages": []
    }
}
```

___
#### Zapytania, które wymagają zabezpieczenia
- parametry, które trzeba wysłać do serwera
  - service - nazwa usługi, która korzysta z naszego API (ustawiamy w konfiguracji)
  - data - informacje przesyłane do serwera
  - signature - podpis danych wysłanych w parametrze **data** przy użyciu klucza przypisanego do nazwy usługi

#### Generowanie sygnatury
- do wygenerowania sygnatury użyta jest metoda [HMAC](https://pl.wikipedia.org/wiki/HMAC) SHA256
- przykład generowania sygnatury w PHP:
```php
$data = array();
$data["commands"] = array("1", "2", "3");

$signature = hash_hmac("sha256", json_encode($data), "defaulttoken");
```
- przyklad generowania sygnatury w języku Java:
```java
String signature = createSignature("defaulttoken", "[1, 2, 3]");

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
```
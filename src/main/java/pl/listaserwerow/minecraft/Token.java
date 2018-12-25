package pl.listaserwerow.minecraft;

public class Token
{
    private final String name;
    private String token;

    public Token(String name, String token)
    {
        this.name = name;
        this.token = token;
    }

    public String getName()
    {
        return name;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}

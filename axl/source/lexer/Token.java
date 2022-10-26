package axl.source.lexer;

public class Token
{
    public TokenType type;

    public String  value_string; // string || word
    public int     value_integer;    // num
    public float   value_float;  // num

    Token(TokenType type, String value_string, int value_integer, float value_float)
    {
        this.type = type;
        this.value_string = value_string;
        this.value_integer = value_integer;
        this.value_float = value_float;
    }
}



package axl.source.lexer;

public class Token
{
    public TokenType type;

    public String  value_string; // string || word
    public int     value_integer;    // num
    public float   value_float;  // num

    Token(TokenType t, String value_s, int value_i, float value_f)
    {
        type = t;
        value_string = value_s;
        value_integer = value_i;
        value_float = value_f;
    }
}



package axl.source.lexer;

public class Token
{
    public TokenType type;

    public String  value_string; // string || word
    public int     value_integer;    // num
    public float   value_float;  // num

    public Token(TokenType t, String value_s, int value_i, float value_f)
    {
        type = t;
        value_string = value_s;
        value_integer = value_i;
        value_float = value_f;
    }

    public boolean is_word()
    {
        return type == TokenType.WORD;
    }

    public boolean is_integer()
    {
        return type == TokenType.INTEGER;
    }

    public boolean is_float()
    {
        return type == TokenType.FLOAT;
    }

    public boolean is_op()
    {
        return !(type == TokenType.WORD || type == TokenType.INTEGER || type == TokenType.FLOAT);
    }

    public boolean is_comma()
    {
        return type == TokenType.COMMA;
    }

    public boolean is_lpar()
    {
        return type == TokenType.LPAR;
    }

    public boolean is_rpar()
    {
        return type == TokenType.RPAR;
    }

    public boolean is_lbrace()
    {
        return type == TokenType.LBRACE;
    }

    public boolean is_rbrace()
    {
        return type == TokenType.RBRACE;
    }

    public boolean is_endfile()
    {
        return type == TokenType.ENDFILE;
    }
}



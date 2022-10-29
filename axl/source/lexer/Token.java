package axl.source.lexer;

import axl.source.ast.DataType;

import javax.swing.text.html.parser.Parser;

public class Token
{
    public TokenType type;

    public String  value_string; // string || word
    public int     value_integer;    // num
    public float   value_float;  // num

    public Token(TokenType type, String value_string, int value_integer, float value_float)
    {
        this.type = type;
        this.value_string = value_string;
        this.value_integer = value_integer;
        this.value_float = value_float;
    }

    public boolean is_word()
    {
        return type == TokenType.WORD;
    }

    public boolean is_data(){
        return switch (type) {
            case INTEGER, STRING, FLOAT -> true;
            default -> false;
        };
    }

    public boolean is_integer()
    {
        return type == TokenType.INTEGER;
    }

    public boolean is_float()
    {
        return type == TokenType.FLOAT;
    }

    public boolean isModifier(){
        return switch (value_string) {
            case "public", "private", "default", "protected", "static" -> true;
            default -> false;
        };
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

    public boolean is_semi()
    {
        return type == TokenType.SEMI;
    }

    public boolean is_dot()
    {
        return type == TokenType.DOT;
    }
}



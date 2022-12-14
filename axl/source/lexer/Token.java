package axl.source.lexer;

import axl.source.ast.DataType;

import javax.swing.text.html.parser.Parser;

public class Token
{
    public TokenType type;

    public String  value_string; // string || word
    public int     value_integer;    // num
    public float   value_float;  // num
    public char    value_char;

    public Token(TokenType type, String value_string)
    {
        this.type = type;
        this.value_string = value_string;
    }

    public Token(TokenType type)
    {
        this.type = type;
    }

    public Token(float value_float)
    {
        this.type = TokenType.FLOAT;
        this.value_float = value_float;
    }

    public Token(int value_integer)
    {
        this.type = TokenType.INTEGER;
        this.value_integer = value_integer;
    }

    public Token(char c)
    {
        this.type = TokenType.CHAR;
        this.value_char = c;
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

    public boolean is_access(){
        return switch (value_string) {
            case "public", "private", "default", "protected" -> true;
            default -> false;
        };
    }

    public byte get_access() {
        return switch (value_string) {
            case "public"    -> 1;
            case "private"   -> 2;
            case "protected" -> 3;
            default -> 0;
        };
    }

    public boolean is_static(){
        return value_string.equals("static");
    }

    public boolean is_final(){
        return value_string.equals("final");
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

    public boolean is_equal()
    {
        return type == TokenType.EQUAL;
    }

    public boolean is_plus()
    {
        return type == TokenType.PLUS;
    }

    public boolean is_minus()
    {
        return type == TokenType.MINUS;
    }

    public boolean is_star()
    {
        return type == TokenType.STAR;
    }

    public boolean is_slash()
    {
        return type == TokenType.SLASH;
    }
}



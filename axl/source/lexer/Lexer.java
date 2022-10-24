package axl.source.lexer;

import java.util.ArrayList;

import static java.lang.System.exit;

public class Lexer
{
    private final String content;
    private int i = 0;
    private char current = 0;

    public ArrayList<Token> tokens = new ArrayList<>();

    public Lexer(String content)
    {
        this.content = content;
    }

    private void next()
    {
        if(i < content.length()-1)
            current = content.charAt(++i);
        else {
            i = content.length();
            current = '\0';
        }
    }

    private void last()
    {
        if(i > 0)
            current = content.charAt(--i);
    }

    private void skip()
    {
        while(current == ' ' || current == '\n' || current == '\t' || current == '\r')
            next();
    }

    private void token_string()
    {
        char end = current;
        next();

        StringBuilder str = new StringBuilder();

        while(current != end)
        {
            str.append(current);
            next();
            if(i == content.length()) exit(2);
        }

        next();

        String value = str.toString();
        tokens.add(new Token(TokenType.STRING, value, 0, 0));
    }

    private void token_number()
    {
        StringBuilder str = new StringBuilder();
        TokenType type = TokenType.INTEGER;

        if(current == '-' || current == '.')
        {
            str.append(current);

            if(current == '.') {
                type = TokenType.FLOAT;
                next();
                if(!(current >= '0' && '9' >= current))
                {
                    last();
                    token_op();
                    return;
                }
                last();
            }

            next();
        }

        while((current >= '0' && '9' >= current) || current == '.')
        {
            if(current == '.')
            {
                if(type == TokenType.FLOAT) exit(1);
                else type = TokenType.FLOAT;
            }

            str.append(current);
            next();
        }

        String value = str.toString();
        Token token;
        if(type == TokenType.FLOAT)
        {
            token = new Token(type, "", 0, Float.parseFloat(value));
        }
        else
        {
            token = new Token(type, "", Integer.parseInt(value), 0);
        }

        tokens.add(token);
    }

    private void token_word()
    {
        StringBuilder str = new StringBuilder();

        while(Character.isDigit(current) || Character.isLetter(current) || current == '_')
        {
            str.append(current);
            next();
        }

        String value = str.toString();
        tokens.add(new Token(TokenType.WORD, value, 0, 0));
    }

    private void token_op()
    {
        TokenType type = switch (current) {
            case '(' -> TokenType.LPAR;
            case ')' -> TokenType.RPAR;
            case '{' -> TokenType.LBRACE;
            case '}' -> TokenType.RBRACE;
            case ';' -> TokenType.SEMI;
            case '+' -> TokenType.PLUS;
            case '-' -> TokenType.MINUS;
            case '*' -> TokenType.STAR;
            case '/' -> TokenType.SLASH;
            case '.' -> TokenType.DOT;
            case '=' -> TokenType.EQUAL;
            default -> TokenType.UNDEFINED;
        };
        next();
        tokens.add(new Token(type, "", 0, 0));
    }

    public void gen_tokens()
    {
        current = content.charAt(i);
        while (i < content.length()-1) {
            if (current == ' ' || current == '\n' || current == '\t' || current == '\r')
                skip();

            if (current == '"' || current == '\'') {
                token_string();
                continue;
            }

            if ((current >= '0' && '9' >= current) || current == '-' || current == '.') {
                token_number();
                continue;
            }

            if(Character.isDigit(current) || Character.isLetter(current) || current == '_') {
                token_word();
                continue;
            }

            token_op();
        }
    }
}
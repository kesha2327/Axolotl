package axl.source.lexer;

import java.util.ArrayList;

import static java.lang.System.exit;

public class Lexer
{
    private final String CONTENT;
    private int i = 0;
    private char current = 0;

    public ArrayList<Token> tokens = new ArrayList<>();

    public Lexer(String CONTENT)
    {
        this.CONTENT = CONTENT;
    }

    private void next()
    {
        if(i < CONTENT.length()-1)
            current = CONTENT.charAt(++i);
        else {
            i = CONTENT.length();
            current = '\0';
        }
    }

    private void last()
    {
        if(i > 0)
            current = CONTENT.charAt(--i);
    }

    private void skip()
    {
        while(current == ' ' || current == '\n' || current == '\t' || current == '\r')
            next();
    }

    private void token_string()
    {
        next();

        StringBuilder str = new StringBuilder();

        while(current != '"')
        {
            str.append(current);
            next();
            if(i == CONTENT.length()) exit(1);
        }

        next();

        tokens.add(new Token(TokenType.STRING, str.toString()));
    }

    private void token_char()
    {
        next();
        tokens.add(new Token(current));
        next();

        if(current != '\'')
            exit(1);
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
                if(type == TokenType.FLOAT) exit(2);
                else type = TokenType.FLOAT;
            }

            str.append(current);
            next();
        }

        float value = Float.parseFloat(str.toString());
        Token token;
        if(type == TokenType.FLOAT)
        {
            token = new Token(value);
        }
        else
        {
            token = new Token((int)value);
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

        tokens.add(new Token(TokenType.WORD, str.toString()));
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
            case ',' -> TokenType.COMMA;
            case '=' -> TokenType.EQUAL;
            default -> TokenType.UNDEFINED;
        };
        next();
        tokens.add(new Token(type));
    }

    public void gen_tokens()
    {
        current = CONTENT.charAt(i);
        while (i < CONTENT.length()-1) {
            if (current == ' ' || current == '\n' || current == '\t' || current == '\r')
                skip();

            if (current == '"') {
                token_string();
                continue;
            }

            if (current == '\'') {
                token_char();
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

        tokens.add(new Token(TokenType.ENDFILE));
    }
}
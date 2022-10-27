package axl.source.ast.parser;

import axl.source.ast.AstCompound;
import axl.source.lexer.Token;
import axl.source.lexer.TokenType;

import java.util.ArrayList;

import static java.lang.System.exit;

public class parser {
    private final ArrayList<Token> tokens;
    private Token current;
    private int i = 0;

    public AstCompound AbstractSyntaxTree = new AstCompound();

    public parser(ArrayList<Token> tokens)
    {
        this.tokens = tokens;
    }

    private void next()
    {
        if(i < tokens.size())
        {
            current = tokens.get(i++);
        }
        else
        {
            current = new Token(TokenType.ENDFILE, "", 0, 0);
        }
    }

    private boolean is_reserved(String word){
        if (word.equals("class"))        return true;
        if (word.equals("void"))         return true;
        if (word.equals("int"))          return true;
        return word.equals("float");
    }

    private void parse_file(){
        next();
        while (!current.is_endfile()) {
            if (current.is_word()) {
                if (current.value_string.equals("class")) {
                    next();
                    if (!is_reserved(current.value_string)) {
                        parse_class();
                    }
                }
            }
        }

        if(!current.is_endfile())
            exit(1);
    }

    private void parse_class(){}

    private void parse_function(){}

    private void parse_function_call(){}

    private void parse_var_definition(){}

    private void parse_var(){}

    private void parse_data(){}

    private void parse_statement(){}

    public void parse(){}
}

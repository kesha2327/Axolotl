package axl.source;

import axl.source.lexer.Lexer;
import axl.source.lexer.Token;
import axl.source.lexer.TokenType;

public class Axolotl {
    static String code =
            """
            class Axolotl {
                int x = 21;
                println(x);
            }
            """;

    public static void main(String[] args) {
        Lexer lexer = new Lexer(code);
        lexer.gen_tokens();
        for(int i = 0; i < lexer.tokens.size(); i++){
            Token token = lexer.tokens.get(i);
            System.out.print(token.type+" ");

            if(token.type == TokenType.STRING || token.type == TokenType.WORD)
                System.out.println(lexer.tokens.get(i).value_string);
            else if (token.type == TokenType.INTEGER)
                System.out.println(lexer.tokens.get(i).value_integer);
            else if (token.type == TokenType.FLOAT)
                System.out.println(lexer.tokens.get(i).value_float);
            else
                System.out.println();
        }
    }
}

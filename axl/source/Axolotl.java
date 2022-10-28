package axl.source;

import axl.source.ast.parser.Parser;
import axl.source.lexer.Lexer;

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

        Parser parser = new Parser(lexer.tokens);
        parser.parse_file();
    }
}

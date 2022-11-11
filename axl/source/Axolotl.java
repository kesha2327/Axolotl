package axl.source;

import axl.source.ast.parser.Parser;
import axl.source.lexer.Lexer;

public class Axolotl {





    static String code =
            """
            package org.test;
            
            public class Axolotl
            {
                public static void main()
                {
                    int x = 4;
                    println(4);
                }
            }
            """;

    public static void main(String[] args) {

        Lexer lexer = new Lexer(code); //1 - 2 exit
        lexer.gen_tokens();

        Parser parser = new Parser(lexer.tokens); //3 - 16 exit
        parser.parse_file();

        //total number of exit methods: 20
    }
}

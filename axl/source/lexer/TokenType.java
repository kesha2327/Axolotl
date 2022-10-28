package axl.source.lexer;

public enum TokenType {
    WORD,
    STRING,
    INTEGER,
    FLOAT,

    LPAR,     // (
    RPAR,     // )
    LBRACE,   // {
    RBRACE,   // }
    SEMI,     // ;
    PLUS,
    MINUS,
    STAR,
    SLASH,
    DOT,      // .
    COMMA,    // ,
    EQUAL,    // =
    UNDEFINED,
    ENDFILE
}

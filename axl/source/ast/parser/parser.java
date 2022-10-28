package axl.source.ast.parser;

import axl.source.ast.AstClassDefinition;
import axl.source.ast.AstCompound;
import axl.source.ast.values.AstString;
import axl.source.lexer.Token;
import axl.source.lexer.TokenType;

import java.util.ArrayList;

import static java.lang.System.exit;
import static java.lang.System.out;

public class Parser {

    private static final String
    PACKAGE      = "package",
    CLASS         = "class",
    EXTENDS       = "extends",
    IMPLEMENTS    = "implements",
    VOID          = "void",
    INT           = "int",
    FLOAT         = "float",
    TRUE          = "true",
    FALSE         = "false"
    ;

    private final ArrayList<Token> tokens;
    private Token current;
    private int i = 0;

    private String _dir_ = "";

    public AstCompound AbstractSyntaxTree = new AstCompound();

    public Parser(ArrayList<Token> tokens)
    {
        this.tokens = tokens;
    }

    private void next() // Переход к следующему токену
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

    private void last() // Переход к прошлому токену
    {
        if(i >= 0)
        {
            current = tokens.get(--i);
        }
    }

    private void last(int count) // Переход к прошлому токену
    {
        for(int x = 0; x < count; x++)
        {
            last();
        }
    }

    private boolean is_reserved(String word){
        return switch (word) {
            case CLASS, VOID, INT, FLOAT, TRUE, FALSE -> true;
            default -> false;
        };
    }

    public AstCompound parse_file()
    {
        next();
        AstCompound file = new AstCompound();

        switch (current.type)
        {
            case WORD -> {
                if(current.value_string.equals(PACKAGE)) {
                    parse_dir();
                    break;
                }
                if(current.value_string.equals(CLASS))
                    file.body.add(parse_class());
            }
            default -> {
                exit(1);
                return null; //чтобы IDE не материла
            }
        }

        return file;
    }

    private void parse_dir()
    {
        next();

        StringBuilder dir_ = new StringBuilder();

        while (!current.is_endfile() && !current.is_semi())
        {
            if(!current.is_word())
            {
                exit(4);
                return; //чтобы IDE не материла
            }

            dir_.append(current.value_string);

            next();

            if(!current.is_dot() || !current.is_semi())
            {
                exit(4);
                return; //чтобы IDE не материла
            }
        }

        this._dir_ = dir_.toString();
        next();
    }

    private AstClassDefinition parse_class()
    {
        if(!current.is_word())
        {
            exit(2);
            return null; //чтобы IDE не материла
        }

        String name = current.value_string;
        next();

        if(!current.is_word())
        {
            exit(2);
            return null; //чтобы IDE не материла
        }

        AstClassDefinition ast_class = new AstClassDefinition(name, this._dir_, new AstCompound());

        while(!current.is_lbrace() && !current.is_endfile()) {
            if (current.type == TokenType.WORD) {
                next();
                if (current.value_string.equals(EXTENDS)) {
                    ast_class.extends_list = parse_extends();
                }
                if (current.value_string.equals(IMPLEMENTS)) {
                    ast_class.implements_list = parse_implements();
                }

                if(!current.is_lbrace())
                {
                    exit(9);
                    return null; //чтобы IDE не материла
                }
                exit(666666666);
                return null;
            }

            if(!current.is_lbrace())
            {
                exit(2);
                return null; //чтобы IDE не материла
            }
        }

        return null;
    }

    private ArrayList<AstString> parse_extends()
    {
        if (!current.is_word()) {
            exit(3);
            return null;
        }

        ArrayList<AstString> list = new ArrayList<>();

        while (!current.value_string.equals(IMPLEMENTS) && !current.is_lbrace() && !current.is_endfile())
        {
            if (!current.is_word()) {
                exit(3);
                return null;
            }

            list.add(new AstString(current.value_string));
            next();
            if(!current.is_comma()) { // ,
                return list;
            }
            next();
        }

        return list;
    }

    private ArrayList<AstString> parse_implements()
    {
        if (!current.is_word()) {
            exit(3);
            return null;
        }

        ArrayList<AstString> list = new ArrayList<>();

        while (!current.is_lbrace() && !current.is_endfile())
        {
            if (!current.is_word()) {
                exit(3);
                return null;
            }

            list.add(new AstString(current.value_string));
            next();
            if(!current.is_comma()) {
                return list;
            }
            next();
        }

        return list;
    }
}
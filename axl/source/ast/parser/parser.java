package axl.source.ast.parser;

import axl.source.ast.*;
import axl.source.ast.math.AstSum;
import axl.source.lexer.Token;
import axl.source.lexer.TokenType;

import java.util.ArrayList;

import static java.lang.System.exit;
import static java.lang.System.out;

public class Parser {

    private static final String
            PACKAGE = "package",
            CLASS = "class",
            EXTENDS = "extends",
            IMPLEMENTS = "implements",
            VOID = "void",
            INT = "int",
            FLOAT = "float",
            DOUBLE = "double",
            CHAR = "char",
            BYTE = "byte",
            LONG = "long",
            SHORT = "short",
            TRUE = "true",
            FALSE = "false",
            BOOLEAN = "boolean",
            STRING = "String",
            PUBLIC = "public",
            PRIVATE = "private",
            PROTECTED = "protected",
            DEFAULT = "default",
            SYNCHRONIZED = "synchronized",
            STATIC = "static";

    private final ArrayList<Token> tokens;
    private Token current;
    private int i = 0;

    private String _dir_ = "/";

    public AstCompound AbstractSyntaxTree = new AstCompound();

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    private void next() // + Переход к следующему токену
    {
        if (i < tokens.size()) {
            current = tokens.get(i++);
        } else {
            current = new Token(TokenType.ENDFILE);
        }
    }

    private void last() // + Переход к прошлому токену
    {
        if (i >= 0) {
            current = tokens.get(--i);
        }
    }

    private void last(int count) // + Переход к прошлому токену
    {
        for (int x = 0; x < count; x++) {
            last();
        }
    }

    private boolean is_reserved(String word) // +
    {
        return switch (word) {
            case CLASS, VOID, INT, FLOAT, TRUE, FALSE, DOUBLE, LONG, BYTE, CHAR, SHORT, BOOLEAN, STRING, STATIC, PUBLIC, PRIVATE, PROTECTED, DEFAULT, SYNCHRONIZED ->
                    true;
            default -> false;
        };
    }

    private boolean is_data(String word) // +
    {
        return switch (word) {
            case INT, FLOAT, SHORT, BYTE, DOUBLE, LONG, BOOLEAN, VOID, CHAR -> true;
            default -> false;
        };
    }

    private void exit_if_not_word() // *не знает английский
    {
        if(!current.is_word())
            exit(1415);
    }

    private void exit_if_reserved() // *не знает английский
    {
        if(is_reserved(current.value_string))
            exit(9265);
    }

    private void exit_if_endfile()
    {
        if (current.is_endfile())
            exit(2384);
    }

    public AstCompound parse_file() {
        next();
        AstCompound file = new AstCompound();

        switch (current.type) {
            case WORD -> {
                if (current.value_string.equals(PACKAGE)) {
                    parse_dir();
                }

                AstModifiers modifiers = parse_modifiers();
                file.body.add(parse_class(modifiers));
            }
            default -> {
                exit(1);
            }
        }

        return file;
    }

    private void parse_dir() // +
    {
        next();

        StringBuilder dir_ = new StringBuilder();

        while (!current.is_endfile() && !current.is_semi()) {
            exit_if_not_word();

            dir_.append(current.value_string).append("/");

            next();

            if (!(current.is_dot() || current.is_semi())) {
                exit(3);
            } else if (current.is_dot()) {
                next();
            } else {
                break;
            }
        }

        exit_if_endfile();

        this._dir_ = dir_.toString();
        next();
    }

    private AstModifiers parse_modifiers() // +-
    {
        AstModifiers modifiers = new AstModifiers();

        boolean modifier = false;
        while (current.is_access() || current.is_static() || current.is_final()) {
            if (current.is_final()) {
                if (!modifiers.is_final) {
                    modifiers.is_final = true;
                } else
                    exit(4);
            } else if (current.is_static()) {
                if (!modifiers.is_static) {
                    modifiers.is_static = true;
                } else
                    exit(5);
            } else if (current.is_access()) {
                if (!modifier) {
                    modifiers.modifier_access = current.get_access();
                    modifier = true;
                } else
                    exit(6);
            }

            next();
        }

        return modifiers;
    }

    private AstClassDefinition parse_class(AstModifiers modifiers) // -
    {
        exit_if_not_word();

        AstClassDefinition ast_class = new AstClassDefinition(null, this._dir_, modifiers, new AstCompound());
        next();

        exit_if_not_word();

        ast_class.name = current.value_string;

        if(is_reserved(ast_class.name))
            exit(19);

        next();


        while (!current.is_lbrace() && !current.is_endfile()) {
            if (current.is_word()) {
                if (current.value_string.equals(EXTENDS)) {
                    next();
                    ast_class.extends_list = parse_extends();
                }
                if (current.value_string.equals(IMPLEMENTS)) {
                    next();
                    ast_class.implements_list = parse_implements();
                }
            }

            if (!current.is_lbrace())
                exit(9);
        }

        if (!current.is_lbrace())
            exit(16);
        next(); // skip lbrace

        debug_class(ast_class);

//        boolean isVariable = false;
//        while (!current.is_rbrace() && !current.is_endfile() && !current.value_string.equals(CLASS)) {
//            next();
//            if (current.is_word()) {
//
//                for (int j = 0; j < tokens.size() - tokens.indexOf(current); j++) {
//                    if (tokens.get(i + j).is_lpar()) {
//                        isVariable = false;
//                        break;
//                    } else if (tokens.get(i + j).is_semi()) {
//                        isVariable = true;
//                        break;
//                    }
//
//                    if (j == tokens.size() - tokens.indexOf(current))
//                        exit(10);
//                }
//
//
//                if (isVariable) {
//                    ast_class.body.body.add(parse_variable());
//                } else {
//                    ast_class.body.body.add(parse_function_signature());
//                }
//
//
//            } else {
//                exit(11);
//            }
//        }

//        if (current.value_string.equals(CLASS)) {
//            parse_class(modifiers);
//        }
        while (!current.is_rbrace() && !current.is_endfile())
        {
            exit_if_not_word();
            AstModifiers modifiers_vf = parse_modifiers(); // vf = vars | functions

            exit_if_not_word();
            AstDataType type = new AstDataType(current.value_string);
            next();

            exit_if_not_word();
            exit_if_reserved();
            String name = current.value_string;
            next();

            if (current.is_semi())
            {
                next();
                ast_class.body.body.add(new AstVarDefinition(modifiers_vf, name, type));
                continue;
            }

            if (current.is_equal())
            {
                next();
                AstVarDefinition var = new AstVarDefinition(modifiers_vf, name, type);
                // parse body var definition
                ArrayList<Token> math = new ArrayList<>();
                while (!current.is_semi() && !current.is_endfile())
                    math.add(current);
                exit_if_endfile();

                var.value = parse_math(math);

                ast_class.body.body.add(var);
                continue;
            }

            if (current.is_lpar())
            {
                next();
                AstFunctionDefinition function = new AstFunctionDefinition(modifiers_vf, name, type);

                while (current.is_rpar() && !current.is_endfile())
                {
                    //parse args
                } exit_if_endfile();

                ast_class.body.body.add(function);
            }

        } exit_if_endfile();

        return ast_class;
    }

    private Ast parse_math(ArrayList<Token> tokens)
    {
        Ast value = new Ast();
        return value;
    }

    private ArrayList<String> parse_extends() // +
    {
        exit_if_not_word();
        ArrayList<String> list = new ArrayList<>();

        while (!current.is_lbrace() && !current.is_endfile()) {
            exit_if_not_word();

            list.add(current.value_string);
            next();
            if (!current.is_comma()) { // ,
                return list;
            }
            next();
        } exit_if_endfile();


        return list;
    }

    private ArrayList<String> parse_implements() // +
    {
        exit_if_not_word();
        ArrayList<String> list = new ArrayList<>();

        while (!current.is_lbrace() && !current.is_endfile()) {
            exit_if_not_word();

            list.add(current.value_string);
            next();
            if (!current.is_comma()) {
                return list;
            }
            next();
        } exit_if_endfile();

        return list;
    }

    private AstVarDefinition parse_variable() {
        return null;
    }

    private AstFunctionCall parse_function_signature() {
        AstFunctionCall function_signature = new AstFunctionCall(new ArrayList<String>(), current.value_string, new AstCompound());
        return null;
    }


    private void debug_class(AstClassDefinition ast_class)
    {
        out.println("name: " + ast_class.name);
        out.println("access: " + ast_class.modifiers.modifier_access);
        out.println("static: " + ast_class.modifiers.is_static);
        out.println("final: " + ast_class.modifiers.is_final);
        out.println("dir: " + _dir_);
        out.println("Extends:");
        for(String extend:  ast_class.extends_list)
            out.println("- "+extend);
        out.println("Implements:");
        for(String implement:  ast_class.implements_list)
            out.println("- "+implement);
    }
}
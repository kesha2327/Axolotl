package axl.source.ast.parser;

import axl.source.ast.*;
import axl.source.ast.values.AstString;
import axl.source.lexer.Token;
import axl.source.lexer.TokenType;

import java.util.ArrayList;

import static java.lang.System.exit;
import static java.lang.System.out;

public class Parser {

    private static final String
    PACKAGE       = "package",
    CLASS         = "class",
    EXTENDS       = "extends",
    IMPLEMENTS    = "implements",
    VOID          = "void",
    INT           = "int",
    FLOAT         = "float",
    DOUBLE        = "double",
    CHAR          = "char",
    BYTE          = "byte",
    LONG          = "long",
    SHORT         = "short",
    TRUE          = "true",
    FALSE         = "false",
    BOOLEAN       = "boolean",
    STRING        = "String",
    PUBLIC        = "public",
    PRIVATE       = "private",
    PROTECTED     = "protected",
    DEFAULT       = "default",
    SYNCHRONIZED  = "synchronized",
    STATIC        = "static"
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
            case CLASS, VOID, INT, FLOAT, TRUE, FALSE, DOUBLE, LONG, BYTE, CHAR, SHORT, BOOLEAN, STRING, STATIC, PUBLIC, PRIVATE, PROTECTED, DEFAULT, SYNCHRONIZED -> true;
            default -> false;
        };
    }

    private boolean is_data(String word)
    {
        return switch (word) {
            case INT, FLOAT, SHORT, BYTE, DOUBLE, LONG, BOOLEAN, VOID, CHAR -> true;
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
                if(current.is_access() || current.is_static() || current.is_final())
                {
                    int temp_i = i;

                    do next();
                    while ((current.is_access() || current.is_static() || current.is_final()) && !current.is_endfile());

                    if(current.is_endfile() || !current.value_string.equals(CLASS))
                    {
                        exit(20);
                        return null;
                    }
                    last(i-temp_i-1);
                    i = temp_i-1;
                    current = tokens.get(i++);
                    file.body.add(parse_class());
                }

                if(current.value_string.equals(CLASS))
                    file.body.add(parse_class());
            }
            default -> {
                exit(3);
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
                exit(5);
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
            exit(6);
            return null; //чтобы IDE не материла
        }
        AstClassDefinition ast_class = new AstClassDefinition(null, this._dir_, new AstCompound());

        { // Для памяти
            boolean modifier = false;
            while (current.is_access() || current.is_static() || current.is_final()) {
                if (current.is_final()) {
                    if (!ast_class.is_final) {
                        ast_class.is_final = true;
                    } else {
                        exit(17);
                        return null;
                    }
                } else if (current.is_static()) {
                    if (!ast_class.is_static) {
                        ast_class.is_static = true;
                    } else {
                        exit(18);
                        return null;
                    }
                } else if (current.is_access()) {
                    if (!modifier) {
                        ast_class.modifier_access = current.get_access();
                        modifier = true;
                    } else {
                        exit(19);
                        return null;
                    }
                }

                next();
            }
        }
        next(); // skip "class"

        if(!current.is_word())
        {
            exit(21);
            return null; //чтобы IDE не материла
        }

        ast_class.name = current.value_string;

        next();

        if(current.is_word())
        {
            exit(7);
            return null; //чтобы IDE не материла
        }
        out.println("access: "+ast_class.modifier_access);
        out.println("static: "+ast_class.is_static);
        out.println("final: "+ast_class.is_final);
        exit(666);
        while(!current.is_lbrace() && !current.is_endfile()) {
            next();
            if (current.is_word()) {
                if (current.value_string.equals(EXTENDS)) {
                    ast_class.extends_list = parse_extends();
                }
                if (current.value_string.equals(IMPLEMENTS)) {
                    ast_class.implements_list = parse_implements();
                }

                if(!current.is_lbrace())
                {
                    exit(8);
                    return null; //чтобы IDE не материла
                }
                exit(9);
                return null;
            }

            if(!current.is_lbrace())
            {
                exit(10);
                return null; //чтобы IDE не материла
            }
        }

        if(current.is_lbrace()){
            boolean isVariable = false;
            while(!current.is_rbrace() && !current.is_endfile() && !current.value_string.equals(CLASS)){
                next();
                if(current.is_word()){

                    for(int j = 0; j < tokens.size() - tokens.indexOf(current); j++){
                        if(tokens.get(i + j).is_lpar()){
                            isVariable = false;
                            break;
                        }else if(tokens.get(i + j).is_semi()){
                            isVariable = true;
                            break;
                        }

                        if(j == tokens.size() - tokens.indexOf(current)){
                            exit(16);
                            return null;
                        }
                    }


                    if(isVariable){
                        ast_class.body.body.add(parse_variable());
                    }else{
                        ast_class.body.body.add(parse_function_signature());
                    }


                }else{
                    exit(15);
                }
            }

            if(current.value_string.equals(CLASS)){
                parse_class();
            }
        }

        return null;
    }

    private ArrayList<AstString> parse_extends()
    {
//        if (!current.is_word()) { //этот метод и так вызывается только если токен это слово
//            exit(11);
//            return null;
//        }

        ArrayList<AstString> list = new ArrayList<>();

        while (!current.is_lbrace() && !current.is_endfile())
        {
            if (!current.is_word()) { //same
                exit(12);
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
//        if (!current.is_word()) { //same
//            exit(13);
//            return null;
//        }

        ArrayList<AstString> list = new ArrayList<>();

        while (!current.is_lbrace() && !current.is_endfile())
        {
            if (!current.is_word()) {
                exit(14);
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

    private AstVarDefinition parse_variable(){

        AstVarDefinition ast_var = new AstVarDefinition(new ArrayList<String>(), current.value_string, DataType.BOOL, new AstCompound());

//        while(!current.is_semi()){
//            if(current.isModifier()){
//                ast_var.modifiers.add(current.value_string);
//            }
//
//            if(current.is_data()){
////                ast_var.type = current.type; //тип данных
//                ast_var.name = tokens.get(++i).value_string; //имя переменной
//            }
//
//
//            next();
//        }

        return null;

    }

    private AstFunctionCall parse_function_signature(){
        AstFunctionCall function_signature = new AstFunctionCall(new ArrayList<String>(), current.value_string, new AstCompound());
        return null;
    }
}
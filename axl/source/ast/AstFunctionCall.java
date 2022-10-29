package axl.source.ast;

import axl.source.ast.values.AstString;

import java.util.ArrayList;

public class AstFunctionCall extends Ast{
    public ArrayList<String> modifiers = new ArrayList<>();
    public String name;
    public AstCompound arguments;

    public AstFunctionCall(ArrayList<String> modifiers, String name, AstCompound arguments)
    {
        this.modifiers = modifiers;
        this.name = name;
        this.arguments = arguments;
    }

    boolean isFunctionCall(){
        return true;
    }
}
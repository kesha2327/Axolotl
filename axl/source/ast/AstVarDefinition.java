package axl.source.ast;

import axl.source.ast.values.AstString;

import java.util.ArrayList;

public class AstVarDefinition extends Ast{

    public ArrayList<String> modifiers = new ArrayList<>();
    public String name;
    public DataType type;
    public AstCompound value;

    public AstVarDefinition(ArrayList<String> modifiers, String name, DataType type, AstCompound value)
    {
        this.modifiers = modifiers;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    boolean isVarDefinition(){
        return true;
    }
}

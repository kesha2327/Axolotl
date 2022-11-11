package axl.source.ast;

import axl.source.ast.values.AstString;

import java.util.ArrayList;

public class AstClassDefinition extends Ast{
    public String dir;

    public AstModifiers modifiers;

    public String name;
    public AstCompound body;
    public ArrayList<String> extends_list = new ArrayList<>();
    public ArrayList<String> implements_list = new ArrayList<>();

    public AstClassDefinition(String name, String dir, AstModifiers modifiers, AstCompound body)
    {
        this.name = name;
        this.dir = dir;
        this.modifiers = modifiers;
        this.body = body;
    }

    boolean isClassDefinition(){
        return true;
    }
}

package axl.source.ast;

import axl.source.ast.values.AstString;

import java.util.ArrayList;

public class AstClassDefinition extends Ast{
    public String dir;
    public ArrayList<String> modifiers = new ArrayList<>();
    public String name;
    public AstCompound body;
    public ArrayList<AstString> extends_list = new ArrayList<>();
    public ArrayList<AstString> implements_list = new ArrayList<>();

    public AstClassDefinition(ArrayList<String> modifiers, String name, String dir, AstCompound body)
    {
        this.modifiers = modifiers;
        this.name = name;
        this.dir = dir;
        this.body = body;
    }

    boolean isClassDefinition(){
        return true;
    }
}

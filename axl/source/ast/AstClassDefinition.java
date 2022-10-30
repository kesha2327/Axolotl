package axl.source.ast;

import axl.source.ast.values.AstString;

import java.util.ArrayList;

public class AstClassDefinition extends Ast{
    public String dir;

    public byte modifier_access;
    /*
    0 - default
    1 - public
    2 - private
    3 - protected
     */
    public boolean
            is_static,
            is_final;

    public String name;
    public AstCompound body;
    public ArrayList<AstString> extends_list = new ArrayList<>();
    public ArrayList<AstString> implements_list = new ArrayList<>();

    public AstClassDefinition(String name, String dir, AstCompound body)
    {
        this.name = name;
        this.dir = dir;
        this.body = body;
    }

    boolean isClassDefinition(){
        return true;
    }
}

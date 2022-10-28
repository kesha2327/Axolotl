package axl.source.ast.values;

import axl.source.ast.Ast;

public class AstString extends Ast {
    public String value;

    public AstString(String value)
    {
        this.value = value;
    }

    boolean isValue(){
        return true;
    }
}

package axl.source.ast.values;

import axl.source.ast.Ast;

public class AstInteger extends Ast {
    public int value;

    AstInteger(int value)
    {
        this.value = value;
    }

    boolean isValue(){
        return true;
    }
}

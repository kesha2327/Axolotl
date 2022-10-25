package axl.source.ast.values;

import axl.source.ast.Ast;

public class AstFloat extends Ast {
    public float value;

    AstFloat(float value)
    {
        this.value = value;
    }

    boolean isValue(){
        return true;
    }
}

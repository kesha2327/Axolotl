package axl.source.ast.math;

import axl.source.ast.Ast;

// класс чисто для наследования
public class AstMath extends Ast{
    public Ast left;
    public Ast right;

    AstMath(Ast left, Ast right)
    {
        this.left = left;
        this.right = right;
    }

    boolean isMath(){
        return true;
    }
}

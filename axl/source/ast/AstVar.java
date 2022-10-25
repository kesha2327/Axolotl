package axl.source.ast;

public class AstVar extends Ast{
    public String name;

    AstVar(String name)
    {
        this.name = name;
    }

    boolean isVar(){
        return true;
    }
}
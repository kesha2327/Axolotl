package axl.source.ast;

public class AstFunctionCall extends Ast{
    public String name;
    public AstCompound arguments;

    AstFunctionCall(String name, AstCompound arguments)
    {
        this.name = name;
        this.arguments = arguments;
    }

    boolean isFunctionCall(){
        return true;
    }
}
package axl.source.ast;

public class AstFunctionDefinition extends Ast{
    public String name;
    public AstModifiers modifiers;
    public AstDataType type;
    public AstCompound BODY;

    public AstFunctionDefinition(AstModifiers modifiers, String name, AstDataType type)
    {
        this.modifiers = modifiers;
        this.name = name;
        this.type = type;
    }

    boolean isFunctionDefinition(){
        return true;
    }
}

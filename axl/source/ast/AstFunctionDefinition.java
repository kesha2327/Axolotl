package axl.source.ast;

public class AstFunctionDefinition {
    public String name;
    public DataType type;
    public AstCompound BODY;

    AstFunctionDefinition(String name)
    {
        this.name = name;
    }

    boolean isFunctionDefinition(){
        return true;
    }
}

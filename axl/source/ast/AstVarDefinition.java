package axl.source.ast;

public class AstVarDefinition extends Ast{
    public String name;
    public DataType type;
    public AstCompound value;

    AstVarDefinition(String name, DataType type, AstCompound value)
    {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    boolean isVarDefinition(){
        return true;
    }
}

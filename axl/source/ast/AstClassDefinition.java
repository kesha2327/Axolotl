package axl.source.ast;

public class AstClassDefinition {
    public String name;
    public AstCompound body;

    AstClassDefinition(String name, AstCompound body)
    {
        this.name = name;
        this.body = body;
    }

    boolean isClassDefinition(){
        return true;
    }
}

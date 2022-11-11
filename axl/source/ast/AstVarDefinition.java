package axl.source.ast;

import static java.lang.System.exit;

public class AstVarDefinition extends Ast{

    public AstModifiers modifiers;
    public String name;
    public AstDataType type;
    public Ast value = null;

    public AstVarDefinition(AstModifiers modifiers, String name, AstDataType type)
    {
        if(type.type == DataType.VOID)
            exit(3593);

        this.modifiers = modifiers;
        this.name = name;
        this.type = type;
    }

    boolean isVarDefinition(){
        return true;
    }
}

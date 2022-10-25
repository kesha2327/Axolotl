package axl.source.ast;

// класс чисто для наследования
public class Ast {
    boolean isCompound(){
        return false;
    }

    boolean isValue(){
        return false;
    }

    boolean isMath(){
        return false;
    }

    boolean isVarDefinition(){
        return false;
    }

    boolean isVar(){
        return false;
    }

    boolean isFunctionDefinition(){
        return false;
    }

    boolean isFunctionCall(){
        return false;
    }

    boolean isClassDefinition(){
        return false;
    }
}

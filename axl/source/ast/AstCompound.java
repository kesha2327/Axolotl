package axl.source.ast;

import java.util.ArrayList;

public class AstCompound {
    public ArrayList<Ast> body = new ArrayList<>();

    boolean isCompound(){
        return true;
    }
}

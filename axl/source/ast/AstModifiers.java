package axl.source.ast;

public class AstModifiers extends Ast{
    public byte modifier_access;
    /*
    0 - default
    1 - public
    2 - private
    3 - protected
     */

    public boolean
            is_static,
            is_final;
}

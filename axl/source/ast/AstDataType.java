package axl.source.ast;

import axl.source.lexer.TokenType;

public class AstDataType {
    public DataType type;
    public String name = "";

    public AstDataType(DataType type)
    {
        this.type = type;
    }

    public AstDataType(String type_str)
    {
        this.type = switch (type_str)
        {
            case "float" -> DataType.FLOAT;
            case "int" -> DataType.INT;
            case "short" -> DataType.SHORT;
            case "byte" -> DataType.BYTE;
            case "double" -> DataType.DOUBLE;
            case "long" -> DataType.LONG;
            case "boolean" -> DataType.BOOLEAN;
            case "void" -> DataType.VOID;
            case "char" -> DataType.CHAR;
            default -> DataType.OBJECT;
        };
    }
}

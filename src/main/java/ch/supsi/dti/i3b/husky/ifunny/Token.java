package ch.supsi.dti.i3b.husky.ifunny;

import java.math.BigDecimal;

public class Token {

    public enum Type{
        NIL, FALSE, TRUE, IF, IFNOT, THEN, ELSE, FI, WHILE, WHILENOT, DO, OD,
        PRINT, PRINTLN, ID, NUM, STRING, SEMICOLON, COMMA, ARROW, OPN_RND_BRKT,
        CLS_RND_BRKT, OPN_CRLY_BRKT, CLS_CRLY_BRKT, NOT, MULT, DIV, MOD,
        SUM, SUB, MIN, MINEQ, MAJ, MAJEQ, EQ, NOTEQ, AND, OR, ASSIGNM, ASSIGNMSUM,
        ASSIGNMSUB, ASSIGNMULT, ASSIGNMDIV, ASSIGNMOD, EOS, UNKNOWN
    }

    private Type type;
    private BigDecimal num;
    private String str;

    private Token(Type type, BigDecimal num, String str){
        this.type = type;
        this.num = num;
        this.str = str;
    }
    Token(BigDecimal num){
        this(Type.NUM, num, null);
    }
    Token(String str){
        this(Type.STRING, null, str);
    }
    Token(Type type, String str){
        this(type, null, str);
    }
    Token(Type type){
        this(type, null, null);
    }


    public Type type(){
        return type;
    }
    BigDecimal getNum(){
        return num;
    }
    String getStr(){
        return str;
    }

    @Override
    public String toString() {
        if (type == Type.NUM) {
            return String.format("[%s]: %e", type, num);
        }
        return String.format("[%s]: %s", type, str);
    }
}

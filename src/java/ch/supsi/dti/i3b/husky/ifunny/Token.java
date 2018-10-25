package ch.supsi.dti.i3b.husky.ifunny;

import java.math.BigDecimal;

public class Token {

    public enum Type{
        NIL, FALSE, TRUE, IF, IFNOT, THEN, ELSE, FI, WHILE, WHILENOT, DO, OD,
        PRINT, PRINTLN, ID, NUM, STRING, SEMICOLON, COMMA, ARROW, OPNRNBRACKET,
        CLSRNBRACKET, OPNCRLYBRACKET, CLSCRLYBRACKET, NOT, MULT, DIV, MOD,
        SUM, SUB, MIN, MINEQ, MAJ, MAJEQ, EQ, NOTEQ, AND, OR, ASSIGNM, ASSIGNMSUM,
        ASSIGNMSUB, ASSIGNMULT, ASSIGNMDIV, ASSIGNMOD, EOS, UNKNOW
    }

    private Type type;
    private int num;
    private String str;

    private Token(Type type, int num, String str){
        this.type = type;
        this.num = num;
        this.str = str;
    }
    Token(int num){
        this(Type.NUM, num, null);
    }
    Token(String str){
        this(Type.STRING, -1, str);
    }
    Token(Type type, String str){
        this(type, -1, str);
    }
    Token(Type type){
        this(type, -1, null);
    }


    public Type type(){
        return type;
    }
    public int getNum(){
        return num;
    }
    public String getStr(){
        return str;
    }



}

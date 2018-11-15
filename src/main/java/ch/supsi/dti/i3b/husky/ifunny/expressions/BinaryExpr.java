package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Token;
import ch.supsi.dti.i3b.husky.ifunny.Val;

public class BinaryExpr extends Expr {

    private Expr firstExpr;
    private Token.Type binaryType;
    private Expr lastExpr;

    public BinaryExpr(Expr firstExpr, Token.Type binaryType, Expr lastExpr) {
        this.firstExpr = firstExpr;
        this. binaryType = binaryType;
        this.lastExpr = lastExpr;
    }

    @Override
    public Val eval(Env env) {
        return null;
    }
}

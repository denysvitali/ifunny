package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Token;
import ch.supsi.dti.i3b.husky.ifunny.Val;

public class UnaryExpr extends Expr {

    private Token.Type unaryType;
    private Expr expr;

    public UnaryExpr(Token.Type unaryType, Expr expr) {
        this.unaryType = unaryType;
        this.expr = expr;
    }

    @Override
    public Val eval(Env env) {
        return null;
    }
}

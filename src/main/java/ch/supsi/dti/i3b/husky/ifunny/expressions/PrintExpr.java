package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.ExprList;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

public class PrintExpr extends Expr {

    private ExprList args;

    public PrintExpr(ExprList args) {
        this.args = args;
    }

    @Override
    public Val eval(Env env) {
        return null;
    }
}

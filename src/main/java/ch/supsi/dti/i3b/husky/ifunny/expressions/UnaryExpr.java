package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Token;
import ch.supsi.dti.i3b.husky.ifunny.values.NumVal;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;

public class UnaryExpr extends Expr {

    private Token.Type unaryType;
    private Expr expr;

    public UnaryExpr(Token.Type unaryType, Expr expr) {
        this.unaryType = unaryType;
        this.expr = expr;
    }

    @Override
    public Val eval(Env env) {
        switch(unaryType){
            case SUB:
                return new NumVal(0).sub(expr.eval(env));
            case NOT:
                return expr.eval(env).not();
            case SUM:
                return expr.eval(env);
        }

        return Nil;
    }
}

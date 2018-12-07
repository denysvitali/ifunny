package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.values.NilVal;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

import java.util.ArrayList;

import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;

public class SequenceExpr extends Expr{

    private ArrayList<Expr> expressions;

    public SequenceExpr(ArrayList<Expr> expressions){
        this.expressions = expressions;
    }

    @Override
    public Val eval(Env env) {
        /*
            Design Choice, either:
            - return Nil (multiple expressions return Nil), or
            - return last's function value

            I'm choosing the first option here. -D
         */

        expressions.forEach(expr -> expr.eval(env));
        return Nil;
    }

    public ArrayList<Expr> getExprs() {
        return expressions;
    }
}

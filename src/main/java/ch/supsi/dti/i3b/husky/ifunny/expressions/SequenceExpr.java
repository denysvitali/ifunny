package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
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

            As per Slide 32/51 of "Course Notes"
         */
        return expressions.stream()
                .map(expr -> expr.eval(env))
                .reduce((a,b) -> b)
                .orElse(Nil);
    }

    public ArrayList<Expr> getExprs() {
        return expressions;
    }
}

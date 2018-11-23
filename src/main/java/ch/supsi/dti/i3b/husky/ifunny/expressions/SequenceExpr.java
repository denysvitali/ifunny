package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

import java.util.ArrayList;

public class SequenceExpr extends Expr{

    private ArrayList<Expr> expressions;

    public SequenceExpr(ArrayList<Expr> expressions){
        this.expressions = expressions;
    }

    @Override
    public Val eval(Env env) {
        return null;
    }

    public ArrayList<Expr> getExprs() {
        return expressions;
    }
}

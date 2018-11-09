package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Val;

import java.util.ArrayList;

public class SequenceExpr extends Expr{

    private ArrayList<Expr> listAssignment;

    public SequenceExpr(ArrayList<Expr> listAssignment){
        this.listAssignment = listAssignment;
    }

    @Override
    Val eval(Env env) {
        return null;
    }

}

package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Token;
import ch.supsi.dti.i3b.husky.ifunny.Val;

public class AssignmExpr extends Expr {

    private String idVal;
    private Token.Type assignmType;
    private Expr additionalExpr;

    public AssignmExpr(String idVal, Token.Type assignmType, Expr additionalExpr) {
        this.idVal = idVal;
        this.assignmType = assignmType;
        this.additionalExpr = additionalExpr;
    }

    @Override
    public Val eval(Env env) {
        return null;
    }
}

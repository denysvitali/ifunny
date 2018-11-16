package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Val;

public class GetVarExpr extends Expr {

    private String idVal;

    public GetVarExpr(String idVal) {
        this.idVal = idVal;
    }

    @Override
    public Val eval(Env env) {
        return null;
    }
}

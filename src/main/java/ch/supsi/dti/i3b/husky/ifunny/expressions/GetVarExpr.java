package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

public class GetVarExpr extends Expr {

    private String idVal;

    public GetVarExpr(String idVal) {
        this.idVal = idVal;
    }

    public String getIdVal() {
        return idVal;
    }

    @Override
    public Val eval(Env env) {
        return env.getVal(idVal);
    }
}

package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Token;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

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
    public Val eval(Env env){
        Val left = env.getVal(idVal);
        Val right = additionalExpr.eval(env);

        switch(assignmType){
            case MAJ:
                return left.gt(right);
            case MIN:
                return left.lt(right);
            case MINEQ:
                return left.lteq(right);
            case MAJEQ:
                return left.gteq(right);
            case MOD:
                return left.mod(right);

            // Assignments

            case ASSIGNM:
                return env.setVal(idVal, right);

            case ASSIGNMDIV:
                return env.setVal(idVal,
                        left.div(right));
            case ASSIGNMULT:
                return env.setVal(idVal,
                        left.mult(right));
            case ASSIGNMSUM:
                return env.setVal(idVal,
                        left.sum(right));
            case ASSIGNMSUB:
                return env.setVal(idVal,
                        left.sub(right));
            case ASSIGNMOD:
                return env.setVal(idVal,
                        left.mod(right));
        }
        throw new RuntimeException("Not implemented");
    }

    public String getIdVal() {
        return idVal;
    }

    public Token.Type getAssignmType() {
        return assignmType;
    }

    public Expr getAdditionalExpr() {
        return additionalExpr;
    }
}

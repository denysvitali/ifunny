package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Token;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;

public class BinaryExpr extends Expr {

    private Expr firstExpr;
    private Token.Type binaryType;
    private Expr lastExpr;

    public BinaryExpr(Expr firstExpr, Token.Type binaryType, Expr lastExpr) {
        this.firstExpr = firstExpr;
        this.binaryType = binaryType;
        this.lastExpr = lastExpr;
    }

    public Expr getFirstExpr() {
        return firstExpr;
    }

    public Token.Type getBinaryType() {
        return binaryType;
    }

    public Expr getLastExpr() {
        return lastExpr;
    }

    @Override
    public Val eval(Env env) {
        Val lval = firstExpr.eval(env);
        Val rval = lastExpr.eval(env);

        switch(binaryType){
            case EQ:
                return lval.eq(rval);
            case SUB:
                return lval.sub(rval);
            case SUM:
                return lval.sum(rval);
            case DIV:
                return lval.div(rval);
            case MULT:
                return lval.mult(rval);
			case MAJ:
                return lval.gt(rval);
            case MIN:
                return lval.lt(rval);
            case MOD:
                return lval.mod(rval);
            case MAJEQ:
                return lval.gteq(rval);
            case MINEQ:
                return lval.lteq(rval);
            case OR:
                return lval.or(rval);
            case AND:
                return lval.and(rval);
        }

        return Nil;
    }
}

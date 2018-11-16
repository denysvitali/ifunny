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

    @Override
    public Val eval(Env env) {
        Val lval = firstExpr.eval(env);
        Val rval = lastExpr.eval(env);

        switch(binaryType){
            case SUB:
                return lval.sub(rval);
            case SUM:
                return lval.sum(rval);
            case DIV:
                return lval.div(rval);
            case MULT:
                return lval.mult(rval);
			case MAJ:
                return lval.maj(rval);
            case MIN:
                return lval.min(rval);
            case MAJEQ:
                return lval.majeq(rval);
            case MINEQ:
                return lval.mineq(rval);
            case OR:
                return lval.or(rval);
            case AND:
                return lval.and(rval);
        }

        return Nil;
    }
}

package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Val;

public class NotExpr extends Expr {
	private Expr origExpr;

	public NotExpr(Expr origExpr){
		this.origExpr = origExpr;
	}

	@Override
	Val eval(Env env) {
		if(origExpr.eval(env).getValue().equals(true)){
			return new Val(false);
		}
		return new Val(true);
	}
}

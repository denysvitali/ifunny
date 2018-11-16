package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;
import ch.supsi.dti.i3b.husky.ifunny.values.BoolVal;

public class NotExpr extends Expr {
	private Expr origExpr;

	public NotExpr(Expr origExpr){
		this.origExpr = origExpr;
	}

	@Override
	public Val eval(Env env) {
		if(origExpr.eval(env).bool()){
			return new BoolVal(false);
		}
		return new BoolVal(true);
	}
}

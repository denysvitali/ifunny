package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Val;

public class FunExpr extends Expr {
	private Expr body;

	public FunExpr(Expr body){
		this.body = body;
	}

	@Override
	Val eval(Env env) {
		return null;
	}
}

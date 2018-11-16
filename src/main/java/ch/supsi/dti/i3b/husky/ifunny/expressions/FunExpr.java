package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Scope;
import ch.supsi.dti.i3b.husky.ifunny.Val;

import java.util.ArrayList;

public class FunExpr extends Expr {
	private Scope scope;
	private Expr body;

	public FunExpr(ArrayList<String> params, ArrayList<String> locals, Expr body) {
		this.scope = new Scope(null, params, locals);
		this.body = body;
	}

	@Override
	public Val eval(Env env) {
		return this.body.eval(env);
	}
}

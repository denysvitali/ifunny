package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Scope;
import ch.supsi.dti.i3b.husky.ifunny.Val;
import ch.supsi.dti.i3b.husky.ifunny.values.ClosureVal;

import java.util.ArrayList;
import java.util.List;

public class FunExpr extends Expr {
	private Scope scope;
	private List<String> params;
	private List<String> locals;
	private Expr body;

	public FunExpr(List<String> params, List<String> locals, Expr body) {
		this.params = params;
		this.locals = locals;
		this.body = body;
	}

	public List<String> params() {
		return params;
	}

	public List<String> locals() {
		return locals;
	}

	@Override
	public Val eval(Env env) {
		return new ClosureVal(env, this);
	}
}

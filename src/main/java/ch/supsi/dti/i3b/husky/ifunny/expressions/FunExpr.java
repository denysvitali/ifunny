package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;
import ch.supsi.dti.i3b.husky.ifunny.values.ClosureVal;

import java.util.List;

import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;

public class FunExpr extends Expr {
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

	public Expr body(){
		return body;
	}

	@Override
	public Val eval(Env env) {
		return new ClosureVal(env, this);
	}
}

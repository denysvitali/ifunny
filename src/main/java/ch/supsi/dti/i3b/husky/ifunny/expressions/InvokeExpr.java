package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Val;

import java.util.List;

public class InvokeExpr extends Expr {
	private Expr expr;
	private List<Val> args;


	public InvokeExpr(Expr expr, List<Val> args){
		this.expr = expr;
		this.args = args;
	}

	@Override
	public Val eval(Env env) {
		return expr.eval(env).checkClosure().apply(args);
	}
}

package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.ExprList;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

public class InvokeExpr extends Expr {
	private Expr expr;
	private ExprList args;


	public InvokeExpr(Expr expr, ExprList args){
		this.expr = expr;
		this.args = args;
	}

	public Expr getExpr() {
		return expr;
	}

	public ExprList getArgs() {
		return args;
	}

	@Override
	public Val eval(Env env) {
		return expr.eval(env).checkClosure().apply(args.eval(env));
	}
}

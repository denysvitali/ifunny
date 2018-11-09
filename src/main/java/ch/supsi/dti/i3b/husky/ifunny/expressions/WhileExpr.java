package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Val;

public class WhileExpr extends Expr {
	private Expr condition;
	private Expr body;

	public WhileExpr(Expr condition, Expr body){
		this.condition = condition;
		this.body = body;
	}

	@Override
	public Val eval(Env env) {
		while(this.condition.eval(env).getValue().equals(true)){
			this.body.eval(env);
		}
		return null;
	}
}

package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

public class WhileExpr extends Expr {
	private Expr condition;
	private Expr body;

	public WhileExpr(Expr condition, Expr body){
		this.condition = condition;
		this.body = body;
	}

	public Expr getCondition() {
		return condition;
	}

	public Expr getBody() {
		return body;
	}

	private Val evalCond(Env env){
		return this.condition.eval(env);
	}

	@Override
	public Val eval(Env env) {
		while(evalCond(env).bool()){
			this.body.eval(env);
		}
		return null;
	}
}

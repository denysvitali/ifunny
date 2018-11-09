package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Scope;
import ch.supsi.dti.i3b.husky.ifunny.Val;

public class IfExpr extends Expr {
	private Scope scope;
	private Expr ifEvaluation;
	private Expr ifBody;
	private Expr elseBody;

	public IfExpr(Expr eval, Expr body, Expr elseBody){
		this.ifEvaluation = eval;
		this.ifBody = body;
		this.elseBody = elseBody;
	}

	@Override
	public Scope getScope() {
		return scope;
	}

	@Override
	public Val eval(Env env) {
		if(this.ifEvaluation.eval(env).getValue().equals(true)){
			return this.ifBody.eval(env);
		}
		return this.elseBody.eval(env);
	}
}

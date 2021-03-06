package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.exceptions.FunnyRuntimeException;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

public class IfExpr extends Expr {
	private Expr ifEvaluation;
	private Expr ifBody;
	private Expr elseBody;

	public IfExpr(Expr eval, Expr body, Expr elseBody){
		this.ifEvaluation = eval;
		this.ifBody = body;
		this.elseBody = elseBody;
	}

	public Expr getIfEvaluation() {
		return ifEvaluation;
	}

	public Expr getIfBody() {
		return ifBody;
	}

	public Expr getElseBody() {
		return elseBody;
	}

	@Override
	public Val eval(Env env) {

		Val ifEval = ifEvaluation.eval(env);

		if(ifEval.isClosure()){
			ifEval = ifEval.checkClosure().eval(env);
		}

		if(ifEvaluation.eval(env).bool()){
			return ifBody.eval(env);
		}
		return elseBody.eval(env);
	}

}

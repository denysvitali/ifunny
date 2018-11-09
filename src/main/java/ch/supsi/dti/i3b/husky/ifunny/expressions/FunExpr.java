package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Scope;
import ch.supsi.dti.i3b.husky.ifunny.Val;

import java.util.ArrayList;
import java.util.List;

public class FunExpr extends Expr {
	private Scope scope = new Scope();
	private ArrayList<String> params;
	private ArrayList<String> locals;
	private Expr body;

	public FunExpr(ArrayList<String> params, ArrayList<String> locals, Expr body) {
		this.params = params;
		this.locals = locals;
		this.body = body;

		for(String v : params){
			scope.addVar(v);
		}

		for(String v : locals){
			scope.addVar(v);
		}
	}

	@Override
	public Scope getScope() {
		return scope;
	}

	@Override
	public Val eval(Env env) {
		return this.body.eval(env);
	}
}

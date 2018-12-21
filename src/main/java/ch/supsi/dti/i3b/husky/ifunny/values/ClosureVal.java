package ch.supsi.dti.i3b.husky.ifunny.values;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.Frame;
import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;

import java.util.List;

import static ch.supsi.dti.i3b.husky.ifunny.values.BoolVal.False;
import static ch.supsi.dti.i3b.husky.ifunny.values.BoolVal.True;

public class ClosureVal extends Val {
	private FunExpr expr;
	private Env env;

	public ClosureVal(Env env, FunExpr funExpr) {
		expr = funExpr;
		this.env = env;
	}

	public ClosureVal apply(List<Val> argVals){
		this.env = new Env(env,
				new Frame(
						expr.params(),
						expr.locals(),
						argVals
				));
		return this;
	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public Val eval(Env env){
		return expr
				.body()
				.eval(this.env);
	}

	@Override
	public boolean isClosure() {
		return true;
	}

	@Override
	public ClosureVal checkClosure() {
		return this;
	}

	@Override
	public Val eq(Val rval) {
		return this == rval ? True : False;
	}
}

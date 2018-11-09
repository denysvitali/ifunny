package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.Expr;

public class Val extends Expr {
	private Object value = null;

	public Val(Object o) {
		this.value = o;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public Scope getScope() {
		return null;
	}

	@Override
	public Val eval(Env env) {
		return this;
	}
}

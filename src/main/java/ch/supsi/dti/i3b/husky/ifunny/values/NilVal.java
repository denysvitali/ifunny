package ch.supsi.dti.i3b.husky.ifunny.values;

import static ch.supsi.dti.i3b.husky.ifunny.values.BoolVal.False;
import static ch.supsi.dti.i3b.husky.ifunny.values.BoolVal.True;

public class NilVal extends Val {
	public static final NilVal Nil = new NilVal();

	@Override
	public Val eq(Val rval) {
		return rval.isNil() ? True : False;
	}

	@Override
	public Val sub(Val rval) {
		return Nil;
	}

	@Override
	public Val sum(Val rval) {
		return Nil;
	}

	@Override
	public Val div(Val rval) {
		return Nil;
	}

	@Override
	public Val mult(Val rval) {
		return Nil;
	}

	@Override
	public Val gt(Val rval) {
		return Nil;
	}

	@Override
	public Val lt(Val rval) {
		return Nil;
	}

	@Override
	public Val gteq(Val rval) {
		return Nil;
	}

	@Override
	public Val lteq(Val rval) {
		return Nil;
	}

	@Override
	public boolean isNil() {
		return true;
	}

	@Override
	public String toString() {
		return "nil";
	}
}

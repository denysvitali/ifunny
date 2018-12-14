package ch.supsi.dti.i3b.husky.ifunny.values;

public class NilVal extends Val {
	public static final NilVal Nil = new NilVal();

	@Override
	public Object getValue() {
		return null;
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
	public Val maj(Val rval) {
		return Nil;
	}

	@Override
	public Val min(Val rval) {
		return Nil;
	}

	@Override
	public Val majeq(Val rval) {
		return Nil;
	}

	@Override
	public Val mineq(Val rval) {
		return Nil;
	}

	@Override
	public Val or(Val rval) {
		return Nil;
	}

	@Override
	public Val and(Val rval) {
		return Nil;
	}

	@Override
	public boolean isNil() {
		return true;
	}
}

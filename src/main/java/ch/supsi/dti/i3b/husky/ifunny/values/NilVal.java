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
		return null;
	}

	@Override
	public Val min(Val rval) {
		return null;
	}

	@Override
	public Val majeq(Val rval) {
		return null;
	}

	@Override
	public Val mineq(Val rval) {
		return null;
	}

	@Override
	public Val or(Val rval) {
		return null;
	}

	@Override
	public Val and(Val rval) {
		return null;
	}

	@Override
	public boolean bool() {
		return false;
	}

	@Override
	public NumVal num() {
		return null;
	}

	@Override
	public StringVal string() {
		return null;
	}

	@Override
	public ClosureVal checkClosure() {
		return null;
	}
}

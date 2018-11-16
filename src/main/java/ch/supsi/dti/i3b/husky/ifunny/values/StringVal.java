package ch.supsi.dti.i3b.husky.ifunny.values;

public class StringVal extends Val {
	private String val = "";

	public StringVal(String s){
		this.val = s;
	}

	@Override
	public String getValue() {
		return val;
	}

	@Override
	public Val sub(Val rval) {
		return null;
	}

	@Override
	public Val sum(Val rval) {
		return null;
	}

	@Override
	public Val div(Val rval) {
		return null;
	}

	@Override
	public Val mult(Val rval) {
		return null;
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
	public boolean isString() {
		return true;
	}

	@Override
	public StringVal string() {
		return this;
	}
}

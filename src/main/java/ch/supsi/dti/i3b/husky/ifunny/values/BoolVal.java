package ch.supsi.dti.i3b.husky.ifunny.values;

public class BoolVal extends Val {

	private boolean value;

	public BoolVal(boolean value){
		this.value = value;
	}

	public static BoolVal True(){
		return new BoolVal(true);
	}

	public static BoolVal False() {
		return new BoolVal(false);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof BoolVal)){
			return false;
		}

		BoolVal b = (BoolVal) obj;
		return b.value == this.value;
	}

	@Override
	public Object getValue() {
		return null;
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
	public boolean bool() {
		return this.value;
	}

	@Override
	public boolean isBool() {
		return true;
	}
}

package ch.supsi.dti.i3b.husky.ifunny.values;

public class BoolVal extends Val {

	private boolean value;

	private BoolVal(boolean value){
		this.value = value;
	}

	public static BoolVal True = new BoolVal(true);
	public static BoolVal False = new BoolVal(false);

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof BoolVal)){
			return false;
		}

		BoolVal b = (BoolVal) obj;
		return b.value == this.value;
	}

	@Override
	public Val not() {
		return new BoolVal(!value);
	}

	@Override
	public boolean bool() {
		return this.value;
	}

	@Override
	public boolean isBool() {
		return true;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}

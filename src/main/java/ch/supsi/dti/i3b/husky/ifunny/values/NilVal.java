package ch.supsi.dti.i3b.husky.ifunny.values;

import ch.supsi.dti.i3b.husky.ifunny.Val;

public class NilVal extends Val {
	private NilVal() {
		super(null);
	}

	public static final NilVal Nil = new NilVal();
}

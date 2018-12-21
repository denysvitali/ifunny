package ch.supsi.dti.i3b.husky.ifunny.exceptions;

import ch.supsi.dti.i3b.husky.ifunny.values.Val;

public class InvalidOperationException extends FunnyRuntimeException {
	public InvalidOperationException(String operation, Val v1, Val v2) {
		super(
				"Invalid operation " + operation + " between " +
				v1.getClass() +
				" and " +
				v2.getClass());
	}
}

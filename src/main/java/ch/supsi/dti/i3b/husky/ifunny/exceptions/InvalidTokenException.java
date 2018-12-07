package ch.supsi.dti.i3b.husky.ifunny.exceptions;

import ch.supsi.dti.i3b.husky.ifunny.Token;

public class InvalidTokenException extends RuntimeException {
	public InvalidTokenException(Token t) {
		super("Invalid Token: " + t.type());
	}
}

package ch.supsi.dti.i3b.husky.ifunny.exceptions;

public class InvalidTypeException extends RuntimeException {
	public InvalidTypeException(String msg){
		super(String.format("Invalid Type: %s", msg));
	}
}

package ch.supsi.dti.i3b.husky.ifunny.values;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.exceptions.FunnyRuntimeException;
import ch.supsi.dti.i3b.husky.ifunny.exceptions.InvalidOperationException;
import ch.supsi.dti.i3b.husky.ifunny.exceptions.InvalidTypeException;
import ch.supsi.dti.i3b.husky.ifunny.expressions.Expr;

import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;

public abstract class Val extends Expr {
	@Override
	public Val eval(Env env) {
		return this;
	}

	public Val sub(Val rval) {
		throw new InvalidOperationException("-", this, rval);
	}

	public Val eq(Val rval) {
		throw new InvalidOperationException("=", this, rval);
	}

	public Val mod(Val rval) {
		throw new InvalidOperationException("%", this, rval);
	}

	public Val sum(Val rval) {
		throw new InvalidOperationException("+", this, rval);
	}

	public Val div(Val rval) {
		throw new InvalidOperationException("/", this, rval);
	}

	public Val mult(Val rval) {
		throw new InvalidOperationException("*", this, rval);
	}

	public Val gt(Val rval) {
		throw new InvalidOperationException(">", this, rval);
	}

	public Val lt(Val rval) {
		throw new InvalidOperationException("<", this, rval);
	}

	public Val gteq(Val rval) {
		throw new InvalidOperationException(">=", this, rval);
	}

	public Val lteq(Val rval) {
		throw new InvalidOperationException("<=", this, rval);
	}

	public Val or(Val rval) {
		throw new InvalidOperationException("||", this, rval);
	}

	public Val and(Val rval) {
		throw new InvalidOperationException("&&", this, rval);
	}

	public boolean bool() {
		throw new InvalidTypeException(this + " is not a boolean");
	}

	public NumVal num() {
		throw new InvalidTypeException(this + " is not a number");
	}

	public StringVal string() {
		throw new InvalidTypeException(this + " is not a string");
	}

	public ClosureVal checkClosure() {
		throw new InvalidTypeException(this + " is not a boolean");
	}

	public boolean isNum() {
		return false;
	}

	public boolean isBool() {
		return false;
	}

	public boolean isString() {
		return false;
	}

	public boolean isClosure() {
		return false;
	}

	public boolean isNil() {
		return false;
	}
}

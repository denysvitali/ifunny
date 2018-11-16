package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.exceptions.InvalidTypeException;
import ch.supsi.dti.i3b.husky.ifunny.expressions.Expr;
import ch.supsi.dti.i3b.husky.ifunny.values.ClosureVal;
import ch.supsi.dti.i3b.husky.ifunny.values.NilVal;
import ch.supsi.dti.i3b.husky.ifunny.values.NumVal;
import ch.supsi.dti.i3b.husky.ifunny.values.StringVal;

import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;

public abstract class Val extends Expr {
	public abstract Object getValue();

	@Override
	public Val eval(Env env) {
		return this;
	}

	public Val sub(Val rval) {
		return Nil;
	}

	public Val sum(Val rval) {
		return Nil;
	}

	public Val div(Val rval) {
		return Nil;
	}

	public Val mult(Val rval) {
		return Nil;
	}

	public Val maj(Val rval) {
		return Nil;
	}

	public Val min(Val rval) {
		return Nil;
	}

	public Val majeq(Val rval) {
		return Nil;
	}

	public Val mineq(Val rval) {
		return Nil;
	}

	public Val or(Val rval) {
		return Nil;
	}

	public Val and(Val rval) {
		return Nil;
	}

	public boolean bool(){
		throw new InvalidTypeException(this + " is not a boolean");
	}
	public NumVal num(){
		throw new InvalidTypeException(this + " is not a number");
	}
	public StringVal string() {
		throw new InvalidTypeException(this + " is not a string");
	}
	public ClosureVal checkClosure(){
		throw new InvalidTypeException(this + " is not a boolean");
	}

	public boolean isNum(){
		return false;
	}

	public boolean isBool(){
		return false;
	}

	public boolean isString(){
		return false;
	}

	public boolean isClosure(){
		return false;
	}

}

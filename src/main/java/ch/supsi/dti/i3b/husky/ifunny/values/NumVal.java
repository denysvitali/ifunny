package ch.supsi.dti.i3b.husky.ifunny.values;

import ch.supsi.dti.i3b.husky.ifunny.Val;
import ch.supsi.dti.i3b.husky.ifunny.exceptions.InvalidTypeException;

import java.math.BigDecimal;

public class NumVal extends Val {

	private BigDecimal num;

	public NumVal(int num){
		this.num = BigDecimal.valueOf(num);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof NumVal)){
			return false;
		}

		NumVal numVal = (NumVal) obj;
		return numVal.num.equals(num);
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
		throw new InvalidTypeException("Not a bool.");
	}

	@Override
	public NumVal num() {
		return this;
	}

	@Override
	public StringVal string() {
		throw new InvalidTypeException("Not a string.");
	}

	@Override
	public ClosureVal checkClosure() {
		throw new InvalidTypeException("Not a closure.");
	}
}

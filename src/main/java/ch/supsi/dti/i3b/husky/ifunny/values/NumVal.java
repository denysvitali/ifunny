package ch.supsi.dti.i3b.husky.ifunny.values;

import ch.supsi.dti.i3b.husky.ifunny.exceptions.InvalidTypeException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static ch.supsi.dti.i3b.husky.ifunny.values.BoolVal.False;
import static ch.supsi.dti.i3b.husky.ifunny.values.BoolVal.True;
import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;

public class NumVal extends Val {

	private BigDecimal num;
	private static MathContext MCTX = new MathContext(
			32,
			RoundingMode.HALF_EVEN);

	public NumVal(BigDecimal num){
		this.num = num;
	}
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
	public boolean isNum() {
		return true;
	}

	@Override
	public Val eq(Val rval){
		if(!rval.isNum()){
			return False;
		}

		return (this.num.compareTo(rval.num().num) == 0 ? True : False);
	}

	@Override
	public Val sub(Val rval) {
		if(rval.isNum()){
			return new NumVal(this.num.subtract(rval.num().num));
		}
		return Nil;
	}

	@Override
	public Val sum(Val rval) {
		if(rval.isNum()){
			return new NumVal(this.num.add(rval.num().num));
		}

		if(rval.isString()){
			return new StringVal(
					String.format("%s%s", this.num, rval.string())
			);
		}

		return Nil;
	}

	@Override
	public Val mod(Val rval) {
		if(rval.isNum()){
			return new NumVal(this.num.remainder(rval.num().num));
		}
		return Nil;
	}

	@Override
	public Val div(Val rval) {
		if(rval.isNum()){
			try {
				return new NumVal(this.num.divide(rval.num().num));
			} catch(ArithmeticException ex){
				return new NumVal(this.num.divide(rval.num().num, MCTX));
			}
		}
		return Nil;
	}

	@Override
	public Val mult(Val rval) {
		if(rval.isNum()){
			return new NumVal(this.num.multiply(rval.num().num));
		}

		return Nil;
	}

	@Override
	public Val gt(Val rval) {
		if(rval.isNum()){
			return (this.num.compareTo(rval.num().num) > 0 ? True : False);
		}

		return Nil;
	}

	@Override
	public Val lt(Val rval) {
		if(rval.isNum()){
			return (this.num.compareTo(rval.num().num) < 0 ? True : False);
		}

		return Nil;
	}

	@Override
	public Val gteq(Val rval) {
		if(rval.isNum()){
			return (this.num.compareTo(rval.num().num) >= 0 ? True : False);
		}

		return Nil;
	}

	@Override
	public Val lteq(Val rval) {
		if(rval.isNum()){
			return (this.num.compareTo(rval.num().num) <= 0 ? True : False);
		}

		return Nil;
	}

	@Override
	public Val or(Val rval) {
		return Nil;
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

	@Override
	public String toString(){
		return this.num.toString();
	}
}

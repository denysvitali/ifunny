package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.IfExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.NotExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.WhileExpr;
import ch.supsi.dti.i3b.husky.ifunny.values.BoolVal;
import ch.supsi.dti.i3b.husky.ifunny.values.NumVal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionTest {
	@Test
	void testNotExpr(){
		NotExpr notExpr = new NotExpr(new BoolVal(true));
		assertEquals(notExpr.eval(new Env(null)).getValue(), false);
	}

	@Test
	void testIfExpr(){
		IfExpr ifExpr = new IfExpr(
				new BoolVal(true),
				new NumVal(1),
				new NumVal(2)
		);

		assertEquals(1, ifExpr.eval(new Env(null)).getValue());

		ifExpr = new IfExpr(
				new BoolVal(false),
				new NumVal(1),
				new NumVal(2)
		);

		assertEquals(2, ifExpr.eval(new Env(null)).getValue());
	}

	@Test
	void testFunExpr(){
		FunExpr funExpr = new FunExpr(new ArrayList<>(),
				new ArrayList<>(),
				new NumVal(42));
		assertEquals(42, funExpr.eval(new Env(null)).getValue());
	}

	@Test
	void multiExpressionTest1(){
		IfExpr ifExpr = new IfExpr(
				new NotExpr(new BoolVal(false)),
				new NumVal(5),
				Nil
		);

		assertEquals(ifExpr.eval(new Env(null)).getValue(), 5);
	}
}

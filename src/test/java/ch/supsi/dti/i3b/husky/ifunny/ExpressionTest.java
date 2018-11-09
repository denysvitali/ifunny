package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.IfExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.NotExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.WhileExpr;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpressionTest {
	@Test
	void testNotExpr(){
		NotExpr notExpr = new NotExpr(new Val(true));
		assertEquals(notExpr.eval(new Env()).getValue(), false);
	}

	@Test
	void testIfExpr(){
		IfExpr ifExpr = new IfExpr(
				new Val(true),
				new Val(1),
				new Val(2)
		);

		assertEquals(1, ifExpr.eval(new Env()).getValue());

		ifExpr = new IfExpr(
				new Val(false),
				new Val(1),
				new Val(2)
		);

		assertEquals(2, ifExpr.eval(new Env()).getValue());
	}

	@Test
	void testFunExpr(){
		FunExpr funExpr = new FunExpr(new Val(42));
		assertEquals(42, funExpr.eval(new Env()).getValue());
	}
}

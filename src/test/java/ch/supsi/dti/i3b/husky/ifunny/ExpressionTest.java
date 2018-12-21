package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.IfExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.NotExpr;
import ch.supsi.dti.i3b.husky.ifunny.values.BoolVal;
import ch.supsi.dti.i3b.husky.ifunny.values.NumVal;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static ch.supsi.dti.i3b.husky.ifunny.values.BoolVal.False;
import static ch.supsi.dti.i3b.husky.ifunny.values.BoolVal.True;
import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionTest {
	@Test
	void testNotExpr(){
		NotExpr notExpr = new NotExpr(True);
		assertEquals(False, notExpr.eval(new Env(null)));
	}

	@Test
	void testIfExpr(){
		IfExpr ifExpr = new IfExpr(
				True,
				new NumVal(1),
				new NumVal(2)
		);

		assertEquals(new NumVal(1), ifExpr.eval(new Env(null)));

		ifExpr = new IfExpr(
				False,
				new NumVal(1),
				new NumVal(2)
		);

		assertEquals(new NumVal(2), ifExpr.eval(new Env(null)));
	}

	@Test
	void testFunExpr(){
		FunExpr funExpr = new FunExpr(new ArrayList<>(),
				new ArrayList<>(),
				new NumVal(42));
		ArrayList<Val> argList = new ArrayList<>();
		Env emptyEnv = new Env();
		assertEquals(new NumVal(42),
				funExpr
						.eval(emptyEnv)
						.checkClosure()
						.apply(argList)
						.eval(emptyEnv));
	}

	@Test
	void multiExpressionTest1(){
		IfExpr ifExpr = new IfExpr(
				new NotExpr(False),
				new NumVal(5),
				Nil
		);

		assertEquals(new NumVal(5), ifExpr.eval(new Env(null)));
	}
}

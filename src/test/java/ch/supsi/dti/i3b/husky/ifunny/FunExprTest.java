package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;
import ch.supsi.dti.i3b.husky.ifunny.values.NumVal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FunExprTest {
	@Test
	void scopeTest() {
		ArrayList<String> params = new ArrayList<>();
		params.add("lorem");

		ArrayList<String> locals = new ArrayList<>();
		locals.add("sit");
		locals.add("amet");

		Env emptyEnv = new Env();
		ArrayList<Val> argValues = new ArrayList<>(
				Collections.singleton(new NumVal(4))
		);

		FunExpr funExpr = new FunExpr(params, locals, new NumVal(2));
		assertEquals(new NumVal(2),
				funExpr.eval(emptyEnv)
						.checkClosure()
						.apply(argValues)
						.eval(emptyEnv));

		params.add("lorem");


		assertThrows(RuntimeException.class, () ->
				new FunExpr(params, locals, new NumVal(2))
						.eval(emptyEnv)
						.checkClosure()
						.apply(argValues)
						.eval(emptyEnv));
	}
}

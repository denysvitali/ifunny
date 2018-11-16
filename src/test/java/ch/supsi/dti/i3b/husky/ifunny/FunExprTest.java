package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FunExprTest {
	@Test
	void scopeTest() {
		ArrayList<String> params = new ArrayList<>();
		params.add("lorem");
		params.add("ipsum");
		params.add("dolor");

		ArrayList<String> locals = new ArrayList<>();
		params.add("sit");
		params.add("amet");

		FunExpr funExpr = new FunExpr(params, locals, new Val(2));
		assertEquals(2, funExpr.eval(new Env()).getValue());

		params.add("lorem");
		assertThrows(RuntimeException.class, () -> new FunExpr(params, locals, new Val(2)));
	}
}

package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.AssignmExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.SequenceExpr;
import ch.supsi.dti.i3b.husky.ifunny.values.NilVal;
import ch.supsi.dti.i3b.husky.ifunny.values.NumVal;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ParserTest {


	private FunExpr parseString(String str) throws IOException {
		Reader areader = new StringReader(str);
		Tokenizer tokenizer = new Tokenizer(areader);
		Parser parser = new Parser(tokenizer);
		return parser.parse();
	}
	@Test
	public void test1() throws IOException {
		FunExpr fun = parseString("{fib ->\n" +
				"    fib = {(n) ->\n" +
				"        if n < 2 then n else fib(n - 1) + fib(n - 2) fi\n" +
				"    };\n" +
				"    \n" +
				"    println(fib(40))\n" +
				"}");
		assertNotEquals(null, fun.params());
		assertEquals(0, fun.params().size());
		assertEquals("fib", fun.locals().get(0));

		assertEquals(SequenceExpr.class, fun.body().getClass());
		SequenceExpr sexpr = (SequenceExpr) fun.body();
		assertEquals(2, sexpr.getExprs().size());

		// Expr 1
		assertEquals(AssignmExpr.class, sexpr.getExprs().get(0).getClass());
		AssignmExpr expr1 = (AssignmExpr) sexpr.getExprs().get(0);
		assertEquals("fib", expr1.getIdVal());

		// SubExpr
		// TODO: Continue
	}
	@Test
	public void function() throws IOException {

		FunExpr fun = parseString("{->}");
		assertEquals(0, fun.params().size());
		assertEquals(0, fun.locals().size());
		assertEquals(NilVal.class, fun.body().getClass());

		fun = parseString("{(n)x->}");
		assertEquals("n", fun.params().get(0));
		assertEquals("x", fun.locals().get(0));

	}
	@Test
	public void assignm() throws IOException {
		FunExpr fun = parseString("{a->a=5}");
		assertEquals(AssignmExpr.class, fun.body().getClass());
		assertEquals("a", ((AssignmExpr)fun.body()).getIdVal());
		assertEquals(Token.Type.ASSIGNM, ((AssignmExpr)fun.body()).getAssignmType());
		assertEquals(NumVal.class, ((AssignmExpr)fun.body()).getAdditionalExpr().getClass());
		assertEquals(BigDecimal.valueOf(5), ((NumVal)((AssignmExpr)fun.body()).getAdditionalExpr()).getValue());
	}
}

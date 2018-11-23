package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.AssignmExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.SequenceExpr;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ParserTest {
	@Test
	public void test1() throws IOException {
		Reader areader = new StringReader("{fib ->\n" +
				"    fib = {(n) ->\n" +
				"        if n < 2 then n else fib(n - 1) + fib(n - 2) fi\n" +
				"    };\n" +
				"    \n" +
				"    println(fib(40))\n" +
				"}");

		Tokenizer tokenizer = new Tokenizer(areader);
		Parser parser = new Parser(tokenizer);
		FunExpr fun = parser.parse();
		assertNotEquals(null, fun.params());
		assertEquals(1, fun.params().size());
		assertEquals("fib", fun.params().get(0));

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
}

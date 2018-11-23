package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.*;
import ch.supsi.dti.i3b.husky.ifunny.values.StringVal;
import ch.supsi.dti.i3b.husky.ifunny.expressions.AssignmExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.SequenceExpr;
import ch.supsi.dti.i3b.husky.ifunny.values.NilVal;
import ch.supsi.dti.i3b.husky.ifunny.values.NumVal;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ParserTest {

	private static Reader getTestFile(String file) throws FileNotFoundException {
		return new BufferedReader(
				new FileReader(
						ParserTest.class.getResource("/parser/" + file)
								.getFile()
				)
		);
	}



	private FunExpr parseString(String str) throws IOException {
		Reader areader = new StringReader(str);
		Tokenizer tokenizer = new Tokenizer(areader);
		Parser parser = new Parser(tokenizer);
		return parser.parse();
	}
	@Test
	public void test1() throws IOException {
		Tokenizer tokenizer = new Tokenizer(getTestFile("t1.txt"));
		Parser parser = new Parser(tokenizer);
		FunExpr fun = parser.parse();
		assertNotEquals(null, fun.params());
		assertEquals(0, fun.params().size());
		assertEquals(1, fun.locals().size());
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
		assertEquals(FunExpr.class, expr1.getAdditionalExpr().getClass());
		FunExpr addExpr1 = (FunExpr) expr1.getAdditionalExpr();
		assertEquals(0, addExpr1.locals().size());
		assertEquals(1, addExpr1.params().size());
		assertEquals("n", addExpr1.params().get(0));
	}

	@Test
	void test2() throws IOException {
		Tokenizer tokenizer = new Tokenizer(getTestFile("t2.txt"));
		Parser parser = new Parser(tokenizer);
		FunExpr fun = parser.parse();
		assertNotEquals(null, fun.params());
		assertEquals(0, fun.params().size());
		assertEquals(1, fun.locals().size());
		assertEquals("fib", fun.locals().get(0));
	}

	@Test
	void test3() throws IOException {
		Tokenizer tokenizer = new Tokenizer(getTestFile("t3.txt"));
		Parser parser = new Parser(tokenizer);
		FunExpr fun = parser.parse();
		assertNotEquals(null, fun.params());
		assertEquals(0, fun.params().size());
		assertEquals(0, fun.locals().size());
		assertEquals(SequenceExpr.class, fun.body().getClass());
		SequenceExpr sequenceExpr = (SequenceExpr) fun.body();
		assertEquals(3, sequenceExpr.getExprs().size());

		// TODO: Convert \\n to \n
		ArrayList<String> expectedResults = new ArrayList<>(Arrays.asList(
				"Hello, world!\\n",
				"Hi!",
				"\\n",
				"你好"
		));

		for(Expr e : sequenceExpr.getExprs()){
			assertEquals(PrintExpr.class, e.getClass());
		}

		PrintExpr p1 = (PrintExpr) sequenceExpr.getExprs().get(0);
		PrintExpr p2 = (PrintExpr) sequenceExpr.getExprs().get(1);
		PrintExpr p3 = (PrintExpr) sequenceExpr.getExprs().get(2);

		assertEquals(1, p1.getArgs().size());
		assertEquals(2, p2.getArgs().size());
		assertEquals(1, p3.getArgs().size());

		List<Expr> svals = Arrays.asList(
				p1.getArgs().get(0),
				p2.getArgs().get(0),
				p2.getArgs().get(1),
				p3.getArgs().get(0)
		);

		int idx = 0;
		for(Expr stringVal : svals){
			assertEquals(StringVal.class, stringVal.getClass());
			StringVal sval = (StringVal) stringVal;
			assertEquals(expectedResults.get(idx), sval.getValue());
			idx++;
		}
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

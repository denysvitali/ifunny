package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.*;
import ch.supsi.dti.i3b.husky.ifunny.values.StringVal;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

		ArrayList<String> expectedResults = new ArrayList<>(Arrays.asList(
				"Hello, world!\n",
				"Hi!",
				"\n",
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
	void test4() throws IOException {
		Tokenizer tokenizer = new Tokenizer(getTestFile("t4.txt"));
		Parser parser = new Parser(tokenizer);
		FunExpr fun = parser.parse();
		assertNotEquals(null, fun.params());
		assertEquals(0, fun.params().size());
		assertEquals(0, fun.locals().size());
		assertEquals(PrintExpr.class, fun.body().getClass());

		String expectedResult = "Hello,\n world!\n";
		PrintExpr p1 = (PrintExpr) fun.body();

		assertEquals(1, p1.getArgs().size());

		assertEquals(StringVal.class, p1.getArgs().get(0).getClass());
		StringVal sval = (StringVal) p1.getArgs().get(0);
		assertEquals(expectedResult, sval.getValue());
	}
}

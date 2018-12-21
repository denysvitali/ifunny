package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.*;
import ch.supsi.dti.i3b.husky.ifunny.values.NumVal;
import ch.supsi.dti.i3b.husky.ifunny.values.StringVal;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ch.supsi.dti.i3b.husky.ifunny.Utils.parseString;
import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

	public static Reader getTestFile(String file) throws FileNotFoundException {
		return new BufferedReader(
				new FileReader(
						ParserTest.class.getResource("/parser/" + file)
								.getFile()
				)
		);
	}


	@Test
	void print() throws IOException {
		Tokenizer tokenizer = new Tokenizer(getTestFile("prints.txt"));
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
	public void closureInClosure() throws IOException {
		FunExpr fun = parseString("{sqr x -> " +
				"sqr = {(x) -> x * x};" +
				"x = {(z) -> sqr};" +
				"print(x(2)(3));" +
				"}");

		fun.eval(new Env());
	}

	@Test
	public void function() throws IOException {

		FunExpr fun = parseString("{->}");
		assertEquals(0, fun.params().size());
		assertEquals(0, fun.locals().size());
		assertEquals(Nil, fun.body());

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

	@Test
	public void negativeAssignm() throws IOException {

		FunExpr fun = parseString("{a->a=-5}");
		assertEquals(AssignmExpr.class, fun.body().getClass());
		assertEquals("a", ((AssignmExpr)fun.body()).getIdVal());
		assertEquals(Token.Type.ASSIGNM, ((AssignmExpr)fun.body()).getAssignmType());
		assertEquals(NumVal.class, ((AssignmExpr)fun.body()).getAdditionalExpr().getClass());
		assertEquals(BigDecimal.valueOf(-5), ((NumVal)((AssignmExpr)fun.body()).getAdditionalExpr()).getValue());
	}

    @Test
    public void binary() throws IOException {

        FunExpr fun = parseString("{a->a>5}");
        assertEquals(BinaryExpr.class, fun.body().getClass());
        assertEquals("a", ((GetVarExpr)((BinaryExpr)fun.body()).getFirstExpr()).getIdVal());
        assertEquals(Token.Type.MAJ, ((BinaryExpr)fun.body()).getBinaryType());
        assertEquals(BigDecimal.valueOf(5), ((NumVal)((BinaryExpr)fun.body()).getLastExpr()).getValue());
    }

    @Test
    public void scope() throws IOException {

        assertThrows(RuntimeException.class, ()->parseString("{a->a=b}"));
        try{
            parseString("{a->a=b}");

        }catch(RuntimeException e){
            assertEquals("Id \"b\" not in the scope", e.getMessage());
        }

    }

    @Test
    public void condition() throws IOException {
        FunExpr fun = parseString("{a->if a>1 then a else print(a) fi}");
        assertEquals(IfExpr.class, fun.body().getClass());
        assertEquals(BinaryExpr.class, ((IfExpr)fun.body()).getIfEvaluation().getClass());
        assertEquals(GetVarExpr.class, ((IfExpr)fun.body()).getIfBody().getClass());
        assertEquals(PrintExpr.class, ((IfExpr)fun.body()).getElseBody().getClass());
    }

    @Test
    public void loop() throws IOException {
        FunExpr fun = parseString("{n a -> while n < 10 do a=1 od}");
        assertEquals(WhileExpr.class, fun.body().getClass());
        assertEquals(BinaryExpr.class, ((WhileExpr)fun.body()).getCondition().getClass());
        assertEquals(AssignmExpr.class, ((WhileExpr)fun.body()).getBody().getClass());
    }

    @Test
    public void fibonacciTree() throws IOException {
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

        // Expr 0 of sequence
        assertEquals(AssignmExpr.class, sexpr.getExprs().get(0).getClass());
        AssignmExpr expr0 = (AssignmExpr) sexpr.getExprs().get(0);
        assertEquals("fib", expr0.getIdVal());
        // SubExpr of
        assertEquals(FunExpr.class, expr0.getAdditionalExpr().getClass());
        FunExpr subFun = (FunExpr) expr0.getAdditionalExpr();
        assertEquals(0, subFun.locals().size());
        assertEquals(1, subFun.params().size());
        assertEquals("n", subFun.params().get(0));
        assertEquals(IfExpr.class, subFun.body().getClass());
        IfExpr cond = (IfExpr) subFun.body();
        assertEquals(InvokeExpr.class, ((BinaryExpr)cond.getElseBody()).getFirstExpr().getClass());

        // Expr 1 of sequence
        assertEquals(PrintExpr.class, sexpr.getExprs().get(1).getClass());
        PrintExpr expr1 = (PrintExpr) sexpr.getExprs().get(1);
        assertEquals(1, expr1.getArgs().size());
        assertEquals(InvokeExpr.class, expr1.getArgs().get(0).getClass());
        InvokeExpr invoke = (InvokeExpr) expr1.getArgs().get(0);
        assertEquals("fib", ((GetVarExpr)invoke.getExpr()).getIdVal());
        assertEquals(1, invoke.getArgs().size());
        assertEquals(BigDecimal.valueOf(40), ((NumVal)invoke.getArgs().get(0)).getValue());

    }

	@Test
	void PrintNewLinedString() throws IOException {
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

	@Test
	void println() throws IOException {

		FunExpr fun = parseString("{->println(\"ciao\")}");

		assertEquals(PrintExpr.class, fun.body().getClass());
		assertTrue(((PrintExpr)fun.body()).isAddNewline());

		fun = parseString("{->print(\"ciao\")}");

		assertEquals(PrintExpr.class, fun.body().getClass());
		assertFalse(((PrintExpr)fun.body()).isAddNewline());

	}
}

package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;
import ch.supsi.dti.i3b.husky.ifunny.values.ClosureVal;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static ch.supsi.dti.i3b.husky.ifunny.Utils.parseFile;
import static ch.supsi.dti.i3b.husky.ifunny.Utils.parseString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterpreterTest {

	public static String getTestFile(String resPath){
		return InterpreterTest.class.getResource(resPath).getPath();
	}

	@Test
	public void intTest1() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bos));

		Parser p = new Parser(getTestFile("/interpreter/t1.txt"));
		FunExpr funExpr = p.parse();
		ClosureVal closureVal = funExpr.eval(new Env()).checkClosure();
		closureVal.apply(new ArrayList<>()).eval(new Env());
		assertEquals("Hello, world!\n", bos.toString());
	}

	@Test
	public void intMultExpr() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bos));

		Parser p = new Parser(getTestFile("/interpreter/t_i_multexpr.txt"));
		FunExpr funExpr = p.parse();
		ClosureVal closureVal = funExpr.eval(new Env()).checkClosure();
		closureVal.apply(new ArrayList<>()).eval(new Env());
		assertEquals("Hello, world!\nHi!\n你好\n", bos.toString());
	}

	@Test
	public void intConditional() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_conditional.txt"),
				"Hello\n");
	}

	@Test
	public void intCounterSimple() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_countersimple.txt"),
				"myCounter: \n" +
				"yourCounter: \n" +
				"\n");
	}

	private static void testExprF(String path, String ev) throws IOException {
		testExpr(parseFile(path), ev);
	}

	private static void testExprS(String expr, String ev) throws IOException {
		testExpr(parseString(expr), ev);
	}

	private static void testExpr(FunExpr fexpr, String ev) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bos));
		Env debuggableEnv = new Env();
		ClosureVal closure = fexpr.eval(debuggableEnv).checkClosure();
		closure.apply(new ArrayList<>()).eval(debuggableEnv);
		assertEquals(ev, bos.toString());
	}

	@Test
	public void intTestMajor() throws IOException {
		testExprS("{val ->\nval = 1; if val > 0 then print(\"OK\"); else println(\"This shouldn't happen.\"); fi }", "OK");
	}

	@Test
	public void intTestMajor2() throws IOException {
		testExprS("{val ->\nval = 0; if val > 0 then print(\"KO\"); else print(\"OK\"); fi }", "OK");
	}

	@Test
	public void intTestMajor3() throws IOException {
		testExprS("{val ->\nval = -1; if val > 0 then print(\"KO\"); else print(\"OK\"); fi }", "OK");
	}

	@Test
	public void intTestMinor() throws IOException {
		testExprS("{val ->\nval = -1; if val < 0 then print(\"OK\"); else print(\"KO\"); fi }", "OK");
	}

	@Test
	public void intTestMinor2() throws IOException {
		testExprS("{val ->\nval = 0; if val < 0 then print(\"KO\"); else print(\"OK\"); fi }", "OK");
	}

	@Test
	public void intTestMinor3() throws IOException {
		testExprS("{val ->\nval = 1; if val < 0 then print(\"KO\"); else print(\"OK\"); fi }", "OK");
	}

	@Test
	public void intTestMinorEq() throws IOException {
		testExprS("{val ->\nval = 0; if val <= 0 then print(\"OK\"); else print(\"KO\"); fi }", "OK");
	}

	@Test
	public void intTestMinorEq2() throws IOException {
		testExprS("{val ->\nval = 1; if val <= 0 then print(\"KO\"); else print(\"OK\"); fi }", "OK");
	}

	@Test
	public void intTestMinorEq3() throws IOException {
		testExprS("{val ->\nval = -1; if val <= 0 then print(\"OK\"); else print(\"KO\"); fi }", "OK");
	}

	@Test
	public void intTestMajorEq() throws IOException {
		testExprS("{val ->\nval = 0; if val >= 0 then print(\"OK\"); else print(\"KO\"); fi }", "OK");
	}

	@Test
	public void intTestMajorEq2() throws IOException {
		testExprS("{val ->\nval = 1; if val >= 0 then print(\"OK\"); else print(\"KO\"); fi }", "OK");
	}

	@Test
	public void intTestMajorEq3() throws IOException {
		testExprS("{val ->\nval = -1; if val >= 0 then print(\"KO\"); else print(\"OK\"); fi }", "OK");
	}

	@Test
	public void intMod() throws IOException {
		testExprS("{->print(1 % 2);}", "1");
	}

	@Test
	public void intMod1() throws IOException {
		testExprS("{->print(3 % 2);}", "1");
	}

	@Test
	public void intMult() throws IOException {
		testExprS("{->print(1 * 2);}", "2");
	}
}

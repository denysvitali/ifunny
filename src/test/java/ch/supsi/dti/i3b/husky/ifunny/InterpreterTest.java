package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.exceptions.InvalidOperationException;
import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;
import ch.supsi.dti.i3b.husky.ifunny.values.ClosureVal;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static ch.supsi.dti.i3b.husky.ifunny.Utils.parseFile;
import static ch.supsi.dti.i3b.husky.ifunny.Utils.parseString;
import static org.junit.jupiter.api.Assertions.*;

public class InterpreterTest {

	private static String NL = System.getProperty("line.separator");

	static String getTestFile(String resPath){
		return InterpreterTest.class.getResource(resPath).getPath();
	}

	@Test
	void intTest1() throws IOException {
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
		assertEquals("Hello, world!\nHi!\n你好" + NL, bos.toString());
	}

	@Test
	public void intSingleUTF8Expr() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bos));

		Parser p = new Parser(getTestFile("/interpreter/t_i_emoji.txt"));
		FunExpr funExpr = p.parse();
		ClosureVal closureVal = funExpr.eval(new Env()).checkClosure();
		closureVal.apply(new ArrayList<>()).eval(new Env());
		assertEquals("Hello\uD83D\uDD25!", bos.toString());
	}

	@Test
	public void intConditional() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_conditional.txt"),
				"Hello\n");
	}

	@Test
	public void intCounterSimple() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_countersimple.txt"),
				"myCounter: 100" + NL + "" +
				"yourCounter: 50" + NL + "" +
				"" + NL + "");
	}

	@Test
	public void intCounterComplex() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_countercomplex.txt"),
				"myCounter: 100" + NL +
						"yourCounter: 50" + NL +
						NL +
						"myCounter[0]: 150" + NL +
						"yourCounter[0]: 40" + NL +
						NL +
						"myCounter[1]: 200" + NL +
						"yourCounter[1]: 30" + NL +
						NL +
						"myCounter[2]: 250" + NL +
						"yourCounter[2]: 20" + NL +
						NL +
						"myCounter[3]: 300" + NL +
						"yourCounter[3]: 10" + NL +
						NL +
						"myCounter[4]: 350" + NL +
						"yourCounter[4]: 0" + NL +
						NL +
						"myCounter[5]: 400" + NL +
						"yourCounter[5]: -10" + NL +
						NL +
						"myCounter[6]: 450" + NL +
						"yourCounter[6]: -20" + NL +
						NL +
						"myCounter[7]: 500" + NL +
						"yourCounter[7]: -30" + NL +
						NL +
						"myCounter[8]: 550" + NL +
						"yourCounter[8]: -40" + NL +
						NL +
						"myCounter[9]: 600" + NL +
						"yourCounter[9]: -50" + NL +
						NL);
	}

	// TODO: Test CoinChange
	@Test
	public void intCoinChange() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_coinchange.txt"),
				"13" + NL);
	}

	@Test
	public void intCoinChange2() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_coinchange_2.txt"),
				"0" + NL);
	}

	@Test
	public void intCoinChange3() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_coinchange_3.txt"),
				"1" + NL);
	}

	@Test
	void intFibonacci() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_fibonacci.txt"),
				"354224848179261915075" + NL);
	}

	@Test
	public void intIsEven() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_iseven.txt"),
				"true" + NL);
	}

	@Test
	public void intIsEvenFake() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_iseven_fake.txt"),
				"false" + NL);
	}

	@Test
	public void intSqrtTest1() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_sqrt.txt"),
				"sqrt(16): 3.99999999999999999999999999999998515625" + NL);
	}

	@Test
	public void intTree() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_tree.txt"),
				"two" + NL);
	}
	@Test
	public void intHanoi() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_hanoi.txt"),
				"left -> right" + NL +
						"left -> center" + NL +
						"right -> center" + NL +
						"left -> right" + NL +
						"center -> left" + NL +
						"center -> right" + NL  +
						"left -> right" + NL);
	}

	@Test
	public void intGeneralTest1() throws IOException {
		testExprF(getTestFile("/interpreter/t_i_generaltest1.txt"),
				"9" + NL +
						"3.3333333333333333333333333333333" + NL +
						"6.6666666666666666666666666666667" + NL +
						"4" + NL  +
						"1237940039285380274899124224" + NL  +
						"8.07793566946316088741610050849573099185363389551639556884765625E-28" + NL  +
						"1.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" + NL  +
						"true" + NL  +
						"0.47" + NL);
	}

	@Test
	void intClosureInClosure() throws IOException {
		testExprS("{sqr x -> " +
				"sqr = {(x) -> x * x};" +
				"x = {(z) -> sqr};" +
				"print(x(2)(3));" +
				"}", "9");
	}

	private static void testExprF(String path, String ev) throws IOException {
		testExpr(parseFile(path), ev);
	}

	private static void testExprS(String expr, String ev) throws IOException {
		testExpr(parseString(expr), ev);
	}

	private static <T extends Throwable> void testExprST(String expr, Class<T> throwable) throws IOException {
		testExprT(parseString(expr), throwable);
	}

	private static void testExprSSW(String fexpr, String ev) throws IOException {
		assertTrue(testOutput(parseString(fexpr)).startsWith(ev));
	}

	private static void testExprSWaEW(String fexpr, String sw, String ew) throws IOException {
		String output = testOutput(parseString(fexpr));
		assertTrue(output.startsWith(sw));
		assertTrue(output.endsWith(ew));
	}

	private static void testExprSEW(String fexpr, String ev) throws IOException {
		assertTrue(testOutput(parseString(fexpr)).endsWith(ev));
	}

	private static void testExpr(FunExpr fexpr, String ev) {
		assertEquals(ev, testOutput(fexpr));
	}

	private static String testOutput(FunExpr fexpr) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bos));
		Env debuggableEnv = new Env();
		ClosureVal closure = fexpr.eval(debuggableEnv).checkClosure();
		closure.apply(new ArrayList<>()).eval(debuggableEnv);
		return bos.toString();
	}

	private static <T extends Throwable> void testExprT(FunExpr fexpr, Class<T> throwable) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bos));
		Env debuggableEnv = new Env();
		ClosureVal closure = fexpr.eval(debuggableEnv).checkClosure();
		assertThrows(throwable, ()->{
			closure.apply(new ArrayList<>()).eval(debuggableEnv);
		});
	}

	@Test
	public void intTestMajor() throws IOException {
		testExprS("{val ->" + NL + "val = 1; if val > 0 then print(\"OK\"); else println(\"This shouldn't happen.\"); fi }", "OK");
	}

	@Test
	public void intTestMajor2() throws IOException {
		testExprS("{val ->" + NL + "val = 0; if val > 0 then print(\"KO\"); else print(\"OK\"); fi }", "OK");
	}

	@Test
	public void intTestMajor3() throws IOException {
		testExprS("{val ->" + NL + "val = -1; if val > 0 then print(\"KO\"); else print(\"OK\"); fi }", "OK");
	}

	@Test
	public void intTestMinor() throws IOException {
		testExprS("{val ->" + NL + "val = -1; if val < 0 then print(\"OK\"); else print(\"KO\"); fi }", "OK");
	}

	@Test
	public void intTestMinor2() throws IOException {
		testExprS("{val ->" + NL + "val = 0; if val < 0 then print(\"KO\"); else print(\"OK\"); fi }", "OK");
	}

	@Test
	public void intTestMinor3() throws IOException {
		testExprS("{val ->" + NL + "val = 1; if val < 0 then print(\"KO\"); else print(\"OK\"); fi }", "OK");
	}

	@Test
	public void intTestMinorEq() throws IOException {
		testExprS("{val ->" + NL + "val = 0; if val <= 0 then print(\"OK\"); else print(\"KO\"); fi }", "OK");
	}

	@Test
	public void intTestMinorEq2() throws IOException {
		testExprS("{val ->" + NL + "val = 1; if val <= 0 then print(\"KO\"); else print(\"OK\"); fi }", "OK");
	}

	@Test
	public void intTestMinorEq3() throws IOException {
		testExprS("{val ->" + NL + "val = -1; if val <= 0 then print(\"OK\"); else print(\"KO\"); fi }", "OK");
	}

	@Test
	public void intTestMajorEq() throws IOException {
		testExprS("{val ->" + NL + "val = 0; if val >= 0 then print(\"OK\"); else print(\"KO\"); fi }", "OK");
	}

	@Test
	public void intTestMajorEq2() throws IOException {
		testExprS("{val ->" + NL + "val = 1; if val >= 0 then print(\"OK\"); else print(\"KO\"); fi }", "OK");
	}

	@Test
	public void intTestMajorEq3() throws IOException {
		testExprS("{val ->" + NL + "val = -1; if val >= 0 then print(\"KO\"); else print(\"OK\"); fi }", "OK");
	}

	@Test
	public void intNot() throws IOException {
		testExprS("{->print(!true);}", "false");
	}

	@Test
	public void intNot2() throws IOException {
		testExprS("{->print(!false);}", "true");
	}

	@Test
	public void intNot3() throws IOException {
		testExprST("{x->x=1; print(!x);}", InvalidOperationException.class);
	}

	@Test
	public void intClosPlusString() throws IOException {
		testExprSWaEW("{x->x={(z)->z}; print(x + \"hello\");}",
				"ch.supsi.dti.i3b.husky.ifunny.values.ClosureVal@",
				"hello");
	}

	@Test
	public void intStringPlusClos() throws IOException {
		testExprSSW("{x->x={(z)->z}; print(\"hello\" + x);}",
				"helloch.supsi.dti.i3b.husky.ifunny.values.ClosureVal@");
	}

	@Test
	public void intClosPlusClos() throws IOException {
		testExprST("{x->x={(z)->z}; print(x + x);}",
				InvalidOperationException.class);
	}

	@Test
	public void intMinusVar() throws IOException {
		testExprS("{x->x=1; print(-x);}", "-1");
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
	public void intMod2() throws IOException {
		testExprS("{->print(6 % 2);}", "0");
	}

	@Test
	public void intEq() throws IOException {
		testExprS("{->print(3.0 == 3.00);}", "true");
	}

	@Test
	public void intEq2() throws IOException {
		testExprS("{->print(\"a\" == \"a\");}", "true");
	}

	@Test
	public void intEq3() throws IOException {
		testExprS("{->print(nil == nil);}", "true");
	}

	@Test
	public void intEq4() throws IOException {
		testExprS("{->print({} == {});}", "false");
	}

	@Test
	public void intEq5() throws IOException {
		testExprS("{a->a = {}; print(a == a);}", "true");
	}

	@Test
	public void intAnd() throws IOException {
		testExprS("{-> print(true && true)}", "true");
	}

	@Test
	public void intAnd2() throws IOException {
		testExprS("{-> false && print(\"I won't print anything\")}", "");
	}

	@Test
	public void intAnd3() throws IOException {
		testExprS("{-> true && print(\":)\")}", ":)");
	}

	@Test
	public void intAnd4() throws IOException {
		testExprS("{-> print(true && true)}", "true");
	}

	@Test
	public void intAnd5() throws IOException {
		testExprS("{-> print(true && false)}", "false");
	}

	@Test
	public void intOr() throws IOException {
		testExprS("{-> true || print(\"I won\'t print anything.\")}", "");
	}

	@Test
	public void intOr2() throws IOException {
		testExprS("{-> false || print(\":)\")}", ":)");
	}

	@Test
	public void intOr3() throws IOException {
		testExprS("{-> print(false || true)}", "true");
	}

	@Test
	public void intOr4() throws IOException {
		testExprS("{-> print(false || false)}", "false");
	}



	@Test
	public void intMod3() throws IOException {
		testExprS("{->print(3.27 % 0.7);}", "0.47");
	}

	@Test
	public void intMult() throws IOException {
		testExprS("{->print(1 * 2);}", "2");
	}

	@Test
	public void intDiv() throws IOException {
		testExprS("{->print(1 / 2);}", "0.5");
	}

	@Test
	public void intDiv2() throws IOException {
		testExprS("{->print(1 / 3);}", "0.33333333333333333333333333333333");
	}

	@Test
	public void intPrintFalse() throws IOException {
		testExprS("{->print(false);}", "false");
	}

	@Test
	public void intPrintConcat() throws IOException {
		testExprS("{->print(1.0 + \"ciao\");}", "1.0ciao");
	}

	@Test
	public void intPrintConcat2() throws IOException {
		testExprS("{->print(\"ciao\" + 1.0);}", "ciao1.0");
	}

	@Test
	public void intPrintConcat3() throws IOException {
		testExprS("{->print(\"hello \" + \"world\");}", "hello world");
	}

	@Test
	public void intPrintTrue() throws IOException {
		testExprS("{->print(true);}", "true");
	}

	@Test
	public void intPrintNil() throws IOException {
		testExprS("{->print(nil);}", "nil");
	}
}

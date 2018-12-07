package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;
import ch.supsi.dti.i3b.husky.ifunny.values.ClosureVal;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

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
	public void testConditional() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bos));

		Parser p = new Parser(getTestFile("/interpreter/t2.txt"));
		FunExpr funExpr = p.parse();
		ClosureVal closureVal = funExpr.eval(new Env()).checkClosure();
		closureVal.apply(new ArrayList<>()).eval(new Env());
		assertEquals("Hello\n", bos.toString());
	}
}

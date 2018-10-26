package ch.supsi.dti.i3b.husky.ifunny;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {
	private void doTokenization(String s) throws IOException {
		Tokenizer t = createStringTokenizer(s);
		Token token = t.token;
		while(token.type() != Token.Type.EOS){
			token = t.getNextToken();
		}
	}

	private static Tokenizer createStringTokenizer(String s) throws IOException {
		return new Tokenizer(new StringReader(s));
	}

	@Test
	public void tokenization1() throws IOException {
		doTokenization("1234 /* comment */ 1234\n// Hello World\n12345");
	}

	@Test
	public void tokenization2() throws IOException {
		doTokenization("2 <= 3");
	}

	@Test
	public void integerTokenizer() throws IOException {
		Tokenizer t = createStringTokenizer("2");
		Token dbl = t.token;

		assertEquals(new BigDecimal("2"), dbl.getNum());
	}

	@Test
	public void negativeIntegerTokenizer() throws IOException {
		Tokenizer t = createStringTokenizer("-2");
		Token dbl = t.token;

		assertEquals(new BigDecimal("-2"), dbl.getNum());
	}

	@Test
	public void negativeDoubleTokenizer() throws IOException {
		Tokenizer t = createStringTokenizer("-2.13");
		Token dbl = t.token;

		assertEquals(new BigDecimal("-2.13"), dbl.getNum());
	}


	@Test
	public void negativeExpTokenizer() throws IOException {
		Tokenizer t = createStringTokenizer("-4.21E-10");
		Token dbl = t.token;

		assertEquals(new BigDecimal("-4.21E-10"), dbl.getNum());
	}

	@Test
	public void negativeExpTokenizer2() throws IOException {
		Tokenizer t = createStringTokenizer("-4.21E+10");
		Token dbl = t.token;

		assertEquals(new BigDecimal("-4.21E+10"), dbl.getNum());
	}

	@Test
	public void negativeExpTokenizer3() throws IOException {
		Tokenizer t = createStringTokenizer("-4.21e+10");
		Token dbl = t.token;

		assertEquals(new BigDecimal("-4.21e+10"), dbl.getNum());
	}

	@Test
	public void doubleTokenizer() throws IOException {
		Tokenizer t = createStringTokenizer("2.14");
		Token dbl = t.token;

		assertEquals(new BigDecimal("2.14"), dbl.getNum());
	}

	@Test
	public void doubleExpTokenizer() throws IOException {
		Tokenizer t = createStringTokenizer("8.1E-12");
		Token dbl = t.token;

		assertEquals(new BigDecimal("8.1e-12"), dbl.getNum());
	}

	@Test
	public void inlineComment() throws IOException {
		Tokenizer t = createStringTokenizer("// I'm a comment");
		Token token = t.token;

		assertEquals(Token.Type.EOS, token.type());
	}

	@Test
	public void multilineComment() throws IOException {
		Tokenizer t = createStringTokenizer("/* I'm a comment */");
		Token token = t.token;

		assertEquals(Token.Type.EOS, token.type());
	}
}
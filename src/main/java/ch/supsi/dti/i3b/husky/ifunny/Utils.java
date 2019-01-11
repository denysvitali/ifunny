package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;

import java.io.*;

public class Utils {
	public static FunExpr parseString(String str) throws IOException {
		Reader areader = new StringReader(str);
		Tokenizer tokenizer = new Tokenizer(areader);
		Parser parser = new Parser(tokenizer);
		return parser.parse();
	}

	public static FunExpr parseFile(String path) throws IOException {
		BufferedReader areader = new BufferedReader(new FileReader(path));
		Tokenizer tokenizer = new Tokenizer(areader);
		Parser parser = new Parser(tokenizer);
		return parser.parse();
	}
}

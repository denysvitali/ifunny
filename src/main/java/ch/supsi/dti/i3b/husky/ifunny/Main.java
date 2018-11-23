package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Main {

    public static void main(String[] args) {
	    Reader areader = new StringReader("{fib ->\n" +
                "    fib = {(n) ->\n" +
                "        if n < 2 then n else fib(n - 1) + fib(n - 2) fi\n" +
                "    };\n" +
                "    \n" +
                "    println(fib(40))\n" +
                "}");

        try {
            Tokenizer tokenizer = new Tokenizer(areader);
            Parser parser = new Parser(tokenizer);
            FunExpr fun = parser.parse();
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

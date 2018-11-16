package ch.supsi.dti.i3b.husky.ifunny;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Main {

    public static void main(String[] args) {
	    Reader areader = new StringReader("{average sqr abs sqrt x ->\n" +
                "    average = {(x y) -> (x + y) / 2};\n" +
                "    sqr = {(x) -> x * x};\n" +
                "    abs = {(x) -> if x >= 0 then x else -x fi};\n" +
                "    sqrt = {(x) tolerance isGoodEnough improve sqrtIter ->\n" +
                "        tolerance = 1e-30;\n" +
                "        \n" +
                "        isGoodEnough = {(guess) -> abs(sqr(guess) - x) < tolerance};\n" +
                "        improve = {(guess) -> average(guess, x / guess)};\n" +
                "        sqrtIter = {(guess) ->\n" +
                "            if isGoodEnough(guess) then guess else sqrtIter(improve(guess)) fi\n" +
                "        };\n" +
                "        sqrtIter(1)\n" +
                "    };\n" +
                "\n" +
                "    x = 16;\n" +
                "    println(\"sqrt(\", x, \"): \", sqrt(x));\n" +
                "}");

        try {
            Tokenizer tokenizer = new Tokenizer(areader);
            Parser parser = new Parser(tokenizer);
            parser.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

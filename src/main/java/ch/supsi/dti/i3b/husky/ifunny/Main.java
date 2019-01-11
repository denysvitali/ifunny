package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        if(args.length < 1){
            throw new IllegalArgumentException("Too few arguments. Input file path.");
        }
        else {
            FunExpr funny;
            try {
                funny = Utils.parseFile(args[0]);
                Env env = new Env();
                Val val = funny.eval(env).checkClosure().apply(new ArrayList<>()).eval(env);
              //  System.out.println(val);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.ExprList;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

import java.util.ArrayList;

import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;

public class PrintExpr extends Expr {

    private ArrayList<Expr> args;
    private boolean addNewline = false;

    public PrintExpr(ArrayList<Expr> args, boolean addNewline){
        this.args = args;
        this.addNewline = addNewline;
    }

    public PrintExpr(ArrayList<Expr> args) {
        this.args = args;
    }

    @Override
    public Val eval(Env env) {
        for(Expr e : args){
            Val v = e.eval(env);
            System.out.print(v);
        }
        if(addNewline){
            System.out.print(System.getProperties().get("line.separator"));
        }
        return Nil;
    }

    public ArrayList<Expr> getArgs() {
        return args;
    }

    public boolean isAddNewline() {
        return addNewline;
    }
}

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
            if(v.isBool()) {
                System.out.print(v.bool());
            } else if(v.isNum()){
                System.out.print(v.num().getValue());
            } else if(v.isString()){
                System.out.print(v.string().getValue());
            } else if (v.isNil()){
                System.out.print("nil");
            }
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

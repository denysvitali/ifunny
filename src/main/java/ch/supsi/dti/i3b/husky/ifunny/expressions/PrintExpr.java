package ch.supsi.dti.i3b.husky.ifunny.expressions;

import ch.supsi.dti.i3b.husky.ifunny.Env;
import ch.supsi.dti.i3b.husky.ifunny.ExprList;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;

public class PrintExpr extends Expr {

    private ExprList args;
    private boolean addNewline = false;

    public PrintExpr(ExprList args, boolean addNewline){
        this.args = args;
    }

    public PrintExpr(ExprList args) {
        this.args = args;
    }

    @Override
    public Val eval(Env env) {
        for(Val v : args.eval(env)){
            if(v.isBool()) {
                System.out.print(v.bool());
            } else if(v.isNum()){
                System.out.print(v.num().getValue());
            } else if(v.isString()){
                System.out.print(v.string().getValue());
            }
        }
        if(addNewline){
            System.out.print(System.getProperties().get("line.separator"));
        }
        return Nil;
    }

    public ExprList getArgs() {
        return args;
    }
}

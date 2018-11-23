package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.Expr;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

import java.util.List;
import java.util.stream.Collectors;

public class ExprList {

    private List<Expr> exprList;

    public ExprList(List<Expr> exprList){
        this.exprList = exprList;
    }

    public Expr get(int index){
        return exprList.get(index);
    }

    public int size(){
        return exprList.size();
    }

    public List<Val> eval(Env env){
        return exprList.stream().map((e)->e.eval(env)).collect(Collectors.toList());
    }
}

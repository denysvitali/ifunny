package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.Expr;

import java.util.List;
import java.util.stream.Collectors;

public class ExprList {

    private List<Expr> exprList;

    public ExprList(List<Expr> exprList){
        this.exprList = exprList;
    }

    public List<Val> eval(Env env){
        return exprList.stream().map((e)->e.eval(env)).collect(Collectors.toList());
    }
}

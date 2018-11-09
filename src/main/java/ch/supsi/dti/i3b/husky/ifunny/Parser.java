package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.Expr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.SequenceExpr;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    Tokenizer tokenStream;

    Parser(Tokenizer tokenStream){
        this.tokenStream = tokenStream;
    }

    public FunExpr parse() throws IOException {

        FunExpr fFunc = function();

        if(tokenStream.check(Token.Type.EOS)){
            return fFunc;
        }
        else{
            System.out.println("Wrong token");
            throw new RuntimeException();
        }
    }

    private FunExpr function(Scope scope) throws IOException {
        if (tokenStream.check(Token.Type.OPNCRLYBRACKET)) {
            tokenStream.nextToken();
            ArrayList<String> param = optParams();
            ArrayList<String> locals = optLocals();
            Expr seqExpr = optSequence(scope);
            if (tokenStream.check(Token.Type.CLSCRLYBRACKET)) {
                return new FunExpr(param, locals, seqExpr);
            }
            else {
                System.out.println("Wrong token");
                throw new RuntimeException();
            }
        }
        else {
            System.out.println("Wrong token");
            throw new RuntimeException();
        }
    }

    private ArrayList<String> optParams() throws IOException {
        ArrayList<String> listParams = new ArrayList<>();
        if(tokenStream.check(Token.Type.OPNRNBRACKET)) {
            tokenStream.nextToken();
            listParams = optId();
            if(!tokenStream.check(Token.Type.CLSRNBRACKET)) {
                System.out.println("Wrong token");
                throw new RuntimeException();
            }
            tokenStream.nextToken();
        }
        return listParams;
    }

    private ArrayList<String> optLocals() throws IOException {
        return optId();
    }

    private ArrayList<String> optId() throws IOException {
        ArrayList<String> listId = new ArrayList<>();
        while(tokenStream.check(Token.Type.ID)){
            listId.add(tokenStream.getToken().getStr());
            tokenStream.nextToken();
        }
    }

    private Expr optSequence(Scope scope) throws IOException {
        if(tokenStream.check(Token.Type.ARROW)){
            tokenStream.nextToken();
            return sequence(scope);
        }
        return new NilVal();
    }

    private Expr sequence(Scope scope) throws IOException {
        ArrayList<Expr> listAssignment = new ArrayList<>();
        Expr assignExpr;
        if((assignExpr = optAssignment(scope)) != null){
            listAssignment.add(assignExpr);
        }
        while(tokenStream.check(Token.Type.SEMICOLON)){
            tokenStream.nextToken();
            if((assignExpr = optAssignment(scope)) != null){
                listAssignment.add(assignExpr);
            }
        }
        return listAssignment.size() == 0 ? new NilVal() : listAssignment.size() == 1 ? 
                listAssignment.get(0) : new SequenceExpr(listAssignment);
    }

    private Expr optAssignment(Scope scope) {
    }
}
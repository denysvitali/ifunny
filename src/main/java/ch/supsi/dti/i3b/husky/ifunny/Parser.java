package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.Expr;
import ch.supsi.dti.i3b.husky.ifunny.expressions.FunExpr;

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
            Expr seqExpr = optSequence();
            if (tokenStream.check(Token.Type.CLSCRLYBRACKET)) {
                return new FunExpr(param, locals, seqExpr);
            }
            else{
                System.out.println("Wrong token");
                throw new RuntimeException();
            }
        }
        else{
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
}

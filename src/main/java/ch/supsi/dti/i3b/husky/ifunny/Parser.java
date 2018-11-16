package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.expressions.*;

import java.io.IOException;
import java.util.ArrayList;

import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;

public class Parser {

    Tokenizer tokenStream;

    Parser(Tokenizer tokenStream){
        this.tokenStream = tokenStream;
    }

    public FunExpr parse() throws IOException {

        FunExpr fFunc = function(new Scope());

        if(tokenStream.check(Token.Type.EOS)){
            return fFunc;
        }
        else{
            throw new RuntimeException("Wrong token");
        }
    }

    private FunExpr function(Scope scope) throws IOException {
        if (tokenStream.check(Token.Type.OPN_CRLY_BRKT)) {
            tokenStream.nextToken();
            ArrayList<String> param = optParams();
            ArrayList<String> locals = optLocals();

            // TODO: Implement duplicate check between lists, & scope assignment.
            Expr seqExpr = optSequence(new Scope(scope, param, locals));
            if (tokenStream.check(Token.Type.CLS_CRLY_BRKT)) {
                tokenStream.nextToken();
                return new FunExpr(param, locals, seqExpr);
            }
            else {
                throw new RuntimeException("Wrong token");
            }
        }
        else {
            throw new RuntimeException("Wrong token");
        }
    }

    private ArrayList<String> optParams() throws IOException {
        ArrayList<String> listParams = new ArrayList<>();
        if(tokenStream.check(Token.Type.OPN_RND_BRACKET)) {
            tokenStream.nextToken();
            listParams = optIds();
            if(!tokenStream.check(Token.Type.CLS_RND_BRACKET)) {
                throw new RuntimeException("Wrong token");
            }
            tokenStream.nextToken();
        }
        return listParams;
    }

    private ArrayList<String> optLocals() throws IOException {
        return optIds();
    }

    private ArrayList<String> optIds() throws IOException {
        ArrayList<String> listId = new ArrayList<>();
        while(tokenStream.check(Token.Type.ID)){
            listId.add(tokenStream.getToken().getStr());
            tokenStream.nextToken();
        }
        return listId;
    }

    private Expr optSequence(Scope scope) throws IOException {
        if(tokenStream.check(Token.Type.ARROW)){
            tokenStream.nextToken();
            return sequence(scope);
        }
        return Nil;
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
        return listAssignment.size() == 0 ? Nil : listAssignment.size() == 1 ?
                listAssignment.get(0) : new SequenceExpr(listAssignment);
    }

    private Expr optAssignment(Scope scope) throws IOException {
        //Chiedere per come riconoscere la presenza di assignment
        return assignment(scope);
    }
    private Expr assignment(Scope scope) throws IOException {

        if(tokenStream.check(Token.Type.ID)) {
            String id = tokenStream.getToken().getStr();
            tokenStream.nextToken();
            if (tokenStream.check(Token.Type.ASSIGNM)
                    || tokenStream.check(Token.Type.ASSIGNMSUM)
                    || tokenStream.check(Token.Type.ASSIGNMSUB)
                    || tokenStream.check(Token.Type.ASSIGNMULT)
                    || tokenStream.check(Token.Type.ASSIGNMDIV)
                    || tokenStream.check(Token.Type.ASSIGNMOD)) {
                Token token = tokenStream.getToken();
                tokenStream.nextToken();
                return new AssignmExpr(id, token.type(), assignment(scope));
            }
            else{
                tokenStream.revertToken();
            }
        }
        return logicalOr(scope);

    }

    private Expr logicalOr(Scope scope) throws IOException {

        Expr expr = logicalAnd(scope);
        if(tokenStream.check(Token.Type.OR)){
            Token token = tokenStream.getToken();
            tokenStream.nextToken();
            return new BinaryExpr(expr, token.type(), logicalOr(scope));
        }
        return expr;
    }

    private Expr logicalAnd(Scope scope) throws IOException {

        Expr expr = equality(scope);
        if(tokenStream.check(Token.Type.AND)){
            Token token = tokenStream.getToken();
            tokenStream.nextToken();
            return new BinaryExpr(expr, token.type(), logicalAnd(scope));
        }
        return expr;
    }

    private Expr equality(Scope scope) throws IOException {

        Expr expr = comparison(scope);
        if(tokenStream.check(Token.Type.EQ) || tokenStream.check(Token.Type.NOTEQ)){
            Token token = tokenStream.getToken();
            tokenStream.nextToken();
            return new BinaryExpr(expr, token.type(), comparison(scope));
        }
        return expr;
    }

    private Expr comparison(Scope scope) throws IOException {

        Expr expr = add(scope);
        if(tokenStream.check(Token.Type.MAJ)
                || tokenStream.check(Token.Type.MAJEQ)
                || tokenStream.check(Token.Type.MIN)
                || tokenStream.check(Token.Type.MINEQ)){
            Token token = tokenStream.getToken();
            tokenStream.nextToken();
            return new BinaryExpr(expr, token.type(), add(scope));
        }
        return expr;
    }

    private Expr add(Scope scope) throws IOException {

        Expr expr = mult(scope);
        while(tokenStream.check(Token.Type.SUM) || tokenStream.check(Token.Type.SUB)){
            Token token = tokenStream.getToken();
            tokenStream.nextToken();
            expr = new BinaryExpr(expr, token.type(), mult(scope));
        }
        return expr;
    }

    private Expr mult(Scope scope) throws IOException {

        Expr expr = unary(scope);
        while(tokenStream.check(Token.Type.MULT)
                || tokenStream.check(Token.Type.DIV)
                || tokenStream.check(Token.Type.MOD)){
            Token token = tokenStream.getToken();
            tokenStream.nextToken();
            expr = new BinaryExpr(expr, token.type(), unary(scope));
        }
        return expr;
    }

    private Expr unary(Scope scope) throws IOException {

        if(tokenStream.check(Token.Type.SUM)
                || tokenStream.check(Token.Type.SUB)
                || tokenStream.check(Token.Type.NOT)){
            Token token = tokenStream.getToken();
            tokenStream.nextToken();
            return new UnaryExpr(token.type(), unary(scope));
        }
        return postFix(scope);
    }

    private Expr postFix(Scope scope) throws IOException {

        Expr expr = primary(scope);
        ArrayList<Expr> listArgs = new ArrayList<>();

        while (tokenStream.check(Token.Type.OPN_RND_BRACKET)) {
            tokenStream.nextToken();
            Expr seqExpr = sequence(scope);
            listArgs.add(seqExpr);
            while (tokenStream.check(Token.Type.COMMA)) {
                tokenStream.nextToken();
                listArgs.add(sequence(scope));
            }
            if(tokenStream.check(Token.Type.CLS_RND_BRACKET)){
                return new InvokeExpr(expr, new ExprList(listArgs));
            }
            else{
                throw new RuntimeException("Wrong token");
            }
        }

        return expr;

    }

    private Expr primary(Scope scope) {
        return Nil;
    }


}

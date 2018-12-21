package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.exceptions.InvalidTokenException;
import ch.supsi.dti.i3b.husky.ifunny.expressions.*;
import ch.supsi.dti.i3b.husky.ifunny.values.*;

import java.io.IOException;
import java.util.ArrayList;

import static ch.supsi.dti.i3b.husky.ifunny.values.BoolVal.False;
import static ch.supsi.dti.i3b.husky.ifunny.values.BoolVal.True;
import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;

public class Parser {

    private Tokenizer tokenStream;

    Parser(String filePath) throws IOException {
        this.tokenStream = new Tokenizer(filePath);
    }

    Parser(Tokenizer tokenStream){
        this.tokenStream = tokenStream;
    }

    public FunExpr parse() throws IOException {

        FunExpr fFunc = function(new Scope());

        if(tokenStream.check(Token.Type.EOS)){
            return fFunc;
        }
        else{
            throw new InvalidTokenException(tokenStream.getToken());
        }
    }

    private FunExpr function(Scope scope) throws IOException {
        if (tokenStream.check(Token.Type.OPN_CRLY_BRKT)) {
            tokenStream.nextToken();
            ArrayList<String> params = optParams();
            ArrayList<String> locals = optLocals();

            Expr seqExpr = optSequence(new Scope(scope, params, locals));
            if (tokenStream.check(Token.Type.CLS_CRLY_BRKT)) {
                tokenStream.nextToken();
                return new FunExpr(params, locals, seqExpr);
            }
            else {
                throw new InvalidTokenException(tokenStream.getToken());
            }
        }
        else {
            throw new InvalidTokenException(tokenStream.getToken());
        }
    }

    private ArrayList<String> optParams() throws IOException {
        ArrayList<String> listParams = new ArrayList<>();
        if(tokenStream.check(Token.Type.OPN_RND_BRKT)) {
            tokenStream.nextToken();
            listParams = optIds();
            if(!tokenStream.check(Token.Type.CLS_RND_BRKT)) {
                throw new InvalidTokenException(tokenStream.getToken());
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
            Expr seq = sequence(scope);
            return seq == null ? Nil : seq;
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
        return listAssignment.size() == 0 ? null : (listAssignment.size() == 1 ?
                listAssignment.get(0) : new SequenceExpr(listAssignment));
    }

    private Expr optAssignment(Scope scope) throws IOException {
        if (isStartOfAssignm()){
            return assignment(scope);
        }
        else{
            return null;
        }
    }

    private boolean isStartOfAssignm() {
        switch (tokenStream.getToken().type()){
            case ID:
            case NUM:
            case NIL:
            case STRING:
            case TRUE:
            case FALSE:
            case OPN_CRLY_BRKT:
            case OPN_RND_BRKT:
            case IF:
            case IFNOT:
            case WHILE:
            case WHILENOT:
            case PRINT:
            case PRINTLN:
            case SUM:
            case SUB:
            case NOT:
                return true;
            default:
                return false;
        }
    }

    private Expr assignment(Scope scope) throws IOException {

        if(tokenStream.check(Token.Type.ID)) {
            String id = tokenStream.getToken().getStr();
            if(!scope.containsId(id)){
                throw new RuntimeException("Id \""+id+"\" not in the scope");
            }
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
        while(tokenStream.check(Token.Type.OPN_RND_BRKT)) {
            ArrayList<Expr> args = args(scope);
            expr = new InvokeExpr(expr, new ExprList(args));
        }

        return expr;

    }

    private Expr primary(Scope scope) throws IOException {
        if (tokenStream.check(Token.Type.NUM)) {
            Val val = new NumVal(tokenStream.getToken().getNum());
            tokenStream.nextToken();
            return val;

        } else if (tokenStream.check(Token.Type.TRUE)) {
            tokenStream.nextToken();
            return True;

        } else if (tokenStream.check(Token.Type.FALSE)) {
            tokenStream.nextToken();
            return False;

        } else if (tokenStream.check(Token.Type.NIL)) {
            tokenStream.nextToken();
            return new NilVal();

        } else if (tokenStream.check(Token.Type.STRING)) {
            String str = tokenStream.getToken().getStr();
            tokenStream.nextToken();
            return new StringVal(str);

        } else if (tokenStream.check(Token.Type.ID)) {
            String str = tokenStream.getToken().getStr();
            tokenStream.nextToken();
            return new GetVarExpr(str);

        } else if (tokenStream.check(Token.Type.OPN_CRLY_BRKT)) {
            return function(scope);

        } else if (tokenStream.check(Token.Type.OPN_RND_BRKT)) {
            tokenStream.nextToken();
            return subSequence(scope);

        } else if (tokenStream.check(Token.Type.IF) || tokenStream.check(Token.Type.IFNOT)) {
            return cond(scope);

        } else if (tokenStream.check(Token.Type.WHILE) || tokenStream.check(Token.Type.WHILENOT)) {
            return loop(scope);

        } else if (tokenStream.check(Token.Type.PRINT) || tokenStream.check(Token.Type.PRINTLN)) {
            return print(scope);

        } else {
            throw new InvalidTokenException(tokenStream.getToken());
        }
    }

    private Expr subSequence(Scope scope) throws IOException {
        Expr expr = sequence(scope);
        if(tokenStream.check(Token.Type.CLS_RND_BRKT)){
            tokenStream.nextToken();
            return expr;
        }
        else{
            throw new InvalidTokenException(tokenStream.getToken());
        }
    }

    private Expr not(Expr expr){
        return new UnaryExpr(Token.Type.NOT, expr);
    }

    private Expr cond(Scope scope) throws IOException {
        Expr exprBody;
        Expr exprElse = Nil;
        Token.Type ifcond = tokenStream.getToken().type();
        tokenStream.nextToken();
        Expr exprEval = sequence(scope);
        exprEval = (ifcond == Token.Type.IFNOT ? not(exprEval) : exprEval);
        if(tokenStream.check(Token.Type.THEN)){
            tokenStream.nextToken();
            exprBody = sequence(scope);
            if(tokenStream.check(Token.Type.ELSE)){
                tokenStream.nextToken();
                exprElse = sequence(scope);
            }
            if(tokenStream.check(Token.Type.FI)){
                tokenStream.nextToken();
                return new IfExpr(exprEval, exprBody, exprElse);
            }
            else{
                throw new InvalidTokenException(tokenStream.getToken());
            }

        }
        else{
            throw new InvalidTokenException(tokenStream.getToken());
        }

    }

    private Expr loop(Scope scope) throws IOException {
        Expr exprBody = Nil;
        Token.Type ifcond = tokenStream.getToken().type();
        tokenStream.nextToken();
        Expr exprEval = sequence(scope);
        exprEval = (ifcond == Token.Type.WHILENOT ? not(exprEval) : exprEval);
        if(tokenStream.check(Token.Type.DO)){
            tokenStream.nextToken();
            exprBody = sequence(scope);
            if(!tokenStream.check(Token.Type.OD)){
                throw new InvalidTokenException(tokenStream.getToken());
            }
            tokenStream.nextToken();
        }
        return new WhileExpr(exprEval, exprBody);
    }

    private ArrayList<Expr> args(Scope scope) throws IOException {
        ArrayList<Expr> args = new ArrayList<>();
        if(tokenStream.check(Token.Type.OPN_RND_BRKT)){
            tokenStream.nextToken();
            if(!tokenStream.check(Token.Type.CLS_RND_BRKT)){
                args.add(sequence(scope));
                while(tokenStream.check(Token.Type.COMMA)){
                    tokenStream.nextToken();
                    args.add(sequence(scope));
                }

                if(!tokenStream.check(Token.Type.CLS_RND_BRKT)){
                    throw new InvalidTokenException(tokenStream.getToken());
                }
            }
            tokenStream.nextToken();
        } else{
            throw new InvalidTokenException(tokenStream.getToken());
        }
        return args;
    }

    private Expr print(Scope scope) throws IOException {
        Token.Type printcond = tokenStream.getToken().type();
        tokenStream.nextToken();
        ArrayList<Expr> listArgs = args(scope);
        return(printcond == Token.Type.PRINTLN ? new PrintExpr(listArgs,true) : new PrintExpr(listArgs));
    }


}

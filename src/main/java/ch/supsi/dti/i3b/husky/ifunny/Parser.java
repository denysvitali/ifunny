package ch.supsi.dti.i3b.husky.ifunny;

import java.io.IOException;

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

    private FunnyFunction function() throws IOException {
        if (tokenStream.check(Token.Type.OPNCRLYBRACKET)) {
            tokenStream.nextToken();
            FunnyParameter fParam = optParams();
            FunnyLocals fLoc = optLocals();
            FunnySequence fSeq = optSequence();
            if (tokenStream.check(Token.Type.CLSCRLYBRACKET)) {
                return new FunnyFunction(fParam, fLoc, fSeq);
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

    private FunnyParameter optParams() {
        while(tokenStream.check(Token.Type.OPNRNBRACKET)) {

        }
    }
}

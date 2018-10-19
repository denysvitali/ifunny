package ch.supsi.dti.i3b.husky.ifunny;

import java.io.IOException;
import java.io.Reader;

public class Tokenizer {
    Token token;
    private Reader input;
    private int carattereAttuale;
    private StringBuilder stringBuilder;

    Tokenizer(Reader input) throws IOException {
        this.input = input;
        stringBuilder = new StringBuilder();
        next();
    }

    public int peek() throws IOException {

        input.mark(1);
        int c = input.read();
        input.reset();
        return c;
    }

    public void next() throws IOException {

        carattereAttuale = input.read();
        while(Character.isWhitespace(carattereAttuale)) carattereAttuale = input.read();

        switch (carattereAttuale) {
            case '{':
                token = new Token(Token.Type.OPNCRLYBRACKETS);
                break;
            case '}':
                token = new Token(Token.Type.CLSCRLYBRACKETS);
                break;
            case ',':
                token = new Token(Token.Type.COMMA);
                break;
            case ';':
                token = new Token(Token.Type.SEMICOLON);
                break;
            case '(':
                token = new Token(Token.Type.OPNRNBRACKETS);
                break;
            case ')':
                token = new Token(Token.Type.CLSRNBRACKETS);
                break;
            case '<':
                //TODO: implement peek check
                break;
            case '>':
                //TODO: implement peek check
                break;
            case '*':
                //TODO: implement peek check
                break;
            case '/':
                //TODO: implement peek check
                break;
            case '%':
                //TODO: implement peek check
                break;
            case '+':
                //TODO: implement peek check
                break;
            case '-':
                //TODO: implement peek check
                break;
            case '=':
                //TODO: implement peek check
                break;
            case '!':
                //TODO: implement peek check
                break;
            case '&':
                //TODO: implement peek check
                break;
            case '|':
                //TODO: implement peek check
                break;
            case '"':
                //TODO: implement read string
                readStr();
                break;
            case -1:
                token = new Token(Token.Type.EOS);
                break;
            default:
                //TODO: implement check for id, num, unknow
                if(!readNum())
                    token = new Token();
                break;
        }

    }

    private void readStr() throws IOException{
        carattereAttuale = input.read();
        while(carattereAttuale != '"' && carattereAttuale != -1){
            stringBuilder.append(carattereAttuale);
            carattereAttuale = input.read();
        }
        if(carattereAttuale != -1){
            token = new Token(stringBuilder.toString());
            stringBuilder.setLength(0);
        }
    }
}

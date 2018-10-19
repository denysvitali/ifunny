package ch.supsi.dti.i3b.husky.ifunny;

import java.io.IOException;
import java.io.Reader;

public class Tokenizer {
    Token token;
    private Reader input;
    private int currentChar;
    private StringBuilder stringBuilder;

    Tokenizer(Reader input) throws IOException {
        this.input = input;
        next();
    }

    public int peek() throws IOException {

        input.mark(1);
        int c = input.read();
        input.reset();
        return c;
    }

    private static boolean isNumber(int c){
        return c >= '0' && c <= '9';
    }

    public void next() throws IOException {

        currentChar = input.read();
        while(Character.isWhitespace(currentChar)) currentChar = input.read();
        if(stringBuilder == null) {
            switch (currentChar) {
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
                    break;
                case -1:
                    token = new Token(Token.Type.EOS);
                    break;
                default:
                    break;
            }
        }
    }
}

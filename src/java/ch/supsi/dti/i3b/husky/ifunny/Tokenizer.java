package ch.supsi.dti.i3b.husky.ifunny;

import java.io.IOException;
import java.io.Reader;

public class Tokenizer {
    Token token;
    private Reader r;
    private int currentChar;
    private StringBuilder stringBuilder = new StringBuilder();

    Tokenizer(Reader input) throws IOException {
        this.r = input;
        next();
    }

    public int peek() throws IOException {

        r.mark(1);
        int c = r.read();
        r.reset();
        return c;
    }

    private static boolean isNumber(int c){
        return c >= '0' && c <= '9';
    }

    public void next() throws IOException {

        currentChar = r.read();
        while(Character.isWhitespace(currentChar)){
            currentChar = r.read();
        }

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
                readStr();
                break;
            case -1:
                token = new Token(Token.Type.EOS);
                break;
            default:
                // Number Checking
                stringBuilder.setLength(0);
                while(isNumber(currentChar)){
                    stringBuilder.append(currentChar);
                    currentChar = r.read();
                }

                if(stringBuilder.length() == 0){
                    token = new Token(Integer.valueOf(stringBuilder.toString()));
                    break;
                }
                break;
        }
    }

	private void readStr() throws IOException{
		currentChar = r.read();
		while(currentChar != '"' && currentChar != -1){
			stringBuilder.append(currentChar);
			currentChar = r.read();
		}
		if(currentChar != -1){
			token = new Token(stringBuilder.toString());
			stringBuilder.setLength(0);
		}
	}
}

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
        nextToken();
    }

    private int peekChar() throws IOException {

        r.mark(1);
        int c = r.read();
        r.reset();
        return c;
    }

    private static boolean isNumber(int c){
        return c >= '0' && c <= '9';
    }

    public void nextToken() throws IOException {

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
                if(peekChar() == '=') {
                    currentChar = r.read();
                    token = new Token(Token.Type.MINEQ);
                }
                else{
                    token = new Token(Token.Type.MIN);
                }
                break;
            case '>':
                if(peekChar() == '=') {
                    currentChar = r.read();
                    token = new Token(Token.Type.MAJEQ);
                }
                else{
                    token = new Token(Token.Type.MAJ);
                }
                break;
            case '*':
                if(peekChar() == '=') {
                    currentChar = r.read();
                    token = new Token(Token.Type.ASSIGNMULT);
                }
                else{
                    token = new Token(Token.Type.MULT);
                }
                break;
            case '/':
                if(peekChar() == '=') {
                    currentChar = r.read();
                    token = new Token(Token.Type.ASSIGNMDIV);
                }
                else if(peekChar() == '/'){
                    currentChar = r.read();
                    //TODO: create commentLine function (this should recall next after is done)
                    commentLine();
                }
                else if(peekChar() == '*'){
                    currentChar = r.read();
                    //TODO: create commentBlock function (this should recall next after is done)
                    commentBlock();
                }
                else{
                    token = new Token(Token.Type.DIV);
                }
                break;
            case '%':
                if(peekChar() == '=') {
                    currentChar = r.read();
                    token = new Token(Token.Type.ASSIGNMOD);
                }
                else{
                    token = new Token(Token.Type.MOD);
                }
                break;
            case '+':
                if(peekChar() == '=') {
                    currentChar = r.read();
                    token = new Token(Token.Type.ASSIGNMSUM);
                }
                else{
                    token = new Token(Token.Type.SUM);
                }
                break;
            case '-':
                if(peekChar() == '=') {
                    currentChar = r.read();
                    token = new Token(Token.Type.ASSIGNMSUB);
                }
                else{
                    token = new Token(Token.Type.SUB);
                }
                break;
            case '=':
                if(peekChar() == '=') {
                    currentChar = r.read();
                    token = new Token(Token.Type.EQ);
                }
                else{
                    token = new Token(Token.Type.ASSIGNM);
                }
                break;
            case '!':
                if(peekChar() == '=') {
                    currentChar = r.read();
                    token = new Token(Token.Type.NOTEQ);
                }
                else{
                    token = new Token(Token.Type.NOT);
                }
                break;
            case '&':
                if(peekChar() == '&') {
                    currentChar = r.read();
                    token = new Token(Token.Type.AND);
                }
                else{
                    //TODO: not a valid token exception
                    token = new Token(Token.Type.UNKNOW);
                }
                break;
            case '|':
                if(peekChar() == '|') {
                    currentChar = r.read();
                    token = new Token(Token.Type.OR);
                }
                else{
                    //TODO: not a valid token exception
                    token = new Token(Token.Type.UNKNOW);
                }
                break;
            case '"':
                readStr();
                break;
            case -1:
                token = new Token(Token.Type.EOS);
                break;
            default:
                token = new Token(Token.Type.UNKNOW);
                readNum();
                idOrKeyword();
                break;
        }
    }

    public Token getNextToken() throws IOException {
        nextToken();
        return token;
    }

    private void idOrKeyword() throws IOException{
        if(!Character.isJavaIdentifierStart(currentChar)){
            return;
        }
        stringBuilder.setLength(0);
        while(Character.isJavaIdentifierPart(currentChar)){
            stringBuilder.append(currentChar);
            currentChar = r.read();

            //TODO: hashmap con controllo keyword (utilizzando peek)
        }
        if(stringBuilder.length() != 0){
            token = new Token(Token.Type.ID, stringBuilder.toString());
        }
    }
    private void readNum() throws IOException{
        // Number Checking
        stringBuilder.setLength(0);
        while(isNumber(currentChar)){
            stringBuilder.append(currentChar);
            currentChar = r.read();
        }
        if(stringBuilder.length() != 0){
            token = new Token(Integer.valueOf(stringBuilder.toString()));
        }
    }
	private void readStr() throws IOException{
        stringBuilder.setLength(0);
		currentChar = r.read();
		while(currentChar != '"' && currentChar != -1){
			stringBuilder.append(currentChar);
			currentChar = r.read();
		}
		if(currentChar != -1){
			token = new Token(stringBuilder.toString());
		}
	}
}

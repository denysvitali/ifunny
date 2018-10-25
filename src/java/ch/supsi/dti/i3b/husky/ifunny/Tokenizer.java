package ch.supsi.dti.i3b.husky.ifunny;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class Tokenizer {
    Token token;
    private Reader r;
    private int currentChar;
    private StringBuilder stringBuilder = new StringBuilder();
    private Map<String, Token.Type> mapKeyWord = new HashMap<>();

    Tokenizer(Reader input) throws IOException {
        this.r = input;
        nextToken();

        mapKeyWord.put("while",Token.Type.WHILE);
        mapKeyWord.put("if",Token.Type.IF);
        mapKeyWord.put("fi",Token.Type.FI);
        mapKeyWord.put("else",Token.Type.ELSE);
        mapKeyWord.put("whilenot",Token.Type.WHILENOT);
        mapKeyWord.put("ifnot",Token.Type.IFNOT);
        mapKeyWord.put("print",Token.Type.PRINT);
        mapKeyWord.put("println",Token.Type.PRINTLN);
        mapKeyWord.put("do",Token.Type.DO);
        mapKeyWord.put("od",Token.Type.OD);
        mapKeyWord.put("true",Token.Type.TRUE);
        mapKeyWord.put("false",Token.Type.FALSE);
        mapKeyWord.put("nil",Token.Type.NIL);
        mapKeyWord.put("then",Token.Type.THEN);
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
                token = new Token(Token.Type.OPNCRLYBRACKET);
                break;
            case '}':
                token = new Token(Token.Type.CLSCRLYBRACKET);
                break;
            case ',':
                token = new Token(Token.Type.COMMA);
                break;
            case ';':
                token = new Token(Token.Type.SEMICOLON);
                break;
            case '(':
                token = new Token(Token.Type.OPNRNBRACKET);
                break;
            case ')':
                token = new Token(Token.Type.CLSRNBRACKET);
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
                else if(peekChar() == '>'){
                    currentChar = r.read();
                    token = new Token(Token.Type.ARROW);
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

    private void commentLine() throws IOException {


        nextToken();
    }

    private void commentBlock() throws IOException {


        nextToken();
    }

    private void idOrKeyword() throws IOException{
        if(!Character.isJavaIdentifierStart(currentChar)){
            return;
        }
        stringBuilder.setLength(0);
        while(Character.isJavaIdentifierPart(currentChar)){
            stringBuilder.append(currentChar);
            currentChar = r.read();

            if(mapKeyWord.containsKey(stringBuilder.toString())){
                if(!Character.isJavaIdentifierPart(peekChar())){
                    token = new Token(mapKeyWord.get(stringBuilder.toString()));
                    return;
                }
            }
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

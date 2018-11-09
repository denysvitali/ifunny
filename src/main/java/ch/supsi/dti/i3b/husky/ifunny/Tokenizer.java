package ch.supsi.dti.i3b.husky.ifunny;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

class Tokenizer {
	private Token token;
	private Reader r;
	private int currentChar;
	private StringBuilder stringBuilder = new StringBuilder();
	private Map<String, Token.Type> mapKeyWord = new HashMap<>();

	Tokenizer(Reader input) throws IOException {
		this.r = input;
		mapKeyWord.put("while", Token.Type.WHILE);
		mapKeyWord.put("if", Token.Type.IF);
		mapKeyWord.put("fi", Token.Type.FI);
		mapKeyWord.put("else", Token.Type.ELSE);
		mapKeyWord.put("whilenot", Token.Type.WHILENOT);
		mapKeyWord.put("ifnot", Token.Type.IFNOT);
		mapKeyWord.put("print", Token.Type.PRINT);
		mapKeyWord.put("println", Token.Type.PRINTLN);
		mapKeyWord.put("do", Token.Type.DO);
		mapKeyWord.put("od", Token.Type.OD);
		mapKeyWord.put("true", Token.Type.TRUE);
		mapKeyWord.put("false", Token.Type.FALSE);
		mapKeyWord.put("nil", Token.Type.NIL);
		mapKeyWord.put("then", Token.Type.THEN);


		nextToken();
	}


	private int peekChar() throws IOException {

		r.mark(1);
		int c = r.read();
		r.reset();
		return c;
	}

	private static boolean isNumber(int c) {
		return c >= '0' && c <= '9';
	}

	public void nextToken() throws IOException {

		currentChar = r.read();
		while (Character.isWhitespace(currentChar)) {
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
				if (peekChar() == '=') {
					currentChar = r.read();
					token = new Token(Token.Type.MINEQ);
				} else {
					token = new Token(Token.Type.MIN);
				}
				break;
			case '>':
				if (peekChar() == '=') {
					currentChar = r.read();
					token = new Token(Token.Type.MAJEQ);
				} else {
					token = new Token(Token.Type.MAJ);
				}
				break;
			case '*':
				if (peekChar() == '=') {
					currentChar = r.read();
					token = new Token(Token.Type.ASSIGNMULT);
				} else {
					token = new Token(Token.Type.MULT);
				}
				break;
			case '/':
				if (peekChar() == '=') {
					currentChar = r.read();
					token = new Token(Token.Type.ASSIGNMDIV);
				} else if (peekChar() == '/') {
					currentChar = r.read();
					commentLine();
				} else if (peekChar() == '*') {
					currentChar = r.read();
					commentBlock();
				} else {
					token = new Token(Token.Type.DIV);
				}
				break;
			case '%':
				if (peekChar() == '=') {
					currentChar = r.read();
					token = new Token(Token.Type.ASSIGNMOD);
				} else {
					token = new Token(Token.Type.MOD);
				}
				break;
			case '+':
				if (peekChar() == '=') {
					currentChar = r.read();
					token = new Token(Token.Type.ASSIGNMSUM);
				} else if (isNumber(peekChar())) {
					readNum();
				} else {
					token = new Token(Token.Type.SUM);
				}
				break;
			case '-':
				if (peekChar() == '=') {
					currentChar = r.read();
					token = new Token(Token.Type.ASSIGNMSUB);
				} else if (peekChar() == '>') {
					currentChar = r.read();
					token = new Token(Token.Type.ARROW);
				} else if (isNumber(peekChar())) {
					readNum();
				} else {
					token = new Token(Token.Type.SUB);
				}
				break;
			case '=':
				if (peekChar() == '=') {
					currentChar = r.read();
					token = new Token(Token.Type.EQ);
				} else {
					token = new Token(Token.Type.ASSIGNM);
				}
				break;
			case '!':
				if (peekChar() == '=') {
					currentChar = r.read();
					token = new Token(Token.Type.NOTEQ);
				} else {
					token = new Token(Token.Type.NOT);
				}
				break;
			case '&':
				if (peekChar() == '&') {
					currentChar = r.read();
					token = new Token(Token.Type.AND);
				} else {
					//TODO: not a valid token exception
					token = new Token(Token.Type.UNKNOW);
				}
				break;
			case '|':
				if (peekChar() == '|') {
					currentChar = r.read();
					token = new Token(Token.Type.OR);
				} else {
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

	public Token getToken(){
		return token;
	}
	public Token getNextToken() throws IOException {
		nextToken();
		return token;
	}

	private void commentLine() throws IOException {
		while (currentChar != '\n' && currentChar != -1) {
			currentChar = r.read();
		}
		nextToken();
	}

	private void commentBlock() throws IOException {
		do {
			while (currentChar != '*') {
				currentChar = r.read();
			}
			currentChar = r.read();
		} while (currentChar != '/');
		nextToken();
	}

	private void idOrKeyword() throws IOException {
		if (!Character.isJavaIdentifierStart(currentChar)) {
			return;
		}
		stringBuilder.setLength(0);
		while (Character.isJavaIdentifierPart(currentChar)) {
			stringBuilder.append(currentChar);
			currentChar = r.read();

			if (mapKeyWord.containsKey(stringBuilder.toString())) {
				if (!Character.isJavaIdentifierPart(peekChar())) {
					token = new Token(mapKeyWord.get(stringBuilder.toString()));
					return;
				}
			}
		}
		if (stringBuilder.length() != 0) {
			token = new Token(Token.Type.ID, stringBuilder.toString());
		}
	}

	private void readNum() throws IOException {
		// Number Checking

        /* Possible Values:
            1
            1.0
            -1.0
            1E-10
            1E10
            1.0E-10
            1.0E+10
         */

		stringBuilder.setLength(0);
		if (isSign(currentChar)) {
			stringBuilder.append((char) currentChar);
			currentChar = r.read();
		}

		while (isNumber(currentChar)) {
			stringBuilder.append(currentChar - '0');
			currentChar = r.read();
		}

		if (isNumericComma(currentChar)) {
			currentChar = r.read();
			stringBuilder.append(".");
			while (isNumber(currentChar)) {
				stringBuilder.append(currentChar - '0');
				currentChar = r.read();
			}
		}

		r.mark(1);

		// EXP
		if (currentChar == 'E' || currentChar == 'e') {
			stringBuilder.append((char) currentChar);
			currentChar = r.read();
			if (isSign(currentChar)) {
				stringBuilder.append((char) currentChar);
				currentChar = r.read();
				while (isNumber(currentChar)) {
					stringBuilder.append(currentChar - '0');
					currentChar = r.read();
				}
			} else {
				r.reset();
			}
		} else {
			r.reset();
		}


		if (stringBuilder.length() != 0) {
			token = new Token(new BigDecimal(stringBuilder.toString()));
		}
	}

	private boolean isSign(int currentChar) {
		return currentChar == '+' || currentChar == '-';
	}

	private boolean isNumericComma(int currentChar) {
		return currentChar == '.';
	}

	private void readStr() throws IOException {
		stringBuilder.setLength(0);
		currentChar = r.read();
		while (currentChar != '"' && currentChar != -1) {
			stringBuilder.append((char) currentChar);
			currentChar = r.read();
		}
		if (currentChar != -1) {
			token = new Token(stringBuilder.toString());
		}
	}

    public boolean check(Token.Type tipo){
        return token.type() == tipo;
    }
}

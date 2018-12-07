package ch.supsi.dti.i3b.husky.ifunny.values;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringVal extends Val {
	private String val;

	public StringVal(String s){
		this.val = s;
	}

	public static String unescape(String val){
		// TODO: Backtick syntax (Java 12?)
		Pattern p = Pattern.compile("\\\\(.)", Pattern.MULTILINE);
		Matcher m = p.matcher(val);

		int offset = 0;

		while(m.find()){
			int start = offset + m.start();
			int end = offset + m.end();

			String v = m.group(1);

			StringBuilder sb = new StringBuilder();
			sb.append(val, 0, start);
			switch(v.charAt(0)){
				case 'n':
					sb.append("\n");
					break;
				case 'r':
					sb.append("\r");
					break;
				case 'u':
					// Unicode
					sb.append("\\u");
					break;
				case '\\':
					sb.append("\\");
					break;
				default:
					sb.append(m.group());
			}
			offset += start - sb.length();
			sb.append(val.substring(end));
			val = sb.toString();
		}

		return val;
	}

	@Override
	public String getValue() {
		return unescape(val);
	}

	@Override
	public Val sub(Val rval) {
		return null;
	}

	@Override
	public Val sum(Val rval) {
		return null;
	}

	@Override
	public Val div(Val rval) {
		return null;
	}

	@Override
	public Val mult(Val rval) {
		return null;
	}

	@Override
	public Val maj(Val rval) {
		return null;
	}

	@Override
	public Val min(Val rval) {
		return null;
	}

	@Override
	public Val majeq(Val rval) {
		return null;
	}

	@Override
	public Val mineq(Val rval) {
		return null;
	}

	@Override
	public Val or(Val rval) {
		return null;
	}

	@Override
	public Val and(Val rval) {
		return null;
	}

	@Override
	public boolean isString() {
		return true;
	}

	@Override
	public StringVal string() {
		return this;
	}
}

package ch.supsi.dti.i3b.husky.ifunny.values;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ch.supsi.dti.i3b.husky.ifunny.values.BoolVal.False;
import static ch.supsi.dti.i3b.husky.ifunny.values.BoolVal.True;
import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;

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
	public Val eq(Val rval) {
		if(!rval.isString()){
			return False;
		}

		return (val.equals(rval.string().val) ? True : False);
	}

	@Override
	public Val sum(Val rval) {
		if(rval.isNum()){
			return new StringVal(val + rval.num());
		}

		if(rval.isString()){
			return new StringVal(val + rval.string().val);
		}

		return Nil;
	}

	@Override
	public boolean isString() {
		return true;
	}

	@Override
	public StringVal string() {
		return this;
	}

	@Override
	public String toString() {
		return this.val;
	}
}

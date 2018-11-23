package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.values.StringVal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringValTest {
	@Test
	public void unescapeTest1(){
		assertEquals("\n", StringVal.unescape("\\n"));
		assertEquals("\n\n", StringVal.unescape("\\n\\n"));
		assertEquals("\n\n\\", StringVal.unescape("\\n\\n\\\\"));
	}
}

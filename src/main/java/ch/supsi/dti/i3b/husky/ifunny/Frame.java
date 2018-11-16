package ch.supsi.dti.i3b.husky.ifunny;

import java.util.HashMap;
import java.util.List;

import static ch.supsi.dti.i3b.husky.ifunny.values.NilVal.Nil;

public class Frame {
	private HashMap<String, Val> bindings = new HashMap<>();

	public Frame(List<String> params, List<String> locals, List<Val> argVals) {
		for(int i=0; i<= params.size(); i++){
			bindings.put(params.get(i), argVals.get(i));
		}

		for(int i=0; i<=locals.size(); i++){
			bindings.put(locals.get(i), Nil);
		}
	}

	public Val getVal(String var){
		return bindings.get(var);
	}

	public boolean contains(String var){
		return bindings.containsKey(var);
	}

	public void setVal(String var, Val value){
		bindings.put(var, value);
	}
}

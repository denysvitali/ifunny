package ch.supsi.dti.i3b.husky.ifunny;

import java.util.HashMap;

public class Env {
	private Env parentEnv;
	private HashMap<String, Val> variables;

	public Env(Env parentEnv){
		this.parentEnv = parentEnv;
	}

	private boolean hasId(String id){
		return variables.containsKey(id);
	}

	private Env getParent(){
		return parentEnv;
	}

	Val getVal(String id){
		if(hasId(id)){
			return variables.get(id);
		}

		Env parent = parentEnv;
		while(parent != null){
			if(parent.hasId(id)){
				return parent.getVal(id);
			}
			parent = parent.getParent();
		}

		throw new RuntimeException(String.format("Variable \"%s\" not found.", id));
	}

	public Env getParentEnv() {
		return parentEnv;
	}
}

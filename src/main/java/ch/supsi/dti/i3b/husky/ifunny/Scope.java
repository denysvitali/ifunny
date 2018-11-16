package ch.supsi.dti.i3b.husky.ifunny;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Scope {
	private List<String> vars = new ArrayList<>();
	private Scope parent;

	Scope(){

	}
	public Scope(Scope parent, ArrayList<String> param, ArrayList<String> locals) {
		this.parent = parent;
		vars = param;
		vars.addAll(locals);
		if(containsDuplicates()){
			throw new RuntimeException("Scope: Duplicates Found");
		}
	}

	private boolean containsDuplicates(){
		return (new HashSet<>(vars).size() < vars.size());
	}
}

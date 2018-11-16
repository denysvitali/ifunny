package ch.supsi.dti.i3b.husky.ifunny;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Scope {
	private List<String> vars = new ArrayList<>();
	private Scope parent;

	Scope(){

	}
	public Scope(Scope parent, ArrayList<String> params, ArrayList<String> locals) {
		this.parent = parent;
		vars = params;
		vars.addAll(locals);
		if(containsDuplicates()){
			throw new RuntimeException("Scope: Duplicates Found");
		}
	}
	public Scope(Scope parent, String param, String local) {
		this.parent = parent;
		vars.add(param);
		vars.add(local);
		if(param == local){
			throw new RuntimeException("Scope: Duplicates Found");
		}
	}

	private boolean containsDuplicates(){
		return (new HashSet<>(vars).size() < vars.size());
	}

	public boolean containsId(String id){
		if(parent == null){
			return false;
		}
		if(vars.contains(id)){
			return true;
		}
		else{
			return parent.containsId(id);
		}
	}
}

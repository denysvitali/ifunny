package ch.supsi.dti.i3b.husky.ifunny;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Scope {
	private List<String> vars = new ArrayList<>();

	public void addVar(String id){
		vars.add(id);
		if(containsDuplicates()){
			throw new RuntimeException("Scope: I can't add an already existing variable to the scope!");
		}
	}

	public boolean containsDuplicates(){
		return (new HashSet<>(vars).size() < vars.size());
	}
}

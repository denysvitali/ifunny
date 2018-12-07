package ch.supsi.dti.i3b.husky.ifunny;

import ch.supsi.dti.i3b.husky.ifunny.exceptions.FunnyRuntimeException;
import ch.supsi.dti.i3b.husky.ifunny.values.Val;

import java.util.ArrayList;

public class Env {
	private Env parentEnv;
	private Frame frame;

	public Env(Env env, Frame frame){
		this.parentEnv = env;
		this.frame = frame;
	}

	public Env(){
		this.frame = new Frame(new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>());
	}

	public Env(Env parentEnv){
		this.parentEnv = parentEnv;
	}

	private boolean hasId(String id){
		return frame.contains(id);
	}

	private Env getParent(){
		return parentEnv;
	}

	public Val getVal(String id){
		if(hasId(id)){
			return frame.getVal(id);
		}

		Env parent = parentEnv;
		while(parent != null){
			if(parent.hasId(id)){
				return parent.getVal(id);
			}
			parent = parent.getParent();
		}

		throw new FunnyRuntimeException(String.format("Variable \"%s\" not found.", id));
	}

	public Val addVal(String id, Val val){
		if(hasId(id)){
			throw new FunnyRuntimeException(String.format("Variable \"%s\" cannot be reassigned!",
					id));
		}

		frame.setVal(id, val);
		return val;
	}

	public Val setVal(String id, Val val){
		if(hasId(id)){
			frame.setVal(id, val);
			return val;
		}

		Env parent = parentEnv;
		while(parent != null){
			if(parent.hasId(id)){
				return parent.setVal(id, val);
			}
			parent = parent.getParent();
		}

		throw new FunnyRuntimeException(String.format("Variable \"%s\" not found.", id));
	}

	@Override
	public String toString() {
		return (parentEnv != null ? parentEnv.toString() + "\n" : "") +
				frame.toString();
	}
}

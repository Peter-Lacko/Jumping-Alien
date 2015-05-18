package jumpingalien.model.program.type;

public class Bool extends Type<Boolean> {
	
	public Bool(boolean value){
		super(value);
	}

	@Override
	public String toString(){
		return getValue().toString();
	}
	
}

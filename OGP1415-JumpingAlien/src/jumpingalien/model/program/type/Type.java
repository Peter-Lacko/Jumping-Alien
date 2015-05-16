package jumpingalien.model.program.type;

public abstract class Type<T>{
	
	enum TypeEnum{
		
	}
	
	protected Type(T value){
		this.value = value;
	}
	
	public T getValue(){
		return this.value;
	}
	
	private T value;
	
}

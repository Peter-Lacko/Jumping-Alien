package jumpingalien.model.program.expression;

import jumpingalien.model.program.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public class ExpressionBasic<T extends Type<?>> extends Expression<T> {

	public ExpressionBasic(T value, SourceLocation sourceLocation){
		super(sourceLocation);
		this.setValue(value);
	}
	
	@Override
	public T compute(){
		return value;
	}
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	private T value;


}

package jumpingalien.model.program.expression;

import jumpingalien.model.program.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Expression<T extends Type<?>> {

//	public Expression() {
////		this.column = column;
////		this.line = line;
//	}
	public Expression(SourceLocation sourceLocation){
		setSourceLocation(sourceLocation);
	}
	
	private SourceLocation sourceLocation;
	
//	public T getType(){
//		return this.type;
//	}
//	 
//	public void setType(T type){
//		this.type = type;
//	}
//	 
//	private T type ;
	 
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}

	public void setSourceLocation(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public abstract T compute();

//		public int getLine(){
//			return this.line;
//		}
	//	
//		private final int line;
	//	
//		public int getColumn(){
//			return this.column;
//		}
	//	
//		private final int column;

}
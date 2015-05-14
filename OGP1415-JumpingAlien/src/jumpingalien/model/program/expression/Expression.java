package jumpingalien.model.program.expression;

import jumpingalien.model.program.type.Type;

public abstract class Expression<T> {

	public Expression() {
//		this.column = column;
//		this.line = line;
	}
	
	public Type getType(){
		return this.type;
	}
	 
	public void setType(Type type){
		this.type = type;
	}
	 
	private Type type ;
	 
	public abstract Object compute();

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
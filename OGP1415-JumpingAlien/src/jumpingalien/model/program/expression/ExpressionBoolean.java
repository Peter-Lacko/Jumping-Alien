package jumpingalien.model.program.expression;

import jumpingalien.model.program.type.*;
import jumpingalien.part3.programs.SourceLocation;

public class ExpressionBoolean extends ExpressionBasic<Bool> {

	public ExpressionBoolean(SourceLocation sourceLocation,boolean flag){
		super(sourceLocation);
		Bool value = new Bool(flag);
		this.setValue(value);
	}

	

}

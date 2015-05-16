package jumpingalien.model.program.expression;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.type.*;

public class ExpressionBoolean<Boolean> extends ExpressionBasic {

	public ExpressionBoolean(boolean flag){
		super();
		this.setValue(flag);
		this.setType(Bool);
	}


}

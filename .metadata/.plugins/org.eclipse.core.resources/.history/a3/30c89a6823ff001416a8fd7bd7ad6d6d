package jumpingalien.model.program.expression.unary;

import java.util.Random;

import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.expression.ExpressionBasic;
import jumpingalien.model.program.type.DoubleType;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.internal.generated.JumpingAlienProgParser.GetExprContext;

public class ExpressionRandomDouble extends Unary<DoubleType,DoubleType> {

	public ExpressionRandomDouble(Expression<DoubleType> maxValue, SourceLocation sourceLocation){
		super(maxValue , sourceLocation);
	}
	
	
	
	@Override
	public DoubleType compute() {
		Random r = new Random(); 
		DoubleType d = new DoubleType(r.nextDouble()*getOperand().compute().getValue());
		return d;
	}

}

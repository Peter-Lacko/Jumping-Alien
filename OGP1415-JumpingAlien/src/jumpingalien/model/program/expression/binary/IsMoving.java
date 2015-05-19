package jumpingalien.model.program.expression.binary;

import jumpingalien.model.*;
import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.IProgramFactory.Direction;

public class IsMoving extends Binary<Object,DirectionType,Bool> {

	public IsMoving(Expression<Object> unary, Expression<DirectionType> direction, SourceLocation sourceLocation) {
		super(unary,direction,sourceLocation);
		this.setDirectionType(direction.compute());
	}
	
	public Direction getDirection() {
		return direction.getValue();
	}

	public void setDirectionType(DirectionType direction) {
		this.direction = direction;
	}

	private DirectionType direction;

	@Override
	public Bool compute() {
		boolean b;
		if (!(this.getOperand1().compute().getValue() instanceof Characters)){
			b = false;
		}
		else if (this.getDirection() == Direction.UP){
			b = ((Characters) this.getOperand1().compute().getValue()).getVerticalVelocity() > 0;
		}
		else if (this.getDirection() == Direction.DOWN){
			b = ((Characters) this.getOperand1().compute().getValue()).getVerticalVelocity() < 0;
		}
		else if (this.getDirection() == Direction.LEFT){
			b = ((Characters) this.getOperand1().compute().getValue()).getHorizontalVelocity() < 0;
		}
		else if (this.getDirection() == Direction.RIGHT){
			b = ((Characters) this.getOperand1().compute().getValue()).getHorizontalVelocity() > 0;
		}
		else
			b = false;
		return new Bool(b);
	}

}

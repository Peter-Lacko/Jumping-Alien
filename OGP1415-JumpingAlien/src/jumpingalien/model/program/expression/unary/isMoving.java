package jumpingalien.model.program.expression.unary;

import jumpingalien.model.*;
import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.IProgramFactory.Direction;

public class isMoving extends Unary<Object,Bool> {

	public isMoving(Expression<Object> unary,DirectionType direction, SourceLocation sourceLocation) {
		super(unary,sourceLocation);
		this.setDirectionType(direction);
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
		if (!(this.getOperand().compute().getValue() instanceof Characters)){
			b = false;
		}
		else if (this.getDirection() == Direction.UP){
			b = ((Characters) this.getOperand().compute().getValue()).getVerticalVelocity() > 0;
		}
		else if (this.getDirection() == Direction.DOWN){
			b = ((Characters) this.getOperand().compute().getValue()).getVerticalVelocity() < 0;
		}
		else if (this.getDirection() == Direction.LEFT){
			b = ((Characters) this.getOperand().compute().getValue()).getHorizontalVelocity() < 0;
		}
		else if (this.getDirection() == Direction.RIGHT){
			b = ((Characters) this.getOperand().compute().getValue()).getHorizontalVelocity() > 0;
		}
		else
			b = false;
		return new Bool(b);
	}

}

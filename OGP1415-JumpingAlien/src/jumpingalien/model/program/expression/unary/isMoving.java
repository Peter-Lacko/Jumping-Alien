package jumpingalien.model.program.expression.unary;

import jumpingalien.model.*;
import jumpingalien.model.program.expression.Expression;
import jumpingalien.part3.programs.IProgramFactory.Direction;

public class isMoving extends Unary<Object> {

	public isMoving(Expression<Object> unary,Direction direction) {
		super(unary);
		this.setDirection(direction);
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	private Direction direction;

	@Override
	public Object compute() {
		if (!(this.getExpr().compute() instanceof Characters)){
			return false;
		}
		if (this.getDirection() == Direction.UP){
			return ((Characters) this.getExpr().compute()).getVerticalVelocity() > 0;
		}
		else if (this.getDirection() == Direction.DOWN){
			return ((Characters) this.getExpr().compute()).getVerticalVelocity() < 0;
		}
		else if (this.getDirection() == Direction.LEFT){
			return ((Characters) this.getExpr().compute()).getHorizontalVelocity() < 0;
		}
		else if (this.getDirection() == Direction.RIGHT){
			return ((Characters) this.getExpr().compute()).getHorizontalVelocity() > 0;
		}
		return false;
	}

}
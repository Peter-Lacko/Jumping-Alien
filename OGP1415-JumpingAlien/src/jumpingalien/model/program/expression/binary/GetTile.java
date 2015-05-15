package jumpingalien.model.program.expression.binary;

import jumpingalien.model.Tile;
import jumpingalien.model.program.Program;
import jumpingalien.model.program.expression.Expression;

public class GetTile extends Binary<Double>{

	public GetTile(Expression<Double> operand1, Expression<Double> operand2, 
			Program program) {
		super(operand1, operand2);
		setProgram(program);
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	private Program program;
	
	@Override
	public Object compute() {
		int[] pos =this.getProgram().getWorld().getPixelOfTileContaining((int)this.getExpr1().compute(), (int)this.getExpr2().compute());
		for (Tile tile : this.getProgram().getWorld().getTiles()){
			if (tile.getPosition() == pos)
				return tile;
		}
		return null;
	}

}

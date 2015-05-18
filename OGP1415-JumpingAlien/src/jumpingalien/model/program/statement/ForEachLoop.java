package jumpingalien.model.program.statement;

import java.util.Iterator;
import java.util.stream.Stream;

import jumpingalien.model.GameObject;
import jumpingalien.model.program.expression.Expression;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.IProgramFactory.*;
import jumpingalien.part3.programs.SourceLocation;

public class ForEachLoop extends LoopStatement {

	public ForEachLoop(String name, Kind kind, Expression<Bool> whereExpression,
			Expression<DoubleType> sortExpression, SortDirection sortDirection,
			SourceLocation sourceLocation, Statement loopStatement) {
		super(sourceLocation, loopStatement);
		// TODO Auto-generated constructor stub
	}

	private ForEachLoop getForEachLoop(){
		return this;
	}
	
    public Stream<GameObject> stream() {
    	Stream.Builder builder = Stream.builder();
    	for (GameObject element: getProgram().getCharacter().getWorld().getAllObjectsEnTiles())
    		builder.accept(element);
    	return builder.build();    		
    }
	
	@Override
	public Iterator<Statement> iterator() {
		return new Iterator<Statement>(){

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Statement next() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}

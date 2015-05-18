package jumpingalien.model.program.expression;

import jumpingalien.model.*;
import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.SourceLocation;

public class SelfObject extends Expression<Object> {


	public SelfObject(SourceLocation sourceLocation){
		super(sourceLocation);
	}

	@Override
	public Characters compute() {
		return this.getCharacter();
	}

	private Characters getCharacter() {
		return this.character;
	}
	
	private Characters character;
}

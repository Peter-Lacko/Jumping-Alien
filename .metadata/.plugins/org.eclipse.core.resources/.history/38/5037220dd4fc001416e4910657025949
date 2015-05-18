package jumpingalien.model.program.expression;

import jumpingalien.model.*;
import jumpingalien.model.program.Program;

public class SelfObject extends Expression<Object> {


	public SelfObject(Program program){
		super();
		this.character = program.getCharacter();
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

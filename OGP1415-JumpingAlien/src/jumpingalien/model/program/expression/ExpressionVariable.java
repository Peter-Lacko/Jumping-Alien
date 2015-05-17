package jumpingalien.model.program.expression;

import jumpingalien.model.program.Program;
import jumpingalien.model.program.type.*;
import jumpingalien.part3.programs.SourceLocation;

public abstract class ExpressionVariable<T extends Type<?>> extends Expression<T> {

	public ExpressionVariable(SourceLocation sourceLocation, Program program, String name) {
		super(sourceLocation);
		setProgram(program);
		setName(name);
	}
	
	public ExpressionVariable(SourceLocation sourceLocation, String name) {
		this(sourceLocation, null, name);
	}

	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Program program;
	
	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

//	@Override
//	public T compute() {
//		T result = (T) getProgram().getGlobalVariables().get(getName());
//		return null;
//	}

	// hoe ben je zeker dat er een programma bij staat?

}

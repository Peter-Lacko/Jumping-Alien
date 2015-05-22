package jumpingalien.model.program.statement;

import jumpingalien.model.program.Program;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Statement implements Iterable<Statement> {
	
	public Statement(SourceLocation sourceLocation){
		setSourceLocation(sourceLocation);
	}
	
	private SourceLocation sourceLocation;
	
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}

	public void setSourceLocation(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public abstract void execute();
	
	private Program getBasicProgram(){
		return this.program;
	}
	
	public Program getProgram(){
		if (this.program == null){
			setProgram(getTopStatement().getProgram());
		}
		return this.program;
	}
	
	public void setProgram(Program program){
		this.program = program;
	}
	
	private Program program = null;
	
	private Statement enclosingStatement;

	public Statement getEnclosingStatement() {
		return enclosingStatement;
	}

	public void setEnclosingStatement(Statement enclosingStatement) {
		this.enclosingStatement = enclosingStatement;
	}
	
	public Statement getTopStatement(){
		Statement superStatement = this;
		if (getBasicProgram() == null){
			superStatement = getEnclosingStatement().getTopStatement();
		}
		return superStatement;
	}

}

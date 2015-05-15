package jumpingalien.model.program.statement;

import jumpingalien.model.program.Program;

public abstract class Statement {
	
	public Statement(){
	}
	
	public abstract void execute();
	
	public Program getProgram(){
		return this.program;
	}
	
	private Program program;

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
	private boolean done;

}

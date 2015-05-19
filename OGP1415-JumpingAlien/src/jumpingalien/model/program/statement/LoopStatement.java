package jumpingalien.model.program.statement;

import jumpingalien.part3.programs.SourceLocation;

public abstract class LoopStatement extends Statement {

	public LoopStatement(SourceLocation sourceLocation, Statement loopStatement) {
		super(sourceLocation);
		if (loopStatement instanceof Break)
			((Break) loopStatement).setLoopStatement(this);
		else if (loopStatement instanceof StatementSequence){
			//while and foreach statements do no go through their body if they aren't executed first
			//(condition is standard set to false, stream is empty.
			for(Statement statement : loopStatement){
				if (statement instanceof Break)
					((Break) statement).setLoopStatement(this);
			}
		}
		this.loopBody = loopStatement;
		loopStatement.setEnclosingStatement(this);
	}

	public Statement getLoopBody() {
		return loopBody;
	}
	
	private final Statement loopBody;
	
	private boolean isBreak = false;

	public boolean isBreak() {
		return isBreak;
	}

	public void setBreak(boolean isBreak) {
		this.isBreak = isBreak;
	}
	
}
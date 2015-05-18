package jumpingalien.model.program.expression;

import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.model.program.statement.Statement;
import jumpingalien.model.program.type.Object;

public class SelfObject extends Expression<Object> {


	public SelfObject(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	@Override
	public void setStatement(Statement statement) {
		this.statement = statement;
		this.setSelf(this.getStatement().getProgram().getObject());
	}
	
	public Object getSelf() {
		return self;
	}

	public void setSelf(Object self) {
		this.self = self;
	}

	private Object self;
	
	@Override
	public Object compute() {
		return this.getSelf();
	}
}

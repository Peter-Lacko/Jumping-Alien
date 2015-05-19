package jumpingalien.model.program;
import java.util.List;
import java.util.Map;

import jumpingalien.model.program.expression.*;
import jumpingalien.model.program.expression.unary.*;
import jumpingalien.model.program.expression.binary.*;
import jumpingalien.model.program.statement.*;
import jumpingalien.model.program.type.*;
import jumpingalien.model.program.type.Object;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.SourceLocation;


public class ProgramFactory implements IProgramFactory<Expression<? extends Type<?>>, Statement, Type<?>, Program>{

	@Override
	public Expression<? extends Type<?>> createReadVariable(
			String variableName, Type<?> variableType,
			SourceLocation sourceLocation) {
		if (variableType instanceof Bool){
			return new ExpressionVariableBoolean(sourceLocation,variableName); 
		}
		else if (variableType instanceof DoubleType) {
			return new ExpressionVariableDouble(sourceLocation,variableName);
		}
		else if (variableType instanceof DirectionType) {
			return new ExpressionVariableDirection(sourceLocation,variableName);
		}
		else  {
			return new ExpressionVariableGameObject(sourceLocation,variableName);
		}
	}

	@Override
	public Expression<DoubleType> createDoubleConstant(double value,
			SourceLocation sourceLocation) {
		ExpressionBasic<DoubleType> result = new ExpressionBasic<DoubleType>(new DoubleType(value), sourceLocation);
		return result;
	}

	@Override
	public Expression<Bool> createTrue(
			SourceLocation sourceLocation) {
		ExpressionBasic<Bool> result = new ExpressionBasic<Bool>(new Bool(true), sourceLocation);
		return result;
	}

	@Override
	public Expression<Bool> createFalse(
			SourceLocation sourceLocation) {
		ExpressionBasic<Bool> result = new ExpressionBasic<Bool>(new Bool(false), sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createNull(
			SourceLocation sourceLocation) {
		ExpressionBasic<Object> result = new ExpressionBasic<Object>(new Object(null), sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createSelf(
			SourceLocation sourceLocation) {
		SelfObject self = new SelfObject(sourceLocation);
		return self;
	}

	@Override
	public Expression<? extends Type<?>> createDirectionConstant(
			jumpingalien.part3.programs.IProgramFactory.Direction value,
			SourceLocation sourceLocation) {
		ExpressionBasic<DirectionType> result = new ExpressionBasic<DirectionType>(new DirectionType(value), sourceLocation);
		return result;
	}

	@Override
	public Expression<DoubleType> createAddition(
			Expression<? extends Type<?>> left,
			Expression<? extends Type<?>> right, SourceLocation sourceLocation) {
//		Expression<DoubleType> result = new Addition(left, right, sourceLocation);
//		return result;
		Expression<DoubleType> result = new Addition((Expression<DoubleType>) left, (Expression<DoubleType>) right, sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createSubtraction(
			Expression<? extends Type<?>> left,
			Expression<? extends Type<?>> right, SourceLocation sourceLocation) {
		Expression<DoubleType> result = new Subtraction((Expression<DoubleType>) left, (Expression<DoubleType>) right, sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createMultiplication(
			Expression<? extends Type<?>> left,
			Expression<? extends Type<?>> right, SourceLocation sourceLocation) {
		Expression<DoubleType> result = new Multiplication((Expression<DoubleType>) left, (Expression<DoubleType>) right, sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createDivision(
			Expression<? extends Type<?>> left,
			Expression<? extends Type<?>> right, SourceLocation sourceLocation) {
		Expression<DoubleType> result = new Division((Expression<DoubleType>) left, (Expression<DoubleType>) right, sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createSqrt(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<DoubleType> result = new Sqrt((Expression<DoubleType>)expr,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createRandom(
			Expression<? extends Type<?>> maxValue,
			SourceLocation sourceLocation) {
		Expression<DoubleType> result = new ExpressionRandomDouble((Expression<DoubleType>)maxValue,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createAnd(
			Expression<? extends Type<?>> left,
			Expression<? extends Type<?>> right, SourceLocation sourceLocation) {
		Expression<Bool> result = new And((Expression<Bool>) left, (Expression<Bool>) right, sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createOr(
			Expression<? extends Type<?>> left,
			Expression<? extends Type<?>> right, SourceLocation sourceLocation) {
		Expression<Bool> result = new Or((Expression<Bool>) left, (Expression<Bool>) right, sourceLocation);
		return result;
	}

	@Override
	public Expression<Bool> createNot(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<Bool> result = new Negation((Expression<Bool>) expr, sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createLessThan(
			Expression<? extends Type<?>> left,
			Expression<? extends Type<?>> right, SourceLocation sourceLocation) {
		Expression<Bool> result = new SmallerThan((Expression<DoubleType>) left, (Expression<DoubleType>) right, sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createLessThanOrEqualTo(
			Expression<? extends Type<?>> left,
			Expression<? extends Type<?>> right, SourceLocation sourceLocation) {
		Expression<Bool> result = new SmallerThanOrEqual((Expression<DoubleType>) left, (Expression<DoubleType>) right, sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createGreaterThan(
			Expression<? extends Type<?>> left,
			Expression<? extends Type<?>> right, SourceLocation sourceLocation) {
		Expression<Bool> result = new GreaterThan((Expression<DoubleType>) left, (Expression<DoubleType>) right, sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createGreaterThanOrEqualTo(
			Expression<? extends Type<?>> left,
			Expression<? extends Type<?>> right, SourceLocation sourceLocation) {
		Expression<Bool> result = new GreaterThanOrEqual((Expression<DoubleType>) left, (Expression<DoubleType>) right, sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createEquals(
			Expression<? extends Type<?>> left,
			Expression<? extends Type<?>> right, SourceLocation sourceLocation) {
		Expression<Bool> result = new Equal((Expression<DoubleType>) left, (Expression<DoubleType>) right, sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createNotEquals(
			Expression<? extends Type<?>> left,
			Expression<? extends Type<?>> right, SourceLocation sourceLocation) {
		Expression<Bool> result = new NotEqual((Expression<DoubleType>) left, (Expression<DoubleType>) right, sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createGetX(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<DoubleType> result = new GetX((Expression<Object>)expr,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createGetY(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<DoubleType> result = new GetY((Expression<Object>)expr,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createGetWidth(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<DoubleType> result = new GetWidth((Expression<Object>)expr,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createGetHeight(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<DoubleType> result = new GetHeight((Expression<Object>)expr,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createGetHitPoints(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<DoubleType> result = new GetHp((Expression<Object>)expr,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createGetTile(
			Expression<? extends Type<?>> x, Expression<? extends Type<?>> y,
			SourceLocation sourceLocation) {
		Expression<Object> result = new GetTile((Expression<DoubleType>) x, (Expression<DoubleType>) y, sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createSearchObject(
			Expression<? extends Type<?>> direction,
			SourceLocation sourceLocation) {
		Expression<Object> result = new SearchObject((Expression<DirectionType>)direction ,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createIsMazub(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<Bool> result = new IsMazub((Expression<Object>)expr ,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createIsShark(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<Bool> result = new IsShark((Expression<Object>)expr ,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createIsSlime(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<Bool> result = new IsSlime((Expression<Object>)expr ,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createIsPlant(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<Bool> result = new IsPlant((Expression<Object>)expr ,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createIsDead(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<Bool> result = new IsDead((Expression<Object>)expr ,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createIsTerrain(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<Bool> result = new IsTerrain((Expression<Object>)expr ,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createIsPassable(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<Bool> result = new IsPassable((Expression<Object>)expr ,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createIsWater(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<Bool> result = new IsWater((Expression<Object>)expr ,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createIsMagma(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<Bool> result = new IsMagma((Expression<Object>)expr ,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createIsAir(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<Bool> result = new IsAir((Expression<Object>)expr ,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createIsMoving(
			Expression<? extends Type<?>> expr,
			Expression<? extends Type<?>> direction,
			SourceLocation sourceLocation) {
		Expression<Bool> result = new IsMoving((Expression<Object>)expr,(Expression<DirectionType>) direction ,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createIsDucking(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<Bool> result = new IsDucking((Expression<Object>)expr ,sourceLocation);
		return result;
	}

	@Override
	public Expression<? extends Type<?>> createIsJumping(
			Expression<? extends Type<?>> expr, SourceLocation sourceLocation) {
		Expression<Bool> result = new IsJumping((Expression<Object>)expr ,sourceLocation);
		return result;
	}

	@Override
	public Statement createAssignment(String variableName,
			Type<?> variableType, Expression<? extends Type<?>> value,
			SourceLocation sourceLocation) {
		//waar moet ik HIER variableType gebruiken??!!
		//nu kan je (denk ik) door assignment variablen van type veranderen -> MAG NIET!
		// hoe zou je hier detecteren of je een type-fout maakt in een assignment?
		// zoals expressionVariable: 4 verschillende assignments?
		Statement result = new Assignment(variableName, value, sourceLocation, variableType);
		return result;
	}

	@Override
	public Statement createWhile(Expression<? extends Type<?>> condition,
			Statement body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createForEach(
			String variableName,
			jumpingalien.part3.programs.IProgramFactory.Kind variableKind,
			Expression<? extends Type<?>> where,
			Expression<? extends Type<?>> sort,
			jumpingalien.part3.programs.IProgramFactory.SortDirection sortDirection,
			Statement body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createIf(Expression<? extends Type<?>> condition,
			Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createPrint(Expression<? extends Type<?>> value,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStartRun(Expression<? extends Type<?>> direction,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopRun(Expression<? extends Type<?>> direction,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStartJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStartDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createWait(Expression<? extends Type<?>> duration,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSkip(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSequence(List<Statement> statements,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleType getDoubleType() {
		return new DoubleType(0.0);
	}

	@Override
	public Bool getBoolType() {
		return new Bool(false);
	}

	@Override
	public Type<?> getGameObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type<?> getDirectionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Program createProgram(Statement mainStatement,
			Map<String, Type<?>> globalVariables) {
		// TODO Auto-generated method stub
		return null;
	}



}

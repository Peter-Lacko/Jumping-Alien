package jumpingalien.model.program.type;

public class DoubleType extends Type<Double> {

	public DoubleType(double value){
		super(value);
	}
	
	@Override
	public String toString(){
		return getValue().toString();
	}
}

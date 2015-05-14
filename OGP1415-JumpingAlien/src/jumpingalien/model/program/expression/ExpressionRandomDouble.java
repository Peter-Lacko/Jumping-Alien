package jumpingalien.model.program.expression;

import java.util.Random;

public class ExpressionRandomDouble extends ExpressionBasic<Double> {

	public ExpressionRandomDouble(double maxValue){
		this.maxValue = maxValue;
	}
	
	public double getMaxValue() {
		return maxValue;
	}
	
	private double maxValue;
	
	@Override
	public Object compute() {
		Random r = new Random(); 
		return r.nextDouble()*getMaxValue();
	}

}

package jumpingalien.model.program.type;

import jumpingalien.model.*;
import jumpingalien.part3.programs.IProgramFactory.Kind;

public class Object extends Type<GameObject> {

	public Object(GameObject value) {
		super(value);
	}
	
	public Kind getKind(){
		GameObject value = getValue();
		if (value instanceof Mazub)
			return Kind.MAZUB;
		else if (value instanceof Buzam)
			return Kind.BUZAM;
		else if (value instanceof Slime)
			return Kind.SLIME;
		else if (value instanceof Shark)
			return Kind.SHARK;
		else if (value instanceof Plant)
			return Kind.PLANT;
		else if (value instanceof Tile)
			return Kind.TERRAIN;
		else
			return Kind.ANY;
	}
	
	@Override
	public String toString(){
		return getKind().toString();
	}

}

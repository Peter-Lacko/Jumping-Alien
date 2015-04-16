package jumpingalien.model;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.som.annotate.*;

public class School {

	public School(){
	}
	
	public void addAsSlime(Slime slime){
		slimes.add(slime);
	}
	
	public void removeAsSlime(Slime slime){
		if (this.getIndexOfObject(slime) == -1)
			this.removeAsSlimeAt(this.getIndexOfObject(slime));
	}
	
	public void removeAsSlimeAt(int index){
		slimes.remove(index);
	}
	
	public List<Slime> getSlimes(){
		return this.slimes;
	}
	
	public int getIndexOfObject(Slime slime) throws IllegalArgumentException{
		if (! hasAsSlime(slime))
			throw new IllegalArgumentException();
		int index = 0;
		for (Slime blob : slimes){
			if (blob == slime)
				return index;
			index ++;
		}
		return -1;
	}
	
	private boolean hasAsSlime(Slime slime) {
		return slimes.contains(slime);
	}

	private List<Slime> slimes = new ArrayList<Slime>();
	
	public int getNbSlimes(){
		return slimes.size();
	}
	
    /**
     * Check whether this School is already terminated.
     */
    @Basic @Raw
    public boolean isTerminated() {
        return this.isTerminated;
    }
	
    private boolean isTerminated = false;
    
}

package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;

public class School {

	public School(){
		
	}
	
    /**
     * Check whether this School is already terminated.
     */
    @Basic @Raw
    public boolean isTerminated() {
        return this.isTerminated;
    }
	
    private boolean isTerminated = false;
    
    public void changeSchool(Slime peter,Slime sander){
    	
    }
}

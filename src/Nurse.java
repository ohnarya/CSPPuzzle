


public final class Nurse extends Job {
	Nurse(){
		this.name = "Nurse";
	}
	@Override
	public boolean isEligible(Person p) {
		/*male only*/
		if(p instanceof Woman)
			return false;		
		
		/* beyond 9th grade */
		if(p.edu < 9)
			return false;
		return true;
	}

}

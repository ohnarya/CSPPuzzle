



public final class Teacher extends Job  {

	Teacher(){
		this.name = "Teacher";
	}
	@Override
	public boolean isEligible(Person p) {
		/* beyond 9th grade */
		if(p.edu < 9)
			return false;
		
		return true;
	}

}

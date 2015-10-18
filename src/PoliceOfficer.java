


public final class PoliceOfficer extends Job{

	PoliceOfficer(){
		this.name = "PoliceOfficer";
	}
	@Override
	public boolean isEligible(Person p) {
		/* Roberta can be a chef */
		if(p.name.equals("Roberta"))
			return false;
		
		/* beyond 9th grade */
		if(p.edu < 9)
			return false;
		
		return true;
	}

}

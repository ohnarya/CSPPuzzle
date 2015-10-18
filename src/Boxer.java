


public final class Boxer extends Job{

	Boxer(){
		this.name = "Boxer";
	}
	@Override
	public boolean isEligible(Person p) {
		/* Roberta can be a chef */
		if(p.name.equals("Roberta"))
			return false;
		
		return true;
	}

}

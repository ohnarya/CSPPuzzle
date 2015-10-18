



public final class Chef extends Job  {

	Chef(){
		this.name = "Chef";
	}
	@Override
	public boolean isEligible(Person p) {
		/*female only*/
		if(p instanceof Man)
			return false;
		
		/* Roberta can not be a chef */
		if(p.name.equals("Roberta"))
			return false;
		
		return true;
	}
}

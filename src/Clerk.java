



public final class Clerk extends Job{

	Clerk(){
		this.name = "Clerk";
	}
	@Override
	public boolean isEligible(Person p) {
		/*male only*/
		if(p instanceof Woman)
			return false;
		
		return true;
	}

}

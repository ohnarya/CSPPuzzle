

public final class Actor extends Job {
	Actor(){
		this.name = "Actor";
	}
	@Override
	public boolean isEligible(Person p) {
		/*male only*/
		if(p instanceof Woman)
			return false;
		
		return true;
	}

}

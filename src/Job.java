/*
 * Author : Jiyoung Hwang
 * Description : Jobs are domains 
 *               this is a super class of 8 specific jobs
 * Date   : 2015.10.17
 * 
 * */


public abstract class Job implements Comparable<Job> {
	protected String name;
	
	public String getName(){
		return name;
	}
	
	public abstract boolean isEligible(Person p);
	
	@Override
	public int hashCode(){
		return this.name.hashCode();
	}	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Job)
			return this.name.equals(((Job)o).name);
		else
			return false;
	}	
	@Override
	public int compareTo(Job j){
		return this.name.compareTo(j.name);
	}
	public String toString(){
		return name;
	}
	 
}



import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public abstract class Person {
	final int MAX_EDU = 12;
	public    String name;
	protected String gender;
	          int    edu = MAX_EDU;
	
	/*Domain*/          
	List<Job>  joblist  = new ArrayList<Job>();          
	Stack<Job> canNotDO = new Stack<Job>();
	List<Job>  canDO    = new ArrayList<Job>();
	
	Person(String name){
		this.name = name;	
	}
	
	public List<Job> getCanDO(List<Job> assigned){
		//canDO.clear();
		canDO    = new ArrayList<Job>();
		for(Job j:joblist){
			/*skip assigned jobs*/
			if(assigned.size()> 0 && assigned.contains(j)){
				if(canDO.size()>0 && canDO.contains(j)){
					canDO.remove(j);
				}else
					continue;
				
			}
				
			if(j.isEligible(this))
				canDO.add(j);			
		}
		return canDO;
	}
	public List<Job> getCanDOList(){
		return this.canDO;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Person)
			return this.name.equals(((Person)o).name);
		else
			return false;
	}	
	@Override
	public int hashCode(){
		return this.name.hashCode();
	}	
	public String toString(){
		return name;
	}	
}

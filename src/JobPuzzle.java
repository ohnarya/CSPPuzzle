/*
 * Author : Jiyoung Hwang
 * Description : run Job puzzle with/without MRV heuristic using backtracking
 *                
 * Date   : 2015.10.17
 */


import java.util.ArrayList;
import java.util.List;


public class JobPuzzle {

	List<Job>      joblist     = new ArrayList<Job>();
	List<Person>   people      = new ArrayList<Person>();
	List<JobNode>  assignments = new ArrayList<JobNode>();
	
	int iter            = 0;	
	int backtrack       = 0;
	int passconsistency = 0;
	
	JobPuzzle(){
		/*initialize Joblist and people*/
		initializeJoblist();  /*set domain    */
		initializePeople();   /*set variables */
	}
	/*
	 * backtracking_search();
	 * 
	 */
	public boolean backtracking_search(boolean isMRV){
			return backtracking(assignments, isMRV);
	}
	/*
	 * backtracking
	 */
	public boolean backtracking(List<JobNode> assignments, boolean isMRV){
		
		if(isComplete(assignments))
			return true;

		/*select an unassigned variable with/without MRV*/
		Person p = null;
		
		if(!isMRV)
			p = selectUnassignedVariable(assignments);
		else
			p = selectThroughMRV(assignments);

		/*Order-Domain-Value*/
		List<Job> cando = null;
		if(isMRV)
			cando = p.getCanDO(getAssignedJobs(assignments));
		else
			cando = p.joblist;
		
		for(Job j1:cando){
			for(Job j2:cando){
				/*not the same job*/
				if(j1.equals(j2))
					continue;
				/*create a partial assignment*/
				JobNode jn = new JobNode(p, j1, j2);
			
				iter++;
				
				/*check consistency for jobs*/
				if(consistency_Job(assignments, jn)){
					passconsistency++;
					assignments.add(jn);
					
					if(backtracking(assignments, isMRV)){
						backtrack++;
						return true;
					}

				}else{					
				}
			}
		}
		return false;
	}
	/*
	 * find unassigned variable with MRV heuristic
	 * 
	 * */	
	public Person selectThroughMRV(List<JobNode> assignments){
		int min     = joblist.size();
	
		Person minP = null;
		List<Person> aPeople = getAssignedPeople(assignments);

		for(Person p: people){			
			if(aPeople!=null && aPeople.contains(p))
				continue;
		
			List<Job> jobs = p.getCanDO(getAssignedJobs(assignments));
			
			if(min > jobs.size()){
				min  = jobs.size();
				minP = p;
			
			}
		}
			
		return minP;
	}
	/*
	 * find unassigned variable without MRV heuristic
	 * 
	 * */
	public Person selectUnassignedVariable(List<JobNode> assignments){
		List<Person> ap = new ArrayList<Person>();
		for(JobNode jn : assignments){
			ap.add(jn.p);
		}
		/*return an unassigned variable(person)*/
		for(Person p:people){
			if(!ap.contains(p))
				return p;
		}
		return null;
	}
	/*
	 * get assigned jobs
	 * 
	 * */	
	public List<Job> getAssignedJobs(List<JobNode> assignments){
		List<Job> ret = new ArrayList<Job>();
		for(JobNode jn : assignments){
			ret.add(jn.j1);
			ret.add(jn.j2);
		}
		return ret;
	}
	/*
	 * get assigned people
	 * 
	 * */
	public List<Person> getAssignedPeople(List<JobNode> assignments){
		List<Person> ret = new ArrayList<Person>();

		for(JobNode jn : assignments){
			ret.add(jn.p);
		}
		return ret;
	}	
	/*
	 * check if assignments are complete
	 * 
	 * */
	public boolean isComplete(List<JobNode> assignments){
		return assignments.size() == people.size(); 
	}
	/*
	 * consistency check
	 * */
	public boolean consistency_Job(List<JobNode> assignments, JobNode jn){
		Person p = jn.p;
		Job j1 = jn.j1;
		Job j2 = jn.j2;
		
		/* check elibigility
		 */
		if(!j1.isEligible(p)){
			return false;
		}
		if(!j2.isEligible(p)){
			return false;
		}
		/*check assigned job*/
		List<Job> aJob = getAssignedJobs(assignments);
		
		if(aJob.contains(j1))
			return false;
		if(aJob.contains(j2))
			return false;
		
		return true;
		
	}
	/*
	 * initialize people
	 * */
	public void initializePeople(){
		Person Roberta = new Woman("Roberta");
		Roberta.joblist.addAll(joblist);
		
		Person Thelma  = new Woman("Thelma");
		Thelma.joblist.addAll(joblist);
		
		Person Steve   = new Man("Steve");
		Steve.joblist.addAll(joblist);
		
		Person Pete    = new Man("Pete");
		Pete.joblist.addAll(joblist);
		Pete.edu = 8;
		
		people.add(Roberta);
		people.add(Thelma);
		people.add(Steve);
		people.add(Pete);
		
		
	}
	/*
	 * initialize job lists
	 * */
	public void initializeJoblist(){
		Job Chef          = new Chef();
		Job Guard         = new Guard();
		Job Nurse         = new Nurse();
		Job Clerk         = new Clerk();
		Job PoliceOfficer = new PoliceOfficer();
		Job Teacher       = new Teacher();
		Job Actor         = new Actor();
		Job Boxer         = new Boxer();
		
		joblist.add(Chef);
		joblist.add(Guard);
		joblist.add(Nurse);
		joblist.add(Clerk);
		joblist.add(PoliceOfficer);
		joblist.add(Teacher);
		joblist.add(Actor);
		joblist.add(Boxer);
		
	}
	/*
	 * print progress
	 * */
	public void progressReport(){
		System.out.format("iter: %d, ",iter);
	}
	/*
	 * print final report*/
	public void report(boolean isMRV){
		System.out.format("\n\nJobPuzzle: %s iteration=%d, pass=%d, backtrack=%d\n",(isMRV? "MRV": "BASELINE"),iter,passconsistency, backtrack);
		System.out.println("-----------------------------------------------------");
		for(JobNode jn: assignments){
			System.out.format("[%-10s] is [%-8s] and [%-8s].\n",jn.p.name, jn.j1.name, jn.j2.name);
		}
		System.out.println("-----------------------------------------------------");
	}
	
	public void consistency_check(){
		for(Person p: people){
			for(Job j: joblist){
				if(j.isEligible(p)){
					p.canDO.add(j);
				}
			}
			
		}
		
	}	

}

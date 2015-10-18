/*
 * Author : Jiyoung Hwang
 * Description : JobNode is a variable group of this CSP 
 * Date   : 2015.10.17
 * 
 * */

import java.util.ArrayList;
import java.util.List;

public class JobNode implements Comparable<JobNode> {
	Person p;
	Job    j1;
	Job    j2;
	
	JobNode(){
	}
	
	JobNode(Person p, Job j1, Job j2){
		this.p  = p;
		this.j1 = j1;
		this.j2 = j2;

	}
	public List<JobNode> getSuccessor(Person p, List<Job>    joblist){
		List<JobNode> successors = new ArrayList<JobNode>();
		for(Job j :joblist){
			for(Job jj : joblist){
				successors.add(new JobNode(p,j,jj));
			}
		}
		return successors;
	}
	public void printNode(){
		System.out.format("%-8s is %-6s and %s.\n",p.name, j1.name, j2.name);
	}

	public int hashCode(){
		return p.hashCode()+j1.hashCode()+j2.hashCode();
	}
	
	public boolean equals(Object o){
		if(o instanceof JobNode){
			if(    this.p.name.equals(((JobNode)o).p.name)
			    && this.j1.name.equals(((JobNode)o).j1.getName())
			    && this.j2.name.equals(((JobNode)o).j2.getName())
			    ){
			    return true;
			}else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	@Override
	public int compareTo(JobNode n) {
		String thisString = p.name+j1.name+j2.name;
		String nodeString = n.p.name+n.j1.name+n.j2.name;
		
		return thisString.compareTo(nodeString);
	}
	

}
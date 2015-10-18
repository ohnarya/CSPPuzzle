import java.util.ArrayList;
import java.util.HashMap;


public class Variable {
	String name;
	int    location;
	HashMap<Integer, Boolean> domain = new HashMap<Integer,Boolean>(5);
	
	Variable(String name){
		this.name = name;
		setDefaultDomain();
	}
	
	public HashMap<Integer,Boolean> getDomain(){
		return domain;
	}
	public void setDomain(boolean[] b){

		
		for(int i=1;i<=5;i++){
			this.domain.put(i,b[i]);
		}
	}
	public void setDefaultDomain(){
		for(int i=1;i<=5;i++){
			domain.put(i,false);
		}
	}
	public String getName(){
		return this.name;
	}
	public int getDomainSize(){
		int cnt=0;
		for(Integer key:domain.keySet()){
			if(!domain.get(key))
				cnt++;
		}
		return cnt;
	}
	
	public void insertDomain(int location){
		domain.put(location,false);
	}
	
	public void removeDomain(int location){
		domain.put(location,true);
	}
	
}
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class HousePuzzle {
	public  HashMap<String, Integer> board = new HashMap<String, Integer>(25);
	public  HashMap<String, Variable> variables = new HashMap<String, Variable>(25);
	
	public  ArrayList<String> houses  = new ArrayList<String>(5);
	public  ArrayList<String> races   = new ArrayList<String>(5);
	public  ArrayList<String> animals = new ArrayList<String>(5);
	public  ArrayList<String> foods   = new ArrayList<String>(5);
	public  ArrayList<String> drinks  = new ArrayList<String>(5);	
	
	int iter=0;	
	public void printBoard(){
		for(int i=1;i<=5;i++){
			for(String key:board.keySet()){
				if(board.get(key)==i)
					System.out.print(key + "/");
			}
			System.out.println("");
		}
		System.out.println("=========================================");
	}
	/*
	 * backtrack()
	 * 
	 * */
	public boolean backtrack(){
		iter++;
		
		if(iter%10000==0){
			System.out.format("[%d]",iter);
			printBoard();
		}
		

		for(String key : board.keySet()){
			if(board.get(key)>0)
				continue;
			
			Variable v = variables.get(key);
			String name  = v.name;
			
			if(v.getDomainSize()>0){
				HashMap<Integer, Boolean> domain = v.getDomain();
			    for(Integer i: domain.keySet()){
			    	/*already assigned*/
			    	if(board.get(name)>0)
			    		continue;
			    	
			    	/*domain assigned */
			    	if(domain.get(i))
			    		continue;
					
			    	v.location = (int)i;
			    	//System.out.format("target: [%d][%s][%d][%d] backtrack()\n",iter,v.getName(),v.location,v.getDomainSize());
	
			    	board.put(name, i);
		    		domain.put(i, true);
		    		
			    	/*check consistency*/
			    	if(consistency_house(v)){
			    		//System.out.println("pass>>>>>>>>>>");
	   		
			    		/*backtrack*/
			    		if(!backtrack()){
			    			if(isComplete())
			    				return true;
			    			
			    			domain.put(i,false);
			    			board.put(name, -1);
	
			    		}
			    	}else{
			    		domain.put(i,false);
		    			board.put(name, -1);
			    		//System.out.format("[%d] FAIL>>>>>>>>>>>\n",(int)i);
			    	}
			    }
			}
		    return false;
		}
		return true;
	}
	
	public boolean isDuplicated(Variable v){
		int racecnt   = 0;
		int housecnt  = 0;
		int animalcnt = 0;
		int foodcnt   = 0;
		int drinkcnt  = 0;
		String name   = v.name;
		int location  = v.location;
		
		for(String s:board.keySet()){
			if(board.get(s) == location){
				if(races.contains(name) && races.contains(s)){
					racecnt++;
					if(racecnt>1)
						return true;
				}else if(houses.contains(name) && houses.contains(s)){
					housecnt++;
					if(housecnt>1)
						return true;
				}else if(animals.contains(name) && animals.contains(s)){
					animalcnt++;
					if(animalcnt>1)
						return true;
				}else if(foods.contains(name) && foods.contains(s)){
					foodcnt++;
					if(foodcnt>1)
						return true;
				}else if(drinks.contains(name) && drinks.contains(s)){
					drinkcnt++;
					if(drinkcnt>1)
						return true;
				}
			}
		}
		
		return false;
	}
	public boolean consistency_house(Variable v) {
		String name = v.getName();
		int location = v.location;

		
		if(isDuplicated(v)){
			//System.out.println("Duplicated!");
			return false;
		}

		
		//rule 1. the englishman lives in the red house.
		if(board.get("english") > 0 && board.get("red") > 0){
			if(board.get("english") != board.get("red"))
				return false;
		}
		
		//rule 2. the spaniard owns the dog.
		if(board.get("spaniard")> 0 && board.get("dog") > 0){
			if(board.get("spaniard") != board.get("dog"))
				return false;
 
		}
		
		//rule 3. the norwegian lives inthe first house on the left.
		if(board.get("norwegian")>0 && board.get("norwegian")!=1){
			
			return false; 
		}
		
		//rule 4. the green house is immediately to the right of the ivory house.
		int green = (int)board.get("green");
		int ivory = (int)board.get("ivory");
		
		if(green>0 && ivory>0){
			if( Math.abs(green - ivory)!=1) 
				return false; 
		}	

//		//rule 5. the man who eats hershey bars lives in the house next to the man with the fox
		int hershey = board.get("hershey");
		int fox     = board.get("fox");
		
		if(hershey >0  && fox >0){	
			if ( Math.abs(hershey - fox)!=1 )
				return false; 
		}	
		
		//rule 6. the kits kats are eaten in the yellow house
		if(board.get("kitkat") > 0 && board.get("yellow") > 0){					
			if ( board.get("kitkat") != board.get("yellow"))
				return false; 
		}	
		
		//rule 7. the norwegian lives next to the blue house
		if(board.get("blue")>0 && board.get("blue")!=2){
			
			return false; 
		}
		//rule 8. the smarties eater owns snails
		if(board.get("smarty")>0 && board.get("snail")>0){
			if ( board.get("smarty") != board.get("snail"))
				return false; 			
		}
		//rule 9. the snickers eater drinks orange juice
		if(board.get("snicker")>0 && board.get("orangejuice")>0){
			if ( board.get("snicker") != board.get("orangejuice"))
				return false; 			
		}
		//rule 10. the ukranian drinks tea
		if(board.get("ukranian")>0 && board.get("tea")>0){
			if ( board.get("ukranian") != board.get("tea"))
				return false; 			
		}		
		//rule 11. the japanese person eats milky ways
		if(board.get("japanese")>0 && board.get("milkyway")>0){
			if ( board.get("japanese") != board.get("milkyway"))
				return false; 			
		}
		//rule 12. kit kats are eaten in a house next to the house where the horse is kept
		int kitkat = board.get("kitkat");
		int horse  = board.get("horse");
		
		if(kitkat >0  && horse >0){	
			if ( Math.abs(kitkat - horse)!=1 )
				return false; 
		}			
		//rule 13. coffee is drunk in the green house
		if(board.get("coffee")>0 && board.get("green")>0){
			if ( board.get("coffee") != board.get("green"))
				return false; 			
		}	
		//rule 14. milk is drunk in the middle house
		if(board.get("milk")>0 && board.get("milk")!=3){		
			return false; 
		}
		
		return true;
		
	}
	
	/*
	 * find unassigned variable
	 * 
	 * */
	public Variable getUnAssignedVariable(){
		String ret = null;
		for(String key : board.keySet()){
			if(board.get(key)>0)
				continue;
			else{
				ret = key;
				break;
			}
		}
		return (ret==null)? null:variables.get(ret);
	}
	
	public boolean isComplete(){
		for(String key : board.keySet()){
			if(board.get(key)>0)
				continue;
			else
				return false;
		}
		return true;
	}

	
	HousePuzzle(){
		races.add("norwegian");
		races.add("ukranian");
		races.add("english");
		races.add("spaniard");
		races.add("japanese");
		
		houses.add("yellow");
		houses.add("blue");
		houses.add("red");
		houses.add("ivory");
		houses.add("green");
		
		animals.add("fox");
		animals.add("horse");
		animals.add("snail");
		animals.add("dog");
		animals.add("zebra");
		
		foods.add("kitkat");
		foods.add("hershey");
		foods.add("smarty");
		foods.add("snicker");
		foods.add("milkyway");
		
		drinks.add("water");
		drinks.add("tea");
		drinks.add("milk");
		drinks.add("orangejuice");
		drinks.add("coffee");
		
		for(String s:races){
			board.put(s, -1);
			variables.put(s,new Variable(s));
			
		}
		for(String s:houses){
			board.put(s, -1);
			variables.put(s,new Variable(s));
		}
		for(String s:animals){
			board.put(s,-1);
			variables.put(s,new Variable(s));
		}
		for(String s:foods){
			board.put(s,-1);
			variables.put(s,new Variable(s));
		}
		for(String s:drinks){
			board.put(s,-1);
			variables.put(s,new Variable(s));
		}
	}
	/*
	 * backtrackSearch
	 * */
	public boolean backtrackSearch() {
		System.out.println("BacktrackSearch has started!");
		return backtrack();
	}	
}

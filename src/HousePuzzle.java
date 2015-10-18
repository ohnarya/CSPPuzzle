/*
 * Author : Jiyoung Hwang
 * Description : run House puzzle with/without MRV heuristic using backtracking
 *                
 * Date   : 2015.10.17
 */


import java.util.ArrayList;
import java.util.HashMap;


public class HousePuzzle {
	
	/*data structure*/
	public  HashMap<String, Integer>  board     = new HashMap<String, Integer>(25); /*hold overall status*/
	public  HashMap<String, Variable> variables = new HashMap<String, Variable>(25);/*hold variables and its domains*/ 
	
	/*input*/
	public  ArrayList<String> houses  = new ArrayList<String>(5);
	public  ArrayList<String> races   = new ArrayList<String>(5);
	public  ArrayList<String> animals = new ArrayList<String>(5);
	public  ArrayList<String> foods   = new ArrayList<String>(5);
	public  ArrayList<String> drinks  = new ArrayList<String>(5);	
	
	int iter            = 0;	
	int backtrack       = 0;
	int passconsistency = 0;
	
	/*
	 * backtrackSearch
	 * */
	public boolean backtrackSearch(boolean mrv) {
		return backtrack(mrv);
	}
	/*
	 * backtrack()
	 * 
	 * */
	public boolean backtrack(boolean mrv){
		
		Variable v = null;
		if(!mrv)
			v = getUnAssignedVariable();
		else
			v = getUnAssignedVariableMRV();
		
		if(v == null){
			return false;
		}

		String name = v.getName();

		if(v.getDomainSize()>0){
			HashMap<Integer, Boolean> domain = v.getDomain();
		    for(Integer i: domain.keySet()){
		    	/*already assigned*/
		    	if(board.get(name)>0)
		    		continue;
		    	
		    	/*domain assigned */
		    	if(domain.get(i))
		    		continue;
		    	else{
		    		iter++;
		    	}
		    	v.location = (int)i;

		    	board.put(name, i);
	    		domain.put(i, true);
	    		
		    	/*check consistency*/
		    	if(consistency_house(v)){
		    		passconsistency++;
		    		/*backtrack*/
		    		if(!backtrack(mrv)){
		    			backtrack++;
		    			if(isComplete())
		    				return true;
		    			
		    			domain.put(i,false);
		    			board.put(name, -1);

		    		}
		    	}else{
		    		domain.put(i,false);
	    			board.put(name, -1);
		    	}
		    }
		}
		return false;
	}
	/*
	 * consistency test
	 * */
	public boolean consistency_house(Variable v) {
		
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

		//rule 5. the man who eats hershey bars lives in the house next to the man with the fox
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
	 * find unassigned variable without MRV heuristic
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
	
	/*
	 * find unassigned variable with MRV heuristic
	 * 
	 * */	
	
	public Variable getUnAssignedVariableMRV(){
		int min = 6;
		Variable minV = null;
		
		setDomainMRV();
		
		for(String key:board.keySet()){
			if(board.get(key)>0)
				continue;
			
			Variable v = variables.get(key);
			
			if(min>v.getDomainSize()){
				min  = v.getDomainSize();
				minV = v;
			}
				
		}
		return minV;
	}
	/*
	 * check search is complete
	 * */
	public boolean isComplete(){
		for(String key : board.keySet()){
			if(board.get(key)>0)
				continue;
			else
				return false;
		}
		return true;
	}
	/*
	 * check if there is duplicated value
	 */
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
	/*
	 * set domain as a variable has assigned
	 * */
	public void setDomainMRV(){

		
		for(String key:board.keySet()){
			/*check assigned one */
				int index = board.get(key);
				Variable v = variables.get(key);
				
				for(int i=1;i<=5;i++){
					if(index>0)
						break;
					if(i==index)
						continue;
					switch(key){
						/*races*/
						case "english":
							if(board.get("english")<0 && board.get("red") > 0 && i != board.get("red")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;
						case "red":
							if(board.get("red")<0 && board.get("english") > 0 && i != board.get("english")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
							break;							
						case "spaniard":
							if(board.get("spaniard")<0 &&  board.get("dog") > 0 && i != board.get("dog")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;
						case "dog":
							if(board.get("dog")<0 && board.get("spaniard") > 0 && i != board.get("spaniard")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;								
						case "norwegian":
							if(board.get("norwegian")<0 && i==1)
								v.insertDomain(i);
							else
								v.removeDomain(i);
							
							break;
						case "yellow":
							if(board.get("yellow")<0 && board.get("kitkat") > 0 && i != board.get("kitkat")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;	
						case "kitkat":
							if(board.get("kitkat")<0 && board.get("yellow") > 0 && i != board.get("yellow")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;	
						case "blue":
							if(board.get("blue")<0 && i==2)
								v.insertDomain(i);
							else
								v.removeDomain(i);
							
							break;	
						case "smarty":
							if(board.get("smarty")<0 && board.get("snail") > 0 && i != board.get("snail")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;	
						case "snail":
							if(board.get("snail")<0 && board.get("smarty") > 0 && i != board.get("smarty")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;	
						case "snicker":
							if(board.get("snicker")<0 && board.get("orangejuice") > 0 && i != board.get("orangejuice")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;	
						case "orangejuice":
							if(board.get("orangejuice")<0 && board.get("snicker") > 0 && i != board.get("snicker")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;		
						case "ukranian":
							if(board.get("ukranian")<0 && board.get("tea") > 0 && i != board.get("tea")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;
						case "tea":
							if(board.get("tea")<0 && board.get("ukranian") > 0 && i != board.get("ukranian")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;	
						case "japanese":
							if(board.get("japanese")<0 && board.get("milkyway") > 0 && i != board.get("milkyway")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;
						case "milkyway":
							if(board.get("milkyway")<0 && board.get("japanese") > 0 && i != board.get("japanese")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;	

						case "green":
							if(board.get("green")<0 && board.get("coffee") > 0 && i != board.get("coffee")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;	

						case "coffee":
							if(board.get("coffee")<0 && board.get("green") > 0 && i != board.get("green")){
								v.removeDomain(i);
							}else{
								v.insertDomain(i);
							}
								
							break;		
	
						case "milk":
							if(board.get("milk")<0 &&  i==3)
								v.insertDomain(i);
							else
								v.removeDomain(i);
							break;
						default:
							v.insertDomain(i);

					}
				}
		
				variables.put(key,v);
			}	
	}
	/*
	 * initialize puzzle */

	
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
	 * print result 
	 * */
	
	public void printResult(boolean mrv){
		System.out.format("\n\nHouse Puzzle: %s iternation=%d, pass=%d, backtrack=%d \n",(mrv?"MRV":"BASELINE"), iter,passconsistency, backtrack);
		System.out.println("-----------------------------------------------------");
		printBoard();
	}
	/*
	 * printBoard
	 * */
	public void printBoard(){
		for(int i=1;i<=5;i++){
			for(String key:board.keySet()){
				if(board.get(key)==i)
					System.out.format("[%-11s]",key);
			}
			System.out.println("");
		}
		System.out.println("-----------------------------------------------------");
	}
}
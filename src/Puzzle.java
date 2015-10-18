
/*
 * Author : Jiyoung Hwang
 * Description : solve 2 Constraint Satisfaction Problems (Jobs puzzle and Houses puzzle) 
 *               with/without MRV(minimum remaining variable heuristic) and compare 2 versions
 * Date   : 2015.10.17
 * 
 * */


public class Puzzle {

	public static void main(String args[]){
		HousePuzzle hp = new HousePuzzle();
		JobPuzzle jp = new JobPuzzle();
		
		jp.backtracking_search(false);
		jp.report(false);
		
		jp = new JobPuzzle();
		jp.backtracking_search(true);
		jp.report(true);
		
		/*Houses puzzle*/
		/*No MRV*/
		hp.backtrackSearch(false);
		hp.printResult(false);
		
		/*Using MRV*/
		hp = new HousePuzzle();
		hp.backtrackSearch(true);
		hp.printResult(true);
	}
}

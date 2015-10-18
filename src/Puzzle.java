
public class Puzzle {

	public static void main(String args[]){
		HousePuzzle hp = new HousePuzzle();
		
		/*Houses puzzle*/
		/*No MRV*/
		hp.backtrackSearch(false);
		hp.printResult();
		
		/*Using MRV*/
		hp = new HousePuzzle();
		hp.backtrackSearch(true);
		hp.printResult();
	}
}

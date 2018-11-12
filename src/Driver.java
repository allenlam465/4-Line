
public class Driver {

	public static void main(String[] args) {
		Board x = new Board();
		menu();
		System.out.println(x.printBoard());
	}
	
	private static void menu() {
		System.out.println("4 In a Line Game with Minmax and Alpha-Beta Pruning");
		System.out.println("\nStarting player: \n1. You\n2. Opponent");
	}

}

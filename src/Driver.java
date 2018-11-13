
public class Driver {

	public static void main(String[] args) {
		Board x = new Board();

		menu();

		System.out.println(x.printBoard());

		x.checkRow(1);
		x.checkRow(2);
		x.checkRow(3);

		x.checkCol(1);
		x.checkCol(2);
		x.checkCol(6);

	}

	private static void menu() {
		System.out.println("4 In a Line Game with Minmax and Alpha-Beta Pruning");
		System.out.println("\nStarting player: \n1. You\n2. Opponent");
	}

}

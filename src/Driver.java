import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {

		menu();

	}

	private static void menu() {
		Board game = new Board();
		Scanner s = new Scanner(System.in);
		boolean matchOver = false;

		System.out.println("4 In a Line Game with Minmax and Alpha-Beta Pruning");
		System.out.print("\nStarting player: \n1. Player\n2. Opponent \n>");

		String input = s.nextLine();

		System.out.println(input);

		switch(input) {
		
		case "1":
			System.out.println("Player goes first.");
			while(!matchOver) {
				System.out.println(game.printBoard());
				
				System.out.print("Input Move \n>");
				
				input = s.nextLine();
				
				if(game.validateMove(input)) {
					game.placePiece('O');
				}
				else {
					System.out.println("Invalid move pick another move.");
				}
				
				matchOver = true;
			}
			break;
		case "2":
			System.out.println("Opponent goes first.");
			while(!matchOver) {
				System.out.println(game.printBoard());
				
				System.out.print("Input Move \n>");
				
				input = s.nextLine();
				
				if(game.validateMove(input)) {
					game.placePiece('X');
				}
				else {
					System.out.println("Invalid move pick another move.");
				}
				
				matchOver = true;
			}
			break;

		default:
			System.out.println("Invalid input try again.");
			
		}


	}

}

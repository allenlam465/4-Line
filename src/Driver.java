import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		menu();
	}

	private static void menu() {
		int currentPlayer;
		String input;
		Board game = new Board();
		Scanner s = new Scanner(System.in);
		boolean matchOver = false;

		System.out.println("4 In a Line Game with Minmax and Alpha-Beta Pruning");
		System.out.print("Time allowed for generating moves (seconds)?\n>");

		int time = Integer.parseInt(s.nextLine());

		do{
			System.out.print("\nStarting player: \n1. Player\n2. Opponent \n>");

			input = s.nextLine();

			if(input.equals("1")) {
				currentPlayer = 1;
				System.out.println("Player goes first.");
				break;
			}
			else if(input.equals("2")) {
				currentPlayer = 2;
				System.out.println("Opponents goes first.");
				break;
			}
			else
				System.out.println("Invalid input.");

		}while(true);
		
		System.out.println(game.printBoard());
		
		
		while(!matchOver) {
			
			do{
				System.out.print("Input Move \n>");
				input = s.nextLine();
				
				if(game.validateMove(input)) {
					game.placePiece(currentPlayer);
					
					System.out.println(game.printBoard());
					System.out.println(game.evaluateBoard());
					
					System.out.print("Input Move \n>");
				}
				else {
					System.out.println("Invalid move pick another move.");
					System.out.print("Input Move \n>");	
				}
			}while(game.validateMove(input));
			
			
			currentPlayer *= -1;
			
		}
		
		/*
		 * Better to change to a while statement??
		 * 
		 * while(matchOver()){
		 * 
		 * 	if(currentPlayer == 1)
		 * 
		 *  else
		 *  
		 *  
		 *  currentPlayer = 
		 *  
		 * }
		 * 
		 */

		s.close();
	}

}

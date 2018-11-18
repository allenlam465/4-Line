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
                
                double alpha = Double.NEGATIVE_INFINITY;
                double beta  = Double.POSITIVE_INFINITY;

		//System.out.println("4 In a Line Game with Minmax and Alpha-Beta Pruning");
		//System.out.print("Time allowed for generating moves (seconds)?\n>");

		//int time = Integer.parseInt(s.nextLine());
                int time = 60;

		do{
			System.out.print("\nStarting player: \n1. Player\n2. Opponent \n>");

			input = s.nextLine();

			if(input.equals("1")) {
				currentPlayer = 1;
				System.out.println("\nPlayer goes first.");
				break;
			}
			else if(input.equals("2")) {
				currentPlayer = 2;
				System.out.println("\nOpponents goes first.");
				break;
			}
			else
				System.out.println("Invalid input.");

		}while(true);

		while(!game.checkWin('X') && !game.checkWin('O') && !game.checkDraw()) {

			System.out.println(game.printBoard());
                        //ABP(game, 1, 1, 1, 1);

			do{
				System.out.print("\nInput Move \n>");
				input = s.nextLine();

				if(game.validateMove(input)) {
					game.placePiece(currentPlayer);
					System.out.println(game.evaluateBoard());
				}
				else 
					System.out.println("Invalid move pick another move.");				

			}while(game.validateMove(input));

			currentPlayer *= -1;
			
			System.out.println(game.checkWin('X'));
			System.out.println(game.checkWin('O'));

		}

		s.close();
	}
        
        static double ABP(Board game, int currentPlayer, int alpha, int beta, int depth) {
            Board tempGame = new Board();
            tempGame = game;
            
            System.out.println("\n" + tempGame.printBoard());
            
            double v = MaxValue(tempGame, alpha, beta, depth);
            
            return v; //return action
        }
        
        
        static double MaxValue(Board state, int alpha, int beta, int depth) {
            double utility = 0;
            
            if(state.checkDraw()) {
                return 1;
            }
            else if(state.checkWin('O')) {
                return Double.POSITIVE_INFINITY;
            }
            else if(state.checkWin('X')){
                return Double.NEGATIVE_INFINITY;
            }
            else {
                utility += state.evaluateBoard();
            }
            
            double v = Double.NEGATIVE_INFINITY;
            
            //go through all actions and update v, a, or accordingly
            //call MaxValue again
            
            return v;
        }
        
        static double MinValue(Board state, int alpha, int beta) {
            double utility = 0;
            
            if(state.checkDraw()) {
                return 1;
            }
            else if(state.checkWin('O')) {
                return Double.POSITIVE_INFINITY;
            }
            else if(state.checkWin('X')){
                return Double.NEGATIVE_INFINITY;
            }
            else {
                utility += state.evaluateBoard();
            }
            
            double v = Double.POSITIVE_INFINITY;
            
            //go through all actions and update v, a, or accordingly
            //call MinValue again
            
            return v;
        }
        

}

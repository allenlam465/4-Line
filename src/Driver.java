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

		int alpha = Integer.MIN_VALUE;
		int beta  = Integer.MAX_VALUE;
		int depth = 3;

		//System.out.println("4 In a Line Game with Minimax and Alpha-Beta Pruning");
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

			//ABP(game, currentPlayer, alpha, beta, depth);

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

	static int ABP(Board game, int currentPlayer, int alpha, int beta, int depth) {
		Board tempGame = new Board();
		tempGame = game;
		int run = 0;

		System.out.println("\n" + tempGame.printBoard());

		int v = MaxValue(tempGame, alpha, beta, depth);

		return v; //return action
	}

	static int MaxValue(Board state, int alpha, int beta, int depth) {
		//if terminal test(state) then return utility(state)
		if(state.checkDraw()) {
			return 1;
		}
		else if(state.checkWin('O')) {
			return Integer.MAX_VALUE;
		}
		else if(state.checkWin('X')){
			return Integer.MIN_VALUE;
		}
		//cutoff at certain depth
		if(depth < 3) {
			depth++;
			return state.evaluateBoard();
		}

		//v <- neg inf
		int v = Integer.MIN_VALUE;

		//go through all actions and update v, a, or accordingly
		//call MaxValue again

		return v;
	}

	static int MinValue(Board state, int alpha, int beta, int depth) {
		int utility = 0;

		//if terminal test(state) then return utility(state)
		if(state.checkDraw()) {
			return 1;
		}
		else if(state.checkWin('O')) {
			return Integer.MAX_VALUE;
		}
		else if(state.checkWin('X')){
			return Integer.MIN_VALUE;
		}
		//cutoff at certain depth
		if(depth > 3) {
			depth++;
			return state.evaluateBoard();
		}

		//v <- pos inf
		int v = Integer.MAX_VALUE;

		//go through all actions and update v, a, or accordingly
		//call MaxValue again

		return v;
	}


}

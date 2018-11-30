import java.util.Random;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		menu();
	}
	
	private static int currentPlayer;

	private static void menu() {
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

		while(true){
			System.out.print("\nStarting player: \n1. Player\n2. Opponent \n>");

			input = s.nextLine();

			if(input.equals("1")) {
				currentPlayer = 1;
				System.out.println("\nPlayer goes first.");
				break;
			}
			else if(input.equals("2")) {
				currentPlayer = -1;
				System.out.println("\nOpponents goes first.");
				break;
			}
			else
				System.out.println("Invalid input.");
		}

		while(!game.checkWin('X') && !game.checkWin('O') && !game.checkDraw()) {
			//ABP(game, currentPlayer, alpha, beta, depth);
			System.out.println(game.printBoard());

			if(currentPlayer == 1) {
				while(true){
					System.out.print("\nInput Move \n>");
					input = s.nextLine();

					if(game.validateMove(input)) {
						game.placePiece(currentPlayer);
						System.out.println(game.printBoard());
						System.out.println(game.evaluateBoard());
						break;
					}
					else {
						System.out.println("Invalid move pick another move.");
					}
				}

			}
			else {
				//AI WILL MOVE HERE
				System.out.println("AI Moving...");
				aiMove(game, 200, 5, Integer.MIN_VALUE, Integer.MAX_VALUE);
			}

			currentPlayer *= -1;	
			//System.out.println(game.checkWin('X'));
			//System.out.println(game.checkWin('O'));
		}

		s.close();
	}
	
	//Add the Alpha-Beta Pruning/Minimax to this maybe
	private static void aiMove(Board game, int time, int depthGoal, int alpha, int beta) {
		Random rand = new Random();
		int row, col;
		long startTime = System.currentTimeMillis();
		
		if(game.emptyBoard()) {
			char x = (char)(rand.nextInt('F' - 'C') + 'C');
			int y = rand.nextInt((6 - 3) + 1) + 3;
			
			String move = Character.toString(x) + Integer.toString(y);
			System.out.println("AI Move: " + move);
                        //ABP(game, alpha, beta, depthGoal);
			game.validateMove(move);
			game.placePiece(currentPlayer);
		}
	}

	static int ABP(Board game, int alpha, int beta, int depthGoal) {
		Board tempBoard = game;
		int run = 0;

		System.out.println("\n" + tempBoard.printBoard());

		int v = MaxValue(tempBoard, alpha, beta, depthGoal);

		return v; //return action
	}

	static int MaxValue(Board board, int alpha, int beta, int depthGoal) {
		//if terminal test(state) then return utility(state)
		if(board.checkDraw()) {
			return 1;
		}
		else if(board.checkWin('O')) {
			return Integer.MAX_VALUE;
		}
		else if(board.checkWin('X')){
			return Integer.MIN_VALUE;
		}
		//cutoff at certain depth
		if(depthGoal < 3) {
			depthGoal++;
			return board.evaluateBoard();
		}

		//v <- neg inf
		int v = Integer.MIN_VALUE;

		//go through all actions and update v, a, or accordingly
		//call MaxValue again
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				v = Math.max(v, MaxValue(board, alpha, beta, depthGoal+1));
			}
		}

		return v;
	}

	static int MinValue(Board board, int alpha, int beta, int depthGoal) {
		int utility = 0;

		//if terminal test(state) then return utility(state)
		if(board.checkDraw()) {
			return 1;
		}
		else if(board.checkWin('O')) {
			return Integer.MAX_VALUE;
		}
		else if(board.checkWin('X')){
			return Integer.MIN_VALUE;
		}
		//cutoff at certain depth
		if(depthGoal > 3) {
			depthGoal++;
			return board.evaluateBoard();
		}

		//v <- pos inf
		int v = Integer.MAX_VALUE;

		//go through all actions and update v, a, or accordingly
		//call MaxValue again

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				v = Math.min(v, MaxValue(board, alpha, beta, depthGoal+1));
			}
		}

		return v;
	}


}

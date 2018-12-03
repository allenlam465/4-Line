import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Driver {

	public static void main(String[] args) {
		menu();
	}

	private static int currentPlayer;
	private static long timeLimit;
	private static int alpha = Integer.MIN_VALUE;
	private static int beta = Integer.MAX_VALUE;
	private static int depthLimit = 6;

	private static void menu() {
		String input;
		Board game = new Board();
		Scanner s = new Scanner(System.in);

		//System.out.println("4 In a Line Game with Minimax and Alpha-Beta Pruning");
		//System.out.print("Time allowed for generating moves (seconds)?\n>");

		//time = Integer.parseInt(s.nextLine());
		timeLimit = 25;

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
			//System.out.println(game.printBoard());
			if(currentPlayer == 1) {
				while(true){
					System.out.println(game.printBoard());
					System.out.print("\nInput Move \n>");
					input = s.nextLine();

					if(game.validateMove(input)) {
						game.placePiece(currentPlayer);
						System.out.println(game.evaluateBoard(currentPlayer));
						break;
					}
					else {
						System.out.println("Invalid move pick another move.");
					}
//					currentPlayer *= -1;	
				}
			}
			else {
				//AI WILL MOVE HERE
				System.out.println("AI Moving...\n");
				aiMove(game, timeLimit, 5);
			}
			
			currentPlayer *= -1;	

		}

		s.close();
	}

	//Add the Alpha-Beta Pruning/Minimax to this maybe
	private static void aiMove(Board game, long time, int depthGoal) {
		Random rand = new Random();
		int row, col;
		long startTime = System.currentTimeMillis();

		//Randomness introduced at empty board
		if(game.emptyBoard()) {
			char x = (char)(rand.nextInt('F' - 'C') + 'C');
			int y = rand.nextInt((6 - 3) + 1) + 3;

			String move = Character.toString(x) + Integer.toString(y);
			System.out.println("AI Move: " + move);

			game.validateMove(move);
			game.placePiece(currentPlayer);
		}
		else {

			int[] bestMove = minimax(game, currentPlayer, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
			Move aiMove = new Move(bestMove[1],bestMove[2]);

			game.validateMove(aiMove.getMove());
			game.placePiece(currentPlayer);
		}
	}

	private static int[] minimax(Board game, int player, int depth, int alpha, int beta) {

		Set<String> possibleMoves = game.getPossibleMoves();

		int currentScore;
		int bestRow = -1;
		int bestCol = -1;

		if(possibleMoves.isEmpty() || depthLimit == depth ) {
			currentScore = game.evaluateBoard(player);
			return new int[] {currentScore, bestRow, bestCol};				
		}
		else {
			for(String pos : possibleMoves) {
				
				Move possible = new Move(pos);
				game.placePiece(player);

				if (player == 1) {  
					currentScore = minimax(game, player *= -1, depth+=1, alpha, beta)[0];
					if (currentScore > alpha) {
						alpha = currentScore;
						bestCol = possible.getX();
						bestRow = possible.getY();
					}
				} else {
					currentScore = minimax(game, player *= -1, depth+=1, alpha, beta)[0];
					if (currentScore < beta) {
						beta = currentScore;
						bestCol = possible.getX();
						bestRow = possible.getY();
					}
				}

				game.getBoard()[possible.getX()][possible.getY()] = '-';

				if(alpha >= beta)
					break;

			}

			return new int[] {(player == currentPlayer) ? alpha : beta, bestRow, bestCol};

		}

	}

	static int ABP(Board game, int player, int alpha, int beta, int depth) {
		Board tempBoard = new Board();
		tempBoard = game;
		int run = 0;

		//System.out.println("\n" + tempBoard.printBoard());

		int v = MaxValue(tempBoard, currentPlayer, alpha, beta, depth);

		return v; //return action
	}

	static int MaxValue(Board board, int player, int alpha, int beta, int depth) {
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
		if(depth < 3) {
			depth++;
			return board.evaluateBoard(player);
		}

		//v <- neg inf
		int v = Integer.MIN_VALUE;

		//go through all actions and update v, a, or accordingly
		//call MaxValue again
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				v = Math.max(v, MaxValue(board, player, alpha, beta, depth+1));
			}
		}

		return v;
	}

	static int MinValue(Board board, int player, int alpha, int beta, int depth) {
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
		if(depth > 3) {
			depth++;
			return board.evaluateBoard(player);
		}

		//v <- pos inf
		int v = Integer.MAX_VALUE;

		//go through all actions and update v, a, or accordingly
		//call MaxValue again

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				v = Math.min(v, MaxValue(board, player, alpha, beta, depth+1));
			}
		}

		return v;
	}


}

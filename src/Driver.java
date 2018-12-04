import java.util.ArrayList;
import java.util.HashSet;
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

		System.out.println("4 In a Line Game with Minimax and Alpha-Beta Pruning");
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
			System.out.println(game.printBoard());
			if(currentPlayer == 1) {
				while(true){
					//System.out.println(game.printBoard());
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
				}
			}
			else {
				System.out.println("AI Moving...\n");
				aiMove(game, timeLimit, 5);
				
			}

			System.out.print(game.checkWin('X') + " " + game.checkWin('O'));
			currentPlayer *= -1;	

		}

		s.close();
	}

	//Add the Alpha-Beta Pruning/Minimax to this maybe
	private static void aiMove(Board game, long time, int depthGoal) {
		Random rand = new Random();
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
			Object[] bestMove = minimax(game, currentPlayer, 0, alpha, beta );
			System.out.println("Out of loop");			
			Move aiMove = new Move((String)bestMove[1]);
			
			System.out.println(aiMove.getMove());

			game.validateMove(aiMove.getMove());
			game.placePiece(currentPlayer);
		}
	}

	private static Object[] minimax(Board game, int player, int depth, int alpha, int beta) {
		ArrayList<String> moveList;
		Set<String> possibleMoves = new HashSet<>();
		ArrayList<String> playerMoves =  game.currentPlayerMoves(player*-1);

		for(int i = 0; i < playerMoves.size(); i++) {
			possibleMoves.addAll(game.adjacencyCheck(playerMoves.get(i)));
		}		

		if(possibleMoves.isEmpty()) {
			moveList = new ArrayList<>(game.getEmptySpace());			
		}
		else {
			moveList = new ArrayList<>(possibleMoves);			
		}

		int currentScore = 0, bestScore;
		String bestMove = "";

		if(depth == depthLimit) {
			Object[] x = {game.evaluateBoard(player), moveList.get(0)};
			return x;
		}

		bestScore = alpha;

		while(moveList.size() > 0) {
			System.out.println(moveList.size());
			Board testBoard = new Board(game);
			String newMove = moveList.get(0);

			testBoard.placePiece(player, newMove);
			Object[] temp = minimax(testBoard, player*=-1, depth+1, alpha, -bestScore );
			currentScore = -(Integer)temp[0];

			if(currentScore > bestScore) {
				bestScore = currentScore;
				bestMove = newMove;
			}
			if(bestScore > currentScore) {
				//System.out.println("PRUNED");
				Object[] x = {bestScore, bestMove};
				return x;
			}
			moveList.remove(0);
			System.out.println("Stuck");
		}
		Object[] x = {bestScore, bestMove};
		return x;
	}

}
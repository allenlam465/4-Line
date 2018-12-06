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
						System.out.println(game.evaluateBoard());
						break;
					}
					else {
						System.out.println("Invalid move pick another move.");
					}
				}
			}
			else {
				System.out.println("AI Moving...\n");
				long startTime = System.currentTimeMillis();
				aiMove(game, 5);
				long endTime = System.currentTimeMillis();

				System.out.println(((endTime - startTime) / 1000) + " seconds" );
			}

			//System.out.print(game.checkWin('X') + " " + game.checkWin('O'));
			currentPlayer *= -1;	

		}

		s.close();
	}

	//Add the Alpha-Beta Pruning/Minimax to this maybe
	private static void aiMove(Board game, int depthGoal) {
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
			int[] bestMove = miniMaxAB(game, startTime, currentPlayer, 0, alpha, beta );
			Move aiMove = new Move(bestMove[1],bestMove[2]);

			System.out.println(bestMove[0]);
			System.out.println(aiMove.getMove());

			game.validateMove(aiMove.getMove());
			game.placePiece(currentPlayer);
		}
	}

	private static int[] miniMaxAB(Board game, long time, int depth, int player, int alpha, int beta) {
		Board testBoard = new Board(game);
		int score;
		int bestRow = -1, bestCol = -1;

		ArrayList<Move> piecesAdjacency = game.adjacencyCheck();

		if(piecesAdjacency.size() == 1) {
			score = game.evaluateBoard();
			bestRow = piecesAdjacency.get(0).getX();
			bestCol = piecesAdjacency.get(0).getY();
			return new int[] {score, bestRow, bestCol};
		}
		else if(piecesAdjacency.isEmpty() || depth == 0 || (System.currentTimeMillis() - time >= timeLimit )) {
			score = game.evaluateBoard();
			return new int[] {score, bestRow, bestCol};
		}
		else {
			for(Move move : piecesAdjacency) {
				if(player == -1) {
					testBoard.placePiece(player, move.getMove());
					score = miniMaxAB(testBoard, time, depth - 1, player * -1, alpha, beta)[0];
					if(score > alpha) {
						alpha = score;
						bestRow = move.getX();
						bestCol = move.getY();
					}
				}
				else {
					testBoard.placePiece(player, move.getMove());
					score = miniMaxAB(testBoard, time, depth - 1, player * -1, alpha, beta)[0];
					if(score < beta) {
						beta = score;
						bestRow = move.getX();
						bestCol = move.getY();
					}
				}

				if(alpha >= beta)
					break;
			}
			return new int[]{(player == -1) ? alpha : beta, bestRow, bestCol};
		}
	}

}

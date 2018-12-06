import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		menu();
	}

	private static Board board;
	private static Board cState;
	private static int currentPlayer;
	private static long timeLimit = 25;
	private static long startTime = 0;
	private static int alpha = Integer.MIN_VALUE;
	private static int beta = Integer.MAX_VALUE;
	private static int depthLimit = 16;
        private static ArrayList<String> playerMoves = new ArrayList<String>();
        private static ArrayList<String> aiMoves = new ArrayList<String>();
        

	private static void menu() {
		String input;
		board = new Board();
		Scanner s = new Scanner(System.in);

		System.out.println("4 In a Line board with Minimax and Alpha-Beta Pruning");
		System.out.print("Time allowed for generating moves (seconds)?\n>");

		timeLimit = Integer.parseInt(s.nextLine());

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

		while(!board.checkWin('X') && !board.checkWin('O') && !board.checkDraw()) {
			System.out.println(board.printBoard());
                        printMoves(playerMoves, aiMoves);
                        
			if(currentPlayer == 1) {
				while(true){
					System.out.print("\nInput Move \n>");
					input = s.nextLine();
                                        
                                        if(playerMoves.size() < 5) { 
                                            playerMoves.add(input);
                                        }
                                        else {
                                            playerMoves.remove(4);
                                            playerMoves.add(input);
                                        }

					if(board.validateMove(input)) {
						board.placePiece(currentPlayer);
						break;
					}
					else {
						System.out.println("Invalid move pick another move.");
					}
				}
				
				currentPlayer = -1;	
			}
			else {
				System.out.println("AI Moving...\n");
				startTime = System.currentTimeMillis();
				Move aiMove = aiMove(startTime, depthLimit);
				long endTime = System.currentTimeMillis();

				System.out.println(((endTime - startTime)) + " seconds" );
				
                                if(aiMoves.size() < 5) { 
                                    aiMoves.add(aiMove.getMove());
                                }
                                else {
                                    aiMoves.remove(4);
                                    aiMoves.add(aiMove.getMove());
                                }
                                
				board.validateMove(aiMove.getMove());
				board.placePiece(currentPlayer);
				
				currentPlayer = 1;	
			}
		}

		if (board.checkWin('X')) {
			System.out.println("You won!");
		} else if (board.checkWin('O')) {
			System.out.println("You lost!");
		} else {
			System.out.println("It's a draw!");
		}

		s.close();
	}

	//Add the Alpha-Beta Pruning/Minimax to this maybe
	private static Move aiMove(long startTime, int depthGoal) {
		Random rand = new Random();
		Move aiMove = null;
		cState = new Board(board);

		//Randomness introduced at empty board
		if(board.emptyBoard()) {
			char x = (char)(rand.nextInt('F' - 'C') + 'C');
			int y = rand.nextInt((6 - 3) + 1) + 3;
			String move = Character.toString(x) + Integer.toString(y);
			
			aiMove = new Move(move);
		}
		else if(board.getMoveHistory().size() == 1) {
			ArrayList<Move> adjacent = board.adjacencyCheck(board.getMoveHistory().get(0).getMove());
			aiMove = adjacent.get(rand.nextInt(adjacent.size()));
		}
		else {
			int[] bestMove = miniMaxAB(depthGoal, currentPlayer, alpha, beta );
			aiMove = new Move((int)bestMove[1], (int)bestMove[2]);
		}
		
		System.out.println(aiMove.getMove());
		
		System.out.println("AI Move: " + aiMove.getMove());
		
		return aiMove;
	}

	private static int[] miniMaxAB(int depth, int player, int alpha, int beta) {
		int score;
		int bestRow = -1, bestCol = -1;

		ArrayList<Move> possibleMoves = cState.possibleMoves();

		if(possibleMoves.size() == 1) {
			score = cState.evaluateBoard();
			bestRow = possibleMoves.get(0).getX();
			bestCol = possibleMoves.get(0).getY();
			return new int[] {score, bestRow, bestCol};
		}
		else if(possibleMoves.isEmpty() || depth == 0 || (System.currentTimeMillis() - startTime >= timeLimit )) {
			score = cState.evaluateBoard();
			return new int[] {score, bestRow, bestCol};
		}
		else {
			for(Move move : possibleMoves) {
				if(player == -1) {
					cState.placePiece(player, move.getMove());
					score = miniMaxAB(depth - 1, 1, alpha, beta)[0];
					if(score > alpha) {
						alpha = score;
						bestRow = move.getX();
						bestCol = move.getY();
					}
				}
				else {
					cState.placePiece(player, move.getMove());
					score = miniMaxAB(depth - 1, -1, alpha, beta)[0];
					if(score < beta) {
						beta = score;
						bestRow = move.getX();
						bestCol = move.getY();
					}
				}

				cState.removePiece(move);

				if(alpha >= beta)
					break;

			}
			return new int[]{(player == -1) ? alpha : beta, bestRow, bestCol};
		}

	}
        
        private static void printMoves(ArrayList<String> playerMoves, ArrayList<String> aiMoves) { 
            System.out.println("\nPlayer vs. Opponent");
            
            Iterator<String> playerIt = playerMoves.iterator();
            Iterator<String> aiIt = aiMoves.iterator();
            
            while(playerIt.hasNext() && aiIt.hasNext()){
                System.out.print("   " + playerIt.next());
                System.out.print("       ");
                System.out.println(aiIt.next());
            }
            
        }
}
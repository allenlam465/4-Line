import java.util.Random;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		menu();
                
                
                
                //String moveTest = "";
                //moveTest = convertMove(10);
                //System.out.println("move: " + moveTest);
	}
	
	private static int currentPlayer;

	private static void menu() {
		String input;
		Board game = new Board();
		Scanner s = new Scanner(System.in);

		int alpha = Integer.MIN_VALUE;
		int beta  = Integer.MAX_VALUE;
		int depthGoal = 5;

		//System.out.println("4 In a Line Game with Minimax and Alpha-Beta Pruning");
		//System.out.print("Time allowed for generating moves (seconds)?\n>");

		//int time = Integer.parseInt(s.nextLine());
		int time = 600;

		while(true){
			//System.out.print("\nStarting player: \n1. Player\n2. Opponent \n>");

			//input = s.nextLine();
                        
                        input = "1";

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
				System.out.println("\nAI Moving...");
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

            int abMove = 0;
            String aiMove = "";

            abMove = AlphaBetaSearch(game, alpha, beta, depthGoal);

            aiMove = convertMove(abMove);
            
            System.out.println("\nAI Move: " + abMove + " " + aiMove);
            System.out.println();
            
            game.validateMove(aiMove);
            game.placePiece(currentPlayer);

//            if(game.emptyBoard()) {
//                //char x = (char)(rand.nextInt('F' - 'C') + 'C');
//                //int y = rand.nextInt((6 - 3) + 1) + 3;
//
//                //String move = Character.toString(x) + Integer.toString(y);
//                System.out.println("AI Move: " + aiMove);
//                game.validateMove(aiMove);
//                game.placePiece(currentPlayer);
//            }
	}

	static int AlphaBetaSearch(Board game, int alpha, int beta, int depthGoal) {
            int run = 0;

            //System.out.println("\n" + game.printBoard());

            int v = MaxValue(game, alpha, beta, depthGoal);

            return v; //return action
	}

	static int MaxValue(Board game, int alpha, int beta, int depthGoal) {
            int indexOfBestMove = -1;
            
            //if terminal test(state) then return utility(state)
            if(game.checkDraw()) {
                return game.evaluateBoard();
            }
            else if(game.checkWin('O')) {
                return Integer.MAX_VALUE;
            }
            else if(game.checkWin('X')){
                return Integer.MIN_VALUE;
            }
            //cutoff at certain depth
            if(depthGoal != 0) {
                depthGoal--;
                return game.evaluateBoard();
            }

            //v <- neg inf
            int v = Integer.MIN_VALUE;
            

            //go through all actions and update v, a, or b accordingly
            //call MaxValue again
            for(Integer iMove : game.getAvailableMoves()) {
                Board tempBoard = game;
                
                String move = "";
                
                move = convertMove(iMove);
                
                tempBoard.validateMove(move);
                
                v = Math.max(v, MinValue(tempBoard, alpha, beta, depthGoal));
                
                if(v >= beta) {
                    return iMove;
                }
                
                alpha = Math.max(alpha, v);
                
            }

            return v;
	}

	static int MinValue(Board game, int alpha, int beta, int depthGoal) {
            int indexOfBestMove = -1;

            //if terminal test(state) then return utility(state)
            if(game.checkDraw()) {
                return game.evaluateBoard();
            }
            else if(game.checkWin('O')) {
                return Integer.MAX_VALUE;
            }
            else if(game.checkWin('X')){
                return Integer.MIN_VALUE;
            }
            //cutoff at certain depth
            if(depthGoal != 0) {
                depthGoal--;
                return game.evaluateBoard();
            }

            //v <- pos inf
            int v = Integer.MAX_VALUE;

            //go through all actions and update v, a, or b accordingly
            //call MaxValue again
            for(Integer iMove : game.getAvailableMoves()) {
                Board tempBoard = game;
                
                String move = "";
                
                move = convertMove(iMove);
                
                tempBoard.validateMove(move);
                
                v = Math.max(v, MinValue(tempBoard, alpha, beta, depthGoal));
                
                if(v <= alpha) {
                    return iMove;
                }
                
                alpha = Math.min(alpha, v);
                
            }


            return v;
	}
        
        static public String convertMove(int move) {
            
            String sMove = "";
            
            int digits = (int)(Math.log10(move)+1);
            
            int firstDigit = Integer.parseInt(Integer.toString(move).substring(0, 1));
            int secondDigit = 0;
            
            //System.out.println("digit 1: " + firstDigit);
            
            if(digits == 2) {
                secondDigit = move%10;

                //System.out.println("digit 2: " + secondDigit);

            }
            
            if(move >= 0 && move <= 7) {
                sMove = "A";
                
                if(move == 0) {
                    sMove = "A1";
                }
                else if(move == 1) {
                    sMove = "A2";
                }
                else if(move == 2) {
                    sMove = "A3";
                }
                else if(move == 3) {
                    sMove = "A4";
                }
                else if(move == 4) {
                    sMove = "A5";
                }
                else if(move == 5) {
                    sMove = "A6";
                }
                else if(move == 6) {
                    sMove = "A7";
                }
                else if(move == 7) {
                    sMove = "A8";
                }
            }
            else if(move >= 8 && move <= 15) {
                sMove = "B";
            }
            else if(move >= 16 && move <= 23) {
                sMove = "C";
            }
            else if(move >= 24 && move <= 31) {
                sMove = "D";
            }
            else if(move >= 32 && move <= 39) {
                sMove = "E";
            }
            else if(move >= 40 && move <= 47) {
                sMove = "F";
            }
            else if(move >= 48 && move <= 55) {
                sMove = "G";
            }
            else if(move >= 56 && move <= 63) {
                sMove = "H";
            }
            
            if(digits == 2) {
                move %= 7;
                sMove += Integer.toString(move);
            }
            
            return sMove;
        }


}

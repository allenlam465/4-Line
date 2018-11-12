import java.util.*;

public class Board {
	
	private char[][] board;
        private HashSet<Integer> availableMoves;    //keeps track of valid moves
        
        Board() {
            this.board = new char[8][8];
		

            availableMoves = new HashSet<>();
            
            initializeBoard();
        }
	
        private void initializeBoard() {
            for(int i = 0; i < 8; i++) {
		for(int j = 0; j < 8; j++) {
                    board[i][j] = '-';
		}
            }
            
            availableMoves.clear();
                
            for (int i = 0; i < 64; i++) {
                availableMoves.add(i);
            }
        }
        
	
	public void setBoard(char[][] board) {
		this.board = board;
	}
	
	public char[][] getBoard() {
		return board;
	}
        
	public String printBoard() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("  1 2 3 4 5 6 7 8 	Player vs. Oppenent");
		
		for(int i = 0; i < 8; i ++) {
			sb.append("\n" + Character.toString(((char)(65 + i))) + " ");
			for(int j = 0; j < 8; j++) {
				sb.append(board[i][j] + " ");
			}
			sb.append("	   " + "Moves Here");	
		}
		
		return sb.toString();
	}
        
        // 0 -> no move
        // 1 -> X
        // 2 -> O
        public int[][] getIntBoard() {
            int[][] intBoard = new int[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if(this.board[i][j] == '-') {
                        intBoard[i][j] = 0;
                    }
                    else if(this.board[i][j] == 'X') {
                        intBoard[i][j] = 1;
                    }
                    else {
                        intBoard[i][j] = 2;
                    }
                }
            }
            return intBoard;
        }
        
	public void printIntBoard() {
            int[][] intBoard = new int[8][8];
            intBoard = getIntBoard();
            
            for (int i = 0; i < 8; i++) {
                System.out.println();
                for (int j = 0; j < 8; j++) {
                    System.out.print(intBoard[i][j]);
                }
            }
	}
	
        
        public void checkRow(int row) { 
            for (int i = 0; i < 8; i++) {
                if(board[row][i] != board[row][i+1]) {
                    break;
                }
                if(i == 7) {
                    //WIN
                    System.out.println("datWIN");
                }
            }
        }
	
}

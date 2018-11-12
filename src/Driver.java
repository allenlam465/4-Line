
public class Driver {

	public static void main(String[] args) {
		Board x = new Board();
                
                System.out.println(x.printBoard());
                
                //x.printIntBoard();
                //System.out.println();
                
                x.checkRow(1);
                x.checkRow(2);
                x.checkRow(3);
                
                x.checkCol(1);
                x.checkCol(2);
                x.checkCol(6);
		
		
	}

}

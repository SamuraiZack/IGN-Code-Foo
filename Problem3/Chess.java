import java.util.ArrayList;

/* 
 * Program will calculate how many moves a Knight will take for it to cover a standard 
 * 8x8 Chess board.
 */
public class Chess {

	// 8x8 board
	private static int rows = 8;
	private static int cols = 8;

	// The board itself
	private static int[][] board;
	// All the knight's movements
	private static int[][] knight = {{1,-2}, {-1,-2}, {1,2}, {-1, 2}, {2,-1}, {-2,-1}, {2,1}, {-2,1}};
	
	// Just a count of all possible knight's movement
	private static int knightMoves = 8;
	
	// All the possible places for each spot
	private static ArrayList<Integer> possiblePlaces = new ArrayList<Integer>();
	
	
	// The minimal amount of moves
	private static int minMove = 0;
	// The amount of the board that is covered
	private static int boardCovered = 0;
	// The arraylist which will hold the numbers for how much the board is covered with each move
	private static ArrayList<Integer> graphPoints = new ArrayList<Integer>();
	
	// Will call start(row,col) which will calculate the number of moves it takes for a Knight
	// to cover a standard Chess board
    public static void main(String[] args) {
    	// Create a new board
        createBoard();
        // Add the starting graph point (0,0)
		graphPoints.add(minMove);
		graphPoints.add(boardCovered);
		// Start the calculation with one of the Knight's starting position
        start(0, 1);
        // Prints out the answer
        System.out.println("The minimal amount of movement for the knight to cover the whole board is " + minMove);
        // The points for the graph
        System.out.println("Number of moves \t Covered the board");
        for (int i = 0; i < graphPoints.size(); i = i + 2){
        	System.out.println(graphPoints.get(i) + "\t" + graphPoints.get(i + 1));
        }
        // And also the finished looking board
        System.out.println("The Board itself");
        for (int i = 0; i < rows; i++)
        {
        	for (int j = 0; j < cols; j++)
        	{
        		System.out.print("" + board[i][j]);
        	}
        	System.out.println("");
        }
    }

	// Create an 8x8 board and set each location to 0
	private static void createBoard() {

		board = new int[rows][cols];
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				board[i][j] = 0;
			}
		
		}
	}	
	
	private static void start(int row, int col) {
	
		// Increment the move count
		minMove++;
		boardCovered++;
		graphPoints.add(minMove);
		graphPoints.add(boardCovered);
		
		// Mark that we've visited the place
		board[row][col] = 1;
		// Find all possible places to move to
		possiblePlaces = findPlaces(row, col);
		int kRow;
		int kCol;
		// The next place to go
		int nextRow = 0;
		int nextCol = 0;
		// The count of the place with lowest number of moves
		// Initialize to some random number in this case, row * col
		int min = rows * cols;
		
		int count = 0;
		// go through all possible places
		// i + 2 because arrayList contains both rows and columns of a possible place
		for (int i = 0; i < possiblePlaces.size(); i = i + 2) {
			kRow = possiblePlaces.get(i);
			kCol = possiblePlaces.get(i+1);
			
			// Get the number of possible places the next spot can go
			count = (findPlaces(kRow, kCol).size())/2;

			// We want to find the next spot which has the minimum amount of spaces to go
			if (count < min) {
				nextRow = kRow;
				nextCol = kCol;
				min = count;
			}
		}
		
		// If the amount of places to go are 0 then we know that the board is cover!
		if (possiblePlaces.size() != 0) {
			start(nextRow, nextCol);
		}
	
	}
	
	// Find all possible places the Knight can move
	private static ArrayList<Integer> findPlaces (int currRow, int currCol) {
	
		// The knight's current possible movement position for row and column
		int kRow;
		int kCol;
		ArrayList<Integer> places = new ArrayList<Integer>();
		
		for (int i = 0; i < knightMoves; i++) {
			
			// Get the next possible movement
			kRow = knight[i][0];
			kCol = knight[i][1];
			
			// Want to skip the move that will go off the board!
			if ((currRow+kRow < 0) || (currRow+kRow >= rows) || (currCol+kCol >= cols) || (currCol+kCol < 0))
			{
				continue;
			}
			
			// Get the location of next spot
			int location = board[currRow+kRow][currCol+kCol];
			// If location == 0 then we can move to that spot so increment the count and add it to arrayList
			// location == 0 when that spot has not been covered yet
			if (location == 0)
			{
				places.add(currRow+kRow);
				places.add(currCol+kCol);
			}
		}
		return places;
	}	
	
}
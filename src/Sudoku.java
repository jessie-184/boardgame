import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Sudoku {
	private int[][] board = new int[9][9];
	
	public Sudoku (String filename) {
		try {
			load(filename);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		configureBoard();
	}
	 
	public int get(int row, int col) {
		if (row < 0 || col < 0 || row >= board.length || col >= board.length) {
			return -1;
		}
		return board[row][col];
	}
	
	public void set(int row, int col, int val) {
		if (row < 0 || col < 0 || row >= board.length || col >= board.length) {
			//do nothing
		}
		if (!isValid(row, col, val)) {
			System.out.println("Invalid Value For This Place!");
		} 
		board[row][col] = val;
	}
	
	//we check if a possible number is already in a row
	private boolean isInRow(int row, int number) {
		for (int i = 0; i < 9; i++) {
			if (board[row][i] == number)
				return true;
		} return false;
	}
	
	//we check if a possible number is already in a row
	private boolean isInCol(int col, int number) {
		for (int i = 0; i < 9; i++) {
			if (board[i][col] == number)
				return true;
		} return false;
	}
	
	//we check if a possible number is already in its 3x3 box
	private boolean isInBox(int row, int col, int number) {
		int r = row - row % 3;
		int c = col - col % 3;
		
		for (int i = r; i < r + 3; i++) {
			for (int j = c; j < c + 3; j++) {
				if (board[i][j] == number)
					return true;
			}
		} return false;
	}
	
	public boolean isValid(int row, int col, int number) {
		return !isInRow(row, number) &&  
			   !isInCol(col, number) &&
			   !isInBox(row, col, number);
	}
	
	public boolean solve() {
        for (int row = 0; row < 9; row++) {
        	for (int col = 0; col < 9; col++) {
        		// we search an empty cell
        		if (board[row][col] == 0) {
        			// we try possible numbers
        			for (int number = 1; number <= 9; number++) {
        				if (isValid(row, col, number)) {
        					// number is valid. it respects sudoku's constraints
        					board[row][col] = number;

        					if (solve()) { // we start backtracking recursively
        						return true;
        					} else { // if not a solution, we empty the cell and we continue
        						board[row][col] = 0;
        					}
        				}
        			} return false; // we return false
        		}
        	}
         } return true; // game solved
	}
	
	public void load(String filename) throws IOException {
		Scanner scan = new Scanner(new FileInputStream(filename));
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				int val = scan.nextInt();
				board[r][c] = val;
			}
		}
	}
	
	public void configureBoard() {
		Random rand = new Random();
		int[][] temp1 = new int[9][9];
		int[][] temp2 = new int[9][9];
		int a = rand.nextInt(3);
		int b = rand.nextInt(3);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				temp1[i][(j-3*a+9)%9] = board[i][j];
			}
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				temp2[(j-3*b+9)%9][i] = temp1[j][i];
			}
		}
		board = temp2;
	}
	
	public void showHint() {
		boolean hint = false;
		int[][] temp = new int[9][9];
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				temp[r][c] = board[r][c];
			}
		}
		while(!hint) {
			Random rand = new Random();
			int a = rand.nextInt(9);
			int b = rand.nextInt(9);
			solve();
			if (temp[a][b] == 0) {
				temp[a][b] = board[a][b];
				hint = true;
			}
		}
		board = temp;
	}
	
//	public boolean gameOver() {
//		//check that there are still open spots
//		for (int i = 0; i < 9; i++) {
//			for (int j = 0; j < 9;  j++) {
//				if (board[i][j] == 0) {
//					return false;
//				}
//			}
//		} return true;
//	}
	
	public String toString() {
		String result = "";
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				int val = get(r, c);
				if (val == 0) {
					result += "_ ";
				} else {
					result += val + " ";
				} 
			}
			result += "\n";
		}
		return result;
	}
	
//	public static void main(String[] args) {
//		Sudoku s1 = new Sudoku("hard.txt");
//		System.out.println(s1);
//		s1.showHint();
//		System.out.println(s1);
//		s1.solve();
//		System.out.println(s1);
//	}
}

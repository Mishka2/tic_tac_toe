import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
	public static int[][] board= { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
	public static int self= 1;
	public static int computer= 2;
	public static int[] compTurn= { 5, 5 };
	public static int[] compWinningTurn= { 5, 5 };

	public static void main(String[] args) {
		printWelcomeScreen();

		Boolean selfWin= false;
		Boolean compWin= false;

		System.out.println("");
		int start= pickStartingPlayer();
		if (start == 1) {
			System.out.println("Starting player: You!");
		} else if (start == 2) {
			System.out.println("Starting player: Computer!");
			int[] firstTurn= { 1, 1 };
			nextCompTurn(firstTurn);
		}

		while (!(selfWin || compWin) && checkEmpty()) {
			displayBoard();

			selfWin= choosePlay();
			if (!selfWin && checkEmpty()) {
				if (compWinningTurn[0] == 5) {
					compWin= nextCompTurn(compTurn);
				} else if (board[compWinningTurn[0]][compWinningTurn[1]] == 0) {
					compWin= nextCompTurn(compWinningTurn);
				} else {
					compWinningTurn[0]= 5;
					compWinningTurn[1]= 5;
					compWin= nextCompTurn(compTurn);
				}
			}
		}
		displayBoard();
		if (selfWin) {
			System.out.println("");
			System.out.println("");
			System.out.println("You win!");
		} else if (compWin) {
			System.out.println("");
			System.out.println("");
			System.out.println("You lose!");
		} else if (!checkEmpty()) {
			System.out.println("");
			System.out.println("");
			System.out.println("No winner. It's a tie!");
		}
	}

	public static int pickStartingPlayer() {
		Random rn= new Random();
		int player= 1 + rn.nextInt(2);
		return player;
	}

	public static void printWelcomeScreen() {
		System.out.println("Welcome to Tic Tac Toe! You are '1' and the computer is '2'");
		System.out.println("When prompted you will input an x and y coordinate:");
		System.out.println("");
		System.out.println("y       3  X  [2,3]  X");
		System.out.println("|       2  X    X    X");
		System.out.println("V       1  X    X    X");
		System.out.println("x-->       1    2    3");
		System.out.println("");

		Scanner in= new Scanner(System.in);
		boolean play= false;
		while (!play) {
			System.out.println("Enter 'y' when ready to begin");
			play= in.next().equals("y");
		}
	}

	public static int[] computerChooseRand() {
		ArrayList<Integer> x_pos= new ArrayList<>();
		ArrayList<Integer> y_pos= new ArrayList<>();
		for (int i= 0; i < 3; i++ ) {
			for (int j= 0; j < 3; j++ ) {
				if (board[i][j] == 0) {
					x_pos.add(i);
					y_pos.add(j);
				}
			}
		}

		Random rn= new Random();
		int[] randPoint= new int[2];
		if (x_pos.size() != 0) {
			int randIndex= rn.nextInt(x_pos.size());
			randPoint[0]= x_pos.get(randIndex);
			randPoint[1]= y_pos.get(randIndex);
		} else {
			randPoint[0]= 10;
			randPoint[1]= 10;
		}
		return randPoint;
	}

	public static int[] computerChooseRandCorner() {
		ArrayList<Integer> x_pos= new ArrayList<>();
		ArrayList<Integer> y_pos= new ArrayList<>();
		for (int i= 0; i < 3; i= i + 2) {
			for (int j= 0; j < 3; j= j + 2) {
				if (board[i][j] == 0) {
					x_pos.add(i);
					y_pos.add(j);
				}
			}
		}

		Random rn= new Random();
		int[] randPoint= new int[2];
		if (x_pos.size() != 0) {
			int randIndex= rn.nextInt(x_pos.size());
			randPoint[0]= x_pos.get(randIndex);
			randPoint[1]= y_pos.get(randIndex);
		} else {
			randPoint[0]= 10;
			randPoint[1]= 10;
		}

		return randPoint;

	}

	public static Boolean computerTurn() {
		int[] randCorner= computerChooseRandCorner();
		if (randCorner[0] != 10) {
			return changeBoard(randCorner[0] + 1, randCorner[1] + 1, computer);
		} else {
			int[] randPoint= computerChooseRand();
			return changeBoard(randPoint[0] + 1, randPoint[1] + 1, computer);
		}
	}

	public static void compCanWin() {
		if (compTurn[0] != 5) {
			if (compWinningTurn[0] == 5) {
				checkWin(computer, compTurn[0], compTurn[1]);
				compWinningTurn[0]= compTurn[0];
				compWinningTurn[1]= compTurn[1];
			}
		}
	}

	public static Boolean checkEmpty() {
		Boolean empty= false;
		for (int i= 0; i < 3; i++ ) {
			for (int j= 0; j < 3; j++ ) {
				if (board[i][j] == 0) {
					empty= true;
				}
			}
		}
		return empty;
	}

	public static void displayBoard() {
		for (int i= 2; i >= 0; i-- ) {
			System.out.println("");
			for (int j= 0; j < 3; j++ ) {
				System.out.print(board[i][j] + "  ");
			}
		}
	}

	public static Boolean choosePlay() {
		boolean valid= false;
		Scanner in= new Scanner(System.in);
		int xPlay= 0;
		int yPlay= 0;
		while (!valid) {
			System.out.println("");
			System.out.println("");
			System.out.println("Your play --> [x,y]:");
			String play= in.next();

			if (play.length() == 5) {
				if (play.charAt(0) == '[' && Character.isDigit(play.charAt(1)) && play.charAt(4) == ']' &&
					Character.isDigit(play.charAt(3)) && play.charAt(2) == ',') {
					yPlay= Character.getNumericValue(play.charAt(1));
					xPlay= Character.getNumericValue(play.charAt(3));
					if (yPlay < 4 && yPlay > 0 && xPlay > 0 && xPlay < 4) {
						if (board[xPlay - 1][yPlay - 1] == 0) {
							valid= true;
						} else {
							System.out.println("Already played!");
						}
					} else {
						System.out.println("Invalid game play");
					}
				} else {
					System.out.println("Invalid game play");
				}
			} else {
				System.out.println("Invalid game play");
			}
		}

		return changeBoard(xPlay, yPlay, self);
	}

	public static Boolean nextCompTurn(int[] coordinate) {
		int[] coordinatePoint= new int[2];
		coordinatePoint[0]= coordinate[0];
		coordinatePoint[1]= coordinate[1];
		if (coordinate[0] == 5 || board[coordinatePoint[0]][coordinatePoint[1]] != 0) {
			return computerTurn();
		} else {
			compCanWin();
			return changeBoard(coordinatePoint[0] + 1,
				coordinatePoint[1] + 1, 2);
		}
	}

	public static void setCompTurn(int x, int y) {
		compTurn[0]= x;
		compTurn[1]= y;
	}

	public static Boolean checkWin(int player, int x, int y) {
		int checkX= x;
		int checkY= y;
		Boolean win= false;
		Boolean above= false;
		Boolean below= false;
		Boolean left= false;
		Boolean right= false;
		Boolean topLeft= false;
		Boolean topRight= false;
		Boolean bottomRight= false;
		Boolean bottomLeft= false;

		setCompTurn(5, 5);

		// check one above
		if (checkX - 1 >= 0 && board[checkX - 1][checkY] == player) {
			above= true;
			// check one more above
			if (checkX - 2 >= 0) {
				if (board[checkX - 2][checkY] == player) {
					win= true;
				} else if (board[checkX - 2][checkY] == 0) {
					setCompTurn(checkX - 2, checkY);
				}
			} else if (board[checkX + 1][checkY] == 0) {
				setCompTurn(checkX + 1, checkY);
			}
		}
		// check left
		if (checkY - 1 >= 0 && board[checkX][checkY - 1] == player) {
			left= true;
			// check one more left
			if (checkY - 2 >= 0) {
				if (board[checkX][checkY - 2] == player) {
					win= true;
				} else if (board[checkX][checkY - 2] == 0) {
					setCompTurn(checkX, checkY - 2);
				}
			} else if (board[checkX][checkY + 1] == 0) {
				setCompTurn(checkX, checkY + 1);
			}
		}
		// check right
		if (checkY + 1 < 3 && board[checkX][checkY + 1] == player) {
			right= true;
			// check one more right
			if (checkY + 2 < 3) {
				if (board[checkX][checkY + 2] == player) {
					win= true;
				} else if (board[checkX][checkY + 2] == 0) {
					setCompTurn(checkX, checkY + 2);
				}
			} else if (board[checkX][checkY - 1] == 0) {
				setCompTurn(checkX, checkY + 2);
			}
		}
		// check bottom
		if (checkX + 1 < 3 && board[checkX + 1][checkY] == player) {
			below= true;
			// check one more below
			if (checkX + 2 < 3) {
				if (board[checkX + 2][checkY] == player) {
					win= true;
				} else if (board[checkX + 2][checkY] == 0) {
					setCompTurn(checkX + 2, checkY);
				}
			} else if (board[checkX - 1][checkY] == 0) {
				setCompTurn(checkX + 2, checkY);
			}
		}
		// check diagonals
		// top left
		if (checkX - 1 >= 0 && checkY - 1 >= 0 && board[checkX - 1][checkY - 1] == player) {
			topLeft= true;
			if (checkX - 2 >= 0 && checkY - 2 >= 0) {
				if (board[checkX - 2][checkY - 2] == player) {
					win= true;
				} else if (board[checkX - 2][checkY - 2] == 0) {
					setCompTurn(checkX - 2, checkY - 2);
				}
			} else if (checkX + 1 < 3 && checkY + 1 < 3 && board[checkX + 1][checkY + 1] == 0) {
				setCompTurn(checkX + 1, checkY + 1);
			}
		}
		// top right
		if (checkX - 1 >= 0 && checkY + 1 < 3 && board[checkX - 1][checkY + 1] == player) {
			topRight= true;
			if (checkX - 2 >= 0 && checkY + 2 < 3) {
				if (board[checkX - 2][checkY + 2] == player) {
					win= true;
				} else if (board[checkX - 2][checkY + 2] == 0) {
					setCompTurn(checkX - 2, checkY + 2);
				}
			} else if (checkX + 1 < 3 && checkY - 1 >= 0 && board[checkX + 1][checkY - 1] == 0) {
				setCompTurn(checkX + 1, checkY - 1);
			}
		}
		// bottom left
		if (checkX + 1 < 3 && checkY - 1 >= 0 && board[checkX + 1][checkY - 1] == player) {
			bottomLeft= true;
			if (checkX + 2 < 3 && checkY - 2 >= 0) {
				if (board[checkX + 2][checkY - 2] == player) {
					win= true;
				} else if (board[checkX + 2][checkY - 2] == 0) {
					setCompTurn(checkX + 2, checkY - 2);
				}
			} else if (checkX - 1 >= 0 && checkY - 1 >= 0 && board[checkX - 1][checkY - 1] == 0) {
				setCompTurn(checkX - 1, checkY + 1);
			}
		}
		// bottom right
		if (checkX + 1 < 3 && checkY + 1 < 3 && board[checkX + 1][checkY + 1] == player) {
			bottomRight= true;
			if (checkX + 2 < 3 && checkY + 2 < 3) {
				if (board[checkX + 2][checkY + 2] == player) {
					win= true;
				} else if (board[checkX + 2][checkY + 2] == 0) {
					setCompTurn(checkX + 2, checkY + 2);
				}
			} else if (checkX - 1 >= 0 && checkY - 1 >= 0 && board[checkX - 1][checkY - 1] == 0) {
				setCompTurn(checkX - 1, checkY - 1);
			}
		}
		// check outliers
		if (left && right || above && below || topLeft && bottomRight || topRight && bottomLeft) {
			win= true;
		}

		// check for save plays: where the computer needs to block the player from filling in
		// a between piece: 1 0 1 --> 1 1 1
		// check bottom of board plays
		if (checkY == 0) {
			if (board[checkX][checkY + 2] == player) {
				setCompTurn(checkX, checkY + 1);
			}
		}
		// check top of board plays
		if (checkY == 2) {
			if (board[checkX][checkY - 2] == player) {
				setCompTurn(checkX, checkY - 1);
			}
		}
		// check left side plays
		if (checkX == 0) {
			if (board[checkX + 2][checkY] == player) {
				setCompTurn(checkX + 1, checkY);
			}
		}
		// check right side plays
		if (checkX == 2) {
			if (board[checkX - 2][checkY] == player) {
				setCompTurn(checkX - 1, checkY);
			}
		}
		return win;
	}

	public static void tryToWin() {

	}

	public static Boolean changeBoard(int x, int y, int player) {
		board[x - 1][y - 1]= player;
		return checkWin(player, x - 1, y - 1);
	}

}

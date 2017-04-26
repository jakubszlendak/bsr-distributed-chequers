
public class GameSession {

	private ClientInfo player1; // WHITE
	private ClientInfo player2; // NIGGA

	/*
	board[x][y]
	(0,0) (1,0) ... (BOARD_SIZE-1,0)
	(0,1) (1,1) ... (BOARD_SIZE-1,1)
	...   ...   ... ...
	(0,BOARD_SIZE-1) (1,BOARD_SIZE-1) ... (BOARD_SIZE-1,BOARD_SIZE-1)
	*/
	private char[][] board;

	private static final int BOARD_SIZE = 10;
	private static final int PAWN_INITIAL_ROWS = 4;

	public GameSession(ClientInfo player1, ClientInfo player2){
		this.player1 = player1;
		this.player2 = player2;
		initBoard();
	}

	private void initBoard(){
		board = new char [BOARD_SIZE][BOARD_SIZE];
		// clear board
		for (int x=0; x<BOARD_SIZE; x++){
			for (int y=0; y<BOARD_SIZE; y++){
				board[x][y] = BoardSymbols.EMPTY;
			}
		}
		// set initial pawn positions: black
		for (int y=0; y<PAWN_INITIAL_ROWS; y++){
			for (int x=0; x<BOARD_SIZE; x++){
				if ((x + y) % 2 == 1){
					board[x][y] = BoardSymbols.BLACK_PAWN;
				}
			}
		}
		// set initial pawn positions: white
		for (int y=BOARD_SIZE - PAWN_INITIAL_ROWS; y<BOARD_SIZE; y++){
			for (int x=0; x<BOARD_SIZE; x++){
				if ((x + y) % 2 == 1){
					board[x][y] = BoardSymbols.WHITE_PAWN;
				}
			}
		}
	}

	public void executeMove(ClientInfo player, Point source, Point target) throws InvalidMoveException {
		char playerColor = player == player1 ? BoardSymbols.WHITE_PAWN : BoardSymbols.BLACK_PAWN;
		MoveValidator.validateMove(playerColor, board, source, target);
		// TODO execute move

	}

	private int countSymbols(char... symbols){
		int count = 0;
		for (int x=0; x<BOARD_SIZE; x++){
			for (int y=0; y<BOARD_SIZE; y++){
				for(symbol : symbols){
					if (board[x][y] == symbol)
						count++;
				}
			}
		}
		return count;
	}

	private boolean isGameOver(){
		return hasWhiteWon() || hasBlackWon();
	}

	private boolean hasWhiteWon(){
		return countSymbols(BoardSymbols.BLACK_PAWN, BoardSymbols.BLACK_SUPER) == 0;
	}

	private boolean hasBlackWon(){
		return countSymbols(BoardSymbols.WHITE_PAWN, BoardSymbols.WHITE_SUPER) == 0;
	}
}
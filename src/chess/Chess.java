package chess;

import java.util.Scanner;

public class Chess {
	private static boolean drawOffer = false;
	private static Scanner reader;
	public static void main(String[] args) {
		int i = 0, result = 0;
		char colors[] = {'w', 'b'};
		Board board = new Board();
		board.setupBoard();
		while (result==0) {
			board.printBoard();
			result = turn(board, colors[(i++%2)]);
		}
		if (reader!=null) reader.close();
		if (result==-1) System.out.println("Draw");
		if ((result+'a')=='w') System.out.println("White wins");
		if ((result+'a')=='b') System.out.println("Black wins");
	}
	
	private static int turn(Board b, char color) {
		String input;
		int result = -1;
		int i, j;
		char type;
		Piece newpiece;
		if (b.inStalemate(color)==1) {
			System.out.println("Stalemate");
			return -1;
		}
		System.out.print((color=='w') ? "White's move: " : "Black's move: ");
		reader = new Scanner(System.in);
		input = reader.nextLine();
		if (input.compareTo("resign")==0) return ((color=='b') ? ('w' - 'a') : ('b' - 'a'));
		if (drawOffer) {
			drawOffer = false;
			if (input.compareTo("draw")==0) return -1;
		}
		if ((((input.charAt(0)>='a')&&(input.charAt(0)<='h'))
				&&((input.charAt(1)>='1')&&(input.charAt(1)<='8')))
			&&(((input.charAt(3)>='a')&&(input.charAt(3)<='h'))
				&&((input.charAt(4)>='1')&&(input.charAt(4)<='8')))) {
			if (input.contains("draw?")) drawOffer = true; 
			if (((b.getPosition(input.substring(0,2))!=null)&&(input.length()>4))
				&&(b.getPosition(input.substring(0,2)).getColor()==color)) result = b.getPosition(input.substring(0,2)).moveTo(input.substring(0,5));
		}
		if (result==-1) {
			System.out.println("Illegal move, try again");
			return turn(b, color);
		}
		if (result==2) { //pawn promotion
			if (input.length()<9) type = 'Q';
			else type = input.charAt(8);
			switch(type) {
				case 'K':
					newpiece = new King(b);
					break;
				case 'B':
					newpiece = new Bishop(b);
					break;
				case 'N':
					newpiece = new Knight(b);
					break;
				case 'R':
					newpiece = new Rook(b);
					break;
				case 'Q':
				default:
					newpiece = new Queen(b);
			}
			newpiece.setColor(color);
			b.capture(newpiece, input.substring(3,5));
		}
		String loc;
		String kingloc = "a1";
		for(i = 0; i<8; i++) for(j = 0; j<8; j++) {
			loc = Character.toString((char) ('a' + i)).concat(Integer.toString(j+1));
			if ((b.getPosition(loc) instanceof King)&&(b.getPosition(loc).getColor()!=color)) kingloc = loc;
		}
		if (b.inCheckmate(kingloc)==1) {
			System.out.println("Checkmate");
			return (color - 'a');
		}
		if (b.inCheck(kingloc)==1) System.out.println("Check");
		return 0;
	}

}

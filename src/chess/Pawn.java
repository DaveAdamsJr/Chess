package chess;

public class Pawn implements Piece{
	char color;
	Board board;
	int firstMove = 1;
	public Pawn(Board board) { this.board = board; }
	public char getColor() { return color; }
	public void setColor(char color) { this.color = color; }
	public int moveTo(String location) {
		if (location.length()<5) return -1;
		if ((((location.charAt(0)>'h')||(location.charAt(0)<'a'))
				||((location.charAt(1)>'8')||(location.charAt(1)<'1')))
			||(((location.charAt(3)>'h')||(location.charAt(3)<'a'))
				||((location.charAt(4)>'8')||(location.charAt(4)<'1')))) return -1;
		int success = 1;
		String newloc = location.substring(3,5);
		String oldloc = location.substring(0,2);
		if (newloc.charAt(1)==((this.color=='w') ? 8 : 1)) success = 2; //pawn promotion
		if ((!(board.getPosition(oldloc) instanceof Pawn))
				||(board.getPosition(oldloc).getColor()!=this.color)) return -1;
		if (((newloc.charAt(1)-oldloc.charAt(1))==((this.color=='w') ? 1 : -1))
				&&(Math.abs(newloc.charAt(0)-oldloc.charAt(0))==1)) { //diagonal capture move
			if ((board.getPosition(newloc)==null)
					||(board.getPosition(newloc).getColor()==this.color)) return -1;
			board.capture(this, newloc);
			board.capture(null, oldloc);
			firstMove = 0;
			return success;			
		}
		if ((firstMove==1)&&(((newloc.charAt(1)-oldloc.charAt(1))==((this.color=='w') ? 2 : -2)) //double jump first
				&&(newloc.charAt(0)==oldloc.charAt(0)))) {
			if (board.getPosition(oldloc.substring(0,1).concat(Integer.toString((oldloc.charAt(1)+newloc.charAt(1))/2)))!=null) return -1;
			board.capture(this, newloc);
			board.capture(null, oldloc);
			this.firstMove=0;
			return 1;
		}
		if ((oldloc.charAt(0)==newloc.charAt(0)) //regular move
			&&((newloc.charAt(1) - oldloc.charAt(1))==((this.color=='w') ? 1 : -1))) { 
			board.capture(this, newloc);
			board.capture(null, oldloc);
			this.firstMove=0;
			return success;
		}
		return -1;
	}
}
package chess;

public class King implements Piece{
	char color;
	Board board;
	private int moved = 0;
	public King(Board board) { this.board = board; }
	public char getColor() { return color; }
	public void setColor(char color) { this.color = color; }
	public int moveTo(String location) { //move 1 in any direction, but not into check
		if (location.length()<5) return -1;
		int success = 1;
		if ((((location.charAt(0)>'h')||(location.charAt(0)<'a'))
				||((location.charAt(1)>'8')||(location.charAt(1)<'1')))
			||(((location.charAt(3)>'h')||(location.charAt(3)<'a'))
				||((location.charAt(4)>'8')||(location.charAt(4)<'1')))) return -1;
		if (!(board.getPosition(location.substring(0,2)) instanceof King)
				||(board.getPosition(location.substring(0,2)).getColor()!=this.color)) return -1;
		if ((board.getPosition(location.substring(3,5))!=null)
				&&(board.getPosition(location.substring(3,5)).getColor()==this.color)) return -1;
		if (((Math.abs(location.charAt(4) - location.charAt(1)) 
				+ Math.abs(location.charAt(3) - location.charAt(0))) < 3)
			&&((Math.abs((int) location.charAt(4) - location.charAt(1))==1)
				||(Math.abs((int) location.charAt(3) - location.charAt(0))==1))) {
			Piece temp = board.getPosition(location.substring(3,5));
			board.capture(this, location.substring(3, 5));
			board.capture(null, location.substring(0, 2));
			if (board.inCheck(location.substring(3,5))==1) { //make sure to avoid check
				board.capture(temp, location.substring(3,5));
				board.capture(this, location.substring(0,2));
				return -1;
			}
			this.moved = 1;
			return 1;
		}
		if (this.color=='b') { //castling
				if (location.compareTo("e8 g8")==0) {
					if (((board.getPosition("f8")!=null)||(board.getPosition("g8")!=null))
							||(!(board.getPosition("h8") instanceof Rook))) return -1;
					if ((((Rook) board.getPosition("h8")).hasMoved()==1)||(moved==1)) return -1;
					if (board.inCheck("e8")!=0) success = 0;
					board.capture(this, "f8");
					board.capture(null, "e8");
					if (board.inCheck("f8")!=0) success = 0;
					board.capture(this, "g8");
					board.capture(null, "f8");
					if (board.inCheck("g8")!=0) success = 0;
					if (success == 0) {
						board.capture(this, "e8");
						board.capture(null, "g8");
						return -1;
					} else {
						board.capture(board.getPosition("h8"), "f8");
						board.capture(null, "a8");
						return 1;
					}
				} else if (location.compareTo("e8 c8")==0) {
					if (((board.getPosition("d8")!=null)||(board.getPosition("c8")!=null))
							||((board.getPosition("b8")!=null)||(!(board.getPosition("a8") instanceof Rook)))) return -1;
					if ((((Rook) board.getPosition("c8")).hasMoved()==1)||(moved==1)) return -1;
					if (board.inCheck("e8")!=0) success = 0;
					board.capture(this, "d8");
					board.capture(null, "e8");
					if (board.inCheck("d8")!=0) success = 0;
					board.capture(this, "c8");
					board.capture(null, "d8");
					if (board.inCheck("c8")!=0) success = 0;
					if (success == 0) {
						board.capture(this, "e8");
						board.capture(null, "c8");
						return -1;
					} else {
						board.capture(board.getPosition("a8"), "d8");
						board.capture(null, "a8");
						return 1;
					}	
				}
		} else if (this.color=='w') { //castling
			if (location.compareTo("e1 g1")==0) {
				if (((board.getPosition("f1")!=null)||(board.getPosition("g1")!=null))
						||(!(board.getPosition("h1") instanceof Rook))) return -1;
				if ((((Rook) board.getPosition("h1")).hasMoved()==1)||(moved==1)) return -1;
				if (board.inCheck("e1")!=0) success = 0;
				board.capture(this, "f1");
				board.capture(null, "e1");
				if (board.inCheck("f1")!=0) success = 0;
				board.capture(this, "g1");
				board.capture(null, "f1");
				if (board.inCheck("g1")!=0) success = 0;
				if (success == 0) {
					board.capture(this, "e1");
					board.capture(null, "g1");
					return -1;
				} else {
					board.capture(board.getPosition("h1"), "f1");
					board.capture(null, "a1");
					return 1;
				}
			} else if (location.compareTo("e1 c1")==0) {
				if (((board.getPosition("d1")!=null)||(board.getPosition("c1")!=null))
						||((board.getPosition("b1")!=null)||(!(board.getPosition("a1") instanceof Rook)))) return -1;
				if ((((Rook) board.getPosition("c1")).hasMoved()==1)||(moved==1)) return -1;
				if (board.inCheck("e1")!=0) success = 0;
				board.capture(this, "d1");
				board.capture(null, "e1");
				if (board.inCheck("d1")!=0) success = 0;
				board.capture(this, "c1");
				board.capture(null, "d1");
				if (board.inCheck("c1")!=0) success = 0;
				if (success == 0) {
					board.capture(this, "e1");
					board.capture(null, "c1");
					return -1;
				} else {
					board.capture(board.getPosition("a1"), "d1");
					board.capture(null, "a1");
					return 1;
				}
			}
		}
		return -1;
	}
}

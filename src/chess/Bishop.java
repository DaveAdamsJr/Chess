package chess;

public class Bishop implements Piece{
	char color;
	Board board;
	public Bishop(Board board) { this.board = board; }
	public char getColor() { return color; }
	public void setColor(char color) { this.color = color; }
	public int moveTo(String location) {
		if (location.length()<5) return -1;
		if ((((location.charAt(0)>'h')||(location.charAt(0)<'a'))
				||((location.charAt(1)>'8')||(location.charAt(1)<'1')))
			||(((location.charAt(3)>'h')||(location.charAt(3)<'a'))
				||((location.charAt(4)>'8')||(location.charAt(4)<'1')))) return -1;
		int i, j, origx, origy, newx, newy;
		if ((!(board.getPosition(location.substring(0,2)) instanceof Bishop))
			||(board.getPosition(location.substring(0,2)).getColor()!=this.color)) return -1;
		if (Math.abs((int) location.charAt(0) - location.charAt(3)) 
				== Math.abs((int) location.charAt(1) - location.charAt(4))) {
			origx = location.charAt(1)-'0'-1;
			origy = location.charAt(0)-'0'-1;
			newx = location.charAt(4)-'0'-1;
			newy = location.charAt(3)-'0'-1;
			i = (origx<newx) ? 1 : -1;
			j = (origy<newy) ? 1 : -1;
			while((Math.abs((origx+i)-newx)>1)&&(Math.abs((origy+j)-newy)>1)) {
				if (board.getPosition(Integer.toString(origy+j).concat(Integer.toString(origx+i)))!=null) return -1;
				i += (origx<newx) ? 1 : -1;
				j += (origy<newy) ? 1 : -1;
			}
			if ((board.getPosition(location.substring(3,5))!=null)
					&&(board.getPosition(location.substring(3,5)).getColor()==this.color)) return -1;
			board.capture(this, location.substring(3,5));
			board.capture(null, location.substring(0,2));
			return 1;
		}		
		return -1;
	}
}
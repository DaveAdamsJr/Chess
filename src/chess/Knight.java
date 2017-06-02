package chess;

public class Knight implements Piece{
	char color;
	Board board;
	public Knight(Board board) { this.board = board; }
	public char getColor() { return color; }
	public void setColor(char color) { this.color = color; }
	public int moveTo(String location) {
		if (location.length()<5) return -1;
		if ((((location.charAt(0)>'h')||(location.charAt(0)<'a'))
				||((location.charAt(1)>'8')||(location.charAt(1)<'1')))
			||(((location.charAt(3)>'h')||(location.charAt(3)<'a'))
				||((location.charAt(4)>'8')||(location.charAt(4)<'1')))) return -1;
		int i;
		if ((!(board.getPosition(location.substring(0,2)) instanceof Knight))
			||(board.getPosition(location.substring(0,2)).getColor()!=this.color)) return -1;
		String newloc = location.substring(3,5);
		String locs[] = {
				Character.toString((char) (location.charAt(0)+2)).concat(Character.toString((char) (location.charAt(1)+1))),
				Character.toString((char) (location.charAt(0)+2)).concat(Character.toString((char) (location.charAt(1)-1))),
				Character.toString((char) (location.charAt(0)+1)).concat(Character.toString((char) (location.charAt(1)+2))),
				Character.toString((char) (location.charAt(0)+1)).concat(Character.toString((char) (location.charAt(1)-2))),
				Character.toString((char) (location.charAt(0)-1)).concat(Character.toString((char) (location.charAt(1)+2))),
				Character.toString((char) (location.charAt(0)-1)).concat(Character.toString((char) (location.charAt(1)-2))),
				Character.toString((char) (location.charAt(0)-2)).concat(Character.toString((char) (location.charAt(1)+1))),
				Character.toString((char) (location.charAt(0)-2)).concat(Character.toString((char) (location.charAt(1)-1)))
		};
		for(i = 0; i<8; i++) {
			if (newloc.compareTo(locs[i])==0) {
				if ((board.getPosition(newloc)!=null)&&(board.getPosition(newloc).getColor()==color)) return -1;
				board.capture(this, newloc);
				board.capture(null, location.substring(0,2));
				return 1;
			}
		}
		return -1;
	}
}
package chess;

public interface Piece {
	int moveTo(String location);
	char getColor();
	void setColor(char color);
}

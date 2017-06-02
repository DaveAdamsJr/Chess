package chess;

public class Board {
	private Piece pieces[][];	
	
	void setupBoard() {
		pieces = new Piece[8][8];
		pieces[0][0] = new Rook(this);
		pieces[0][7] = new Rook(this);
		pieces[0][6] = new Knight(this);
		pieces[0][1] = new Knight(this);
		pieces[0][2] = new Bishop(this);
		pieces[0][5] = new Bishop(this);
		pieces[0][3] = new Queen(this);
		pieces[0][4] = new King(this);
		pieces[7][0] = new Rook(this);
		pieces[7][7] = new Rook(this);
		pieces[7][6] = new Knight(this);
		pieces[7][1] = new Knight(this);
		pieces[7][2] = new Bishop(this);
		pieces[7][5] = new Bishop(this);
		pieces[7][3] = new Queen(this);
		pieces[7][4] = new King(this);
		for(int i = 0; i<8; i++) {
			pieces[0][i].setColor('w');
			pieces[7][i].setColor('b');
			pieces[1][i] = new Pawn(this);
			pieces[1][i].setColor('w');
			pieces[6][i] = new Pawn(this);
			pieces[6][i].setColor('b');
			
		}
	}
	
	void printBoard() {
		int i, j;
		String type;
		Piece curr;
		System.out.println();
		for(i = 7; i>=0; i--) {
			for(j = 0; j<8; j++) {
				curr = pieces[i][j];
				type = ((curr!=null) ? Character.toString(pieces[i][j].getColor()) : "E");
				if (curr instanceof King) type = type.concat("K");
				else if (curr instanceof Queen) type = type.concat("Q");
				else if (curr instanceof Bishop) type = type.concat("B");
				else if (curr instanceof Knight) type = type.concat("N");
				else if (curr instanceof Rook) type = type.concat("R");
				else if (curr instanceof Pawn) type = type.concat("p");
				else type = ((((i+j)%2)==0) ? "##" : "  ");
				System.out.print(type + " ");
			}
			System.out.println((i+1));
		}
		System.out.println(" a  b  c  d  e  f  g  h");
		System.out.println();
	}
	
	Piece getPosition(String location) {
		if (((location.charAt(0)>'h')||(location.charAt(0)<'a'))
			||((location.charAt(1)>'8')||(location.charAt(1)<'1'))) return null;
		return pieces[(int) (location.charAt(1) - '0' - 1)][(int) (location.charAt(0)- 'a')];
	}
	
	void capture(Piece newpiece, String location) {
		if (((location.charAt(0)>'h')||(location.charAt(0)<'a'))
				||((location.charAt(1)>'8')||(location.charAt(1)<'1'))) return;
		pieces[(int) (location.charAt(1) - '0' - 1)][(int) (location.charAt(0)- 'a')] = newpiece;
	}
	
	int inCheck(String location) {
		char kingcolor = this.getPosition(location).getColor();
		char enemycolor = ((kingcolor=='w') ? 'b' : 'w');
		int i;
		Piece currPiece;
		Piece king = this.getPosition(location);
		for(i = location.charAt(1)-'0'; i<8; i++) { //check vertically up 
			currPiece = this.getPosition(location.substring(0,1).concat(Integer.toString(i)));
			if (currPiece==null) continue;
			if (((currPiece instanceof Rook)||(currPiece instanceof Queen))
				&&(currPiece.getColor()==enemycolor)) return 1;
			break;
		}
		for(i = location.charAt(1)-'0'-2; i>=0; i--) { //check vertically down 
			currPiece = this.getPosition(location.substring(0,1).concat(Integer.toString(i)));
			if (currPiece==null) continue;
			if (((currPiece instanceof Rook)||(currPiece instanceof Queen))
					&&(currPiece.getColor()==enemycolor)) return 1;
			if (currPiece instanceof Pawn) {
				if (currPiece.moveTo(location.substring(0,1)
						.concat(Integer.toString(i)).concat(" ").concat(location.substring(0,2)))!=-1) {
					this.capture(king, location);
					this.capture(currPiece, location.concat(Integer.toString(i)));
					return -1;
				}
			}
			break;
		}
		for(i = 1; i<8; i++) { //diagonally 
			currPiece = this.getPosition(Character.toString((char) (location.charAt(0)+i)).concat(Character.toString((char) (location.charAt(1)+i))));
			if (currPiece==null) continue;
			if (((currPiece instanceof Bishop)||(currPiece instanceof Queen))
					&&(currPiece.getColor()==enemycolor)) return 1;
			break;
		}
		for(i = 1; i<8; i++) { //diagonally 
			currPiece = this.getPosition(Character.toString((char) (location.charAt(0)+i)).concat(Character.toString((char) (location.charAt(1)-i))));
			if (currPiece==null) continue;
			if (((currPiece instanceof Bishop)||(currPiece instanceof Queen))
					&&(currPiece.getColor()==enemycolor)) return 1;
			break;
		}
		for(i = 1; i<8; i++) { //diagonally 
			currPiece = this.getPosition(Character.toString((char) (location.charAt(0)-i)).concat(Character.toString((char) (location.charAt(1)+i))));
			if (currPiece==null) continue;
			if (((currPiece instanceof Bishop)||(currPiece instanceof Queen))
					&&(currPiece.getColor()==enemycolor)) return 1;
			break;
		}
		for(i = 1; i<8; i++) { //diagonally 
			currPiece = this.getPosition(Character.toString((char) (location.charAt(0)-i)).concat(Character.toString((char) (location.charAt(1)-i))));
			if (currPiece==null) continue;
			if (((currPiece instanceof Bishop)||(currPiece instanceof Queen))
					&&(currPiece.getColor()==enemycolor)) return 1;
			break;
		}
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
		for(i = 0; i<8; i++) { //check for knights
			if ((this.getPosition(locs[i]) instanceof Knight)&&(this.getPosition(locs[i]).getColor()==enemycolor)) return 1;
		}
		String locs2[] = {
				Character.toString((char) (location.charAt(0)+1)).concat(Character.toString((char) (location.charAt(1)+1))),
				Character.toString((char) (location.charAt(0)+1)).concat(Character.toString((char) (location.charAt(1)))),
				Character.toString((char) (location.charAt(0)+1)).concat(Character.toString((char) (location.charAt(1)-1))),
				Character.toString((char) (location.charAt(0))).concat(Character.toString((char) (location.charAt(1)+1))),
				Character.toString((char) (location.charAt(0))).concat(Character.toString((char) (location.charAt(1)-1))),
				Character.toString((char) (location.charAt(0)-1)).concat(Character.toString((char) (location.charAt(1)+1))),
				Character.toString((char) (location.charAt(0)-1)).concat(Character.toString((char) (location.charAt(1)))),
				Character.toString((char) (location.charAt(0)-1)).concat(Character.toString((char) (location.charAt(1)-1)))
		};
		for(i = 0; i<8; i++) {
			if ((this.getPosition(locs2[i])!=null)&&(this.getPosition(locs2[i]).moveTo(locs2[i].concat(" ").concat(location))!=-1)) {
				capture(king, location);
				capture(this.getPosition(location), locs2[i]);
				return 1;
			}
		}
		return 0;
	}
	
	int inCheckmate(String location) {
		if (this.inCheck(location)==0) return 0;
		int i, checkmate, threathandled = 0;
		String threatloc = null;
		Piece king, temp, threat, testking;
		king = this.getPosition(location);
		String locs[] = { 
				location.substring(0,1).concat(Character.toString((char) (location.charAt(1)+1))),
				location.substring(0,1).concat(Character.toString((char) (location.charAt(1)-1))),
				Character.toString((char) (location.charAt(0)+1)).concat(location.substring(1,2)),
				Character.toString((char) (location.charAt(0)-1)).concat(location.substring(1,2)),
				Character.toString((char) (location.charAt(0)+1)).concat(Character.toString((char) (location.charAt(1)+1))),
				Character.toString((char) (location.charAt(0)-1)).concat(Character.toString((char) (location.charAt(1)-1))),
				Character.toString((char) (location.charAt(0)+1)).concat(Character.toString((char) (location.charAt(1)-1))),
				Character.toString((char) (location.charAt(0)-1)).concat(Character.toString((char) (location.charAt(1)+1))), 
				};
		checkmate = 1;
		for (i = 0; i<8; i++) {
			temp = this.getPosition(locs[i]);
			if (king.moveTo(locs[i])!=-1) if (this.inCheck(locs[i])==0) checkmate = 0;
			this.capture(temp, locs[i]);
			this.capture(king, location);
		}
		if (checkmate==0) return 0;
		
		//check if threats can be eliminated
		char kingcolor = this.getPosition(location).getColor();
		char enemycolor = ((kingcolor=='w') ? 'b' : 'w');
		Piece currPiece;
		for(i = location.charAt(1)-'0'; i<8; i++) { //check vertically up 
			currPiece = this.getPosition(location.substring(0,1).concat(Integer.toString(i)));
			if (currPiece==null) continue;
			if (((currPiece instanceof Rook)||(currPiece instanceof Queen))
				&&(currPiece.getColor()==enemycolor)) {
				threatloc = location.substring(0,1).concat(Integer.toString(i));
				threat = this.getPosition(threatloc);
				testking = new King(this);
				testking.setColor(threat.getColor());
				this.capture(testking, threatloc);
				threathandled = inCheck(threatloc);
				this.capture(threat, threatloc);
				if (threathandled==0) return 1;
			}
			break;
		}
		for(i = location.charAt(1)-'0'-2; i>=0; i--) { //check vertically down 
			currPiece = this.getPosition(location.substring(0,1).concat(Integer.toString(i)));
			if (currPiece==null) continue;
			if (((currPiece instanceof Rook)||(currPiece instanceof Queen))
					&&(currPiece.getColor()==enemycolor)) {
				threatloc = location.substring(0,1).concat(Integer.toString(i));
				threat = this.getPosition(threatloc);
				testking = new King(this);
				testking.setColor(threat.getColor());
				this.capture(testking, threatloc);
				threathandled = inCheck(threatloc);
				this.capture(threat, threatloc);
				if (threathandled==0) return 1;
			}
			if (currPiece instanceof Pawn) {
				if (currPiece.moveTo(location.substring(0,1)
						.concat(Integer.toString(i)).concat(" ").concat(location.substring(0,2)))!=-1) {
					this.capture(king, location);
					this.capture(currPiece, location.concat(Integer.toString(i)));
					threatloc = location.substring(0,1).concat(Integer.toString(i));
					threat = this.getPosition(threatloc);
					testking = new King(this);
					testking.setColor(threat.getColor());
					this.capture(testking, threatloc);
					threathandled = inCheck(threatloc);
					this.capture(threat, threatloc);
					if (threathandled==0) return 1;
				}
			}
			break;
		}
		for(i = 1; i<8; i++) { //diagonally 
			currPiece = this.getPosition(Character.toString((char) (location.charAt(0)+i)).concat(Character.toString((char) (location.charAt(1)+i))));
			if (currPiece==null) continue;
			if (((currPiece instanceof Bishop)||(currPiece instanceof Queen))
					&&(currPiece.getColor()==enemycolor)) {
				threatloc = Character.toString((char) (location.charAt(0)+i)).concat(Character.toString((char) (location.charAt(1)+i)));
				threat = this.getPosition(threatloc);
				testking = new King(this);
				testking.setColor(threat.getColor());
				this.capture(testking, threatloc);
				threathandled = inCheck(threatloc);
				this.capture(threat, threatloc);
				if (threathandled==0) return 1;
			}
			break;
		}
		for(i = 1; i<8; i++) { //diagonally 
			currPiece = this.getPosition(Character.toString((char) (location.charAt(0)+i)).concat(Character.toString((char) (location.charAt(1)-i))));
			if (currPiece==null) continue;
			if (((currPiece instanceof Bishop)||(currPiece instanceof Queen))
					&&(currPiece.getColor()==enemycolor)) {
				threatloc = Character.toString((char) (location.charAt(0)+i)).concat(Character.toString((char) (location.charAt(1)-i)));
				threat = this.getPosition(threatloc);
				testking = new King(this);
				testking.setColor(threat.getColor());
				this.capture(testking, threatloc);
				threathandled = inCheck(threatloc);
				this.capture(threat, threatloc);
				if (threathandled==0) return 1;
			}
			break;
		}
		for(i = 1; i<8; i++) { //diagonally 
			currPiece = this.getPosition(Character.toString((char) (location.charAt(0)-i)).concat(Character.toString((char) (location.charAt(1)+i))));
			if (currPiece==null) continue;
			if (((currPiece instanceof Bishop)||(currPiece instanceof Queen))
					&&(currPiece.getColor()==enemycolor)) {
				threatloc = Character.toString((char) (location.charAt(0)-i)).concat(Character.toString((char) (location.charAt(1)+i)));
				threat = this.getPosition(threatloc);
				testking = new King(this);
				testking.setColor(threat.getColor());
				this.capture(testking, threatloc);
				threathandled = inCheck(threatloc);
				this.capture(threat, threatloc);
				if (threathandled==0) return 1;
			}
			break;
		}
		for(i = 1; i<8; i++) { //diagonally 
			currPiece = this.getPosition(Character.toString((char) (location.charAt(0)-i)).concat(Character.toString((char) (location.charAt(1)-i))));
			if (currPiece==null) continue;
			if (((currPiece instanceof Bishop)||(currPiece instanceof Queen))
					&&(currPiece.getColor()==enemycolor)) {
				threatloc = Character.toString((char) (location.charAt(0)-i)).concat(Character.toString((char) (location.charAt(1)-i)));
				threat = this.getPosition(threatloc);
				testking = new King(this);
				testking.setColor(threat.getColor());
				this.capture(testking, threatloc);
				threathandled = inCheck(threatloc);
				this.capture(threat, threatloc);
				if (threathandled==0) return 1;
			}
			break;
		}
		String locs3[] = {
				Character.toString((char) (location.charAt(0)+2)).concat(Character.toString((char) (location.charAt(1)+1))),
				Character.toString((char) (location.charAt(0)+2)).concat(Character.toString((char) (location.charAt(1)-1))),
				Character.toString((char) (location.charAt(0)+1)).concat(Character.toString((char) (location.charAt(1)+2))),
				Character.toString((char) (location.charAt(0)+1)).concat(Character.toString((char) (location.charAt(1)-2))),
				Character.toString((char) (location.charAt(0)-1)).concat(Character.toString((char) (location.charAt(1)+2))),
				Character.toString((char) (location.charAt(0)-1)).concat(Character.toString((char) (location.charAt(1)-2))),
				Character.toString((char) (location.charAt(0)-2)).concat(Character.toString((char) (location.charAt(1)+1))),
				Character.toString((char) (location.charAt(0)-2)).concat(Character.toString((char) (location.charAt(1)-1)))
		};
		for(i = 0; i<8; i++) { //check for knights
			if ((this.getPosition(locs3[i]) instanceof Knight)&&(this.getPosition(locs3[i]).getColor()==enemycolor)) {
				threatloc = locs3[i];
				threat = this.getPosition(threatloc);
				testking = new King(this);
				testking.setColor(threat.getColor());
				this.capture(testking, threatloc);
				threathandled = inCheck(threatloc);
				this.capture(threat, threatloc);
				if (threathandled==0) return 1;
			}
		}
		String locs2[] = {
				Character.toString((char) (location.charAt(0)+1)).concat(Character.toString((char) (location.charAt(1)+1))),
				Character.toString((char) (location.charAt(0)+1)).concat(Character.toString((char) (location.charAt(1)))),
				Character.toString((char) (location.charAt(0)+1)).concat(Character.toString((char) (location.charAt(1)-1))),
				Character.toString((char) (location.charAt(0))).concat(Character.toString((char) (location.charAt(1)+1))),
				Character.toString((char) (location.charAt(0))).concat(Character.toString((char) (location.charAt(1)-1))),
				Character.toString((char) (location.charAt(0)-1)).concat(Character.toString((char) (location.charAt(1)+1))),
				Character.toString((char) (location.charAt(0)-1)).concat(Character.toString((char) (location.charAt(1)))),
				Character.toString((char) (location.charAt(0)-1)).concat(Character.toString((char) (location.charAt(1)-1)))
		};
		for(i = 0; i<8; i++) {
			if ((this.getPosition(locs2[i])!=null)&&(this.getPosition(locs2[i]).moveTo(locs2[i].concat(" ").concat(location))!=-1)) {
				capture(king, location);
				capture(this.getPosition(location), locs2[i]);
				threatloc=locs2[i];
				threat = this.getPosition(threatloc);
				testking = new King(this);
				testking.setColor(threat.getColor());
				this.capture(testking, threatloc);
				threathandled = inCheck(threatloc);
				this.capture(threat, threatloc);
				if (threathandled==0) return 1;
			}
		}
		return 0;
		
	}
	
	int inStalemate(char color) {
		int i, j, k, l;
		String startloc, newloc;
		Piece temp;
		for(i = 0; i<8; i++) for(j = 0; j<8; j++) for(k = 0; k<8; k++) for (l = 0; l<8; l++) {
			startloc = Character.toString((char) (i + 'a')).concat(Integer.toString(j+1));
			newloc = Character.toString((char) (k + 'a')).concat(Integer.toString(l+1));
			temp = this.getPosition(newloc);
			if (((pieces[i][j]!=null)&&(pieces[i][j].getColor()==color))
					&&(pieces[i][j].moveTo(startloc.concat(" ").concat(newloc)))!=-1) {
				capture(this.getPosition(newloc), startloc);
				capture(temp, newloc);
				return 0;
			}
		}
		return 1;
	}
}

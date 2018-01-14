package main;

import java.util.ArrayList;

public class Board 
{
	private int size;
	private Space[][] board;
	private Position kingPlayer1, kingPlayer2;

	public Board(int sizeOfBoard)
	{
		size = sizeOfBoard;
		board = new Space[size][size];
		initialize();
		setUpPieces();
	}

	public void initialize()
	{
		for(int i = 0;i < size;i++)
			for(int j = 0;j < size;j++)
				board[i][j] = new Space(new Position(i,j));
	}

	public void setUpPieces()
	{
		//set up player 1
		board[size/2][0].setPiece(new King(true,new Position(size/2,0)));
		board[size/2 - 1][0].setPiece(new Knight(true,new Position(size/2 - 1,0)));
		board[size/2 + 1][0].setPiece(new Knight(true,new Position(size/2 + 1,0)));
		kingPlayer1 = new Position(size/2,0);
		board[0][0].setPiece(new Pikeman(true,new Position(0,0)));
		board[1][0].setPiece(new Pikeman(true,new Position(1,0)));
		board[size - 2][0].setPiece(new Pikeman(true,new Position(size - 2,0)));
		board[size - 1][0].setPiece(new Pikeman(true,new Position(size - 1,0)));
		for(int i = 0;i < size;i++)
			board[i][1].setPiece(new Pikeman(true,new Position(i,1)));

		//set up player 2
		board[size/2][size - 1].setPiece(new King(false,new Position(size/2,size - 1)));
		board[size/2 - 1][size - 1].setPiece(new Knight(false,new Position(size/2 - 1,size - 1)));
		board[size/2 + 1][size - 1].setPiece(new Knight(false,new Position(size/2 + 1,size - 1)));
		kingPlayer2 = new Position(size/2,size - 1);
		board[0][size - 1].setPiece(new Pikeman(false,new Position(0,size - 1)));
		board[1][size - 1].setPiece(new Pikeman(false,new Position(1,size - 1)));
		board[size - 2][size - 1].setPiece(new Pikeman(false,new Position(size - 2,size - 1)));
		board[size - 1][size - 1].setPiece(new Pikeman(false,new Position(size - 1,size - 1)));
		for(int i = 0;i < size;i++)
			board[i][size - 2].setPiece(new Pikeman(false,new Position(i,size - 2)));
	}

	public int getSize()
	{
		return size;
	}

	public Piece getPiece(Position position)
	{
		return board[position.x()][position.y()].getPiece();
	}

	public ArrayList<Position> getPiecesMoves(Position position)
	{
		//if(board[position.x()][position.y()].getPiece() != null)
			return scrubMoves(board[position.x()][position.y()].getPiece().getMoves(),board[position.x()][position.y()].getPiece());
	}

	public ArrayList<Position> scrubMoves(ArrayList<Position> moves, Piece piece)
	{


		for(int i = 0;i < moves.size();i++)
			if(moves.get(i).x() < 0 || moves.get(i).x() >= size || moves.get(i).y() < 0 || moves.get(i).y() >= size)
				moves.remove(i);

		if(piece.type == 2)
		{
			ArrayList<Position> remove = new ArrayList<Position>();
			for(int l = 0;l < moves.size();l++)
				if(((moves.get(l).x() == piece.getPosition().x() - 1 || moves.get(l).x() == piece.getPosition().x() + 1) && (moves.get(l).y() == piece.getPosition().y() - 1 || moves.get(l).y() == piece.getPosition().y() + 1)) && !board[moves.get(l).x()][moves.get(l).y()].isEmpty())
					remove.add(moves.get(l));
			for(int l = 0;l < remove.size();l++)
				moves.remove(remove.get(l));
		}

		
		if(piece.type == 1)
		{
			ArrayList<Position> remove = new ArrayList<Position>();
			for(int i = -1;i <= 1;i++)
				for(int j = -1;j <= 1;j++)
					if(!(i == 0 && j == 0) && (piece.getPosition().x() + i >= 0 && piece.getPosition().x() + i < size) && (piece.getPosition().y() + j >= 0 && piece.getPosition().y() + j < size))
						if(board[piece.getPosition().x() + i][piece.getPosition().y() + j].isEmpty() /*|| board[piece.getPosition().x() + (i * 2)][piece.getPosition().y() + (j * 2)].isEmpty()*/)
							remove.add(new Position(piece.getPosition().x() + (i * 2),piece.getPosition().y() + (j * 2)));
			
			for(int i = 0;i < moves.size();i++)
				for(int j = 0;j < remove.size();j++)
					if(moves.get(i).equals(remove.get(j)))
						moves.remove(i);
		}
		/*
		Position king;
		if(piece.player)
			king = kingPlayer1;
		else
			king = kingPlayer2;

		ArrayList<Position> remove = new ArrayList<Position>();
		for(int i = king.x() - 2;i <= king.x() + 2;i++)
			for(int j = king.y() - 2;j <= king.y() + 2;j++)
			{
				if(piece.position.equals(new Position(i,j)))
				{
					for(int p = 0; p<moves.size(); p++)
					{
						Board copy = deepCopy();
						copy.movePiece(piece.position, moves.get(p));
						if(copy.isKingInDanger(piece.player)) remove.add(moves.get(p));
					}
				}
				for(int p = 0; p<moves.size(); p++)
					if(moves.get(p).equals(new Position(i,j)))
					{
						Board copy = deepCopy();
						copy.movePiece(piece.position, moves.get(p));
						if(copy.isKingInDanger(piece.player)) remove.add(moves.get(p));
					}
			}
		for(int l = 0;l < remove.size();l++)
			moves.remove(remove.get(l));
*/
		for(int i = 0;i < moves.size();i++)
			if(!board[moves.get(i).x()][moves.get(i).y()].isEmpty())
				if(board[moves.get(i).x()][moves.get(i).y()].getPiece().getType() == 2 && board[moves.get(i).x()][moves.get(i).y()].getPiece().getPlayer() == piece.getPlayer())
					moves.remove(i);
		
		return moves;

		//cant move off the board
		//knight cant attack in two if there is nothing in between
		//king cant attack diagonally
	}

	public boolean movePiece(Position from, Position to)
	{
		boolean attacking;
		if(board[to.x()][to.y()].isEmpty())
			attacking = false;
		else
			attacking = true;
		
		board[to.x()][to.y()].setPiece(board[from.x()][from.y()].getPiece());
		board[from.x()][from.y()].setPiece(null);
		board[to.x()][to.y()].getPiece().setPosition(to);
		
		if(board[to.x()][to.y()].getPiece().getType() == 2)
		{
			if(board[to.x()][to.y()].getPiece().getPlayer())
				kingPlayer1 = to;
			else
				kingPlayer2 = to;
		}
		
		if(board[to.x()][to.y()].getPiece().getType() == 0)
		{
			if(board[to.x()][to.y()].getPiece().getPlayer())
			{
				if(board[to.x()][to.y()].getPiece().getPosition().y() == 6)
				{
					board[to.x()][to.y()].setPiece(new Knight(board[to.x()][to.y()].getPiece().getPlayer(),board[to.x()][to.y()].getPiece().getPosition()));
				}
			}
			else
			{
				if(board[to.x()][to.y()].getPiece().getPosition().y() == 0)
				{
					board[to.x()][to.y()].setPiece(new Knight(board[to.x()][to.y()].getPiece().getPlayer(),board[to.x()][to.y()].getPiece().getPosition()));
				}
			}
		}
		
		if(board[to.x()][to.y()].getPiece().getType() == 1)
		{
			
		}
		
		return attacking;
	}
	//in danger boolean for if king is in danger
	//two position variables that track both kings

	public boolean isKingInDanger(boolean player)
	{
		Position king;
		if(player)
			king = kingPlayer1;
		else
			king = kingPlayer2;

		for(int i = king.x() - 2;i <= king.x() + 2;i++)
			for(int j = king.y() - 2;j <= king.y() + 2;j++)
				if(i<=6 && j<=6 && i>=0 && j>=0 && board[i][j].getPiece() != null)
				{
					ArrayList<Position> p = board[i][j].getPiece().getMoves();
					for(int k = 0; k < p.size(); k++)
						if(p.get(k).equals(king)) return true;
				}
		return false;
	}
	public Board deepCopy()
	{
		Board copy = new Board(size);
		Space[][] deep = new Space[size][size];
		for(int i = 0;i < size;i++)
			for(int j = 0;j < size;j++)
				deep[i][j] = board[i][j].deepCopy();
		copy.board=deep;
		copy.kingPlayer1 = kingPlayer1;
		copy.kingPlayer2 = kingPlayer2;
		return copy;
	}
}

package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Board;
import main.Position;

public class KingsArmy
{
	JFrame frame;
	JPanel panel;
	JButton[][] buttons;
	JButton selected;
	int x, y;
	ArrayList<JButton> options;
	Board b;
	//Images
	public static void main(String[] args)
	{
		KingsArmy army = new KingsArmy();
	}
	public KingsArmy()
	{
		b = new Board(7);
		buttons = new JButton[7][7];
		initialize();
	}
	public void initialize()
	{
		selected = null;
		panel = new JPanel();
		panel.setLayout(new GridLayout(7,7));
		for(int i = 0 ; i < 6; i ++)
			for(int j = 0 ; j < 6; j ++)
			{
				buttons[i][j] = new JButton();
				if((i+j)/2.0 == (int) (i+j)/2) buttons[i][j].setBackground(new Color(255, 255, 255));
				else buttons[i][j].setBackground(new Color(0, 0, 0));
				panel.add(buttons[i][j]);
			}
		frame = new JFrame("King's Army");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	public class Click implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			JButton clicked = (JButton)arg0.getSource();
			if(selected == null)
			{
				
				for(int i = 0; i < buttons.length; i++)
					for(int j = 0; j < buttons[i].length; j++)
						if(clicked.equals(buttons[i][j]))
						{
							ArrayList<Position> p = b.getPiecesMoves(new Position(i,j));
							for(int l = 0; l < p.size(); l++)
								options.add(buttons[p.get(l).x()][p.get(l).y()]);
							x= i;
							y=j;
						}
			}
			else if(!(options.indexOf(clicked) == -1))
			{
				for(int i = 0; i < buttons.length; i++)
					for(int j = 0; j < buttons[i].length; j++)
						if(clicked.equals(buttons[i][j]))
						{
							b.movePiece(new Position(x,y), new Position(i,j));
						}
			}
						
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}

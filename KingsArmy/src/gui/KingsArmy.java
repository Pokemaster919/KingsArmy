package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Board;
import main.Position;

public class KingsArmy
{
	JFrame frame;
	Click list;
	JPanel panel;
	JButton[][] buttons;
	JButton selected;
	Color sColor;
	int x, y;
	ArrayList<Color> oColor;
	ArrayList<JButton> options;
	Board b;
	public static void main(String[] args)
	{
		KingsArmy army = new KingsArmy();
	}
	public KingsArmy()
	{
		list = new Click();
		b = new Board(7);
		buttons = new JButton[7][7];
		try {
			Assets.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initialize();
	}
	public void initialize()
	{
		selected = null;
		panel = new JPanel();
		panel.setLayout(new GridLayout(7,7));
		for(int i = 0 ; i <= 6; i ++)
			for(int j = 0 ; j <= 6; j ++)
			{
				buttons[i][j] = new JButton();
				if((i+j)/2.0 == (int) (i+j)/2) buttons[i][j].setBackground(new Color(255, 255, 255));
				else buttons[i][j].setBackground(new Color(0, 0, 0));
				panel.add(buttons[i][j]);
				buttons[i][j].addMouseListener(list);
				buttons[i][j].setPreferredSize(new Dimension(100, 100));
			}
		frame = new JFrame("King's Army");
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		frame.setVisible(true);
		Dimension d = buttons[0][0].getSize();
		BufferedImage i  = Assets.rPikemanAtt[0];
		ImageIcon image = new ImageIcon(i.getScaledInstance(175, 175, 0));
		for(int j = 0; j < 7; j++)
		{
			buttons[j][1].setIcon(image);
		}
		d = buttons[0][0].getSize();
		i  = Assets.pPikemanAtt[0];
		image = new ImageIcon(i.getScaledInstance(175, 175, 0));
		for(int j = 0; j < 7; j++)
		{
			buttons[j][5].setIcon(image);
		}
		d = buttons[0][0].getSize();
		i  = Assets.rPikemanAtt[0];
		image = new ImageIcon(i.getScaledInstance(175, 175, 0));
		for(int j = 0; j < 2; j++)
		{
			buttons[j][0].setIcon(image);
		}
		d = buttons[0][0].getSize();
		i  = Assets.rPikemanAtt[0];
		image = new ImageIcon(i.getScaledInstance(175, 175, 0));
		for(int j = 5; j < 7; j++)
		{
			buttons[j][0].setIcon(image);
		}
		d = buttons[0][0].getSize();
		i  = Assets.pPikemanAtt[0];
		image = new ImageIcon(i.getScaledInstance(175, 175, 0));
		for(int j = 0; j < 2; j++)
		{
			buttons[j][6].setIcon(image);
		}
		d = buttons[0][0].getSize();
		i  = Assets.pPikemanAtt[0];
		image = new ImageIcon(i.getScaledInstance(175, 175, 0));
		for(int j = 5; j < 7; j++)
		{
			buttons[j][6].setIcon(image);
		}
		d = buttons[0][0].getSize();
		i  = Assets.rPikemanAtt[0];
		image = new ImageIcon(i.getScaledInstance(175, 175, 0));
		for(int j = 0; j < 2; j++)
		{
			buttons[j][0].setIcon(image);
		}
		d = buttons[0][0].getSize();
		i  = Assets.rKnightAtt[0];
		image = new ImageIcon(i.getScaledInstance(120, 120, 0));
		buttons[4][0].setIcon(image);
		buttons[2][0].setIcon(image);
		d = buttons[0][0].getSize();
		i  = Assets.pKnightAtt[0];
		image = new ImageIcon(i.getScaledInstance(120, 120, 0));
		buttons[4][6].setIcon(image);
		buttons[2][6].setIcon(image);
		d = buttons[0][0].getSize();
		i  = Assets.rKingAtt[0];
		image = new ImageIcon(i.getScaledInstance(150, 150, 0));
		buttons[3][0].setIcon(image);
		d = buttons[0][0].getSize();
		i  = Assets.pKingAtt[0];
		image = new ImageIcon(i.getScaledInstance(150, 150, 0));
		buttons[3][6].setIcon(image);
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
							options = new ArrayList<JButton>();
							oColor = new ArrayList<Color>();
							for(int l = 0; l < p.size(); l++)
							{
								options.add(buttons[p.get(l).x()][p.get(l).y()]);
								oColor.add(options.get(l).getBackground());
								options.get(l).setBackground(Color.GREEN);
							}
								
							x= i;
							y=j;
							selected = clicked;
							sColor = selected.getBackground();
							selected.setBackground(Color.YELLOW);
						}
			}
			else if(!(options.indexOf(clicked) == -1))
			{
				for(int i = 0; i < buttons.length; i++)
					for(int j = 0; j < buttons[i].length; j++)
						if(clicked.equals(buttons[i][j]))
						{
							if(b.movePiece(new Position(x,y), new Position(i,j)))
							{
								System.out.println(selected.getIcon().toString());
							}
							clicked.setIcon(selected.getIcon());
							selected.setIcon(null);
							selected.setBackground(sColor);
							for(int l = 0; l < options.size(); l++)
							{
								options.get(l).setBackground(oColor.get(l));
							}
							selected = null;
							options = null;
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

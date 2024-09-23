

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class CaroOffline extends JFrame implements MouseListener{

	int n = 15;
	int s = 30;
	int os = 50;
	
	List<Point> dadanh = new ArrayList<Point>();
	
	public static void main(String[] args) {
		new CaroOffline();
	}
	public CaroOffline() {
		this.setTitle("Co Caro");
		this.setSize(n*s+os*2, n*s+os*2);
		this.setDefaultCloseOperation(3);
		this.addMouseListener(this);
		
		this.setVisible(true);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.BLACK);
		for (int i=0;i<=n;i++) {
			g.drawLine(os, s*i+os, s*n+os, s*i+os);
			g.drawLine(s*i+os, os, s*i+os, s*n+os);
		}
		
		g.setFont(new Font("arial",Font.BOLD,s));
		
		for (int i=0;i<dadanh.size();i++) {
			String str = "o";
			Color c = Color.RED;
			if (i%2==1) {
				str = "x";
				c = Color.BLUE;
			}
			int x = dadanh.get(i).x*s + os + s - s/2 - s/4;
			int y = dadanh.get(i).y*s + os + s - s/2 + s/4;
			g.setColor(c);
			g.drawString(str, x, y);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (x<os || x>=os+s*n) return;
		if (y<os || y>=os+s*n) return;
		
		int ix = (x-os)/s;
		int iy = (y-os)/s;
		
		for (Point p : dadanh) {
			if (p.x == ix &&  p.y ==iy) return;
		}
		
		dadanh.add(new Point(ix,iy));
		this.repaint();
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

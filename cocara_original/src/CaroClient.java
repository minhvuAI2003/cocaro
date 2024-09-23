

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CaroClient extends JFrame implements MouseListener, Runnable {

	int n = 15;
	int s = 30;
	int os = 50;

	List<Point> dadanh = new ArrayList<Point>();
	Socket soc;

	public static void main(String[] args) {
		new CaroClient();
	}

	public CaroClient() {
		this.setTitle("Co Caro");
		this.setSize(n * s + os * 2, n * s + os * 2);
		this.setDefaultCloseOperation(3);
		this.addMouseListener(this);
		Thread t = new Thread(this);
		t.start();

		this.setVisible(true);
		try {
			soc = new Socket("localhost", 5555);
		} catch (Exception e) {
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.BLACK);
		for (int i = 0; i <= n; i++) {
			g.drawLine(os, s * i + os, s * n + os, s * i + os);
			g.drawLine(s * i + os, os, s * i + os, s * n + os);
		}

		g.setFont(new Font("arial", Font.BOLD, s));

		for (int i = 0; i < dadanh.size(); i++) {
			String str = "o";
			Color c = Color.RED;
			if (i % 2 == 1) {
				str = "x";
				c = Color.BLUE;
			}
			int x = dadanh.get(i).x * s + os + s - s / 2 - s / 4;
			int y = dadanh.get(i).y * s + os + s - s / 2 + s / 4;
			g.setColor(c);
			g.drawString(str, x, y);
		}
	}

	public void run() {
		int t = 1;
		// Todo: nhận phản hồi của server

		while (t > 0) {

			try {
				DataInputStream dis = new DataInputStream(soc.getInputStream());
				String thongbao1 = dis.readUTF();
				System.out.println(thongbao1);
				if (thongbao1.equals("100")) {
					JOptionPane.showMessageDialog(rootPane, "Tro choi ket thuc");
					t = 0;
				} else {
					String thongbao2 = dis.readUTF();
					int ix = Integer.parseInt(thongbao1);
					int iy = Integer.parseInt(thongbao2);
					dadanh.add(new Point(ix, iy));
					this.repaint();
				}
			} catch (Exception e) {

			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (x < os || x >= os + s * n)
			return;
		if (y < os || y >= os + s * n)
			return;

		int ix = (x - os) / s;
		int iy = (y - os) / s;

		// Todo: Gui toa do cho server
		try {
			DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
			dos.writeUTF(ix + "");
			dos.writeUTF(iy + "");
		} catch (Exception e1) {

		}

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

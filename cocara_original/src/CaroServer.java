

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CaroServer {

	public static void main(String[] args) {
		new CaroServer();
	}

	int ok = 0;
	int n = 15;
	List<Point> dadanh = new ArrayList<Point>();
	Vector<CaroProcessing> Clientlist = new Vector<CaroProcessing>();

	public CaroServer() {
		try (ServerSocket server = new ServerSocket(5555)) {
			while (true) {
				Socket soc = server.accept();
				CaroProcessing t = new CaroProcessing(soc, this);
				Clientlist.add(t);
				t.start();
			}
		} catch (Exception e) {

		}
	}
}

class CaroProcessing extends Thread {
	Socket soc;
	CaroServer server;

	public CaroProcessing(Socket soc, CaroServer server) {
		this.soc = soc;
		this.server = server;
	}

	public void run() {

		try {
			DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
			for (int i = 0; i < server.dadanh.size(); i++) {
				dos.writeUTF(server.dadanh.get(i).x + "");
				dos.writeUTF(server.dadanh.get(i).y + "");
			}
		} catch (Exception e) {

		}
		boolean loop = true;
		System.out.println(loop);
		while (loop) {
			try {
				if (this.server.ok == 1) {
					for (int i = 0; i < server.Clientlist.size(); i++) {
				
						try {

							DataOutputStream dost = new DataOutputStream(
									server.Clientlist.get(i).soc.getOutputStream());
							dost.writeUTF(100 + "");
						} catch (Exception e1) {

						}
					}
					break;
				}
				System.out.println(soc.getInputStream());
				DataInputStream dis = new DataInputStream(soc.getInputStream());
				int ix = Integer.parseInt(dis.readUTF());
				int iy = Integer.parseInt(dis.readUTF());
				System.out.println(ix + " " + iy);
				System.out.println("Hello");
				// Kiem tra
				// Kiểm tra bản thân có được đánh hay không
				if (!((this == server.Clientlist.get(0) && server.dadanh.size() % 2 == 0)
						|| (this == server.Clientlist.get(1) && server.dadanh.size() % 2 == 1)))
					continue;
				// Có đánh ra ngoài bàn cờ không?
				if (ix < 0 || ix >= server.n)
					continue;
				if (iy < 0 || ix >= server.n)
					continue;

				// Có đánh ô đã đánh hay chưa
				boolean ok = true;
				for (Point p : server.dadanh) {
					if (p.x == ix && p.y == iy) {
						ok = false;
						break;
					}
				}
				if (!ok)
					continue;

				// gửi tọa độ cho tất cả client
				server.dadanh.add(new Point(ix, iy));
				for (int i = 0; i < server.Clientlist.size(); i++) {
					try {
						DataOutputStream dost = new DataOutputStream(server.Clientlist.get(i).soc.getOutputStream());
						dost.writeUTF(ix + "");
						dost.writeUTF(iy + "");
					} catch (Exception e1) {

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				loop = false;
				if (this == server.Clientlist.get(0) || this == server.Clientlist.get(1) || this.server.ok == 1)
					for (int i = 0; i < server.Clientlist.size(); i++) {
						this.server.ok = 1;
						try {

							DataOutputStream dost = new DataOutputStream(
									server.Clientlist.get(i).soc.getOutputStream());
							dost.writeUTF(100 + "");
						} catch (Exception e1) {

						}
					}
			}
		}
	}
}

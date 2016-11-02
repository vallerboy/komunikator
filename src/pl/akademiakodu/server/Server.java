package pl.akademiakodu.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static final String HOST = "localhost";
	public static final int PORT = 4122;
	private ServerSocket server;

	private ExecutorService threadService;

	public static void main(String[] args) {

		 new Server();

	}

	public Server() {
		// tworzymy nowy serwer
		InetSocketAddress adress = new InetSocketAddress(HOST, PORT);
		try {
			server = new ServerSocket();
			server.bind(adress);
		} catch (IOException e) {
			System.out.println("B��d podczas inicjalizacji serwera");
			e.printStackTrace();
		}

		threadService = Executors.newFixedThreadPool(20);
		listenToSockets();
	}

	private void listenToSockets() {

		System.out.println("~Rozpoczynam nas�uchiwanie~");
		Runnable runnable = new Runnable() {
			@Override
			public void run() {

				while (true) {
					try {
						Socket socket = server.accept();
						PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
						writer.println("Witaj przyjacielu!");
						writer.close();
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		};

		threadService.execute(runnable);
	}

}
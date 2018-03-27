package exercici3;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class nodeC2 implements Runnable{
	int id;
	int numActus;
	FileWriter writer ;
	int dades[] = new int[100];
	//int ports[] = new int[2];
	ArrayList<Socket>llistasock= new ArrayList<Socket>();

	public nodeC2(int num){
		id=num;
		for(int i=0;i<100;i++){
			dades[i]=0;
		}
	//	dades[84]=13;
	}
	public void run3(){


		while(true){
			try {
				Thread.sleep(10000);
				System.out.print("10 segons!!!");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//	setNumActus(0);
			for(int i=0;i<llistasock.size();i++){

				try {



					PrintWriter out;

					out = new PrintWriter(llistasock.get(i).getOutputStream(), true);
					out.println(Arrays.toString(getDades()));
					out.flush();

				} catch (IOException e) {
					System.out.println("Conexió no trobada");
					e.printStackTrace();
				}

			}

		}

	}

	public int getNumActus() {
		return numActus;
	}
	public void setNumActus(int numActus) {
		this.numActus = numActus;
	}
	public void run2(Socket s){ //


		//System.out.println("portRebut"+answer);

		try {
			BufferedReader input =
					new BufferedReader(new InputStreamReader(s.getInputStream()));

			String answer = input.readLine();
			PrintWriter out = null;
			if(answer.charAt(0)=='b'){

				String[] comandes = answer.split(",");
				for(int i=1;i<comandes.length-1;i++){
					//System.out.print(comandes[i]);

					int index= Integer.parseInt(comandes[i].substring(2,comandes[i].length()-1));
				//	System.out.print("lectura iindex]);

					out = new PrintWriter(s.getOutputStream());
					out.println(getDades()[index]);
					out.flush();
				}
				out.println("c");
				out.flush();
			}

			else{
				



					System.out.println("Node "+id+" gravant");
					String a[]=answer.split(",");
					int temp[] = new int[100];
				//	temp[0]=Integer.parseInt(a[0].substring(1,a[99].length()-1));
					for(int i=0;i<99;i++){
						temp[i]=Integer.parseInt(a[i].substring(1,a[i].length()));
					}
					temp[99]=Integer.parseInt(a[99].substring(1,a[99].length()-1));
					setDades(temp);

					try {
						writer= new FileWriter(id+".txt",true);
						writer.write("\r\n");
						writer.write(Arrays.toString(getDades()));
						writer.close();
						

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Notifico l'altre de la capa:
				}

			

		} catch (IOException e) {
			System.out.println("Conexió no trobada");
			e.printStackTrace();
		}


	}
	public synchronized int[] getDades() {
		return dades;
	}
	public synchronized void setDades(int[] dades) {
		this.dades = dades;
	}
	public void afageixNode(int port){
		try {
			Socket s3 = new Socket("localhost", port+9090);
			llistasock.add(s3);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	public void run() { //lectures i solictud de copia


		new Thread(
				new Runnable() {
					public void run() {
						run3();
					}
				}
				).start();


		try {
			ServerSocket listener = new ServerSocket(9090+id);
			try {
				System.out.println("node "+id+" running.");
				while (true) {

					Socket socket = listener.accept();
					new Thread(new Runnable() {
						public void run() {
							run2(socket);
						}
					}).start();

				}
			} finally {
				listener.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

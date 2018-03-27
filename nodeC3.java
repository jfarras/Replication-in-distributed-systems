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

public class nodeC3 implements Runnable{
	int id;
	int numActus;
	FileWriter writer ;
	int dades[] = new int[100];
	//int ports[] = new int[2];
	
	public nodeC3(int num){
		id=num;
		for(int i=0;i<100;i++){
			dades[i]=0;
		}
		//dades[3]=18;
		//dades[84]=4;
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
				//	System.out.print(comandes[i]);

					int index= Integer.parseInt(comandes[i].substring(2,comandes[i].length()-1));
				//	System.out.print("lectura"+getDades()[index]);

					out = new PrintWriter(s.getOutputStream(), true);
					out.println(getDades()[index]);
					out.flush();
				}
				out.println("c");//indica el final
				out.flush();
			}

			else{

				while(true){


					System.out.println("Node "+id+" gravant");
					String a[]=answer.split(",");
					int temp[] = new int[100];
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
						answer = input.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//int port= Integer.parseInt(answer);

				//Socket s2 =new Socket("localhost",port);




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


	public void run() { //lectures i solictud de copia


		try {
			ServerSocket listener = new ServerSocket(9090+id);
			try {
				System.out.println("node "+id+" running.");
				while (true) {

					Socket socket = listener.accept();
					new Thread(new Runnable() {

						public void run() {
							System.out.println("accept");
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


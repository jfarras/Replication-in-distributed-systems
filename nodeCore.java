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


public class nodeCore implements Runnable{
	int dades[] = new int[100];
	int id;
	static Integer semafor = new Integer(0);
	int numActus=0;
	FileWriter writer ;
	int ports[] = new int[2];
	int numAfegits=0;
	public nodeCore(int num){

		id=num;
		for(int i=0;i<100;i++){
			dades[i]=0;
		}
		//dades[2]=76;
	}
	public synchronized FileWriter getWriter() {
		return writer;
	}
	@Override
	public void run() {
		try {
			ServerSocket listener = new ServerSocket(9090+id);
			System.out.println("node "+id+" running.");
			while (true) {
				try {

					Socket s = listener.accept();

					BufferedReader input =
							new BufferedReader(new InputStreamReader(s.getInputStream()));
					String answer = input.readLine();
					//	System.out.println("numAc"+numActus);
					if(answer.charAt(0)=='r'){
						int index=0;
						
						index=Integer.parseInt(answer.substring(2, answer.length()-1));
						PrintWriter out = new PrintWriter(s.getOutputStream(), true);
						//System.out.println("index:"+index);
						synchronized (semafor){
						out.println("resposta:"+getDades()[index]);
						}
						out.flush();
					}
					else if(answer.charAt(0)=='w'){

						//System.out.println("WRITE");
						String[] separats = answer.split("-");
						int index,valor;
						//System.out.println(separats[0]+" "+separats[1]);
						index=Integer.parseInt(separats[0].substring(2, separats[0].length()));
						valor=Integer.parseInt(separats[1].substring(0, separats[1].length()-1));
						
						PrintWriter out = new PrintWriter(s.getOutputStream(), true);
						out.println("ok");
						
						out.flush();
						answer = input.readLine();
						if(answer.equals("ok")){
						
						System.out.println("Node "+id+" gravant");
						synchronized (semafor){
							setDades(index, valor);
							}
						//out.flush();
						
						try {
							out.println("resposta:"+getDades()[index]);
							writer= new FileWriter(id+".txt",true);
							writer.write("\r\n");
							writer.write("\nindex: "+index+" valor: "+valor);
							writer.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						numActus++;
						
						}
					}if (numActus==10){

						numActus=0;
						for(int i=0;i<numAfegits;i++){
							try {
								
								Socket s3 = new Socket("localhost", ports[i]+9090);
								PrintWriter out;

								out = new PrintWriter(s3.getOutputStream(), true);
								synchronized (semafor){
								out.println(Arrays.toString(getDades()));
							//	System.out.println(Arrays.toString(getDades()));
								}
								out.flush();
								



							} catch (IOException e) {
								System.out.println("Conexió no trobada");
								e.printStackTrace();
							}

						}

					}
							

				}
				finally {

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub

	}

	public synchronized void setDades(int index,int valor) {
		this.dades[index] = valor;
	}
	public void afageixNode(int port){


		ports[numAfegits]=port;
		numAfegits++;


	}
	public synchronized int[] getDades() {
		return dades;
	}


}

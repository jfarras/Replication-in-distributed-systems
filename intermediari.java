package exercici3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class intermediari implements Runnable {


	public intermediari (){

	}
	public void run2(Socket s){

	}


	public void run() {


		try {
			ServerSocket listener = new ServerSocket(9090);
			try {
				System.out.println("Central server running.");
				while (true) {

					Socket s = listener.accept();
					
					try {
						//System.out.println("Conexió no trobada");

						BufferedReader input =
								new BufferedReader(new InputStreamReader(s.getInputStream()));
						



						String frase = input.readLine();
						while(!frase.equals("fi")){
							PrintWriter out;


							out = new PrintWriter(s.getOutputStream(), true);
							//out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkko");
							//out.flush();

							String[] comandes = frase.split(",");
							Random randomno = new Random();
							//System.out.println(comandes.length);
							for(int i=1;i<comandes.length-1;i++){
								if(comandes[i].charAt(0)=='r'){

									int valor=(randomno.nextInt(2))+1;
								//	System.out.println(valor);
									Socket s2 =new Socket("localhost",9090+valor);
									PrintWriter out2;

									out2 = new PrintWriter(s2.getOutputStream(), true);
									out2.println(comandes[i]);
									out2.flush();
									BufferedReader input2 =
											new BufferedReader(new InputStreamReader(s2.getInputStream()));
									String answer2 = input2.readLine();
									out.println(answer2);
									out.flush();

									s2.close();
								}
								else{
									LinkedList<Socket> confirms=new LinkedList<>();
									int confirmacions=0;
									for(int j=1;j<4;j++){
										PrintWriter out2;
										Socket s2 =new Socket("localhost",9090+j);
										out2 = new PrintWriter(s2.getOutputStream(), true);
										out2.println(comandes[i]);
										out2.flush();
										BufferedReader input2 =
												new BufferedReader(new InputStreamReader(s2.getInputStream()));
										String answer2 = input2.readLine();
										if(answer2.equals("ok")){
											
											confirmacions++;
										}
										confirms.add(s2);
										//s2.close();

									}for(int j=0;j<3;j++){
										PrintWriter out2;
										out2 = new PrintWriter(confirms.get(j).getOutputStream(), true);
										if(confirmacions==3)out2.println("ok");
										
										else out2.println("ko");
										out2.flush();
										confirms.get(j).close();
									}
									
									
								}
							}out.println("c");
							out.flush();
							//	System.out.println("rebut");

							frase = input.readLine();
						}
					} catch (UnknownHostException e) {
						System.out.println("Conexió no trobada");
						e.printStackTrace();
					} catch (IOException e) {
						System.out.println("Conexió no trobada");
						e.printStackTrace();
					}

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

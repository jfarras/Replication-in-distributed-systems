package exercici3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class client {

	intermediari interm;
	int id;
	public client(int num){
		id=num;
		interm=new intermediari();

	}
	public void read(){
		BufferedReader br = null;

		try {
			Socket s =new Socket("localhost",9090);
			String line;

			br = new BufferedReader(new FileReader("./instruccions"+id+".txt"));
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			while ((line = br.readLine()) != null) {

				
				Random randomno = new Random();
				String[] comandes = line.split(",");
				if(!(comandes[0].equals("b")||(comandes[0].equals("b0")))){
					//System.out.println((comandes[0]));



					if (comandes[0].equals("b1")){
						
						int valor=randomno.nextInt(1)+4;
						Socket s3 =new Socket("localhost",9090+valor);
						PrintWriter out3;
						System.out.println("Client"+id+"envia: "+line);

						out3 = new PrintWriter(s3.getOutputStream(), true);
						out3.println(line);
						out3.flush();
						BufferedReader input3 =
								new BufferedReader(new InputStreamReader(s3.getInputStream()));
						String answer3 = input3.readLine();
						//System.out.println("client "+id+"rebut : "+answer3);
						while(!answer3.equals("c")){
							System.out.println("Client "+id+": rebut: "+answer3);
							answer3 = input3.readLine();
						}
					}else {
						System.out.println("Client "+id+" envia: "+line);
						int valor2=randomno.nextInt(1)+6;
						Socket s2 =new Socket("localhost",9090+valor2);
						PrintWriter out2;

						out2 = new PrintWriter(s2.getOutputStream(), true);
						out2.println(line);
						out2.flush();
						BufferedReader input2 =
								new BufferedReader(new InputStreamReader(s2.getInputStream()));
						String answer2 = input2.readLine();
						while(!answer2.equals("c")){
							System.out.println("Client"+id+": rebut "+answer2);
							answer2 = input2.readLine();
						}

					}

				}
				else {

					System.out.println("Client"+id+" envia: "+line);

					out.println(line);
					out.flush();

					BufferedReader input =
							new BufferedReader(new InputStreamReader(s.getInputStream()));
					String answer = input.readLine();
					while(!answer.equals("c")){
						System.out.println("Client"+id+": rebut: "+answer);
						answer = input.readLine();
					}
				}


			}
			out.println("fi");
			out.flush();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}

package exercici3;


public class main {
	private static Thread client(int id){
		return new Thread()
		{

			client cl =new client(id);
			public void run()
			{	

				cl.read();
				//while(1==1);
			}
		};

	}



	public static void main(String[] args)
	{
		
		new Thread(new intermediari()).start();
		nodeC3 c3 =new nodeC3(6);

		Thread c3Thread = new Thread(c3);
		c3Thread.start();

		nodeC3 c3dos=new nodeC3(7);

		Thread c3dosThread = new Thread(c3dos);
		c3dosThread.start();
		Thread c = client(0);
		Thread c1 = client(1);
		c.start();
		c1.start();
		nodeC2 c2=new nodeC2(4);

		Thread c2Thread = new Thread(c2);
		c2Thread.start();

		nodeC2 c2dos=new nodeC2(5);
		c2dos.afageixNode(6);
		c2dos.afageixNode(7);

		Thread c2dosThread = new Thread(c2dos);
		c2dosThread.start();





		nodeCore core1=new nodeCore(1);


		Thread core1thread = new Thread(core1);
		core1thread.start();

		nodeCore core2=new nodeCore(2);
		core2.afageixNode(4);

		Thread core2thread = new Thread(core2);
		core2thread.start();

		nodeCore core3=new nodeCore(3);
		core3.afageixNode(5);

		Thread core3thread = new Thread(core3);
		core3thread.start();



	}

}

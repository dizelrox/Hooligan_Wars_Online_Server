import java.io.*;
import java.net.*;
import java.util.Scanner;
import Logic.Player;

public class ThreadHandler extends Thread
{

	// socket number
	Socket				clientSocket1;
	Socket				clientSocket2;

	ObjectInputStream	inputFromClient1;
	ObjectInputStream	inputFromClient2;

	ObjectOutputStream	outputToClient1;
	ObjectOutputStream	outputToClient2;

	Integer				STARTING_NEW_GAME	= new Integer(1);

	public ThreadHandler(Socket socket1, Socket socket2)
	{

		// initialize upon creation
		clientSocket1 = socket1;
		clientSocket2 = socket2;

		try
		{
		inputFromClient1 = new ObjectInputStream(clientSocket1.getInputStream());
		inputFromClient2 = new ObjectInputStream(clientSocket2.getInputStream());

		outputToClient1 = new ObjectOutputStream(
				clientSocket1.getOutputStream());
		outputToClient2 = new ObjectOutputStream(
				clientSocket2.getOutputStream());
		} catch (IOException e)
		{
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}

	public void run()
	{
		try
		{
		outputToClient1.writeObject(STARTING_NEW_GAME);
		outputToClient2.writeObject(STARTING_NEW_GAME);

		Object player1 = inputFromClient1.readObject();
		Object player2 = inputFromClient2.readObject();

		outputToClient1.writeObject(player2);
		outputToClient2.writeObject(player1);
		
		outputToClient1.writeObject(true);
		outputToClient2.writeObject(false);

		while (true)
		{
		Object player2DefencekArea = inputFromClient2.readObject();	
		Object player1AtteackArea = inputFromClient1.readObject();	
			
		outputToClient1.writeObject(player2DefencekArea);
		outputToClient2.writeObject(player1AtteackArea);

		Object player1DefencekArea = inputFromClient1.readObject();	
		Object player2AtteackArea = inputFromClient2.readObject();	
			
		outputToClient1.writeObject(player2AtteackArea);
		outputToClient2.writeObject(player1DefencekArea);
		
		
		}
		} catch (ClassNotFoundException e)
		{
		this.stop();
		} catch (IOException e)
		{
		// TODO Auto-generated catch block
		this.stop();
		}

	}
}
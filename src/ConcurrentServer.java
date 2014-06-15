import java.io.*;
import java.net.*;

public class ConcurrentServer
{

	public static void main(String[] args)
	{
		ServerSocket serverSocket = null;
		Socket clientSocket1 = null;
		Socket clientSocket2 = null;
		
		
		System.out.println();
		System.out.println("*************************************");
		System.out.println("Concurrent Server side console");
		System.out.println("*************************************");

		try
		{

		serverSocket = new ServerSocket(55555);

		while (true)
		{

		clientSocket1 = serverSocket.accept();
		System.out.println("First client connected from "
				+ clientSocket1.getLocalAddress().getHostName()
				+ clientSocket1.getLocalAddress() + " port "
				+ clientSocket1.getPort()+"waiting for second...");
		
		clientSocket2 = serverSocket.accept();
		System.out.println("Second client connected from "
				+ clientSocket2.getLocalAddress().getHostName()
				+ clientSocket2.getLocalAddress() + " port "
				+ clientSocket2.getPort()+"starting new thread for them");
		
		
		Thread newThread = new ThreadHandler(clientSocket1, clientSocket2);
		newThread.start();
		}
		} 
		catch (IOException ioe)
		{

		ioe.printStackTrace();
		}
		finally
		{
			try
			{
			serverSocket.close();
			clientSocket1.close();
			clientSocket2.close();
			} catch (IOException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			
		}
	}
}
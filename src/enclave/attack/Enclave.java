package enclave.attack;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

// the server needs to be multithreaded
// if it receives another ecall from the same thread pid then throw and exception, catch it and send the rest of the
// program into exception handling, and exit all threads no matter what the state of the program
public class Enclave
{

	public int authCount;
	public HashSet<General> generalInfo;
	public int counter;

	public Enclave()
	{
		authCount = 0;
		generalInfo = new HashSet<>();
		counter = 0;
	}

	/**
	 * Runs the server.
	 */
	public static void main(String[] args) throws IOException
	{
		Enclave enclave = new Enclave();
		General g1 = new General("general1", 12345L);
		General g2 = new General("general2", 56789L);

		enclave.generalInfo.add(g1);
		enclave.generalInfo.add(g2);

		ServerSocket listener = new ServerSocket(9090);

		try
		{
			while (true)
			{
				Socket socket = listener.accept();

				new Thread(new ThreadWorker(socket, enclave)).start();

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			listener.close();
		}
	}
}

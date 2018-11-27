package serverPC;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PC
{
	public static String prefix = "com.calculator.matrix";
	public static void main(String[] args)
	{
		String hname="localhost";
		if(args.length > 0)
			hname = args[0];

		System.setProperty("java.rmi.server.hostname", hname);
		PC2IO_Impl srvr = null;
		Registry registry = null;
		int port = 50000;
		//-- Create object to be exported
		try{
			srvr = new PC2IO_Impl();
		}
		catch (RemoteException e1) {
			System.err.println("Cannot create exportable object");
			System.exit(1);
		}
		//-- Attempt to create or locate RMI registry
		for(;;port++) {
			try {
				registry  = LocateRegistry.createRegistry(port);
				registry.rebind(prefix+"_ServerTest", srvr);
				System.out.println("Creating new rmiregistry");
				break;
			} 
			catch (RemoteException e) {
				try {
					registry = LocateRegistry.getRegistry(port);
					registry.rebind(prefix+"_ServerTest", srvr);
					System.out.println("Re-using existing rmiregistry");
					break;
				}
				catch (RemoteException e2) {
				}		
			}
		}
		System.out.printf("server.RMI Server is ready. Registry is available on port %d", port);
	}
}


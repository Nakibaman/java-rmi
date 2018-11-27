package commonPC2IO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PC2IO extends Remote {

	public int[][] mult(int[][] matrixA, int[][] matrixB) throws RemoteException;

}
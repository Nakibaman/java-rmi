package serverPC;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import commonPC2IO.PC2IO;

public class PC2IO_Impl extends UnicastRemoteObject implements PC2IO {

	private static final long serialVersionUID = 1L;

	public PC2IO_Impl() throws RemoteException {
		super();
	}

	//Calculate product of matrixA and matrixB
	public int[][] mult(int[][] matrixA, int[][] matrixB) throws RemoteException {

		int[][] matrixC = new int [matrixA.length][matrixA.length];
		
	    //Generates matrix C
		for(int i=0; i < matrixA.length; i++) {
            for(int j=0; j < matrixB[i].length; j++) {
                for(int k=0; k < matrixA[i].length; k++) {
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
	    
	    return matrixC;
	}
}

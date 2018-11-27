
package clientIO;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;


import commonPC2IO.PC2IO;

public class IO
{
	public static String prefix = "com.calculator.matrix";
	public static void main(String[] args) throws IOException { 
		String dest = "localhost";		
		int port = 50000;
		if(args.length > 3) // the destination ip address is supplied in command line arguments too
			dest = args[4];
		Registry registry = null;
		for(;;port++) {
			try {
				registry = LocateRegistry.getRegistry(dest,port);
				String s[] = registry.list();
				boolean flag = false;
				for(int i=0; i< s.length; i++) {
					if(s[i].startsWith(prefix)) {
						flag = true;
					}						
				}
				if(flag)
					break;				
			} 
			catch (RemoteException e) {				
			}
		}

        try {
        	//Call Registry
            PC2IO mtrx = (PC2IO) registry.lookup(prefix+"_ServerTest");

            //Establishes size of desired matrices A and B via a command line argument
			int size = 4;

			int rowA = 4;
			int colA = 4;

			int rowB = 4;
			int colB = 4;
			try
			{
			/* supply the 4 parameters from command prompt

				rowA = Integer.parseInt(args[0]);
				colA = Integer.parseInt(args[1]);
				rowB = Integer.parseInt(args[2]);
				colB = Integer.parseInt(args[3]);
			*/
			}
			catch(NumberFormatException ex) {
				System.err.println("First command-line argument must be an integer");
				System.exit(2);
			}

	        //Create matrices A and B
	       // int rows = size;
			// int columns = size;
			
			int[][] matrixA = new int [rowA][colA];
			int[][] matrixB = new int [rowB][colB];
	        
			Random rand = new Random();
			
			//Generate matrix A
		    for(int i=0; i < matrixA.length; i++){
		        for(int j=0; j < matrixA[i].length; j++){
		        	matrixA[i][j] = rand.nextInt(201) + (-100);
		        }
		    }
		    
		    //Generate matrix B
		    for(int i=0; i < matrixB.length; i++){
		        for(int j=0; j < matrixB[i].length; j++){
		        	matrixB[i][j] = rand.nextInt(201) + (-100);
		        }
		    }
		    
		    //-- Local store created
			PrintWriter pwA = null;
			PrintWriter pwB = null;
			PrintWriter pwC = null;
			
			//-- Open the writers
			try {
				pwA = new PrintWriter(new FileWriter("matrixA.log",true));			
			}
			catch (IOException ex) {
				System.err.println("Cannot create writer.");
				System.exit(1);
			}
			try {
				pwB = new PrintWriter(new FileWriter("matrixB.log",true));			
			}
			catch (IOException ex) {
				System.err.println("Cannot create writer.");
				System.exit(1);
			}
			try {
				pwC = new PrintWriter(new FileWriter("matrixC.log",true));			
			}
			catch (IOException ex) {
				System.err.println("Cannot create writer.");
				System.exit(1);
			}
			
			//Print matrix A to log file
			pwA.println("\r\nMatrix A:\r\n");
		    for(int i=0; i < matrixA.length; i++){
		        for(int j=0; j < matrixA[i].length; j++){
		            pwA.print(matrixA[i][j] + "\t");
		        }
		        pwA.println("\r\n");
		    }
			
		    //Print matrix B to log file
			pwB.println("\r\nMatrix B:\r\n");
		    for(int i=0; i < matrixB.length; i++){
		        for(int j=0; j < matrixB[i].length; j++){
		            pwB.print(matrixB[i][j] + "\t");
		        }
		        pwB.println("\r\n");
		    }
		    
		    //Calls Remote Method
		    mtrx.mult(matrixA, matrixB);
		    
		    //Print matrix C to log file
		    pwC.println("\r\nMatrix C:\r\n");
		    for(int i=0; i < (mtrx.mult(matrixA, matrixB)).length; i++){
		        for(int j=0; j < (mtrx.mult(matrixA, matrixB))[i].length; j++){
		            pwC.print((mtrx.mult(matrixA, matrixB))[i][j] + "\t");
		        }
		        pwC.println("\r\n");
		    }
		    
		    //-- Close the writers
		    pwA.close();
		    pwB.close();
		    pwC.close();
		    
		    //Print matrices A, B and C to screen if under 10*10
		    if (size < 10) {
		    	//Print matrix A
				System.out.println("\r\nMatrix A:\r\n");
			    for(int i=0; i < matrixA.length; i++){
			        for(int j=0; j < matrixA[i].length; j++){
			            System.out.print(matrixA[i][j] + "\t");
			        }
			        System.out.println("\r\n");
			    }
				
			    //Print matrix B
			    System.out.println("\r\nMatrix B:\r\n");
			    for(int i=0; i < matrixB.length; i++){
			        for(int j=0; j < matrixB[i].length; j++){
			            System.out.print(matrixB[i][j] + "\t");
			        }
			        System.out.println("\r\n");
			    }
			    
			    //Print matrix C
			    System.out.println("\r\nMatrix C:\r\n");
			    for(int i=0; i < (mtrx.mult(matrixA, matrixB)).length; i++){
			        for(int j=0; j < (mtrx.mult(matrixA, matrixB))[i].length; j++){
			            System.out.print((mtrx.mult(matrixA, matrixB))[i][j] + "\t");
			        }
			        System.out.println("\r\n");
			    }
		    }
		    else {
		    	System.out.println("Matrices are too big to print. See output log for matrix values.");
		    }
        } 
        catch (RemoteException e2) {
			System.err.println("RemoteException: " + e2);
		}
		catch (NotBoundException e3) {
			System.err.println("NotBoundException: " + e3);			
		} 
    }
}

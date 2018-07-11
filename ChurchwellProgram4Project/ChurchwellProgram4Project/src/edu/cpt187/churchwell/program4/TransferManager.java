package edu.cpt187.churchwell.program4;

class TransferManager {

	// VARIABLES
	int numTransfers = 0;										// NUMBER OF ATTEMPTED TRANSFERS
	int successCount = 0;										// NUMBER OF SUCCESSFUL TRANSFERS
	int numAttemptedTransfers = 0;								// RETURNS NUMBER OF ATTEMPTED TRANSFERS
	int numSuccessfulTransfers = 0;								// RETURNS NUMBER OF SUCCESSFUL TRANSFERS
	
	// GET NUMBER OF ATTEMPTED TRANSFERS
	public int getNumAttemptedTransfers()
	{
		return numAttemptedTransfers;
	}
	
	// GET NUMBER OF SUCCESFUL TRANSFERS
	public int getNumSuccessfulTransfers()
	{
		return numSuccessfulTransfers;
	}
	
	// TRANSFERS $$ FROM CHECKING TO SAVINGS
	public boolean transferCheckingToSavings(double transactionAmount, CheckingAccount myChecking, SavingsAccount mySavings)	
	{
		boolean isTransferSuccessful= false; 
		numAttemptedTransfers++;
		
		if (transactionAmount <= myChecking.getAccountBalance()) 
		{  
			myChecking.withdrawFromBalance(transactionAmount); 
			mySavings.addToBalance(transactionAmount); 
			numSuccessfulTransfers++;
			isTransferSuccessful= true; 
		} 
		
		return isTransferSuccessful; 
		
	}
	
	// TRANSFERS $$ FROM SAVINGS TO CHECKING
	public boolean transferSavingsToChecking(double transactionAmount, SavingsAccount mySavings, CheckingAccount myChecking)  					// TRANSFERS $$ FROM CHECK TO SAVINGS	
	{
		boolean isTransferSuccessful= false; 
		numAttemptedTransfers++;
		
		if (transactionAmount <= mySavings.getAccountBalance()) 
		{  
			mySavings.withdrawFromBalance(transactionAmount); 
			myChecking.addToBalance(transactionAmount); 
			numSuccessfulTransfers++;
			isTransferSuccessful= true; 
		} 
		
		return isTransferSuccessful;
	}
}

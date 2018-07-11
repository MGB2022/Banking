package edu.cpt187.churchwell.program4;

import java.util.Random;

class CheckingAccount 
{

	//  Variables
	private String accountNumber = "";
	private double accountBalance = 0.0;
	private double interestRate = 0.0;
	private double minimumBalance = 0.0;
	
	//  Instantiate 'Random' Object
	Random ranNumGenerator = new Random();

	//  Constructor Method
	public CheckingAccount(double newInterestRate, double newMinimumBalance) 
	{
		interestRate = newInterestRate;
		minimumBalance = newMinimumBalance;
	}

	//  Get Account Number
	public String getAccountNumber() 
	{
		return accountNumber;
	}

	//  Get Account Balance
	public double getAccountBalance() 
	{
		return accountBalance;
	}

	//  Get Interest Rate
	public double getInterestRate() 
	{
		return interestRate;
	}
	
	// Get Required Deposit
	public double getRequiredDeposit(double withdrawalAmount)
	{
		if (accountBalance < minimumBalance)
		{
			return (minimumBalance - accountBalance);

		}
		else if (accountBalance < withdrawalAmount)
		{
			return (withdrawalAmount - accountBalance);
		}	
		else
		{ 
			return 0;
		}
	}
	
	// Get Random Auto Generated Account Number
	public String generateAccountNumber(int ACCT_LIMIT, String CHECKING_PREFIX)
	{

		int savingsRndNum = ranNumGenerator.nextInt(ACCT_LIMIT);
		
		return (CHECKING_PREFIX + savingsRndNum);
		
	}
	
	// Get Random Auto Generated Account Balance
	public double generateBalance(int NEW_LIMIT)
	{
		// GENERATES THE CENTS
		double rangeMin = 0;
		double rangeMax = 1;
		Random r = new Random();
		double randomCents = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		
		// GENERATES THE DOLLAR and ADDS CENTS
		double randomBalance = ranNumGenerator.nextInt(NEW_LIMIT) + randomCents;
		
		return randomBalance;
	}
	
	//  Set Account Number (Assign New Account Number)
	public void setAccountNumber(String newAccountNumber) 
	{
		accountNumber = newAccountNumber;
	}

	//  Set Interest Rate (Assign new Interest Rate)
	public void setInterestRate(double newInterestRate) 
	{
		interestRate = newInterestRate;
	}

	// Set Add to Account Balance
	public void addToBalance(double depositBalanceAmount) 
	{
		accountBalance += depositBalanceAmount;
	}

	// Set Withdraw from Account Balance
	public void withdrawFromBalance(double withdrawBalanceAmount) 
	{
		accountBalance -= withdrawBalanceAmount;
	}
	
	// Set Applied Interest
	public double applyInterest()
	{
		return accountBalance * interestRate;
	}
}
// End of CheckingAccount.java Class
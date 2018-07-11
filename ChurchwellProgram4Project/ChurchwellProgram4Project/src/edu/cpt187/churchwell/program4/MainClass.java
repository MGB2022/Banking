package edu.cpt187.churchwell.program4;

import java.util.Scanner;

public class MainClass 
{

	public static void main(String[] args) {

		// CONSTANTS
		final String CHECKING_PREFIX = "CHK";										// CHECKING ACCOUNT NUMBER PREFIX
		final String SAVINGS_PREFIX = "SAV";										// SAVINGS ACCOUNT NUMBER PREFIX
		final int ACCT_NUM_LIMIT = 899;											// ACCOUNT NUMBER GENERATOR LIMIT
		final int RND_BAL_LIMIT = 1001;											// ACCOUNT BALANCE GENERATOR LIMIT
		final int WITHDRAW_LIMIT = 6;												// MAX NUMBER OF SAVINGS ACCOUNT WITHDRAWALS
		final double MIN_BAL = 25.0;												// MININUM ALLOWED CHECKING BALANCE
		final double CHECKING_RATE = .004;										// CHECKING ACCOUNT RATE
		final double SAVINGS_RATE = .012;											// SAVINGS ACCOUNT RATE
		final char SELECTOR_A = 'A';												// INPUT SELECTOR OPTION
		final char SELECTOR_B = 'B';												// INPUT SELECTOR OPTION
		final char SELECTOR_C = 'C';												// INPUT SELECTOR OPTION
		final char SELECTOR_X = 'X';												// INPUT SELECTOR OPTION

		// 	VARIABLES
		String firstName = "";													// CUSTOMER FIRST NAME
		String lastName = "";													// CUSTOMER LAST NAME
		String fullName = "";													// CUSTOMER FULL NAME
		String currentCheckingAcctNum = "";										// STORES CURRENT CHK ACCT NUM
		String currentSavingsAcctNum = "";										// STORES CURRENT SAV ACCT NUM
		int withdrawalCounter = 0;												// STORES NUMBER OF SAVINGS ACCOUNT WITHDRAWALS
		int numSuccessfulTransfers = 0;											// STORES INCREMENTED SUCCESSFUL TRANSFERS
		int numAttemptedTransfers = 0;											// STORES INCREMENTED ATTEMPTED TRANSFERS
		char customerTypeSelection = 0;											// NEW OR EXISTING
		char transactionTypeSelection = 0;										// DEPOSIT, WITHDRAW, TRANSFER
		char accountTypeSelection = 0;											// CHECKING OR SAVINGS
		double currentRequiredDeposit = 0.0;										// STORES REQ DEPOSIT TO VALIDATE WITHDRAWS
		double transactionAmount = 0.0;											// STORES ALL TRANSACTION AMOUNTS
		double currentCheckingBalance = 0.0;										// STORES CURRENT CHK BALANCE
		double currentSavingsBalance = 0.0;										// STORES CURRENT SVG BALANCE
		double checkingInterestEarned = 0.0;										// STORES CHK INTEREST EARNED
		double savingsInterestEarned = 0.0;										// STORES SVG INTEREST EARNED
		double futureCheckingBalance = 0.0;										// BALANCE + INTEREST EARNED
		double futureSavingsBalance = 0.0;										// BALANCE + INTEREST EARNED
		boolean isTransferSuccessful = false;										// USED FOR DECISION STATEMENT IN TRANSFER

		Scanner input = new Scanner(System.in);										// SCANNER OBJECT
		CheckingAccount myChecking = new CheckingAccount(CHECKING_RATE, MIN_BAL);		// CHECKING ACCOUNT OBJECT
		SavingsAccount mySavings = new SavingsAccount(SAVINGS_RATE);					// SAVINGS ACCOUNT OBJECT
		TransferManager manager = new TransferManager();								// TRANSFER MANAGER OBJECT

		welcomeBanner();																// PRINT WELCOME BANNER

		System.out.println("\nPlease enter your first name:\n");
		System.out.print("-> ");
		firstName = input.nextLine();													// ACCEPT FIRST NAME
		System.out.println("\nPlease enter your last name:\n");
		System.out.print("-> ");
		lastName = input.nextLine();													// ACCEPT LAST NAME
		fullName = firstName + " " + lastName;										// CONCAT FULL NAME
		
		customerTypeSelection = customerTypeMenu(input, firstName);					// PRINT MENU, ACCEPT, VALIDATE INPUT
		customerTypeSelection = customerTypeValidator(input, customerTypeSelection, firstName);			// VALIDATES ENTRY
		
		if (customerTypeSelection == SELECTOR_A) 														// IF NEW CUSTOMER
		{
			currentCheckingBalance = promptInitialChkDeposit(input, firstName);						// CUSTOMER INPUTS CHECKING BALANCE
			myChecking.addToBalance(currentCheckingBalance);											// ADDS RND BAL
			currentSavingsBalance = promptInitialSavDeposit(input);									// CUSTOMER INPUTS SAVINGS BALANCE
			mySavings.addToBalance(currentSavingsBalance);											// ADDS RND BAL
			currentCheckingAcctNum = myChecking.generateAccountNumber(ACCT_NUM_LIMIT, CHECKING_PREFIX);	// GENERATE CHECKING ACCT NUM
			myChecking.setAccountNumber(currentCheckingAcctNum);										// STORES ACCT NUM
			currentSavingsAcctNum = mySavings.generateAccountNumber(ACCT_NUM_LIMIT, SAVINGS_PREFIX);	// GENERATE SAVINGS ACCT NUM
			mySavings.setAccountNumber(currentSavingsAcctNum);										// STORES ACCT NUM
			
			startingAccountPrompt(currentCheckingAcctNum, currentCheckingBalance, 
					currentSavingsAcctNum, currentSavingsBalance); 										// PRINTS PRELIMINARY ACCOUNT INFO

		}
		else 																							// IF EXISTING CUSTOMER
		{
			welcomeBack(firstName);																		// WELCOME BACK CUSTOMER
			currentCheckingBalance = myChecking.generateBalance(RND_BAL_LIMIT);							// GENERATE RANDOM CHECKING BALANCE
			myChecking.addToBalance(currentCheckingBalance);												// ADDS RND BAL
			currentSavingsBalance = mySavings.generateBalance(RND_BAL_LIMIT);								// GENERATE RANDOM SAVINGS BALANCE
			mySavings.addToBalance(currentSavingsBalance);												// ADDS RND BAL
			currentCheckingAcctNum = myChecking.generateAccountNumber(ACCT_NUM_LIMIT, CHECKING_PREFIX);		// GENERATE CHECKING ACCT NUM
			myChecking.setAccountNumber(currentCheckingAcctNum);											// STORES ACCT NUM
			currentSavingsAcctNum = mySavings.generateAccountNumber(ACCT_NUM_LIMIT, SAVINGS_PREFIX);		// GENREATE SAVINGS ACCT NUM
			mySavings.setAccountNumber(currentSavingsAcctNum);											// STORES ACCT NUM
			startingAccountPrompt(currentCheckingAcctNum, currentCheckingBalance, 
					currentSavingsAcctNum, currentSavingsBalance); 										// PRINTS PRELIMINARY ACCOUNT INFO
		}

		// MAIN MENU
		while (transactionTypeSelection != SELECTOR_X) 													// WHILE DEPOSIT, WITHDRAW, TRANSFER
		{
			transactionTypeSelection = transactionTypeMenu(input, firstName); 								// DEPOSIT, WITHDRAW, TRANSFER, QUIT SELECTION
			transactionTypeSelection = transactionTypeValidator(input, transactionTypeSelection, firstName);
			if (transactionTypeSelection != SELECTOR_X) 
			{
				accountTypeSelection = accountTypeMenu(input, firstName, transactionTypeSelection); 		// CHECKING, SAVNGS, QUIT SELECTION
				accountTypeSelection = accountTypeValidator(input, accountTypeSelection, firstName);
				transactionAmount = transactionAmountInput(input, transactionTypeSelection); 				// ACCEPTS AND VALIDATES TRANSACTION AMOUNT
				
				if (transactionTypeSelection == SELECTOR_A) 									// IF DEPOSIT 
				{
					if (accountTypeSelection == SELECTOR_A) 									// IF CHECKING ACCOUNT
					{
						myChecking.addToBalance(transactionAmount); 							// ADD TRANSACTION AMOUNT TO CHECKING BALANCE
					}
					else																		// IF SAVINGS ACCOUNT
					{
						mySavings.addToBalance(transactionAmount); 							// ADD TRANSACTION AMOUNT TO SAVINGS BALANCE
					}
				}
				else if (transactionTypeSelection == SELECTOR_B) 								// IF WITHDRAW
				{
					if (accountTypeSelection == SELECTOR_A)									// IF CHECKING ACCOUNT
					{
						
						currentRequiredDeposit = myChecking.getRequiredDeposit(transactionAmount); 					// TEST ACCOUNT BALANCE < TRANSACTION AMT & < REQ BALANCE
						if (currentRequiredDeposit == 0)
						{
							myChecking.withdrawFromBalance(transactionAmount);					// SUBTRACT TRAN AMOUNT FROM CHECKING BALANCE

						}
						else
						{
							System.out.println("\nInsufficient Funds For Withdrawal!");
							
						}
					}
					else																		// IF SAVINGS ACCOUNT
					{
						if (withdrawalCounter < WITHDRAW_LIMIT)								// IF WITHDRAW COUNTER < LIMIT
						{
							
							currentRequiredDeposit = mySavings.getRequiredDeposit(transactionAmount);					// TEST ACCOUNT BALANCE < TRANSACTION AMT & < REQ BALANCE
							if (currentRequiredDeposit == 0)
							{
								mySavings.withdrawFromBalance(transactionAmount);					// SUBTRACT TRAN AMOUNT FROM CHECKING BALANCE
								withdrawalCounter++;												// INCREMENT COUNTER (+1)
							}
							else
							{
								System.out.println("\nInsufficient Funds For Withdrawal!");
							}
						}
						else																	// IF WITHDRAW COUNTER == LIMIT
						{
							withdrawalLimitAlert(WITHDRAW_LIMIT);								// ALERT USER HAS REACHED MAXIMUM SAVINGS WITHDRAWALS
						}
					}
				}
				else if (transactionTypeSelection == SELECTOR_C) 										// IF TRANSFER
				{
					if (accountTypeSelection == SELECTOR_A)											// IF CHECKING ACCOUNT
					{
						currentRequiredDeposit = myChecking.getRequiredDeposit(transactionAmount);								// TEST ACCOUNT BALANCE < TRANSACTION AMT & < REQ BALANCE
						if (currentRequiredDeposit == 0)
						{
							isTransferSuccessful = manager.transferCheckingToSavings(transactionAmount, myChecking, mySavings);	// TRANSFER TRAN AMOUNT FROM CHECKING TO SAVINGS
							
							if (isTransferSuccessful == true)
							{
								numSuccessfulTransfers = manager.getNumSuccessfulTransfers();
								System.out.println("\nSuccessful Transfer!\n");
								System.out.printf("Number of Successful Transfers: (%s)\n", numSuccessfulTransfers);
							}
						}
						else
						{
							numAttemptedTransfers = manager.getNumAttemptedTransfers();
							System.out.println("\nInsufficient Funds For Transfer!\n");
							System.out.printf("Number of Failed Transfers: (%s)\n", numAttemptedTransfers);
						}
					}
					else																				// IF SAVINGS ACCOUNT
					{
						currentRequiredDeposit = mySavings.getRequiredDeposit(transactionAmount);
						if (currentRequiredDeposit == 0)
						{
							isTransferSuccessful = manager.transferSavingsToChecking(transactionAmount, mySavings, myChecking);	// TRANSFER TRAN AMOUNT FROM SAVINGS TO CHECKING
							
							if (isTransferSuccessful == true)
							{
								numSuccessfulTransfers = manager.getNumSuccessfulTransfers();
								System.out.println("\nSuccessful Transfer!\n");
								System.out.printf("Number of Successful Transfers: (%s)\n", numSuccessfulTransfers);
							}
						}
						else
						{
							numAttemptedTransfers = manager.getNumAttemptedTransfers();
							System.out.println("\nInsufficient Funds For Transfer!\n");
							System.out.printf("Number of Failed Transfers: (%s)\n", numAttemptedTransfers);

						}
					}
				}
				else																			// IF USER QUITS
				{
					heresYourReport(firstName);												// THANKS FOR BANKING W MAIN ST BANK MESSAGE
				}
			}
		}

		currentCheckingBalance = myChecking.getAccountBalance();								// GET CHECKING ACCOUNT BALANCE
		currentCheckingAcctNum = myChecking.getAccountNumber();								// GET CHECKING ACCOUNT NUM
		checkingInterestEarned = myChecking.applyInterest();									// GET CHECKING ACCOUNT APPLIED INTEREST
		futureCheckingBalance = currentCheckingBalance + checkingInterestEarned;				// CALCULATE FUTURE BALANCE (Bal + Interest)

		currentSavingsBalance = mySavings.getAccountBalance();									// GET SAVINGS ACCOUNT BALANCE
		currentSavingsAcctNum = mySavings.getAccountNumber();									// GET SAVINGS ACCOUNT NUM
		savingsInterestEarned = mySavings.applyInterest();									// GET SAVINGS ACCOUNT APPLIED INTEREST
		futureSavingsBalance = currentSavingsBalance + savingsInterestEarned;					// CALCULATE FUTURE BALANCE (Bal + Interest)

		printAccountSummary(fullName, currentCheckingAcctNum, currentCheckingBalance, 			// PRINT ACCOUNT SUMMARY
			CHECKING_RATE, checkingInterestEarned, futureCheckingBalance, 
			currentSavingsAcctNum, currentSavingsBalance, SAVINGS_RATE, 
			savingsInterestEarned, futureSavingsBalance);

		printFarewellMessage();																// PRINT FAREWELL MESSAGE
	}


	/*========================================================*/
	/*------------  W E L C O M E   B A N N E R  -------------*/
	/*========================================================*/
	public static void welcomeBanner() 
	{

		// Welcome Banner Variables
		String bannerBorder = "";
		String companySpacer = "";
		String companyMessage = "";
		String welcomeBorder = "";
		String welcomeSpacer = "";
		String welcomeSubHeader = "";
		String welcomeBanner = "";
		String companyName = "    MAIN STREET BANK    ";
		String welcomeMessage = "Welcome to the Main St. Bank App!   //////////////////////// \n\n///////////////   Manage your account, deposit & withdraw $$";

		// Banner Double Dash Border Length
		for (int e = 0; e < 60; e++) 
		{
			char doubleDashSpacer = '=';
			bannerBorder += doubleDashSpacer;
		}

		// Company Name Spacer Length
		for (int e = 0; e < 18; e++) 
		{
			char dashSpacer = '-';
			companySpacer += dashSpacer;
		}

		// Company Banner Concatenation
		companyMessage = companySpacer + companyName + companySpacer;

		// Welcome Single Dash Boarder Length
		for (int e = 0; e < 60; e++) 
		{
			char dashSpacer = '-';
			welcomeBorder += dashSpacer;
		}

		// Welcome Message Spacer Length
		for (int e = 0; e < 30; e++) 
		{
			char blankSpacer = ' ';
			welcomeSpacer += blankSpacer;
		}

		// Welcome SubHeader Concatenation
		welcomeSubHeader = welcomeMessage + welcomeSpacer;

		// Welcome Banner Concatenation
		welcomeBanner = bannerBorder + "\n" + companyMessage + "\n" + bannerBorder + "\n" + welcomeSubHeader + "\n" + welcomeBorder;

		// Return Welcome Banner
		System.out.println(welcomeBanner);
	}

	
	
	
	
	/*================================================================*/
	/*-------------  P R I N T   D A S H   B O R D E R  --------------*/
	/*================================================================*/
	public static void printDashBorder()
	{
		String welcomeBorder = "";
		
		// Welcome Single Dash Boarder Length
		for (int e = 0; e < 60; e++) 
		{
			char dashSpacer = '-';
			welcomeBorder += dashSpacer;
		}
		System.out.println(welcomeBorder);
	}


	/*================================================================*/
	/*------------  C U S T O M E R   T Y P E   M E N U  -------------*/
	/*================================================================*/
	public static char customerTypeMenu(Scanner input, String firstName)
	{

		//  CONSTANTS
		final String MAKE_SELECTION = ", please log into an existing account or create account: \n\n";
		final String SELECTION_A = "[A] Create A New Account \n\n";
		final String SELECTION_B = "[B] Log Into Existing Account \n\n";
		
		String customerTypeMenu = "";
		String dashBorder = "";
		char dashChar = 0;
		char selection = 0;
		
		// Welcome Single Dash Boarder Length
		for (int e = 0; e < 60; e++) 
		{
			dashChar = '-';
			dashBorder += dashChar;
		}

		//  Concatenate Customer Type Menu
		customerTypeMenu = "\n" + dashBorder + "\n\n" + firstName + MAKE_SELECTION + SELECTION_A + SELECTION_B;

		//  Return Customer Type Menu to Main
		System.out.print(customerTypeMenu);

		System.out.print("-> ");
		selection = input.next().charAt(0);
		selection = Character.toUpperCase(selection);

		return selection;
	}
	
	public static char customerTypeValidator(Scanner input, char customerTypeSelection, String firstName)
	{
		char SELECTOR_A = 'A';
		char SELECTOR_B = 'B';
		
		while (customerTypeSelection != SELECTOR_A && customerTypeSelection != SELECTOR_B) 
		{
			System.out.println("\n\nInvalid Entry - Your must enter either 'A' or 'B' \n");
			System.out.print("-> ");
			customerTypeSelection = input.next().charAt(0);
			customerTypeSelection = Character.toUpperCase(customerTypeSelection);
		}
		return customerTypeSelection;
	}


	/*=====================================================================*/
	/*------------  T R A N S A C T I O N  T Y P E   M E N U  -------------*/
	/*=====================================================================*/
	public static char transactionTypeMenu(Scanner input, String firstName)
	{
		//  Local Constants
		final String MAKE_SELECTION = "Which transaction would you like to make? \n\n";
		final String SELECTION_A = "[A]  Deposit \n\n";
		final String SELECTION_B = "[B]  Withdrawal \n\n";
		final String SELECTION_C = "[C]  Transfer \n\n";
		final String SELECTION_X = "[X]  Exit & Review Account \n";

		String transactionTypeMenu = "";
		String dashBorder = "";
		char dashChar = '-';
		char selection = 0;

		// Welcome Single Dash Boarder Length
		for (int e = 0; e < 60; e++) 
		{
			dashChar = '-';
			dashBorder += dashChar;
		}

		//  Concatenate Customer Type Menu
		transactionTypeMenu = "\n" + dashBorder + "\n\n" + MAKE_SELECTION + SELECTION_A + SELECTION_B + SELECTION_C + SELECTION_X;

		//  Return Customer Type Menu to Main
		System.out.println(transactionTypeMenu);

		System.out.print("-> ");
		selection = input.next().charAt(0);
		selection = Character.toUpperCase(selection);

		return selection;
	}

	public static char transactionTypeValidator(Scanner input, char transactionTypeSelection, String firstName)
	{
		char SELECTOR_A = 'A';
		char SELECTOR_B = 'B';
		char SELECTOR_C = 'C';
		char SELECTOR_X = 'X';
		
		while (transactionTypeSelection != SELECTOR_A && transactionTypeSelection != SELECTOR_B && transactionTypeSelection != SELECTOR_C && transactionTypeSelection != SELECTOR_X) 
		{
			System.out.println("\n\nInvalid Entry - Your must enter either 'A', 'B', 'C', or 'X'\n");
			System.out.print("-> ");
			transactionTypeSelection = input.next().charAt(0);
			transactionTypeSelection = Character.toUpperCase(transactionTypeSelection);
		}
		return transactionTypeSelection;
	}

	/*==============================================================*/
	/*------------  A C C O U N T   T Y P E   M E N U  -------------*/
	/*==============================================================*/
	public static char accountTypeMenu(Scanner input, String firstName, char transactionTypeSelection)
	{
		//  Local Constants
		final String MAKE_DEPOSIT = "\nFrom which account would you like to make a deposit? \n\n";
		final String MAKE_WITHDRAWAL = "\n\nFrom which account would you like to make a withdrawal? \n\n";
		final String MAKE_TRANSFER = "\n\nFrom which account would you like to make a transfer? \n\n";
		final String SELECTION_A = "[A]  Checking \n\n";
		final String SELECTION_B = "[B]  Savings \n";
		final char SELECTOR_A = 'A';
		final char SELECTOR_B = 'B';
		
		String accountTypeMenu = "";
		String dashBorder = "";
		char dashChar = '-';
		char selection = 0;
		
		// Welcome Single Dash Boarder Length
		for (int e = 0; e < 60; e++) 
		{
			dashChar = '-';
			dashBorder += dashChar;
		}

		if (transactionTypeSelection == SELECTOR_A)
		{
			accountTypeMenu = "\n" + dashBorder + "\n" + MAKE_DEPOSIT + SELECTION_A + SELECTION_B;
		} 
		else if (transactionTypeSelection == SELECTOR_B)
		{
			accountTypeMenu = "\n" + dashBorder + MAKE_WITHDRAWAL + SELECTION_A + SELECTION_B;
		}
		else 
		{
			accountTypeMenu = "\n" + dashBorder + MAKE_TRANSFER + SELECTION_A + SELECTION_B;
		}

		//  Return Customer Type Menu to Main
		System.out.println(accountTypeMenu);
		System.out.print("-> ");
		selection = input.next().charAt(0);
		selection = Character.toUpperCase(selection);

		return selection;
	}
	
	public static char accountTypeValidator(Scanner input, char accountTypeSelection, String firstName)
	{
		char SELECTOR_A = 'A';
		char SELECTOR_B = 'B';
		
		while (accountTypeSelection != SELECTOR_A && accountTypeSelection != SELECTOR_B) 
		{
			System.out.println("\n\nInvalid Entry - Your must enter either 'A' or 'B'\n");
			System.out.print("-> ");
			accountTypeSelection = input.next().charAt(0);
			accountTypeSelection = Character.toUpperCase(accountTypeSelection);
		}
		return accountTypeSelection;
	}


	/*============================================================================*/
	/*------------  T R A N S A C T I O N   A M O U N T   I N P U T  -------------*/
	/*============================================================================*/
	public static double transactionAmountInput(Scanner input, char transactionTypeSelection)
	{

		//	LOCAL CONSTANTS
		char SELECTOR_A = 'A';
		char SELECTOR_B = 'B';

		//	LOCAL VARIABLES
		double transactionAmount = 0.0;

		if (transactionTypeSelection == SELECTOR_A)
		{
			printDashBorder();
			System.out.println("\nPlease enter the amount of your deposit: \n");
		}
		else if (transactionTypeSelection == SELECTOR_B)
		{
			printDashBorder();
			System.out.println("\nPlease enter the amount of your withdrawal: \n");
		}
		else
		{
			printDashBorder();
			System.out.println("\nPlease enter the amount of your transfer: \n");
		}

		System.out.print("-> $ ");
		transactionAmount = input.nextDouble();

		while (transactionAmount < 0)
		{
			System.out.print("\n\nMain St. Bank doesn't allow users to deposit, withdraw, or transfer a negative amount!\n\n");
			System.out.print("Please enter a positive amount!\n\n");
			System.out.print("-> $ ");
			transactionAmount = input.nextDouble();
		}

		return transactionAmount;
	}
	
	
	/*==================================================================================*/
	/*------------  P R I N T   S T A R T I N G   A C C O U N T   I N F O  -------------*/
	/*==================================================================================*/
	public static void startingAccountPrompt(String currentCheckingAccountNum, double currentCheckingBalance, String currentSavingsAccountNum, double currentSavingsBalance)
	{
		System.out.println("\n------------------------ Your Accounts ---------------------\n");
		System.out.printf("Checking Account Number: %s // Balance: $%.2f\n\n", currentCheckingAccountNum, currentCheckingBalance);
		System.out.printf("Savings Account Number: %s // Balance: $%.2f\n", currentSavingsAccountNum, currentSavingsBalance);

	}


	/*===================================================================*/
	/*------------  W E L C O M E   B A C K   M E S S A G E -------------*/
	/*===================================================================*/
	public static void welcomeBack(String firstName)
	{
		System.out.println("");
		printDashBorder();
		System.out.println("\nWelcome back, " + firstName + "!");
	}

	/*===========================================================================*/
	/*------------  I N I T I A L   C H E C K I N G   D E P O S I T -------------*/
	/*===========================================================================*/
	public static double promptInitialChkDeposit(Scanner input, String firstName)
	{

		double initialDeposit = 0.0;
		
		System.out.println("");
		printDashBorder();
		System.out.println("\nWelcome " + firstName + "! We're glad to have you as a new customer.\n");
		printDashBorder();
		System.out.printf("\nPlease enter the initial deposit for your checking account:");
		System.out.print("\n\n -> $ ");

		initialDeposit = input.nextDouble();

		while (initialDeposit < 0)
		{
			System.out.print("\n\nMain St. Bank doesn't allow users to deposit a negative amount!\n\n");
			System.out.print("Please enter a positive amount for your deposit!\n\n");
			System.out.print("-> $ ");
			initialDeposit = input.nextDouble();
		}

		return initialDeposit;
	}
	
	/*=========================================================================*/
	/*------------  I N I T I A L   S A V I N G S   D E P O S I T -------------*/
	/*=========================================================================*/
	public static double promptInitialSavDeposit(Scanner input)
	{

		double initialDeposit = 0.0;
		
		printDashBorder();
		System.out.printf("\nPlease enter the initial deposit for your savings account:");
		System.out.print("\n\n -> $ ");

		initialDeposit = input.nextDouble();

		while (initialDeposit < 0)
		{
			System.out.print("\n\nMain St. Bank doesn't allow users to deposit a negative amount!\n\n");
			System.out.print("Please enter a positive amount for your deposit!\n\n");
			System.out.print("-> $ ");
			initialDeposit = input.nextDouble();
		}

		return initialDeposit;
	}
	
	


	/*================================================================================*/
	/*------------  T H A N K S   F O R   B A N K I N G   M E S S A G E  -------------*/
	/*================================================================================*/
	public static void heresYourReport(String firstName)
	{
		System.out.println(firstName + ", here are your account summaries: \n");
	}


	/*========================================================================*/
	/*------------  W I T H D R A W A L   L I M I T   A L E R T  -------------*/
	/*========================================================================*/
	public static void withdrawalLimitAlert(int WITHDRAW_LIMIT)
	{
		System.out.printf("\nYou already reached the maximum number of savings \n\naccount withdrawals ( %s )\n", WITHDRAW_LIMIT);
	}


	/*========================================================================*/
	/*------------  A C C O U N T   S U M M A R Y   R E P O R T  -------------*/
	/*========================================================================*/
	public static void printAccountSummary(String fullName, String checkingAccountNumber, double checkingAccountBalance, double CHECKING_RATE, 
			double checkingInterestEarned, double checkingFutureBalance, String savingsAccountNumber, double savingsAccountBalance, double SAVINGS_RATE, 
			double savingsInterestEarned, double savingsFutureBalance) {

		String accountReportBorder = "";
		char doubleDash = '=';
		String customerName = "Customer Name:";
		String checkingAccountNumberDesc = "Checking Account #:";
		String checkingAccountBalanceDesc = "Account Balance:";
		String checkingInterestRateDesc = "Interest Rate:";
		String checkingInterestEarnedDesc = "Interest Earned:";
		String checkingFutureBalanceDesc = "Next Month's Balance:";
		String savingsAccountNumberDesc = "Savings Account #:";
		String savingsAccountBalanceDesc = "Account Balance:";
		String savingsInterestRateDesc = "Interest Rate:";
		String savingsInterestEarnedDesc = "Interest Earned:";
		String savingsFutureBalanceDesc = "Next Month's Balance:";
		String spacer = " ";
		char percentTag = '%';

		for (int e = 0; e < 60; e++) 
		{
			accountReportBorder += doubleDash;
		}

		System.out.println("\n" + accountReportBorder);
		System.out.println("               Main St. Bank Account Summary            ");
		System.out.println(accountReportBorder);
		System.out.printf("%n%9s %-26s %s %n%n", spacer, customerName, fullName);
		System.out.printf("%4s %-31s %s %n", spacer, checkingAccountNumberDesc, checkingAccountNumber);
		System.out.printf("%7s %-28s $ %.2f %n", spacer, checkingAccountBalanceDesc, checkingAccountBalance);
		System.out.printf("%9s %-26s %s%s %n", spacer, checkingInterestRateDesc, CHECKING_RATE * 100, percentTag);
		System.out.printf("%7s %-28s $ %.2f %n", spacer, checkingInterestEarnedDesc, checkingInterestEarned);
		System.out.printf("%2s %-33s $ %.2f %n%n", spacer, checkingFutureBalanceDesc, checkingFutureBalance);
		System.out.printf("%5s %-30s %s %n", spacer, savingsAccountNumberDesc, savingsAccountNumber);
		System.out.printf("%7s %-28s $ %.2f %n", spacer, savingsAccountBalanceDesc, savingsAccountBalance);
		System.out.printf("%9s %-26s %s%s %n", spacer, savingsInterestRateDesc, SAVINGS_RATE * 100, percentTag);
		System.out.printf("%7s %-28s $ %.2f %n", spacer, savingsInterestEarnedDesc, savingsInterestEarned);
		System.out.printf("%2s %-33s $ %.2f %n%n", spacer, savingsFutureBalanceDesc, savingsFutureBalance);
		System.out.println(accountReportBorder);
	}
	
	
	/*============================================================*/
	/*------------  F A R E W E L L   M E S S A G E  -------------*/
	/*============================================================*/
	public static void printFarewellMessage() 
	{
		final String TY_MESSAGE = "Thanks for Using the Main St. Banking App!";
		String accountReportBorder = "";
		char doubleDash = '=';

		for (int e = 0; e < 60; e++) 
		{
			accountReportBorder += doubleDash;
		}

		System.out.printf("%51s %n", TY_MESSAGE);
		System.out.println(accountReportBorder);
	}
}
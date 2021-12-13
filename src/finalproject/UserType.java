package finalproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

// ABSTRACT CLASS USERTYPE - EXTENDS FOR HOLDER AND MINER
public abstract class UserType implements Serializable {
	
	protected Blockchain blockchain;
	protected String hash;
	protected String privateKey;
	protected double balance = 100.0;
	protected Date created;
	protected ArrayList<Transaction> transactions = new ArrayList<>();
	
	
	
	// Allows for a UserType to send to another UserType
	//	This means a Holder can send to another Holder or to a Miner; or vice versa.
	//	Checks if the privateKey entered is equal to the senders private key in order to validate the transaction (kind of like a bank pin)
	//	It the checks if you have enough balance in order to complete the transaction
	//	If all is good, it will then update the balances of the UserTypes and create a transaction that will go to the pending transactions on the blockchain
	public void sendTransaction(String privateKey, UserType getter, double amount) throws Exception {
		if(this.privateKey.equals(privateKey)) {
			
			if(this.balance - amount >= 0) {
				updateBalance(-amount);
				getter.updateBalance(amount);
				
				Transaction newTransaction = new Transaction(this, getter, amount);
				System.out.println(newTransaction);
				
				addTransaction(newTransaction);
				getter.addTransaction(newTransaction);
				
				blockchain.addPendingTransaction(newTransaction);
			} else {
				System.out.println("Transaction Failed: Not Enough Balance to Complete Transaction");
				throw new Exception();
			}
				
		} else 
			System.out.println("Transaction Failed: Invalid Private Key");
		
	}
	
	
	// Updates the balance of the UserType with an amount
	public double updateBalance(double amount) {
		balance += amount;
		return balance;
	}
	
	// Adds a transaction to the user, this is so you can publically see each of the users transactions
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	
	
	// Accessor Methods
	public String getHash() {
		return hash;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public ArrayList<Transaction> transactions() {
		return transactions;
	}
	
}


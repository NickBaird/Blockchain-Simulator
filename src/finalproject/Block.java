package finalproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Block implements Serializable {
	
	private String blockHash;
	private String previousBlockHash;
	private int index;
	private Date timestamp;
	private ArrayList<Transaction> transactions;
	
	// Creates a random-hashed block to add to the blockchain
	public Block(int index, ArrayList<Transaction> transactions, String previousBlockHash) {
		this.blockHash = SHA256.generateHash(index + transactions.toString() + previousBlockHash);
		this.index = index;
		this.transactions = transactions;
		this.previousBlockHash = previousBlockHash;
		this.timestamp = new Date();
	}
	
	
	// Creates a given-hashed block, that is obtained through mining, to add to the blockchain
	public Block(String blockHash, int index, ArrayList<Transaction> transactions, String previousBlockHash) {
		this.blockHash = blockHash;
		this.index = index;
		this.transactions = transactions;
		this.previousBlockHash = previousBlockHash;
		this.timestamp = new Date();
	}
	
	// Adds a transaction to the block in order to publically see all of the transactions on a block
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	
	
	// Accessor Methods
	public String getHash() {
		return blockHash;
	}
	
	public String getPreviousBlockHash() {
		return previousBlockHash;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	// toString method to nicely give a string output for the block information
	public String toString() {
		String output = "Index: " + index + "\n"
					  + "Block Hash: " + blockHash + "\n"
					  + "Previous BlockHash: " + previousBlockHash + "\n"
					  + "Time Created: " + timestamp.toString() + "\n";
		
		if(transactions.size() > 0) {
		output += "Transactions: \n";
		for(Transaction t : transactions)
			output += "\t" + t.toString() + "\n";
		}
		return output;
	}
	
	public String toStringBasic() {
		String output = "Index: " + index + "\n"
				  + "Block Hash: " + blockHash + "\n"
				  + "Previous BlockHash: " + previousBlockHash + "\n"
				  + "Time Created: " + timestamp.toString();
		return output;
	}
	
}

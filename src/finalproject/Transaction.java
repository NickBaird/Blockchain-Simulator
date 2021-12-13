package finalproject;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
	
	private String hash;
	private Date timestamp;
	private UserType sender;
	private UserType getter;
	private UserType miner;
	private double amount;
	
	// Holder Transaction
	public Transaction(UserType sender, UserType getter, double amount) {
		this.timestamp = new Date();
		this.hash = SHA256.generateHash(timestamp.toString() + sender.getHash() + getter.getHash() + amount);
		this.sender = sender;
		this.getter = getter;
		this.amount = amount;
	}
	
	
	// Miner Transaction
	public Transaction(Miner miner, double amount) {
		this.timestamp = new Date();
		this.hash = SHA256.generateHash(timestamp.toString() + miner.getHash() + amount);
		this.miner = miner;
		this.amount = amount;
	}
	
	// Accessor Methods
	public String getHash() {
		return hash;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public UserType getSender() {
		return sender;
	}
	
	public UserType getGetter() {
		return getter;
	}
	
	public UserType getMiner() {
		return miner;
	}
	
	public double getAmount() {
		return amount;
	}
	
	
	// toString method, creates a nice looking output
	public String toString() {
		try {
			String output = "Hash: " + hash + "\n\t"
						  + "Timestamp: " + timestamp + "\n\t"
						  + "Sender Hash: " + sender.getHash() + "\n\t"
						  + "Getter Hash: " + getter.getHash() + "\n\t"
						  + "Amount: " + amount + "\n";
			return output;
		} catch(NullPointerException e) {
			String output = "Hash: " + hash + "\n\t"
					  	  + "Timestamp: " + timestamp + "\n\t"
					  	  + "Miner Hash: " + miner.getHash() + "\n\t"
					  	  + "Amount: " + amount + "\n";
			return output;
		}
	}
	
}

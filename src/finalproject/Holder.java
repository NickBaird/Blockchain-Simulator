package finalproject;

import java.util.Date;

public class Holder extends UserType {
	
	// Subclass of UserType, customer Constructor method
	public Holder(Blockchain blockchain, String privateKey) {
		this.blockchain = blockchain;
		this.privateKey = privateKey;
		this.created = new Date();
		this.hash = SHA256.generateHash(blockchain.getHash() + this.privateKey + this.created.toString());
	}
	
	public String toString() {
		return "Holder: " + this.hash.substring(0, 6) + " Balance: " + getBalance();			
	}
	
}

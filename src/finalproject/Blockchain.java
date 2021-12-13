package finalproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Blockchain implements Serializable {
	
	private String name;
	private String chainHash;
	private Currency currency;
	private int length;
	private ArrayList<Block> blockChain = new ArrayList<>();
	private ArrayList<Transaction> pendingTransactions = new ArrayList<>();
	
	// Creates a blockchain that runs off a given currency
	Blockchain(String name, Currency currency) {
		this.name = name;
		this.chainHash = SHA256.generateHash(name + currency.getHash() + currency.getIdentifier() + currency.getTotalSupply() + currency.getCurrentSupply() + currency.getCurrentUSDPrice() + new Date());
		this.currency = currency;
		this.length = 0;
		blockChain.add(new Block(this.length, new ArrayList<Transaction>(), "0"));
	}
	
	// Adds a transaction to the pendingTransactions arrayList. These transactions will not be verified until a block is mined
	public void addPendingTransaction(Transaction transaction) {
		pendingTransactions.add(transaction);
	}
	
	// Adds a block to the blockchain, with a given hash
	public void addBlock(String hash) {
		blockChain.add(new Block(hash, ++length, pendingTransactions, blockChain.get(length-1).getHash()));
		pendingTransactions = new ArrayList<>();
	}
	
	// Adds a block to the blockchain, with a random hash
	public void addBlock() {
		blockChain.add(new Block(++length, pendingTransactions, blockChain.get(length-1).getHash()));
		pendingTransactions = new ArrayList<>();
	}
	
	
	// Accessor Methods
	public String getName() {
		return name;
	}
	
	public String getHash() {
		return chainHash;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public ArrayList<Block> toChain() {
		return blockChain;
	}
	
	public Block getLatestBlock() {
		return blockChain.get(length);
	}
	
	public ArrayList<Transaction> pendingTransactions() {
		return pendingTransactions;
	}
	
	public String toString() {
		return "Name: " + name + "    Length: " + length + "    Runs Off: " + currency;
	}
	
}

package finalproject;

import java.io.Serializable;

public class Currency implements Serializable {
	
	private String currencyHash;
	private String identifier;
	private long totalSupply;
	private double currentSupply;
	private double currentUSDPrice;
	
	public Currency(String identifier, long totalSupply, double currentSupply, double currentUSDPrice) {
		this.currencyHash = SHA256.generateHash(identifier + totalSupply + currentSupply + currentUSDPrice);
		this.identifier = identifier;
		this.totalSupply = totalSupply;
		this.currentSupply = currentSupply;
		this.currentUSDPrice = currentUSDPrice;
	}
	
	// Accessor Methods
	public String getHash() {
		return currencyHash;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public long getTotalSupply() {
		return totalSupply;
	}
	
	public double getCurrentSupply() {
		return currentSupply;	
	}
	
	public double getCurrentUSDPrice() {
		return currentUSDPrice;
	}
	
	
	// Mutator Methods
	public double addCurrentSupply(double amount) {
		currentSupply += amount;
		return currentSupply;
	}
	
	public double updateCurrentUSDPrice(double price) {
		currentUSDPrice = price;
		return currentUSDPrice;
	}
	
	
	public String toString() {
		return this.identifier;
	}
	
}

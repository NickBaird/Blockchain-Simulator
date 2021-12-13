package finalproject;

import java.util.Date;

public class Miner extends UserType {
	
	// Subclass of UserType, customer Constructor Method
	public Miner(Blockchain blockchain, String privateKey) {
		this.blockchain = blockchain;
		this.privateKey = privateKey;
		this.created = new Date();
		this.hash = SHA256.generateHash(blockchain.getHash() + this.privateKey + this.created.toString());
	}
	
	// Allows a Miner to start mining with a given difficulty
	// Higher difficulty - higher reward
	// Reward = 25 * (difficulty /5) - This can be changed to whatever.
	//		This mining works by taking all of the pending transactions in the blockchain, and converting that data to a string.
	//		Then generating a SHA256 hash based on that data, 
	//		in conjunction with a nonce (an incremeter for each time a hash is generated in order to create uniqueness).
	// 		If a hash is generated with the beginning of the hash equal to 'difficulty' (number entered) amount of zeros,
	//		Then add the block to the blockchain with that hash.
	//			Otherwise just increase the nonce and try again.
	
	// 		Before adding the block to the blockchain give the reward to the miner as a transaction in order to show that they miner got rewarded for mining the block.
	public void startMining(int difficulty) {
		
		if(difficulty == 0 )
			difficulty = 1;
		
		String outcome = "";
		for(int i = 0; i < difficulty; i++)
			outcome += "0";
		
		boolean mined = false;
		String hash = "";
		int nonce = 0;
		
		while(!mined) {
			String input = "";
			for(Transaction T : blockchain.pendingTransactions())
				input += T.toString();

			hash = SHA256.generateHash(input + nonce);
			if(hash.substring(0, difficulty).equals(outcome))
				mined = true;
			
			input = "";
			nonce++;
		}
		
		Transaction reward = new Transaction(this, 25.0 * ( (double) difficulty / 5.0));
		addTransaction(reward);
		blockchain.addPendingTransaction(reward);
		updateBalance(25.0 * ( (double) difficulty / 5.0 ));
		
		blockchain.addBlock(hash);
			
		System.out.println("Blocked " + blockchain.getLatestBlock().getIndex() + " Mined: " + blockchain.getLatestBlock().getHash());
		System.out.println("Nonce: " + nonce);
		System.out.println("Miner: " + this.hash);
		System.out.println("Reward: " + 25.0 * ( (double) difficulty / 5.0 ));
		System.out.println("\nBlock Information: \n" + blockchain.getLatestBlock().toString());
		
	}
	
	public String toString() {
		return "Miner: " + this.hash.substring(0, 6) + " Balance: " + getBalance();			
	}
	
}

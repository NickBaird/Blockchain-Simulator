package finalproject;

// Test class used to test the fundamentals of the blockchain
public class Test {
	
	public static void main(String[] args) throws Exception {
		/* 
		 * Creating a currency that the Blockchain will be based off:
		 * 	USDCoin
		 * 	1 Billion Total Supply
		 *  100000 Current Supply
		 *  1.00 USD per Coin (Hence why its called USD - aka Stablecoin)
		 */
		Currency cryptocurrency = new Currency("USDCoin", 1000000000L, 100000, 1.00);
		
		// Creating the blockchain that runs off the currency
		Blockchain blockchain = new Blockchain("TEST", cryptocurrency);
		
		// Create two holder accounts, that run off the blockchain with their own private keys.
		//	Private keys are used to validate transactions
		Holder h1 = new Holder(blockchain, "1234");
		Holder h2 = new Holder(blockchain, "Private");
		
		// Send two successful transactions to test that they will be stored on the blockchain
		h1.sendTransaction("1234", h2, 50);
		h2.sendTransaction("Private", h1, 5);
		
		// Send a unsuccessful transactions : Invalid Private Key
		h1.sendTransaction("123", h2, 1000);
		
		// Check the Balance for both holder accounts:
		//	should be:
		//		h1 = 55.0
		//		h2 = 145.0
		System.out.println("Balance on h1: " + h1.getBalance());
		System.out.println("Balance on h2: " + h2.getBalance() + "\n");
		
		// Then manually add block to blockchain
		blockchain.addBlock();
		
		// Then display the two blocks that should be the only ones on the chain
		//	Block 0: Genesis Block - Added when the blockchain is created.
		// 	Block 1: The one we just added, should hold two transactions
		System.out.println(blockchain.toChain().get(0).toString());
		System.out.println(blockchain.toChain().get(1).toString());
		
		// Send another transaction from holder 1
		h1.sendTransaction("1234", h2, 25);
		
		// Create a miner that runs off the blockchain
		Miner m1 = new Miner(blockchain, "1111");
		
		// Start Mining with difficulty 4
		// Difficulty is the number of zeros the beginning of the hash must have,
		// The higher the difficulty the more reward, because it means the blockchain blocks are more secure and harder to manipulate.
		m1.startMining(4);
	}
}

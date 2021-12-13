package finalproject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
	
	// Custom salt, used to 'psuedorandomize' the hashes being generated. That means no one else will be able to generate these hashes without this particular salt.
	// Technically they could; however, the chance in somewhere in the 1/multiquadrillions - EXTREMELY UNLIKELY
	private String salt = "9d5c108f946fdb9ff40b32a6cf15769e25b1af685d81a71c34f4f14dccad1639";
	
	// Generates a random SHA256 hash using MessageDigest SHA3-256 algorithm
	public static String generateHash(String information) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA3-256");
			byte[] encodedhash = digest.digest(information.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(encodedhash);
		} catch(NoSuchAlgorithmException e) {
			System.out.println("SHA-256 Error: NoSuchAlgorithm: " + e);
			return "";
		}
	}
	
	// Converts the given bytes from the SHA256 algorith and converts them into a hex value
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
}

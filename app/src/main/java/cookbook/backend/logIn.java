package cookbook.backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

public class LogIn {
  private String passwordHash;

  public String getPasswordHash() {
    return this.passwordHash;
  }

  // Create a hash value from the password, return and save it.
  public String hashPassword(String password) {
    String hashTxt = "";
    try {
      MessageDigest hash = MessageDigest.getInstance("SHA-1");
      System.out.println("Input password: " + password);

      byte[] passwordDigest = hash.digest(password.getBytes());
      BigInteger number = new BigInteger(1, passwordDigest);
      hashTxt = number.toString(16);

      while (hashTxt.length() < 32) {
        hashTxt = "0" + hashTxt;
      }
      this.passwordHash = hashTxt;
      return hashTxt;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return hashTxt;
  }
  // Method here to change the password to hash and link to the database to check if the hash value matches
}
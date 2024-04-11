package cookbook.backend.be_objects;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

public class LogIn {
  private String passwordHash;

  // Provides access to the stored hash value of the password. 
  // Can be used to compare against a hashed password string during authentication.
  public String getPasswordHash() {
    return this.passwordHash;
  }

  // Create a hash value from the password, return and save it.
  // Core task of the class. Takes a plaintext password as an input and goes through 
  // the following steps to generate a hash:
  public String hashPassword(String password) {
    String hashTxt = "";
    try {
      MessageDigest hash = MessageDigest.getInstance("SHA-256");
      System.out.println("Input password: " + password);

      byte[] passwordDigest = hash.digest(password.getBytes());
      BigInteger number = new BigInteger(1, passwordDigest);

      // Converts MD to hex value.
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
  // Method to verify if the input password matches the stored hash
  public boolean checkPassword(String inputPassword, String storedPasswordHash) {
    String hashedInput = hashPassword(inputPassword);
    return hashedInput.equals(storedPasswordHash);
  }
  // Method to link to the database to check if the hash value matches.
}
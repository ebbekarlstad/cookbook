package cookbook.backend.be_objects;
import cookbook.backend.DatabaseMng;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.util.Optional;

public class LogIn {
  private DatabaseMng dbManager;

  public LogIn(DatabaseMng dbManager) {
    this.dbManager = dbManager;
  }


  // Create a hash value from the password, return and save it.
  // Core task of the class. Takes a plaintext password as an input and goes through 
  // the following steps to generate a hash:
  public String hashPassword(String password) {
    try {
      MessageDigest hash = MessageDigest.getInstance("SHA-256");
      hash.update(password.getBytes());
      byte[] passwordDigest = hash.digest();
      BigInteger number = new BigInteger(1, passwordDigest);
      StringBuilder hashTxt = new StringBuilder(number.toString(16));
      while (hashTxt.length() < 32) {
        hashTxt.insert(0, "0");
      }
      return hashTxt.toString();
    } catch (NoSuchAlgorithmException e) {
      System.err.println("Hashing not found: " + e.getMessage());
      return null;
    }
  }
  // Method to verify if the input password matches the stored hash
  public boolean checkPassword(String inputPassword, String storedPasswordHash) {
    String hashedInput = hashPassword(inputPassword);
    return hashedInput != null && hashedInput.equals(storedPasswordHash);
  }
 
  // Method that habdles login
  public boolean[] doLogin(String userName, String inputPassword) {
    boolean[] result = new boolean[2];
    try {
      Optional<String> storedPasswordHash = dbManager.getPasswordHashForUser(userName);
      if (storedPasswordHash.isPresent() && checkPassword(inputPassword, storedPasswordHash.get())) {
        Optional<Integer> userId = dbManager.getUserId(userName);  // Fetch the userId
        if (userId.isPresent()) {
          boolean isAdmin = dbManager.isAdminUser(userId.get());
          UserSession.getInstance().setUserId(userId.get());  // Set the userId in UserSession
          System.out.println("Debug: User logged in successfully - UserID: " + userId.get() + ", Username: " + userName);
          result[0] = true;
          result[1] = isAdmin;
        }
      }
    } catch (Exception e) {
        System.err.println("Error during login: " + e.getMessage());
      }
    return result;
  }
}

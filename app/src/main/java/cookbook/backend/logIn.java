package cookbook.backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger:


public class logIn {
  private String passwordHash;

  public String getPasswordHash() {
    return this.passwordHash;
  }

  // Skapa hashvärde från lösenord, returnera och spara det.
  public String hashPassword(String password) {
    String hashTxt = "";
    MessageDigest hash;
    try {
      hash = MessageDigest.getInstance("SHA1");
      System.out.println("Input password: " + password);

      byte [] MessageDigest = hash.digest(password.getBytes());

      BigInteger num = new BigInteger(1, MessageDigest);

      hashTxt = num.toString(16);

      while (hashTxt.length() < 32) {
        hashTxt = "0" + hashTxt;
      }
      this.passwordHash = hashTxt;

      return hashTxt;
    } catch ( NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return hashTxt;
  }
 // Metod här för att ända lösenord till hash och koppla ihop databas för att se om hashvärdet stämmer

}
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package cookbook;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_objects.LogIn;
import cookbook.backend.be_objects.User;

class AppTest {
    // Commented out Greeting Test Case
    /*@Test void appHasAGreeting() {
         App classUnderTest = new App();
         assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
     }*/
     private LogIn login;
     private DatabaseMng dbManager;

    @BeforeEach
    void setup() {
        dbManager = new DatabaseMng();  
        login = new LogIn(dbManager);
    }

     @Test
     void testPasswordHashing() {
         String testPassword = "password123";
         String hashedPassword = login.hashPassword(testPassword);
 
         // Testa att hash-värdet inte är null och inte lika med klartextlösenordet
         assertNotNull(hashedPassword, "Hashed password should not be null");
         assertNotEquals(testPassword, hashedPassword, "Hashed password should not match plain text");
     }

    @Test
    void testUserSaveToDatabase() {
        User testUser = new User(123, "testUser", "testDisplayName", "testPassword", false, dbManager);
        
        testUser.setPassword("testPassword");  // Denna metod borde hash lösenordet internt
        boolean isSaved = testUser.saveToDatabase();

        assertTrue(isSaved, "User should be successfully saved to the database");
    }
}
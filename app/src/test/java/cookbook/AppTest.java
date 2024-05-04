/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package cookbook;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_objects.LogIn;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.User;
import cookbook.backend.be_controllers.UserController;
import cookbook.backend.be_controllers.FavoritesController;

import java.sql.SQLException;
import java.util.List;

class AppTest {
    // Commented out Greeting Test Case
    /*@Test void appHasAGreeting() {
         App classUnderTest = new App();
         assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
     }*/
     private LogIn login;
     private DatabaseMng dbManager;
     private UserController userController;
     private FavoritesController favoritesController;

    @BeforeEach
    void setup() throws SQLException {
        dbManager = new DatabaseMng();
        userController = new UserController(dbManager);
        login = new LogIn(dbManager);
        favoritesController = new FavoritesController(dbManager);

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
        User testUser = new User(1L, "user", "test user", "user", false, dbManager, null);
        userController.setUser(testUser);
        boolean isSaved = userController.saveToDatabase();

        assertTrue(isSaved, "User should be successfully saved to the database");
    }

    @Test
    void testAddFavorite() {
        // Skapa ett receptobjekt
        Recipe recipe = new Recipe("52", "Pasta Carbonara", "Simple pasta", "Cook pasta and mix with sauce");
        Long userId = 1L; // Användar-ID som redan finns i din databas
    
        // Anropa addFavorite och kontrollera resultatet
        boolean result = favoritesController.addFavorite(userId, recipe);
    
        // Verifiera att lägga till var framgångsrikt
        assertTrue(result, "Adding favorite should return true when successful");
        List<Recipe> favorites = favoritesController.getFavorites(userId);
        assertTrue(favorites.contains(recipe), "Recipe should be in users favorites");
    }

    @Test
    void testRemoveFavorite() {
        // Skapa ett receptobjekt
        Recipe recipe = new Recipe("52", "Pasta Carbonara", "Simple pasta", "Cook pasta and mix with sauce");
        Long userId = 1L; // Användar-ID som redan finns i din databas

        // Lägg till ett recept som ett favoritrecept
        favoritesController.addFavorite(userId, recipe);

        // Anropa removeFavorite och kontrollera resultatet
        boolean result = favoritesController.removeFavorite(userId, recipe);

        // Verifiera att ta bort var framgångsrikt
        assertTrue(result, "Removing favorite should return true when successful");
        List<Recipe> favorites = favoritesController.getFavorites(userId);
        assertFalse(favorites.contains(recipe), "Recipe should not be in users favorites");
        }
}
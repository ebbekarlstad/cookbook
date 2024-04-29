package cookbook.frontend.fe_controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ShoppingListViewController {

  @FXML
  private TableView<Ingredient> IngredientTable;

  @FXML
  private TableColumn<Ingredient, String> ingredientColumn;

  @FXML
  private TableColumn<Ingredient, String> amountColumn;

  @FXML
  private TextField ingredientName;

  @FXML
  private TextField amount;

  @FXML
  private Button addIngredient;

  private final ObservableList<Ingredient> ingredientList = FXCollections.observableArrayList();

  @FXML
  public void initialize() {
    ingredientColumn.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
    amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    IngredientTable.setItems(ingredientList);
  }

  @FXML
  void addIngredientToList(ActionEvent event) {
    String name = ingredientName.getText();
    String qty = amount.getText();
    if (!name.isEmpty() && !qty.isEmpty()) {
      Ingredient newIngredient = new Ingredient(name, qty);
      ingredientList.add(newIngredient);
      ingredientName.clear();
      amount.clear();
    }
  }

  public static class Ingredient {
    private final String ingredientName;
    private final String amount;

    public Ingredient(String ingredientName, String amount) {
      this.ingredientName = ingredientName;
      this.amount = amount;
    }

    public String getIngredientName() {
      return ingredientName;
    }

    public String getAmount() {
      return amount;
    }
  }
}

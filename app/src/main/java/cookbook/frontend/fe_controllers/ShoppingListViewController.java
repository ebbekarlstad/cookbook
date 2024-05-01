package cookbook.frontend.fe_controllers;

import cookbook.backend.be_controllers.UserController;
import cookbook.backend.be_objects.AmountOfIngredients;
import cookbook.backend.be_objects.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;


public class ShoppingListViewController implements Initializable {

    @FXML
    private ListView<AmountOfIngredients> ingView;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button modifyBtn;
    @FXML
    private Button amounttUp;
    @FXML
    private Button amounttDown;
    @FXML
    private Label currentIng;
    @FXML
    private Label amount_text;

    private LocalDate startDateglobal;

    private ObservableList<AmountOfIngredients> ingredients = FXCollections.observableArrayList();
    private ObservableList<AmountOfIngredients> x = FXCollections.observableArrayList();

    /**
     * Clears the shopping list by removing all items from the list view and ingredients list.
     */

    private void clear() {
        ingView.getItems().clear();
        ingredients.clear();
    }

    /**
     * Returns a string representation of the shopping list.
     *
     * @return The string representation of the shopping list.
     */

    public String stringRep() {
        StringBuilder s = new StringBuilder();
        for (AmountOfIngredients Quantity : ingView.getItems()) {
            s.append(Quantity.toData() + "\n");
        }
        String outstring = s.toString();
        return outstring;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clear();
        ingView.setCellFactory(ingr -> new ShoppingListCellView());
        ingView.setItems(ingredients);

        ingView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<AmountOfIngredients>() {
                    @Override
                    public void changed(ObservableValue<? extends AmountOfIngredients> ob, AmountOfIngredients oldQe, AmountOfIngredients newQe) {
                        selectQe(newQe);
                    }
                });
    }



    public void getShoppingList(ObservableList<AmountOfIngredients> shoppingList, LocalDate ld) {
        startDateglobal = ld;

        // Clear the ingredients list
        ingredients.clear();

        // Iterate over the shoppingList and add ingredients to the ingredients list,
        // checking for duplicates and keeping only one occurrence
        for (AmountOfIngredients ingredient : shoppingList) {
            boolean isDuplicate = false;
            for (AmountOfIngredients existingIngredient : ingredients) {
                if (ingredient.getName().equals(existingIngredient.getName())) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                ingredients.add(ingredient);
            }
        }

        // Update the ingView with the updated ingredients list
        ingView.setItems(ingredients);
    }


    public void selectQe(AmountOfIngredients quantity) {
        if (quantity != null){
            amount_text.setText(String.valueOf(quantity.getAmount()));
            currentIng.setText(quantity.getName());
        } else {
            return;
        }
    }

    @FXML
    public void onModifyBtn(ActionEvent event) {
        AmountOfIngredients quantity = ingView.getSelectionModel().getSelectedItem();
        if (quantity == null) {
            return;
        } else {
            quantity.setAmount(Float.valueOf(amount_text.getText()));
            ingView.setItems(x);
            ingView.setItems(ingredients);
            save();
        }
    }

    @FXML
    public void onDeleteBtn (ActionEvent event) {
        AmountOfIngredients qe = ingView.getSelectionModel().getSelectedItem();
        if (qe == null) {
            return;
        } else {
            ingredients.remove(qe);
            ingView.setItems(x);
            ingView.setItems(ingredients);
            save();
        }
    }


    @FXML
    public void onUpButton(ActionEvent event) {
        AmountOfIngredients qe = ingView.getSelectionModel().getSelectedItem();
        if (qe == null) {
            return;
        } else {
            Float currentAmt = Float.valueOf(amount_text.getText());
            String newAmt = String.valueOf(currentAmt+1);
            amount_text.setText(newAmt);
        }
    }


    @FXML
    public void onDownButton(ActionEvent event) {
        AmountOfIngredients qe = ingView.getSelectionModel().getSelectedItem();
        if (qe == null) {
            return;
        } else {
            Float currentAmt = Float.valueOf(amount_text.getText());
            if (currentAmt > 0) {
                String newAmt = String.valueOf(currentAmt-1);
                amount_text.setText(newAmt);
            } else {
                amount_text.setText("0");
            }
        }
    }


    public void save() {
        String pathdate = startDateglobal.toString();
        User user = UserController.loggedInUser;
        Long userId = user.getUserId();
        String basePath = "generatedDinnerList";
        String folderPath = basePath + "/" + userId;
        String fullPath = folderPath + "/" + pathdate + ".data";

        try {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(fullPath);
            file.createNewFile();

            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            BufferedWriter bWriter = new BufferedWriter(out);
            bWriter.write(stringRep());
            bWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<AmountOfIngredients> read() {
        String datePath = startDateglobal.toString();
        User user = UserController.loggedInUser;
        Long userId = user.getUserId();
        String basePath = "generatedDinnerList";
        String fullPath = basePath + "/" + userId + "/" + datePath + ".data";

        List<AmountOfIngredients> x = new ArrayList<>();

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fullPath);
            if (inputStream != null) {
                Scanner scanner = new Scanner(inputStream, "utf-8");

                if (!scanner.hasNextLine()) {
                    scanner.close();
                    return null;
                }

                String line = scanner.nextLine();
                String[] data = line.split(":");
                if (!data[0].equals("INGREDIENT")) {
                    scanner.close();
                    return null;
                }

                AmountOfIngredients ingredient = new AmountOfIngredients(data[2], Float.valueOf(data[1]), data[3]);
                x.add(ingredient);

                while (scanner.hasNext()) {
                    String ingredientLine = scanner.nextLine();
                    data = ingredientLine.split(":");

                    if (data[0].equals("INGREDIENT")) {
                        ingredient = new AmountOfIngredients(data[2], Float.valueOf(data[1]), data[3]);
                        x.add(ingredient);
                    }
                }
                scanner.close();
                return x;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
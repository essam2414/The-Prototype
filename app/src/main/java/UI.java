import java.util.List;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;


public class UI extends Application {

    private Camera camera;
    private List<String> fridgeIngredients;
    private ListView<String> ingredientsList;
    private List<RecipeMatch> matchedRecipes;
    ObservableList<RecipeMatch> recipeMatchData;

    private TableView<RecipeMatch> recipeTable;

    @Override
    public void start(Stage primaryStage) {

        // Initialize App and load recipes
        App.welcome();
        App.loadRecipes();

        // Title
        Label titleLabel = new Label("SSH Recipe Finder");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 10;");
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);

        // Ingredients List Section
        Label ingredientsLabel = new Label("Ingredients");
        ingredientsLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ingredientsList = new ListView<>();
        ObservableList<String> ingredients = FXCollections.observableArrayList();
        ingredientsList.setItems(ingredients);

        // Allow ingredientsList to grow with the layout
        VBox.setVgrow(ingredientsList, Priority.ALWAYS);

        Button GetIngredients = new Button("Get Ingredients");
        GetIngredients.setStyle("-fx-padding: 12px; -fx-background-color: #59326C; -fx-text-fill: white;");
        GetIngredients.setOnAction(e -> GetIngredients());

        VBox ingredientsBox = new VBox(10, ingredientsLabel, ingredientsList, GetIngredients);
        ingredientsBox.setPrefWidth(200);
        VBox.setVgrow(ingredientsBox, Priority.ALWAYS);

        // Recipe Table Section
        Label recipeLabel = new Label("Matched Recipes:");
        recipeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        recipeTable = new TableView<>();
        recipeTable.setPrefHeight(400);

        TableColumn<RecipeMatch, String> nameColumn = new TableColumn<>("Recipe Name");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRecipe().getName()));
        nameColumn.setPrefWidth(150);

        TableColumn<RecipeMatch, String> ingredientsColumn = new TableColumn<>("Ingredients");
        ingredientsColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRecipe().getIngredients()));
        ingredientsColumn.setPrefWidth(250);

        TableColumn<RecipeMatch, String> stepsColumn = new TableColumn<>("Steps");
        stepsColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRecipe().getSteps()));
        stepsColumn.setPrefWidth(150);

        TableColumn<RecipeMatch, String> cookColumn = new TableColumn<>("Cook Time");
        cookColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRecipe().getCookTime() + " mins"));
        cookColumn.setPrefWidth(75);

        TableColumn<RecipeMatch, String> matchColumn = new TableColumn<>("% match");
        matchColumn.setCellValueFactory(data -> new SimpleStringProperty(String.format("%.1f%%", data.getValue().getMatchPercentage())));
        matchColumn.setPrefWidth(75);

        recipeTable.getColumns().addAll(nameColumn, ingredientsColumn, stepsColumn, cookColumn, matchColumn);

        // Add horizontal scrollbar by wrapping the TableView in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(recipeTable);
        scrollPane.setFitToHeight(true); // Allow vertical fit
        scrollPane.setFitToWidth(true); // Allow horizontal fit
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        VBox recipeBox = new VBox(10, recipeLabel, scrollPane);
        HBox.setHgrow(recipeBox, Priority.ALWAYS);

        // Buttons Section below Recipe Table
        Button changeButton = new Button("Add Recipe");
        changeButton.setStyle("-fx-padding: 10px; -fx-background-color: #0078d7; -fx-text-fill: white;");
        changeButton.setOnAction(e -> addRecipe(recipeTable)); // Set action for Add Recipe button

        Button deleteButton = new Button("Hide Recipe");
        deleteButton.setStyle("-fx-padding: 10px; -fx-background-color: #d32f2f; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> HideRecipe(recipeTable)); // Set action for Delete Recipe button


        HBox tableButtons = new HBox(20, changeButton, deleteButton);
        tableButtons.setStyle("-fx-alignment: center;");

        VBox tableWithButtons = new VBox(10, recipeBox, tableButtons);

        // Main Layout
        HBox mainContent = new HBox(20, ingredientsBox, tableWithButtons);
        HBox.setHgrow(tableWithButtons, Priority.ALWAYS);

        VBox layout = new VBox(10, titleLabel, mainContent);
        VBox.setVgrow(mainContent, Priority.ALWAYS);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f0f0f0;");

        // Scene and Stage
        Scene scene = new Scene(layout, 800, 550);
        primaryStage.setTitle("Recipe Finder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void GetIngredients() {
        camera = new Camera();
        camera.simulateCapture(App.recipes); // Pass the list of recipes to the camera
        camera.printCapturedIngredients();
        fridgeIngredients = camera.getCapturedIngredients(); // Store the captured ingredients
        ingredientsList.getItems().setAll(fridgeIngredients);
        matchedRecipes = App.matchedRecipes(fridgeIngredients);
        recipeMatchData = FXCollections.observableArrayList(matchedRecipes);
        recipeTable.setItems(recipeMatchData);
    }
    private void addRecipe(TableView<RecipeMatch> recipeTable) {
        // Create a new stage (window)
        Stage addRecipeStage = new Stage();
        addRecipeStage.setTitle("Add New Recipe");
    
        // Recipe Name
        Label nameLabel = new Label("Recipe Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter recipe name");
    
        // Ingredients
        Label ingredientsLabel = new Label("Ingredients (comma-separated):");
        TextField ingredientsField = new TextField();
        ingredientsField.setPromptText("Enter ingredients");
    
        // Steps
        Label stepsLabel = new Label("Steps:");
        TextField stepsField = new TextField();
        stepsField.setPromptText("Enter steps");
    
        // Cooking Time
        Label cookingTimeLabel = new Label("Cooking Time (in minutes):");
        TextField cookingTimeField = new TextField();
        cookingTimeField.setPromptText("Enter cooking time");
    
        // Buttons
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
    
        // Save Button Action
        saveButton.setOnAction(e -> {
            String recipeName = nameField.getText().trim();
            String ingredients = ingredientsField.getText().trim();
            String steps = stepsField.getText().trim();
            String cookingTimeStr = cookingTimeField.getText().trim();
    
            if (!recipeName.isEmpty() && !ingredients.isEmpty() && !steps.isEmpty() && !cookingTimeStr.isEmpty()) {
                try {
                    // Parse cooking time
                    int cookingTime = Integer.parseInt(cookingTimeStr);
    
                    // Calculate match percentage directly here
                    double matchPercentage = 0.0;
                    if (fridgeIngredients != null && !fridgeIngredients.isEmpty()) {
                        String[] recipeIngredientsArray = ingredients.split(",");
                        int matchingCount = 0;
    
                        // Normalize ingredient names for comparison
                        for (String ingredient : recipeIngredientsArray) {
                            if (fridgeIngredients.stream().map(String::toLowerCase).anyMatch(i -> i.equals(ingredient.trim().toLowerCase()))) {
                                matchingCount++;
                            }
                        }
    
                        matchPercentage = (double) matchingCount / recipeIngredientsArray.length * 100;
                    }
    
                    // Create new Recipe and RecipeMatch
                    Recipe newRecipe = new Recipe(recipeName, ingredients, steps, cookingTime);
                    RecipeMatch newRecipeMatch = new RecipeMatch(newRecipe, matchPercentage);
    
                    // Add to the table
                    recipeTable.getItems().add(newRecipeMatch);
    
                    // Close the window
                    addRecipeStage.close();
                } catch (NumberFormatException ex) {
                    // Show alert if cooking time is invalid
                    showAlert("Invalid Input", "Please enter a valid number for cooking time.");
                }
            } else {
                // Show alert if any field is empty
                showAlert("Incomplete Input", "Please fill in all fields.");
            }
        });
    
        // Cancel Button Action
        cancelButton.setOnAction(e -> addRecipeStage.close());
    
        // Layout
        VBox layout = new VBox(10,
            nameLabel, nameField,
            ingredientsLabel, ingredientsField,
            stepsLabel, stepsField,
            cookingTimeLabel, cookingTimeField,
            new HBox(10, saveButton, cancelButton)
        );
        layout.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(layout, 400, 400);
    
        // Show the new stage
        addRecipeStage.setScene(scene);
        addRecipeStage.show();
    }
    
    // Delete Recipe functionality
    private void HideRecipe(TableView<RecipeMatch> recipeTable) {
        RecipeMatch selectedRecipeMatch = recipeTable.getSelectionModel().getSelectedItem(); // Fetch the selected RecipeMatch
        if (selectedRecipeMatch != null) {
            boolean confirm = showConfirmation("Delete Recipe", "Are you sure you want to delete this recipe?");
            if (confirm) {
                recipeTable.getItems().remove(selectedRecipeMatch); // Remove selected RecipeMatch from the table
            }
        } else {
            showAlert("No Recipe Selected", "Please select a recipe to delete.");
        }
    }
    

    // Show an alert box for messages
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Show a confirmation dialog
    private boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }
    public static void main(String[] args) {
        launch(args);
    }
}

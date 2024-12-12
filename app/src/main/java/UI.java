import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class UI extends Application {

    private Camera camera;
    private List<String> fridgeIngredients;
    private ListView<String> ingredientsList;

    @Override
    public void start(Stage primaryStage) {

        // Initialize App and load recipes
        App.loadRecipes();

        // Title
        Label titleLabel = new Label("SSH Recipe Finder");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 10;");
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setStyle("-fx-alignment: center; -fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 10;");
        
        // Ingredients List Section
        Label ingredientsLabel = new Label("Ingredients from Fridge:");
        ingredientsLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ingredientsList = new ListView<>();
        ObservableList<String> ingredients = FXCollections.observableArrayList(
        );
        ingredientsList.setItems(ingredients);
        ingredientsList.setPrefWidth(200);
        ingredientsList.setPrefHeight(400);

        VBox ingredientsBox = new VBox(10, ingredientsLabel, ingredientsList);
        ingredientsBox.setPrefWidth(200);

        // Recipe Table Section
        Label recipeLabel = new Label("Matched Recipes:");
        recipeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TableView<Recipe> recipeTable = new TableView<>();
        recipeTable.setPrefHeight(400);

        TableColumn<Recipe, String> nameColumn = new TableColumn<>("Recipe Name");
        //nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        nameColumn.setPrefWidth(100);

        TableColumn<Recipe, String> ingredientsColumn = new TableColumn<>("Ingredients");
        //ingredientsColumn.setCellValueFactory(data -> data.getValue().ingredientsProperty());
        ingredientsColumn.setPrefWidth(100);

        TableColumn<Recipe, String> stepsColumn = new TableColumn<>("Steps");
        //nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        nameColumn.setPrefWidth(100);

        TableColumn<Recipe, String> cookColumn = new TableColumn<>("Cook Time");
        //nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        nameColumn.setPrefWidth(100);

        TableColumn<Recipe, String> matchColumn = new TableColumn<>("% match");
        //nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        nameColumn.setPrefWidth(100);

        recipeTable.getColumns().addAll(nameColumn, ingredientsColumn, stepsColumn, cookColumn, matchColumn);

        VBox recipeBox = new VBox(10, recipeLabel, recipeTable);
        HBox.setHgrow(recipeBox, Priority.ALWAYS);

        // Layout for Ingredients and Recipes
        HBox mainContent = new HBox(20, ingredientsBox, recipeBox);
        
        // Buttons Section
        Button GetIngredients = new Button("Get Ingredients");
        GetIngredients.setStyle("-fx-padding: 10px; -fx-background-color: #59326C; -fx-text-fill: white;");
        GetIngredients.setOnAction(e -> GetIngredients());


        Button changeButton = new Button("Change Recipe");
        changeButton.setStyle("-fx-padding: 10px; -fx-background-color: #0078d7; -fx-text-fill: white;");
        changeButton.setTranslateX(300);

        Button deleteButton = new Button("Delete Recipe");
        deleteButton.setStyle("-fx-padding: 10px; -fx-background-color: #d32f2f; -fx-text-fill: white;");
        deleteButton.setTranslateX(300);

        HBox buttonBox = new HBox(10,GetIngredients, changeButton, deleteButton);
        buttonBox.setStyle("-fx-padding: 10;");
        buttonBox.setSpacing(10);

        // Main Layout
        VBox layout = new VBox(10, titleLabel, mainContent, buttonBox);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f0f0f0;");

        // Scene and Stage
        Scene scene = new Scene(layout, 700, 600);
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
    }


    public static void main(String[] args) {
        launch(args);
    }
}

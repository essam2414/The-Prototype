import java.util.List;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        titleLabel.setStyle("-fx-alignment: center; -fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 10;");
        
        // Ingredients List Section
        Label ingredientsLabel = new Label("Ingredients");
        ingredientsLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ingredientsList = new ListView<>();
        ObservableList<String> ingredients = FXCollections.observableArrayList(
        );
        ingredientsList.setItems(ingredients);
        ingredientsList.setPrefWidth(200);
        ingredientsList.setPrefHeight(400);

        Button GetIngredients = new Button("Get Ingredients");
        GetIngredients.setStyle("-fx-padding: 12px; -fx-background-color: #59326C; -fx-text-fill: white;");
        GetIngredients.setOnAction(e -> GetIngredients());

        VBox ingredientsBox = new VBox(10, ingredientsLabel, ingredientsList, GetIngredients);
        ingredientsBox.setPrefWidth(200);

        // Recipe Table Section
        Label recipeLabel = new Label("Matched Recipes:");
        recipeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        recipeTable = new TableView<>();
        //recipeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        recipeTable.setPrefHeight(400);

        TableColumn<RecipeMatch, String> nameColumn = new TableColumn<>("Recipe Name");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRecipe().getName()));
        nameColumn.setPrefWidth(100);

        TableColumn<RecipeMatch, String> ingredientsColumn = new TableColumn<>("Ingredients");
        ingredientsColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRecipe().getIngredients()));
        ingredientsColumn.setPrefWidth(250);

        TableColumn<RecipeMatch, String> stepsColumn = new TableColumn<>("Steps");
        stepsColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRecipe().getSteps()));
        stepsColumn.setPrefWidth(150);

        TableColumn<RecipeMatch, String> cookColumn = new TableColumn<>("Cook Time");
        cookColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRecipe().getCookTime() + " mins"));
        cookColumn.setPrefWidth(50);

        TableColumn<RecipeMatch, String> matchColumn = new TableColumn<>("% match");
        matchColumn.setCellValueFactory(data -> new SimpleStringProperty(String.format("%.1f%%", data.getValue().getMatchPercentage())));
        matchColumn.setPrefWidth(50);

        recipeTable.getColumns().addAll(nameColumn, ingredientsColumn, stepsColumn, cookColumn, matchColumn);

        // Add horizontal scrollbar by wrapping the TableView in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(recipeTable);
        scrollPane.setFitToHeight(true); // Allow vertical fit
        scrollPane.setFitToWidth(false); // Allow horizontal scrolling
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox recipeBox = new VBox(10, recipeLabel, recipeTable);
        HBox.setHgrow(recipeBox, Priority.ALWAYS);

        // Buttons Section below Recipe Table
        Button changeButton = new Button("Change Recipe");
        changeButton.setStyle("-fx-padding: 10px; -fx-background-color: #0078d7; -fx-text-fill: white;");

        Button deleteButton = new Button("Delete Recipe");
        deleteButton.setStyle("-fx-padding: 10px; -fx-background-color: #d32f2f; -fx-text-fill: white;");

        HBox tableButtons = new HBox(20, changeButton, deleteButton);
        tableButtons.setStyle("-fx-alignment: center;");
        tableButtons.setSpacing(20);

        VBox tableWithButtons = new VBox(10, recipeBox, tableButtons);

        // Main Layout
        HBox mainContent = new HBox(20, ingredientsBox, tableWithButtons);
        VBox layout = new VBox(10, titleLabel, mainContent);
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
        matchedRecipes = App.matchedRecipes(fridgeIngredients);
        recipeMatchData = FXCollections.observableArrayList(matchedRecipes);
        recipeTable.setItems(recipeMatchData);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

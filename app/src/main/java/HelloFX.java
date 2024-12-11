import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class HelloFX extends Application {

    private Button sshCameraButton;
    private Button showAllRecipesButton;
    private Button returnButton;
    private Text recipeText;

    private Stage stage; 
    private Camera camera; 
    private List<String> fridgeIngredients; 

    
    @Override
    public void start(Stage primaryStage) {
    this.stage = primaryStage;

    // Initialize recipeText
    recipeText = new Text();

    // Initialize App and load recipes
    App.loadRecipes();

    // Create the "SSH Camera" Button
    sshCameraButton = new Button("SSH Camera");
    sshCameraButton.setOnAction(e -> onSshCameraClicked());

    // Create the "Show All Recipes" Button
    showAllRecipesButton = new Button("Show All Recipes");
    showAllRecipesButton.setOnAction(e -> showAllRecipes());

    // Create the "Return" Button
    returnButton = new Button("Return");
    returnButton.setOnAction(e -> goBackToInitialPage());

    // Create the initial scene with both buttons
    StackPane initialRoot = new StackPane();
    initialRoot.getChildren().addAll(sshCameraButton, showAllRecipesButton);
    sshCameraButton.setTranslateY(-50);
    showAllRecipesButton.setTranslateY(50);

    Scene initialScene = new Scene(initialRoot, 640, 480);
    primaryStage.setTitle("Fridge & Recipe Finder");
    primaryStage.setScene(initialScene);
    primaryStage.show();
}


    // Handle the SSH Camera Button click
    private void onSshCameraClicked() {
        // Navigate to "Take a Picture" page
        Button takePictureButton = new Button("Take a Picture");
        takePictureButton.setOnAction(e -> onTakePictureClicked());

        // Create a page with the "Take a Picture" button
        StackPane pictureRoot = new StackPane();
        Text takingPictureText = new Text("Click 'Take a Picture' to capture the fridge ingredients.");
        pictureRoot.getChildren().addAll(takingPictureText, takePictureButton, returnButton);

        takePictureButton.setTranslateY(50);
        returnButton.setTranslateY(100);

        Scene pictureScene = new Scene(pictureRoot, 640, 480);
        stage.setScene(pictureScene);
    }

    // Handle the "Take a Picture" Button click
    private void onTakePictureClicked() {
        // Simulate taking a picture (camera functionality) and capture the fridge ingredients
        if (fridgeIngredients == null) {
            camera = new Camera();
            camera.simulateCapture(App.recipes); // Pass the list of recipes to the camera
            camera.printCapturedIngredients();
            fridgeIngredients = camera.getCapturedIngredients(); // Store the captured ingredients
        }

        // After picture is taken, show the top recipes based on the captured fridge ingredients
        showTopRecipes(fridgeIngredients);
    }

    // Show top recipes based on detected fridge ingredients
    private void showTopRecipes(List<String> fridgeIngredients) {
        // Create a Text object to display the fridge ingredients
        Text ingredientsText = new Text("Ingredients detected by Camera: " + String.join(", ", fridgeIngredients));
    
        // Calculate match percentages
        List<RecipeMatch> recipeMatches = new ArrayList<>();
        for (Recipe recipe : App.recipes) {
            double matchPercentage = App.calculateMatchPercentage(recipe, fridgeIngredients);
            recipeMatches.add(new RecipeMatch(recipe, matchPercentage));
        }
    
        // Sort recipes by match percentage
        recipeMatches.sort((r1, r2) -> Double.compare(r2.getMatchPercentage(), r1.getMatchPercentage()));
    
        // Display top 3 matching recipes
        StringBuilder topRecipesText = new StringBuilder("Top 3 Matching Recipes:\n");
        for (int i = 0; i < Math.min(3, recipeMatches.size()); i++) {
            RecipeMatch match = recipeMatches.get(i);
            topRecipesText.append(String.format("%s (%.1f%% match)\n", match.getRecipe().getName(), match.getMatchPercentage()));
        }
    
        // Display the ingredients and the top 3 recipes on the UI
        recipeText.setText(topRecipesText.toString());
    
        // Switch to recipe scene
        StackPane recipeRoot = new StackPane();
        recipeRoot.getChildren().addAll(ingredientsText, recipeText, returnButton);
    
        // Position the texts and return button
        ingredientsText.setTranslateY(-100);  // Position the ingredients text above the top recipes text
        recipeText.setTranslateY(0);          // Position the recipe text
        returnButton.setTranslateY(100);      // Position the return button at the bottom
    
        // Create a new scene with the grid layout and the return button
        Scene recipeScene = new Scene(recipeRoot, 640, 480);
        stage.setScene(recipeScene);
    }

    // Show all available recipes in a grid layout
    private void showAllRecipes() {
    // Create a GridPane to display recipes in a table format
    GridPane grid = new GridPane();
    grid.setVgap(10);  // Vertical gap between rows
    grid.setHgap(10);  // Horizontal gap between columns

    // Add headers for the grid columns
    Text recipeNameHeader = new Text("Recipe Name");
    Text ingredientsHeader = new Text("Ingredients");
    Text stepsHeader = new Text("Steps");
    Text cookTimeHeader = new Text("Cook Time");

    grid.add(recipeNameHeader, 0, 0);  // Add header to the first row, first column
    grid.add(ingredientsHeader, 1, 0);  // Add header to the first row, second column
    grid.add(stepsHeader, 2, 0);  // Add header to the first row, third column
    grid.add(cookTimeHeader, 3, 0);  // Add header to the first row, fourth column

    // Add recipe details starting from the second row
    int row = 1;
    for (Recipe recipe : App.recipes) {
        Text recipeNameText = new Text(recipe.getName());
        Text ingredientsText = new Text(String.join(", ", recipe.getIngredients()));
        Text stepsText = new Text(recipe.getSteps());
        Text cookTimeText = new Text(recipe.getCookTime() + " minutes");

        grid.add(recipeNameText, 0, row);
        grid.add(ingredientsText, 1, row);
        grid.add(stepsText, 2, row);
        grid.add(cookTimeText, 3, row);

        row++;
    }

    // Add a return button to go back to the initial page
    returnButton.setOnAction(e -> goBackToInitialPage());

    // Create a StackPane to hold the grid and the return button
    StackPane recipeRoot = new StackPane();
    recipeRoot.getChildren().addAll(grid, returnButton);

    // Position the return button at the bottom of the screen
    returnButton.setTranslateY(250);

    // Create a new scene with the grid layout and the return button
    Scene recipeScene = new Scene(recipeRoot, 800, 600);  // Adjust the size as needed
    stage.setScene(recipeScene);
}


    // Go back to the initial page
    private void goBackToInitialPage() {
        // Create and set the initial scene with both buttons
        StackPane initialRoot = new StackPane();
        initialRoot.getChildren().addAll(sshCameraButton, showAllRecipesButton);
        sshCameraButton.setTranslateY(-50);
        showAllRecipesButton.setTranslateY(50);

        Scene initialScene = new Scene(initialRoot, 640, 480);
        stage.setScene(initialScene);
    }

    public static void main(String[] args) {
        launch();
    }
}

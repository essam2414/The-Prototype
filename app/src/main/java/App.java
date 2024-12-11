import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class App {

    public static List<Recipe> recipes = new ArrayList<>();

    public String getGreeting() {
        return "Recipe SSH Prototype";
    }

    private static String[] parseLine(String line) {
        // Regular expression to match fields with or without quotes
        return line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    }

    public static void loadRecipes() {
        System.out.println();
        try {
            InputStream inputStream = App.class.getClassLoader().getResourceAsStream("recipe.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] recipe = parseLine(line); // Split by commas

                String name = recipe[0];
                String ingredients = recipe[1].replace("\"", "");
                String steps = recipe[2].replace("\"", "");
                int cookTime = Integer.parseInt(recipe[3]);
                recipes.add(new Recipe(name, ingredients, steps, cookTime));
            }
            reader.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void printRecipes(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            System.out.println(recipe.toString());
        }
    }

    //calculate the match percentage between a recipe and fridge ingredients
    public static double calculateMatchPercentage(Recipe recipe, List<String> fridgeIngredients) {
        String[] recipeIngredients = recipe.getIngredients().split(", ");
        int matchedCount = 0;

        for (String ingredient : recipeIngredients) {
            if (fridgeIngredients.contains(ingredient)) {
                matchedCount++;
            }
        }

        return (double) matchedCount / recipeIngredients.length * 100;
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        //load recipes.csv 
        loadRecipes();
        printRecipes(recipes);

        //simulate camera functionality
        Camera camera = new Camera();
        camera.simulateCapture(recipes); // Pass the list of recipes to the camera
        camera.printCapturedIngredients();

        //fetch detected ingredients
        List<String> fridgeIngredients = camera.getCapturedIngredients();
        System.out.println("\nIngredients detected by the camera: " + fridgeIngredients);

        //calculate match percentages
        List<RecipeMatch> recipeMatches = new ArrayList<>();
        for (Recipe recipe : recipes) {
            double matchPercentage = calculateMatchPercentage(recipe, fridgeIngredients);
            recipeMatches.add(new RecipeMatch(recipe, matchPercentage));
        }

        //sort recipes by match percentage
        Collections.sort(recipeMatches, Comparator.comparingDouble(RecipeMatch::getMatchPercentage).reversed());

        //print top 3 matching recipes
        System.out.println("\nTop 3 Matching Recipes:");
        for (int i = 0; i < Math.min(3, recipeMatches.size()); i++) {
            RecipeMatch match = recipeMatches.get(i);
            System.out.printf("%s (%.1f%% match)\n", match.getRecipe().getName(), match.getMatchPercentage());
        }
    }
}

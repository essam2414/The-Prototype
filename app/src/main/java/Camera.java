import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Camera {

    private List<String> allIngredients; //ingredients from the recipes
    private List<String> capturedIngredients; //ingredients captured during simulation

    //constructor
    public Camera() {
        this.allIngredients = new ArrayList<>();
        this.capturedIngredients = new ArrayList<>();
        loadIngredientsFromRecipes();
        simulateCapture();
    }

    //load all ingredients from the recipe.csv file
    private void loadIngredientsFromRecipes() {
        try {
            InputStream inputStream = Camera.class.getClassLoader().getResourceAsStream("recipe.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine(); //skip header line

            Set<String> uniqueIngredients = new HashSet<>(); //use set to avoid duplicates
            while ((line = reader.readLine()) != null) {
                String[] recipe = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); //parse CSV line
                String[] ingredients = recipe[1].replace("\"", "").split(", "); //split ingredients
                for (String ingredient : ingredients) {
                    uniqueIngredients.add(ingredient.trim());
                }
            }
            this.allIngredients.addAll(uniqueIngredients); //convert set to list
            reader.close();
        } catch (Exception ex) {
            System.out.println("Error loading ingredients: " + ex.getMessage());
        }
    }

    //simulate capturing ingredients from the fridge
    private void simulateCapture() {
        Random random = new Random();
        int numIngredients = random.nextInt(6) + 5; //random number (5-10)
        capturedIngredients.clear();

        while (capturedIngredients.size() < numIngredients) {
            String ingredient = allIngredients.get(random.nextInt(allIngredients.size()));
            if (!capturedIngredients.contains(ingredient)) {
                capturedIngredients.add(ingredient);
            }
        }
    }

    //get the captured ingredients
    public List<String> getCapturedIngredients() {
        return capturedIngredients;
    }

    public void printCapturedIngredients() {
        System.out.println("Captured Ingredients:");
        for (String ingredient : capturedIngredients) {
            System.out.println("- " + ingredient);
        }
    }
}

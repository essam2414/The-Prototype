import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Camera {

    private List<String> capturedIngredients; //ingredients captured during simulation

    //constructor
    public Camera() {
        this.capturedIngredients = new ArrayList<>();
    }

    //capturing ingredients from the recipes provided by App
    public void simulateCapture(List<Recipe> recipes) {
        Random random = new Random();
        List<String> allIngredients = new ArrayList<>();

        //collect all unique ingredients from the recipes
        for (Recipe recipe : recipes) {
            String[] ingredients = recipe.getIngredients().split(", "); // Split ingredients
            for (String ingredient : ingredients) {
                if (!allIngredients.contains(ingredient)) {
                    allIngredients.add(ingredient.trim());
                }
            }
        }

        //select 5 to 10 ingredients
        int numIngredients = random.nextInt(6) + 5; //random number (5-10)
        capturedIngredients.clear();
        while (capturedIngredients.size() < numIngredients) {
            String ingredient = allIngredients.get(random.nextInt(allIngredients.size()));
            if (!capturedIngredients.contains(ingredient)) {
                capturedIngredients.add(ingredient);
            }
        }
    }

    //get captured ingredients
    public List<String> getCapturedIngredients() {
        return capturedIngredients;
    }

    //print ingredients
    public void printCapturedIngredients() {
        System.out.println("Captured Ingredients:");
        for (String ingredient : capturedIngredients) {
            System.out.println("- " + ingredient);
        }
        System.out.println();
    }
}

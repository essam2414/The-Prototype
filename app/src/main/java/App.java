import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
                String ingredients = recipe[1].replace("\"","");
                String steps = recipe[2].replace("\"","");
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

    public static List<Recipe> matchRecipes(String cameraInput) {
        List<Recipe> matchedRecipes = new ArrayList<>();
    
        for (Recipe recipe : recipes) {
            if (recipe.getName().toLowerCase().contains(cameraInput.toLowerCase()) || 
                recipe.getIngredients().toLowerCase().contains(cameraInput.toLowerCase())) {
                matchedRecipes.add(recipe);
            }
        }
    
        return matchedRecipes;
    }
    

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        loadRecipes();
        printRecipes(recipes);

    }
}

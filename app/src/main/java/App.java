import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static List<Recipe> recipes = new ArrayList<>();

    public String getGreeting() {
        return "Recipe SSH Prototype";
    }

    private static String[] parseLine(String line) {
        return line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    }

    public static void loadRecipes() {
        System.out.println();
        try {
            InputStream inputStream = App.class.getClassLoader().getResourceAsStream("recipe.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] recipe = parseLine(line);
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

    public static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. View Recipes");
        System.out.println("2. Add Recipe");
        System.out.println("3. Remove Recipe");
        System.out.println("4. Edit Recipe");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    public static void addRecipe() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter recipe name: ");
        String name = scanner.nextLine();

        System.out.print("Enter ingredients (comma-separated): ");
        String ingredients = scanner.nextLine();

        System.out.print("Enter steps: ");
        String steps = scanner.nextLine();

        System.out.print("Enter cooking time (in minutes): ");
        int cookTime = scanner.nextInt();
        scanner.nextLine(); //consume newline

        recipes.add(new Recipe(name, ingredients, steps, cookTime));
        System.out.println("Recipe added successfully!");
    }

    public static void removeRecipe() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the recipe to remove: ");
        String name = scanner.nextLine();

        recipes.removeIf(recipe -> recipe.getName().equalsIgnoreCase(name));
        System.out.println("Recipe removed successfully (if it existed).");
    }

    public static void editRecipe() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the recipe to edit: ");
        String name = scanner.nextLine();

        for (Recipe recipe : recipes) {
            if (recipe.getName().equalsIgnoreCase(name)) {
                System.out.print("Enter new ingredients (comma-separated): ");
                String ingredients = scanner.nextLine();

                System.out.print("Enter new steps: ");
                String steps = scanner.nextLine();

                System.out.print("Enter new cooking time (in minutes): ");
                int cookTime = scanner.nextInt();
                scanner.nextLine(); //consume newline

                recipes.remove(recipe);
                recipes.add(new Recipe(name, ingredients, steps, cookTime));
                System.out.println("Recipe updated successfully!");
                return;
            }
        }
        System.out.println("Recipe not found.");
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        loadRecipes();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); //consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nExisting Recipes:");
                    printRecipes(recipes);
                    break;
                case 2:
                    addRecipe();
                    break;
                case 3:
                    removeRecipe();
                    break;
                case 4:
                    editRecipe();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
public class Recipe {
    private final String name;
    private final String ingredients;
    private final String steps;
    private final int cookTime;

    public Recipe(String name, String ingredients, String steps, int cookTime) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.cookTime = cookTime;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public int getCookTime() {
        return cookTime;
    }

    @Override
    public String toString() {
        return "Recipe Name: " + name + "\n" +
               "Ingredients: " + ingredients + "\n" +
               "Steps: " + steps + "\n" +
               "Cook Time: " + cookTime + " min\n";
    }
}

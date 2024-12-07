# The Prototype
SSH for life !

Team members: Essam, Badr, Ahmed

### Key Tasks:
1. **Group Formation**: Form a group of 3-4 members who wrote the same EDR in the first coursework.

2. **Prototype Development**: Choose one of the group’s EDRs and build a prototype to demonstrate its viability. The prototype should focus on the essential components of the design.

3. **Simulation**: Simulate other system parts that the prototype depends on (e.g., using hardcoded values, mock systems, or user input).

4. **Software Engineering Techniques**: Use appropriate techniques such as version control, testing, build automation, and continuous integration.

5. **Collaboration**: Work effectively as a team, leveraging strengths and supporting weaknesses, ensuring equal contribution from all members.

6. **Report Writing**: Submit:
   - A **200-word summary** of the chosen EDR and accomplishments.
   - An **800-word report** on the prototype’s development, highlighting team collaboration and software engineering practices.
   
7. **Individual Reflections**: Each member writes **250 words of constructive feedback** about every other group member.

### Assessment Criteria:
1. **Implementation of the Prototype (40%)**: 
   - How well does the prototype demonstrate the viability of the design?
   - Were challenges in the design addressed and solved during the prototyping process?
   
2. **Software Engineering Techniques (40%)**: 
   - Effective use of software engineering practices like version control, build automation, dependency management, testing, and continuous integration.
   
3. **Project Management (20%)**: 
   - Planning and breaking down work into manageable tasks.
   - Communicating progress and changes, updating tasks when necessary.
   - Ensuring all group members contribute appropriately.

### Submission:
- **Code repository** (with full history).
- **Report** (summary and detailed description of the prototype).
- **Individual reflections** on other group members' contributions.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


### **Predicted Planning**

1. **Core Objective**
   - Match available ingredients in the fridge with a basic recipe database and display suggestions.

2. **Minimal Dependencies**
   - Assume the SSH camera already detects and uploads fridge inventory to the SSH Cloud.
   - Use a basic recipe database with static data or mock examples.
   - No need for advanced UI—just a basic list of suggestions in the app.

3. **Single Goal**
   - Provide a daily list of recipe suggestions based on ingredients with at least a basic match (e.g., 50% of ingredients).

---

### **Predicted Implementation**

#### **Step 1: Ingredient Detection**
   - Use the SSH camera's daily inventory update.
   - Fetch this inventory list from the cloud.

#### **Step 2: Match Ingredients to Recipes**
   - Use a predefined recipe list (even a small sample of 10–20 recipes).
   - Calculate a simple match percentage for each recipe:
     \[
     \text{Match Percentage} = \frac{\text{Available Ingredients in Recipe}}{\text{Total Ingredients in Recipe}} \times 100
     \]
   - Set a threshold, e.g., 50%, to include a recipe in the suggestions.

#### **Step 3: Basic Output**
   - Generate a plain list of recipes with the match percentage.
   - Display this list as a simple text output in the SSH app or even via a notification.

#### **Step 4: Feedback Collection**
   - Include a basic option for users to mark recipes as "useful" or "not useful."
   - Use this input to refine the matching logic or prioritize recipes.

---

### **Predicted Workflow**

1. **User Interaction**
   - User opens the SSH app, navigates to "Suggested Recipes."
   - Sees a list of recipes with matched ingredients and missing ones.

2. **Daily Update**
   - Inventory is refreshed once a day.
   - Recipes are suggested based on the updated inventory.

3. **Testing**
   - Verify if the recipes are practical and the feature is intuitive.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### **Objective**
Demonstrate the viability of the recipe suggestion feature described in the EDR by building a basic proof-of-concept system. Simulate dependent systems to avoid unnecessary complexity.

---

### **Prototype Plan**

#### **1. Simplified Scope**
- **Core Functionality**: Match a list of fridge ingredients to a predefined recipe database and suggest recipes.
- **Simulated Systems**: Mock the SSH camera and cloud data (e.g., use hardcoded or randomly generated ingredient lists).
- **Basic Output**: Display suggested recipes in a terminal or simple web interface.

#### **2. Key Steps**

1. **Mock Fridge Data**
   - Create a script to generate a small list of "detected" fridge ingredients (e.g., `["milk", "eggs", "flour"]`).

2. **Static Recipe Database**
   - Use a basic JSON or CSV file with a small set of recipes:
     ```json
     [
       {
         "name": "Pancakes",
         "ingredients": ["milk", "eggs", "flour", "sugar"]
       },
       {
         "name": "Omelette",
         "ingredients": ["eggs", "butter", "salt"]
       }
     ]
     ```

3. **Ingredient Matching**
   - Write a function to calculate the match percentage:
     ```python
     def calculate_match(fridge, recipe):
         common = set(fridge) & set(recipe)
         return (len(common) / len(recipe)) * 100
     ```

4. **Threshold-Based Suggestions**
   - Filter recipes based on a hardcoded match threshold (e.g., 75%).

5. **Simple Output**
   - Display matching recipes and missing ingredients in the console or a simple HTML page.

---

### **Implementation**

#### **Step 1: Mock Data**
Generate fridge ingredients:
```
fridge_ingredients = ["milk", "eggs", "flour", "butter", "sugar"]
```

#### **Step 2: Recipe Matching**
Use the recipe database to suggest matches:
```
recipes = [
    {"name": "Pancakes", "ingredients": ["milk", "eggs", "flour", "sugar"]},
    {"name": "Omelette", "ingredients": ["eggs", "butter", "salt"]}
]

threshold = 50
suggested_recipes = []

for recipe in recipes:
    match = calculate_match(fridge_ingredients, recipe["ingredients"])
    if match >= threshold:
        suggested_recipes.append({"name": recipe["name"], "match": match})

print(suggested_recipes)
```

#### **Step 3: Output**
Show results in the simplest format possible:
```bash
Suggested Recipes:
1. Pancakes (75% match)
2. Omelette (66% match)
```

---

### **Simulations**
- **Mock Ingredient Data**: Hardcode or randomly generate fridge contents.
- **Mock Recipe Database**: Use a small, static recipe list.
- **Simplified Interaction**: Display results in a terminal or basic webpage.

---

### **Group Collaboration and Documentation**
1. **Version Control**:
   - Use GitHub for code sharing and collaboration.
2. **Team Workflows**:
   - Divide tasks: one person for mock data, another for matching logic, and one for output.
3. **Testing**:
   - Write simple unit tests for the matching function.
4. **Report**:
   - Summarize the approach, what was implemented, and lessons learned in ~800 words.

---
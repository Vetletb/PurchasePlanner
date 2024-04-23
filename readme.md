
<h1> Purchase planner </h1>
The purchase planner is a tool to help you track your food inventory and plan your future meals.

<h3> Installation </h3>
This short guide will help you install the purchase planner on your computer. You can either run the program in an IDE or as a JAR file.

</br>

**Running from an IDE:**
1. Clone the repository
2. Open the project in your IDE
3. Either run the `Main.java` file or start the program from the the command line with `$ mvn javafx:run`

</br>

**Running as a JAR file:**
1. Either download the JAR file from the releases or build it yourself with `$ mvn clean compile package`
2. Run the JAR file with `$ java -jar purchase-planner-1.0.0-jar-with-depencencies.jar`
3. You will be prompted to choose an installation directory. The program will create a folder with the necessary files in the chosen directory.

</br>

<h3> Usage </h3>
The purchase planner has a couple of main features:

<h4>Inventory management</h4>

![The inventory page](/assets/inventory-main.png)

The inventory management page allows you to add, remove and update items in your inventory. 

Adding items to the inventory is as simple as clicking the "plus" button in the top right and filling out information about expiration date, unit and quantity. 

![Inventoryitem add popup](/assets/inventory-add.png)

If an item is missing, you can add it by clicking the "missing an item" text at the bottom of the popup.

![Item add popup](/assets/inventory-item-add.png)

Removing and updating items is done by clicking on an item in the inventory. This will open a popup where you can update the information about the item, or delete it. 

![Item update popup](/assets/inventory-popup.png)

Changing the image of an item is also possible by clicking the "change image" button. The effect will not apply until you reopen the inventory page, of if you are running from some specific IDEs (such as IntelliJ), you may need to restart the program.

<h4>Cookbook</h4>

![The cookbook page](/assets/cookbook-main.png)

The cookbook page allows you to add, remove and update recipes. 

Adding recipies is done in a similar way to adding items to the inventory, but you are now required to add a list of ingredients, cooking time and category.

![Recipe add popup](/assets/cookbook-add.png)

Changing steps, ingredients and information about the recipe is done by clicking on the recipe in the cookbook. This will open a similar popup to the inventory page, but with more pages for different options.

![Recipe update popup page 1](/assets/cookbook-popup-1.png)

![Recipe update popup page 2](/assets/cookbook-popup-2.png)

![Recipe update popup page 3](/assets/cookbook-popup-3.png)



<h4>Planning</h4>

![The planning page](/assets/plan-main.png)

The planning page allows you to plan your meals for the week. You can add recipes to a specific day of the coming week.

![Plan add popup](/assets/plan-add.png)


<h4>Shopping list</h4>

![The shopping list page](/assets/shopping-main.png)

The shopping list page is a page for you to keep track of what you need to buy. You can add items to the shopping list by clicking the "plus" button in the top right.

![Shopping add popup](/assets/shopping-add.png)

When you have bought an item, you can click on the checkbox button on the right to mark it as completed.

![Shopping list completed](/assets/shopping-items-completed.png)

When you have finished shopping you can click the "clear all completed" button to remove all items that are marked as completed, and add them to the inventory.

![Items have been added to the inventory](/assets/shopping-items-added.png)


<h4>Cooking mode</h4>

![The cooking mode page](/assets/cooking-main.png)

When you have a meal planned for the day, you can enter cooking mode. This will show you a set by step guide on how to prepare the meal, as outlined in the recipe.

![Cooking mode](/assets/cooking-step.png)


<h3> Troubleshooting </h3>
If you encounter any issues with connecting to the database please refer to the following steps:

1. Try running the program from an IDE. This should clear the java preferences and allow you to connect to the database from the resources folder. This should also allow you to set the installation directory again when you run the program as a JAR file.
2.  If you are still having issues, try manually clearing the java preferences.

package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.demo.util.VerifyInput;

/**
 * This class is representing an instruction for a recipe.
 */
public class RecipeStep implements Storable {
  private int step_id;
  private int recipe_id;
  private int step_number;
  private String instruction;

  /**
   * Constructor for the RecipeStep class. When creating a new recipe step, the id is not known.
   *
   * @param recipe_id the id of the recipe
   * @param step_number the number of the step
   * @param instruction the instruction of the step
   */
  public RecipeStep(int step_id, int recipe_id, int step_number, String instruction) {
    this(recipe_id, step_number, instruction);
    VerifyInput.verifyPositiveNumberMinusOneNotAccepted(step_id, "step_id");
    this.step_id = step_id;
  }

  /**
   * Constructor for the RecipeStep class. When creating a new recipe step, the id is not known.
   *
   * @param recipe_id the id of the recipe
   * @param step_number the number of the step
   * @param instruction the instruction of the step
   */
  public RecipeStep(int recipe_id, int step_number, String instruction) {
    VerifyInput.verifyNotEmpty(instruction, "instruction");
    VerifyInput.verifyPositiveNumberMinusOneNotAccepted(step_number, "step_number");
    VerifyInput.verifyPositiveNumberMinusOneNotAccepted(recipe_id, "recipe_id");
    this.recipe_id = recipe_id;
    this.step_number = step_number;
    this.instruction = instruction;
  }

  /**
   * Getter for the step id.
   *
   * @return the step id
   */
  public int getStepId() {
    return step_id;
  }

  /**
   * Getter for the recipe id.
   *
   * @return the recipe id
   */
  public int getRecipeId() {
    return recipe_id;
  }

  /**
   * Getter for the step number.
   *
   * @return the step number
   */
  public int getStepNumber() {
    return step_number;
  }

  /**
   * Getter for the instruction.
   *
   * @return the instruction
   */
  public String getInstruction() {
    return instruction;
  }

  /**
   * This method returns the attributes of the recipe step.
   *
   * @return the attributes of the recipe step
   */
  @Override
  public List<String> getAttributes() {
    List<String> attributes = new ArrayList<>();
    attributes.add(String.valueOf(step_id));
    attributes.add(String.valueOf(recipe_id));
    attributes.add(String.valueOf(step_number));
    attributes.add(instruction);
    return attributes;
  }

  /**
   * This method returns the attribute names of the recipe step.
   *
   * @return the attribute names of the recipe step
   */
  @Override
  public List<String> getAttributeNames() {
    List<String> attributeNames = new ArrayList<>();
    attributeNames.add("step_id");
    attributeNames.add("recipe_id");
    attributeNames.add("step_number");
    attributeNames.add("instruction");
    return attributeNames;
  }

  /**
   * This method returns the id of the recipe step.
   *
   * @return the id of the recipe step
   */
  @Override
  public int getId() {
    return getStepId();
  }

  /**
   * This method returns the name of the id.
   *
   * @return the name of the id
   */
  @Override
  public String getIdName() {
    return "step_id";
  }
}

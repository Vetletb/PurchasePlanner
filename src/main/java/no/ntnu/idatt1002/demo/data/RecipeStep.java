package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.demo.util.VerifyInput;

/**
 * This class is representing an instruction for a recipe.
 */
public class RecipeStep implements Storable {
  private int stepId;
  private int recipeId;
  private int stepNumber;
  private String instruction;

  /**
   * Constructor for the RecipeStep class. When creating a new recipe step, the id
   * is not known.
   *
   * @param recipeId    the id of the recipe
   * @param stepNumber  the number of the step
   * @param instruction the instruction of the step
   */
  public RecipeStep(int stepId, int recipeId, int stepNumber, String instruction) {
    this(recipeId, stepNumber, instruction);
    VerifyInput.verifyPositiveNumberZeroNotAccepted(stepId, "step_id");
    this.stepId = stepId;
  }

  /**
   * Constructor for the RecipeStep class. When creating a new recipe step, the id
   * is not known.
   *
   * @param recipeId    the id of the recipe
   * @param stepNumber  the number of the step
   * @param instruction the instruction of the step
   */
  public RecipeStep(int recipeId, int stepNumber, String instruction) {
    VerifyInput.verifyNotEmpty(instruction, "instruction");
    VerifyInput.verifyPositiveNumberMinusOneNotAccepted(stepNumber, "step_number");
    VerifyInput.verifyPositiveNumberMinusOneNotAccepted(recipeId, "recipe_id");
    this.recipeId = recipeId;
    this.stepNumber = stepNumber;
    this.instruction = instruction;
  }

  /**
   * Getter for the step id.
   *
   * @return the step id
   */
  public int getStepId() {
    return stepId;
  }

  /**
   * Getter for the recipe id.
   *
   * @return the recipe id
   */
  public int getRecipeId() {
    return recipeId;
  }

  /**
   * Getter for the step number.
   *
   * @return the step number
   */
  public int getStepNumber() {
    return stepNumber;
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
   * Returns the attributes of the recipe step.
   *
   * @return the attributes of the recipe step
   */
  @Override
  public List<String> getAttributes() {
    List<String> attributes = new ArrayList<>();
    attributes.add(String.valueOf(recipeId));
    attributes.add(String.valueOf(stepNumber));
    attributes.add(instruction);
    return attributes;
  }

  /**
   * Returns the attribute names of the recipe step.
   *
   * @return the attribute names of the recipe step
   */
  @Override
  public List<String> getAttributeNames() {
    List<String> attributeNames = new ArrayList<>();
    attributeNames.add("recipe_id");
    attributeNames.add("step_number");
    attributeNames.add("instructions");
    return attributeNames;
  }

  /**
   * Returns the id of the recipe step.
   *
   * @return the id of the recipe step
   */
  @Override
  public int getId() {
    return getStepId();
  }

  /**
   * Returns the name of the id.
   *
   * @return the name of the id
   */
  @Override
  public String getIdName() {
    return "step_id";
  }
}

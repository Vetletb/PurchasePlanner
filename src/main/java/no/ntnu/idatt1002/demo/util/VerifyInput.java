package no.ntnu.idatt1002.demo.util;

/**
 * Class for verifying input in the data package. Includes checks for common
 * things like checking if an input is empty.
 */
public class VerifyInput {

  /**
   * Checks if a string is null or empty
   *
   * @param input     the string to be checked
   * @param parameter the name of the parameter
   * @throws IllegalArgumentException if the input is null or blank
   */
  public static void verifyNotEmpty(String input, String parameter) throws IllegalArgumentException {
    if (input == null || input.isBlank()) {
      throw new IllegalArgumentException("The input for the parameter '" + parameter + "' cannot be null or blank");
    }
  }

  /**
   * Checks if an object is null
   *
   * @param input     the object to be checked
   * @param parameter the name of the parameter
   * @throws IllegalArgumentException if the object is null
   */
  public static void verifyNotNull(Object input, String parameter) {
    if (input == null) {
      throw new IllegalArgumentException("The input for the parameter '" + parameter + "' cannot be null");
    }
  }

  /**
   * Checks if an int is a positive number or minus one
   *
   * @param input     the int to be checked
   * @param parameter the name of the parameter
   * @throws IllegalArgumentException if the int is not a positive number or minus
   *                                  one
   */
  public static void verifyPositiveNumberMinusOneAccepted(int input, String parameter) {
    if (input == 0 || input <= -1) {
      throw new IllegalArgumentException(
          "The input for the parameter '" + parameter + "' must be a positive number or minus one");
    }
  }

  /**
   * Checks if an int is a positive number
   *
   * @param input     the int to be checked
   * @param parameter the name of the parameter
   * @throws IllegalArgumentException if the int is not a positive number
   */
  public static void verifyPositiveNumberMinusOneNotAccepted(int input, String parameter) {
    if (input <= 0) {
      throw new IllegalArgumentException("The input for the parameter '" + parameter + "' must be a positive number");
    }
  }

  public static void verifyPositiveNumberZeroNotAccepted(int input, String parameter) {
    if(input < 1) {
      throw new IllegalArgumentException("The input for the parameter '" + parameter + "' must be a positive number equal to or greater than 1");
    }
  }

  /**
   * Checks if an int(date) has the correct length of six figures
   *
   * @param input     the int(date) to be checked
   * @param parameter the name of the parameter
   * @throws IllegalArgumentException if the int(date) does not have six figures
   */
  public static void verifyDateLength(int input, String parameter) {
    if (Integer.toString(input).length() != 8) {
      throw new IllegalArgumentException("The input for the parameter '" + parameter + "' must have eight figures");
    }
  }

  /**
   * Extracts the year from an int(date)
   *
   * @param input the int(date) to extract the year from
   * @return the year
   */
  private static int extractYear(int input) {
    String yearString = Integer.toString(input);
    return Integer.parseInt(yearString.substring(0, 4));
  }

  /**
   * Extracts the month from an int(date)
   *
   * @param input the int(date) to extract the month from
   * @return the month
   */
  private static int extractMonth(int input) {
    String monthString = Integer.toString(input);
    return Integer.parseInt(monthString.substring(4, 6));
  }

  /**
   * Extracts the day from an int(date)
   *
   * @param input the int(date) to extract the day from
   * @return the day
   */
  private static int extractDay(int input) {
    String dayString = Integer.toString(input);
    return Integer.parseInt(dayString.substring(6, 8));
  }

  /**
   * Checks if a year is a leap year
   *
   * @param year the year to be checked
   * @return true if the year is a leap year, false if not
   */
  private static boolean isLeapYear(int year) {
    return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
  }

  /**
   * Checks if the month in an int(date) is between 01 and 12
   *
   * @param input the int(date) to be checked
   * @param parameter the name of the parameter
   * @throws IllegalArgumentException if the month is not between 01 and 12
   */
  public static void verifyDateMonth(int input, String parameter) {
    int month = extractMonth(input);

    if(month < 1 || month > 12) {
      throw new IllegalArgumentException("In the input for the parameter '" + parameter + "', the 3rd and 4th figure must be a number between 01 and 12");
    }
  }

  /**
   * Checks if the day in an int(date) is between 01 and 31
   *
   * @param input the int(date) to be checked
   * @param parameter the name of the parameter
   * @throws IllegalArgumentException if the day is not between 01 and 31
   */
  public static void verifyDateDay(int input, String parameter) {
    int year = extractYear(input);
    int month = extractMonth(input);
    int day = extractDay(input);

    // Checks if the day is between 01 and 31
    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
      if (day < 1 || day > 31) {
        throw new IllegalArgumentException("In the input for the parameter '" + parameter + "', the 5th and 6th figure must be a number between 01 and 31");
      }
      // Checks if the day is between 01 and 30
    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
      if (day < 1 || day > 30) {
        throw new IllegalArgumentException("In the input for the parameter '" + parameter + "', the 5th and 6th figure must be a number between 01 and 30");
      }
      // Checks if the day is between 01 and 29 or 01 and 28 depending on if it is a leap year
    } else if (month == 2) {
      verifyDayForFebruary(day, year, parameter);
    }
  }

  /**
   * Checks if the day in an int(date) is between 01 and 29 or 01 and 28 depending on if it is a leap year
   *
   * @param day the day to be checked
   * @param year the year to be checked
   * @param parameter the name of the parameter
   * @throws IllegalArgumentException if the day is not between 01 and 29 or 01 and 28 depending on if it is a leap year
   */
  private static void verifyDayForFebruary(int day, int year, String parameter) {
    if (isLeapYear(year)) {
      if (day < 1 || day > 29) {
        throw new IllegalArgumentException("In the input for the parameter '" + parameter + "', the 5th and 6th figure must be a number between 01 and 29");
      }
    } else {
      if (day < 1 || day > 28) {
        throw new IllegalArgumentException("In the input for the parameter '" + parameter + "', the 5th and 6th figure must be a number between 01 and 28");
      }
    }
  }


  /**
   * Checks if an int(date) has the correct length of eight figures or is zero or
   * minus one
   *
   * @param input     the int(date) to be checked
   * @param parameter the name of the parameter
   * @throws IllegalArgumentException if the int(date) does not have eight figures
   *                                  or is zero or minus one
   */
  public static void verifyDateZeroAndMinusOneAccepted(int input, String parameter) {
    if ((Integer.toString(input).length() != 8) && input != 0 && input != -1) {
      throw new IllegalArgumentException("The input for the parameter '" + parameter + "' must have eight figures");
    }
  }
}

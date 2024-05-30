package sortingnetworks;

public class NumberInputValidator {
    public static boolean isValidInput(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        String[] numbersStr = input.split(",");
        for (String numStr : numbersStr) {
            try {
                Integer.parseInt(numStr.trim());
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return input.matches("(-?\\\\d+,\\\\s*)*-?\\\\d+");
    }
}

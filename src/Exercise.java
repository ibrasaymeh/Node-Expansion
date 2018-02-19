import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise {

    private static final String DELIMITER_COMMA = ",";
    private static final String DELIMITER_HYPHEN = "-";
    private static final String NODE_NAME_REGEX = "[A-Za-z0-9]+(?![^\\[]*\\])\\w*";
    private static final String BRACKETED_SECTION_REGEX = "[A-Za-z0-9,-]+(?=[^\\[]*\\])\\w*";
    private static final String INSTRUCTION_MESSAGE = "Please input a node list string:";

    /**
     * Entry point to the application. Accepts user input and prints the output
     * to the console, per the spec.
     */
    public static void main(String[] args) {

        String input = readUserInput();
        List<String> output = evaluate(input);
        System.out.println(output);

    }

    /**
     * Handles the user input via a scanner. Only accepts one line of input.
     */
    private static String readUserInput() {

        Scanner sc = new Scanner(System.in);
        String line = null;
        System.out.println(INSTRUCTION_MESSAGE);
        try {
            line = sc.nextLine();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("ERROR: Invalid input, terminating...");
        }
        sc.close();
        return line;

    }

    /**
     * Public method that handles basic error checking and parsing the input.
     *
     * Made public for the unit tests.
     */
    public static List<String> evaluate(String input) {

        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("ERROR: Empty/null input string.");
        }

        List<String> mergedNodesAndBrackets = getIsolatedNodesAndBrackets(input);
        return parse(mergedNodesAndBrackets);

    }

    /**
     * Matches the input with a regex and segregates the results.
     * Checks for errors.
     */
    private static List<String> getIsolatedNodesAndBrackets(String input) {

        List<String> nodeNames = new ArrayList<>();
        List<String> bracketedSections = new ArrayList<>();

        Pattern pattern = Pattern.compile(NODE_NAME_REGEX);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            nodeNames.add(matcher.group());
        }

        pattern = Pattern.compile(BRACKETED_SECTION_REGEX);
        matcher = pattern.matcher(input);
        while (matcher.find()) {
            bracketedSections.add(matcher.group());
        }

        if (bracketedSections.size() > nodeNames.size()) {
            throw new IllegalArgumentException("ERROR: Regex could not parse an invalid/missing node name.");
        } else if (nodeNames.size() - bracketedSections.size() > 1) {
            throw new IllegalArgumentException("ERROR: Regex could not parse an invalid bracketed section.");
        } else if (input.contains("[]")) {
            throw new IllegalArgumentException("ERROR: Regex could not parse an empty bracketed section.");
        }

        return getMergedNodesAndBrackets(nodeNames, bracketedSections);

    }

    /**
     * Merges the node names and the bracketed sections into a unified list, for easier processing.
     */
    private static List<String> getMergedNodesAndBrackets(List<String> nodeNames, List<String> bracketedSections) {

        List<String> mergedNodesAndBrackets = new ArrayList<String>();

        for(int i=0; i < bracketedSections.size(); i++) {
            mergedNodesAndBrackets.add(nodeNames.get(i));
            mergedNodesAndBrackets.add(bracketedSections.get(i));
        }

        if (nodeNames.size()-1 == bracketedSections.size()) {
            mergedNodesAndBrackets.add(nodeNames.get(nodeNames.size()-1));
        }

        return mergedNodesAndBrackets;

    }

    /**
     * Handles several edge cases (node name without a bracketed section, invalid
     * ranges, etc) while doing the bulk of the parsing to evaluate the complete
     * expanded node list.
     */
    private static List<String> parse(List<String> mergedNodesAndBrackets) {

        List<String> results = new ArrayList<>();
        int parsedElementsAfterPreviousIteration = 0;

        for (int i=0; i < mergedNodesAndBrackets.size(); i+=2) {

            if(i == mergedNodesAndBrackets.size() -1) {
                String suffixNodeName = mergedNodesAndBrackets.get(i);

                if (results.isEmpty()) {
                    results.add(suffixNodeName);
                } else {
                    for (int j=0; j < results.size(); j++) {
                        results.set(j, results.get(j) + suffixNodeName);
                    }
                }

                break;
            }

            String nodeName = mergedNodesAndBrackets.get(i);
            String bracketedSection = mergedNodesAndBrackets.get(i+1);
            String[] bracketedSectionSplitted = bracketedSection.split(DELIMITER_COMMA);

            for (String bracketedSectionElement : bracketedSectionSplitted) {
                String[] ranges = bracketedSectionElement.split(DELIMITER_HYPHEN);
                if (null == ranges || ranges.length > 2 || ranges.length < 1) {
                    throw new IllegalArgumentException("ERROR: Invalid range found: " + bracketedSectionElement);
                }

                String startRangeStr = ranges[0];
                String endRangeStr = (ranges.length == 2) ? ranges[1] : startRangeStr;

                int startRange = 0;
                int endRange = 0;

                try {
                    startRange = Integer.parseInt(startRangeStr);
                    endRange = Integer.parseInt(endRangeStr);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("ERROR: Non-numeric ranges found: " + startRangeStr
                            + " and/or " + endRangeStr);
                }

                if (endRange < startRange) {
                    throw new IllegalArgumentException("ERROR: Start range is larger than end range: "
                            + startRange);
                }

                for (int j = startRange; j <= endRange; j++) {
                    if (i == 0) {
                        results.add(nodeName + j);
                    } else {
                        for (int k = 0; k < parsedElementsAfterPreviousIteration; k++) {
                            results.add(results.get(k) + nodeName + j);
                        }
                    }
                }
            }

            for (int j=0; j < parsedElementsAfterPreviousIteration; j++) {
                results.remove(0);
            }

            parsedElementsAfterPreviousIteration = results.size();

        }

        Collections.sort(results);
        return results;

    }

}
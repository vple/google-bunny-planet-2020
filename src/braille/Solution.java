package braille;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Solution {
    private static final int SPACE = 0; // 000000
    private static final int CAPITAL_MARK = 32; // 000001
    // Probably makes more sense to directly have this go to a string.
    private static final Map<Character, Integer> CHAR_INT_MAPPING;

    static {
        // https://en.wikipedia.org/wiki/Braille#Derivation
        Map<Character, Integer> mapping = new HashMap<>();
        // A-J
        mapping.put('a', 1);
        mapping.put('b', 1 + 2);
        mapping.put('c', 1 + 8);
        mapping.put('d', 1 + 8 + 16);
        mapping.put('e', 1 + 16);
        mapping.put('f', 1 + 2 + 8);
        mapping.put('g', 1 + 2 + 8 + 16);
        mapping.put('h', 1 + 2 + 16);
        mapping.put('i', 2 + 8);
        mapping.put('j', 2 + 8 + 16);

        // K-T
        for (int i = 0; i < 10; i++) {
            int reference = mapping.get((char)('a' + i));
            char c = (char)('a' + i + 10);
            mapping.put(c, reference + 4);
        }

        // W
        mapping.put('w', 2 + 8 + 16 + 32);

        // U-V, X-Z
        for (int i = 0; i < 5; i++) {
            int reference = mapping.get((char)('a' + i));
            char c;
            if (i < 2) {
                c = (char)('a' + i + 20);
            } else {
                // We skipped W.
                c = (char)('a' + i + 21);
            }
            mapping.put(c, reference + 4 + 32);
        }

        CHAR_INT_MAPPING = mapping;
    }

    public static String solution(String s) {
        List<Integer> intRepresentation = new ArrayList<>();
        for (char c : s.toCharArray()) {
            if (' ' == c) {
                intRepresentation.add(SPACE);
                continue;
            }

            if (Character.isUpperCase(c)) {
                intRepresentation.add(CAPITAL_MARK);
            }

            char lower = Character.toLowerCase(c);
            intRepresentation.add(char2Int(lower));
        }

        String result =
                intRepresentation.stream()
                        .map(Solution::int2Braille)
                        .collect(Collectors.joining(""));

        System.out.println(s);
        System.out.println(result);
        return result;
    }

    // https://en.wikipedia.org/wiki/Braille#Derivation
    private static int char2Int(char lowercaseCharacter) {
        return CHAR_INT_MAPPING.get(lowercaseCharacter);
    }

    private static String int2Braille(int value) {
        String binary = Integer.toBinaryString(value);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6 - binary.length(); i++) {
            sb.append('0');
        }
        sb.append(binary);
        sb.reverse();
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
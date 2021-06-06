package server;

import Demo.Calc;
import Demo.Line;
import com.zeroc.Ice.Current;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class CalcI implements Calc {

    public static String printTimestamp() {
        return "[" + new Timestamp(System.currentTimeMillis()) + "] ";
    }


    @Override
    public float lineLength(Line l, Current current) {
        System.out.println(printTimestamp() +
                "LINE LENGTH: Line (" + l.x1 + "," + l.y1 + ") -> (" + l.x2 + "," + l.y2 + ").");
        float length = (float) Math.sqrt(Math.pow(l.x1 - l.x2, 2) + Math.pow(l.y1 - l.y2, 2));
        System.out.println(printTimestamp() + "The length of the line is " + length + ".");
        return length;
    }

    @Override
    public float[] squareSequence(float[] s, Current current) {
        System.out.println(printTimestamp() + "SQUARE SENTENCE: Input Sequence = " + Arrays.toString(s) + ".");
        float[] squares = s.clone();
        for (int i = 0; i < squares.length; i++) {
            squares[i] = (float) Math.pow(squares[i], 2);
        }
        System.out.println(printTimestamp() + "Output Sequence = " + Arrays.toString(squares) + ".");
        return squares;
    }

    @Override
    public Map<String, Integer> countLetters(String sentence, Current current) {
        System.out.println(printTimestamp() + "COUNT LETTERS: Input word = '" + sentence + "'.");
        Map<String, Integer> count = new HashMap<>();
        for (int i = 0; i < sentence.length(); i++) {
            String lowerCase = Character.toString(sentence.charAt(i)).toLowerCase();
            int c = count.get(lowerCase) == null ?
                    0 : count.get(lowerCase);
            count.put(lowerCase,
                    c + 1);
        }
        System.out.print(printTimestamp() + "Letter count =");
        for (String key : count.keySet()) {
            System.out.print(" '" + key + "': " + count.get(key));
        }
        System.out.println();
        return count;
    }
}

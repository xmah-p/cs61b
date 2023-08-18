package gh2;

import deque.ArrayDeque;
import deque.MaxArrayDeque;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
    private static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    private static double freqOf(char ch) {
        return 440.0 * Math.pow(2, (keyboard.indexOf(ch) - 24) / 12.0);
    }

    public static final ArrayDeque<GuitarString> GUITAR = new ArrayDeque<>();

    public static void main(String[] args) {

        for (char ch : keyboard.toCharArray())
            GUITAR.addLast(new GuitarString(freqOf(ch)));

        while (true) {

            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index != -1)
                    GUITAR.get(index).pluck();
            }

            double sample = 0.0;
            for (GuitarString str : GUITAR) sample += str.sample();

            StdAudio.play(sample);

            for (GuitarString str : GUITAR) str.tic();
        }
    }
}

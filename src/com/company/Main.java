package com.company;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final AtomicInteger counter3 = new AtomicInteger();
    private static final AtomicInteger counter4 = new AtomicInteger();
    private static final AtomicInteger counter5 = new AtomicInteger();
    private static final String[] texts = new String[100_000];

    public static void main(String[] args) throws InterruptedException {
        Thread palindrome = new Thread(Main::countPalindromes);
        Thread oneLetter = new Thread(Main::countOneLetterWords);
        Thread incrementLetters = new Thread(Main::countIncrementLettersWords);

        Random random = new Random();
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        palindrome.start();
        oneLetter.start();
        incrementLetters.start();

        palindrome.join();
        oneLetter.join();
        incrementLetters.join();

        System.out.println("Красивых слов с длинной 3: " + counter3.get() + " шт");
        System.out.println("Красивых слов с длинной 4: " + counter4.get() + " шт");
        System.out.println("Красивых слов с длинной 5: " + counter5.get() + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static void countPalindromes() {
        for (var i = 0; i < texts.length; i++) {
            boolean isPalindrome = true;
            int textLength = texts[i].length();
            for (var j = 0; j < textLength / 2; j++) {
                if (texts[i].charAt(j) != texts[i].charAt(textLength - j - 1)) {
                    isPalindrome = false;
                    break;
                }
            }
            if (isPalindrome) {
                incrementCounts(texts[i].length());
            }
        }
    }

    private static void countOneLetterWords() {
        for (int i = 0; i < texts.length; i++) {
            boolean isOneLetter = true;
            var letter = texts[i].charAt(0);
            for (var j = 1; j < texts[i].length(); j++) {
                if (letter != texts[i].charAt(j)) {
                    isOneLetter = false;
                    break;
                }
            }
            if (isOneLetter) {
                incrementCounts(texts[i].length());
            }
        }
    }

    private static void countIncrementLettersWords() {
        for (int i = 0; i < texts.length; i++) {
            boolean isIncrementLetters = true;
            for (int j = 0; j < texts[i].length() - 1; j++) {
                if (texts[i].charAt(j + 1) < texts[i].charAt(j)) {
                    isIncrementLetters = false;
                    break;
                }
            }
            if (isIncrementLetters) {
                incrementCounts(texts[i].length());
            }
        }
    }

    private static void incrementCounts(int length) {
        switch (length) {
            case 3:
                counter3.incrementAndGet();
                break;
            case 4:
                counter4.incrementAndGet();
                break;
            case 5:
                counter5.incrementAndGet();
                break;
            default:
                break;
        }
    }
}

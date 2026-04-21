package com.final_project.blog_service.utile;


public class ReadTimeCalculator {

    private static final int WORDS_PER_MINUTE = 200;

    public static int calculateReadTime(String content) {
        if (content == null || content.isEmpty()) {
            return 1;
        }

        String[] words = content.split("\\s+");
        int wordCount = words.length;

        int readTime = (wordCount + WORDS_PER_MINUTE - 1) / WORDS_PER_MINUTE;

        return Math.max(1, readTime);
    }

    public static String formatReadTime(int minutes) {
        if (minutes <= 1) {
            return "1 min read";
        }
        return minutes + " min read";
    }
}

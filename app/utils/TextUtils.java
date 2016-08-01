package utils;


import io.github.gitbucket.markedj.Marked;
import io.github.gitbucket.markedj.Options;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//
//import org.pegdown.PegDownProcessor;

/**
 * Created by dru on 23.01.2015.
 */
public class TextUtils {

   public static final int TRUNCATED_CHAR_COUNT = 500;
   public static final int TRUNCATED_LINE_COUNT = 10;

   private final static String NON_THIN = "[^iIl1\\.,']";

   private static int textWidth(String str) {
      return (int) (str.length() - str.replaceAll(NON_THIN, "").length() / 2);
   }


   public static String ellipsize(String text, int maxChar) {

      //TODO line count

      if (textWidth(text) <= maxChar)
         return text;

      // Start by chopping off at the word before max
      // This is an over-approximation due to thin-characters...
      int end = text.lastIndexOf(' ', maxChar - 3);

      // Just one long word. Chop it off.
      if (end == -1)
         return text.substring(0, maxChar-3) + "...";

      // Step forward as long as textWidth allows.
      int newEnd = end;
      do {
         end = newEnd;
         newEnd = text.indexOf(' ', end + 1);

         // No more spaces.
         if (newEnd == -1)
            newEnd = text.length();

      } while (textWidth(text.substring(0, newEnd) + "...") < maxChar);

      return text.substring(0, end) + "...";
   }


   //TODO if images.count>1 reduce it. use jsoup.
   //TODO not working properly
   public static String generateTruncatedHtmlPreview(String html) {

      return TextUtils.ellipsize(html, TRUNCATED_CHAR_COUNT);
   }

   /**
    *
    * @param markdown
    * @return html string
     */
   public static String markdownToHtml(String markdown) {

      Options options = new Options();
      options.setGfm(true);
      options.setTables(true);
      options.setPedantic(false);
      options.setSanitize(false);

      return Marked.marked(markdown, options);
   }

   public static String toReadableDate(Date date) {
      final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm");
      return sdf.format(date);
   }
}

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


/**
 * Created by dru on 23.01.2015.
 */
public class TextUtils {

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

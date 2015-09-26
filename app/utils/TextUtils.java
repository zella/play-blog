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

   public static final int TRUNCATED_CHAR_COUNT = 250;
   public static final int TRUNCATED_WORD_COUNT = 200;
   public static final int TRUNCATED_LINE_COUNT = 20;

   /**
    * Copyright (c) Django Software Foundation and individual contributors.
    * All rights reserved.
    * <p>
    * Copyright (c) 2011 Masood Behabadi <masood@dentcat.com>
    * <p>
    * Redistribution and use in source and binary forms, with or without modification,
    * are permitted provided that the following conditions are met:
    * <p>
    * 1. Redistributions of source code must retain the above copyright notice,
    * this list of conditions and the following disclaimer.
    * <p>
    * 2. Redistributions in binary form must reproduce the above copyright
    * notice, this list of conditions and the following disclaimer in the
    * documentation and/or other materials provided with the distribution.
    * <p>
    * 3. Neither the name of Django nor the names of its contributors may be used
    * to endorse or promote products derived from this software without
    * specific prior written permission.
    * <p>
    * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
    * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
    * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
    * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
    * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
    * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
    * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
    * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
    * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
    */

   public static String truncateHtmlWords(String html, int length) {
      if (length <= 0)
         return new String();

      List<String> html4Singlets = Arrays.asList(
          "br", "col", "link", "base", "img",
          "param", "area", "hr", "input");
      // Set up regular expressions
      Pattern pWords = Pattern.compile("&.*?;|<.*?>|(\\w[\\w-]*)");
      Pattern pTag = Pattern.compile("<(/)?([^ ]+?)(?: (/)| .*?)?>");
      Matcher mWords = pWords.matcher(html);
      // Count non-HTML words and keep note of open tags
      int endTextPos = 0;
      int words = 0;
      List<String> openTags = new ArrayList<String>();
      while (words <= length) {
         if (!mWords.find())
            break;
         if (mWords.group(1) != null) {
            // It's an actual non-HTML word
            words += 1;
            if (words == length)
               endTextPos = mWords.end();
            continue;
         }
         // Check for tag
         Matcher tag = pTag.matcher(mWords.group());
         if (!tag.find() || endTextPos != 0)
            // Don't worry about non tags or tags after our
            // truncate point
            continue;
         String closingTag = tag.group(1);
         // Element names are always case-insensitive
         String tagName = tag.group(2).toLowerCase();
         String selfClosing = tag.group(3);
         if (closingTag != null) {
            int i = openTags.indexOf(tagName);
            if (i != -1)
               openTags = openTags.subList(i + 1, openTags.size());
         } else if (selfClosing == null && !html4Singlets.contains(tagName))
            openTags.add(0, tagName);
      }

      if (words <= length)
         return html;
      StringBuilder out = new StringBuilder(html.substring(0, endTextPos));
      for (String tag : openTags)
         out.append("");

      return out.toString();
   }

   public static String truncateStringLineBreak(String what, int numberChars) {
      if (what.length() <= numberChars) return what;
      BreakIterator bi = BreakIterator.getLineInstance();
      bi.setText(what);
      int first_after = bi.following(numberChars);
      what = what.substring(0, first_after);
      return what;
   }

   public static String truncateStringLineCount(String what, int wordCount) {

      if (what.length()<= wordCount) return what;

      BreakIterator breakIterator = BreakIterator.getWordInstance();
      breakIterator.setText(what);

      int currentWord = 0;

      int boundaryIndex = breakIterator.first();
      while (boundaryIndex != BreakIterator.DONE && currentWord < wordCount) {
         currentWord ++;
         boundaryIndex = breakIterator.next();
      }

      return what.substring(0, boundaryIndex);
   }

   //TODO if images.count>1 reduce it. use jsoup.
   //TODO not working properly
   public static String generateTruncateHtmlPreview(String markdown, int charCount) {

      Options options = new Options();
      options.setGfm(true);
      options.setTables(true);
      options.setBreaks(true);
      options.setPedantic(false);
      options.setSanitize(false);

      String html = Marked.marked(markdown, options);
      //Temorary. Need time to develop proper html truncater with jsoup
      String truncatedHtml = TextUtils.truncateStringLineCount(html, TRUNCATED_WORD_COUNT);

      truncatedHtml += "<br/>. . . ";
      return truncatedHtml;
   }

   public static String toReadableDate(Date date) {
      final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm");
      return sdf.format(date);
   }
}

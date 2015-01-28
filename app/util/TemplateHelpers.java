package util;

import scala.collection.immutable.Range;

/**
 * Created by dru on 28.01.2015.
 */
public class TemplateHelpers {

    public static Range pagingRange(int totalPages, int buttons, int currentPage) {
        int lowerLimit, upperLimit;
        lowerLimit = upperLimit = Math.min(currentPage, totalPages);
        for (int b = 1; b < buttons && b < totalPages; ) {
            if (lowerLimit > 1) {
                lowerLimit--;
                b++;
            }
            if (b < buttons && upperLimit < totalPages) {
                upperLimit++;
                b++;
            }
        }
        return new Range(lowerLimit, upperLimit+1, 1);
    }

}

package it.unibo.nestedenum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    public static enum Month {
        JANUARY(31), FEBRUARY(28), MARCH(31), APRIL(30), MAY(31), JUNE(30),
        JULY(31), AUGUST(31), SEPTEMBER(30), OCTOBER(31), NOVEMBER(30), DECEMBER(31);

        private final int days;

        private Month(int days){
            this.days = days;
        }

        public static Month fromString(String string) {
            final String upText = string.toUpperCase();
            final Month[] array = Month.values();
            List<Month> matchmMonths = new ArrayList<>();
            for (Month month : array){
                if (month.name().startsWith(upText)) {
                    matchmMonths.add(month);
                }
            }
            if (matchmMonths.size()==1) {
                return matchmMonths.getFirst();
            } 
            throw new IllegalArgumentException("No month matched"+string);
        }
        
        
    }  

    public class SortByMonthOrder implements Comparator<String> {

        @Override 
        public int compare(String first,String second) {
            final int firstOrd = Month.fromString(first).ordinal();
            final int secondOrd = Month.fromString(second).ordinal();
            return (firstOrd-secondOrd);
        }
    } 

    public class SortByDate implements Comparator<String> {

        @Override 
        public int compare(String first,String second){
            final int firstOrd = Month.fromString(first).days;
            final int secondOrd = Month.fromString(second).days;
            return (firstOrd-secondOrd);
        }
    }


    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }
}

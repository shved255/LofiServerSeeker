package ru.shved255;

import java.util.Collection;
import java.util.List;

public class Choice {
    @SuppressWarnings("unchecked")
	public static <T> T choiceList(List<? extends Object> list) {
        boolean isEmpty = list.isEmpty();
        if (isEmpty) {
            return null;
        }
        int size = list.size();
        int maxIndex = size - 1;
        int rand = (int)Math.round(Math.random() * (double)maxIndex);
        Object obj = list.get(rand);
        return (T)obj;
    }

    public static Object choiceCollection(Collection<? extends Object> collection) {
        @SuppressWarnings("rawtypes")
		List list = (List)collection;
        boolean isEmpty = list.isEmpty();
        if (isEmpty) {
            return null;
        }
        int size = list.size();
        int maxIndex = size - 1;
        int rand = (int)Math.round(Math.random() * (double)maxIndex);
        Object obj = list.get(rand);
        return obj;
    }

    public static int choiceRange(Range range) {
        List<Integer> list = range.getList();
        return (Integer)Choice.choiceList(list);
    }

    public static int getRandomInt(int start, int stop) {
        if (stop < start) {
            return 0;
        }
        List<Integer> range = new Range(start, stop + 1).getList();
        return (Integer)Choice.choiceList(range);
    }
    
    public static Long getRandomLong(Long start, Long stop) {
        if (stop < start) {
            return (long) 0;
        }
        List<Integer> range = new Range(start, stop + 1).getList();
        return (Long)Choice.choiceList(range);
    }

    public static boolean getRandomBoolean() {
        int result = Choice.getRandomInt(0, 1);
        return result != 0;
    }

    public static boolean getRandomBoolean(boolean flag) {
        int result = Choice.getRandomInt(0, 1);
        if (result == 0) {
            if (flag) {
                return flag;
            }
            return flag;
        }
        return !flag;
    }

    public static boolean getChance(double chance) {
        int percents = Double.valueOf(chance * 100.0).intValue();
        if (percents >= 100) {
            return true;
        }
        if (percents <= 0) {
            return false;
        }
        int res = percents == 99 ? Choice.getRandomInt(0, 100 - percents) : Choice.getRandomInt(0, 100 - percents - 1);
        return res == 0;
    }
}
 
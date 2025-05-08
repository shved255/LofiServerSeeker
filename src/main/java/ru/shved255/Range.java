package ru.shved255;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Range implements Ranges {
    private Collection<Integer> CollectionRange = new LinkedList<Integer>();
    List<Integer> ListRange = new LinkedList<Integer>();
    private Collection<Long> CollectionRange1 = new LinkedList<Long>();
    private List<Long> ListRange1 = new LinkedList<Long>();

    public Range() {
        int i = 0;
        while (i < 10) {
            this.CollectionRange.add(i);
            this.ListRange.add(i);
            ++i;
        }
    }

    public Range(int stop) {
        int i = 0;
        while (i < stop) {
            this.CollectionRange.add(i);
            this.ListRange.add(i);
            ++i;
        }
    }

    public Range(int start, int stop) {
        int i = start;
        while (i < stop) {
            this.CollectionRange.add(i);
            this.ListRange.add(i);
            ++i;
        }
    }
    
    public Range(Long start, Long stop) {
    	Long i = start;
        while (i < stop) {
            this.CollectionRange1.add(i);
            this.ListRange1.add(i);
            ++i;
        }
    }

    public Range(int start, int stop, int step) {
        int i = start;
        while (i < stop) {
            this.CollectionRange.add(i);
            this.ListRange.add(i);
            i += step;
        }
    }

    public Collection<Integer> getCollection() {
        return this.CollectionRange;
    }

    public List<Integer> getList() {
        return this.ListRange;
    }

    public boolean isEmpty() {
        return this.CollectionRange.isEmpty();
    }

    public boolean equals(Object obj) {
        Range range = (Range)obj;
        return this.ListRange.equals(range.ListRange);
    }
}
 
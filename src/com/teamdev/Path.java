package com.teamdev;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kirill.sidorchuk on 8/7/2016.
 */
class Path {
    List<Integer> indexes = new ArrayList<>();
    Set<Integer> set = new HashSet<>();
    boolean abandoned = false;

    public Path() {
    }

    public Path(List<Integer> indexes) {
        this.indexes = indexes;
        set = new HashSet<>(indexes);
    }

    public boolean contains(int i) {
        return set.contains(i);
    }

    public boolean add(int i) {
        if (!contains(i)) {
            indexes.add(i);
            set.add(i);
            return true;
        }
        return false;
    }

    public Path copy() {
        Path p = new Path();
        p.indexes.addAll(indexes);
        p.set.addAll(set);
        return p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path path = (Path) o;
        return set.equals(path.set);
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }
}

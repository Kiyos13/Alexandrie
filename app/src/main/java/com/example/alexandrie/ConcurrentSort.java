package com.example.alexandrie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ConcurrentSort {

    public static <T extends Comparable<T>> void concurrentSort(String way, final List<T> key, ArrayList<ArrayList<T>> arrayList) {
        // Create a List of indices
        List<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < key.size(); i++)
            indices.add(i);

        // Sort the indices list based on the key
        Collections.sort(indices, new Comparator<Integer>() {
            @Override
            public int compare(Integer i, Integer j) {
                String iKey = (String) key.get(i);
                String jKey = (String) key.get(j);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date iDate = null;
                Date jDate = null;
                try {
                    iDate = dateFormat.parse(iKey);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    jDate = dateFormat.parse(jKey);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if ((iDate != null) && (jDate != null)) {
                    return iDate.compareTo(jDate);
                }
                else {
                    if (way.equals("descending"))
                        return -((String) key.get(i)).toUpperCase().compareTo(((String) key.get(j)).toUpperCase());
                    else
                        return ((String) key.get(i)).toUpperCase().compareTo(((String) key.get(j)).toUpperCase());
                }
            }
        });

        // Create a mapping that allows sorting of the List by N swaps.
        // Only swaps can be used since we do not know the type of the lists
        Map<Integer, Integer> swapMap = new HashMap<Integer, Integer>(indices.size());
        List<Integer> swapFrom = new ArrayList<Integer>(indices.size()), swapTo = new ArrayList<Integer>(indices.size());
        for (int i = 0; i < key.size(); i++) {
            int k = indices.get(i);
            while (i != k && swapMap.containsKey(k))
                k = swapMap.get(k);

            swapFrom.add(i);
            swapTo.add(k);
            swapMap.put(i, k);
        }

        // use the swap order to sort each list by swapping elements
        for (int i = 0; i < arrayList.size(); i++) {
            List<T> list = arrayList.get(i);
            for (int j = 0; j < list.size(); j++)
                Collections.swap(list, swapFrom.get(j), swapTo.get(j));
        }
    }
}
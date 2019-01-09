package com.bellis.server;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class CollectorService {

    private static Set<Integer> list = Collections.synchronizedSet(new HashSet<Integer>());
    public static AtomicInteger duplicate = new AtomicInteger(0);


    public boolean addToList(int number){
        return list.add(number);
    }

    public int count(){
        return list.size();
    }

    public static String printList(){
        return list.toString();
    }
    public static int uniqueCount(){
        return list.size();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customhashmap;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class CustomHashMap {
    
    private class KeyValue {
        private String key;
        private String val;
        public KeyValue next = null;
        
        KeyValue(String k, String v, KeyValue n) {
            key = k;
            val = v;
            next = n;
        }
        
        public String getKey() {
            return key;
        }
        
        public String getValue() {
            return val;
        }
    }
    
    private final List<KeyValue> map = new ArrayList<>();
    private final int BUCKET_SIZE = 10;
    
    public void init() {
        for(int i = 0; i < BUCKET_SIZE; i++) {
            map.add(null);
        }
    }
    
    public CustomHashMap() {
        init();
    }
    
    private int getHash(String key) {
        int hash = key.hashCode();
        hash = abs(hash % BUCKET_SIZE);
        return hash;
    }
    
    public Boolean add(String key, String val) {
        int hash = getHash(key);
        
        try {
            if(map.get(hash) == null) {
                map.set(hash, new KeyValue(key, val, null));
            } else {
                KeyValue temp = new KeyValue(key, val, map.get(hash));
                map.set(hash, temp);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public String get(String key) throws Exception {
        int hash = getHash(key);
        
        if(map.get(hash) != null) {
            KeyValue temp = map.get(hash);
            while(temp != null) {
                if(temp.getKey().equals(key)) {
                    return temp.getValue();
                }
                temp = temp.next;
            }
        }
        
        throw new Exception("Specified key doesn't exists"); 
    }

    public Boolean remove(String key) throws Exception {
        int hash = getHash(key);
        
        try {        
            if(map.get(hash) != null) {
                KeyValue temp = map.get(hash);
                KeyValue parent = null;

                while(temp != null) {
                    if(temp.getKey().equals(key)) {
                        if(parent == null) {
                            map.set(hash, temp.next);
                        } else {
                            parent.next = temp.next;
                            // delete temp
                            temp.next = null;
                        }
                        return true;
                    }
                    temp = temp.next;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CustomHashMap abc = new CustomHashMap();
        
        try {        
            abc.add("abc", "def");
            abc.add("abdefc", "askjfhdjf");

            System.out.println(abc.get("abdefc"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}

package org.microag.loader.data;

import lombok.Data;

import java.util.Hashtable;

@Data
public class Record {
    private String ref;
    private int index;
    private Hashtable<String,String> data = new Hashtable<>();

    public Record(String ref , int index) {
        this.ref = ref;
        this.index = index;
    }

    public String get(String key){
        if(data.containsKey(key)){
            return data.get(key);
        }
        return null;
    }
    public void fillData(String key , String value){
        data.put(key,value);
    }
}

package ArraySetMap;

import java.util.HashMap;

public class MapC{
    private HashMap<String, String> obMap;
    
    public MapC(){
        obMap = new HashMap<>();
        obMap.put("Fruit", "Apple");
        obMap.put("Vegetable", "Carrot");
        obMap.put("Desert", "Cake");

    }

    public HashMap<String, String> getHashMap(){
        return obMap;
    }

}
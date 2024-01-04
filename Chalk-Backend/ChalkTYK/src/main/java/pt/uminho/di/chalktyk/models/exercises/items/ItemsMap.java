package pt.uminho.di.chalktyk.models.exercises.items;

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemsMap extends LinkedHashMap<String,Item> {
    public ItemsMap() {
    }

    public ItemsMap(Map<? extends String, ? extends Item> m) {
        super(m);
    }
}

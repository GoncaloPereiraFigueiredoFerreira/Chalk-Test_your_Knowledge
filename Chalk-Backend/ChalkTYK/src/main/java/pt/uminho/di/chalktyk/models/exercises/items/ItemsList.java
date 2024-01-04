package pt.uminho.di.chalktyk.models.exercises.items;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class ItemsList extends ArrayList<Item> {
    public ItemsList() {
    }

    public ItemsList(@NotNull Collection<? extends Item> c) {
        super(c);
    }

    public ItemsList(int initialCapacity) {
        super(initialCapacity);
    }
}

package pt.uminho.di.chalktyk.models.exercises.items;

import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

public abstract class Item {
    private Integer id;

    public abstract void verifyProperties() throws BadInputException;

    public abstract Item clone();
}
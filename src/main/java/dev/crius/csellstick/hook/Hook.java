package dev.crius.csellstick.hook;

public interface Hook {

    String getName();

    default void init() {}

}

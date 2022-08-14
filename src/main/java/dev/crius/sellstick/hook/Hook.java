package dev.crius.sellstick.hook;

public interface Hook {

    String getName();

    default void init() {}

}

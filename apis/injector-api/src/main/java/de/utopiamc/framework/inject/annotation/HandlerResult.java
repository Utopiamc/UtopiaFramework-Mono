package de.utopiamc.framework.inject.annotation;

@FunctionalInterface
public interface HandlerResult {

    default void handleGlobal() {}

    void handle();

}

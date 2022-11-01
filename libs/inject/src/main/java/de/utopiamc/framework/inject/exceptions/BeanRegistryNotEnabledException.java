package de.utopiamc.framework.inject.exceptions;

public class BeanRegistryNotEnabledException extends RuntimeException {

    public BeanRegistryNotEnabledException() {
    }

    public BeanRegistryNotEnabledException(String message) {
        super(message);
    }

    public BeanRegistryNotEnabledException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanRegistryNotEnabledException(Throwable cause) {
        super(cause);
    }

    public BeanRegistryNotEnabledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

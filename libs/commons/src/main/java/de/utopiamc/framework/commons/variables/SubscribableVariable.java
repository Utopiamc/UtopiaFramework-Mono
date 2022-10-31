package de.utopiamc.framework.commons.variables;

import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * <h1>Subscribable-Variable</h1>
 * <p>
 * Should hold an instance of {@link T} and call all {@link SubscribableVariable#subscribe(Consumer) subscriptions} on an update.
 *
 * @param <T> The type of the instance that should be held.
 * @see Variables#createSubscribableVariable(T)
 */
@API(status = API.Status.STABLE, since = "2")
public interface SubscribableVariable<T> {

    /**
     * Sets the current instance. This will invoke all {@link SubscribableVariable#subscribe(Consumer) subscriptions} and delete the current instance.
     * @param instance The instance that should be updated to.
     */
    void set(@Nullable T instance);

    /**
     * Returns the current instance
     * @return the current instance
     */
    @Nullable T get();

    /**
     * Subscribes to the variable. Consumer gets called when the current instance is updated.
     * @param consumer Gets called when the current instance is updated.
     * @see SubscribableVariable#unsubscribe(Consumer)
     */
    void subscribe(@NotNull Consumer<T> consumer);

    /**
     * Removes a subscription.
     * @param consumer The subscription that should be removed.
     * @see SubscribableVariable#subscribe(Consumer)
     */
    void unsubscribe(@NotNull Consumer<T> consumer);

}

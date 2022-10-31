package de.utopiamc.framework.commons.variables;

import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * <h1>Shared-Subscribable-Variable</h1>
 * <p>
 * Should hold a instance of {@link T} linked to {@link K} and call all {@link SharedSubscribableVariable#subscribe(BiConsumer) subscriptions} on an update.
 *
 * @param <T> The type of the instance that should be held.
 * @see Variables#createSubscribableVariable(T)
 */
@API(status = API.Status.STABLE, since = "2")
public interface SharedSubscribableVariable<K, T> extends SubscribableVariable<T> {

    /**
     * Sets the current instance. This will invoke all {@link SubscribableVariable#subscribe(Consumer) subscriptions} when the instance <i>has changed.</i>
     *
     * @param instance The instance that should be updated to.
     */
    void set(@Nullable T instance);

    /**
     * Sets the instance for the corresponding key. This will invoke all {@link SubscribableVariable#subscribe(Consumer) subscriptions} when the instance <i>has changed.</i>
     * @param key
     * @param instance
     */
    void set(@NotNull K key, @Nullable T instance);

    /**
     * @throws IllegalStateException Can't get instance without key!
     */
    default T get() throws IllegalStateException {
        throw new IllegalStateException("Can't get instance from shared variable. Use SharedSubscribableVariable#get(K) instead.");
    }

    /**
     * Returns the current instance for the corresponding key!
     * @return the current instance for the corresponding key!
     */
    @Nullable T get(@NotNull K key);

    /**
     * Subscribes to the variable. Consumer gets called when an instance is updated.
     *
     * @param consumer Gets called when an instance is updated.
     * @see SubscribableVariable#unsubscribe(Consumer)
     * @see SharedSubscribableVariable#subscribe(BiConsumer)
     * @deprecated Use {@link SharedSubscribableVariable#subscribe(BiConsumer)} instead.
     */
    @Deprecated
    void subscribe(@NotNull Consumer<T> consumer);

    /**
     * Subscribes to the variable. Consumer gets called when an instance is updated. {@link BiConsumer#accept(K, T) K} is null, when all instances are updated.
     *
     * @param consumer Gets called when an instance is updated.
     * @see SharedSubscribableVariable#unsubscribe(BiConsumer)
     */
    void subscribe(@NotNull BiConsumer<K, T> consumer);

    /**
     * Removes a subscription.
     *
     * @param consumer The subscription that should be removed.
     * @see SubscribableVariable#subscribe(Consumer)
     * @see SharedSubscribableVariable#unsubscribe(BiConsumer)
     * @deprecated Use {@link SharedSubscribableVariable#unsubscribe(BiConsumer)} instead.
     */
    @Deprecated
    void unsubscribe(@NotNull Consumer<T> consumer);

    /**
     * Removes a subscription.
     *
     * @param consumer The subscription that should be removed.
     * @see SubscribableVariable#subscribe(Consumer)
     */
    void unsubscribe(@NotNull BiConsumer<K, T> consumer);

}

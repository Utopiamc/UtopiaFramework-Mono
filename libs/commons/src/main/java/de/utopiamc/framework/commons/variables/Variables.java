package de.utopiamc.framework.commons.variables;

import lombok.experimental.UtilityClass;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@UtilityClass
@API(status = API.Status.STABLE, since = "2")
public class Variables {

    /**
     * Creates a new {@link SubscribableVariable} with the specified instance.
     * <p>
     * <b>WARNING:</b> this implementation is not thread-safe!
     * @param instance The instance the {@link SubscribableVariable} should be created with.
     * @return the newly created variable
     * @see SubscribableVariable
     */
    public <T> SubscribableVariable<T> createSubscribableVariable(T instance) {
        return new SimpleSubscribableVariable<>(instance);
    }

    private static final class SimpleSubscribableVariable<T> implements SubscribableVariable<T> {

        private T instance;
        private final Set<Consumer<T>> subscribers;

        public SimpleSubscribableVariable(T instance) {
            this.instance = instance;
            this.subscribers = new HashSet<>();
        }

        private void notifySubscribers() {
            for (Consumer<T> subscriber : subscribers) {
                subscriber.accept(instance);
            }
        }

        @Override
        public void set(@Nullable T instance) {
            this.instance = instance;
            notifySubscribers();
        }

        @Override
        public @Nullable T get() {
            return instance;
        }

        @Override
        public void subscribe(@NotNull Consumer<T> consumer) {
            subscribers.add(consumer);
        }

        @Override
        public void unsubscribe(@NotNull Consumer<T> consumer) {
            subscribers.remove(consumer);
        }
    }

    /**
     * Creates a new {@link SharedSubscribableVariable}. Every new instance gets instantiated with the {@code startInstance}.
     * <p>
     * <b>WARNING:</b> this implementation is not thread-safe!
     * @param startInstance How should every new instance get instantiated.
     * @return the newly created variable
     * @see SharedSubscribableVariable
     */
    public <K, T> SharedSubscribableVariable<K, T> createSharedSubscribableVariable(Function<K, T> startInstance) {
        return new SimpleSharedSubscribableVariable<>(startInstance);
    }

    private static final class SimpleSharedSubscribableVariable<K, T> implements SharedSubscribableVariable<K, T> {

        private Function<K, T> startInstanceResolver;
        private final Map<K, T> instances;
        private final HashSet<BiConsumer<K, T>> subscriptions;

        public SimpleSharedSubscribableVariable(Function<K, T> startInstanceResolver) {
            this.startInstanceResolver = startInstanceResolver;
            this.instances = new HashMap<>();
            this.subscriptions = new HashSet<>();
        }

        private void notifySubscriptions(K key, T instance) {
            for (BiConsumer<K, T> subscription : subscriptions) {
                subscription.accept(key, instance);
            }
        }

        @Override
        public void set(@Nullable T instance) {
            instances.forEach((k, t) -> instances.put(k, instance));
            startInstanceResolver = (k) -> instance;
            notifySubscriptions(null, instance);
        }

        @Override
        public void set(@NotNull K key, @Nullable T instance) {
            if (Objects.equals(instances.get(key), instance)) {
                return;
            }

            instances.put(key, instance);
            notifySubscriptions(key, instance);
        }

        @Override
        public @Nullable T get(@NotNull K key) {
            T t = instances.get(key);
            if (t == null) {
                t = startInstanceResolver.apply(key);
                set(key, t);
            }
            return t;
        }

        @Override
        public void subscribe(@NotNull Consumer<T> consumer) {
            subscribe(new BiConsumerWrapper<>(consumer));
        }

        @Override
        public void subscribe(@NotNull BiConsumer<K, T> consumer) {
            subscriptions.add(consumer);
        }

        @Override
        public void unsubscribe(@NotNull Consumer<T> consumer) {
            unsubscribe(new BiConsumerWrapper<>(consumer));
        }

        @Override
        public void unsubscribe(@NotNull BiConsumer<K, T> consumer) {
            if (consumer instanceof BiConsumerWrapper) {
                BiConsumerWrapper<K, T> wrapper = (BiConsumerWrapper<K, T>) consumer;
                for (BiConsumer<K, T> subscription : subscriptions) {
                    if (subscription.hashCode() == wrapper.hashCode()) {
                        subscriptions.remove(subscription);
                        break;
                    }
                }
            }else {
                subscriptions.remove(consumer);
            }
        }

        private static final class BiConsumerWrapper<K, T> implements BiConsumer<K, T> {

            private final Consumer<T> consumer;

            public BiConsumerWrapper(Consumer<T> consumer) {
                this.consumer = consumer;
            }

            @Override
            public void accept(K k, T t) {
                consumer.accept(t);
            }

            @Override
            public int hashCode() {
                return consumer.hashCode();
            }
        }

    }

}

package de.utopiamc.framework.commons.variables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class SimpleSharedSubscribableVariableTests {

    @Mock
    private Function<String, String> startInstance;

    private SharedSubscribableVariable<String, String> underTest;

    @BeforeEach
    void setUp() {
        this.underTest = Variables.createSharedSubscribableVariable(startInstance);
    }

    @Test
    void itShouldSafeInstanceUnderKey() {
        // given
        String key = "key";
        String newInstance = "new Instance";

        // when
        underTest.set(key, newInstance);

        // then
        assertThat(underTest.get(key)).isEqualTo(newInstance);
    }

    @Test
    void itShouldSafeManyInstancesUnderManyKeys() {
        // given
        String key = "key";
        String key1 = "key1";
        String newInstance = "new Instance";
        String newInstance1 = "new Instance1";

        // when
        underTest.set(key, newInstance);
        underTest.set(key1, newInstance1);

        // then
        assertThat(underTest.get(key)).isEqualTo(newInstance);
        assertThat(underTest.get(key1)).isEqualTo(newInstance1);
    }

    @Test
    void itShouldSetGlobalInstance() {
        // given
        String key = "key";
        String key2 = "key2";
        String globalInstance = "glob";
        underTest.get(key); // consider key as registered
        BiConsumer<String, String> subscriber = Mockito.mock(StringBiConsumer.class);

        // when
        underTest.subscribe(subscriber);
        underTest.set(globalInstance);
        String instance2 = underTest.get(key2);

        // then
        verify(subscriber).accept(null, globalInstance);
        assertThat(instance2).isEqualTo(globalInstance);
        assertThat(underTest.get(key)).isEqualTo(globalInstance);
    }

    @Test
    void itShouldCreateNewInstanceBasedOnStartInstance() {
        // given
        String key = "key";

        // when
        String instance = underTest.get(key);

        // then
        verify(startInstance).apply(key);
        assertThat(instance).isEqualTo(startInstance.apply(key));
    }

    @Test
    void itShouldNotifyOnBiConsumer() {
        // given
        String key = "key";
        String newInstance = "new Instance";
        BiConsumer<String, String> subscriber = Mockito.mock(StringBiConsumer.class);

        // when
        underTest.subscribe(subscriber);
        underTest.set(key, newInstance);

        // then
        verify(subscriber).accept(key, newInstance);
    }

    @Test
    void itShouldNotNotifyOnUnsubscribedBiConsumer() {
        // given
        String key = "key";
        String newInstance = "new Instance";
        BiConsumer<String, String> subscriber = Mockito.mock(StringBiConsumer.class);

        // when
        underTest.subscribe(subscriber);
        underTest.unsubscribe(subscriber);
        underTest.set(key, newInstance);

        // then
        verifyNoInteractions(subscriber);
    }

    @Test
    void itShouldNotNotifyOnSameInstance() {
        // given
        String key = "key";
        String instance = "instance";
        underTest.set(key, instance);
        BiConsumer<String, String> subscriber = Mockito.mock(StringBiConsumer.class);

        // when
        underTest.subscribe(subscriber);
        underTest.set(key, instance);

        // then
        verifyNoInteractions(subscriber);
    }

    @Test
    void itShouldNotifyConsumer() {
        // given
        String key = "key";
        String newInstance = "instance";
        Consumer<String> subscriber = Mockito.mock(StringConsumer.class);

        // when
        underTest.subscribe(subscriber);
        underTest.set(key, newInstance);

        // then
        verify(subscriber).accept(newInstance);
    }

    @Test
    void itShouldNotNotifyUnsubscribedConsumer() {
        // given
        String key = "key";
        String newInstance = "instance";
        Consumer<String> subscriber = Mockito.mock(StringConsumer.class);

        // when
        underTest.subscribe(subscriber);
        underTest.unsubscribe(subscriber);
        underTest.set(key, newInstance);

        // then
        verifyNoInteractions(subscriber);
    }

    private interface StringBiConsumer extends BiConsumer<String, String> {}
    private interface StringConsumer extends Consumer<String> {}

}

package de.utopiamc.framework.commons.variables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

public class SimpleSubscribableVariableTests {

    private static final String START_INSTANCE = "Test";
    private SubscribableVariable<String> underTest;

    @BeforeEach
    void setUp() {
        this.underTest = Variables.createSubscribableVariable(START_INSTANCE);
    }

    @Test
    void itShouldUpdateInstance() {
        // given
        String newInstance = "new Test";

        // when
        underTest.set(newInstance);

        // then
        assertThat(underTest.get()).isEqualTo(newInstance);
    }

    @Test
    void itShouldNotifyListener() {
        // given
        String newInstance = "new Test";
        Consumer<String> subscriber = Mockito.mock(StringConsumer.class);

        // when
        underTest.subscribe(subscriber);
        underTest.set(newInstance);

        // then
        verify(subscriber).accept(newInstance);
    }

    @Test
    void itShouldNotifyAllListeners() {
        // given
        String newInstance = "new Test";
        Consumer<String> subscriber = Mockito.mock(StringConsumer.class);
        Consumer<String> subscriber2 = Mockito.mock(StringConsumer.class);

        // when
        underTest.subscribe(subscriber);
        underTest.subscribe(subscriber2);
        underTest.set(newInstance);

        // then
        verify(subscriber).accept(newInstance);
        verify(subscriber2).accept(newInstance);
    }

    @Test
    void itShouldNotNotifyUnregisteredListeners() {
        // given
        String newInstance = "new Test";
        Consumer<String> subscriber = Mockito.mock(StringConsumer.class);

        // when
        underTest.subscribe(subscriber);
        underTest.unsubscribe(subscriber);
        underTest.set(newInstance);

        // then
        verifyNoInteractions(subscriber);
    }

    private interface StringConsumer extends Consumer<String> {}

}

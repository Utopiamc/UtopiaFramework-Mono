package de.utopiamc.framework.inject.annotations;

import org.apiguardian.api.API;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <h1>Component</h1>
 * <p>
 * A component is used to mark a class an injectable candidate. Components should be automatically scanned by the framework.
 *
 * @since 1
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
@API(status = API.Status.STABLE, since = "1")
public @interface Component {
}

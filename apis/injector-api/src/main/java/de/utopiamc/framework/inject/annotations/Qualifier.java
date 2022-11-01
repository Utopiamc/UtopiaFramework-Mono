package de.utopiamc.framework.inject.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <h1>Qualifier</h1>
 * <p>
 * A qualifier is used to:
 * <ol>
 *     <li>Specify witch candidate should be injected in a given parameter or field.</li>
 *     <li>Specify a name to a injectable candidate.</li>
 *     <li>Create an annotation based qualifier for the use cases above.</li>
 * </ol>
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE, ANNOTATION_TYPE, METHOD, PARAMETER, FIELD})
public @interface Qualifier {

    String named() default "";

}

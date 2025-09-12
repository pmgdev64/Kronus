package net.kronus.mod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface KronusMod {
    String name();
    String version() default "1.0";
    String author() default "Unknown";
    String description() default "No description";
}

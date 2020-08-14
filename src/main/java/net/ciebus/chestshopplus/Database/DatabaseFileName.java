package net.ciebus.chestshopplus.Database;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseFileName {
    String value();
}

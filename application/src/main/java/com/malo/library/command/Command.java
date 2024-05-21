package com.malo.library.command;

import com.malo.library.command.CommandHandler;
import org.springframework.transaction.annotation.Isolation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface Command {

   default boolean checkValidityBeforeHandling(StringBuilder violationsMessage){
       return true;
   }

   default boolean checkValidityAfterHandling(StringBuilder violationsMessage){
       return true;
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Usecase{
        Class<? extends CommandHandler<?>>[] handlers();

        boolean parallelHandling() default false;

        Isolation isolation() default Isolation.DEFAULT;

        String requiredAuthority() default "";

        boolean secured() default false;
    }
}

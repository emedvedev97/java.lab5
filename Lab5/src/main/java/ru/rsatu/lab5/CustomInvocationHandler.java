/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rsatu.lab5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author emedvedev
 */
class CustomInvocationHandler implements InvocationHandler {

    private AnnotationDemo annotationOriginal;

    CustomInvocationHandler(AnnotationDemo annotationOriginal) {
        this.annotationOriginal = annotationOriginal;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method md = annotationOriginal.getClass().getMethod(method.getName(), (Class<?>[]) method.getGenericParameterTypes());
           
        Logged ld = md.getAnnotation(Logged.class);
        if (ld != null) {

            Logger logger = LogManager.getLogger(ld.loggedClass());

            switch (ld.value()) {
                case ERROR:
                    logger.error(md);
                    break;
                case DEBUG:
                    logger.debug(md);
                    break;
                case INFO:
                    logger.info(md);
                    break;
                case WARN:
                    logger.warn(md);
                    break;
            }

        }

        return method.invoke(annotationOriginal, args);
    }
}

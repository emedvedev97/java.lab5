/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rsatu.lab5;

import java.io.*;
import java.lang.annotation.Annotation;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 *
 * @author emedvedev
 */
public class MyClassLoader extends ClassLoader {

    private String path;

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String path = name.replace('.', '/');
        String classDirectory = this.path + path;

        //System.out.println(classDirectory);
        File f = new File(classDirectory + ".class");
        if (!f.isFile()) {
            throw new ClassNotFoundException("Нет такого класса " + name);
        }
        try {
            InputStream ins = new BufferedInputStream(new FileInputStream(f));
            ClassPool cp = ClassPool.getDefault();
            cp.importPackage("org.apache.logging.log4j");
            CtClass cc = cp.makeClass(ins);
            for (CtMethod cm : cc.getDeclaredMethods()) {
                Logged ld = (Logged) cm.getAnnotation(Logged.class);
                if (ld != null) {
                    String cmdB = "{Logger logger = LogManager.getLogger(); "
                            + "String param = \"Method "+ cm.getName()
                            +" start, param: \";"
                            + "for (int i = 0; i < $args.length; i++ )"
                            + " param += $args[i] + \" \";"
                            + "logger.info(param);}";
                    cm.insertBefore(cmdB);
                    String cmdA = "{Logger logger = LogManager.getLogger();"
                            + " logger.info(\"Method "+ cm.getName() +" end,"
                            + " return: \" + $_);}";
                    cm.insertAfter(cmdA);
                }
            }

            return cc.toClass();
        } catch (IOException e) {
            throw new ClassNotFoundException("Проблемы с байт кодом");
        } catch (CannotCompileException ex) {
            Logger.getLogger(MyClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rsatu.lab5;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import javassist.CannotCompileException;
import javassist.NotFoundException;

/**
 *
 * @author emedvedev
 */
public class App {

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.NoSuchMethodException
     * @throws javassist.NotFoundException
     * @throws java.io.IOException
     * @throws javassist.CannotCompileException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, NotFoundException, CannotCompileException, IOException, InstantiationException, IllegalAccessException, InterruptedException {
        File currentClass = new File(URLDecoder.decode(App.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath(), "UTF-8"));
        String classDirectory = currentClass.getParent() + "/classes/";
        MyClassLoader loader1 = new MyClassLoader();
        loader1.setPath(classDirectory);
        Class cl = loader1.findClass("ru.rsatu.lab5.AnnotationDemo");
        Demo dm = (Demo) cl.newInstance();
        dm.setId(Long.MIN_VALUE);
        dm.getId();
        Thread.sleep(1000);
        dm.setId(Long.MAX_VALUE);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rsatu.lab5;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 *
 * @author emedvedev
 */
public class Info {

    private static final String DEPTH_CHARACTER = " ";
    private static final String DEPTH_DIRECTORY = "|-";


    /**
     *
     * @param name
     * @return
     */
    public String getAllInfo(Class name) {
        return this.getAll(name, new StringBuilder()).toString();
    }

    private StringBuilder getAll(Class name, StringBuilder depth) {
        StringBuilder sb = new StringBuilder();
        StringBuilder dp = new StringBuilder();
        dp.append(depth);
        dp.append(DEPTH_CHARACTER);
        sb.append(this.getField(name, dp));
        sb.append(this.getMethods(name, dp));
        sb.append(this.getInterfaces(name, dp));
        sb.append(this.getParent(name, dp));
        sb.append(this.getLoader(name, dp));
        return sb;
    }

    private StringBuilder getParent(Class name, StringBuilder depth) {
        StringBuilder sb = new StringBuilder();
        StringBuilder dp = new StringBuilder();
        dp.append(depth);
        sb.append(dp);
        dp.append(DEPTH_CHARACTER);
        sb.append(getAnotations(name.getAnnotations(), depth));
        sb.append("Class name:  ");
        sb.append(name.getName());
        sb.append(" Parant: ");
        if (name.getSuperclass() != null) {
            sb.append(name.getSuperclass().getName()).append("\n");
        } else {
            sb.append("none").append("\n");
        }
        if (name.getSuperclass() != null) {
            sb.append(this.getAll(name.getSuperclass(), dp));
        }
        return sb;
    }

    private StringBuilder getInterfaces(Class name, StringBuilder depth) {
        Class[] cl = name.getInterfaces();
        StringBuilder sb = new StringBuilder();
        StringBuilder dp = new StringBuilder();
        dp.append(depth);
        StringBuilder lineBegin = new StringBuilder();
        lineBegin.append(dp);
        sb.append(lineBegin);
        lineBegin.append(DEPTH_DIRECTORY);
        dp.append(DEPTH_CHARACTER);
        sb.append(getAnotations(name.getAnnotations(), depth));
        sb.append("Class name:  ").append(name.getName());
        sb.append(" Interfaces: ").append(cl.length).append("\n");
        for (Class cs : cl) {
            sb.append(getAnotations(cs.getAnnotations(), depth));
            sb.append(lineBegin);
            sb.append(cs.getName());
            Type tempType = cs.getGenericSuperclass();
            if (tempType != null) {
                sb.append("    Generic type:   ").append(tempType);
            }
            /**
             * Type[] tempType = cs.getGenericInterfaces(); if (tempType.length
             * > 0) { sb.append(" Generic type: "); }
             *
             * for (Type tp : tempType) {
             * sb.append(tp.toString()).append(DEPTH_CHARACTER); }
             *
             */
            sb.append("\n");
            sb.append(this.getAll(cs, dp));
        }
        return sb;
    }

    private StringBuilder getField(Class name, StringBuilder depth) {
        Field[] fd = name.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        StringBuilder lineBegin = new StringBuilder();
        lineBegin.append(depth);
        sb.append(lineBegin);
        lineBegin.append(DEPTH_DIRECTORY);
        sb.append(getAnotations(name.getAnnotations(), depth));
        sb.append("Class name:  ").append(name.getName());
        sb.append(" Fields: ").append(fd.length).append("\n");
        for (Field fl : fd) {
            sb.append(getAnotations(fl.getAnnotations(), depth));
            sb.append(lineBegin);
            String mf = Modifier.toString(fl.getModifiers());
            if (mf.length() > 0) {
                sb.append(mf).append("    ");
            }
            sb.append(fl.getType().getName()).append(" ");
            sb.append(fl.getName());
            Type tempType = fl.getGenericType();
            if (tempType != null) {
                sb.append("    Generic type:   ").append(tempType);
            }
            sb.append("\n");
        }
        return sb;
    }

    private StringBuilder getLoader(Class name, StringBuilder depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(depth);
        ClassLoader cl = name.getClassLoader();
        sb.append(getAnotations(name.getAnnotations(), depth));
        sb.append("Class name:  ").append(name.getName());
        sb.append(" Class loader name:  ");
        if (cl != null) {
            sb.append(getAnotations(cl.getClass().getAnnotations(), depth));
            sb.append(cl.getClass().getName());
        } else {
            sb.append("none");
        }
        sb.append("\n");
        return sb;
    }

    private StringBuilder getMethods(Class name, StringBuilder depth) {
        Method[] md = name.getDeclaredMethods();
        StringBuilder sb = new StringBuilder();
        StringBuilder lineBegin = new StringBuilder();
        lineBegin.append(depth);
        sb.append(lineBegin);
        lineBegin.append(DEPTH_DIRECTORY);
        sb.append(getAnotations(name.getAnnotations(), depth));
        sb.append("Class name:  ").append(name.getName());
        sb.append(" Methods:    ").append(md.length).append("\n");
        for (Method me : md) {
            sb.append(getAnotations(me.getAnnotations(), depth));
            sb.append(lineBegin);
            sb.append(me.toString());
            Type[] tempType = me.getGenericParameterTypes();
            if (tempType.length > 0) {
                sb.append("    Generic type:   ");
            }
            for (Type tp : tempType) {
                sb.append(tp.toString()).append(DEPTH_CHARACTER);
            }
            sb.append("\n");
        }
        return sb;
    }

    private StringBuilder getAnotations(Annotation[] at, StringBuilder depth) {
        StringBuilder sb = new StringBuilder();
        for (Annotation an : at) {
            sb.append(depth).append(DEPTH_CHARACTER);
            sb.append(an.toString()).append("\n");
        }
        return sb;
    }

}

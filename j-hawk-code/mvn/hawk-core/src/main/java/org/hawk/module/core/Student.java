/*
 * This file is part of hawkeye
 *  
 *
 * 
 *
 *  
 *  
 * 3) Developers are encouraged but not required to notify the hawkeye maintainers at abeautifulmind98@gmail.com
 *  
 *
 * 
 * 
 *
 * 
 */
package org.hawk.module.core;

/**
 *
 * @author manoranjan
 */
public class Student {

    public String name;
    public double age;

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" + "name=" + name + "age=" + age + '}';
    }
}

/*
 * This file is part of hawkeye
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the hawkeye maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * 
 * 
 *
 * 
 */
package org.hawk.module.core;

import java.util.ArrayList;
import java.util.List;
import org.common.di.AppContainer;
import org.hawk.logger.HawkLogger;
import org.hawk.module.annotation.SubTask;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author manoranjan
 */
@ScanMe(true)
//@Component(TESTMODULE)
//@Qualifier(DEFAULTQUALIFIER)

public class TestModule extends HawkCoreModule {

    @Override
    public String toString() {
        return this.getName();
    }

    public String getName() {
        return "TestModule";
    }

    @Override
    public boolean startUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SubTask(name = "test", sequence = 1, ignoreException = false, hawkParam = "")
    public Object test(Object... args) throws Exception {

        Student student = new Student();
        student.setAge(5);
        student.setName("Deepika");

        Student student1 = new Student();
        student1.setAge(4);
        student1.setName("Mrinali");

        List<Student> studs = new ArrayList<Student>();
        studs.add(student);
        studs.add(student1);
        double x = 5;
        return x;
    }

    @SubTask(name = "reverse", sequence = 1, ignoreException = false, hawkParam = "var student")
    public Object reverse(Object... args) throws Exception {


        Student student = (Student) args[0];

        student.setAge(56);

        return student;
    }
    @Create
    public HawkCoreModule create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }
    private static final HawkLogger logger = HawkLogger.getLogger(TestModule.class.getName());
}

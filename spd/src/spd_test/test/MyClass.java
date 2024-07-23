package spd_test.test;

import lopicost.config.logger.Logger;

public class MyClass {

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        myClass.log(": listado", Logger.INFO);
        myClass.log(": otra cosa", Logger.INFO);
    }

    public void log(String message, int level) {
        Logger.log("SPDLogger", message, level);
    }
}
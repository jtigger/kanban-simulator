package com.bigvisible.kanbansimulatortester.core.unit;

import java.security.SecureRandom;

public class UnitTestHelper {

    /**
     * Where any number will do, within reason.
     * 
     * @return an integer between 10 and 30
     */
    public static int anyReasonableNumber() {
        /*
         * big enough that some values can be subtracted without resulting in a negative number, but small enough that
         * if it's used for looping values, we don't get carried away.
         */
        return UnitTestHelper.random.nextInt(20) + 10;
    }
    private static SecureRandom random = new SecureRandom();

}

/*
 * Created on Jun 16, 2016 by aeswara
 *
 * Copyright (c) 2016 Walmart Inc. All Rights Reserved.
 */
package com.foo.bar;

import java.time.Instant;

public class InstantTest {
    public static void main(String[] args) throws InterruptedException {
        java.time.Instant mTimestamp = Instant.now();
        System.out.println(mTimestamp);
        Thread.sleep(100);
        mTimestamp = Instant.now();
        System.out.println(mTimestamp);
        Thread.sleep(100);
        mTimestamp = Instant.now();
        System.out.println(mTimestamp);

    }
}

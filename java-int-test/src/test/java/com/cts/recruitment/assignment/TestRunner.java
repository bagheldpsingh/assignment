package com.cts.recruitment.assignment;

import com.intuit.karate.junit5.Karate;
public class TestRunner {
    @Karate.Test
    Karate testFullPath() {
        return Karate.run("classpath:com/cts/recruitment/assignment/test.feature");
    }

}

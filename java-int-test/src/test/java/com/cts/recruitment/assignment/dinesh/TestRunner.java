package com.cts.recruitment.assignment.dinesh;

import com.intuit.karate.junit5.Karate;
public class TestRunner {
    @Karate.Test
    Karate testFullPath() {
        return Karate.run("classpath:test/dinesh/test.feature");
    }

}

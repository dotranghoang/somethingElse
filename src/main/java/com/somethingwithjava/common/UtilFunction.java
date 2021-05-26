package com.somethingwithjava.common;


import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UtilFunction {
    public int getRandomNumber() {
        Random random = new Random();

        int number = random.nextInt(999999);
        return number;
    }
}

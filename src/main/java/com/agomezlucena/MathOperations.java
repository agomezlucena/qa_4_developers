package com.agomezlucena;

import java.util.Arrays;

public class MathOperations {
    public static int add(int ...summands){
        return Arrays.stream(summands).sum();
    }

    public static int toBeMocked(){
        return 10;
    }

    public static void voidMethodToMock(){
        System.out.println("this isn't the mocked output");
    }
}

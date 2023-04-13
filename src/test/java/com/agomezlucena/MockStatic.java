package com.agomezlucena;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class MockStatic {

    @Test
    @DisplayName("mock static method")
    void testStaticMethod() {
        try (var mockedStatic = mockStatic(MathOperations.class)) {
            mockedStatic.when(MathOperations::toBeMocked).thenReturn(100);
            assertEquals(100, MathOperations.toBeMocked());
        }
    }

    @Test
    @DisplayName("mock static void method")
    void testStaticMockMethod() {
        try (var mockedStatic = mockStatic(MathOperations.class)){
            mockedStatic.when(MathOperations::voidMethodToMock).thenAnswer(invocationOnMock -> {
                System.out.println("this is the mocked output");
                return null;
            });
            MathOperations.voidMethodToMock();
        }
    }

    @Test
    @DisplayName("without mock")
    void testStaticMethodWithoutMock() {
        assertEquals(10, MathOperations.toBeMocked());
    }
}

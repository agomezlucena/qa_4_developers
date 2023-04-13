package com.agomezlucena;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathOperationsTest {

    @BeforeAll
    static void generateFileWithRandomInputs() throws IOException, URISyntaxException {
        Random rng = new Random();
        Path path = Paths.get(ClassLoader.getSystemResource("data_for_testing").toURI());
        for (int i = 0; i < rng.nextInt(11) + 1; i++){
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.write(new TestLine(rng).toString());
            }
        }
    }

    @AfterAll
    static void cleanFile() throws URISyntaxException, IOException {
        Path path = Paths.get(ClassLoader.getSystemResource("data_for_testing").toURI());
        try (BufferedWriter ignored = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
            //used to clean file.
        }
    }

    @Test
    @DisplayName("check if correctly add various numbers")
    void checkSum() {
        //given
        var givenInput = new int[]{1, 2, 3};
        var expectedOutput = 6;
        //when
        var obtainedValue = MathOperations.add(givenInput);
        //then
        assertEquals(expectedOutput, obtainedValue);
    }

    @DisplayName("for random input return the expected value")
    @CsvFileSource(resources = "/data_for_testing",delimiter = ',')
    @ParameterizedTest(name = "input {index}: {0} expected output: {1}")
    void checkSumForRandomInput(String rawInput, int expectedResult) {
        int[] givenInput = Arrays.stream(rawInput.split(";")).mapToInt(Integer::valueOf).toArray();
        int obtainedValue = MathOperations.add(givenInput);
        assertEquals(expectedResult, obtainedValue);
    }

    private static final class TestLine {
        private final int[] numberLines;
        private int result;

        public TestLine(Random rng){
            int numberQuantity = rng.nextInt(10) + 2;
            this.numberLines = new int[numberQuantity];

            for (int i = 0; i < numberQuantity; i++) {
                this.numberLines[i] = rng.nextInt(20)+1;
                result += this.numberLines[i];
            }
        }

        @Override
        public String toString() {
            return String.format(
                    "%s,%d\n",
                    Arrays.stream(this.numberLines).mapToObj(String::valueOf).collect(Collectors.joining(";")),
                    this.result
            );
        }

        public void setResult(int result){
            this.result = result;
        }
    }
}
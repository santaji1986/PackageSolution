package com.mobiquity.packer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import com.mobiquity.exception.APIException;

class PackerTest {

	@ParameterizedTest
	@NullAndEmptySource
	void testPackExpectedExceptionForEmptyFilePath(String filePath) throws APIException {
		assertThrows(APIException.class, () -> Packer.pack(filePath));
	}

	@ParameterizedTest
	@MethodSource("filePathAndOutputProvider")
	void testPackWithFile(String fileName, String expected) throws APIException {
		String filePath = getAbsolutePathForFile(fileName);
		String actual = Packer.pack(filePath);
		assertEquals(expected, actual);
	}

	private String getAbsolutePathForFile(String fileName) {
		ClassLoader loader = this.getClass().getClassLoader();
		String filePath = loader.getResource(fileName).toString();
		return filePath.substring("file:/".length());
	}

	static Stream<Arguments> filePathAndOutputProvider() {
		StringBuffer sb = new StringBuffer();
		sb.append("4").append("\r\n");
		sb.append("-").append("\r\n");
		sb.append("2,7").append("\r\n");
		sb.append("8,9").append("\r\n");
		return Stream.of(Arguments.of("testinput1", "4\r\n"), Arguments.of("testinput2", "-\r\n"),
				Arguments.of("example_input", sb.toString()), Arguments.of("emptyInput.txt", ""));
	}

	@ParameterizedTest
	@MethodSource("inputLineProvider")
	void testGetPackageIndexes(String expected,String line) throws APIException {
		assertEquals(expected, Packer.getPackageIndexes(line));
	}

	static Stream<Arguments> inputLineProvider() {
		return Stream.of(
				Arguments
						.of("4", "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)"),
				Arguments.of("-", "45 : (1,65,€55)"));
	}
}

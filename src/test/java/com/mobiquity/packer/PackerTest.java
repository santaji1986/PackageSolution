package com.mobiquity.packer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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
		return Stream.of(Arguments.of("emptyInput.txt", ""));
	}

}

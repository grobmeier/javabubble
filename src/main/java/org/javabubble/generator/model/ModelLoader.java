package org.javabubble.generator.model;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.jetbrains.annotations.NotNull;

public class ModelLoader {

	private final Path base;
	private final ObjectMapper mapper;

	public ModelLoader(Path base) {
		this.base = base;
		this.mapper = new ObjectMapper(new YAMLFactory());
	}

	public JavaBubble load(String bubbleFileName) throws IOException {
		return new JavaBubble(parseYAML(bubbleFileName, new TypeReference<List<JavaPerson>>() {
		}));
	}

	private <T> T parseYAML(String file, TypeReference<T> type) throws IOException {
		try (var in = Files.newBufferedReader(base.resolve(file), UTF_8)) {
			return mapper.readValue(in, type);
		}
	}

}

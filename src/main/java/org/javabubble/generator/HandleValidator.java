package org.javabubble.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import org.javabubble.generator.model.JavaBubble;
import org.javabubble.generator.model.ModelLoader;
import org.javabubble.generator.validation.PersonValidator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class HandleValidator {

	public static void main(String[] args) throws IOException {
		var file = new File("./bubbles");
		if (file.isDirectory()) {
			for (var bubbleFileName : Objects.requireNonNull(file.list())) {
				var validator = new PersonValidator();
				var bubble = new ModelLoader(Path.of("bubbles")).load(bubbleFileName);
				var cleanedBubble = new JavaBubble(bubble.people().stream() //
						.map(validator::validate) //
						.toList());
				try (var writer = Files.newBufferedWriter(Path.of("bubbles/" + bubbleFileName))) {
					writer.write("# Please add new entries in alphabetic order of the name.\n");
					var mapper = new ObjectMapper(new YAMLFactory());
					mapper.writeValue(writer, cleanedBubble.people());
				}
			}
		}
	}

}

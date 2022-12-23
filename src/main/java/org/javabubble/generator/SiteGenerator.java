package org.javabubble.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.javabubble.generator.model.ModelLoader;
import org.javabubble.generator.site.FileOutput;
import org.javabubble.generator.site.Site;

public class SiteGenerator {

	public static void main(String[] args) throws IOException {
		var file = new File("./bubbles");
		if (file.isDirectory()) {
			for (var bubbleFileName : Objects.requireNonNull(file.list())) {
				var bubbleName = bubbleFileName.replace(".yaml", "");

				var bubble = new ModelLoader(Path.of("bubbles")).load(bubbleFileName);
				var output = new FileOutput(Path.of("target/site/" +  bubbleName));
				new Site(bubble, output).generate();
			}
		}
	}
}

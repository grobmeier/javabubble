package org.javabubble.generator;

import org.javabubble.generator.model.ModelLoader;
import org.javabubble.generator.site.FileOutput;
import org.javabubble.generator.site.Site;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class SiteGenerator {

	public static void main(String[] args) throws IOException {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setSuffix(".html");
		templateResolver.setPrefix("templates/");
		templateResolver.setCacheable(false);
		final TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);


		var file = new File("./bubbles");
		if (file.isDirectory()) {
			for (var bubbleFileName : Objects.requireNonNull(file.list())) {
				var bubbleName = bubbleFileName.replace(".yaml", "");

				var bubble = new ModelLoader(Path.of("bubbles")).load(bubbleName, bubbleFileName);

				var output = new FileOutput(Path.of("target/site/" +  bubbleName));
				new Site(templateEngine, bubble, output).generate();
			}
		}
	}
}

package org.javabubble.generator;

import org.javabubble.generator.model.Bubble;
import org.javabubble.generator.model.ModelLoader;
import org.javabubble.generator.site.FileOutput;
import org.javabubble.generator.site.BubblePage;
import org.javabubble.generator.site.IntroPage;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SiteGenerator {

	public static void main(String[] args) throws IOException {
		new SiteGenerator().run();
	}

	public void run() throws IOException  {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setSuffix(".html");
		templateResolver.setPrefix("templates/");
		templateResolver.setCacheable(false);
		final TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		List<Bubble> bubbles = readBubbles();

		generateBubblePages(templateEngine, bubbles);
		generateIntroPage(templateEngine, bubbles);
	}

	private void generateIntroPage(TemplateEngine templateEngine, List<Bubble> bubbles) throws IOException {
		var output = new FileOutput(Path.of("target/site/"));
		new IntroPage(templateEngine, bubbles, output).generate();
	}

	private void generateBubblePages(TemplateEngine templateEngine, List<Bubble> bubbles) throws IOException {
		for (Bubble bubble : bubbles) {
			var output = new FileOutput(Path.of("target/site/" +  bubble.bubbleName()));
			new BubblePage(templateEngine, bubble, output).generate();
		}
	}

	private List<Bubble> readBubbles() throws IOException {
		List<Bubble> bubbles = new ArrayList<>();
		var file = new File("./bubbles");
		if (file.isDirectory()) {
			for (var bubbleFileName : Objects.requireNonNull(file.list())) {
				var bubbleName = bubbleFileName.replace(".yaml", "");

				var bubble = new ModelLoader(Path.of("bubbles")).load(bubbleName, bubbleFileName);
				bubbles.add(bubble);
			}
		}
		return bubbles;
	}
}

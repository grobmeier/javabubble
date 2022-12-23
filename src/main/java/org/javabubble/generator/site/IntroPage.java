package org.javabubble.generator.site;

import org.javabubble.generator.model.Bubble;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class IntroPage {

	private TemplateEngine templateEngine;
	private List<Bubble> bubbles;
	private SiteOutput output;

	public IntroPage(TemplateEngine templateEngine, List<Bubble> bubbles, SiteOutput output) {
		this.templateEngine = templateEngine;
		this.bubbles = bubbles;
		this.output = output;
	}

	public void generate() throws IOException {
		try (Writer writer = output.newTextDocument("index.html")) {
			Context context = new Context();
			context.setVariable("bubbles", bubbles);
			writer.write(templateEngine.process("intro", context));
		}
	}

}

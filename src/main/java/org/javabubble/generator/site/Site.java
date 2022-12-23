package org.javabubble.generator.site;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.javabubble.generator.model.Handle;
import org.javabubble.generator.model.Bubble;
import org.javabubble.generator.model.Person;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class Site {

	private TemplateEngine templateEngine;
	private Bubble bubble;
	private SiteOutput output;

	public Site(TemplateEngine templateEngine, Bubble bubble, SiteOutput output) {
		this.templateEngine = templateEngine;
		this.bubble = bubble;
		this.output = output;
	}

	public void generate() throws IOException {
		generateIndex();
		generatePeopleYaml();
		generatePeopleJson();
		generateFollowingCsv();
	}

	private void generateIndex() throws IOException {
		try (Writer writer = output.newTextDocument("index.html")) {
			Context context = new Context();
			context.setVariable("people", bubble.people());
			context.setVariable("bubbleName", bubble.bubbleName());
			String index = templateEngine.process("bubble", context);
			System.out.println(index);
			writer.write(index);
		}
	}

	private void generatePeopleYaml() throws IOException {
		try (Writer writer = output.newTextDocument("bubble.yaml")) {
			var mapper = new ObjectMapper(new YAMLFactory());
			mapper.writeValue(writer, bubble.people());
		}
	}

	private void generatePeopleJson() throws IOException {
		try (Writer writer = output.newTextDocument("bubble.json")) {
			var mapper = new ObjectMapper(new JsonFactory());
			mapper.writeValue(writer, bubble.people());
		}
	}

	private void generateFollowingCsv() throws IOException {
		try (Writer writer = output.newTextDocument("following.csv")) {
			var printer = new PrintWriter(writer);
			printer.println("Account address,Show boosts,Notify on new posts,Languages");
			bubble.people().stream() //
					.map(Person::fediverse) //
					.filter(Objects::nonNull) //
					.map(Handle::getHandle) //
					.map("@%s,true,false,"::formatted) //
					.forEach(printer::println);
			printer.flush();
		}
	}
}

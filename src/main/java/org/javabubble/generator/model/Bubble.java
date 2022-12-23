package org.javabubble.generator.model;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public record Bubble(String bubbleName, List<Person> people) {
	private static final Collator COLLATOR = Collator.getInstance(Locale.ENGLISH);
	private static final Comparator<Person> ORDER = Comparator.comparing(p -> p.name().split("-|\s"),
			(a1, a2) -> Arrays.compare(a1, a2, COLLATOR));


	public Bubble {
		people.stream().reduce(Bubble::checkOrder);
	}

	private static Person checkOrder(Person a, Person b) {
		if (ORDER.compare(a, b) > 0) {
			throw new IllegalArgumentException(
					"Invalid ordering: %s should be listed before %s".formatted(b.name(), a.name()));
		}
		return b;
	}

}

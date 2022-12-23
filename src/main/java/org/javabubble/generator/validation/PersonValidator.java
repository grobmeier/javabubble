package org.javabubble.generator.validation;

import org.javabubble.generator.model.FediverseHandle;
import org.javabubble.generator.model.GithubHandle;
import org.javabubble.generator.model.Person;
import org.javabubble.generator.model.RedditHandle;
import org.javabubble.generator.model.TwitterHandle;

public class PersonValidator {

	private final HandleValidator<TwitterHandle> twitterValidator = new LoggingValidator<>(new TwitterValidator());
	private final HandleValidator<FediverseHandle> fediverseValidator = new LoggingValidator<>(
			new FediverseValidator());
	private final HandleValidator<GithubHandle> githubValidator = new LoggingValidator<>(new GithubValidator());
	private final HandleValidator<RedditHandle> redditValidator = new LoggingValidator<>(new RedditValidator());

	public Person validate(Person person) {
		return new Person(person.name(), //
				twitterValidator.validate(person.twitter()), //
				fediverseValidator.validate(person.fediverse()), //
				githubValidator.validate(person.github()), //
				redditValidator.validate(person.reddit()));
	}

}

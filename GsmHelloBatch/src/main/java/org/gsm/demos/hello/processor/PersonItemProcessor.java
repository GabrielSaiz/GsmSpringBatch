package org.gsm.demos.hello.processor;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.gsm.demos.hello.entity.Person;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LogManager.getLogger(PersonItemProcessor.class.getName());

	@Override
	public Person process(Person person) throws Exception {

		final String firstName = person.getFirstName().toUpperCase();
		final String lastName = person.getLastName().toUpperCase();

		final Person transformedPerson = new Person(firstName, lastName);

		logger.info("Converting (" + person + ") into (" + transformedPerson + ")");

		return transformedPerson;
	}

}

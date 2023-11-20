package org.netflix.restservice;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.netflix.model.genre.GenreDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@GetMapping("/genre/{id}")
	public GenreDao genre(@PathVariable(required = true) long id) {
		return new GenreDao(id);
	}

	@GetMapping("/genre/all")
	public List<GenreDao> allGenre() {
		return GenreDao.loadAll();
	}
	
}

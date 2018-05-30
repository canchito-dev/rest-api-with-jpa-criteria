/**
 * This content is released under the MIT License (MIT)
 *
 * Copyright (c) 2018, canchito-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 * @author 		José Carlos Mendoza Prego
 * @copyright	Copyright (c) 2018, canchito-dev (http://www.canchito-dev.com)
 * @license		http://opensource.org/licenses/MIT	MIT License
 * @link		https://github.com/canchito-dev/rest-api-with-with-spring-jpa-criteria
 **/
package com.canchitodev.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.canchitodev.example.domain.Author;
import com.canchitodev.example.domain.Books;
import com.canchitodev.example.service.AuthorService;

@SpringBootApplication
public class RestApiWithWithSpringJpaCriteriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiWithWithSpringJpaCriteriaApplication.class, args);
	}
	
	@Bean
	InitializingBean authorInitializer(final AuthorService authorService) {

	    return new InitializingBean() {
	        public void afterPropertiesSet() throws Exception {
	        	List<Books> books = new ArrayList<Books>();
	        	Books book1 = new Books();
	        	Books book2 = new Books();
	        	Books book3 = new Books();
	        	
	        	// save first record
	        	Author author = new Author();
	        	author.setLastName("Doe");
	        	author.setMail("john@canchito-dev.com");
	        	author.setFirstName("John");
	        	author.setTelephone("123456");
	        	
	        	book1.setPrice("12,94€");
	        	book1.setTitle("Book Title 1");
	        	book1.setYear(2009);
	        	book1.setAuthor(author);
	        	books.add(book1);
	        	
	        	book2.setPrice("23,87€");
	        	book2.setTitle("Book Title 2");
	        	book2.setYear(1999);
	        	book2.setAuthor(author);
	        	books.add(book2);
	        	
	        	book3.setPrice("20,18€");
	        	book3.setTitle("Book Title 5");
	        	book3.setYear(1990);
	        	book3.setAuthor(author);
	        	books.add(book3);
	        	
	        	author.setBooks(books);	        	
	        	authorService.save(author);
	        	
	        	// save second record
	        	books = new ArrayList<Books>();
	        	book1 = new Books();
	        	book2 = new Books();
	        	book3 = new Books();
	        	
	        	author = new Author();
	        	author.setLastName("Perez");
	        	author.setMail("juan@canchito-dev.com");
	        	author.setFirstName("Juan");
	        	author.setTelephone("098765");
	        	
	        	book1.setPrice("6,99€");
	        	book1.setTitle("Book Title 3");
	        	book1.setYear(2017);
	        	book1.setAuthor(author);
	        	books.add(book1);
	        	
	        	book2.setPrice("9,76€");
	        	book2.setTitle("Book Title 4");
	        	book2.setYear(1990);
	        	book2.setAuthor(author);
	        	books.add(book2);
	        	
	        	book3.setPrice("19,82€");
	        	book3.setTitle("Book Title 6");
	        	book3.setYear(1999);
	        	book3.setAuthor(author);
	        	books.add(book3);
	        	
	        	author.setBooks(books);	        	
	        	authorService.save(author);
	        	
	        	// save third record
	        	books = new ArrayList<Books>();
	        	book1 = new Books();
	        	book2 = new Books();
	        	book3 = new Books();
	        	
	        	author = new Author();
	        	author.setLastName("Escobar");
	        	author.setMail("pablo@canchito-dev.com");
	        	author.setFirstName("Pablo");
	        	author.setTelephone("13579");
	        	
	        	book1.setPrice("9,00€");
	        	book1.setTitle("Book Title 7");
	        	book1.setYear(2010);
	        	book1.setAuthor(author);
	        	books.add(book1);
	        	
	        	book2.setPrice("8,56€");
	        	book2.setTitle("Book Title 8");
	        	book2.setYear(1991);
	        	book2.setAuthor(author);
	        	books.add(book2);
	        	
	        	book3.setPrice("14,22€");
	        	book3.setTitle("Book Title 9");
	        	book3.setYear(1987);
	        	book3.setAuthor(author);
	        	books.add(book3);
	        	
	        	author.setBooks(books);	        	
	        	authorService.save(author);
	        }
	    };
	}
}

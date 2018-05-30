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
package com.canchitodev.example.response;

import java.util.ArrayList;
import java.util.List;

import com.canchitodev.example.domain.Author;
import com.canchitodev.example.domain.Books;

public class RestResponseBuilder {

	public static AuthorResponse createAuthorResponse(Author author) {
		AuthorResponse response = new AuthorResponse();
		response.setAuthorId(author.getAuthorId());
		response.setFirstName(author.getFirstName());
		response.setLastName(author.getLastName());
		response.setMail(author.getMail());
		response.setTelephone(author.getTelephone());
		response.setBooks(RestResponseBuilder.createBookResponseList(author.getBooks()));
		
		return response;
	}
	
	public static List<AuthorResponse> createAuthorResponseList(List<Author> authors) {
		List<AuthorResponse> response = new ArrayList<AuthorResponse>();
		for (Author author : authors)
			response.add(RestResponseBuilder.createAuthorResponse(author));
		
		return response;
	}
	
	public static BooksResponse createBookResponse(Books book) {
		BooksResponse response = new BooksResponse();
		response.setBookId(book.getBookId());
		response.setPrice(book.getPrice());
		response.setTitle(book.getTitle());
		response.setYear(book.getYear());
		response.setAuthorId(book.getAuthor().getAuthorId());
		
		return response;
	}
	
	public static List<BooksResponse> createBookResponseList(List<Books> books) {
		List<BooksResponse> response = new ArrayList<BooksResponse>();
		for (Books book : books)
			response.add(RestResponseBuilder.createBookResponse(book));
		
		return response;
	}
}

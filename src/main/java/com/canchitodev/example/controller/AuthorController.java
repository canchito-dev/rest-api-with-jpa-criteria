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
package com.canchitodev.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.canchitodev.example.domain.Author;
import com.canchitodev.example.response.AuthorResponse;
import com.canchitodev.example.response.JsonResponseCreator;
import com.canchitodev.example.response.RestResponseBuilder;
import com.canchitodev.example.search.SearchCriteria;
import com.canchitodev.example.service.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {
	
	@Autowired
	private AuthorService authorService;
	
	private List<SearchCriteria> setSearchCriteria(String search) {
		List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|!:|<|<=|>|>=)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
            	params.add(new SearchCriteria(matcher.group(1), 
                  matcher.group(2), matcher.group(3)));
            }
        }
        return params;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonResponseCreator<AuthorResponse>> search(
			@RequestParam(value = "searchAuthor", required = false) String searchAuthor,
			@RequestParam(value = "searchBooks", required = false) String searchBooks,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,				// zero-based page index
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size,				// the size of the page to be returned
			@RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,		// direction must not be {@literal null}
			@RequestParam(value = "orderBy", required = false, defaultValue = "authorId") String orderBy			// properties must not be {@literal null}
		) {
		List<SearchCriteria> paramsAuthor = this.setSearchCriteria(searchAuthor);
		List<SearchCriteria> paramsBooks = this.setSearchCriteria(searchBooks);
        
        Page<Author> pagedContent = this.authorService.search(paramsAuthor, paramsBooks, PageRequest.of(page, size, Direction.fromString(direction), orderBy));

        List<AuthorResponse> response = RestResponseBuilder.createAuthorResponseList(pagedContent.getContent());
        JsonResponseCreator<AuthorResponse> jsonResponseCreator = new JsonResponseCreator<AuthorResponse>(pagedContent.getTotalElements(), page, size, direction, orderBy, response);
		return new ResponseEntity<JsonResponseCreator<AuthorResponse>>(jsonResponseCreator, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{authorId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthorResponse> findOne(@PathVariable Long authorId) {
		Author author = this.authorService.findById(authorId);
		
		AuthorResponse response = RestResponseBuilder.createAuthorResponse(author);
		return new ResponseEntity<AuthorResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthorResponse> create(@RequestBody Author author) {
		this.authorService.save(author);
		
		AuthorResponse response = RestResponseBuilder.createAuthorResponse(author);
		return new ResponseEntity<AuthorResponse>(response, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{authorId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthorResponse> update(@PathVariable Long authorId, @RequestBody Author author) {
		this.authorService.findById(authorId);
		
		author.setAuthorId(authorId);
		this.authorService.update(author);
		
		AuthorResponse response = RestResponseBuilder.createAuthorResponse(author);
		return new ResponseEntity<AuthorResponse>(response, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/{authorId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HttpEntity delete(@PathVariable Long authorId) {
		this.authorService.findById(authorId);
		this.authorService.delete(authorId);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
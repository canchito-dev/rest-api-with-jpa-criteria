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
package com.canchitodev.example.service;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.canchitodev.example.domain.Author;
import com.canchitodev.example.domain.Books;
import com.canchitodev.example.exception.ObjectNotFoundException;
import com.canchitodev.example.repository.AuthorRepository;
import com.canchitodev.example.search.SearchCriteria;

@Service
@Transactional
public class AuthorService {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	public Author findById(Long authorId) {
		Author author = this.authorRepository.findByAuthorId(authorId);
		if(author == null)
			throw new ObjectNotFoundException("Could not find author with id '" + authorId + "'");
		return author;
	}
	
	public List<Author> findAll() {
		return this.authorRepository.findAll();
	}
	
	public Page<Author> findAll(Pageable page) {
		return this.authorRepository.findAll(page);
	}
	
	public Page<Author> findAll(Specification<Author> specification, Pageable page) {
		return this.authorRepository.findAll(specification, page);
	}
	
	public void save(Author author) {
		this.authorRepository.save(author);
	}

	public void update(Author author) {
		Author entity = this.findById(author.getAuthorId());
		
		if(author.getLastName() == null)
			author.setLastName(entity.getLastName());
		if(author.getMail() == null)
			author.setMail(entity.getMail());
		if(author.getFirstName() == null)
			author.setFirstName(entity.getFirstName());
		if(author.getTelephone() == null)
			author.setTelephone(entity.getTelephone());
		
		this.save(author);
	}
	
	public void delete(Long authorId) {
		Author author = this.findById(authorId);
		this.authorRepository.delete(author);
	}
	
	@SuppressWarnings("unused")
	public Page<Author> search(List<SearchCriteria> paramsAuthor, List<SearchCriteria> paramsBooks, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> author = criteriaQuery.from(Author.class);
    	Join<Author, Books> books = author.join("books", JoinType.INNER);
 
        Predicate predicate = this.predicateRootBuilder(criteriaBuilder, paramsAuthor, author);
        
        if(!paramsBooks.isEmpty())
        	predicate = this.predicateJoinBuilder(criteriaBuilder, paramsBooks, books);
        
        Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
        
        if (sort.isSorted())
        	criteriaQuery.orderBy(toOrders(sort, author, criteriaBuilder));
        
        criteriaQuery.distinct(true);
        
        criteriaQuery.where(predicate);
        
        TypedQuery<Author> typedQuery = entityManager.createQuery(criteriaQuery);
        
        if(pageable == null)
			return new PageImpl<Author>(typedQuery.getResultList());
        else {
			Long total = (long) typedQuery.getResultList().size();
			
			// Sets the offset position in the result set to start pagination
			typedQuery.setFirstResult((int) pageable.getOffset());
			
			// Sets the maximum number of entities that should be included in the page
			typedQuery.setMaxResults(pageable.getPageSize());

			List<Author> content = total > pageable.getOffset() ? typedQuery.getResultList() : Collections.<Author> emptyList();

			return new PageImpl<Author>(content, pageable, total);
		}
	}
	
	public Predicate predicateRootBuilder(CriteriaBuilder builder, List<SearchCriteria> params, Root<Author> root) {
		Predicate predicate = builder.conjunction();
		 
        for (SearchCriteria param : params) {
        	switch(param.getOperation().toLowerCase()) {
	        	case ">":
	        		predicate = builder.and(predicate, 
	        				builder.greaterThan(root.get(param.getKey()), 
	                        param.getValue().toString()));
	        		break;
	        	case ">:":
	        		predicate = builder.and(predicate, 
	        				builder.greaterThanOrEqualTo(root.get(param.getKey()), 
	                        param.getValue().toString()));
	        		break;
	        	case "<":
	        		predicate = builder.and(predicate, 
	                         builder.lessThan(root.get(param.getKey()), 
	                         param.getValue().toString()));
	        		break;
	        	case "<:":
	        		 predicate = builder.and(predicate, 
	                         builder.lessThanOrEqualTo(root.get(param.getKey()), 
	                         param.getValue().toString()));
	        		break;
	        	case ":":
	        		if (root.get(param.getKey()).getJavaType() == String.class) {
	                    predicate = builder.and(predicate, 
	                    		builder.like(root.get(param.getKey()),
	                    		"%" + param.getValue() + "%"));
	                } else {
	                    predicate = builder.and(predicate, 
	                    		builder.equal(root.get(param.getKey()), param.getValue()));
	                }
	        		break;
	        	case "!:":
	        		if (root.get(param.getKey()).getJavaType() == String.class) {
	                    predicate = builder.and(predicate, 
	                    		builder.notLike(root.get(param.getKey()),
	                    		"%" + param.getValue() + "%"));
	                } else {
	                    predicate = builder.and(predicate, 
	                    		builder.notEqual(root.get(param.getKey()), param.getValue()));
	                }
	        		break;
        	}
        }
        return predicate;
	}
	
	public Predicate predicateJoinBuilder(CriteriaBuilder builder, List<SearchCriteria> params, Join<Author, Books> books) {
		Predicate predicate = builder.conjunction();
		 
        for (SearchCriteria param : params) {
        	switch(param.getOperation().toLowerCase()) {
	        	case ">":
	        		predicate = builder.and(predicate, 
	        				builder.greaterThan(books.get(param.getKey()), 
	                        param.getValue().toString()));
	        		break;
	        	case ">:":
	        		predicate = builder.and(predicate, 
	        				builder.greaterThanOrEqualTo(books.get(param.getKey()), 
	                        param.getValue().toString()));
	        		break;
	        	case "<":
	        		predicate = builder.and(predicate, 
	                         builder.lessThan(books.get(param.getKey()), 
	                         param.getValue().toString()));
	        		break;
	        	case "<:":
	        		 predicate = builder.and(predicate, 
	                         builder.lessThanOrEqualTo(books.get(param.getKey()), 
	                         param.getValue().toString()));
	        		break;
	        	case ":":
	        		if (books.get(param.getKey()).getJavaType() == String.class) {
	                    predicate = builder.and(predicate, 
	                    		builder.like(books.get(param.getKey()),
	                    		"%" + param.getValue() + "%"));
	                } else {
	                    predicate = builder.and(predicate, 
	                    		builder.equal(books.get(param.getKey()), param.getValue()));
	                }
	        		break;
	        	case "!:":
	        		if (books.get(param.getKey()).getJavaType() == String.class) {
	                    predicate = builder.and(predicate, 
	                    		builder.notLike(books.get(param.getKey()),
	                    		"%" + param.getValue() + "%"));
	                } else {
	                    predicate = builder.and(predicate, 
	                    		builder.notEqual(books.get(param.getKey()), param.getValue()));
	                }
	        		break;
        	}
        }
        return predicate;
	}
}
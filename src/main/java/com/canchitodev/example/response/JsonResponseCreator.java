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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonResponseCreator<T> {
	protected final long total;
	protected final int page;
	protected final int size;
	protected final String direction;
	protected final String orderBy;
	protected final List<T> entity;
	
	@JsonCreator
	public JsonResponseCreator(@JsonProperty("total") long total, 
			@JsonProperty("page") int page, 
			@JsonProperty("size") int size, 
			@JsonProperty("direction") String direction,
			@JsonProperty("orderBy") String orderBy,
			@JsonProperty("rows") List<T> entity) {
		this.total = total;
		this.page = page;
		this.size = size;
		this.direction = direction;
		this.orderBy = orderBy;
		this.entity = entity;
	}
	
	public List<T> getRows() {
        return this.entity;
    }
	
	public long getTotal() {
		return this.total;
	}
	
	public int getPage() {
		return this.page;
	}
	
	public int getSize() {
		return this.size;
	}

	public String getDirection() {
		return this.direction;
	}

	public String getOrderBy() {
		return this.orderBy;
	}
}
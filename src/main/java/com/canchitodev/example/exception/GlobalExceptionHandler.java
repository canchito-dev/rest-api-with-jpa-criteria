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
package com.canchitodev.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE) // 415
	@ExceptionHandler(ContentNotSupportedException.class)
	@ResponseBody
	public ErrorInformation handleNotSupported(ContentNotSupportedException e) {
	  return new ErrorInformation("Content is not supported", e);
	}
	
	@ResponseStatus(HttpStatus.CONFLICT) // 409
	@ExceptionHandler(ConflictException.class)
	@ResponseBody
	public ErrorInformation handleConflict(ConflictException e) {
	  return new ErrorInformation("Conflict", e);
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)  // 404
	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseBody
	public ErrorInformation handleNotFound(ObjectNotFoundException e) {
	    return new ErrorInformation("Not found", e);
	}
	
	@ResponseStatus(HttpStatus.FORBIDDEN)  // 403
	@ExceptionHandler(ForbiddenException.class)
	@ResponseBody
	public ErrorInformation handleForbidden(ForbiddenException e) {
	    return new ErrorInformation("Forbidden", e);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	public ErrorInformation handleIllegal(IllegalArgumentException e) {
	  return new ErrorInformation("Bad request", e);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler(HttpMessageConversionException.class)
	@ResponseBody
	public ErrorInformation handleBadMessageConversion(HttpMessageConversionException e) {
	  return new ErrorInformation("Bad request", e);
	}
	
	// Fall back
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ErrorInformation handleOtherException(Exception e) {
		return new ErrorInformation("Internal server error", e);
	}
}
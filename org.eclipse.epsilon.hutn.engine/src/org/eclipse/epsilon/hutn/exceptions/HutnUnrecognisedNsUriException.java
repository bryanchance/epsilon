/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.exceptions;

public class HutnUnrecognisedNsUriException extends HutnValidationException {

	// Generated by Eclipse
	private static final long serialVersionUID = -1080099386134587595L;
	
	private final String uri;
	private final int line;
	private final int col;

	public HutnUnrecognisedNsUriException(String uri, int line, int col) {
		super("Package with URI, '" + uri + "' not found.");
		
		this.uri  = uri;
		this.line = line;
		this.col  = col;
	}
	
	public String getUri() {
		return uri;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getColumn() {
		return col;
	}
}

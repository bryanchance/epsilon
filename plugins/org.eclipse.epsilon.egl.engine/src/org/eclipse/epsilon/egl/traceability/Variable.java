/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.traceability;

import java.util.Map.Entry;
import java.util.Objects;

/**
 * 
 * @since 1.6 implements Map.Entry
 */
public class Variable extends Content<Template> implements Entry<String, Object> {

	private final String name;
	private final Object value;
	
	protected Variable(Template parent, String name, Object value) {
		super(parent);
		
		if (name == null)
			throw new NullPointerException("name cannot be null");
		
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Variable)) return false;
		
		final Variable that = (Variable) o;
		return
			Objects.equals(this.name, that.name) &&
			Objects.equals(this.value, that.value);
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		
		result += 37 * result + name.hashCode();
		result += 37 * result + Objects.hashCode(value);
		return result;
	}
	
	@Override
	public String toString() {
		return name + "=" + Objects.toString(value);
	}

	@Override
	public String getKey() {
		return name;
	}

	@Override
	public Object setValue(Object value) {
		return value;
	}
}

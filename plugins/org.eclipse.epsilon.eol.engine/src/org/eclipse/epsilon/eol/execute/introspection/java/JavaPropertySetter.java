/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.introspection.java;

import java.lang.reflect.Method;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertySetter;
import org.eclipse.epsilon.eol.execute.introspection.IReflectivePropertySetter;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributorRegistry;

public class JavaPropertySetter extends AbstractPropertySetter implements IReflectivePropertySetter {
	
	protected ObjectMethod getMethodFor(Object object, String property, Object value) {
		OperationContributorRegistry registry = context.getOperationContributorRegistry();
		
		// Look for a setX() method
		ObjectMethod om = registry.findContributedMethodForEvaluatedParameters(object, "set" + property, new Object[]{value}, context);
		if (om != null) return om;
		
		return new ObjectMethod(object);
	}
	
	@Override
	public void invoke(Object value) throws EolRuntimeException{
		ObjectMethod objectMethod = getMethodFor(object, property, value);
		Method method = objectMethod.getMethod();
		
		if (method == null) {
			throw new EolIllegalPropertyException(object, property, ast, context);
		}
		
		try {
			//TODO: use canAccess(Object)
			if (!method.isAccessible()) {
				method.setAccessible(true);
			}
			method.invoke(objectMethod.getObject(), new Object[]{value});
		}
		catch (Exception ex) {
			throw new EolInternalException(ex);
		}
	}

	@Override
	public Object coerce(Object value) throws EolIllegalPropertyException {
		return value;
	}

	@Override
	public boolean conforms(Object value) throws EolIllegalPropertyException {
		// TODO implement this method
		throw new UnsupportedOperationException("Not yet implemented.");
	}
}

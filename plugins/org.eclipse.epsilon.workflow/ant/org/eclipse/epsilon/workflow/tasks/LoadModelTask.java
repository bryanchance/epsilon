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
package org.eclipse.epsilon.workflow.tasks;

import java.util.ArrayList;

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.profiling.Profiler;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.workflow.tasks.hosts.HostManager;
import org.eclipse.epsilon.workflow.tasks.nestedelements.ParameterNestedElement;

public class LoadModelTask extends EpsilonTask{

	protected String name;
	protected String type;
	protected String config;
	protected ArrayList<ParameterNestedElement> parameterNestedElements = new ArrayList<ParameterNestedElement>();
	
	@Override
	public void executeImpl() throws BuildException {	
		ShutdownProjectRepositoryListener.activate(getProject(), getProjectRepository());
		
		if (profile) Profiler.INSTANCE.start("Load model : " + name);
		IModel model = createModel(type);
		try {
			//model.load(getStringProperties(), getBaseDir().getAbsolutePath());
			model.load(getStringProperties(), null);
			model.setName(name);
			getProjectRepository().addModel(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
		finally {
			if (profile) Profiler.INSTANCE.stop("Load model : " + name);
		}
	}
	
	protected StringProperties getStringProperties() {
		StringProperties properties = new StringProperties();
		for (ParameterNestedElement parameterNestedElement : parameterNestedElements) {
			properties.put(parameterNestedElement.getName(), parameterNestedElement.getValue());
		}
		return properties;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ParameterNestedElement createParameter() {
		ParameterNestedElement parameterNestedElement = new ParameterNestedElement();
		parameterNestedElements.add(parameterNestedElement);
		return parameterNestedElement;
	}

	protected IModel createModel(String type) throws BuildException {
		return HostManager.getHost().createModel(type);
	}
}

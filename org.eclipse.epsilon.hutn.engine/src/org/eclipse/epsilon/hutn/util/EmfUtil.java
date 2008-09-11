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
package org.eclipse.epsilon.hutn.util;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.hutn.exceptions.HutnMetaModelRegistrationException;

public abstract class EmfUtil {

	private EmfUtil() {}
	
	private final static URI DEFAULT_URI = URI.createFileURI("foo.ecore");
	
	public static Resource createResource() {
		return createResource(DEFAULT_URI);
	}
	
	public static Resource createResource(URI uri) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());

		return resourceSet.createResource(uri);
	}
	
	public static Resource createResourceFor(EObject rootObject) {
		return createResourceFor(rootObject, DEFAULT_URI);
	}
	
	public static Resource createResourceFor(EObject rootObject, URI uri) {
		final Resource resource = createResource(uri);
		resource.getContents().add(rootObject);
		return resource;
	}
	
	private static EmfModel initialiseModel(String modelName, File modelFile) {
		final EmfModel model = new EmfModel();
		model.setName(modelName);
		model.setModelFile(modelFile.getAbsolutePath());
		return model;
	}
	
	public static IModel loadModel(String modelName, File modelFile, File metaModelFile) throws EolModelLoadingException {
		final EmfModel model = initialiseModel(modelName, modelFile);
		model.setMetamodelFileBased(true);
		model.setMetamodelFile(metaModelFile.getAbsolutePath());
				
		model.load();
		return model;
	}
	
	public static IModel loadModel(String modelName, File modelFile, String metaModelUri) throws EolModelLoadingException {
		final EmfModel model = initialiseModel(modelName, modelFile);
		model.setMetamodelFileBased(false);
		model.setMetamodelUri(metaModelUri);
				
		model.load();
		return model;
	}
	
	public static IModel loadMetaModel(String modelName, File metaModelFile) throws EolModelLoadingException {
		return loadModel(modelName, metaModelFile, EcorePackage.eNS_URI);
	}
	
	public static void registerMetaModel(String nsUri, URI uri) throws HutnMetaModelRegistrationException {
		try {
			if (EPackage.Registry.INSTANCE.getEPackage(nsUri) == null) {
				org.eclipse.epsilon.emc.emf.EmfUtil.register(uri, EPackage.Registry.INSTANCE);
			}
		} catch (Exception e) {
			throw new HutnMetaModelRegistrationException("Could not register metamodel with nsUri: " + nsUri, e);
		}
	}
}

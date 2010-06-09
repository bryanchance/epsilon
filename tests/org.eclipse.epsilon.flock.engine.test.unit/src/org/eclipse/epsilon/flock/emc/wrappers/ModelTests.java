/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.flock.emc.wrappers;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IReflectivePropertySetter;
import org.eclipse.epsilon.eol.models.IReflectiveModel;
import org.eclipse.epsilon.flock.emc.wrappers.AttributeValue;
import org.eclipse.epsilon.flock.emc.wrappers.BackedModelValue;
import org.eclipse.epsilon.flock.emc.wrappers.Model;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.emc.wrappers.ModelValueWrapper;
import org.eclipse.epsilon.hutn.test.model.families.DogBreed;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage;
import org.junit.Test;

public class ModelTests {

	@SuppressWarnings("unchecked")
	@Test
	public void setPropertyValueShouldDelegateToPropertySetterAndUnwrapValue() throws EolRuntimeException {
		final IReflectiveModel          mockUnderlyingModel = createMock(IReflectiveModel.class);
		final IReflectivePropertySetter mockPropertySetter  = createMock(IReflectivePropertySetter.class);
		final BackedModelValue          mockWrappedValue    = createMock(BackedModelValue.class);
	
		
		// Expectations
		
		expect(mockWrappedValue.unwrap())
			.andReturn("bar");
		
		mockPropertySetter.setProperty("foo");
		mockPropertySetter.setObject("dummy model element");
		
		expect(mockPropertySetter.coerce("bar")).andReturn("bar");
		mockPropertySetter.invoke("bar");
		
		expect(mockUnderlyingModel.getPropertySetter())
			.andReturn(mockPropertySetter);
		
		replay(mockUnderlyingModel, mockPropertySetter, mockWrappedValue);
		
		
		// Verification
		
		new Model(mockUnderlyingModel).setValueOfProperty("dummy model element", "foo", mockWrappedValue);
		
		verify(mockUnderlyingModel, mockPropertySetter, mockWrappedValue);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getPropertyValueShouldDelegateToPropertyGetterAndWrapValue() throws EolRuntimeException {
		final IReflectiveModel  mockUnderlyingModel = createMock(IReflectiveModel.class);
		final IPropertyGetter   mockPropertyGetter  = createMock(IPropertyGetter.class);
		final ModelValueWrapper mockWrapper         = createMock(ModelValueWrapper.class);
		
		final Model model = new Model(mockUnderlyingModel, mockWrapper);
		
		
		// Expectations
		
		expect(mockUnderlyingModel.getPropertyGetter())
			.andReturn(mockPropertyGetter);
		
		expect(mockPropertyGetter.invoke("dummy model element", "foo"))
			.andReturn("bar");
		
		expect(mockWrapper.wrapValue("bar"))
			.andReturn((BackedModelValue)new AttributeValue(model, "bar"));
		
		replay(mockUnderlyingModel, mockPropertyGetter, mockWrapper);
		
		
		// Verification
		
		assertEquals(new AttributeValue(model, "bar"), model.getValueOfProperty("dummy model element", "foo"));
		
		verify(mockUnderlyingModel, mockPropertyGetter, mockWrapper);
	}
	
	@Test
	public void createInstanceDelegatesToUnderlyingModelAndWrapsValue() throws EolRuntimeException {
		final IReflectiveModel  mockUnderlyingModel = createMock(IReflectiveModel.class);
		final ModelValueWrapper mockWrapper         = createMock(ModelValueWrapper.class);
		
		final Model model = new Model(mockUnderlyingModel, mockWrapper);
		
		
		// Expectations
		
		expect(mockUnderlyingModel.createInstance("DummyType"))
			.andReturn("foo");
		
		expect(mockWrapper.wrapModelElement("foo"))
			.andReturn(new ModelElement(model, new ModelType(model, "DummyType"), "foo"));
		
		replay(mockUnderlyingModel, mockWrapper);
		
		
		// Verification
		
		assertEquals(new ModelElement(model, new ModelType(model, "DummyType"), "foo"), model.createInstance("DummyType"));
		
		verify(mockUnderlyingModel, mockWrapper);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void allContentsDelegatesToUnderlyingModelAndWrapsValues() throws EolRuntimeException {
		final IReflectiveModel  mockUnderlyingModel = createMock(IReflectiveModel.class);
		final ModelValueWrapper mockWrapper         = createMock(ModelValueWrapper.class);
		
		final Model model = new Model(mockUnderlyingModel, mockWrapper);
		
		final ModelType dummyType = new ModelType(model, "DummyType");
		
		// Expectations
		
		expect(mockUnderlyingModel.allContents())
			.andReturn((Collection)Arrays.asList("foo", "bar", "baz"));
		
		expect(mockWrapper.wrapModelElement("foo"))
			.andReturn(new ModelElement(model, dummyType, "foo"));
		
		expect(mockWrapper.wrapModelElement("bar"))
			.andReturn(new ModelElement(model, dummyType, "bar"));
		
		expect(mockWrapper.wrapModelElement("baz"))
			.andReturn(new ModelElement(model, dummyType, "baz"));
		
		replay(mockUnderlyingModel, mockWrapper);
		
		
		// Verification
		
		assertEquals(Arrays.asList(
		             	new ModelElement(model, dummyType, "foo"),
		             	new ModelElement(model, dummyType, "bar"),
		             	new ModelElement(model, dummyType, "baz")
		             ), 
		             model.allContents());
		
		verify(mockUnderlyingModel, mockWrapper);
	}
	
	
	@Test
	public void getEquivalentForEnumeratorDelegatesToUnderlyingModel() throws EolEnumerationValueNotFoundException {
		final IReflectiveModel  mockUnderlyingModel = createMock(IReflectiveModel.class);
		
		final Model model = new Model(mockUnderlyingModel);
		
		
		// Expectations
		
		expect(mockUnderlyingModel.getEnumerationValue("DogBreed", "labrador"))
			.andReturn(DogBreed.LABRADOR);
		
		replay(mockUnderlyingModel);
		

		// Verification
		
		assertEquals(DogBreed.LABRADOR, model.getEquivalent(DogBreed.LABRADOR));
		
		verify(mockUnderlyingModel);
	}
	
	@Test
	public void getEquivalentForEEnumLiteralDelegatesToUnderlyingModel() throws EolEnumerationValueNotFoundException {
		final IReflectiveModel  mockUnderlyingModel = createMock(IReflectiveModel.class);
		
		final Model model = new Model(mockUnderlyingModel);
		
		
		// Expectations
		
		expect(mockUnderlyingModel.getEnumerationValue("DogBreed", "labrador"))
			.andReturn(DogBreed.LABRADOR);
		
		replay(mockUnderlyingModel);

		
		// Verification
		
		assertEquals(DogBreed.LABRADOR, model.getEquivalent(FamiliesPackage.eINSTANCE.getDogBreed().getEEnumLiteral("labrador")));
		
		verify(mockUnderlyingModel);
	}
	
	
	@Test
	public void isManyDelegatesToUnderlyingModel() throws EolEnumerationValueNotFoundException {
		final IReflectiveModel  mockUnderlyingModel = createMock(IReflectiveModel.class);
		
		final Model model = new Model(mockUnderlyingModel);
		
		
		// Expectations
		
		expect(mockUnderlyingModel.getEnumerationValue("DogBreed", "labrador"))
			.andReturn(DogBreed.LABRADOR);
		
		replay(mockUnderlyingModel);

		
		// Verification
		
		assertEquals(DogBreed.LABRADOR, model.getEquivalent(FamiliesPackage.eINSTANCE.getDogBreed().getEEnumLiteral("labrador")));
		
		verify(mockUnderlyingModel);
	}
	
	
	@Test
	public void getIdentityDelegatesToUnderlyingModel() throws EolEnumerationValueNotFoundException {
		final IReflectiveModel  mockUnderlyingModel = createMock(IReflectiveModel.class);
		
		final Model model = new Model(mockUnderlyingModel);
		
		
		// Expectations
		
		expect(mockUnderlyingModel.getElementId("dummyModelElement"))
			.andReturn("anIdentity");
		
		replay(mockUnderlyingModel);

		
		// Verification
		
		assertEquals("anIdentity", model.getIdentity("dummyModelElement"));
		
		verify(mockUnderlyingModel);
	}
	
	@Test
	public void setIdentityDelegatesToUnderlyingModel() throws EolEnumerationValueNotFoundException {
		final IReflectiveModel  mockUnderlyingModel = createMock(IReflectiveModel.class);
		
		final Model model = new Model(mockUnderlyingModel);
		
		
		// Expectations
		
		mockUnderlyingModel.setElementId("dummyModelElement", "anIdentity");
		
		replay(mockUnderlyingModel);

		
		// Verification
		
		model.setIdentity("dummyModelElement", "anIdentity");
		
		verify(mockUnderlyingModel);
	}
}

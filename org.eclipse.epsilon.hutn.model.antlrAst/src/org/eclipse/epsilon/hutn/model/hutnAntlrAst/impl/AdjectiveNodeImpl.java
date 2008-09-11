/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: AdjectiveNodeImpl.java,v 1.3 2008/08/14 12:37:29 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.impl.NodeImpl;

import org.eclipse.epsilon.hutn.model.hutnAntlrAst.AdjectiveNode;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.HutnAntlrAstPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Adjective Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class AdjectiveNodeImpl extends NodeImpl implements AdjectiveNode {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdjectiveNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HutnAntlrAstPackage.Literals.ADJECTIVE_NODE;
	}

} //AdjectiveNodeImpl

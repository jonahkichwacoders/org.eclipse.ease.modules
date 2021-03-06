/*******************************************************************************
 * Copyright (c) 2015 Christian Pontesegger and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christian Pontesegger - initial API and implementation
 *******************************************************************************/
package org.eclipse.ease.modules.unittest.ui.views;

import org.eclipse.ease.modules.unittest.components.Test;
import org.eclipse.ease.modules.unittest.components.TestComposite;
import org.eclipse.ease.modules.unittest.components.TestStatus;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TestFileContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		// nothing to do
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		// nothing to do
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof TestComposite) {
			if (((TestComposite) inputElement).getStatus() != TestStatus.NOT_RUN)
				return ((TestComposite) inputElement).getTests().toArray();
		}

		return new Object[0];
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof Test)
			return ((Test) parentElement).getMetaData().entrySet().toArray();

		return new Object[0];
	}

	@Override
	public Object getParent(final Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof Test)
			return !((Test) element).getMetaData().isEmpty();

		return false;
	}
}

/**********************************************************************************
 * Copyright (c) 2011 Mia-Software.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  	Gabriel Barbier (Mia-Software) - initial API and implementation
 * 		Nicolas Guyomar (Mia-Software) - Bug 333652 Extension point offering the possibility to declare an EPackage browser
 ***********************************************************************************/
package org.eclipse.ease.modules.modeling.ui;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.ease.modules.modeling.Activator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * A dialog which displays a list of URIs from the EMF package registry, and allows the user to open one of them.
 *
 * @author Gabriel Barbier
 */
public class UriSelectionDialog extends ElementListSelectionDialog {

	private static final String DIALOG_SETTINGS = "UriSelectionDialogSettings"; //$NON-NLS-1$

	private String uri = ""; //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            the parent shell
	 */
	public UriSelectionDialog(final Shell parent) {
		super(parent, new LabelProvider());

		setTitle("Metamodel Selection");
		setMessage("Choose the metamodele you want to set");

		final Set<String> uris = new TreeSet<String>();

		for (final Object name : ((Map<?, ?>) EPackage.Registry.INSTANCE).keySet()) {
			uris.add((name).toString());
		}

		setElements(uris.toArray());
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		// this.fFilteredList.setFilterMatcher(new AnywhereFilterMatcher());
		return composite;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.dialogs.SelectionStatusDialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		super.okPressed();

		if (getResult().length > 0) {
			uri = getResult()[0].toString();
		}
	}

	public String getUriSelected() {
		return uri;
	}

	@Override
	protected void configureShell(final Shell shell) {
		super.configureShell(shell);
	}

	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings();
		IDialogSettings section = settings.getSection(UriSelectionDialog.DIALOG_SETTINGS);
		if (section == null) {
			section = settings.addNewSection(UriSelectionDialog.DIALOG_SETTINGS);
		}
		return section;
	}
}

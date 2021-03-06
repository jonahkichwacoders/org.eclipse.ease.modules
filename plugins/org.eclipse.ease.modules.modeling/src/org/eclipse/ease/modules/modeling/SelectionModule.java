/*******************************************************************************
 * Copyright (c) 2013 Atos
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Arthur Daussy - initial implementation
 *******************************************************************************/
package org.eclipse.ease.modules.modeling;

import org.eclipse.core.expressions.IIterable;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ease.Logger;
import org.eclipse.ease.modules.AbstractScriptModule;
import org.eclipse.ease.modules.ScriptParameter;
import org.eclipse.ease.modules.WrapToScript;
import org.eclipse.ease.modules.platform.UIModule;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.services.IEvaluationService;

import com.google.common.collect.Lists;

/**
 * Module to interact with selection
 *
 * @author adaussy
 *
 */
public class SelectionModule extends AbstractScriptModule {

	public SelectionModule() {
		super();
	}

	/**
	 * Return the current selection using the selection service. The selection service return transformed selection using some rules define in the platform.
	 * This method use the selector with the Highest priority
	 *
	 * @return
	 */
	@WrapToScript
	public Object getCustomSelection() {
		getEnvironment().getModule(UIModule.class);
		ISelection selection = UIModule.getSelection(null);
		IEvaluationService esrvc = PlatformUI.getWorkbench().getService(IEvaluationService.class);

		Object customSelection = SelectorService.getInstance().getSelectionFromContext(selection, esrvc.getCurrentState());
		if (customSelection != null) {
			return customSelection;
		}
		return selection;
	}

	/**
	 * Return the current selection using the selection service. The selection service return transformed selection using some rules define in the platform.
	 *
	 * @param selectorID
	 *            The if of the selector to use
	 * @return
	 */
	@WrapToScript
	public Object getCustomSelectionFromSelector(final String selectorID) {
		getEnvironment().getModule(UIModule.class);
		ISelection selection = UIModule.getSelection(null);
		return SelectorService.getInstance().getSelectionFromSelector(selection, selectorID);
	}

	/**
	 * Return the selection after being adapter to {@link IIterable}
	 *
	 * @return
	 */
	@WrapToScript
	public Iterable<Object> getIterableSelection(@ScriptParameter(defaultValue = ScriptParameter.NULL) final String name) {
		getEnvironment().getModule(UIModule.class);
		ISelection selection = UIModule.getSelection(name);

		IIterable result = getAdapter(IIterable.class, selection);
		if (result != null) {
			return Lists.newArrayList(result.iterator());
		}
		getEnvironment().getModule(UIModule.class);
		UIModule.showErrorDialog("Error", "The current selection is not an iterable");
		return null;
	}

	protected <T extends Object> T getAdapter(final Class<T> cla, final Object o) {
		if ((o != null) && (cla != null)) {
			if (cla.isInstance(o)) {
				return (T) o;
			} else if (o instanceof IAdaptable) {
				return ((IAdaptable) o).getAdapter(cla);
			} else {
				IAdapterManager manager = Platform.getAdapterManager();
				if (manager != null) {
					return manager.getAdapter(o, cla);
				} else {
					Logger.error(Activator.PLUGIN_ID, "Unable to get thr AdapterManger");
					return null;
				}
			}
		}
		return null;
	}
}

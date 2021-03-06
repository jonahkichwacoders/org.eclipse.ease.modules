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
package org.eclipse.ease.modules.modeling.impl;

import org.eclipse.core.expressions.Expression;
import org.eclipse.ease.modules.modeling.ISelector;

/**
 * Wrapper for {@link ISelector} that add id and Core Expression information
 *
 * @author adaussy
 *
 */
public class SelectorWrapper implements Comparable<SelectorWrapper> {

	private final ISelector selector;

	private final Expression expression;

	private final String id;

	public SelectorWrapper(final ISelector selector, final Expression exression, final String id) {
		super();
		this.selector = selector;
		this.id = id;
		expression = exression;
	}

	/**
	 * @return the selector
	 */
	public ISelector getSelector() {
		return selector;
	}

	/**
	 * @return the expression
	 */
	public Expression getExpression() {
		return expression;
	}

	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectorWrapper other = (SelectorWrapper) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(final SelectorWrapper o) {
		if (expression == o.getExpression()) {
			return 0;
		} else if (expression != null) {
			return -1;
		}
		return 1;
	}

}

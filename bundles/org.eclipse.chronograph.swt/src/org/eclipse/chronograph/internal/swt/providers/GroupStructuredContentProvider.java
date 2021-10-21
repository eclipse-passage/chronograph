package org.eclipse.chronograph.internal.swt.providers;

import java.util.Collection;

import org.eclipse.chronograph.internal.api.graphics.Group;
import org.eclipse.jface.viewers.IStructuredContentProvider;

public class GroupStructuredContentProvider implements IStructuredContentProvider {

	private AbstractStructureDataProvider provider;

	public GroupStructuredContentProvider(AbstractStructureDataProvider provider) {
		this.provider = provider;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Collection) {
			return ((Collection<?>) inputElement).toArray();
		}
		if (inputElement instanceof Object[]) {
			return (Object[]) inputElement;
		}
		if (inputElement instanceof Group) {
			return provider.getGroups(inputElement).toArray();
		}

		return new Object[] { inputElement };
	}

}

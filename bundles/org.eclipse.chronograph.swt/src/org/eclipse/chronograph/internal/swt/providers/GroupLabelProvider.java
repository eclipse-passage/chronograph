package org.eclipse.chronograph.internal.swt.providers;

import org.eclipse.chronograph.internal.api.data.LabelDataProvider;
import org.eclipse.chronograph.internal.api.graphics.Group;
import org.eclipse.jface.viewers.LabelProvider;

public class GroupLabelProvider extends LabelProvider {

	private LabelDataProvider provider;

	public GroupLabelProvider(LabelDataProvider provider) {
		this.provider = provider;
	}

	@Override
	public String getText(Object element) {

		if (element instanceof Group) {
			return provider.getText(((Group) element).data());
		}
		return provider.getText(element);
	}

}

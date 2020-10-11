package utils;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CheckBoxListRendererAnaliza extends JCheckBox implements ListCellRenderer<CheckBoxListItemAnaliza>{
	private static final long serialVersionUID = 5688834168295926935L;

	@Override
	public Component getListCellRendererComponent(
		JList<? extends CheckBoxListItemAnaliza> list, CheckBoxListItemAnaliza value,
		int index, boolean isSelected, boolean cellHasFocus) {
		setEnabled(list.isEnabled());
		setSelected(value.isSelected());
		setFont(list.getFont());
		setBackground(list.getBackground());
		setForeground(list.getForeground());
		setText(value.toString());
		return this;
	}

}

package org.danor.util;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UcTableRow
{
	private int rowAt;
	private final String[] values;
	protected final JTable table;
	
	public UcTableRow(int rowAt, int colCount, JTable table)
	{
		this.setRowAt(rowAt);
		this.values = new String[colCount];
		this.table = table;
	}
	
	public void rowLoad()
	{
		if(rowAt != -1)
		{
			for(int i=0;i<values.length;i++)
			  values[i] = (String)table.getValueAt(rowAt,i);
		}
	}
	
	public void rowSave()
	{
		if(rowAt == -1)
		{
			((DefaultTableModel)table.getModel()).addRow(values);
			rowAt = table.getRowCount()-1;
		}
		else if(rowAt >= 0)
			for(int i=0;i<values.length;i++)
				if(values[i] != null)
					((DefaultTableModel)table.getModel()).setValueAt(values[i], rowAt, i);
	}
	
	public void rowSave(int col, String value)
	{
		values[col] = value;
		rowSave();
	}

	public int getRowAt() { return rowAt; }
	public void setRowAt(int rowAt) { this.rowAt = rowAt; }
	public String[] getValues() { return values; }
}
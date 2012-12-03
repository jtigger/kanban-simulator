package com.bigvisible.kanbansimulator.app;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class WorkflowModel {
    private DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
    private IterationParameterTableModel tableModel = new IterationParameterTableModel();

    public WorkflowModel() {
        addColumnWithIdentifier("Iteration");
        addColumnWithIdentifier("Batch Size");
    }

    public TableColumnModel getIterationParameterTableColumnModel() {
        return columnModel;
    }

    public TableModel getIterationParameterTableModel() {
        return tableModel;
    }

    public void addStep(String newWorkflowStepName) {
        addColumnWithIdentifier(newWorkflowStepName);
    }

    public void removeStep(String workflowStepName) {
        int modelIndexOfColumn = columnModel.getColumnIndex(workflowStepName);
        TableColumn workflowStepColumn = columnModel.getColumn(modelIndexOfColumn);
        columnModel.removeColumn(workflowStepColumn);
    }
    
    private void addColumnWithIdentifier(String columnIdentifier) {
        TableColumn newColumn = new TableColumn();
        newColumn.setIdentifier(columnIdentifier);
        columnModel.addColumn(newColumn);
    }
    
    @SuppressWarnings("serial")
    private class IterationParameterTableModel extends AbstractTableModel {

        public int getRowCount() {
            return 0;
        }

        public int getColumnCount() {
            return columnModel.getColumnCount();
        }
        
        @Override
        public String getColumnName(int column) {
            return (String) columnModel.getColumn(column).getIdentifier();
        }
        
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return Integer.class;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return null;
        }
        
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        }
    }

}

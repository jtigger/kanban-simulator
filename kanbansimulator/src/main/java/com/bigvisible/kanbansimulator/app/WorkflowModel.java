package com.bigvisible.kanbansimulator.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.fest.swing.hierarchy.ExistingHierarchy;

import com.bigvisible.kanbansimulator.IterationParameter;
import com.bigvisible.kanbansimulator.IterationParameter.WorkflowStepParameter;

public class WorkflowModel {
    private DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
    private IterationParameterTableModel tableModel = new IterationParameterTableModel();
    private List<String> workflowStepNames = new LinkedList<String>();
    
    public static final String COLUMN_ID_ITERATION = "Iteration";
    public static final String COLUMN_ID_BATCH_SIZE = "Batch Size";

    public WorkflowModel() {
        addColumnWithIdentifier(COLUMN_ID_ITERATION);
        addColumnWithIdentifier(COLUMN_ID_BATCH_SIZE);
    }

    public TableColumnModel getIterationParameterTableColumnModel() {
        return columnModel;
    }

    public TableModel getIterationParameterTableModel() {
        return tableModel;
    }

    public void addStep(String newWorkflowStepName) {
        addColumnWithIdentifier(newWorkflowStepName);
        workflowStepNames.add(newWorkflowStepName);
    }

    public void removeStep(String workflowStepName) {
        int modelIndexOfColumn = columnModel.getColumnIndex(workflowStepName);
        TableColumn workflowStepColumn = columnModel.getColumn(modelIndexOfColumn);
        columnModel.removeColumn(workflowStepColumn);
    }

    public void addIteration() {
        tableModel.addRow();
    }

    public List<IterationParameter> getIterationParameters() {
        List<IterationParameter> allIterationParameters = new LinkedList<IterationParameter>();
        for (IterationParameterTableModel.IterationParameterRow row : tableModel.rows) {
            allIterationParameters.addAll(row.columnIdentifierToIterationParameter.values());
        }
        return allIterationParameters;
    }

    private void addColumnWithIdentifier(String columnIdentifier) {
        TableColumn newColumn = new TableColumn();
        newColumn.setIdentifier(columnIdentifier);
        newColumn.setHeaderValue(columnIdentifier);
        columnModel.addColumn(newColumn);
    }

    @SuppressWarnings("serial")
    private class IterationParameterTableModel extends AbstractTableModel {
        private class IterationParameterRow {
            Integer iteration;
            Map<String,IterationParameter> columnIdentifierToIterationParameter = new HashMap<String,IterationParameter>();
        }

        private List<IterationParameterRow> rows = new LinkedList<IterationParameterRow>();

        public int getRowCount() {
            return rows.size();
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
            Object value = null;
            boolean isSettingBatchSize = (columnIndex == 1);

            IterationParameterRow row = rows.get(rowIndex);

            if (columnIndex == 0) {
                value = row.iteration;
            } else {
                String columnIdentifier = (String) columnModel.getColumn(columnIndex).getIdentifier();
                IterationParameter iterationParameter = row.columnIdentifierToIterationParameter.get(columnIdentifier);

                if (isSettingBatchSize) {
                    value = iterationParameter != null ? iterationParameter.getBatchSize() : null;
                } else {
                    value = iterationParameter != null ? iterationParameter.getCapacity() : null;
                }
            }
            
            return value;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            IterationParameterRow row = rows.get(rowIndex);

            boolean isSettingBatchSize = (columnIndex == 1);

            IterationParameter newParameter = null;

            if (isSettingBatchSize) {
                Integer batchSize = (Integer) aValue;
                newParameter = IterationParameter.startingAt(row.iteration).setBatchSize(batchSize);
            } else {
                Integer capacity = (Integer) aValue;
                String workflowStepName = (String) columnModel.getColumn(columnIndex).getIdentifier();
                newParameter = IterationParameter.startingAt(row.iteration).forStep(
                        WorkflowStepParameter.named(workflowStepName).setCapacity(capacity));
            }

            if (newParameter != null) {
                String columnIdentifier = newParameter.hasWorkflowConfiguration() ? newParameter
                        .getWorkflowStepName() : COLUMN_ID_BATCH_SIZE;
                row.columnIdentifierToIterationParameter.remove(columnIdentifier);
                if (aValue != null) {
                    row.columnIdentifierToIterationParameter.put(columnIdentifier, newParameter);
                }

                fireTableCellUpdated(rowIndex, columnIndex);
            }
        }

        public void addRow() {
            IterationParameterRow row = new IterationParameterRow();
            row.iteration = rows.size() + 1;
            rows.add(row);
        }
    }
}

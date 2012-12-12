package com.bigvisible.kanbansimulator.app;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.bigvisible.kanbansimulator.IterationParameter;

public class WorkflowModel {
    private DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
    private IterationParameterTableModel tableModel = new IterationParameterTableModel();
    private List<String> workflowStepNames = new LinkedList<String>();

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
            allIterationParameters.addAll(row.iterationParameters);
        }
        return allIterationParameters;
    }

    private void addColumnWithIdentifier(String columnIdentifier) {
        TableColumn newColumn = new TableColumn();
        newColumn.setIdentifier(columnIdentifier);
        columnModel.addColumn(newColumn);
    }

    @SuppressWarnings("serial")
    private class IterationParameterTableModel extends AbstractTableModel {
        private class IterationParameterRow {
            Integer iteration;
            Integer batchSize;
            List<IterationParameter> iterationParameters = new LinkedList<IterationParameter>();
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
            return null;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            IterationParameterRow row = rows.get(rowIndex);
            List<IterationParameter> existingParameters = row.iterationParameters;

            boolean isSettingBatchSize = (columnIndex == 1);
            
            IterationParameter newParameter = null;

            if (isSettingBatchSize) {
                Integer batchSize = (Integer) aValue;
                newParameter = IterationParameter.startingAt(row.iteration).setBatchSize(batchSize);
            }

            if (newParameter != null) {
                removeIterationParameterIfExists(newParameter, existingParameters);
                if (aValue != null) {
                    existingParameters.add(newParameter);
                }

                fireTableCellUpdated(rowIndex, columnIndex);
            }
        }

        private void removeIterationParameterIfExists(IterationParameter iterationParameter,
                List<IterationParameter> iterationParameters) {
            Iterator<IterationParameter> iterationParametersItr = iterationParameters.iterator();
            while (iterationParametersItr.hasNext()) {
                IterationParameter existingParameter = iterationParametersItr.next();
                // TODO-TODAY: figure out how to fold batch size and capacity settings in the IterationParameter
                if (!existingParameter.hasWorkflowConfiguration() && !iterationParameter.hasWorkflowConfiguration()) {
                    iterationParametersItr.remove();
                }
            }
        }

        public void addRow() {
            IterationParameterRow row = new IterationParameterRow();
            row.iteration = rows.size() + 1;
            rows.add(row);
        }
    }
}

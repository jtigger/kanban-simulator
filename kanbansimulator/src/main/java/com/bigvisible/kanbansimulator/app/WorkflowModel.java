package com.bigvisible.kanbansimulator.app;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class WorkflowModel {
    private DefaultTableColumnModel columnModel = new DefaultTableColumnModel();

    public WorkflowModel() {
        TableColumn iterationColumn = new TableColumn();
        iterationColumn.setIdentifier("Iteration");

        TableColumn batchSizeColumn = new TableColumn();
        batchSizeColumn.setIdentifier("Batch Size");

        columnModel.addColumn(iterationColumn);
        columnModel.addColumn(batchSizeColumn);
    }

    public TableColumnModel getIterationParameterTableColumnModel() {
        return columnModel;
    }

    public void addStep(String newWorkflowStepName) {
        TableColumn newColumn = new TableColumn();
        newColumn.setIdentifier(newWorkflowStepName);
        columnModel.addColumn(newColumn);
    }

    public void removeStep(String workflowStepName) {
        int modelIndexOfColumn = columnModel.getColumnIndex(workflowStepName);
        TableColumn workflowStepColumn = columnModel.getColumn(modelIndexOfColumn);
        columnModel.removeColumn(workflowStepColumn);
    }

}

package com.bigvisible.kanbansimulatortester.app;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.junit.Test;

import com.bigvisible.kanbansimulator.app.WorkflowModel;

/**
 * A {@link WorkflowModel} provides the models that the Swing {@link JTable}.  This specification describes how
 * making changes to the {@link WorkflowModel} affects the model that the {@link JTable} listens to.
 */
public class WorkflowModelAsAModelForAJTableSpec {

    @Test
    public void by_default_a_WorkflowModel_has_a_Swing_TableColumn_named_iteration() throws Exception {
        WorkflowModel workflowModel = new WorkflowModel();
        String iterationColumnIdentifier = "Iteration";
        List<String> columnIdentifiers = collectColumnIdentifiers(workflowModel);
        assertThat(columnIdentifiers, hasItem(iterationColumnIdentifier));
    }

    @Test
    public void by_default_a_WorkflowModel_has_a_Swing_TableColumn_named_batch_size() throws Exception {
        WorkflowModel workflowModel = new WorkflowModel();
        String iterationColumnIdentifier = "Batch Size";
        List<String> columnIdentifiers = collectColumnIdentifiers(workflowModel);
        assertThat(columnIdentifiers, hasItem(iterationColumnIdentifier));
    }
    
    @Test
    public void WHEN_told_to_add_a_workflow_step_THEN_the_iteration_paramater_table_has_a_Swing_TableColumn_of_the_same_name() throws Exception {
        WorkflowModel workflowModel = new WorkflowModel();
        String newWorkflowStepName = "BA";

        workflowModel.addStep(newWorkflowStepName);
        List<String> columnIdentifiers = collectColumnIdentifiers(workflowModel);
        assertThat(columnIdentifiers, hasItem(newWorkflowStepName));
    }
    
    @Test
    public void WHEN_told_to_remove_an_existing_workflow_step_THEN_the_iteration_parameter_JTable_no_longer_has_the_corresponding_TableColumn() throws Exception {
        WorkflowModel workflowModel = new WorkflowModel();
        String newWorkflowStepName = "BA";
        workflowModel.addStep(newWorkflowStepName);
        
        workflowModel.removeStep(newWorkflowStepName);
        List<String> columnIdentifiers = collectColumnIdentifiers(workflowModel);
        assertThat(columnIdentifiers, not(hasItem(newWorkflowStepName)));
    }
    
    private List<String> collectColumnIdentifiers(WorkflowModel workflowModel) {
        TableColumnModel columnModel = workflowModel.getIterationParameterTableColumnModel();
        List<TableColumn> listOfColumns = Collections.list(columnModel.getColumns());
        List<String> listOfIdentifiers = new ArrayList<String>(listOfColumns.size());
        for (TableColumn tableColumn : listOfColumns) {
            listOfIdentifiers.add((String)tableColumn.getIdentifier());
        }
        List<String> columnIdentifiers = listOfIdentifiers;
        return columnIdentifiers;
    }

}

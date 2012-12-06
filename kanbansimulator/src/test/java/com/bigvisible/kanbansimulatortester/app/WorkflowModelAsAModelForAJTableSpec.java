package com.bigvisible.kanbansimulatortester.app;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.junit.Ignore;
import org.junit.Test;

import com.bigvisible.kanbansimulator.app.WorkflowModel;

/**
 * A {@link WorkflowModel} provides the models that the Swing {@link JTable}.  This specification describes how
 * making changes to the {@link WorkflowModel} affects the model that the {@link JTable} listens to.
 */
public class WorkflowModelAsAModelForAJTableSpec {

    @Test
    public void by_default_a_WorkflowModel_has_a_TableModel() throws Exception {
        WorkflowModel workflowModel = new WorkflowModel();
        
        TableModel tableModel = workflowModel.getIterationParameterTableModel();
        
        assertThat(tableModel.getColumnCount(), is(2));
    }

    @Test
    public void the_TableModel_stores_nothing_but_Integers() throws Exception {
        WorkflowModel workflowModel = new WorkflowModel();
        
        for (int columnIndex = 0; columnIndex < workflowModel.getIterationParameterTableModel().getColumnCount(); columnIndex++) {
            assertEquals(workflowModel.getIterationParameterTableModel().getColumnClass(columnIndex), Integer.class);
        }
    }

    @Test
    public void by_default_a_WorkflowModel_has_a_TableColumn_named_iteration() throws Exception {
        WorkflowModel workflowModel = new WorkflowModel();
        String iterationColumnIdentifier = "Iteration";
        List<String> columnIdentifiers = collectColumnIdentifiers(workflowModel);
        assertThat(columnIdentifiers, hasItem(iterationColumnIdentifier));
    }

    @Test
    public void by_default_a_WorkflowModel_has_a_TableColumn_named_batch_size() throws Exception {
        WorkflowModel workflowModel = new WorkflowModel();
        String iterationColumnIdentifier = "Batch Size";
        List<String> columnIdentifiers = collectColumnIdentifiers(workflowModel);
        assertThat(columnIdentifiers, hasItem(iterationColumnIdentifier));
    }
    
    @Test
    public void WHEN_told_to_add_a_workflow_step_THEN_the_iteration_parameter_table_has_a_TableColumn_of_the_same_name() throws Exception {
        WorkflowModel workflowModel = new WorkflowModel();
        String newWorkflowStepName = "BA";

        workflowModel.addStep(newWorkflowStepName);
        List<String> columnIdentifiers = collectColumnIdentifiers(workflowModel);
        assertThat(columnIdentifiers, hasItem(newWorkflowStepName));
    }
    
    @Test
    public void WHEN_told_to_remove_an_existing_workflow_step_THEN_the_TableColumnModel_no_longer_has_the_corresponding_TableColumn() throws Exception {
        WorkflowModel workflowModel = new WorkflowModel();
        String newWorkflowStepName = "BA";
        workflowModel.addStep(newWorkflowStepName);
        
        workflowModel.removeStep(newWorkflowStepName);
        List<String> columnIdentifiers = collectColumnIdentifiers(workflowModel);
        assertThat(columnIdentifiers, not(hasItem(newWorkflowStepName)));
    }
    
    @Test
    public void WHEN_told_to_add_a_new_iteration_THEN_the_TableModel_has_a_new_row() throws Exception {
      WorkflowModel workflowModel = new WorkflowModel();
      int numRowsBeforeAddingIteration = workflowModel.getIterationParameterTableModel().getRowCount();
      workflowModel.addIteration();
      int numRowsAfterAddingIteration = workflowModel.getIterationParameterTableModel().getRowCount();
      assertThat(numRowsAfterAddingIteration, is(numRowsBeforeAddingIteration+1));
    }
    
    @Ignore(value="Not yet implemented")
    @Test
    public void attempts_to_set_a_value_for_any_row_in_the_Iteration_column_is_ignored() throws Exception {
    }
    
    @Ignore(value="Not yet implemented")
    @Test
    public void WHEN_a_value_is_set_in_the_table_for_batch_size_AND_the_WorkflowModel_is_asked_for_simulator_configuration_THEN_a_corresponding_IterationParameter_is_included() throws Exception {
        
    }

    @Ignore(value="Not yet implemented")
    @Test
    public void WHEN_a_value_is_set_in_the_table_for_a_WorkflowStep_capacity_AND_the_WorkflowModel_is_asked_for_simulator_configuration_THEN_a_corresponding_IterationParameter_is_included() throws Exception {
        
    }

    @Ignore(value="Not yet implemented")
    @Test
    public void WHEN_told_to_set_iteration_parameter_data_en_mass_THEN_the_corresponding_data_in_the_TableModel_matches() throws Exception {
        
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

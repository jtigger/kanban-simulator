

# Author:: John S. Ryan (jtigger@infosysengr.com)
class WorkflowTestHelper
  def WorkflowTestHelper.add_testworkflow_to_workflow_class
    class << Workflow
      def Workflow.TestWorkflow
        workflow = Workflow.new("TestWorkflow")

        workflow.steps << WorkflowStep.new("In Analysis") { |step| step.wip_limit = 3 }
        workflow.steps << WorkflowStep.new("In Dev") { |step| step.wip_limit = 3 }
        workflow.steps << WorkflowStep.new("In Test") { |step| step.wip_limit = 3 }
        workflow.steps << WorkflowStep.new("Done") { |step| step.wip_limit = nil }

        workflow
      end
    end    
  end
  
  def WorkflowTestHelper.remove_testworkflow_from_workflow_class
    class << Workflow
      remove_method(:TestWorkflow)
    end
  end
  
end
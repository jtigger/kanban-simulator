require File.dirname(__FILE__) + "/./workflow_step.rb"

# Author:: John S. Ryan (jtigger@infosysengr.com)
class Workflow

  # Creates a fresh Kanban workflow
  def Workflow.Kanban
    workflow = []
    workflow << WorkflowStep.new("In Analysis", 3)
    workflow << WorkflowStep.new("In Dev", 3)
    workflow << WorkflowStep.new("In Test", 3)
    workflow << WorkflowStep.new("Done", nil)
    
    return workflow
  end
  
end
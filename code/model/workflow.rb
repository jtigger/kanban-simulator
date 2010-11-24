require File.dirname(__FILE__) + "/./workflow_step.rb"

# Author:: John S. Ryan (jtigger@infosysengr.com)
class Workflow
  attr_accessor :name
  attr_accessor :steps

  def initialize(name)
    @steps = []
    @name = name
  end

  # Creates a fresh Kanban workflow
  def Workflow.Kanban
    workflow = Workflow.new("Kanban")
    
    workflow.steps << WorkflowStep.new("In Analysis", 3)
    workflow.steps << WorkflowStep.new("In Dev", 3)
    workflow.steps << WorkflowStep.new("In Test", 3)
    workflow.steps << WorkflowStep.new("Done", nil)
    
    workflow
  end
  
end
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
    
    workflow.steps << WorkflowStep.new("In Analysis") { |step| step.wip_limit = 3 }
    workflow.steps << WorkflowStep.new("In Dev") { |step| step.wip_limit = 3 }
    workflow.steps << WorkflowStep.new("In Test") { |step| step.wip_limit = 3 }
    workflow.steps << WorkflowStep.new("Done") { |step| step.wip_limit = nil }
    
    workflow
  end
  
end
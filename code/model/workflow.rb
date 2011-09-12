require 'observer'
require File.dirname(__FILE__) + "/./workflow_step.rb"
require File.dirname(__FILE__) + "/../lang/array.rb"

# Author:: John S. Ryan (jtigger@infosysengr.com)
class Workflow
  include Observable
  
  attr_accessor :name
  attr_accessor :steps

  def initialize(name="", observer=nil)
    @name = name

    @steps = [].make_observable
    @steps.add_observer(self)

    add_observer(observer) if observer != nil

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

  def is_backlog?(step)
    @steps.index(step) == 0
  end

  def reverse_each # :yield: 
    @steps.reverse_each do |step|
      break if is_backlog?(step)
      previous_step = @steps[@steps.index(step)-1]
      yield step, previous_step
    end
  end

  def update(event)
    changed
    notify_observers(event)
  end  
end
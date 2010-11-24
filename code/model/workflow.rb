require File.dirname(__FILE__) + "/./kanban_process_step.rb"

# Author:: John S. Ryan (jtigger@infosysengr.com)
class Workflow

  # Creates a fresh Kanban workflow
  def Workflow.Kanban
    workflow = []
    workflow << KanbanProcessStep.new("In Analysis", 3)
    workflow << KanbanProcessStep.new("In Dev", 3)
    workflow << KanbanProcessStep.new("In Test", 3)
    workflow << KanbanProcessStep.new("Done", nil)
    
    return workflow
  end
  
end
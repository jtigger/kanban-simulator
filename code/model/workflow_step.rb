
# A step in a Kanban-style workflow.
#
# Author:: John S. Ryan (jtigger@infosysengr.com)
class WorkflowStep
  attr_accessor :name
  attr_accessor :wip_limit
  
  def initialize(name, wip_limit)
    @name = name
    @wip_limit = wip_limit
  end
end
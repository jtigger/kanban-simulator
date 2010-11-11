
# A step in a Kanban-style workflow.
#
# Author:: John S. Ryan (jtigger@infosysengr.com)
class KanbanProcessStep
  attr_accessor :name
  attr_accessor :wip_limit
  
  def initialize(new_name, new_wip_limit)
    @name = new_name
    @wip_limit = new_wip_limit
  end
end
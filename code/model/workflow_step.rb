
# A single step in a workflow.
#
# WorkflowSteps support dynamic addition of properties.  To add a new property
# to an instance of WorkflowStep, set a value for it:
#  step = WorkflowStep.new("In QA")
#  step.wip_limit = 4
#
# or supply a block to initialize with (which is passed "self"):
#
#  step = WorkflowStep.new("In QA") { |step| step.wip_limit = 4 }
#
# Note that this new property is only available on that instance of WorkflowStep.
#
# Author:: John S. Ryan (jtigger@infosysengr.com)
class WorkflowStep
  attr_accessor :name
  attr_accessor :wip
  
  # Initializes a workflow step
  def initialize(name) # :yield: (passed a ref to "self"); initialization logic.
    @name = name
    @wip = []
    yield self if block_given?
  end
  
  # whether or not another story card can be pulled from queue into wip
  def can_pull?
    true
  end

  Ends_With_Equal_Sign = /(.*)=$/
  # when a setter is called (i.e. a method ending with an '=' character),
  # and that attribute does not already, exists, dynamically adds the 
  # attr_accessor for that attribute.
  def method_missing(symbol, *args)
    if symbol.to_s =~ Ends_With_Equal_Sign
      attr_name = Ends_With_Equal_Sign.match(symbol.to_s)[1]
      add_property attr_name
      self.send(symbol, *args)
    else
      super.method_missing(symbol, args)
    end
  end
  
  def has_completed_work_items?
    @wip.each do |work_item|
      if work_item.completed_current_step?
        return true
      end
    end
    return false
  end
  
  def pop_next_completed_work_item
    @wip.each do |work_item|
      if work_item.completed_current_step?
        @wip.delete(work_item)
        return work_item
      end
    end
  end
  
private
  def add_property(name)
    # see also: https://github.com/jtigger/kanban-simulator/wiki/idiom%3A-Singleton-Class
    (class << self; self; end).class_eval do  # add to this instance
      attr_accessor name.to_sym
    end
  end
end
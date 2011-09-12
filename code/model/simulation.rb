require "observer"
require File.dirname(__FILE__) + "/work_item.rb"
require File.dirname(__FILE__) + "/config/configuration_parser.rb"
require File.dirname(__FILE__) + "/../lang/array.rb"

# Driver for executing a workflow simulation.
#
# Author:: John S. Ryan (jtigger@infosysengr.com)
class Simulation
  include Observable
  attr_accessor :work_items  
  attr_accessor :workflow     # setter is redefined below
  attr_reader   :cycle
  
  # sets the simulation's workflow by copying the definition supplied;
  # while doing so, signaling to observers of the change.
  def workflow=(workflow)
    if !@work_items.empty?
      raise "Can not reset the workflow once story cards have been added to the simulation."
    end
    
    # TODO: remove each step in the existing workflow to generate the appropriate events.
    @workflow = Workflow.new(workflow.name, self)
    workflow.steps.each { |step|
      @workflow.steps << step
    }
  end
  
  def initialize(workflow=nil, observer=nil)
    add_observer(observer) if observer != nil
    self.workflow=(workflow) if workflow != nil
    reset
  end
  
  def reset
    @cycle = -1
    @work_items = [].make_observable
    @work_items.add_observer(self)
  end
  
  def cleanup
    reset
  end
  
  # Adds so many additional stories to the backlog.
  def generate_to_backlog(num_of_work_items) # :yield: initialization logic
    (1..num_of_work_items).each { |idx|
      work_item = WorkItem.new
      yield work_item, idx if block_given?
      add_to_backlog(work_item)
    }
  end
  
  def add_to_backlog(work_item)
    @work_items << work_item
    
    # first step is assumed to be a "backlog"
    workflow.steps[0].wip << work_item
    work_item.start_work(0)
  end
  
  # :config_plan: is one of the following:
  #  1. the output of ConfigurationParser.parse()
  #  2. a string containing contents parse-able by ConfigurationParser
  #  3. a pathname to a file containing data parse-able by ConfigurationParser
  def configure(config_plan_input)
    config_plan = config_plan_input
    if config_plan_input.kind_of? String
      # perhaps, the input is a string containing configuration.
      parser = ConfigurationParser.new
      config_plan = parser.parse(config_plan_input)
      if config_plan.empty?
        # perhaps, then, the input is a pathname?
        if File.file?(config_plan_input)
          config = File.open(config_plan_input) { |f| f.read }
          config_plan = parser.parse(config)
        end
      end
    end
    config_plan.each { |config_step| config_step.configure self };
  end
  
  def step
    @cycle += 1
    update({ :action => :cycle_start, :time => self.cycle })
    
    promote
    pull
    work
    
    update({ :action => :cycle_end, :time => self.cycle })
  end

  # Being an "Observer" of Workflows and Story Cards, propagate any events to listeners of 
  # this simulation.
  def update(event)
    changed
    notify_observers(event)
  end
  
private  
  def promote
    @workflow.steps.each do |step|
      break if @workflow.steps.index(step)+1 == @workflow.steps.size  # can't promote past the penultimate step

      next_step = @workflow.steps[@workflow.steps.index(step)+1] 
      step.wip.each do |work_item|
        if work_item.completed_current_step?
          step.wip.delete(work_item)
          next_step.queue << work_item
          update({:action => :promote, :work_item => work_item.dup, :step => step.dup })
        end
      end
    end
  end

  def pull
    @workflow.steps.each do |step|
      while !step.queue.empty? && step.can_pull?
        work_item = step.queue.pop
        step.wip.push(work_item)
        update({ :action => :pull, :work_item => work_item.dup, :step => step.dup })
      end
    end

  end
  
  def work
    @workflow.steps.each do |step|
      break if @workflow.steps.index(step) == @workflow.steps.size  # can't work what's in the last step

      step.wip.each do |work_item|
        work_item.work
      end
    end
  end

end
require "observer"
require File.dirname(__FILE__) + "/story_card.rb"
require File.dirname(__FILE__) + "/config/configuration_parser.rb"


# Driver for executing a workflow simulation.
#
# Author:: John S. Ryan (jtigger@infosysengr.com)
class Simulation
  include Observable
  attr_accessor :story_cards  
  attr_accessor :workflow     # setter is redefined below
  
  # sets the simulation's workflow by copying the definition supplied;
  # while doing so, signaling to observers of the change.
  def workflow=(workflow)
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
    @story_cards = []
  end
  
  # Adds so many additional stories to the backlog.
  def add_to_backlog(num_of_story_cards) # :yield: initialization logic
    (1..num_of_story_cards).each { |idx|
      story_card = StoryCard.new
      yield story_card, idx if block_given?
      @story_cards << story_card
    }
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
  
  def cleanup
    reset
  end

  # Being an "Observer" of Workflows, propagate any events to listeners of 
  # this simulation.
  def update(event)
    changed
    notify_observers(event)
  end
end
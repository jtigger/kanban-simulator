require File.dirname(__FILE__) + "/story_card.rb"
require File.dirname(__FILE__) + "/config/configuration_parser.rb"


# Driver for executing a workflow simulation.
#
# Author:: John S. Ryan (jtigger@infosysengr.com)
class Simulation
  attr_accessor :story_cards
  attr_accessor :workflow
  
  def initialize
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
  
end
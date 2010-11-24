require File.dirname(__FILE__) + "/../model/story_card.rb"

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
  
  def configure(config_plan)
    config_plan.each { |config_step| config_step.configure self };
  end
  
  def cleanup
    reset
  end
  
end
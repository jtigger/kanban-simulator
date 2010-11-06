require File.dirname(__FILE__) + "/../model/story_card.rb"

class Simulator
  
  attr_accessor :story_cards
  
  def initialize
    @story_cards = []
  end
  
  def create_initial_backlog(num_of_story_cards)
    (1..num_of_story_cards).each {
      @story_cards << StoryCard.new()
    }
  end
  
  def cleanup
    @story_cards = nil
  end
  
end
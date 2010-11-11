require "test/unit"

require File.dirname(__FILE__) + "/../code/app/simulator.rb"
require File.dirname(__FILE__) + "/../code/lang/enumerable.rb"
require File.dirname(__FILE__) + "/../code/model/kanban_process_step.rb"


# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestSimulator < Test::Unit::TestCase
  def setup
    @simulator = Simulator.new()
  end
  
  def teardown
    @simulator.cleanup()
  end

  def test_init
    @simulator.add_to_backlog(20)
    assert_equal(20, @simulator.story_cards.size)
  end
  
  def test_different_size_backlogs
    @simulator.add_to_backlog(10)
    assert_equal(10, @simulator.story_cards.size)
   
    @simulator.reset
    @simulator.add_to_backlog(30)
    assert_equal(30, @simulator.story_cards.size)
  end
  
  # From Story #1:
  # Acceptance Criteria
  # 2. After the backlog is created I should be able to see the list of story cards created in priority 
  # order along with the randomly generated Size Estimate.
  def test_generate_story1_backlog
    @simulator.add_to_backlog(20) do |story_card, idx|
      story_card.priority = idx
      story_card.estimated_points = StoryCard::Acceptable_Point_Values[rand(StoryCard::Acceptable_Point_Values.length)]
    end
    
    expected_priority = 1
    estimations = []
    @simulator.story_cards.each { |story_card|
      estimations << story_card.estimated_points
      assert_equal(expected_priority, story_card.priority)
      expected_priority += 1
    }
    
    # assert that estimations are basically "random"
    assert(estimations.standard_deviation > 10.0)
  end
  
  def test_generate_story2_workflow
     workflow = []
     workflow << KanbanProcessStep.new("In Analysis", 3)
     workflow << KanbanProcessStep.new("In Dev", 3)
     workflow << KanbanProcessStep.new("In Test", 3)
     workflow << KanbanProcessStep.new("Done", nil)
     @simulator.workflow = workflow
  end
end
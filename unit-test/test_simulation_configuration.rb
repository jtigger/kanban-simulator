require "test/unit"
require "Tempfile"

require File.dirname(__FILE__) + "/../code/lang/enumerable.rb"
require File.dirname(__FILE__) + "/../code/model/simulation.rb"
require File.dirname(__FILE__) + "/../code/model/workflow_step.rb"
require File.dirname(__FILE__) + "/helpers/workflow_test_helper.rb"


# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestSimulationConfiguration < Test::Unit::TestCase
  
  def setup
    WorkflowTestHelper.add_testworkflow_to_workflow_class    
    @simulation = Simulation.new()
    @simulation.workflow = Workflow.TestWorkflow
  end
  
  def teardown
    @simulation.cleanup()
    WorkflowTestHelper.remove_testworkflow_from_workflow_class
  end

  def test_init
    @simulation.generate_to_backlog(20)
    assert_equal(20, @simulation.story_cards.size)
  end
  
  def test_different_size_backlogs
    @simulation.generate_to_backlog(10)
    assert_equal(10, @simulation.story_cards.size)
   
    @simulation.reset
    @simulation.generate_to_backlog(30)
    assert_equal(30, @simulation.story_cards.size)
  end
  
  # From Story #1:
  # Acceptance Criteria
  # 2. After the backlog is created I should be able to see the list of story cards created in priority 
  # order along with the randomly generated Size Estimate.
  def test_generate_story1_backlog
    @simulation.generate_to_backlog(20) do |story_card, idx|
      story_card.priority = idx
      story_card.estimated_points = StoryCard::Acceptable_Point_Values[rand(StoryCard::Acceptable_Point_Values.length)]
    end
    
    expected_priority = 1
    estimations = []
    @simulation.story_cards.each { |story_card|
      estimations << story_card.estimated_points
      assert_equal(expected_priority, story_card.priority)
      expected_priority += 1
    }
    
    # assert that estimations are basically "random"
    assert(estimations.standard_deviation > 10.0)
  end
  
  def test_load_config_from_file
    config = Tempfile.new("test-config")
    config.puts('Using "Kanban".')
    config.puts('where the "WIP limit" of "In Analysis" is 4')
    config.puts('where the "WIP limit" of "In Dev" is 4')
    config.puts('where the "WIP limit" of "In Test" is 4')
    config.close
    
    @simulation.configure(config.path)
    
    assert_equal(4, @simulation.workflow.steps[1].wip_limit)
  end
  
  def test_changing_workflow_after_adding_cards_fails
    @simulation.generate_to_backlog(1)
    assert_raises (RuntimeError) { @simulation.workflow = Workflow.new("test") }
  end
end
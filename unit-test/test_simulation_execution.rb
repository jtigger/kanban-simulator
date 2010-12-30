require "test/unit"

require File.dirname(__FILE__) + "/../code/model/simulation.rb"
require File.dirname(__FILE__) + "/helpers/workflow_test_helper.rb"
require File.dirname(__FILE__) + "/mocks/mock_simulation_observer.rb"

# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestSimulationExecution < Test::Unit::TestCase
  
  def setup
    WorkflowTestHelper.add_testworkflow_to_workflow_class
    @simulation_observer = MockSimulationObserver.new
    @simulation = Simulation.new
    @simulation.add_observer(@simulation_observer)
    @simulation.workflow = Workflow.TestWorkflow
  end
  
  def teardown
    @simulation.cleanup()
    WorkflowTestHelper.remove_testworkflow_from_workflow_class
  end

  def test_notified_when_steps_are_added
    assert(@simulation_observer.received_event( { :action => :push, :object => WorkflowStep.new("In Analysis") } ))
    assert(@simulation_observer.received_event( { :action => :push, :object => WorkflowStep.new("In Dev") } ))
    assert(@simulation_observer.received_event( { :action => :push, :object => WorkflowStep.new("In Test") } ))
    assert(@simulation_observer.received_event( { :action => :push, :object => WorkflowStep.new("Done") } ))
  end
  
  def test_notified_when_story_cards_are_added
    @simulation.add_to_backlog( StoryCard.new() { |card| 
      card.id = "card-1"
      card.name = "As a Simulation Administrator, I want to..."
      card.estimated_points = 2
    } )
    @simulation.add_to_backlog( StoryCard.new() { |card| 
      card.id = "card-2"
      card.name = "As a Simulation Administrator, I want to..."
      card.estimated_points = 3
    } )
    @simulation.add_to_backlog( StoryCard.new() { |card| 
      card.id = "card-3"
      card.name = "As a Simulation Administrator, I want to..."
      card.estimated_points = 1
    } )
    
    assert(@simulation_observer.received_event( { :action => :push, :object => StoryCard.new() {|c| c.id = "card-1" } } ))
    assert(@simulation_observer.received_event( { :action => :push, :object => StoryCard.new() {|c| c.id = "card-2" } } ))
    assert(@simulation_observer.received_event( { :action => :push, :object => StoryCard.new() {|c| c.id = "card-3" } } ))
  end
  
  def test_notified_when_simulation_ticks
    @simulation.step
    assert(@simulation_observer.received_event( { :action => :cycle_start, :time => 0} ))
    assert(@simulation_observer.received_event( { :action => :cycle_end, :time => 0} ))
    @simulation.step
    assert(@simulation_observer.received_event( { :action => :cycle_start, :time => 1} ))
    assert(@simulation_observer.received_event( { :action => :cycle_end, :time => 1} ))
  end
  
  def test_story_card_advances_through_steps
    @simulation.add_to_backlog( StoryCard.new() { |card| 
      card.id = "card-1"
      card.name = "As a Simulation Administrator, I want to..."
      card.estimated_points = 1
    } )
    
    @simulation.workflow.steps.size.times { @simulation.step }
        
    story1 = StoryCard.new() {|c| c.id = "card-1" }
      
    @simulation.workflow.steps.each do |step|
      assert(@simulation_observer.received_event( { :action => :promote, :story_card => story1, :step => step } ))
      assert(@simulation_observer.received_event( { :action => :pull, :story_card => story1, :step => step } ))
    end  
  
  end
end

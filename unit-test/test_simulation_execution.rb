require "test/unit"

require File.dirname(__FILE__) + "/../code/model/simulation.rb"
require File.dirname(__FILE__) + "/helpers/workflow_test_helper.rb"

# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestSimulationExecution < Test::Unit::TestCase
  
  def setup
    WorkflowTestHelper.add_testworkflow_to_workflow_class
    @simulation_observer = MockSimulationObserver.new
    @simulation = Simulation.new
    @simulation.add_observer(@simulation_observer)
  end
  
  def teardown
    @simulation.cleanup()
    WorkflowTestHelper.remove_testworkflow_from_workflow_class
  end

  def test_notified_when_steps_are_added
    @simulation.workflow = Workflow.TestWorkflow
    assert(@simulation_observer.received_event( { :action => "add", :object => WorkflowStep.new("In Analysis") } ))
    assert(@simulation_observer.received_event( { :action => "add", :object => WorkflowStep.new("In Dev") } ))
    assert(@simulation_observer.received_event( { :action => "add", :object => WorkflowStep.new("In Test") } ))
    assert(@simulation_observer.received_event( { :action => "add", :object => WorkflowStep.new("Done") } ))
  end
  
end

class MockSimulationObserver

  def initialize
    @events = []
  end
  
  def update(event)
    @events << event
  end
  
  def received_event(required_event)
    found = @events.find do |event|
      found = true
      found = found and required_event[:action] == event[:action]
      found = found and required_event[:object].name == event[:object].name
      found
    end      
    return found != nil && found
  end
end
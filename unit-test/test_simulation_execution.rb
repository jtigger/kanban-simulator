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
  end
  
  def teardown
    @simulation.cleanup()
    WorkflowTestHelper.remove_testworkflow_from_workflow_class
  end

  def test_notified_when_steps_are_added
    @simulation.workflow = Workflow.TestWorkflow
    assert(@simulation_observer.received_event( { :action => :push, :object => WorkflowStep.new("In Analysis") } ))
    assert(@simulation_observer.received_event( { :action => :push, :object => WorkflowStep.new("In Dev") } ))
    assert(@simulation_observer.received_event( { :action => :push, :object => WorkflowStep.new("In Test") } ))
    assert(@simulation_observer.received_event( { :action => :push, :object => WorkflowStep.new("Done") } ))
  end
end

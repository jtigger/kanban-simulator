require "test/unit"

require File.dirname(__FILE__) + "/../code/model/config/configuration_parser.rb"
require File.dirname(__FILE__) + "/../code/model/simulation.rb"
require File.dirname(__FILE__) + "/../code/model/workflow.rb"
require File.dirname(__FILE__) + "/helpers/workflow_test_helper.rb"


# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestConfigurationPlan < Test::Unit::TestCase

  def setup
    @parser = ConfigurationParser.new
    @simulation = Simulation.new
    WorkflowTestHelper.add_testworkflow_to_workflow_class
  end
  
  def teardown
    @parser = nil
    @simulation = nil
    WorkflowTestHelper.remove_testworkflow_from_workflow_class
  end

  def test_plan_correctly_seeds_workflow
    config_plan = @parser.parse('Using the "TestWorkflow" SDLC.')
    @simulation.configure(config_plan)
    
    assert_equal(Workflow.TestWorkflow.name, @simulation.workflow.name)
  end

  def test_plan_correctly_errors_on_unknown_workflow
    config_plan = @parser.parse('Using the "Foobar" SDLC.')

    assert_raise(ConfigurationException) { @simulation.configure(config_plan) }
  end
  
  def test_plan_correctly_configures_workflow_step
    @simulation.workflow = Workflow.TestWorkflow

    config_plan = [ModifyStep.new("In Dev", {"WIP limit" => 6})]
    
    @simulation.configure(config_plan)
    
    assert_equal(6, @simulation.workflow.steps[2].wip_limit)
  end
  
end
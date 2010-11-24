require "test/unit"

require File.dirname(__FILE__) + "/../code/app/simulation.rb"
require File.dirname(__FILE__) + "/../code/model/config/configuration_parser.rb"
require File.dirname(__FILE__) + "/../code/model/workflow.rb"

class Workflow
  def Workflow.TestWorkflow
    workflow = []
    workflow << KanbanProcessStep.new("In Analysis", 3)
    workflow << KanbanProcessStep.new("In Dev", 3)
    workflow << KanbanProcessStep.new("In Test", 3)
    workflow << KanbanProcessStep.new("Done", nil)
    
    workflow
  end
end

# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestConfigurationPlan < Test::Unit::TestCase

  def setup
    @parser = ConfigurationParser.new
    @simulation = Simulation.new
  end
  
  def teardown
    @parser = nil
    @simulation = nil
  end

  def test_plan_correctly_seeds_workflow
    config_plan = @parser.parse('Using the "TestSDLC" SDLC.')
    @simulation.configure(config_plan)
    
    assert_equal(Workflow.TestWorkflow.size, @simulation.workflow.size)
  end

  def test_plan_correctly_errors_on_unknown_workflow
    config_plan = @parser.parse('Using the "Foobar" SDLC.')

    assert_raise(ConfigurationException) { @simulation.configure(config_plan) }
  end
  
  def test_plan_correctly_configures_workflow_step
    @simulation.workflow = Workflow.TestWorkflow

    config_plan = [ModifyStep.new("In Dev", {"WIP limit" => 6})]
    
    @simulation.configure(config_plan)
    
    assert_equal(6, @simulation.workflow[1].wip_limit)
  end

end
require "test/unit"

require File.dirname(__FILE__) + "/../code/model/config/configuration_parser.rb"
require File.dirname(__FILE__) + "/../code/model/simulation.rb"
require File.dirname(__FILE__) + "/../code/model/workflow.rb"

# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestConfigurationPlan < Test::Unit::TestCase

  def setup
    @parser = ConfigurationParser.new
    @simulation = Simulation.new
    add_testworkflow_to_workflow_class
  end
  
  def teardown
    @parser = nil
    @simulation = nil
    remove_testworkflow_from_workflow_class
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
    
    assert_equal(6, @simulation.workflow.steps[1].wip_limit)
  end
  
  def add_testworkflow_to_workflow_class
    class << Workflow
      def Workflow.TestWorkflow
        workflow = Workflow.new("TestWorkflow")

        workflow.steps << WorkflowStep.new("In Analysis", 3)
        workflow.steps << WorkflowStep.new("In Dev", 3)
        workflow.steps << WorkflowStep.new("In Test", 3)
        workflow.steps << WorkflowStep.new("Done", nil)

        workflow
      end
    end    
  end
  
  def remove_testworkflow_from_workflow_class
    class << Workflow
      remove_method(:TestWorkflow)
    end
  end
end
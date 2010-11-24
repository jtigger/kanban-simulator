require "test/unit"

require File.dirname(__FILE__) + "/../code/app/simulation.rb"
require File.dirname(__FILE__) + "/../code/model/config/configuration_parser.rb"
require File.dirname(__FILE__) + "/../code/model/sdlc.rb"

class SDLC
  def SDLC.TestSDLC
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
    
    assert_equal(SDLC.TestSDLC.size, @simulation.workflow.size)
  end

  def test_plan_correctly_errors_on_unknown_workflow
    config_plan = @parser.parse('Using the "Foobar" SDLC.')

    assert_raise(ConfigurationException) { @simulation.configure(config_plan) }
  end

end
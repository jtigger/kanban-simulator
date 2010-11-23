require "test/unit"

require File.dirname(__FILE__) + "/../code/app/simulation.rb"
require File.dirname(__FILE__) + "/../code/model/config/configuration_parser.rb"
require File.dirname(__FILE__) + "/../code/model/sdlc.rb"

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
    config_plan = @parser.parse('Using the "Kanban" SDLC.')
    @simulation.configure(config_plan)
    
    assert_equal(SDLC.Kanban.size, @simulation.workflow.size)
  end

  def test_plan_correctly_errors_on_unknown_workflow
    config_plan = @parser.parse('Using the "Foobar" SDLC.')

    assert_raise(ConfigurationException) { @simulation.configure(config_plan) }
  end

end
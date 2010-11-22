require "test/unit"

require File.dirname(__FILE__) + "/../code/app/simulation.rb"
require File.dirname(__FILE__) + "/../code/model/config/configuration_parser.rb"


# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestConfiguration < Test::Unit::TestCase
  def test_parse_config_line_with_just_sdlc_specified
    parser = ConfigurationParser.new
    config_line = 'We are using the "Kanban" SDLC.'

    config_plan = parser.parse config_line
    
    assert_equal(1, config_plan.size)
    assert(config_plan[0].kind_of?(EstablishWorkflow))
  end

  # Future tests:
  # test_parse_config_line_with_sdlc_and_wip_limits
  
  def test_configure_simulation
    simulation = Simulation.new
    parser = ConfigurationParser.new
    
    config_line = 'We are using the "Kanban" SDLC where the "WIP limit" for "In Analysis" is 3; and the "WIP limit" for "In Dev" is 4; and the "WIP limit" for "In Test" is 2.'

    # 1. fetch SDLC (name => "Kanban")
    # 2. configure step (name => "In Analysis", property => {"WIP Limit" => "3"})
    config_plan = parser.parse config_line
    
    simulation.configure config_plan
  end
end
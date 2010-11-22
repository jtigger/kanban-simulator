require "test/unit"

require File.dirname(__FILE__) + "/../code/app/simulation.rb"
require File.dirname(__FILE__) + "/../code/model/config/configuration_parser.rb"


# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestConfiguration < Test::Unit::TestCase
  def test_parse_config_line
    parser = ConfigurationParser.new
    simulation = Simulation.new
    
    config_line = 'We are using the "Kanban" SDLC where the "WIP limit" for "In Analysis" is 3; and the "WIP limit" for "In Dev" is 4; and the "WIP limit" for "In Test" is 2.'

    # 1. fetch SDLC (name => "Kanban")
    # 2. configure step (name => "In Analysis", property => {"WIP Limit" => "3"})
    config_plan = parser.parse config_line
    
    simulation.configure config_plan
  end
end
require "test/unit"

require File.dirname(__FILE__) + "/../code/model/config/configuration_parser.rb"


# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestConfigurationParser < Test::Unit::TestCase
  
  def setup
    @parser = ConfigurationParser.new
  end
  
  def teardown
    @parser = nil
  end
  
  def test_parser_properly_identifies_configuration_command
    config_line = "We are using the \"Kanban\" SDLC\n" +
                  "where the \"WIP limit\" for \"In Analysis\" is 3\n"+
                  " and the \"WIP limit\" for \"In Dev\" is 4\n"+
                  " and the \"WIP limit\" for \"In Test\" is 2."

    config_plan = @parser.parse config_line
    assert_equal(4, config_plan.size)
    assert(config_plan[0].kind_of?(EstablishWorkflow))
    assert(config_plan[1].kind_of?(ModifyStep))
    assert(config_plan[2].kind_of?(ModifyStep))
    assert(config_plan[3].kind_of?(ModifyStep))
  end
  
  def test_parser_correctly_captures_workflow_name
    # Strings that must match:
    assert_equal('Kanban', @parser.parse('Using the "Kanban" SDLC.')[0].workflow_name)
    assert_equal('Kanban', @parser.parse('Employing the "Kanban" Workflow,')[0].workflow_name)
    assert_equal('Kanban', @parser.parse('Using the "Kanban" process')[0].workflow_name)
    assert_equal('Scrum', @parser.parse('Using "Scrum"')[0].workflow_name)
    assert_equal('Scrum', @parser.parse('Utilizing "Scrum"')[0].workflow_name)
    assert_equal('Scrum', @parser.parse('With "Scrum"')[0].workflow_name)
    
    # Strings that must NOT match...
    assert_equal(0, @parser.parse('Using the Kanban SDLC.').size)  # missing quotes
    assert_equal(0, @parser.parse('"Using the Kanban Workflow."').size)  # misplaced quotes
  end
  
  def test_parser_correctly_captures_step_configuration
    config_line = "where the \"WIP limit\" for \"In Analysis\" is 3.\n"

    assert_equal('In Analysis', @parser.parse(config_line)[0].step_name)
    assert_equal('WIP limit', @parser.parse(config_line)[0].step_property.keys[0])
    assert_equal(3, @parser.parse(config_line)[0].step_property.values[0])
  end
  
  def test_parser_correctly_identifies_fixnum_value_for_step_configuration
    config_line = "where the \"WIP limit\" for \"In Analysis\" is 3.\n"

    assert_equal('In Analysis', @parser.parse(config_line)[0].step_name)
    assert_equal('WIP limit', @parser.parse(config_line)[0].step_property.keys[0])
    assert_equal(3, @parser.parse(config_line)[0].step_property.values[0])    
  end

  def test_parser_correctly_identifies_string_value_for_step_configuration
    config_line = "where the \"WIP limit\" for \"In Analysis\" is \"3\".\n"

    assert_equal('In Analysis', @parser.parse(config_line)[0].step_name)
    assert_equal('WIP limit', @parser.parse(config_line)[0].step_property.keys[0])
    assert_equal("3", @parser.parse(config_line)[0].step_property.values[0])    
  end
  
  def test_parser_ignores_comment_lines
    config_line = "#We are using the \"Kanban\" SDLC\n" +
                  "#where the \"WIP limit\" for \"In Analysis\" is 3\n"+
                  "# and the \"WIP limit\" for \"In Dev\" is 4\n"+
                  "# and the \"WIP limit\" for \"In Test\" is 2."
                  
    assert_equal(0, @parser.parse(config_line).size)
    
    config_line = "#We are using the \"Kanban\" SDLC\n" +
                  "where the \"WIP limit\" for \"In Analysis\" is 3\n"+
                  "# and the \"WIP limit\" for \"In Dev\" is 4\n"+
                  "# and the \"WIP limit\" for \"In Test\" is 2."
    assert_equal(1, @parser.parse(config_line).size)
    
    config_line = "We are using the \"Kanban\" SDLC\n" +
                  "#where the \"WIP limit\" for \"In Analysis\" is 3\n"+
                  " and the \"WIP limit\" for \"In Dev\" is 4\n"+
                  "# and the \"WIP limit\" for \"In Test\" is 2."
    assert_equal(2, @parser.parse(config_line).size)
  end
end
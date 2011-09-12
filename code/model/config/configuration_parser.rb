require File.dirname(__FILE__) + "/../workflow.rb"
require File.dirname(__FILE__) + "/configuration_command.rb"

require File.dirname(__FILE__) + "/add_workflow_step.rb"
require File.dirname(__FILE__) + "/establish_workflow.rb"
require File.dirname(__FILE__) + "/modify_step.rb"
require File.dirname(__FILE__) + "/set_hardstop.rb"



# Natural language parser for configuration of the simulation.
# This parser understands the following sentences:
#  'Using/Employing/With/Utilizing "(process-name)"' -- to identify the base Workflow to be used in the simulation (quotes are required).
#  'where/and the "(property)" for "(workflow-step)" is "(value)" -- to configure a specific property of a particular step in the Workflow.
# Where each sentence is on a separate line.
# 
# You can comment any line by starting it with a hash character:
#  '# this is just a comment'
#  '# Using "Scrum" (this line does nothing; it's commented-out)'
#
# Author:: John S. Ryan (jtigger@infosysengr.com)
class ConfigurationParser
  Define_Workflow_Pattern = /.*Our workflow, "(.*)" is .*/i
  Add_Workflow_Step_Pattern = /.*"(.*)" step.*/i
  Establish_Workflow_Pattern = /.*(using|employing|with|utilizing) (the )?"(.*)"/i
  Modify_Step_Pattern = /(where|and) the "(.*)" (of|for) "(.*)" is (.*)\.?/i
  Set_Simulation_Hardstop_Pattern = /.* simulation .* stop .* ([0-9]+) ticks?.*/i
  Ends_With_A_Period = /(.*)\.\Z/
  Value_Is_Quoted = /^"(.*)"$/
  Value_Is_A_Fixnum = /^[[:digit:]]*$/
  
  # Parses a natural language configuration, building up a configuration plan
  # that can be applied to a Simulation by calling Simulation.configure()
  def parse(natural_language_config)
    config_plan = []
    
    lines = natural_language_config.split("\n")
    
    lines.each_with_index do |line, line_no|
      config_step = nil

      next if line =~ /^#/
      # if line =~ Define_Workflow_Pattern
      #   workflow_name = Define_Workflow_Pattern.match(line[1])
      #   config_step = 
      # end
      if line =~ Add_Workflow_Step_Pattern
        step_name = Add_Workflow_Step_Pattern.match(line)[1]
        config_step = AddWorkflowStep.new(step_name)
      end
      if line =~ Establish_Workflow_Pattern
        workflow_name = Establish_Workflow_Pattern.match(line)[3]
        config_step = EstablishWorkflow.new(workflow_name)
      end
      if line =~ Modify_Step_Pattern
        match_groups = Modify_Step_Pattern.match(line)
        property_name = match_groups[2]
        step_name = match_groups[4]
        property_value = match_groups[5]
        property_value.sub!(Ends_With_A_Period, '\1')

        if property_value =~ Value_Is_A_Fixnum
          property_value = property_value.to_i
        end
        property_value.sub!(Value_Is_Quoted, '\1') if property_value.kind_of? String
        
        config_step = ModifyStep.new(step_name, { property_name => property_value })
      end
      if line =~ Set_Simulation_Hardstop_Pattern
        hardstop = Set_Simulation_Hardstop_Pattern.match(line)[1].to_i
        config_step = SetHardstop.new(hardstop)
      end
      if !config_step.nil?
        config_step.original_text = line
        config_step.line_no = line_no + 1
        config_plan << config_step        
      end
    end
    return config_plan
  end
end

# Natural language parser for configuration of the simulation.
# This parser understands the following sentences:
#  'Using/Employing/With/Utilizing "(process-name)"' -- to identify the base SDLC to be used in the simulation (quotes are required).
#  'where/and the "(property)" for "(workflow-step)" is "(value)" -- to configure a specific property of a particular step in the SDLC.
#
# You can comment any line by starting it with a hash character:
#  '# this is just a comment'
#  '# Using "Scrum" (this line does nothing; it's commented-out)'
#
# Author:: John S. Ryan (jtigger@infosysengr.com)
class ConfigurationParser
  Establish_Workflow_Pattern = /.*(using|employing|with|utilizing) (the )?"(.*)"/i
  Modify_Step_Pattern = /(where|and) the "(.*)" for "(.*)" is "(.*)"/i
  
  # Parses a natural language configuration, building up a configuration plan
  # that can be applied to a Simulation.
  def parse(natural_language_config)
    config_plan = []
    
    lines = natural_language_config.split('\n')
    
    lines.each_with_index do |line, line_no|
      config_step = nil

      next if line =~ /^#/
      if line =~ Establish_Workflow_Pattern
        workflow_name = Establish_Workflow_Pattern.match(line)[3]
        config_step = EstablishWorkflow.new(workflow_name)
      end
      if line =~ Modify_Step_Pattern
        match_groups = Modify_Step_Pattern.match(line)
        property_name = match_groups[2]
        step_name = match_groups[3]
        property_value = match_groups[4]
        config_step = ModifyStep.new(step_name, { property_name => property_value })
      end
      if !config_step.nil?
        config_step.original_text = line
        config_step.line_no = line_no
        config_plan << config_step        
      end
    end
    return config_plan
  end
end

class ConfigurationCommand
  attr_accessor :original_text
  attr_accessor :line_no
end

class EstablishWorkflow < ConfigurationCommand
  attr_accessor :workflow_name
  
  def initialize(workflow_name)
    self.workflow_name = workflow_name
  end
  
  def configure(simulation)
    
  end
end

class ModifyStep <  ConfigurationCommand
  attr_accessor :step_name
  attr_accessor :step_property
  
  def initialize(step_name, step_property)
    self.step_name = step_name
    self.step_property = step_property
  end
  
  def configure(simulation)
    
  end
end
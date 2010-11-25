require File.dirname(__FILE__) + "/../workflow.rb"

# Natural language parser for configuration of the simulation.
# This parser understands the following sentences:
#  'Using/Employing/With/Utilizing "(process-name)"' -- to identify the base Workflow to be used in the simulation (quotes are required).
#  'where/and the "(property)" for "(workflow-step)" is "(value)" -- to configure a specific property of a particular step in the Workflow.
#
# You can comment any line by starting it with a hash character:
#  '# this is just a comment'
#  '# Using "Scrum" (this line does nothing; it's commented-out)'
#
# Author:: John S. Ryan (jtigger@infosysengr.com)
class ConfigurationParser
  Establish_Workflow_Pattern = /.*(using|employing|with|utilizing) (the )?"(.*)"/i
  Modify_Step_Pattern = /(where|and) the "(.*)" for "(.*)" is (.*)\.?/i
  Ends_With_A_Period = /(.*)\.\Z/
  Value_Is_Quoted = /^"(.*)"$/
  Value_Is_A_Fixnum = /^[[:digit:]]*$/
  
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
        property_value.sub!(Ends_With_A_Period, '\1')

        if property_value =~ Value_Is_A_Fixnum
          property_value = property_value.to_i
        end
        property_value.sub!(Value_Is_Quoted, '\1') if property_value.kind_of? String
        
        config_step = ModifyStep.new(step_name, { property_name => property_value })
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

# Thrown when a configuration step cannot be properly executed on the simulation.
class ConfigurationException < Exception
  def initialize(offending_config_command, problem, hint)
    @config_command = offending_config_command
    @problem = problem
    @hint = hint
    
    self
  end

  def message
    "An error occurred while trying to configure the simulation.  #{@problem} (#{@hint}). [#{@config_command.line_no}: '#{@config_command.original_text}']"
  end
  
end

class ConfigurationCommand
  attr_accessor :original_text
  attr_accessor :line_no
end

class EstablishWorkflow < ConfigurationCommand
  attr_accessor :workflow_name
  
  def initialize(workflow_name)
    @workflow_name = workflow_name
  end
  
  def configure(simulation)
    if !Workflow.respond_to?(workflow_name) 
      raise ConfigurationException.new(self,
        "Unable to establish the base Workflow; #{workflow_name} is not a recognized Workflow.",
        "refer to the Workflow class for all pre-defined Workflow.")
    end
    
    simulation.workflow = Workflow.send(workflow_name)
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
    step = simulation.workflow.steps.find { |step| step.name == step_name }
    if step == nil
      raise ConfigurationException.new(self,
        "Unable to configure workflow step #{step_name}; No such step with that name.",
        "Do you have a properly configured Workflow?  Does that Workflow have a step named #{step_name}?")
    end
    
    step_property.each do |property, value|
      property_name = calc_property_name(property)
      step.send(property_name+"=", value)
    end
  end

private
  # converts a name of a property (expressed in "natural language") into a valid Ruby setter
  # for that same name.
  def calc_property_name(natural_language_property_name)
    identifier = natural_language_property_name.dup  # natural_language_property_name is frozen
    identifier.tr!(' ', '_').downcase!
  end
end


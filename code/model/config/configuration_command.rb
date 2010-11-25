class ConfigurationCommand
  attr_accessor :original_text
  attr_accessor :line_no
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

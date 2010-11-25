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

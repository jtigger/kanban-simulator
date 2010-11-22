
# Author:: John S. Ryan (jtigger@infosysengr.com)
class ConfigurationParser
  
  # Parses a natural language configuration, building up a configuration plan
  # that can be applied to a Simulation.
  def parse(natural_language_config)
    
    return [];
  end
end

# Configuration Commands:
class EstablishWorkflow
  attr_accessor :workflow_name;
  
  def configure(simulation)
    
  end
end

class ModifyStep
  attr_accessor :step_name;
  attr_accessor :step_property;
  
  def configure(simulation)
    
  end
end
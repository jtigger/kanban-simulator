
# Author:: John S. Ryan (jtigger@infosysengr.com)
class ConfigurationParser
  
  Establish_Workflow_Pattern = /.*using the "(.*)" SDLC/i
  
  # Parses a natural language configuration, building up a configuration plan
  # that can be applied to a Simulation.
  def parse(natural_language_config)
    config_plan = [];
    
    if Establish_Workflow_Pattern.match(natural_language_config)
      workflow_name = Establish_Workflow_Pattern.match(natural_language_config)[0]
      config_step = EstablishWorkflow.new(workflow_name)
      config_plan << config_step
    end
    
    return config_plan;
  end
end

# Configuration Commands:
class EstablishWorkflow
  attr_accessor :workflow_name;
  
  def initialize(workflow_name)
    self.workflow_name = workflow_name
  end
  
  def configure(simulation)
    
  end
end

class ModifyStep
  attr_accessor :step_name;
  attr_accessor :step_property;
  
  def configure(simulation)
    
  end
end
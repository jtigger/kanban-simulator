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

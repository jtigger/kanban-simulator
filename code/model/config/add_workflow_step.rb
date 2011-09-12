class AddWorkflowStep <  ConfigurationCommand
  def initialize(name)
    @name = name
  end

  def configure(simulation)
    simulation.workflow.steps << WorkflowStep.new(@name)
  end
end
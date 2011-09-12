class SetHardstop < ConfigurationCommand
  attr_accessor :hardstop
  
  def initialize(hardstop)
    @hardstop = hardstop
  end
  
  def configure(simulation)
    simulation.hardstop = hardstop
  end
end

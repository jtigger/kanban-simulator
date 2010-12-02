# Author:: John S. Ryan (jtigger@infosysengr.com)
class MockSimulationObserver

  def initialize
    @events = []
  end
  
  def update(event)
    @events << event
  end
  
  def received_event(required_event)
    event = @events.find do |event|
      found = true
      found = found && required_event[:action] == event[:action]
      found = found && event[:object].kind_of?(required_event[:object].class)
      found = found && instance_variables_are_equal(required_event[:object], event[:object])
      found
    end      
    !event.nil?
  end
  
private
  def instance_variables_are_equal(source_obj, target_obj)
    source_obj.instance_variables.each do |ivar_name|
      return false if (!target_obj.instance_variables.include?(ivar_name))
      return false if (source_obj.instance_variable_get(ivar_name) != target_obj.instance_variable_get(ivar_name))
    end
    return true
  end
end
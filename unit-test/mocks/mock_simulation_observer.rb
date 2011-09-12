# Author:: John S. Ryan (jtigger@infosysengr.com)
class MockSimulationObserver
  attr_accessor :events
  
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
      if(required_event.include? :object)
        found = found && event[:object].kind_of?(required_event[:object].class)
        found = found && are_equal(required_event[:object], event[:object])
      end
      if(required_event.include? :time)
        found = found && required_event[:time] == event[:time]
      end
      found
    end      
    !event.nil?
  end
  
private
  def are_equal(source_obj, target_obj)
    if(source_obj.instance_variables.empty?)
      return source_obj == target_obj
    else
      source_obj.instance_variables.each do |ivar_name|
        return false if (!target_obj.instance_variables.include?(ivar_name))
        return false if (source_obj.instance_variable_get(ivar_name) != target_obj.instance_variable_get(ivar_name))
      end
    end
    return true
  end
end
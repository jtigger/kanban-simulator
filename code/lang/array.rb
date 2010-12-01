require "observer"

# Author:: John S. Ryan (jtigger@infosysengr.com)
class Array

  def make_observable
    Array.make_observable(self)
  end

  def Array.make_observable(array=[])
    return array if array.class.include?(Observable)  # assumes THIS is the only source of observation behavior.

    # see also: https://github.com/jtigger/kanban-simulator/wiki/idiom%3A-Singleton-Class
    arrays_metaclass = (class << array; self; end)
    
    arrays_metaclass.class_eval do
      include Observable
      
      alias_method :original_push, :push
      def push(item)    
        push_result = original_push(item)
        
        changed
        notify_observers({ :action => :push, :object => item })
        
        return push_result
      end
      
      alias_method "original_<<".to_sym, "<<".to_sym
      def <<(item)
        result = self.send("original_<<".to_sym, item)
        
        changed
        notify_observers({ :action => :push, :object => item })
        
        return result
      end
    end
    
    return array
  end
end
require "test/unit"

require File.dirname(__FILE__) + "/../../code/lang/array.rb"

# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestArray < Test::Unit::TestCase
  def test_plain_arrays_are_unaffected
    plain_array = []
    observable_array = []
    Array.make_observable(observable_array)
    
    assert(!plain_array.respond_to?(:original_push))
    assert(observable_array.respond_to?(:original_push))
  end
  
  def test_observers_hear_about_push
    observable_array = [].make_observable
    observer = MockObserver.new
    
    observable_array.add_observer(observer)
    
    observable_array << "first item"
    observable_array.push "second item"

    assert_equal(2, observer.events.size)
    assert_equal(:push, observer.events[0][:action])
    assert_equal(:push, observer.events[1][:action])
    assert_equal("first item", observer.events[0][:object])
    assert_equal("second item", observer.events[1][:object])
  end
end

class MockObserver
  attr_accessor :events
  
  def initialize
    @events = []
  end
  
  def update(event)
    @events << event  
  end
end
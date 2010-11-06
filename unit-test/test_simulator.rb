require "test/unit"

require File.dirname(__FILE__) + "/../code/app/simulator.rb"

# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestSimulator < Test::Unit::TestCase
  def setup
    @simulator = Simulator.new()
  end
  
  def teardown
    @simulator.cleanup()
  end

  def test_init
    @simulator.add_to_backlog(20)
    assert_equal(20, @simulator.story_cards.size)
  end
  
  def test_different_size_backlogs
    @simulator.add_to_backlog(10)
    assert_equal(10, @simulator.story_cards.size)
   
    @simulator.reset
    @simulator.add_to_backlog(30)
    assert_equal(30, @simulator.story_cards.size)
  end
end
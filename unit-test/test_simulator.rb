require "test/unit"

require File.dirname(__FILE__) + "/../code/app/simulator.rb"

class TestSimulator < Test::Unit::TestCase
  def setup
    @simulator = Simulator.new()
  end
  
  def teardown
    @simulator.cleanup()
  end

  def test_init
    @simulator.create_initial_backlog(20)
    assert_equal(20, @simulator.story_cards.size)
  end
end
require "test/unit"

require File.dirname(__FILE__) + "/../code/model/story_card.rb"

# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestStoryCard < Test::Unit::TestCase
  
  def test_only_accepts_estimates_in_fibonacci_sequence_upto_21
    story_card = StoryCard.new
    
    story_card.estimated_points = 1
    assert_raise( RuntimeError ) { story_card.estimated_points = 2 }
    story_card.estimated_points = 3
    assert_raise( RuntimeError ) { story_card.estimated_points = 4 }
    story_card.estimated_points = 5
    assert_raise( RuntimeError ) { story_card.estimated_points = 6 }
    assert_raise( RuntimeError ) { story_card.estimated_points = 7 }
    story_card.estimated_points = 8
    assert_raise( RuntimeError ) { story_card.estimated_points = 9 }
    assert_raise( RuntimeError ) { story_card.estimated_points = 10 }
    assert_raise( RuntimeError ) { story_card.estimated_points = 11 }
    assert_raise( RuntimeError ) { story_card.estimated_points = 12 }
    story_card.estimated_points = 13
    assert_raise( RuntimeError ) { story_card.estimated_points = 14 }
    assert_raise( RuntimeError ) { story_card.estimated_points = 15 }
    assert_raise( RuntimeError ) { story_card.estimated_points = 16 }
    assert_raise( RuntimeError ) { story_card.estimated_points = 17 }
    assert_raise( RuntimeError ) { story_card.estimated_points = 18 }
    assert_raise( RuntimeError ) { story_card.estimated_points = 19 }
    assert_raise( RuntimeError ) { story_card.estimated_points = 20 }
    story_card.estimated_points = 21
  end
end
require "test/unit"

require File.dirname(__FILE__) + "/../code/lang/enumerable.rb"

# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestEnumerable < Test::Unit::TestCase
  def test_empty_list
    nums = []
    assert_equal(0, nums.sum)
    assert_equal(0, nums.average)
    assert_equal(0, nums.sample_variance)
    assert_equal(0, nums.standard_deviation)
  end
  
  def test_list_of_one
    nums = [1]
    
    assert_equal(1, nums.sum)
    assert_equal(1, nums.average)
    assert_equal(0, nums.sample_variance)
    assert_equal(0, nums.standard_deviation)
  end
  
  def test_list_of_many
    nums = [1,2,3,4,5,6,7,8,9]

    assert_equal(45, nums.sum)
    assert_equal(5, nums.average)
    assert_equal(60, nums.sample_variance)
    assert_in_delta(7.7459, nums.standard_deviation, 0.0001)
  end
end

# Embodies the essential aspects of a single requirement of the system.
#
# Author:: John S. Ryan (jtigger@infosysengr.com)
class StoryCard
  attr_accessor :estimated_points
  attr_accessor :priority
  
  @@Acceptable_Point_Values = [1,3,5,8,13,21]
  def estimated_points=(value)
    if @@Acceptable_Point_Values.member? value
      @estimated_points = value
    else
      raise "Invalid value for estimated_points.  Acceptable values are " + @@Acceptable_Point_Values.join(', ') + "."
    end
  end
end
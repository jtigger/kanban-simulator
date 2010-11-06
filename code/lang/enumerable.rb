# Extends all "Enumerable"s with some simple statistic functions.
# 
# largely stolen from (https://www.bcg.wisc.edu/webteam/support/ruby/standard_deviation)
#
# Author:: John S. Ryan (jtigger@infosysengr.com)
module Enumerable
  
  def sum
    self.inject(0) { |sum, value| sum + value }
  end
  
  def average
    return 0 if length == 0    

    sum / length.to_f      
  end
  
  # http://en.wikipedia.org/wiki/Variance
  def population_variance
    avg = average
    sum_of_diffs_squared = self.inject(0) { |sum, value| sum + (value - avg)**2}
  end
  
  # http://en.wikipedia.org/wiki/Population_standard_deviation
  def population_standard_deviation
    Math.sqrt(population_variance)
  end
end
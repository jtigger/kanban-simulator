require File.dirname(__FILE__) + "/./simulation.rb"
require File.dirname(__FILE__) + "/../model/kanban_process_step.rb"

# Executable for running Kanban simulations
#
# Author:: John S. Ryan (jtigger@infosysengr.com)
class TextUi
  PROGRAM = File.basename(__FILE__)
  attr_accessor :verbosity
  
  def initialize
    @verbosity = 3
  end
  
  def error(message)
    $stderr.puts("#{PROGRAM}:ERROR:#{message}")  
  end
  
  def warn(message)
    $stderr.puts("#{PROGRAM}:WARN:#{message}") if @verbosity >= 0
  end
  
  def info(message)
    $stderr.puts("#{PROGRAM}:INFO:#{message}") if @verbosity >= 1    
  end
  
  def debug(message)
    $stderr.puts("#{PROGRAM}:DEBUG:#{message}") if @verbosity >= 2
  end

  def trace(message)
    $stderr.puts("#{PROGRAM}:TRACE:#{message}") if @verbosity >= 3
  end
end

class KanbanSimulator

  def initialize
    @ui = TextUi.new
    @simulation = Simulation.new
  end
  
  def usage
    @ui.info("Usage:")
    @ui.info("  ruby kanban_simulator.rb <number of stories>")
    @ui.info("where:")
    @ui.info("  <number of stories> = initial size of the backlog.")
  end
  
  def parse_options
    expected_num_of_params = 1
    
    if ARGV.size == expected_num_of_params
      @num_of_stories = ARGV[0].to_i
      @ui.trace("ARGV = #{ARGV.to_s}")
    else
      raise "Invalid number of parameters.  Expected #{expected_num_of_params} but there was #{ARGV.size}."
    end
  end
  
  def main
      @ui.info("KanbanSimulator(R)   Software Development Workflow Simulator    2010-11-05")
      begin
        parse_options
      rescue RuntimeError
        usage
        return -1
      end
      @ui.info("Initializing backlog...")
      @simulation.add_to_backlog(@num_of_stories) do |story_card, idx|
        story_card.priority = idx
        story_card.estimated_points = StoryCard::Acceptable_Point_Values[rand(5)]
      end
      @simulation.story_cards.each_with_index { |story_card, idx|
        @ui.info("Story card \##{idx}: priority = #{story_card.priority}; estimate = #{story_card.estimated_points}")  
      }
      
      workflow = []
      workflow << KanbanProcessStep.new("In Analysis", 3)
      workflow << KanbanProcessStep.new("In Dev", 3)
      workflow << KanbanProcessStep.new("In Test", 3)
      workflow << KanbanProcessStep.new("Done", nil)
      @simulation.workflow = workflow
      
      @ui.info("Workflow definition:")
      @simulation.workflow.each_with_index { |step, idx|
        @ui.info("Step #{idx} = #{step.name} (WIP Limit = #{step.wip_limit})")
      }
  end
end

KanbanSimulator.new.main()
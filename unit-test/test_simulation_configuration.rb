require "test/unit"
require "Tempfile"

require File.dirname(__FILE__) + "/../code/lang/enumerable.rb"
require File.dirname(__FILE__) + "/../code/model/simulation.rb"
require File.dirname(__FILE__) + "/../code/model/workflow_step.rb"
require File.dirname(__FILE__) + "/helpers/workflow_test_helper.rb"

require File.dirname(__FILE__) + "/../code/model/config/add_workflow_step.rb"


# Author:: John S. Ryan (jtigger@infosysengr.com)
class TestSimulationConfiguration < Test::Unit::TestCase
  
  def setup
    WorkflowTestHelper.add_testworkflow_to_workflow_class    
    @simulation = Simulation.new()
    @simulation.workflow = Workflow.TestWorkflow
  end
  
  def teardown
    @simulation.cleanup()
    WorkflowTestHelper.remove_testworkflow_from_workflow_class
  end

  def test_init
    @simulation.generate_to_backlog(20)
    assert_equal(20, @simulation.work_items.size)
  end
  
  def test_different_size_backlogs
    @simulation.generate_to_backlog(10)
    assert_equal(10, @simulation.work_items.size)
   
    @simulation.reset
    @simulation.generate_to_backlog(30)
    assert_equal(30, @simulation.work_items.size)
  end
  
  # From Story #1:
  # Acceptance Criteria
  # 2. After the backlog is created I should be able to see the list of story cards created in priority 
  # order along with the randomly generated Size Estimate.
  def test_generate_story1_backlog
    @simulation.generate_to_backlog(20) do |work_item, idx|
      work_item.priority = idx
      work_item.estimated_points = WorkItem::Acceptable_Point_Values[rand(WorkItem::Acceptable_Point_Values.length)]
    end
    
    expected_priority = 1
    estimations = []
    @simulation.work_items.each { |work_item|
      estimations << work_item.estimated_points
      assert_equal(expected_priority, work_item.priority)
      expected_priority += 1
    }
    
    # assert that estimations are basically "random"
    assert(estimations.standard_deviation > 10.0)
  end

  
  def test_build_workflow_from_config
    @simulation = Simulation.new
    
    config_plan = 'Our workflow, "Kanban" is comprised of:
       a "Step 1" step
       a "Step 2" step
       and a "Step 3" step"'
       
    # config_plan = [  AddWorkflowStep.new("Step 1"), AddWorkflowStep.new("Step 2"), AddWorkflowStep.new("Step 3") ]
    
    @simulation.configure(config_plan)

    assert_equal(3, @simulation.workflow.steps.size)  # includes "Backlog" and "Done"
    assert_equal("Step 1", @simulation.workflow.steps[0].name)
    assert_equal("Step 2", @simulation.workflow.steps[1].name)
    assert_equal("Step 3", @simulation.workflow.steps[2].name)
  end
  
  def test_load_config_from_file
    config = Tempfile.new("test-config")
    config.puts('Using "Kanban".')
    config.puts('where the "WIP limit" of "In Analysis" is 4')
    config.puts('where the "WIP limit" of "In Dev" is 4')
    config.puts('where the "WIP limit" of "In Test" is 4')
    config.close
    
    @simulation.configure(config.path)
    
    assert_equal(4, @simulation.workflow.steps[1].wip_limit)
  end
  
  def test_changing_workflow_after_adding_cards_fails
    @simulation.generate_to_backlog(1)
    assert_raises (RuntimeError) { @simulation.workflow = Workflow.new("test") }
  end
end
package com.appraisal.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appraisal.domain.LookupCompetency;
import com.appraisal.domain.ObjectiveScore;
import com.appraisal.domain.PerformancePlanComments;
import com.appraisal.domain.Employee;
import com.appraisal.domain.EmployeeComments;
import com.appraisal.domain.EvaluationComments;
import com.appraisal.domain.EvaluationCompetency;
import com.appraisal.domain.EvaluationPlanObj;
import com.appraisal.domain.Hrperiod;
import com.appraisal.domain.PerformancePlanObj;
import com.appraisal.domain.PlanCompetency;
import com.appraisal.domain.PlanScore;
import com.appraisal.domain.StaffReviewer;
import com.appraisal.domain.Staffcompetency;
import com.appraisal.domain.SubmitPerformancePlan;
import com.appraisal.domain.Supervisorcompetency;
import com.appraisal.domain.SubmitEvaluation;
import com.appraisal.domain.User;
import com.appraisal.domain.UserRole;
import com.appraisal.domain.Workplan;
import com.appraisal.repository.LookupCompetencyRepository;
import com.appraisal.repository.ObjectiveScoreRepository;
import com.appraisal.repository.PerformancePlanCommentsRepository;
import com.appraisal.repository.EmployeeCommentsRepository;
import com.appraisal.repository.EmployeeRepository;
import com.appraisal.repository.EvaluationCompetencyRepository;
import com.appraisal.repository.EvaluationCommentsRepository;
import com.appraisal.repository.EvaluationPlanObjRepository;
import com.appraisal.repository.HrperiodRepository;
import com.appraisal.repository.PeriodRepository;
import com.appraisal.repository.PerformancePlanObjRepository;
import com.appraisal.repository.PlanCompetencyRepository;
import com.appraisal.repository.PlanScoreRepository;
import com.appraisal.repository.StaffReviewerRepository;
import com.appraisal.repository.StaffcompetencyRepository;
import com.appraisal.repository.SubmitEvaluationRepository;
import com.appraisal.repository.SubmitPerformancePlanRepository;
import com.appraisal.repository.SupervisorcompetencyRepository;
import com.appraisal.repository.UserRepository;
import com.appraisal.repository.UserRoleRepository;
import com.appraisal.repository.WorkplanRepository;
import com.appraisal.services.UserService;
import com.appraisal.utility.MailConstructor;

@Controller
@Component
public class HomeController {
	
	private final String  URL = "https://eservices.lra.gov.lr/PerformanceAppraisal/login";
	@Autowired
	private ObjectiveScoreRepository objectivescorerepository;
	@Autowired
	private PasswordEncoder passswordencoder;
	@Autowired
	private StaffReviewerRepository staffreviewerrepository;
	@Autowired
	private PlanScoreRepository planscorerepository;
	@Autowired
	private EmployeeCommentsRepository employeeCommentsRepository;
	@Autowired
	private PerformancePlanObjRepository performancePlanObjRepository;

	@Autowired
	private SubmitPerformancePlanRepository submitPerformancePlanRepository;

	@Autowired
	private EvaluationPlanObjRepository evaluationPlanObjRepository;

	@Autowired
	private SubmitEvaluationRepository submitEvaluationRepository;

	@Autowired
	private WorkplanRepository workplanRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PeriodRepository performancePeriodRepository;
	
	@Autowired
	private StaffcompetencyRepository staffcompetencyrepository;
	
	@Autowired
	private SupervisorcompetencyRepository supervisorcompetencyrepository;

	@Autowired
	private LookupCompetencyRepository lookupCompetencyRepository;

	@Autowired
	private PlanCompetencyRepository planCompetencyRepository;

	@Autowired
	private EvaluationCompetencyRepository evaluationCompetencyRepository;

	@Autowired
	private EvaluationCommentsRepository evaluationCommentsRepository;

	@Autowired
	private PerformancePlanCommentsRepository performancePlanCommentsRepository;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UserService userservice;

	@Autowired
	private PeriodRepository periodRepository;

	@Autowired
	private StaffReviewerRepository staffReviewerRepository;
	
	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	UserRoleRepository userrolerepository;
	
	@Autowired
	private HrperiodRepository hrperiodrepository;

	@RequestMapping("/appraise")
	public String appraise() {
		return "appraise1";
	}

	@RequestMapping("/myPerformancePlan")
	public String myPerformancePlan(Principal principal, Model model) {

		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
		Hrperiod hrperiod = hrperiodrepository.getperiod();
		String period = hrperiod.getPlanYear();
		String objectivesid = employee.getEmployee_id() + period;
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ " + objectivesid);
		List<SubmitPerformancePlan> myPlanList = submitPerformancePlanRepository
				.getAllSubmittedPlan(employee.getEmployee_id());
		List<SubmitPerformancePlan> staffPlanList = submitPerformancePlanRepository.staffPlanningList(employee.getEmployee_id());
		
		Hrperiod checkPlanningStatus = hrperiodrepository.planPeriodCheck(3);//dominic
		
		//check if staff have submitted for this period
		SubmitPerformancePlan submitplan = submitPerformancePlanRepository.getSubmittedPlan(checkPlanningStatus.getPlanYear(), employee.getEmployee_id());
		System.out.println("=========!!!!!!!!!!!!!!!!!!!!!!============"+submitplan);
		if(checkPlanningStatus.getStatus().equals("close") || submitplan != null) {
			model.addAttribute("planningStatus", true);
		}
		model.addAttribute("myPlanList", myPlanList);
		model.addAttribute("employee", employee);
		model.addAttribute("staffPlanList", staffPlanList);

		return "myplan";
	}

	@RequestMapping("/myAppraisalEvaluation")
	public String myAppraisalEvaluation(Principal principal, Model model) {

		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
		
		
		List<SubmitPerformancePlan> staffPlansAppra = submitPerformancePlanRepository.getAllStaffPlan(employee.getEmployee_id(), 3);
		Employee employeeSuperviorCheck = employeeRepository.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
		
		//List<PerformancePlanObj> submitedStaffObj = performancePlanObjRepository.getSubmitedPerformanceObjForStaff("3", employee.getEmployee_id(), "July, 202-December,2022");
		//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% " + submitedStaffObj);
		
		if (employeeSuperviorCheck != null) {
			//System.out.println("=================================================== we are here");
			model.addAttribute("isSupervisor", true);
		}
		model.addAttribute("employee", employee);
		//model.addAttribute("myAppraisalList", myAppraisalList);
		model.addAttribute("staffPlansAppra", staffPlansAppra);
		//model.addAttribute("submitedStaffObj", submitedStaffObj);

		return "myAppraisalEvaluation";
	}

	@RequestMapping("/")
	public String index() {
		return "redirect:/home";
	}

	@RequestMapping("/login")
	public String LoginPage(Model model) {

		model.addAttribute("classActiveLogin", true);
		return "lra_login";
	}

	@RequestMapping("/staff")
	public String getStaffAppraisal(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "staff";
	}

	@RequestMapping(value = "/staff-login", method = RequestMethod.POST)
	public String getStaffAppraisallogin(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password) {
		if (username.indexOf('.') != -1) {
			if (username != null && password.equalsIgnoreCase("111")) {
				model.addAttribute("invalidCredential", true);
				model.addAttribute("classActiveLogin", true);
				return "redirect:/staff-login?code=" + username;

			} else {

				return "errorPage";
			}

		} else {

			return "errorPage";
		}
	}

	@RequestMapping("/staff-login")
	public String getStaffAppraisalDetails(Model model, @RequestParam("code") String code) {
		Employee employee = employeeRepository.getEmployeeDetails(code.substring(0, code.indexOf('.')),
				code.substring(code.indexOf('.') + 1));
		if (employee != null) {
			List<Employee> staffList = employeeRepository.getEmployeeBySupervisorId(employee.getEmployee_id());
			model.addAttribute("staffList", staffList);
			model.addAttribute("employee", employee);
		} else {
			model.addAttribute("employee", new Employee());

		}
		return "staff";
	}

	@RequestMapping(value = "/login_Evaluation", method = RequestMethod.POST)
	public String viewLoginPageEvaluation(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password) {

//		Employee employee = employeeRepository.getEmployeeDetails(code, code);
//		System.err.println("===============================> " + code.subSequence(0, code.charAt('.')));

		if (username.indexOf('.') != -1) {
			if (username != null && password.equalsIgnoreCase("111")) {
				model.addAttribute("invalidCredential", true);
				model.addAttribute("classActiveLogin", true);
				return "redirect:/evaluation_login?code=" + username;

			} else {

				return "errorPage";
			}

		} else {

			return "errorPage";
		}
	}

	@RequestMapping(value = "/login_Planning", method = RequestMethod.POST)
	public String viewLoginPagePlanning(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password) {

		if (username.indexOf('.') != -1) {
			if (username != null && password.equalsIgnoreCase("111")) {
				model.addAttribute("invalidCredential", true);
				model.addAttribute("classActiveLogin", true);

				return "redirect:/planning_login?code=" + username;

			} else {

				return "errorPage";
			}
		} else {

			return "errorPage";
		}

	}

	@RequestMapping("/home")
	public String home(Principal principal, Model model) {
		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
		List<Employee> employeeList = employeeRepository.getEmployeeBySupervisorId(employee.getEmployee_id());
		
		Employee  supervisor= employeeRepository.findSupervisorDetail(employee.getEmployee_id());
		
		List<Employee> reviewEmployeeList = employeeRepository.getReviewerListByReviewerId(employee.getEmployee_id());

		if (reviewEmployeeList.size() > 0) {
			model.addAttribute("reviewList", true);
			//model.addAttribute("supervisor", supervisor);
		}
		model.addAttribute("employeeList", employeeList);
		model.addAttribute("reviewEmployeeList", reviewEmployeeList);
		model.addAttribute("supervisor", supervisor);
		model.addAttribute("employee", employee);
		model.addAttribute("planScore", new SubmitPerformancePlan());
		
		return "index";
	}

	@RequestMapping(value = "/removePerformancePlanObjective", method = RequestMethod.GET)
	public String removePerformancePlanObjective(Long id, String employee_id, Model model) {

		PerformancePlanObj performancePlanObj = performancePlanObjRepository.findOne(id);
		performancePlanObjRepository.delete(performancePlanObj);

		return "redirect:/plan_performances?id=" + performancePlanObj.getUser_id() + "#PerformancePlanSectionOne";
//		return "redirect:/plan_performance#PerformancePlanSectionOne";

	}

	@RequestMapping(value = "/removeEvaluationObjective", method = RequestMethod.GET)
	public String removeObj(Long id, String employee_id, Model model) {

		// Check if performance plan period is open
		String period = periodRepository.getOpenModule(1903);
		if (period != null) {
			Employee employee = employeeRepository.getEmployeeByEmployeeId(employee_id);

			if (employee != null) {
				model.addAttribute("employee", employee);
			} else {
				model.addAttribute("employee", new Employee());
			}

			EvaluationPlanObj evaluationPlanObj = evaluationPlanObjRepository.findOne(id);
			evaluationPlanObjRepository.delete(evaluationPlanObj);

			// Copy & Paste

			List<PerformancePlanObj> performancePlanList = (List<PerformancePlanObj>) performancePlanObjRepository
					.userPerformancePlanObj(employee.getEmployee_id(), period);
			ArrayList<String> perfPlanObj = new ArrayList<>();
			for (PerformancePlanObj perfPlanLoop : performancePlanList) {
				perfPlanObj.add(perfPlanLoop.getId() + "_" + perfPlanLoop.getPerformance_objective());
			}

			// Calculate grand Total
			List<EvaluationPlanObj> evaluationPlanObjsList = (List<EvaluationPlanObj>) evaluationPlanObjRepository
					.userEvaluationPlanObj(employee.getEmployee_id());

			double grandTotAssignWeigth = 0.00;
			double grandTot = 0.00;

			for (EvaluationPlanObj evaluationPlanObjLoop : evaluationPlanObjsList) {
				grandTotAssignWeigth += evaluationPlanObjLoop.getWeight_assigned();
				grandTot += evaluationPlanObjLoop.getTot();
			}

			model.addAttribute("grandTotAssignWeigth", Math.round(grandTotAssignWeigth) * 100 / 100.0);
			model.addAttribute("grandTot", Math.round(grandTot * 100) / 100.0);
			model.addAttribute("evaluationPlanObjsList", evaluationPlanObjsList);
			model.addAttribute("perfPlanObj", perfPlanObj);
			// model.addAttribute("employee", employee);
			System.out.println("Size of the list " + evaluationPlanObjsList.size());
			return "redirect:/evaluation#EvaluationSectionOne";

		} else {

			// Period is not open for planning should be in error page
			return "error";

		}

	}

	/*******************************************
	 * This section is for Approval
	 *******************************************/
	@RequestMapping("/viewplan")
	public String viewPlan(Model model, @RequestParam("employee_id") String employee_id) {

		Employee employee = employeeRepository.getEmployeeByEmployeeId(employee_id);
		if (employee != null) {
			model.addAttribute("employee", employee);
		} else {
			model.addAttribute("employee", new Employee());
		}

		double totalValue = 0.0;
		List<PerformancePlanObj> performancePlanList = (List<PerformancePlanObj>) performancePlanObjRepository
				.userPerformancePlanObj(employee.getEmployee_id(), "period");
		for (PerformancePlanObj obj : performancePlanList) {
			totalValue = totalValue + obj.getWeight_assigned();
		}

		SubmitPerformancePlan postAllPlanCompetencies = submitPerformancePlanRepository
				.getSubmittedPlanByEmployeeId(employee_id);
		model.addAttribute("postAllPlanCompetencies", postAllPlanCompetencies);

		List<Workplan> workplanActivityList = workplanRepository.worplanActivityLis("1");
//		List<Workplan> workplanActivityList = workplanRepository.worplanActivityList("1", "1");
		model.addAttribute("performancePlanList", performancePlanList);
		model.addAttribute("workplanActivityList", workplanActivityList);
		model.addAttribute("totalValue", totalValue);
		SubmitPerformancePlan submitPerformancePlan = new SubmitPerformancePlan();
		model.addAttribute("submitPerformancePlan", submitPerformancePlan);

		return "performanceplanning_approval";
	}

	@RequestMapping("/viewappraisal")
	public String viewappraisal(Model model, @RequestParam("employee_id") String employee_id) {

		Employee employee = employeeRepository.getEmployeeByEmployeeId(employee_id);
		if (employee != null) {
			model.addAttribute("employee", employee);
		} else {
			model.addAttribute("employee", new Employee());
		}
		// System.err.println("===============================> " + code.substring(0,
		// code.indexOf('.')));

		List<PerformancePlanObj> performancePlanList = (List<PerformancePlanObj>) performancePlanObjRepository
				.userPerformancePlanObj(employee.getEmployee_id(), "period");

		ArrayList<String> perfPlanObj = new ArrayList<>();
		for (PerformancePlanObj perfPlanLoop : performancePlanList) {
			perfPlanObj.add(perfPlanLoop.getId() + "_" + perfPlanLoop.getPerformance_objective());
		}

		// Calculate grand Total
		List<EvaluationPlanObj> evaluationPlanObjsList = (List<EvaluationPlanObj>) evaluationPlanObjRepository
				.userEvaluationPlanObj(employee.getEmployee_id());
		double grandTotAssignWeigth = 0.00;
		double grandTot = 0.00;

		for (EvaluationPlanObj evaluationPlanObjLoop : evaluationPlanObjsList) {
			grandTotAssignWeigth += evaluationPlanObjLoop.getWeight_assigned();
			grandTot += evaluationPlanObjLoop.getTot();
		}

		SubmitEvaluation submitEvaluation = submitEvaluationRepository.getSubmittedAppraisal(employee_id);
		model.addAttribute("submitEvaluation", submitEvaluation);

		model.addAttribute("grandTotAssignWeigth", Math.round(grandTotAssignWeigth) * 100 / 100.0);
		model.addAttribute("grandTot", Math.round(grandTot * 100) / 100.0);
		model.addAttribute("evaluationPlanObjsList", evaluationPlanObjsList);
//		model.addAttribute("performancePlanList", performancePlanList);
		model.addAttribute("perfPlanObj", perfPlanObj);
		System.out.println("Size of the list " + evaluationPlanObjsList.size());
		return "evaluationform_approval";
	}

	/**************************************************
	 * New Methods
	 *********************************************/

	// Method to be used for staff planning
	@RequestMapping("/plan_performance_staff")
	public String perfplanningStaff(@ RequestParam("id")String id, Principal principal, Model model) {

		// Check if performance plan period is open
		Hrperiod checkperiod = hrperiodrepository.planPeriodCheck(3);
		String period = checkperiod.getPlanYear();
		if (checkperiod.getStatus().equals("open")) {
			// Check if performance plan period is open
			int checkobjectiveweight = performancePlanObjRepository.totalweightassign(id, period);
			User user = userservice.findByUsername(principal.getName());
			Employee employee = employeeRepository.supervisorCheck(id);
			Employee supervisorCheck = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
			

			if (!employee.getSupervisor_id().equalsIgnoreCase(supervisorCheck.getEmployee_id())) {
				
				
				return "redirect:/";
			}
			SubmitPerformancePlan submitPerformancePlan = submitPerformancePlanRepository.getSubmittedPlan(period,
					id);
			if (submitPerformancePlan != null) {

				PerformancePlanComments lastComments = performancePlanCommentsRepository
						.getComments(submitPerformancePlan.getId());

				model.addAttribute("performancePlanComments", lastComments);
				model.addAttribute("submitPerformancePlan", submitPerformancePlan);

				if (submitPerformancePlan.getLevel() == 1) {

					if(checkobjectiveweight != 60 ) {
						model.addAttribute("approveReturnActionButton", false);
						model.addAttribute("editObjectiveActionButton", true);
						model.addAttribute("returnPlanBtn", true);
						model.addAttribute("supervisorPrivilege", true);
						model.addAttribute("staffPrivilegeDisable", true);
						model.addAttribute("approveReturnActionButton", false);
						model.addAttribute("staffComment", true);
						model.addAttribute("reviewerComment", true);
						model.addAttribute("supervisorComment", false);
					}else {
						model.addAttribute("approveReturnActionButton", true);
						model.addAttribute("staffComment", true);
						model.addAttribute("reviewerComment", true);
						model.addAttribute("supervisorComment", false);
						model.addAttribute("editObjectiveActionButton", true);
						model.addAttribute("supervisorPrivilege", true);
						model.addAttribute("staffPrivilegeDisable", true);
					}
					
					 //model.addAttribute("sectionOneError", true);

				} else if (submitPerformancePlan.getLevel() > 2) {

					model.addAttribute("supervisorPrivilegeDisable", true);
					model.addAttribute("staffComment", true);
					model.addAttribute("reviewerComment", true);
				}

			} else {

				//PerformancePlanComments lastComments = new PerformancePlanComments();
				//model.addAttribute("performancePlanComments", lastComments);
				model.addAttribute("submitPerformancePlan", new SubmitPerformancePlan());

			}

			double totalValue = 0.0;
			List<PerformancePlanObj> performancePlanList = (List<PerformancePlanObj>) performancePlanObjRepository
					.userPerformancePlanObj(employee.getEmployee_id(), period);
			PerformancePlanObj planid =performancePlanList.get(0);
			for (PerformancePlanObj obj : performancePlanList) {
				totalValue = totalValue + obj.getWeight_assigned();
			}
			System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY" + planid.getPlan_id());
			List<EmployeeComments> showcomments = employeeCommentsRepository.showComments(planid.getPlan_id());
			model.addAttribute("showcomments", showcomments);
			List<LookupCompetency> competencyLookup = lookupCompetencyRepository
					.getCompetency(employee.getPosition_id());
			
			List<Workplan> workplanActivityList = workplanRepository.worplanActivityLis(employee.getEmployee_id());

			List<PlanCompetency> validateLoadCompetency = planCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());

			if (validateLoadCompetency.size() > 0) {

			} else {

				for (LookupCompetency comp : competencyLookup) {
					PlanCompetency planCompetency = new PlanCompetency();
					planCompetency.setCompetency_id((int) comp.getId());
					planCompetency.setEmployee_id(employee.getEmployee_id());
					planCompetency.setPeriod(period);
					planCompetency.setCompetency_title(comp.getCompetency_title());
					planCompetency.setPosition_id(comp.getPosition_id());
					planCompetency.setRequired_point(comp.getRequired_point());
					planCompetencyRepository.save(planCompetency);
				}

			}

			// Validation Check if score is > 40 else show error page
			double totalCompetencyValue = 0.0;
			List<PlanCompetency> coreCompetency = planCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			for (PlanCompetency comp : coreCompetency) {
				totalCompetencyValue = totalCompetencyValue + Double.valueOf(comp.getActual_point());
			}

			// Check if employee is a supervisor
			Employee employeeSuperviorCheck = employeeRepository.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
			if (employeeSuperviorCheck != null) {
				model.addAttribute("isSupervisor", true);
			}

			model.addAttribute("coreCompetency", coreCompetency);
			model.addAttribute("performancePlanList", performancePlanList);
			model.addAttribute("workplanActivityList", workplanActivityList);
			model.addAttribute("employee", employee);
			model.addAttribute("totalValue", totalValue);
			model.addAttribute("totalCompetencyValue", totalCompetencyValue);
			model.addAttribute("period", period);
		}
		
		String staffid = id;
		model.addAttribute("staffid", staffid);

		return "performanceplanning";
	}

	// Method to be used for staff planning
	@RequestMapping("/review_plan_performance_staff")
	public String reviewPerfplanningStaff(@RequestParam("id") String id, Principal principal, Model model) {
		String staffid = id;
		Hrperiod hrperiod = hrperiodrepository.getperiod();
		String period = hrperiod.getPlanYear();
		 SubmitPerformancePlan submitplan = submitPerformancePlanRepository.getSubmittedPlan(period, staffid);
		
		// Check if performance plan period is open
		
		if (period != null) {

			// Check if performance plan period is open
			User user = userservice.findByUsername(principal.getName());
			Employee reviewer = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
			Employee employee = employeeRepository.supervisorCheck(id);
			StaffReviewer reviewerCheck = staffReviewerRepository.reviewerCheck(employee.getEmployee_id(),
					reviewer.getEmployee_id());

			// Check this code well
			if (submitplan.getLevel() >= 2) {
			} else {
				return "redirect:/";
			}
			SubmitPerformancePlan submitPerformancePlan = submitPerformancePlanRepository.getApprovedPlan(period,
					staffid);
			if (submitPerformancePlan != null) {

				PerformancePlanComments lastComments = performancePlanCommentsRepository
						.getComments(submitPerformancePlan.getId());

				model.addAttribute("performancePlanComments", lastComments);
				model.addAttribute("submitPerformancePlan", submitPerformancePlan);

			} else {

				return "redirect:/";
			}

			double totalValue = 0.0;
			List<PerformancePlanObj> performancePlanList = (List<PerformancePlanObj>) performancePlanObjRepository
					.userPerformancePlanObj(employee.getEmployee_id(), period);
			
			PerformancePlanObj planid = performancePlanList.get(0);
			List<EmployeeComments> showcomments = employeeCommentsRepository.showComments(planid.getPlan_id());
			model.addAttribute("showcomments", showcomments);
			for (PerformancePlanObj obj : performancePlanList) {
				totalValue = totalValue + obj.getWeight_assigned();
			}

			List<LookupCompetency> competencyLookup = lookupCompetencyRepository
					.getCompetency(employee.getPosition_id());
			
			List<Workplan> workplanActivityList = workplanRepository.worplanActivityLis(employee.getEmployee_id());

//			List<Workplan> workplanActivityList = workplanRepository.worplanActivityList(employee.getPosition_id(),
//					employee.getEmployee_id());

			List<PlanCompetency> validateLoadCompetency = planCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());

			if (validateLoadCompetency.size() > 0) {

			} else {

				for (LookupCompetency comp : competencyLookup) {
					PlanCompetency planCompetency = new PlanCompetency();
					planCompetency.setCompetency_id((int) comp.getId());
					planCompetency.setEmployee_id(employee.getEmployee_id());
					planCompetency.setPeriod(period);
					planCompetency.setCompetency_title(comp.getCompetency_title());
					planCompetency.setPosition_id(comp.getPosition_id());
					planCompetency.setRequired_point(comp.getRequired_point());
					planCompetencyRepository.save(planCompetency);
				}

			}

			// Validation Check if score is > 40 else show error page
			double totalCompetencyValue = 0.0;
			List<PlanCompetency> coreCompetency = planCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			for (PlanCompetency comp : coreCompetency) {
				totalCompetencyValue = totalCompetencyValue + Double.valueOf(comp.getActual_point());
			}

			// Check if employee is a supervisor
			Employee employeeSuperviorCheck = employeeRepository.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
			if (employeeSuperviorCheck != null) {
				model.addAttribute("isSupervisor", true);
			}

			model.addAttribute("coreCompetency", coreCompetency);
			model.addAttribute("performancePlanList", performancePlanList);
			model.addAttribute("workplanActivityList", workplanActivityList);
			model.addAttribute("employee", employee);
			model.addAttribute("totalValue", totalValue);
			model.addAttribute("totalCompetencyValue", totalCompetencyValue);
			model.addAttribute("period", period);
		}
		model.addAttribute("staffid", staffid);

		return "review_performanceplanning";
	}
	
	// db
	@RequestMapping("/plan_performances")
	public String perfplan( @RequestParam("id") String id, Principal principal, Model model) {
		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeByEmployeeId(id);
		Employee loginUser = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
		
		Hrperiod hrperiod = hrperiodrepository.getperiod();
		String period = hrperiod.getPlanYear();
		
		String isSupervisor = id;
		String isStaff = loginUser.getEmployee_id();
;		
		SubmitPerformancePlan submitPerformancePlan = submitPerformancePlanRepository.getSubmittedPlan(period,
				id);
		
		
		if (submitPerformancePlan != null) {
			
			int currentLevel = submitPerformancePlan.getLevel();
			PerformancePlanComments staffStatement = performancePlanCommentsRepository.getComment(submitPerformancePlan.getPlan_id(), id);
			//System.out.println("%%%%%%%%  DOMINIC  %%%%%%%%%%%%%%%%%%%%%%%%%%%"+staffStatement.getStaffComments()+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+staffStatement.getEmployeeId());
			List<EmployeeComments> showcomments = employeeCommentsRepository.showComments(submitPerformancePlan.getPlan_id());
			
			if(isSupervisor.equals(isStaff)) {
				if(currentLevel == 1) {
					model.addAttribute("submitActionButton", false);
 					model.addAttribute("addObjectiveActionButton", false);
					model.addAttribute("removeObjectiveActionButton", false);
					model.addAttribute("editObjectiveActionButton", false);
					model.addAttribute("approveReturnActionButton", false);
					model.addAttribute("staffComment", true);
					model.addAttribute("reviewerComment", false);
					model.addAttribute("supervisorComment", false);
					

				}else if(currentLevel == 0) {
					model.addAttribute("addObjectiveActionButton", true);
					model.addAttribute("submitActionButton", true);
					model.addAttribute("editObjectiveActionButton", false);
					model.addAttribute("reviewerComment", true);
					model.addAttribute("removeObjectiveActionButton", true);
				}
			}
			
				
			//}
			
			model.addAttribute("staffStatement", staffStatement);
			model.addAttribute("showcomments", showcomments);
			
			
		//	addObjectiveActionButton
			model.addAttribute("submitPerformancePlan", submitPerformancePlan);
		}else {
			
			model.addAttribute("submitActionButton", true);
			model.addAttribute("supervisorComment", true);
			model.addAttribute("reviewerComment", true);
			model.addAttribute("removeObjectiveActionButton", true);
			model.addAttribute("editObjectiveActionButton", true);
			model.addAttribute("addObjectiveActionButton", true);
		}
		
		List<PerformancePlanObj> performancePlanList = (List<PerformancePlanObj>) performancePlanObjRepository
					.userPerformancePlanObj(employee.getEmployee_id(), period);
		
		model.addAttribute("employee", employee);
		model.addAttribute("performancePlanList", performancePlanList);
		model.addAttribute("period", period);
		
		return "performanceplanning";
	}

	@RequestMapping("/plan_performance")
	public String perfplanning( Principal principal, Model model) {
		
		

		// Check if performance plan period is open
		Hrperiod hrperiod = hrperiodrepository.getperiod();
		
		String period = hrperiod.getPlanYear();
		if (period != null) {
			// Check if performance plan period is open
			User user = userservice.findByUsername(principal.getName());
			Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
			
			/*String staffid;
			if(id.equals(employee.getEmployee_id())) {
				 staffid = employee.getEmployee_id();
				 
				 
				 System.out.println("*********id*******%%%**&&&&&&&&" + staffid);
			}else {
				 staffid = id;
				 System.out.println("*********id*********&&&&&&&&" + staffid);
			}*/
			
			SubmitPerformancePlan submitPerformancePlan = submitPerformancePlanRepository.getSubmittedPlan(period,
					employee.getEmployee_id());
			
			//System.out.println("******************&&&&&&&&" + submitPerformancePlan.getLevel());
			if (submitPerformancePlan != null) {

				PerformancePlanComments lastComments = performancePlanCommentsRepository
						.getComments(114);

				model.addAttribute("performancePlanComments", lastComments);
				model.addAttribute("submitPerformancePlan", submitPerformancePlan);
				if (submitPerformancePlan.getLevel() == 1) {
					/*System.out.println("******************&&&&&&&&" + submitPerformancePlan.getLevel());
					model.addAttribute("submitActionButton", true);
					model.addAttribute("supervisorComment", true);
					model.addAttribute("reviewerComment", true);
					model.addAttribute("removeObjectiveActionButton", true);
					model.addAttribute("editObjectiveActionButton", true);
					model.addAttribute("addObjectiveActionButton", true);
					model.addAttribute("supervisorPrivilegeDisable", true);*/
					
					model.addAttribute("submitActionButton", false);
					model.addAttribute("supervisorComment", true);
					model.addAttribute("reviewerComment", true);
					model.addAttribute("removeObjectiveActionButton", false);
					model.addAttribute("editObjectiveActionButton", false);
					model.addAttribute("addObjectiveActionButton", false);
					model.addAttribute("supervisorPrivilegeDisable", true);

				} else {
//					model.addAttribute("postPerformancePlanSaved", true);
					/*model.addAttribute("supervisorComment", true);
					model.addAttribute("reviewerComment", true);
					model.addAttribute("submitActionButton", false);
					model.addAttribute("addObjectiveActionButton", false);*/
					
					model.addAttribute("submitActionButton", true);
					model.addAttribute("supervisorComment", true);
					model.addAttribute("reviewerComment", true);
					model.addAttribute("removeObjectiveActionButton", true);
					model.addAttribute("editObjectiveActionButton", false);
					model.addAttribute("addObjectiveActionButton", true);
				}

			} else {

				//PerformancePlanComments lastComments = new PerformancePlanComments();
				//model.addAttribute("performancePlanComments", lastComments);
				model.addAttribute("submitPerformancePlan", new SubmitPerformancePlan());

				model.addAttribute("submitActionButton", true);
				model.addAttribute("supervisorComment", true);
				model.addAttribute("reviewerComment", true);
				model.addAttribute("removeObjectiveActionButton", true);
				model.addAttribute("editObjectiveActionButton", false);
				model.addAttribute("addObjectiveActionButton", true);
				model.addAttribute("supervisorPrivilegeDisable", true);

			}

			double totalValue = 0.0;
			List<PerformancePlanObj> performancePlanList = (List<PerformancePlanObj>) performancePlanObjRepository
					.userPerformancePlanObj(employee.getEmployee_id(), period);
			for (PerformancePlanObj obj : performancePlanList) {
				totalValue = totalValue + obj.getWeight_assigned();
			}

			List<LookupCompetency> competencyLookup = lookupCompetencyRepository
					.getCompetency(employee.getPosition_id());
			
			List<Workplan> workplanActivityList = workplanRepository.worplanActivityLis(employee.getEmployee_id());

//			List<Workplan> workplanActivityList = workplanRepository.worplanActivityList(employee.getPosition_id(),
//					employee.getEmployee_id());

			List<PlanCompetency> validateLoadCompetency = planCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());

			if (validateLoadCompetency.size() > 0) {

			} else {

				for (LookupCompetency comp : competencyLookup) {
					PlanCompetency planCompetency = new PlanCompetency();
					planCompetency.setCompetency_id((int) comp.getId());
					planCompetency.setEmployee_id(employee.getEmployee_id());
					planCompetency.setPeriod(period);
					planCompetency.setCompetency_title(comp.getCompetency_title());
					planCompetency.setPosition_id(comp.getPosition_id());
					planCompetency.setRequired_point(comp.getRequired_point());
					planCompetencyRepository.save(planCompetency);
				}

			}

			// Validation Check if score is > 40 else show error page
			double totalCompetencyValue = 0.0;
			List<PlanCompetency> coreCompetency = planCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			for (PlanCompetency comp : coreCompetency) {
				totalCompetencyValue = totalCompetencyValue + Double.valueOf(comp.getActual_point());
			}

			// Check if employee is a supervisor
			Employee employeeSuperviorCheck = employeeRepository.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
			if (employeeSuperviorCheck != null) {
				model.addAttribute("isSupervisor", true);
			}

			model.addAttribute("coreCompetency", coreCompetency);
			model.addAttribute("performancePlanList", performancePlanList);
			model.addAttribute("workplanActivityList", workplanActivityList);
			model.addAttribute("employee", employee);
			model.addAttribute("totalValue", totalValue);
			model.addAttribute("totalCompetencyValue", totalCompetencyValue);
			model.addAttribute("period", period);

		}

		return "performanceplanning";

	}

	@RequestMapping("/plan_performance_period")
	public String perfplanningByPeriod(@ModelAttribute("period") String period, Principal principal, Model model) {

		// Check if performance plan period is open
		SubmitPerformancePlan submitPerformancePlanPeriodCheck = submitPerformancePlanRepository
				.getAllSubmittedPlanByPeriod(period);

		/*
		 * if (submitPerformancePlanPeriodCheck != null) {
		 * 
		 * } else {
		 * 
		 * return "redirected:/myPerformancePlan"; }
		 */

		// Check if performance plan period is open
		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
		SubmitPerformancePlan submitPerformancePlan = submitPerformancePlanRepository.getSubmittedPlan(period,
				employee.getEmployee_id());
		if (submitPerformancePlan != null) {

			PerformancePlanComments lastComments = performancePlanCommentsRepository
					.getComments(submitPerformancePlan.getId());
			if (lastComments != null) {

				model.addAttribute("performancePlanComments", lastComments);
			} else {

				//model.addAttribute("performancePlanComments", new PerformancePlanComments());
			}

			model.addAttribute("submitPerformancePlan", submitPerformancePlan);

			// Disable comment section based on level
			if (submitPerformancePlan.getLevel() == 1 || submitPerformancePlan.getLevel() == 0L) {

				model.addAttribute("disableStaffComment", true);
				model.addAttribute("disableReviewerComment", true);
				model.addAttribute("postPerformancePlanSubmitButton", true);

			} else if (submitPerformancePlan.getLevel() == 2) {

				model.addAttribute("disableSupervisorComment", true);
				model.addAttribute("disableReviewerComment", true);
				model.addAttribute("postPerformancePlanApproveButton", true);
				model.addAttribute("postPerformancePlanSavedButton", true);

			} else if (submitPerformancePlan.getLevel() == 3) {

				model.addAttribute("disableSupervisorComment", true);
				model.addAttribute("disableStaffComment", true);
				model.addAttribute("postPerformancePlanSavedButton", true);
			}
		} else {

			//PerformancePlanComments lastComments = new PerformancePlanComments();
			//model.addAttribute("performancePlanComments", lastComments);
			model.addAttribute("submitPerformancePlan", new SubmitPerformancePlan());
			model.addAttribute("postPerformancePlanAddActualScore", true);
			model.addAttribute("disableSupervisorComment", true);
			model.addAttribute("disableReviewerComment", true);
		}

		double totalValue = 0.0;
		List<PerformancePlanObj> performancePlanList = (List<PerformancePlanObj>) performancePlanObjRepository
				.userPerformancePlanObj(employee.getEmployee_id(), period);
		for (PerformancePlanObj obj : performancePlanList) {
			totalValue = totalValue + obj.getWeight_assigned();
		}

		List<LookupCompetency> competencyLookup = lookupCompetencyRepository.getCompetency(employee.getPosition_id());
		
		List<Workplan> workplanActivityList = workplanRepository.worplanActivityLis(employee.getEmployee_id());

//		List<Workplan> workplanActivityList = workplanRepository.worplanActivityList(employee.getPosition_id(),
//				employee.getEmployee_id());

		// Get latest period order by date
		String performance_period = period;

		// Check if plan has been loaded for employee_id and the period and the position
		// otherwise load plan

		List<PlanCompetency> validateLoadCompetency = planCompetencyRepository.getCompetency(performance_period,
				employee.getEmployee_id());

		if (validateLoadCompetency.size() > 0) {

		} else {

			for (LookupCompetency comp : competencyLookup) {
				PlanCompetency planCompetency = new PlanCompetency();
				planCompetency.setCompetency_id((int) comp.getId());
				planCompetency.setEmployee_id(employee.getEmployee_id());
				planCompetency.setPeriod(performance_period);
				planCompetency.setCompetency_title(comp.getCompetency_title());
				planCompetency.setPosition_id(comp.getPosition_id());
				planCompetency.setRequired_point(comp.getRequired_point());

				planCompetencyRepository.save(planCompetency);
			}

		}

		// Validation Check if score is > 40 else show error page
		double totalCompetencyValue = 0.0;
		List<PlanCompetency> coreCompetency = planCompetencyRepository.getCompetency(performance_period,
				employee.getEmployee_id());
		for (PlanCompetency comp : coreCompetency) {
			totalCompetencyValue = totalCompetencyValue + Double.valueOf(comp.getActual_point());
		}

		// Check if plan has been submitted before
//		SubmitPerformancePlan submitPerformancePlanExist = submitPerformancePlanRepository
//				.getSubmittedPlanByStaffForPlanningOperation(period, employee.getEmployee_id());
//		if (submitPerformancePlanExist != null) {
//			model.addAttribute("returnPerformancePlan", false);
//		}

//		model.addAttribute("postPerformancePlanSavedButton", true);

		PlanCompetency planCompetency = new PlanCompetency();

		// Check if employee is a supervisor
		Employee employeeSuperviorCheck = employeeRepository.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
		if (employeeSuperviorCheck != null) {
			model.addAttribute("isSupervisor", true);
		}

		model.addAttribute("planCompetency", planCompetency);
		model.addAttribute("coreCompetency", coreCompetency);
		model.addAttribute("performancePlanList", performancePlanList);
		model.addAttribute("workplanActivityList", workplanActivityList);
		model.addAttribute("employee", employee);
		model.addAttribute("totalValue", totalValue);
		model.addAttribute("totalCompetencyValue", totalCompetencyValue);
		model.addAttribute("period", period);

		return "performanceplanning";

	}

	@RequestMapping(value = "/savePlanActualScore", method = RequestMethod.POST)
	public String savePlanActualScore(@ModelAttribute("PlanCompetency") PlanCompetency planCompetency,
			@ModelAttribute("id") int id, @ModelAttribute("score") double score,
			@ModelAttribute("employeeIdTransfered") String employeeIdTransfered,
			@ModelAttribute("competencyId") long competencyId, Principal principal, Model model) {

//		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeByEmployeeId(employeeIdTransfered);
		String period = periodRepository.getOpenModule(1902);

		if (period != null) {
			SubmitPerformancePlan submitPerformancePlan = submitPerformancePlanRepository.getSubmittedPlan(period,
					employee.getEmployee_id());
			if (submitPerformancePlan != null) {
				model.addAttribute("submitPerformancePlan", submitPerformancePlan);
				model.addAttribute("approveReturnActionButton", true);
				model.addAttribute("staffComment", true);
				model.addAttribute("reviewerComment", true);
				model.addAttribute("editObjectiveActionButton", true);
				model.addAttribute("supervisorPrivilege", true);
				model.addAttribute("staffPrivilegeDisable", true);
				;

				// Load Comments
				PerformancePlanComments lastComments = performancePlanCommentsRepository
						.getComments(submitPerformancePlan.getId());
				if (lastComments != null) {

					model.addAttribute("performancePlanComments", lastComments);
				} else {

					//model.addAttribute("performancePlanComments", new PerformancePlanComments());
				}

			} else {

				model.addAttribute("submitPerformancePlan", new SubmitPerformancePlan());
				//model.addAttribute("performancePlanComments", new PerformancePlanComments());
				//model.addAttribute("staffComment", true);
				//model.addAttribute("reviewerComment", true);
			}

			// Validation Check if score is >= 40 else update score
			double totalValueSectionTwo = 0.0;
			double totalValueSectionOne = 0.0;
			double totalValueSectionTwoTempTotal = 0.0;

			// Summing Section Two Total
			List<PlanCompetency> coreCompetency = planCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			for (PlanCompetency comp : coreCompetency) {
				totalValueSectionTwo = totalValueSectionTwo + Double.valueOf(comp.getActual_point());
			}

			// Summing Section Two Temp Total for validating
			List<PlanCompetency> coreCompetencyTempTotal = planCompetencyRepository
					.getCompetencyTempTotalExcludeCompetencyId(period, employee.getEmployee_id(), competencyId);
			for (PlanCompetency comp : coreCompetencyTempTotal) {
				totalValueSectionTwoTempTotal = totalValueSectionTwoTempTotal + Double.valueOf(comp.getActual_point());
			}

			double totalValueSectionTwoTempTotalPlusInputtedScore = totalValueSectionTwoTempTotal + score;
			if (totalValueSectionTwoTempTotalPlusInputtedScore <= 40.0) {

				planCompetencyRepository.updatePlan(score, id);
				model.addAttribute("totalCompetencyValue", totalValueSectionTwo);

			} else {

				// Need to add section one and two in model because we are returning to a page
				// and not URL (Controller Method)
				// Summing Section One and save in model
				List<PerformancePlanObj> performancePlanObjectiveList = (List<PerformancePlanObj>) performancePlanObjRepository
						.userPerformancePlanObj(employee.getEmployee_id(), period);
				for (PerformancePlanObj obj : performancePlanObjectiveList) {
					totalValueSectionOne = totalValueSectionOne + obj.getWeight_assigned();
				}

				// Summing Section Two and save in model
				
				List<Workplan> workplanActivityList = workplanRepository.worplanActivityLis(employee.getEmployee_id());
				
//				List<Workplan> workplanActivityList = workplanRepository.worplanActivityList(employee.getPosition_id(),
//						employee.getEmployee_id());

				model.addAttribute("performancePlanList", performancePlanObjectiveList);
				model.addAttribute("workplanActivityList", workplanActivityList);
				model.addAttribute("coreCompetency", coreCompetency);
				model.addAttribute("totalValue", totalValueSectionOne);
				model.addAttribute("totalCompetencyValue", totalValueSectionTwo);
				model.addAttribute("employee", employee);
				model.addAttribute("sectionTwoError", true);

				return "performanceplanning";
			}
			// Check User before redirecting
//			return "redirect:/plan_performance_staff?id=" + employee.getEmployee_id() + "#PerformancePlanSectionTwo";
			return "redirect:/plan_performance_staff?id=" + employee.getEmployee_id() + "#routeSectionTwoHere";
		} else {
			return "error";
		}

	}
	
	@RequestMapping(value = "/saveStaffActionTaken", method = RequestMethod.POST)
	public String saveStaffActionTaken(
			@ModelAttribute("myid") long myid, 
			@ModelAttribute("staffActionTaken") String empActionTaken, 
			@ModelAttribute("staffid") String staffid, 
			@ModelAttribute("total") String total, Model model,
			Principal principal) {
		Hrperiod hrperiod = hrperiodrepository.getperiod();
		String period = hrperiod.getPlanYear();
		PerformancePlanObj planObjective = performancePlanObjRepository.findPlanById(myid);
		if (hrperiod != null){
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" + empActionTaken + "try me " + myid);

			planscorerepository.updateSuperActionTaken(empActionTaken, myid);
			
			return "redirect:/viewEvaluation?id="+planObjective.getPlan_id() + "#EvaluationSectionOne";

		} else {

			return "error";
		}

	}

	@RequestMapping(value = "/saveEvaluationActualScore", method = RequestMethod.POST)
	public String saveEvaluationActualScore(
			//@ModelAttribute("planScore") PlanScore planScore,
			@ModelAttribute("myid") long myid, 
			//@ModelAttribute("staffActionTaken") String staffActionTaken,
			@ModelAttribute("supActionTaken") String supActionTaken,
			@ModelAttribute("actual") String actual, 
			@ModelAttribute("finalScore") String finalScore, 
			@ModelAttribute("staffid") String staffid, 
			@ModelAttribute("total") String total, Model model,
			Principal principal) {
		System.out.println( myid +"id" + supActionTaken + "sup" + actual + "actu "+ finalScore + "fina "+ staffid + "staff " + total);
		int actual1 = Integer.valueOf(actual);
		int total1 = Integer.valueOf(total);
		double finalscore1 = Double.parseDouble(finalScore);
		
		Hrperiod hrperiod = hrperiodrepository.getperiod();
		String period = hrperiod.getPlanYear();
		if (hrperiod.getId() == 1 || hrperiod.getId() == 2) {

			User user = userservice.findByUsername(principal.getName());
			Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
			;
			
			PlanScore isObjectPlanScoreAdded = planscorerepository.checkplanscore(myid, hrperiod.getPeriods());
			if(isObjectPlanScoreAdded == null) {
			PlanScore planscore1 = new PlanScore();
			planscore1.setActual(actual1);
			planscore1.setAppraisalYear(period);
			planscore1.setFinalScore(finalscore1);
			planscore1.setPerformancePeriod(hrperiod.getPeriods());
			planscore1.setPerfromanceobjid(myid);
			planscore1.setStaffid(staffid);
			planscore1.setSupActionTaken(supActionTaken);
			planscore1.setTotal(total1);
			
			planscorerepository.save(planscore1);
			}else {
				planscorerepository.updatePlanScore(myid, actual1, total1, finalscore1);
			}
			
			String  planTotalScore = planscorerepository.totalPlanScore(staffid, hrperiod.getPeriods(), period);
			System.out.println("==========()(())(())()()(()(()())()(()()())()(())()((=========="+planTotalScore);

			

			double attained_weight = 0.00;
			double actual_proficiency = 0.00;
			String rmk = "";
			
			model.addAttribute("planTotalScore", planTotalScore);

			

			return "redirect:/evaluation?id="+staffid + "#EvaluationSectionOne";

		} else {

			// Period is not open for planning should be in error page
			return "redirect:/evaluation?id="+staffid + "#EvaluationSectionOne";
		}

	}

	@RequestMapping(value = "/submitPerformancePlan_Delete", method = RequestMethod.POST)
	public String submitPerformancePlan(@ModelAttribute("PlanCompetency") PlanCompetency planCompetency,
			@ModelAttribute("id") int id, @ModelAttribute("score") double score) {

		// Validation Check if score is >= 40 else update score
		planCompetencyRepository.updatePlan(score, id);

		return "redirect:/planning_login?code=mcrinna.collins";
	}

	@RequestMapping(value = "/addActivityPerformancePlan", method = RequestMethod.POST)
	public String addActivityPerformancePlan(
			@ModelAttribute("performancePlan") PerformancePlanObj performancePlan,
			@ModelAttribute("employeeIdTransfered") String employeeIdTransfered, 
			Principal principal, 
			Model model) {
		Employee employee = employeeRepository.getEmployeeByEmployeeId(employeeIdTransfered);
		//System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<,," + employeeIdTransfered);
		//check planning status
			Hrperiod performancePeriod = hrperiodrepository.planPeriodCheck(3);
			String checkPlanningStatus = performancePeriod.getStatus();
		// Check if performance plan period is open
			String period = performancePeriod.getPlanYear();
		//if (period != null) {
			if (checkPlanningStatus.equals("open")) {
				
				String currentYear = period;
				String objectivesid = employee.getEmployee_id() + currentYear;
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ " + objectivesid);
	
				double totalValue = 0.0;
				double totalValueCompare = 0.0;
				performancePlan.setUser_id(employee.getEmployee_id());
				performancePlan.setPlan_id(objectivesid);
				performancePlan.setPlanYear(performancePeriod.getPlanYear());
				
				List<PerformancePlanObj> performancePlanObjectiveList = (List<PerformancePlanObj>) performancePlanObjRepository
						.userPerformancePlanObj(employee.getEmployee_id(), period);
	
				for (PerformancePlanObj obj : performancePlanObjectiveList) {
	
					totalValue = totalValue + obj.getWeight_assigned();
				}
	
				totalValueCompare = totalValue + performancePlan.getWeight_assigned();
				List<Workplan> workplanActivityList = workplanRepository.worplanActivityLis(employee.getEmployee_id());
				//workplanRepository.worplanActivityList(employee.getEmployee_id());
	
				model.addAttribute("employee", employee);
				model.addAttribute("performancePlanList", performancePlanObjectiveList);
				model.addAttribute("workplanActivityList", workplanActivityList);
	
				SubmitPerformancePlan submitPerformancePlan = submitPerformancePlanRepository.getSubmittedPlan(period,
						employee.getEmployee_id());
				if (submitPerformancePlan != null) {
					model.addAttribute("submitPerformancePlan", submitPerformancePlan);
	
					// Return latest comment on the page
					PerformancePlanComments lastComments = performancePlanCommentsRepository
							.getComments(submitPerformancePlan.getId());
	
					model.addAttribute("performancePlanComments", lastComments);
					if (submitPerformancePlan.getLevel() <= 1) {
						model.addAttribute("submitActionButton", true);
						model.addAttribute("supervisorComment", true);
						model.addAttribute("reviewerComment", true);
						model.addAttribute("removeObjectiveActionButton", true);
						model.addAttribute("editObjectiveActionButton", false);
						model.addAttribute("addObjectiveActionButton", true);
						model.addAttribute("supervisorPrivilegeDisable", true);
	
					} else {
	//					model.addAttribute("postPerformancePlanSaved", true);
						model.addAttribute("supervisorComment", true);
						model.addAttribute("reviewerComment", true);
					}
	
				} else {
	
					//model.addAttribute("performancePlanComments", new PerformancePlanComments());
					model.addAttribute("submitPerformancePlan", new SubmitPerformancePlan());
					model.addAttribute("submitActionButton", true);
					model.addAttribute("supervisorComment", true);
					model.addAttribute("reviewerComment", true);
					model.addAttribute("removeObjectiveActionButton", true);
					model.addAttribute("editObjectiveActionButton", false);
					model.addAttribute("addObjectiveActionButton", true);
					model.addAttribute("supervisorPrivilegeDisable", true);
	
				}
	
				// Check if employee is a supervisor
				Employee employeeSuperviorCheck = employeeRepository.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
				if (employeeSuperviorCheck != null) {
					model.addAttribute("isSupervisor", true);
				}
	
				// Summing Section Two
				List<PlanCompetency> coreCompetency = planCompetencyRepository.getCompetency(period,
						employee.getEmployee_id());
				model.addAttribute("coreCompetency", coreCompetency);
	
				// Total Value of Objective manipulation
				if (totalValueCompare > 60) {
					model.addAttribute("totalValue", totalValue);
					model.addAttribute("employee", employee);
					model.addAttribute("sectionOneError", true);
	
					return "performanceplanning";
				} else {

				// Check if performance objective is already added
					PerformancePlanObj performancePlanObjectiveSaved = null;
					PerformancePlanObj performancePlanObjectiveValidate = performancePlanObjRepository
							.validateIfUserAddedObjective(employee.getEmployee_id(), period,
									performancePlan.getPerformance_objective());
	
					if (performancePlanObjectiveValidate != null) {
	
					} else {
						System.out.println("^^^^^^^^^^^^^^^$$$$$$$$$$^^^^^^^^^^^^^^^^^^^^^^^ " + performancePlan);
						performancePlanObjectiveSaved = performancePlanObjRepository.save(performancePlan);
					}
	
					// Check if user ID is not equals id in form submission then redirect
					// return "redirect:/plan_performance_staff?id=" + employee.getEmployee_id() +
					// "#PerformancePlanSectionOne";
	
					return "redirect:/plan_performances?id=" + employeeIdTransfered + "#PerformancePlanSectionOne";
			}
			} else {
				//return "error";
				return "redirect:/myPerformancePlan";
			}
		}
	
	@RequestMapping(value = "/assignWeight", method = RequestMethod.POST)
	public String assignweight(
			@ModelAttribute("employeeIdTransfered") String employeeIdTransfered,
			@ModelAttribute("objectid") long objectid,
			@ModelAttribute("planid") String planid,
			@ModelAttribute("weight_assigned") int weight_assigned) {
		Employee employee = employeeRepository.getEmployeeByEmployeeId(employeeIdTransfered);
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+planid);
		
		performancePlanObjRepository.updateweightassign(weight_assigned, objectid);
		int weightTotal = performancePlanObjRepository.countweightassign(planid);
		//int finalWeightTotal = weightTotal + weight_assigned;
		
		if(weightTotal <= 60) {
			return "redirect:/plan_performance_staff?id=" + employee.getEmployee_id() + "#PerformancePlanSectionOne";
		}else {
			performancePlanObjRepository.updateweightassign(0, objectid);
			return "redirect:/plan_performance_staff?id=" + employee.getEmployee_id() + "#PerformancePlanSectionOne";
		}
		
		
	} 

	@RequestMapping(value = "/editActivityPerformancePlan", method = RequestMethod.POST)
	public String editActivityPerformancePlan(@ModelAttribute("performancePlan") PerformancePlanObj performancePlan,
			@ModelAttribute("employeeIdTransfered") String employeeIdTransfered, Principal principal, Model model) {

		// Check if performance plan period is open
		String period = periodRepository.getOpenModule(1902);
		if (period != null) {

			// User user = userservice.findByUsername(principal.getName());
			Employee employee = employeeRepository.getEmployeeByEmployeeId(employeeIdTransfered);

			double totalValue = 0.0;
			double totalValueCompare = 0.0;
			performancePlan.setUser_id(employee.getEmployee_id());
			performancePlan.setPlanYear(period);
			List<PerformancePlanObj> performancePlanObjectiveList = (List<PerformancePlanObj>) performancePlanObjRepository
					.userPerformancePlanObj(employee.getEmployee_id(), period);

			// Summing Section Two Temp Total for validating
			List<PerformancePlanObj> objectiveTempTotal = performancePlanObjRepository
					.getObjectiveTempTotalExcludeObjectiveId(period, employee.getEmployee_id(),
							performancePlan.getId());
			for (PerformancePlanObj comp : objectiveTempTotal) {
				totalValueCompare = totalValueCompare + comp.getWeight_assigned();
			}
			totalValueCompare = totalValueCompare + performancePlan.getWeight_assigned();
			List<Workplan> workplanActivityList = workplanRepository.worplanActivityLis(employee.getEmployee_id());
//			List<Workplan> workplanActivityList = workplanRepository.worplanActivityList(
//					employee.getEmployee_id());

			model.addAttribute("employee", employee);
			model.addAttribute("performancePlanList", performancePlanObjectiveList);
			model.addAttribute("workplanActivityList", workplanActivityList);

			SubmitPerformancePlan submitPerformancePlan = submitPerformancePlanRepository.getSubmittedPlan(period,
					employee.getEmployee_id());
			if (submitPerformancePlan != null) {
				model.addAttribute("submitPerformancePlan", submitPerformancePlan);

				// Return latest comment on the page
				PerformancePlanComments lastComments = performancePlanCommentsRepository
						.getComments(submitPerformancePlan.getId());

				model.addAttribute("performancePlanComments", lastComments);
				/*if (submitPerformancePlan.getLevel() <= 1) {
					model.addAttribute("submitActionButton", true);
					model.addAttribute("supervisorComment", true);
					model.addAttribute("reviewerComment", true);
					model.addAttribute("removeObjectiveActionButton", true);
					model.addAttribute("editObjectiveActionButton", true);
					model.addAttribute("addObjectiveActionButton", true);
					model.addAttribute("supervisorPrivilegeDisable", true);

				} */
				if(submitPerformancePlan.getLevel() == 1) {
					model.addAttribute("submitActionButton", false);
					model.addAttribute("addObjectiveActionButton", false);
					model.addAttribute("removeObjectiveActionButton", false);
					model.addAttribute("editObjectiveActionButton", true);
					model.addAttribute("approveReturnActionButton", true);
					model.addAttribute("approveReturnActionButton", true);
					//model.addAttribute("approveReturnActionButton", true);
					//model.addAttribute("staffComment", true);
					//model.addAttribute("reviewerComment", true);
					//model.addAttribute("editObjectiveActionButton", true);
					model.addAttribute("supervisorPrivilege", true);
					model.addAttribute("staffPrivilegeDisable", true);
					System.out.println("####################THIS IS A SUPERVISOR LEVEL 1#################");
				}
				/*else if (submitPerformancePlan.getLevel() == 2) {

					model.addAttribute("approveReturnActionButton", true);
					model.addAttribute("staffComment", true);
					model.addAttribute("reviewerComment", true);
					model.addAttribute("editObjectiveActionButton", true);
					model.addAttribute("staffPrivilegeDisable", true);

				}*/ else {
//					model.addAttribute("postPerformancePlanSaved", true);
					model.addAttribute("supervisorComment", true);
					model.addAttribute("reviewerComment", true);
				}

			} else {

				//model.addAttribute("performancePlanComments", new PerformancePlanComments());
				model.addAttribute("submitPerformancePlan", new SubmitPerformancePlan());
				model.addAttribute("submitActionButton", true);
				model.addAttribute("supervisorComment", true);
				model.addAttribute("reviewerComment", true);
				model.addAttribute("removeObjectiveActionButton", true);
				model.addAttribute("editObjectiveActionButton", true);
				model.addAttribute("addObjectiveActionButton", true);
				model.addAttribute("supervisorPrivilegeDisable", true);

			}

			// Check if employee is a supervisor
			Employee employeeSuperviorCheck = employeeRepository.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
			if (employeeSuperviorCheck != null) {
				model.addAttribute("isSupervisor", true);
			}

			// Summing Section Two
			List<PlanCompetency> coreCompetency = planCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			model.addAttribute("coreCompetency", coreCompetency);

			// Total Value of Objective manipulation
			if (totalValueCompare > 60) {
				model.addAttribute("totalValue", totalValue);
				model.addAttribute("employee", employee);
				model.addAttribute("sectionOneError", true);

				return "performanceplanning";
			} else {

				performancePlanObjRepository.save(performancePlan);

				// Check if user ID is not equals id in form submission then redirect
				// return "redirect:/plan_performance_staff?id=" + employee.getEmployee_id() +
				// "#PerformancePlanSectionOne";

				if (submitPerformancePlan != null && submitPerformancePlan.getLevel() >= 2) {
					return "redirect:/plan_performance_staff?id=" + employee.getEmployee_id() + "#routeSectionOneHere";
				} else {
					//return "redirect:/plan_performance#routeSectionOneHere";
					return "redirect:/plan_performance_staff?id=" + employee.getEmployee_id() + "#routeSectionOneHere";
				}
			}
		} else {
			return "error";
		}
	}
	//--------------------------------------------------------------
	@RequestMapping(value = "/submitPerformancePlan", method = RequestMethod.POST)
	public String submitPerfomancePlan(
			@ModelAttribute("submitPerformancePlan") SubmitPerformancePlan submitPerformancePlan,
			@ModelAttribute("employeeIdTransfered") String employeeIdTransfered, Principal principal,
			@RequestParam(value = "param") String operation, @ModelAttribute("staffComments") String staffComment,
			@ModelAttribute("supervisorComments") String supervisorComment,
			@ModelAttribute("reviewerComments") String reviewerComment, BindingResult result, Model model) {

		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeByEmployeeId(employeeIdTransfered);

		// Check if performance plan period is open
		String period = periodRepository.getOpenModule(1902);

		if (period != null) {

			double totalValueSectionOne = 0.0;
			double totalValueSectionTwo = 0.0;
			double totalValueSectionThree = 0.0;
			boolean isValueEmpty = false;

			// Summing Section One
			List<PerformancePlanObj> performancePlanObjectiveList = (List<PerformancePlanObj>) performancePlanObjRepository
					.userPerformancePlanObj(employee.getEmployee_id(), period);
			for (PerformancePlanObj obj : performancePlanObjectiveList) {
				totalValueSectionOne = totalValueSectionOne + obj.getWeight_assigned();
			}
			//submitPerformancePlan.setTot_obj(totalValueSectionOne);
			submitPerformancePlan.setPerformace_Plan_Period(period);

			// Summing Section Two
			List<PlanCompetency> coreCompetency = planCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			for (PlanCompetency comp : coreCompetency) {
				totalValueSectionTwo = totalValueSectionTwo + Double.valueOf(comp.getActual_point());

				// Check if value is set for all Competency
				if (comp.getActual_point() <= 0) {
					isValueEmpty = true;
				}
			}

			//submitPerformancePlan.setTot_core_competences(totalValueSectionTwo);

			// Summing Section Three
			/*totalValueSectionThree = submitPerformancePlan.getSup_staff_guidiance()
					+ submitPerformancePlan.getSup_planning_organizing()
					+ submitPerformancePlan.getSup_performance_mgt() + submitPerformancePlan.getSup_staff_development()
					+ submitPerformancePlan.getSup_coaching_mentoring();
			submitPerformancePlan.setTot_sup_competences(totalValueSectionThree);
*/
			// Add submitted time
			submitPerformancePlan.setSubmitted_time(new Timestamp(System.currentTimeMillis()));
			submitPerformancePlan.setUser_id(employee.getEmployee_id());

			/*
			 * Additional Components for list items and dropdown list. To be properly
			 * commited
			 */
			double totalValue = 0.0;

			for (PerformancePlanObj obj : performancePlanObjectiveList) {
				totalValue = totalValue + obj.getWeight_assigned();
			}

			// Check if employee is a supervisor
			Employee employeeSuperviorCheck = employeeRepository.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
			boolean isSupervisor = false;
			if (employeeSuperviorCheck != null) {
				isSupervisor = true;
				model.addAttribute("isSupervisor", true);
			}

			//List<Workplan> workplanActivityList = workplanRepository.worplanActivityList(employee.getPosition_id(), employee.getEmployee_id());

			model.addAttribute("employee", employee);
			model.addAttribute("performancePlanList", performancePlanObjectiveList);
			//model.addAttribute("workplanActivityList", workplanActivityList);
			model.addAttribute("coreCompetency", coreCompetency);
			model.addAttribute("totalCompetencyValue", totalValueSectionTwo);
			model.addAttribute("totalValue", totalValueSectionOne);

			// check if plan has been saved
			SubmitPerformancePlan isPerformancePlan = submitPerformancePlanRepository.getSubmittedPlan(period,
					employee.getEmployee_id());
			int level = 0;
			if (isPerformancePlan != null) {
				PerformancePlanComments lastComments = performancePlanCommentsRepository
						.getComments(isPerformancePlan.getId());
				if (lastComments != null) {

					model.addAttribute("performancePlanComments", lastComments);
					level = isPerformancePlan.getLevel();
				} else {

					//model.addAttribute("performancePlanComments", new PerformancePlanComments());
					level = submitPerformancePlan.getLevel();
				}

				model.addAttribute("submitPerformancePlan", submitPerformancePlan);

				// Disable comment section based on level
				if (level == 1 || level == 0L) {

					/*
					 * model.addAttribute("submitActionButton", true);
					 * model.addAttribute("reviewerComment", true);
					 * model.addAttribute("supervisorComment", true);
					 * model.addAttribute("removeObjectiveActionButton", true);
					 * model.addAttribute("editObjectiveActionButton", true);
					 * model.addAttribute("addObjectiveActionButton", true);
					 */

					model.addAttribute("submitActionButton", true);
					model.addAttribute("supervisorComment", true);
					model.addAttribute("reviewerComment", true);
					model.addAttribute("removeObjectiveActionButton", true);
					model.addAttribute("editObjectiveActionButton", true);
					model.addAttribute("addObjectiveActionButton", true);
					model.addAttribute("supervisorPrivilegeDisable", true);

				} else if (level == 2) {

					model.addAttribute("approveReturnActionButton", true);
					model.addAttribute("staffComment", true);
					model.addAttribute("reviewerComment", true);
					model.addAttribute("editObjectiveActionButton", true);
					model.addAttribute("supervisorPrivilege", true);
					model.addAttribute("staffPrivilegeDisable", true);

				} else {

					model.addAttribute("staffComment", true);
					model.addAttribute("supervisorComment", true);
				}

			} else {

				//PerformancePlanComments lastComments = new PerformancePlanComments();
				//model.addAttribute("performancePlanComments", lastComments);
				model.addAttribute("submitPerformancePlan", new SubmitPerformancePlan());

				/*
				 * model.addAttribute("submitActionButton", true);
				 * model.addAttribute("reviewerComment", true);
				 * model.addAttribute("supervisorComment", true);
				 * model.addAttribute("removeObjectiveActionButton", true);
				 * model.addAttribute("editObjectiveActionButton", true);
				 */
			}

			// Validate Before Saving Plan
			if (totalValueSectionOne != 60 && level > 1) {
				model.addAttribute("sectionOneError", true);
				return "performanceplanning";

			} else if (totalValueSectionTwo != 40 && level > 1) {

				model.addAttribute("sectionTwoError", true);
				return "performanceplanning";

			} else if (totalValueSectionThree != 50 && isSupervisor == true && level > 1) {

				model.addAttribute("sectionThreeError", true);
				return "performanceplanning";

			} else if (isValueEmpty == true && level > 1) {

				model.addAttribute("sectionTwoCompetencyNotScoreError", true);
				return "performanceplanning";

			} else {

				// Check level then flag status
				if (submitPerformancePlan.getLevel() == 1 || submitPerformancePlan.getLevel() == 0) {

					submitPerformancePlan.setStatus("Submitted");
					submitPerformancePlan.setLevel(2);

				} else if (submitPerformancePlan.getLevel() == 2) {

					submitPerformancePlan.setStatus("Approved");
					submitPerformancePlan.setLevel(3);
				} else if (submitPerformancePlan.getLevel() == 3) {
					submitPerformancePlan.setStatus("Reviewed");
					submitPerformancePlan.setLevel(4);
				}

				// Check if return plan button is clicked
				if (operation.equalsIgnoreCase("Return Performance Plan")) {

					String status = null;
					submitPerformancePlan.setStatus(status);
					submitPerformancePlan.setLevel(1);
				}

				// Save Performance Plan
				SubmitPerformancePlan submitPerformancePlanSaved = submitPerformancePlanRepository
						.save(submitPerformancePlan);

				// Save Comments
				if (submitPerformancePlanSaved != null) {
					/*PerformancePlanComments performancePlanComments = new PerformancePlanComments(level, period, null, period, period);
					performancePlanComments.setStaffComments(staffComment);
					//performancePlanComments.setSupervisorComments(supervisorComment);
					//performancePlanComments.setReviewerComments(reviewerComment);
					performancePlanComments.setEmployeeId(employee.getEmployee_id());
					performancePlanComments.setDateUpdated(new Timestamp(System.currentTimeMillis()));
					performancePlanComments.setModuleOperationId(String.valueOf(submitPerformancePlanSaved.getId()));*/
					//performancePlanCommentsRepository.save(performancePlanComments);
				}
			}

			// Sed Email before form submission
			/*if (operation.equalsIgnoreCase("Submit Performance Plan")) {
				Employee supervisor = employeeRepository.getEmployeeByEmployeeId(employee.getSupervisor_id());
				if (supervisor != null) {
					SimpleMailMessage email = mailConstructor.submitEmail("http://localhost:8094/", employee,
							supervisor, period);

					try {
						mailSender.send(email);
					} catch (MailParseException mailParseException) {

						model.addAttribute("emailIssue", true);

					} catch (MailAuthenticationException mailAuthenticationException) {

						model.addAttribute("emailIssue", true);

					} catch (MailSendException MailSendException) {

						model.addAttribute("emailIssue", true);

					} catch (MailException MailException) {

						model.addAttribute("emailIssue", true);
					}

				} else {

				}

			} else if (operation.equalsIgnoreCase("Approve Performance Plan")) {

				SimpleMailMessage email = mailConstructor.approveEmail("http://localhost:8094/", employee, period);

				try {
					mailSender.send(email);
				} catch (MailParseException mailParseException) {

					model.addAttribute("emailIssue", true);

				} catch (MailAuthenticationException mailAuthenticationException) {

					model.addAttribute("emailIssue", true);

				} catch (MailSendException MailSendException) {

					model.addAttribute("emailIssue", true);

				} catch (MailException MailException) {

					model.addAttribute("emailIssue", true);
				}

			} else if (operation.equalsIgnoreCase("Return Performance Plan")) {

				SimpleMailMessage email = mailConstructor.returnEmail("http://localhost:8094/", employee, period);

				try {
					mailSender.send(email);
				} catch (MailParseException mailParseException) {

					model.addAttribute("emailIssue", true);

				} catch (MailAuthenticationException mailAuthenticationException) {

					model.addAttribute("emailIssue", true);

				} catch (MailSendException MailSendException) {

					model.addAttribute("emailIssue", true);

				} catch (MailException MailException) {

					model.addAttribute("emailIssue", true);
				}

			} else {
			}*/

			if (submitPerformancePlan != null && (submitPerformancePlan.getLevel() > 2 || submitPerformancePlan.getLevel() == 1)) {
				return "redirect:/plan_performance_staff?id=" + employee.getEmployee_id();
			} else {
				return "redirect:/plan_performance";
			}

		} else {
			return "errorPage";
		}

	}

	/*@RequestMapping(value = "/submitPerformancePlan", method = RequestMethod.POST)
	public String submitPerfomancePlan(
			@ModelAttribute("submitPerformancePlan") SubmitPerformancePlan submitPerformancePlan,
			@ModelAttribute("employeeIdTransfered") String employeeIdTransfered, Principal principal,
			@RequestParam(value = "param") String operation, @ModelAttribute("staffComments") String staffComment,
			@ModelAttribute("supervisorComments") String supervisorComment,
			@ModelAttribute("reviewerComments") String reviewerComment, BindingResult result, Model model) {

		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeByEmployeeId(employeeIdTransfered);
		System.err.println("===============================>kind of emp " + employee);
		// Check if performance plan period is open
		
		int currentYear = Year.now().getValue();
		String objectivesid = employee.getEmployee_id() + currentYear;
		
		//String period = periodRepository.getOpenModule(1902);
		String period = hrperiodrepository.checkPlanStatus(3);

		if (period.equals("open")) {

			double totalValueSectionOne = 0.0;
			double totalValueSectionTwo = 0.0;
			double totalValueSectionThree = 0.0;
			boolean isValueEmpty = false;

			// Summing Section One
			List<PerformancePlanObj> performancePlanObjectiveList = (List<PerformancePlanObj>) performancePlanObjRepository
					.userPerformancePlanObj(employee.getEmployee_id(), period);
			for (PerformancePlanObj obj : performancePlanObjectiveList) {
				totalValueSectionOne = totalValueSectionOne + obj.getWeight_assigned();
			}
			//submitPerformancePlan.setTot_obj(totalValueSectionOne);
			submitPerformancePlan.setLevel(1);
			submitPerformancePlan.setPlan_id(objectivesid);
			submitPerformancePlan.setPerformace_Plan_Period(period);

			// Summing Section Two
			List<PlanCompetency> coreCompetency = planCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			for (PlanCompetency comp : coreCompetency) {
				totalValueSectionTwo = totalValueSectionTwo + Double.valueOf(comp.getActual_point());

				// Check if value is set for all Competency
//				if (comp.getActual_point() <= 0) {
//					isValueEmpty = true;
//				}
			}

			//submitPerformancePlan.setTot_core_competences(totalValueSectionTwo);

			// Summing Section Three
			totalValueSectionThree = submitPerformancePlan.getSup_staff_guidiance()
					+ submitPerformancePlan.getSup_planning_organizing()
					+ submitPerformancePlan.getSup_performance_mgt() + submitPerformancePlan.getSup_staff_development()
					+ submitPerformancePlan.getSup_coaching_mentoring();
			submitPerformancePlan.setTot_sup_competences(totalValueSectionThree);

			// Add submitted time
			submitPerformancePlan.setSubmitted_time(new Timestamp(System.currentTimeMillis()));
			submitPerformancePlan.setUser_id(employee.getEmployee_id());

			
			 * Additional Components for list items and dropdown list. To be properly
			 * commited
			 
			double totalValue = 0.0;

			for (PerformancePlanObj obj : performancePlanObjectiveList) {
				totalValue = totalValue + obj.getWeight_assigned();
			}

			// Check if employee is a supervisor
			Employee employeeSuperviorCheck = employeeRepository.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
			System.err.println("===============================>check supvi " + employeeSuperviorCheck);
			boolean isSupervisor = false;
			if (employeeSuperviorCheck != null) {
				isSupervisor = true;
				model.addAttribute("isSupervisor", true);
			}

			List<Workplan> workplanActivityList = workplanRepository.worplanActivityLis(employee.getEmployee_id());
			
//			List<Workplan> workplanActivityList = workplanRepository.worplanActivityList(employee.getPosition_id(),
//					employee.getEmployee_id());

			model.addAttribute("employee", employee);
			model.addAttribute("performancePlanList", performancePlanObjectiveList);
			model.addAttribute("workplanActivityList", workplanActivityList);
			model.addAttribute("coreCompetency", coreCompetency);
			//model.addAttribute("totalCompetencyValue", totalValueSectionTwo);
			model.addAttribute("totalValue", totalValueSectionOne);

			// check if plan has been saved
			SubmitPerformancePlan isPerformancePlan = submitPerformancePlanRepository.getSubmittedPlan(period,
					employee.getEmployee_id());
			int level = 0;
			if (isPerformancePlan != null) {
				PerformancePlanComments lastComments = performancePlanCommentsRepository
						.getComments(isPerformancePlan.getId());
				if (lastComments != null) {

					model.addAttribute("performancePlanComments", lastComments);
					level = isPerformancePlan.getLevel();
				} else {

					model.addAttribute("performancePlanComments", new PerformancePlanComments());
					level = submitPerformancePlan.getLevel();
				}

				model.addAttribute("submitPerformancePlan", submitPerformancePlan);

				// Disable comment section based on level
				if (level == 1 || level == 0L) {

					
					 * model.addAttribute("submitActionButton", true);
					 * model.addAttribute("reviewerComment", true);
					 * model.addAttribute("supervisorComment", true);
					 * model.addAttribute("removeObjectiveActionButton", true);
					 * model.addAttribute("editObjectiveActionButton", true);
					 * model.addAttribute("addObjectiveActionButton", true);
					 

					model.addAttribute("submitActionButton", true);
					model.addAttribute("supervisorComment", true);
					model.addAttribute("reviewerComment", true);
					model.addAttribute("removeObjectiveActionButton", true);
					model.addAttribute("editObjectiveActionButton", true);
					model.addAttribute("addObjectiveActionButton", true);
					model.addAttribute("supervisorPrivilegeDisable", true);

				} else if (level == 2) {

					model.addAttribute("approveReturnActionButton", true);
					model.addAttribute("staffComment", true);
					model.addAttribute("reviewerComment", true);
					model.addAttribute("editObjectiveActionButton", true);
					model.addAttribute("supervisorPrivilege", true);
					model.addAttribute("staffPrivilegeDisable", true);

				} else {

					model.addAttribute("staffComment", true);
					model.addAttribute("supervisorComment", true);
				}

			} else {

				PerformancePlanComments lastComments = new PerformancePlanComments();
				model.addAttribute("performancePlanComments", lastComments);
				model.addAttribute("submitPerformancePlan", new SubmitPerformancePlan());

				
				 * model.addAttribute("submitActionButton", true);
				 * model.addAttribute("reviewerComment", true);
				 * model.addAttribute("supervisorComment", true);
				 * model.addAttribute("removeObjectiveActionButton", true);
				 * model.addAttribute("editObjectiveActionButton", true);
				 
			}

			// Validate Before Saving Plan
			if (totalValueSectionOne != 60 && level > 1) {
				model.addAttribute("sectionOneError", true);
				return "performanceplanning";

			} else if (totalValueSectionTwo != 40 && level > 1) {

				model.addAttribute("sectionTwoError", true);
				return "performanceplanning";

			} else if (totalValueSectionThree != 50 && isSupervisor == true && level > 1) {

				model.addAttribute("sectionThreeError", true);
				return "performanceplanning";

			} else if (isValueEmpty == true && level > 1) {

				model.addAttribute("sectionTwoCompetencyNotScoreError", true);
				return "performanceplanning";

			} else {

				// Check level then flag status
				if (submitPerformancePlan.getLevel() == 1 || submitPerformancePlan.getLevel() == 0) {

					submitPerformancePlan.setStatus("Submitted");
					submitPerformancePlan.setLevel(2);

				} else if (submitPerformancePlan.getLevel() == 2) {

					submitPerformancePlan.setStatus("Approved");
					submitPerformancePlan.setLevel(3);
				} else if (submitPerformancePlan.getLevel() == 3) {
					submitPerformancePlan.setStatus("Reviewed");
					submitPerformancePlan.setLevel(4);
				}

				// Check if return plan button is clicked
				if (operation.equalsIgnoreCase("Return Performance Plan")) {

					String status = null;
					submitPerformancePlan.setStatus(status);
					submitPerformancePlan.setLevel(1);
				}

				// Save Performance Plan
				SubmitPerformancePlan submitPerformancePlanSaved = submitPerformancePlanRepository
						.save(submitPerformancePlan);

				// Save Comments
				if (submitPerformancePlanSaved != null) {
					PerformancePlanComments performancePlanComments = new PerformancePlanComments();
					performancePlanComments.setStaffComments(staffComment);
					performancePlanComments.setSupervisorComments(supervisorComment);
					performancePlanComments.setReviewerComments(reviewerComment);
					performancePlanComments.setEmployeeId(employee.getEmployee_id());
					performancePlanComments.setDateUpdated(new Timestamp(System.currentTimeMillis()));
					performancePlanComments.setModuleOperationId(String.valueOf(submitPerformancePlanSaved.getId()));
					performancePlanCommentsRepository.save(performancePlanComments);
				}
			}
			
			if (operation.equalsIgnoreCase("Submit Performance Plan")) {
				// Send Staff Submission Email
				SimpleMailMessage staffEmail = mailConstructor.submitStaffEmail(URL,employee, period);
				
				try {
					mailSender.send(staffEmail);
					
				} catch (MailParseException mailParseException) {

					model.addAttribute("emailIssue", true);

				} catch (MailAuthenticationException mailAuthenticationException) {

					model.addAttribute("emailIssue", true);

				} catch (MailSendException MailSendException) {

					model.addAttribute("emailIssue", true);

				} catch (MailException MailException) {

					model.addAttribute("emailIssue", true);
				}

				// Send Supervisor Submission Email
				Employee supervisor = employeeRepository.getEmployeeByEmployeeId(employee.getSupervisor_id());
				if (supervisor != null) {
					SimpleMailMessage reviewerEmail = mailConstructor.submitSupervisorEmail(URL,employee, supervisor, period);
				
					try {
						mailSender.send(reviewerEmail);
						
					} catch (MailParseException mailParseException) {

						model.addAttribute("emailIssue", true);

					} catch (MailAuthenticationException mailAuthenticationException) {

						model.addAttribute("emailIssue", true);

					} catch (MailSendException MailSendException) {

						model.addAttribute("emailIssue", true);

					} catch (MailException MailException) {

						model.addAttribute("emailIssue", true);
					}

				} else {

				}
				
				// Send Reviewer Submission Email
				StaffReviewer staffReviewer = staffReviewerRepository.reviewerCheckByEmployeeId(employee.getEmployee_id());
				Employee reviewer = employeeRepository.getEmployeeByEmployeeId(staffReviewer.getReviewer_id());
				if (reviewer != null) {
					SimpleMailMessage reviewerEmail = mailConstructor.submitReviewerEmail(URL,employee, reviewer, period);
				
					try {
						mailSender.send(reviewerEmail);
						
					} catch (MailParseException mailParseException) {

						model.addAttribute("emailIssue", true);

					} catch (MailAuthenticationException mailAuthenticationException) {

						model.addAttribute("emailIssue", true);

					} catch (MailSendException MailSendException) {

						model.addAttribute("emailIssue", true);

					} catch (MailException MailException) {

						model.addAttribute("emailIssue", true);
					}

				} else {

				}


			} else if (operation.equalsIgnoreCase("Approve Performance Plan")) {
				//staff approve email
				SimpleMailMessage staffEmail = mailConstructor.approveStaffEmail(URL, employee, period);

				try {
					mailSender.send(staffEmail);
				} catch (MailParseException mailParseException) {

					model.addAttribute("emailIssue", true);

				} catch (MailAuthenticationException mailAuthenticationException) {

					model.addAttribute("emailIssue", true);

				} catch (MailSendException MailSendException) {

					model.addAttribute("emailIssue", true);

				} catch (MailException MailException) {

					model.addAttribute("emailIssue", true);
				}
				
				//supervisor approve email
				Employee supervisor = employeeRepository.getEmployeeByEmployeeId(employee.getSupervisor_id());
				if (supervisor != null) {
				SimpleMailMessage supervisoremail = mailConstructor.approveSupervisorEmail(URL, employee, supervisor, period);

				try {
					mailSender.send(supervisoremail);
				} catch (MailParseException mailParseException) {

					model.addAttribute("emailIssue", true);

				} catch (MailAuthenticationException mailAuthenticationException) {

					model.addAttribute("emailIssue", true);

				} catch (MailSendException MailSendException) {

					model.addAttribute("emailIssue", true);

				} catch (MailException MailException) {

					model.addAttribute("emailIssue", true);
				}
				}
				
				//reviewer approve email
				StaffReviewer staffReviewer = staffReviewerRepository.reviewerCheckByEmployeeId(employee.getEmployee_id());
				Employee reviewer = employeeRepository.getEmployeeByEmployeeId(staffReviewer.getReviewer_id());
				if (reviewer != null) {
				
					SimpleMailMessage reviewerEmail = mailConstructor.approveReviewerEmail(URL, employee, reviewer, period);

					try {
						mailSender.send(reviewerEmail);
					} catch (MailParseException mailParseException) {

						model.addAttribute("emailIssue", true);

					} catch (MailAuthenticationException mailAuthenticationException) {

						model.addAttribute("emailIssue", true);

					} catch (MailSendException MailSendException) {

						model.addAttribute("emailIssue", true);

					} catch (MailException MailException) {

						model.addAttribute("emailIssue", true);
					}
					}
				}


			} else if (operation.equalsIgnoreCase("Return Performance Plan")) {

				SimpleMailMessage email = mailConstructor.returnEmail(URL, employee, period);

				try {
					mailSender.send(email);
				} catch (MailParseException mailParseException) {

					model.addAttribute("emailIssue", true);

				} catch (MailAuthenticationException mailAuthenticationException) {

					model.addAttribute("emailIssue", true);

				} catch (MailSendException MailSendException) {

					model.addAttribute("emailIssue", true);

				} catch (MailException MailException) {

					model.addAttribute("emailIssue", true);
				}

			} else {
			}

			if (submitPerformancePlan != null && (submitPerformancePlan.getLevel() > 2 || submitPerformancePlan.getLevel() == 1)) {
				return "redirect:/plan_performance_staff?id=" + employee.getEmployee_id();
			} else {
				return "redirect:/plan_performance";
//				return "redirect:/plan_performance"; // orin
			}

		} */ 

	@RequestMapping(value = "/reviewPerformancePlan", method = RequestMethod.POST)
	public String reviewPerformancePlan(
			@ModelAttribute("submitPerformancePlan") SubmitPerformancePlan submitPerformancePlan,
			@ModelAttribute("employeeIdTransfered") String employeeIdTransfered, Principal principal,
			@RequestParam(value = "param") String operation, @ModelAttribute("staffComments") String staffComment,
			@ModelAttribute("supervisorComments") String supervisorComment,
			@ModelAttribute("reviewerComments") String reviewerComment, BindingResult result, Model model) {

		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeByEmployeeId(employeeIdTransfered);

		// Check if performance plan period is open
		String period = periodRepository.getOpenModule(1902);

		if (period != null) {

			double totalValueSectionOne = 0.0;
			double totalValueSectionTwo = 0.0;
			double totalValueSectionThree = 0.0;
			boolean isValueEmpty = false;

			// Summing Section One
			List<PerformancePlanObj> performancePlanObjectiveList = (List<PerformancePlanObj>) performancePlanObjRepository
					.userPerformancePlanObj(employee.getEmployee_id(), period);
			for (PerformancePlanObj obj : performancePlanObjectiveList) {
				totalValueSectionOne = totalValueSectionOne + obj.getWeight_assigned();
			}
			//submitPerformancePlan.setTot_obj(totalValueSectionOne);
			submitPerformancePlan.setPerformace_Plan_Period(period);

			// Summing Section Two
			List<PlanCompetency> coreCompetency = planCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			for (PlanCompetency comp : coreCompetency) {
				totalValueSectionTwo = totalValueSectionTwo + Double.valueOf(comp.getActual_point());

				// Check if value is set for all Competency
//				if (comp.getActual_point() <= 0) {
//					isValueEmpty = true;
//				}
			}

		/*	submitPerformancePlan.setTot_core_competences(totalValueSectionTwo);

			// Summing Section Three
			totalValueSectionThree = submitPerformancePlan.getSup_staff_guidiance()
					+ submitPerformancePlan.getSup_planning_organizing()
					+ submitPerformancePlan.getSup_performance_mgt() + submitPerformancePlan.getSup_staff_development()
					+ submitPerformancePlan.getSup_coaching_mentoring();
			submitPerformancePlan.setTot_sup_competences(totalValueSectionThree);*///kweku

			// Add submitted time
			submitPerformancePlan.setSubmitted_time(new Timestamp(System.currentTimeMillis()));
			submitPerformancePlan.setUser_id(employee.getEmployee_id());

			/*
			 * Additional Components for list items and dropdown list. To be properly
			 * commited
			 */
			double totalValue = 0.0;

			for (PerformancePlanObj obj : performancePlanObjectiveList) {
				totalValue = totalValue + obj.getWeight_assigned();
			}

			// Check if employee is a supervisor
			Employee employeeSuperviorCheck = employeeRepository.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
			boolean isSupervisor = false;
			if (employeeSuperviorCheck != null) {
				isSupervisor = true;
				model.addAttribute("isSupervisor", true);
			}
			
			List<Workplan> workplanActivityList = workplanRepository.worplanActivityLis(employee.getEmployee_id());
			
//			List<Workplan> workplanActivityList = workplanRepository.worplanActivityList(employee.getPosition_id(),
//					employee.getEmployee_id());

			model.addAttribute("employee", employee);
			model.addAttribute("performancePlanList", performancePlanObjectiveList);
			model.addAttribute("workplanActivityList", workplanActivityList);
			model.addAttribute("coreCompetency", coreCompetency);
			model.addAttribute("totalCompetencyValue", totalValueSectionTwo);
			model.addAttribute("totalValue", totalValueSectionOne);

			// check if plan has been saved
			SubmitPerformancePlan isPerformancePlan = submitPerformancePlanRepository.getSubmittedPlan(period,
					employee.getEmployee_id());
			int level = 0;

			PerformancePlanComments lastComments = performancePlanCommentsRepository
					.getComments(isPerformancePlan.getId());
			if (lastComments != null) {

				model.addAttribute("performancePlanComments", lastComments);
				level = isPerformancePlan.getLevel();
			} else {

				//model.addAttribute("performancePlanComments", new PerformancePlanComments());
				level = submitPerformancePlan.getLevel();
			}

			model.addAttribute("submitPerformancePlan", submitPerformancePlan);

			// Validate Before Saving Plan
			if (totalValueSectionOne != 60 && level > 1) {
				model.addAttribute("sectionOneError", true);
				return "performanceplanning";

			} else if (totalValueSectionTwo != 40 && level > 1) {

				model.addAttribute("sectionTwoError", true);
				return "performanceplanning";

			} /*else if (totalValueSectionThree != 50 && isSupervisor == true && level > 1) {

				model.addAttribute("sectionThreeError", true);
				return "performanceplanning";

			} else if (isValueEmpty == true && level > 1) {

				model.addAttribute("sectionTwoCompetencyNotScoreError", true);
				return "performanceplanning";

			}*/ else {

				// Check level then flag status
				if (submitPerformancePlan.getLevel() == 3) {
					submitPerformancePlan.setStatus("Reviewed");
					submitPerformancePlan.setLevel(4);
				}

				// Check if return plan button is clicked
				if (operation.equalsIgnoreCase("Return Reviewed Performance Plan")) {

					String status = null;
					submitPerformancePlan.setStatus(status);
					submitPerformancePlan.setLevel(1);
				}

				// Save Performance Plan
				SubmitPerformancePlan submitPerformancePlanSaved = submitPerformancePlanRepository
						.save(submitPerformancePlan);

				// Save Comments
				if (submitPerformancePlanSaved != null) {
					/*PerformancePlanComments performancePlanComments = new PerformancePlanComments();
					performancePlanComments.setStaffComments(staffComment);
					//performancePlanComments.setSupervisorComments(supervisorComment);
					//performancePlanComments.setReviewerComments(reviewerComment);
					performancePlanComments.setEmployeeId(employee.getEmployee_id());
					performancePlanComments.setDateUpdated(new Timestamp(System.currentTimeMillis()));
					performancePlanComments.setModuleOperationId(String.valueOf(submitPerformancePlanSaved.getId()));
					performancePlanCommentsRepository.save(performancePlanComments);*/
				}
			}

			// Send Email before form submission
			if (operation.equalsIgnoreCase("Approve Reviewed Performance Plan")) {
//				Employee supervisor = employeeRepository.getEmployeeByEmployeeId(employee.getSupervisor_id());
				if (employee != null) {
					/*SimpleMailMessage email = mailConstructor.confirmStaffEmail(URL, employee, period);

					try {
						mailSender.send(email);
					} catch (MailParseException mailParseException) {

						model.addAttribute("emailIssue", true);

					} catch (MailAuthenticationException mailAuthenticationException) {

						model.addAttribute("emailIssue", true);

					} catch (MailSendException MailSendException) {

						model.addAttribute("emailIssue", true);

					} catch (MailException MailException) {

						model.addAttribute("emailIssue", true);
					}
*/
				}
				
				//String supervisor = employee.getSupervisor_id();  
				//System.err.println("========================== sup id "+ employee.getSupervisor_id());
				//System.err.println("========================== rev id "+ reviewerCheckByEmployeeId())
				Employee supervisor = employeeRepository.getEmployeeByEmployeeId(employee.getSupervisor_id());
				//System.err.println("========================== sup id "+ employeeRepository.getEmployeeByEmployeeId(supervisor));
				if (supervisor != null) {
					/*SimpleMailMessage supervisorEmail = mailConstructor.confirmSupervisorEmail(URL, employee, supervisor, period);

					try {
						mailSender.send(supervisorEmail);
					} catch (MailParseException mailParseException) {

						model.addAttribute("emailIssue", true);

					} catch (MailAuthenticationException mailAuthenticationException) {

						model.addAttribute("emailIssue", true);

					} catch (MailSendException MailSendException) {

						model.addAttribute("emailIssue", true);

					} catch (MailException MailException) {

						model.addAttribute("emailIssue", true);
					}*/

				}
				
				
				StaffReviewer staffReviewer = staffReviewerRepository.reviewerCheckByEmployeeId(employee.getEmployee_id());
				Employee reviewer = employeeRepository.getEmployeeByEmployeeId(staffReviewer.getReviewer_id());
				if (reviewer != null) {
					/*SimpleMailMessage reviewerEmail = mailConstructor.confirmReviewerEmail(URL, employee, reviewer, period);

					try {
						mailSender.send(reviewerEmail);
					} catch (MailParseException mailParseException) {

						model.addAttribute("emailIssue", true);

					} catch (MailAuthenticationException mailAuthenticationException) {

						model.addAttribute("emailIssue", true);

					} catch (MailSendException MailSendException) {

						model.addAttribute("emailIssue", true);

					} catch (MailException MailException) {

						model.addAttribute("emailIssue", true);
					}*/

				}
				
				//Note to notify HR

			} else if (operation.equalsIgnoreCase("Return Reviewed Performance Plan")) {

				/*SimpleMailMessage email = mailConstructor.reviewRenturnedEmail("http://localhost:8094/", employee);

				try {
					mailSender.send(email);
				} catch (MailParseException mailParseException) {

					model.addAttribute("emailIssue", true);

				} catch (MailAuthenticationException mailAuthenticationException) {

					model.addAttribute("emailIssue", true);

				} catch (MailSendException MailSendException) {

					model.addAttribute("emailIssue", true);

				} catch (MailException MailException) {

					model.addAttribute("emailIssue", true);
				}*/

			} else {
				
			}

			if (submitPerformancePlan != null && submitPerformancePlan.getLevel() > 2) {
				return "redirect:/plan_performance_staff?id=" + employee.getEmployee_id();
			} else {
				return "redirect:/";
				//return "redirect:/plan_performance";
			}

		} else {
			return "errorPage";
		}

	}

	@RequestMapping("/evaluation_staff")
	public String EvaluationStaff(@RequestParam("id") String id, Model model, Principal principal) {

		// Check if performance plan period is open
		String period = periodRepository.getOpenModule(1903);
		if (period != null) {

			User user = userservice.findByUsername(principal.getName());
			Employee employee = employeeRepository.getEmployeeByEmployeeId(id);

			SubmitEvaluation submitEvaluation = submitEvaluationRepository.getSubmittedEvaluation(period,
					employee.getEmployee_id());

			if (submitEvaluation != null) {

				/*EvaluationComments lastEvaluationComments = evaluationCommentsRepository
						.getComments(submitEvaluation.getId());
				if (lastEvaluationComments != null) {

					model.addAttribute("evaluationComments", lastEvaluationComments);

				} else {

					model.addAttribute("evaluationComments", new EvaluationComments());
				}*/

				model.addAttribute("submitEvaluation", submitEvaluation);

				if (submitEvaluation.getLevel() == 2) {

					model.addAttribute("disableStaffComment", true);
//					model.addAttribute("disableSupervisorComment", true);
					model.addAttribute("disableReviewerComment", true);
					model.addAttribute("performanceEvaluationSaved", true);
					model.addAttribute("evalautionApproveButton", true);

				} else {

					model.addAttribute("disableStaffComment", true);
					model.addAttribute("disableSupervisorComment", true);
					model.addAttribute("disableReviewerComment", true);
					model.addAttribute("performanceEvaluationSaved", true);

				}
			} else {
//
//				EvaluationComments evaluationComments = new EvaluationComments();
//				
//				model.addAttribute("evaluationComments", evaluationComments);
//				model.addAttribute("submitEvaluation", new SubmitEvaluation());
//				model.addAttribute("disableStaffComment", true);
//				model.addAttribute("disableSupervisorComment", true);
//				model.addAttribute("disableReviewerComment", true);
//				model.addAttribute("performanceEvaluationSaved", true);

				return "redirect:/";
			}

			List<PerformancePlanObj> performancePlanList = (List<PerformancePlanObj>) performancePlanObjRepository
					.userPerformancePlanObj(employee.getEmployee_id(), period);

			ArrayList<String> perfPlanObj = new ArrayList<>();
			for (PerformancePlanObj perfPlanLoop : performancePlanList) {
				perfPlanObj.add(perfPlanLoop.getId() + "_" + perfPlanLoop.getPerformance_objective());
			}

			// Calculate grand Total
			List<EvaluationPlanObj> evaluationPlanObjsList = (List<EvaluationPlanObj>) evaluationPlanObjRepository
					.userEvaluationPlanObj(employee.getEmployee_id());

			double grandTotAssignWeigth = 0.00;
			double grandTot = 0.00;

			for (EvaluationPlanObj evaluationPlanObjLoop : evaluationPlanObjsList) {
				grandTotAssignWeigth += evaluationPlanObjLoop.getWeight_assigned();
				grandTot += evaluationPlanObjLoop.getTot();
			}

			// get latest period order by date
//		String currentPeriod = period;

			// Check if plan has been loaded for employee_id and the period and the position
			// otherwise load plan
			List<EvaluationCompetency> validateLoadEvaluationCompetency = evaluationCompetencyRepository
					.getCompetency(period, employee.getEmployee_id());
			// Note, for evaluation competencies lookup directly from planning_competency
			// table instead of actual lookup table but for planning competencies you must
			// lookup from lookup table.
			List<PlanCompetency> competencyLookup = planCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			if (validateLoadEvaluationCompetency.size() > 0) {

			} else {

				for (PlanCompetency comp : competencyLookup) {
					EvaluationCompetency evaluationCompetency = new EvaluationCompetency();
					evaluationCompetency.setCompetency_id((int) comp.getId());
					evaluationCompetency.setEmployee_id(employee.getEmployee_id());
					evaluationCompetency.setPeriod(period);
					evaluationCompetency.setCompetency_title(comp.getCompetency_title());
					evaluationCompetency.setPosition_id(comp.getPosition_id());
					evaluationCompetency.setRequired(Double.valueOf(comp.getRequired_point()));
					evaluationCompetency.setAssigned((Double.valueOf(comp.getActual_point())));

					evaluationCompetencyRepository.save(evaluationCompetency);
				}

			}

			List<EvaluationCompetency> evaluationCompetencies = evaluationCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			double totActualProficiency = 0.0;
			double totWeightAttained = 0.0;
			for (EvaluationCompetency comp : evaluationCompetencies) {
				totActualProficiency += comp.getActual_proficiency();
				totWeightAttained += comp.getAttained();
			}

			// Check if employee is a supervisor
			Employee employeeSuperviorCheck = employeeRepository.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
			if (employeeSuperviorCheck != null) {
				model.addAttribute("isSupervisor", true);
			}

//			SubmitEvaluation postEvaluation = new SubmitEvaluation();
//
//			SubmitEvaluation submitEvaluationExist = submitEvaluationRepository.getSubmittedEvaluation(period,
//					employee.getEmployee_id());
//			if (submitEvaluationExist != null) {
//				model.addAttribute("performanceEvaluationSaved", true);
//			}

//			model.addAttribute("postEvaluation", postEvaluation);
//			model.addAttribute("totActualProficiency", totActualProficiency);
			model.addAttribute("totWeightAttained", Math.round(totWeightAttained) * 100 / 100.0);
			model.addAttribute("evaluationCompetencies", evaluationCompetencies);
			model.addAttribute("grandTotAssignWeigth", Math.round(grandTotAssignWeigth) * 100 / 100.0);
			model.addAttribute("grandTot", Math.round(grandTot * 100) / 100.0);
			model.addAttribute("evaluationPlanObjsList", evaluationPlanObjsList);
			model.addAttribute("perfPlanObj", perfPlanObj);
			model.addAttribute("employee", employee);
			System.out.println("Size of the list " + evaluationPlanObjsList.size());

			return "evaluationform";

		} else {

			// Period is not open for planning should be in error page
			return "error";
		}

	}
	
	@RequestMapping("/supervisor_evaluation")
	public String supervisorEvaluation(@RequestParam("id") String id, Model model, Principal principal) {
		String period = periodRepository.getOpenModule(1903);
		
		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
		
		Supervisorcompetency supervisorcompetency = new Supervisorcompetency();
		
		Employee staffInfo = employeeRepository.getEmployeeByEmployeeId(id);
		Supervisorcompetency findsupervisorcompetency = supervisorcompetencyrepository.findSupervisorCompetency(id);
		//System.out.println("9999999999999999999999999999999999999999999999"+findsupervisorcompetency);
		String checkSupervisorCompetency = supervisorcompetencyrepository.checkSupervisorCompetency(id, "2023");
		System.out.println("888888888888888888888888888888888888"+checkSupervisorCompetency);
		boolean supCompetency = false;
		
		if(checkSupervisorCompetency != null) {
			supCompetency = true;
			model.addAttribute("findsupervisorcompetency", findsupervisorcompetency);
		}
		
		model.addAttribute("supCompetency", supCompetency);
		
		model.addAttribute("staffInfo", staffInfo);
		model.addAttribute("employee", employee);
		model.addAttribute("period", period);
		
		model.addAttribute("checkSupervisorCompetency", checkSupervisorCompetency);
		model.addAttribute("supervisorcompetency", supervisorcompetency);
		return "supervisorEvaluation";
	}
	
	@RequestMapping("/viewEvaluation")
	public String evaluationView(
			@RequestParam("id") String id, 
			Model model, 
			Principal principal) {
		
		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
		
		Hrperiod hrperiod = hrperiodrepository.getperiod();
		String period = hrperiod.getPlanYear();
		
		List<ObjectiveScore> submitedStaffObj = objectivescorerepository.getSubmitedPerformanceObjForStaff1( id, 3);
		Staffcompetency findstaffcompetency = staffcompetencyrepository.findStaffCompetency(employee.getEmployee_id(), submitedStaffObj.get(0).getPlanyear());
		String staffTotalCompetency = staffcompetencyrepository.staffCompetencyTotal(employee.getEmployee_id(), findstaffcompetency.getCompetencyYear(), submitedStaffObj.get(0).getPerformancePeriod());
		Employee staffInfo = employeeRepository.getEmployeeByEmployeeId(employee.getEmployee_id());
		
		System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDd"+submitedStaffObj);
		String  planTotalScore = planscorerepository.totalPlanScore(employee.getEmployee_id(), hrperiod.getPeriods(), period);
		if(planTotalScore != null) {
			model.addAttribute("planTotalScore", planTotalScore);
		}
		/*SubmitEvaluation submitEvaluation = submitEvaluationRepository.getSubmittedEvaluation("2023",
				employee.getEmployee_id());*/
		Staffcompetency checkStaffCompetency = staffcompetencyrepository.checkCompetency(employee.getEmployee_id(), submitedStaffObj.get(0).getPlanyear(), submitedStaffObj.get(0).getPerformancePeriod());
		if(checkStaffCompetency == null) {
			return "redirect:/myAppraisalEvaluation";
		}
		
		List<EmployeeComments> showcomments = employeeCommentsRepository.showEvaluationComments(checkStaffCompetency.getStaffid()+""+checkStaffCompetency.getCompetencyYear());
		
		
		model.addAttribute("showcomments", showcomments);
		model.addAttribute("findstaffcompetency", findstaffcompetency);
		model.addAttribute("staffTotalCompetency", staffTotalCompetency);
		model.addAttribute("submitedStaffObj", submitedStaffObj);
		model.addAttribute("staffInfo", staffInfo);
		model.addAttribute("stateperiod", hrperiod.getDescription());
		//model.addAttribute("submitEvaluation", submitEvaluation);
		model.addAttribute("employee", employee);
		
		if(checkStaffCompetency.getLevel() == 1) {
			model.addAttribute("evalautionApproveButton", true);
			model.addAttribute("evalautionReturnButton", false);
			model.addAttribute("isActionTaken", true);
		}
		return "viewEvaluation";
	}
	
	
	@RequestMapping("/evaluation")
	public String Evaluation(
			@RequestParam("id") String id, 
			Model model, 
			Principal principal) {
		Hrperiod hrperiod = hrperiodrepository.getperiod();
		String period = hrperiod.getPlanYear();
		int performanceperiodid = (int)hrperiod.getId();
		
		Hrperiod evaluationperiod = hrperiodrepository.planPeriodCheck(performanceperiodid);
		if(evaluationperiod.getId() == 3) {
				return "redirect:/home";
		}
		model.addAttribute("performanceperiod", hrperiod.getPeriods());
		
		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
		
		Staffcompetency findstaffcompetency = staffcompetencyrepository.findStaffCompetency(id, period);
		System.out.println("%%%%%%%%%%%%%%%%%%%%%findstaffcompetency%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+findstaffcompetency);
		
		Supervisorcompetency findsupervisorcompetency = supervisorcompetencyrepository.findSupervisorCompetency(employee.getEmployee_id());
		System.out.println("###################login person###############################"+employee.getEmployee_id());
		
		String staffTotalCompetency = staffcompetencyrepository.staffCompetencyTotal(id, period, hrperiod.getPeriods());
		System.out.println("====================staffTotalCompetency==========================="+staffTotalCompetency);
		
		Employee staffInfo = employeeRepository.getEmployeeByEmployeeId(id);
		String sup = staffInfo.getSupervisor_id();
		String sup1 = employee.getEmployee_id();
		System.out.println("++++++++++++++++++sup+++++++++sup1++++++++++++++++++++"+sup1+"="+sup);
		
		String staffid = id;
		System.out.println("%%%%%%%%%%%%%%%%%%%%staffid%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+staffid);
		
		// check if staff have competency for the open period
		Staffcompetency checkStaffCompetency = staffcompetencyrepository.checkCompetency(id, period, hrperiod.getPeriods());
		//check if supervisor have competency for that perion
		String checkSupervisorCompetency = supervisorcompetencyrepository.checkSupervisorCompetency(employee.getEmployee_id(), period);
		System.out.println("=========checkSupervisorCompetency====================" + checkSupervisorCompetency);
		
		List<ObjectiveScore> submitedStaffObj = objectivescorerepository.getSubmitedPerformanceObjForStaff(period, id, 3);
		for(ObjectiveScore obj : submitedStaffObj) {
			//if(obj.getStaffActionTaken())
			System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ " + submitedStaffObj.get(0).getActual());
		}	
		ObjectiveScore getplanid = submitedStaffObj.get(0);
		
		//get total
		String  planTotalScore = planscorerepository.totalPlanScore(staffid, hrperiod.getPeriods(), period);
		System.out.println("********************************)" + planTotalScore);
		if(planTotalScore != null) {
			model.addAttribute("planTotalScore", planTotalScore);
		}
		
		List<EmployeeComments> showcomments = employeeCommentsRepository.showEvaluationComments(getplanid.getPlanid());
		model.addAttribute("showcomments", showcomments);
		
		boolean checkcompetency = false;
		if(checkStaffCompetency != null) {
			checkcompetency = true;
			 
				// Check if evaluation plan period is open
			 model.addAttribute("findstaffcompetency", findstaffcompetency);
			 model.addAttribute("staffTotalCompetency", staffTotalCompetency);
			
			
			if(checkStaffCompetency.getLevel() == 0){
				model.addAttribute("addScore", true);
			}else {
				model.addAttribute("addScore", false);
			}
		}else {
			model.addAttribute("addScore", true);
		}
		
		if(sup.equals(sup1)) {
			model.addAttribute("superv", true);
			System.out.println("=============================== i am the staff supervisor " + sup);
		}else {
			model.addAttribute("superv", false);
			System.out.println("=============================== i am the staff herself " + staffInfo);
		}
		
		
	
		
		// SUPERVISOR
		
		boolean checkSupervisor = false;
		if(checkSupervisorCompetency != null) {
			 
			checkSupervisor = true;
			 
				// Check if evaluation plan period is open
			 model.addAttribute("findsupervisorcompetency", findsupervisorcompetency);
			
		}
		
		
		model.addAttribute("submitedStaffObj", submitedStaffObj);
		model.addAttribute("checkcompetency", checkcompetency);
		model.addAttribute("checkSupervisor", checkSupervisor);
		model.addAttribute("staffid", staffid);  
		model.addAttribute("stateperiod", hrperiod.getDescription());
		
		if (period != null) {
			
			Staffcompetency staffcompetency = new Staffcompetency();
			Supervisorcompetency supervisorcompetency = new Supervisorcompetency();
			model.addAttribute("supervisorcompetency", supervisorcompetency);
			model.addAttribute("staffcompetency", staffcompetency);
			
			model.addAttribute("period", period);
			
			
			

			/*User user = userservice.findByUsername(principal.getName());
			Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());*/

			Employee supervisor = employeeRepository.findSupervisorDetail(id);
			// check if supervisor
			model.addAttribute("supervisor", supervisor);
			Staffcompetency submitEvaluation = staffcompetencyrepository.getSubmittedEvaluation(period,
					id);
			if (submitEvaluation != null) {
				

				model.addAttribute("submitEvaluation", submitEvaluation);

				if (submitEvaluation.getLevel() == 0) {

					model.addAttribute("disableSupervisorComment", true);
					model.addAttribute("disableReviewerComment", true);
					model.addAttribute("evaluationSubmitButton", true);

				} else {

					model.addAttribute("disableStaffComment", true);
					model.addAttribute("disableSupervisorComment", true);
					model.addAttribute("disableReviewerComment", true);
					model.addAttribute("performanceEvaluationSaved", true);
					model.addAttribute("evaluationSubmitButton", false);

				}
			} else {

				EvaluationComments evaluationComments = new EvaluationComments();
				model.addAttribute("evaluationComments", evaluationComments);
				model.addAttribute("submitEvaluation", new SubmitEvaluation());
				model.addAttribute("disableSupervisorComment", true);
				model.addAttribute("disableReviewerComment", true);
				model.addAttribute("evaluationSubmitButton", true);
			}

			List<PerformancePlanObj> performancePlanList = (List<PerformancePlanObj>) performancePlanObjRepository
					.userPerformancePlanObj(employee.getEmployee_id(), period);

			ArrayList<String> perfPlanObj = new ArrayList<>();
			for (PerformancePlanObj perfPlanLoop : performancePlanList) {
				perfPlanObj.add(perfPlanLoop.getId() + "_" + perfPlanLoop.getPerformance_objective());
			}

			// Calculate grand Total
			List<EvaluationPlanObj> evaluationPlanObjsList = (List<EvaluationPlanObj>) evaluationPlanObjRepository
					.userEvaluationPlanObj(employee.getEmployee_id());

			double grandTotAssignWeigth = 0.00;
			double grandTot = 0.00;

			for (EvaluationPlanObj evaluationPlanObjLoop : evaluationPlanObjsList) {
				grandTotAssignWeigth += evaluationPlanObjLoop.getWeight_assigned();
				grandTot += evaluationPlanObjLoop.getTot();
			}

			// get latest period order by date
//		String currentPeriod = period;

			// Check if plan has been loaded for employee_id and the period and the position
			// otherwise load plan
			List<EvaluationCompetency> validateLoadEvaluationCompetency = evaluationCompetencyRepository
					.getCompetency(period, employee.getEmployee_id());
			// Note, for evaluation competencies lookup directly from planning_competency
			// table instead of actual lookup table but for planning competencies you must
			// lookup from lookup table.
			List<PlanCompetency> competencyLookup = planCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			if (validateLoadEvaluationCompetency.size() > 0) {

			} else {

				for (PlanCompetency comp : competencyLookup) {
					EvaluationCompetency evaluationCompetency = new EvaluationCompetency();
					evaluationCompetency.setCompetency_id((int) comp.getId());
					evaluationCompetency.setEmployee_id(employee.getEmployee_id());
					evaluationCompetency.setPeriod("2023");
					evaluationCompetency.setCompetency_title(comp.getCompetency_title());
					evaluationCompetency.setPosition_id(comp.getPosition_id());
					evaluationCompetency.setRequired(Double.valueOf(comp.getRequired_point()));
					evaluationCompetency.setAssigned((Double.valueOf(comp.getActual_point())));

					evaluationCompetencyRepository.save(evaluationCompetency);
				}

			}

			List<EvaluationCompetency> evaluationCompetencies = evaluationCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			double totActualProficiency = 0.0;
			double totWeightAttained = 0.0;
			for (EvaluationCompetency comp : evaluationCompetencies) {
				totActualProficiency += comp.getActual_proficiency();
				totWeightAttained += comp.getAttained();
			}

			// Check if employee is a supervisor
			Employee employeeSuperviorCheck = employeeRepository.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
			if (employeeSuperviorCheck != null) {
				model.addAttribute("isSupervisor", true);
			}
			
			model.addAttribute("totWeightAttained", Math.round(totWeightAttained) * 100 / 100.0);
			model.addAttribute("evaluationCompetencies", evaluationCompetencies);
			model.addAttribute("grandTotAssignWeigth", Math.round(grandTotAssignWeigth) * 100 / 100.0);
			model.addAttribute("grandTot", Math.round(grandTot * 100) / 100.0);
			model.addAttribute("evaluationPlanObjsList", evaluationPlanObjsList);
			model.addAttribute("perfPlanObj", perfPlanObj);
			model.addAttribute("employee", employee);
			model.addAttribute("staffInfo", staffInfo);
			//System.out.println("Size of the list " + evaluationPlanObjsList.size());
			return "evaluationform";

		} else {

			// Period is not open for planning should be in error page
			return "error";
		}

	}

	@RequestMapping(value = "/addActivityEvaluation", method = RequestMethod.POST)
	public String addEvaluationObjective(@ModelAttribute("evaluationPlanObj") EvaluationPlanObj evaluationPlanObj,
			Model model, Principal principal) {

		// Check if performance plan period is open
		String period = periodRepository.getOpenModule(1903);
		if (period != null) {

			User user = userservice.findByUsername(principal.getName());
			Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
			SubmitPerformancePlan submitPerformancePlan = submitPerformancePlanRepository.getSubmittedPlan(period,
					employee.getEmployee_id());
			if (submitPerformancePlan != null) {
				model.addAttribute("submitPerformancePlan", submitPerformancePlan);
			} else {
				model.addAttribute("submitPerformancePlan", new SubmitPerformancePlan());
			}

			evaluationPlanObj.setUser_id(employee.getEmployee_id());

			// Check which quarter Selected and assign score to (Actual in
			// percentage/Target)
			if (evaluationPlanObj.getAppraisal_quarter().equalsIgnoreCase(String.valueOf(1))) {
				evaluationPlanObj.setQuarter2(0);
				evaluationPlanObj.setQuarter3(0);
				evaluationPlanObj.setQuarter4(0);
				evaluationPlanObj
						.setScore(evaluationPlanObj.getActual_in_percentage() / evaluationPlanObj.getQuarter1());

			} else if (evaluationPlanObj.getAppraisal_quarter().equalsIgnoreCase(String.valueOf(2))) {
				evaluationPlanObj.setQuarter3(0);
				evaluationPlanObj.setQuarter4(0);
				evaluationPlanObj
						.setScore(evaluationPlanObj.getActual_in_percentage() / evaluationPlanObj.getQuarter2());
			} else if (evaluationPlanObj.getAppraisal_quarter().equalsIgnoreCase(String.valueOf(3))) {

				evaluationPlanObj.setQuarter4(0);
				evaluationPlanObj
						.setScore(evaluationPlanObj.getActual_in_percentage() / evaluationPlanObj.getQuarter3());
			} else {

				evaluationPlanObj
						.setScore(evaluationPlanObj.getActual_in_percentage() / evaluationPlanObj.getQuarter4());
			}

			// Assign Total to (Score / Weight Assigned) and Save Evaluation
			evaluationPlanObj.setTot(
					Math.round((evaluationPlanObj.getScore() * evaluationPlanObj.getWeight_assigned()) * 100) / 100);

			// List of Performance Objectives for
			List<PerformancePlanObj> performancePlanList = (List<PerformancePlanObj>) performancePlanObjRepository
					.findAll();
			ArrayList<String> perfPlanObj = new ArrayList<>();
			for (PerformancePlanObj perfPlanLoop : performancePlanList) {
				perfPlanObj.add(perfPlanLoop.getId() + "_" + perfPlanLoop.getPerformance_objective());
			}

			// Calculate grand Total
			List<EvaluationPlanObj> evaluationPlanObjsListForGrandTotal = (List<EvaluationPlanObj>) evaluationPlanObjRepository
					.userEvaluationPlanObj(employee.getEmployee_id());

			double grandTot = 0.00;
			for (EvaluationPlanObj evaluationPlanObjLoop : evaluationPlanObjsListForGrandTotal) {
				grandTot += evaluationPlanObjLoop.getTot();
			}

			model.addAttribute("grandTot", Math.round(grandTot * 100) / 100.0);
			model.addAttribute("perfPlanObj", perfPlanObj);

			// Validation to ensure that Grand Total is not above 60
			EvaluationPlanObj postAnEvaluation = null;
			if ((grandTot + evaluationPlanObj.getTot()) < 60) {
				postAnEvaluation = evaluationPlanObjRepository.save(evaluationPlanObj);
			} else {

				// Get evaluationPlanObjsList after on error page
				List<EvaluationPlanObj> evaluationPlanObjsList = (List<EvaluationPlanObj>) evaluationPlanObjRepository
						.userEvaluationPlanObj(employee.getEmployee_id());
				// loading section two data and section two total
				List<EvaluationCompetency> evaluationCompetencies = evaluationCompetencyRepository.getCompetency(period,
						employee.getEmployee_id());
				double totWeightAttained = 0.0;
				for (EvaluationCompetency comp : evaluationCompetencies) {
					totWeightAttained += comp.getAttained();
				}

				model.addAttribute("totWeightAttained", totWeightAttained);
				model.addAttribute("evaluationPlanObjsList", evaluationPlanObjsList);
				model.addAttribute("evaluationCompetencies", evaluationCompetencies);
				model.addAttribute("employee", employee);
				model.addAttribute("sectionOneError", true);

				SubmitEvaluation submitEvaluation = submitEvaluationRepository.getSubmittedEvaluation(period,
						employee.getEmployee_id());
				if (submitEvaluation != null) {

					/*EvaluationComments lastEvaluationComments = evaluationCommentsRepository
							.getComments(submitEvaluation.getId());
					if (lastEvaluationComments != null) {

						model.addAttribute("evaluationComments", lastEvaluationComments);

					} else {

						model.addAttribute("evaluationComments", new EvaluationComments());
					}*/

					model.addAttribute("submitEvaluation", submitEvaluation);

					if (submitEvaluation.getLevel() == 1 || submitEvaluation.getLevel() == 0L) {

						model.addAttribute("disableSupervisorComment", true);
						model.addAttribute("disableReviewerComment", true);
						model.addAttribute("evaluationSubmitButton", true);

					} else {

						model.addAttribute("disableStaffComment", true);
						model.addAttribute("disableSupervisorComment", true);
						model.addAttribute("disableReviewerComment", true);
						model.addAttribute("performanceEvaluationSaved", true);

					}
				} else {

					EvaluationComments evaluationComments = new EvaluationComments();
					model.addAttribute("evaluationComments", evaluationComments);
					model.addAttribute("submitEvaluation", new SubmitEvaluation());
					model.addAttribute("disableSupervisorComment", true);
					model.addAttribute("disableReviewerComment", true);
					model.addAttribute("evaluationSubmitButton", true);
				}

				Employee employeeSuperviorCheck = employeeRepository
						.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
				if (employeeSuperviorCheck != null) {
					model.addAttribute("isSupervisor", true);
				}

				return "evaluationform";
			}

			if (postAnEvaluation != null) {
				model.addAttribute("submitPerformancePlanSaved", true);
				model.addAttribute("postAnEvaluation", true);
			}

			return "redirect:/evaluation";

		} else {

			// Period is not open for planning should be in error page
			return "error";
		}

	}

	@RequestMapping(value = "/getObjQuarters", method = RequestMethod.GET)
	@ResponseBody
	public PerformancePlanObj getObjQuarters(@RequestParam("objId") String objId, Model model) {
		int iend = objId.indexOf("_");
		System.out.println("iend ====> " + iend);
		String subString = null;
		if (iend != -1) {
			subString = objId.substring(0, iend);
		}
		System.out.println("subString ====> " + subString);

		return performancePlanObjRepository.findOne(Long.parseLong(subString));
	}

	@RequestMapping(value = "/getWorkPlanActivitiesIndicator", method = RequestMethod.GET)
	@ResponseBody
	public Workplan getWorkPlanActivitiesIndicator(@RequestParam("objId") String objId, Model model) {

		return workplanRepository.findOne(Long.valueOf(objId));
	}

	@RequestMapping(value = "/editPerformancePlanObjective", method = RequestMethod.GET)
	@ResponseBody
	public PerformancePlanObj editPerformancePlanObjective(@RequestParam("objId") String objId, Model model) {

		return performancePlanObjRepository.findOne(Long.parseLong(objId));
	}

	// Also used for modifying Items. It gives JSON of items clicked.
	@RequestMapping(value = "/scorePlannedCompetency", method = RequestMethod.GET)
	@ResponseBody
	public PlanCompetency getPlanCompetencyById(Long Id, Model model) {
		return planCompetencyRepository.findOne(Id);
	}

	// Also used for modifying Items. It gives JSON of items clicked.
	@RequestMapping(value = "/scoreEvaluationCompetency", method = RequestMethod.GET)
	@ResponseBody
	public EvaluationCompetency getEvaluationCompetencyById(Long Id, Model model) {
		return evaluationCompetencyRepository.findOne(Id);
	}

	@RequestMapping(value = "/submitEvaluation", method = RequestMethod.POST)
	public String savePerformanceEvaluation(@ModelAttribute("submitEvaluation") SubmitEvaluation submitEvaluation,
			@ModelAttribute("staffComments") String staffComment,
			@ModelAttribute("supervisorComments") String supervisorComment,
			@ModelAttribute("reviewerComments") String reviewerComment,
			@ModelAttribute("employeeIdTransfered") String employee_id,
			@ModelAttribute(value = "operation") String operation, Model model, Principal principal) {

		String period = periodRepository.getOpenModule(1903);
		if (period != null) {

			User user = userservice.findByUsername(principal.getName());
			Employee employee = employeeRepository.getEmployeeByEmployeeId(employee_id);

			model.addAttribute("employee", employee);

			// **************************** Check if User Already Submit Performance Plan
			// based on User_Id and Performance_Period ****************************

			double totalValueSectionOne = 0.0;
			double totalValueSectionTwo = 0.0;
			double totalValueSectionThree = 0.0;

			// Summing Section One
			List<EvaluationPlanObj> evaluationPlanObjsList = (List<EvaluationPlanObj>) evaluationPlanObjRepository
					.userEvaluationPlanObj(employee.getEmployee_id());
			model.addAttribute("evaluationPlanObjsList", evaluationPlanObjsList);
			for (EvaluationPlanObj obj : evaluationPlanObjsList) {
				totalValueSectionOne = totalValueSectionOne + obj.getTot();
			}

			submitEvaluation.setTot_obj(totalValueSectionOne);

			// Summing Section Two
			List<EvaluationCompetency> evaluationCompetencies = evaluationCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			double totActualProficienc = 0.0;
//			double totWeightAttaine = 0.0;
			for (EvaluationCompetency comp : evaluationCompetencies) {
				totalValueSectionTwo += comp.getAttained();
//			totWeightAttained += comp.getAttained();
			}

			submitEvaluation.setTot_core_competences(totalValueSectionTwo);
			submitEvaluation.setEvaluation_Period(period);

//			model.addAttribute("totWeightAttained", totWeightAttained);
			model.addAttribute("evaluationCompetencies", evaluationCompetencies);

			// Summing Section Three
			totalValueSectionThree = submitEvaluation.getSup_staff_guidiance()
					+ submitEvaluation.getSup_planning_organizing() + submitEvaluation.getSup_performance_mgt()
					+ submitEvaluation.getSup_staff_development() + submitEvaluation.getSup_coaching_mentoring();
			submitEvaluation.setTot_sup_competences(totalValueSectionThree);

			// Add submitted time
			submitEvaluation.setSubmitted_time(new Timestamp(System.currentTimeMillis()));
			submitEvaluation.setUser_id(employee.getEmployee_id());

			// Check if employee is a supervisor
			Employee employeeSuperviorCheck = employeeRepository.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
			boolean isSupervisor = false;
			if (employeeSuperviorCheck != null) {
				isSupervisor = true;
				model.addAttribute("isSupervisor", true);
			} else {

				submitEvaluation.setFinal_appraisal_score(
						Math.round((totalValueSectionOne + totalValueSectionTwo) * 100) / 100);
				submitEvaluation.setTotal_scores(Math.round((totalValueSectionOne + totalValueSectionTwo) * 100) / 100);

			}

			// ****************************NOTE To Add (User_Id &
			// Parformance_Planning_Period from DropDownList******************************

			// Validate Before Saving Plan

			model.addAttribute("totalValue", totalValueSectionOne);
			model.addAttribute("totWeightAttained", totalValueSectionTwo);
			if (totalValueSectionOne > 60) {
				model.addAttribute("evaluationPlanObjsList", evaluationPlanObjsList);
				model.addAttribute("sectionOneError", true);
				return "performanceplanning";

			} else if (totalValueSectionTwo > 40) {
				model.addAttribute("evaluationPlanObjsList", evaluationPlanObjsList);
				model.addAttribute("sectionTwoError", true);
				return "performanceplanning";

			} else if (totalValueSectionThree > 50 && isSupervisor == true) {
				model.addAttribute("evaluationPlanObjsList", evaluationPlanObjsList);
				model.addAttribute("sectionThreeError", true);
				return "performanceplanning";

			} else {

				// Check level then flag status
				if (submitEvaluation.getLevel() == 1 || submitEvaluation.getLevel() == 0) {

					submitEvaluation.setStatus("Submitted");
					submitEvaluation.setLevel(2);
				} else if (submitEvaluation.getLevel() == 2) {
					submitEvaluation.setLevel(3);
					submitEvaluation.setStatus("Approved");
				} else if (submitEvaluation.getLevel() == 3) {
					submitEvaluation.setLevel(4);
					submitEvaluation.setStatus("Reviewed");
				}

				// Check if return plan button is clicked
				if (operation.equalsIgnoreCase("Return Evaluation Plan")) {

					String status = null;
					submitEvaluation.setStatus(status);
					submitEvaluation.setLevel(1);
				}

				// Save Performance Plan
				SubmitEvaluation submitPerformancePlanSaved = submitEvaluationRepository.save(submitEvaluation);
//				submitEvaluationRepository.approveEvaluation(submitEvaluation.getId());

				// Log Comments
				if (submitPerformancePlanSaved != null) {

					/*EvaluationComments newEvaluationComments = new EvaluationComments();
					newEvaluationComments.setStaffComments(staffComment);
					newEvaluationComments.setSupervisorComments(supervisorComment);
					newEvaluationComments.setReviewComments(reviewerComment);
					newEvaluationComments.setDateUpdated(new Timestamp(System.currentTimeMillis()));
					newEvaluationComments.setEmployeeId(employee.getEmployee_id());
					newEvaluationComments.setModuleOperationId(String.valueOf(submitEvaluation.getId()));
					evaluationCommentsRepository.save(newEvaluationComments);*/

					/*EvaluationComments lastEvaluationComments = evaluationCommentsRepository
							.getComments(submitEvaluation.getId());
					if (lastEvaluationComments != null) {
						model.addAttribute("evaluationComments", lastEvaluationComments);
					} else {

						EvaluationComments evaluationComments = new EvaluationComments();
						model.addAttribute("evaluationComments", evaluationComments);
					}*/
					model.addAttribute("performanceEvaluationSaved", true);
				}
			}

			System.out.println("operation ===================================>" + operation);
			if (operation.equalsIgnoreCase("Submit Evaluation Plan")) {
				return "redirect:/evaluation";
			} else {
				return "redirect:/evaluation_staff?id=" + employee.getEmployee_id();
			}

		} else {

			// Period is not open for planning should be in error page
			return "error";
		}
	}

////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////

	// Method to be used
	@RequestMapping("/plan_performance1")
	public String perfplanning1(Principal principal, Model model) {

		// Check if performance plan period is open
		String period = periodRepository.getOpenModule(1902);
		if (period != null) {

			User user = userservice.findByUsername(principal.getName());
			Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
			SubmitPerformancePlan submitPerformancePlan = submitPerformancePlanRepository.getSubmittedPlan(period,
					employee.getEmployee_id());
			if (submitPerformancePlan != null) {
				model.addAttribute("submitPerformancePlan", submitPerformancePlan);
			} else {
				model.addAttribute("submitPerformancePlan", new SubmitPerformancePlan());
			}

			double totalValue = 0.0;
			List<PerformancePlanObj> performancePlanList = (List<PerformancePlanObj>) performancePlanObjRepository
					.userPerformancePlanObj(employee.getEmployee_id(), period);
			for (PerformancePlanObj obj : performancePlanList) {
				totalValue = totalValue + obj.getWeight_assigned();
			}

			List<LookupCompetency> competencyLookup = lookupCompetencyRepository
					.getCompetency(employee.getPosition_id());
			
			List<Workplan> workplanActivityList = workplanRepository.worplanActivityLis(employee.getEmployee_id());

//			List<Workplan> workplanActivityList = workplanRepository.worplanActivityList(employee.getPosition_id(),
//					employee.getEmployee_id());

			// get latest period order by date
			String performance_period = period;

			// Check if plan has been loaded for employee_id and the period and the position
			// otherwise load plan

			List<PlanCompetency> validateLoadCompetency = planCompetencyRepository.getCompetency(performance_period,
					employee.getEmployee_id());
			if (validateLoadCompetency.size() > 0) {

			} else {

				for (LookupCompetency comp : competencyLookup) {
					PlanCompetency planCompetency = new PlanCompetency();
					planCompetency.setCompetency_id((int) comp.getId());
					planCompetency.setEmployee_id(employee.getEmployee_id());
					planCompetency.setPeriod(performance_period);
					planCompetency.setCompetency_title(comp.getCompetency_title());
					planCompetency.setPosition_id(comp.getPosition_id());
					planCompetency.setRequired_point(comp.getRequired_point());

					planCompetencyRepository.save(planCompetency);
				}

			}

			// Validation Check if score is > 40 else show error page
			double totalCompetencyValue = 0.0;
			List<PlanCompetency> coreCompetency = planCompetencyRepository.getCompetency(performance_period,
					employee.getEmployee_id());
			for (PlanCompetency comp : coreCompetency) {
				totalCompetencyValue = totalCompetencyValue + Double.valueOf(comp.getActual_point());
			}
			PlanCompetency planCompetency = new PlanCompetency();

			model.addAttribute("planCompetency", planCompetency);
			model.addAttribute("coreCompetency", coreCompetency);
			model.addAttribute("performancePlanList", performancePlanList);
			model.addAttribute("workplanActivityList", workplanActivityList);
			model.addAttribute("employee", employee);
			model.addAttribute("totalValue", totalValue);
			model.addAttribute("totalCompetencyValue", totalCompetencyValue);
			model.addAttribute("period", period);

			return "viewplanning";

		} else {

			// Period is not open for planning should be in error page
			return "error";
		}
	}
	/////////////////////////////////////  staffApproveEvaluation  /staffApproveEvaluation
	
	@RequestMapping(value = "/staffApproveEvaluation", method = RequestMethod.POST)
	public String evaluationApprove(
			@ModelAttribute("competencyId") Long competencyId, 
			@ModelAttribute("operation") String operation,
			@ModelAttribute("staffComment") String staffComment,
			Principal principal, 
			Model model) {
		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
		Hrperiod hrperiod = hrperiodrepository.getperiod();
		String period = hrperiod.getPlanYear();
		
		Staffcompetency staffCompetencies = staffcompetencyrepository.getStaffCompetencyById(competencyId);
		
		Employee staffInfo = employeeRepository.getEmployeeByEmployeeId(staffCompetencies.getStaffid());
		Employee supervisorInfo = employeeRepository.getEmployeeByEmployeeId(staffInfo.getSupervisor_id());
		Employee reviewerInfo = employeeRepository.getEmployeeByEmployeeId(supervisorInfo.getSupervisor_id());
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^staff " + staffInfo.getEmployee_id());
		System.out.println("^^^^^^^^^^^^^^^^^^^^^supervisor " + supervisorInfo.getEmployee_id());
		System.out.println("^^^^^^^^^^^^^^^^^^^^^reviewer	 " + reviewerInfo.getEmployee_id());
		
		int getLevel;
		
		  if(operation.equals("Approve Evaluation")) {
			 getLevel = staffCompetencies.getLevel() + 1;
			staffcompetencyrepository.staffEvaluationUpdatLevel(getLevel, competencyId);
			
			if(employee.getEmployee_id().equals(staffInfo.getEmployee_id())) {
				
				
			
		////////reviewer mail
						
						SimpleMailMessage email3 = mailConstructor.approveEvaluationReviewerEmail(reviewerInfo, period, staffInfo, hrperiod.getPeriods());

						try {
							mailSender.send(email3);
						} 
						catch (MailParseException mailParseException) {
							model.addAttribute("emailIssue", true);
						} 
						catch (MailAuthenticationException mailAuthenticationException) {
							model.addAttribute("emailIssue", true);
						} 
						catch (MailSendException MailSendException) {
							model.addAttribute("emailIssue", true);
						} catch (MailException MailException) {
							model.addAttribute("emailIssue", true);
						}
			}else if(employee.getEmployee_id().equals(reviewerInfo.getEmployee_id())){
				////**** REVIWER EVALUATION ****////
				//staff mail
				SimpleMailMessage email = mailConstructor.reviewEvaluationStaffEmail(staffInfo, period, hrperiod.getPeriods());
				try {
					mailSender.send(email);
				} 
				catch (MailParseException mailParseException) {
					model.addAttribute("emailIssue", true);
				} 
				catch (MailAuthenticationException mailAuthenticationException) {
					model.addAttribute("emailIssue", true);
				} 
				catch (MailSendException MailSendException) {
					model.addAttribute("emailIssue", true);
				}
				catch (MailException MailException) {
					model.addAttribute("emailIssue", true);
				}
				
			}
								////****THE RETURN MAILS***///
		} else if(operation.equals("Return Evaluation")) {
			// getLevel = staffCompetencies.getLevel() - 1;
			staffcompetencyrepository.staffEvaluationUpdatLevel(0, competencyId);
			//*** STAFF RETURN EMAIL  **//
			
			SimpleMailMessage email1 = mailConstructor.staffReturnEmail(staffInfo, period, hrperiod.getPeriods());

			try {
				mailSender.send(email1);
			} 
			catch (MailParseException mailParseException) {
				model.addAttribute("emailIssue", true);
			} 
			catch (MailAuthenticationException mailAuthenticationException) {
				model.addAttribute("emailIssue", true);
			} 
			catch (MailSendException MailSendException) {
				model.addAttribute("emailIssue", true);
			} catch (MailException MailException) {
				model.addAttribute("emailIssue", true);
			}
			
//*** supervisor RETURN EMAIL  **//
			
			SimpleMailMessage email2 = mailConstructor.supervisorReturnEmail(supervisorInfo, period, staffInfo, hrperiod.getPeriods());

			try {
				mailSender.send(email2);
			} 
			catch (MailParseException mailParseException) {
				model.addAttribute("emailIssue", true);
			} 
			catch (MailAuthenticationException mailAuthenticationException) {
				model.addAttribute("emailIssue", true);
			} 
			catch (MailSendException MailSendException) {
				model.addAttribute("emailIssue", true);
			} catch (MailException MailException) {
				model.addAttribute("emailIssue", true);
			}
			
		}

		  EvaluationComments staffcomment = new EvaluationComments();
			staffcomment.setDateUpdated(new Timestamp(System.currentTimeMillis()));
			staffcomment.setEmployeeId(employee.getEmployee_id());
			staffcomment.setStaffComments(staffComment);
			staffcomment.setModuleOperationId(staffCompetencies.getStaffid()+""+staffCompetencies.getCompetencyYear());
			evaluationCommentsRepository.save(staffcomment);
		
			
		  if(staffCompetencies.getLevel() > 1){
				//int getLevel = staffCompetencies.getLevel() + 1;
				//staffcompetencyrepository.staffEvaluationUpdatLevel(getLevel, competencyId);
				return "redirect:/review_evaluation_staff?id="+staffCompetencies.getStaffid();
			}
		
		return "redirect:viewEvaluation?id="+staffCompetencies.getStaffid()+staffCompetencies.getCompetencyYear();
	}
	
	///////////////////////////////////////////////////
	
	
	@RequestMapping(value = "/submitCompetency", method = RequestMethod.POST)
	public String saveCompetency(
			@RequestParam("staffid") String staffid,
			@ModelAttribute("staffcompetency") Staffcompetency staffcompetency,
			@ModelAttribute("operation") String operation,
			@ModelAttribute("staffComments") String staffComments,
			@ModelAttribute("planid") String planid,
			@ModelAttribute("evaluationComments") EvaluationComments evaluationComments,
			Principal principal,
			Model model) {
		System.out.println("////////////////////////DDDDDD////////staffid/////////////////////////////"+staffid);
		
		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
		Hrperiod hrperiod = hrperiodrepository.getperiod();
		String period = hrperiod.getPlanYear();
		/*int currentYear = Year.now().getValue();
		String planYear = Integer.toString(currentYear);*/
		System.out.println("////////////////////////DDDDDD////////staffid/////////////////////////////"+staffid);
		
		Staffcompetency staffCompetency = staffcompetencyrepository.checkCompetency(staffid, period, hrperiod.getPeriods());
		
		Employee staffInfo = employeeRepository.getEmployeeByEmployeeId(staffid);
		Employee supervisorInfo = employeeRepository.getEmployeeByEmployeeId(staffInfo.getSupervisor_id());
		Employee reviewerInfo = employeeRepository.getEmployeeByEmployeeId(supervisorInfo.getSupervisor_id());
		
		if(staffCompetency != null) {
			if(operation.equals("Return Evaluation Plan")) {
				int getLevel = staffCompetency.getLevel() - 1;
				long newid = Long.parseLong(staffid);
				staffcompetencyrepository.staffEvaluationUpdatLevel(getLevel, newid);
				
			///////staff mail
				SimpleMailMessage email = mailConstructor.staffReturnEmail(staffInfo, period, hrperiod.getPeriods());

				try {
					mailSender.send(email);
				} 
				catch (MailParseException mailParseException) {
					model.addAttribute("emailIssue", true);
				} 
				catch (MailAuthenticationException mailAuthenticationException) {
					model.addAttribute("emailIssue", true);
				} 
				catch (MailSendException MailSendException) {
					model.addAttribute("emailIssue", true);
				}
				catch (MailException MailException) {
					model.addAttribute("emailIssue", true);
				}
				
			///////supervisor mail
				SimpleMailMessage email1 = mailConstructor.supervisorReturnEmail(supervisorInfo, period, staffInfo, hrperiod.getPeriods());

				try {
					mailSender.send(email1);
				} 
				catch (MailParseException mailParseException) {
					model.addAttribute("emailIssue", true);
				} 
				catch (MailAuthenticationException mailAuthenticationException) {
					model.addAttribute("emailIssue", true);
				} 
				catch (MailSendException MailSendException) {
					model.addAttribute("emailIssue", true);
				}
				catch (MailException MailException) {
					model.addAttribute("emailIssue", true);
				}
				
			}/*
			else if(operation.equals("Submit Evaluation Plan")) {

			}*/
			else if(operation.equals("Approve Evaluation Plan")) {
				int getLevel = staffCompetency.getLevel() + 1;
				long newid = Long.parseLong(staffid);
				staffcompetencyrepository.staffEvaluationUpdatLevel(getLevel, newid);
				
				if(employee.getEmployee_id().equals(staffid)) {
			////////reviewer mail
							
							SimpleMailMessage email3 = mailConstructor.approveEvaluationReviewerEmail(reviewerInfo, period, staffInfo, hrperiod.getPeriods());

							try {
								mailSender.send(email3);
							} 
							catch (MailParseException mailParseException) {
								model.addAttribute("emailIssue", true);
							} 
							catch (MailAuthenticationException mailAuthenticationException) {
								model.addAttribute("emailIssue", true);
							} 
							catch (MailSendException MailSendException) {
								model.addAttribute("emailIssue", true);
							} catch (MailException MailException) {
								model.addAttribute("emailIssue", true);
							}
				}else{
					//staff mail
					/*SimpleMailMessage email = mailConstructor.approveEvaluationStaffEmail(staffInfo, period, hrperiod.getPeriods());

					try {
						mailSender.send(email);
					} 
					catch (MailParseException mailParseException) {
						model.addAttribute("emailIssue", true);
					} 
					catch (MailAuthenticationException mailAuthenticationException) {
						model.addAttribute("emailIssue", true);
					} 
					catch (MailSendException MailSendException) {
						model.addAttribute("emailIssue", true);
					}
					catch (MailException MailException) {
						model.addAttribute("emailIssue", true);
					}*/
					
					////////supervisor mail
				//	Employee supervisor = employeeRepository.getEmployeeByEmployeeId(supervisor_id);
					/*SimpleMailMessage email1 = mailConstructor.approveEvaluationSupervisorEmail(supervisorInfo, period, staffInfo, hrperiod.getPeriods());

					try {
						mailSender.send(email1);
					} 
					catch (MailParseException mailParseException) {
						model.addAttribute("emailIssue", true);
					} 
					catch (MailAuthenticationException mailAuthenticationException) {
						model.addAttribute("emailIssue", true);
					} 
					catch (MailSendException MailSendException) {
						model.addAttribute("emailIssue", true);
					} 
					catch (MailException MailException) {
						model.addAttribute("emailIssue", true);
					}*/
				}
			}
		}else {
			if(operation.equals("Return Evaluation Plan")) {
			}else if(operation.equals("Submit Evaluation Plan")) {
				staffcompetency.setCompetencyYear(period);
				staffcompetency.setPeriod(hrperiod.getPeriods());
				staffcompetency.setLevel(1);
				staffcompetencyrepository.save(staffcompetency);
				
				//staff mail
				SimpleMailMessage email = mailConstructor.submitEvaluationStaffEmail(staffInfo, period,  hrperiod.getPeriods());

				try {
					mailSender.send(email);
				} 
				catch (MailParseException mailParseException) {
					model.addAttribute("emailIssue", true);
				} 
				catch (MailAuthenticationException mailAuthenticationException) {
					model.addAttribute("emailIssue", true);
				} 
				catch (MailSendException MailSendException) {
					model.addAttribute("emailIssue", true);
				}
				catch (MailException MailException) {
					model.addAttribute("emailIssue", true);
				}
				
				
			}else if(operation.equals("Approve Evaluation Plan")) {
			}
		}
		
		EvaluationComments staffcomment = new EvaluationComments();
			staffcomment.setDateUpdated(new Timestamp(System.currentTimeMillis()));
			staffcomment.setEmployeeId(employee.getEmployee_id());
			staffcomment.setStaffComments(staffComments);
			staffcomment.setModuleOperationId(planid);
			evaluationCommentsRepository.save(staffcomment);
		return "redirect:/evaluation?id="+staffid;
	}
	
	
	@RequestMapping(value = "/saveManagerCompetency", method = RequestMethod.POST)
	public String saveSupervisorCompetency(@RequestParam("supervisorid") String supervisorid, @ModelAttribute("supervisorcompetency") Supervisorcompetency supervisorcompetency) {
		int currentYear = Year.now().getValue();
		String planYear = Integer.toString(currentYear);
		System.out.println("////////////////////////DDDDDD////////staffid/////////////////////////////"+planYear);
		supervisorcompetency.setCompetencyYear(planYear);
		
		supervisorcompetencyrepository.save(supervisorcompetency);
		return "redirect:/supervisor_evaluation?id="+supervisorid;
	}
	
	
	@RequestMapping(value = "/submitPlanning", method = RequestMethod.POST)
	public String submitPlanning(
			@RequestParam("param") String param,
			@RequestParam("staffid") String staffid,
			@ModelAttribute("staffComments") String staffComments,
			@ModelAttribute("performancePlanComment") PerformancePlanComments performancePlanComment,
			@ModelAttribute("submitPerformancePlan") SubmitPerformancePlan submitPerformancePlan,
			@ModelAttribute("employeeIdTransfered") String employeeIdTransfered, Principal principal, Model model
			) {
		
		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
		Hrperiod hrperiod = hrperiodrepository.getperiod();
		String period = hrperiod.getPlanYear();
		
		System.out.println("this is the staff id "+ staffid);
		
		//int currentYear = Year.now().getValue();
		String plan_id = employeeIdTransfered + period;
		
		// finding the three parties involve
		String staff_id;
		String supervisor_id;
		String reviewer_id;
		String returnid;
		if(staffid.equals("0")) {
			returnid = employee.getEmployee_id();
			staff_id = employee.getEmployee_id();
			supervisor_id = employee.getSupervisor_id();
		System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIII"+supervisor_id);
			Employee isSupervisor = employeeRepository.getEmployeeByEmployeeId(supervisor_id); 
			reviewer_id = isSupervisor.getSupervisor_id();
		}else {
			returnid = staffid;
			staff_id = staffid;
			supervisor_id = employee.getEmployee_id(); 
			System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIII"+supervisor_id);
			Employee isSupervisor = employeeRepository.getEmployeeByEmployeeId(supervisor_id); 
			reviewer_id = isSupervisor.getSupervisor_id();
		}
		
		Employee staffInfo = employeeRepository.getEmployeeByEmployeeId(staff_id);
		Employee supervisorInfo = employeeRepository.getEmployeeByEmployeeId(staffInfo.getSupervisor_id());
		Employee reviewerInfo = employeeRepository.getEmployeeByEmployeeId(supervisorInfo.getSupervisor_id());
		
		 String checkbtn = param;
		 
		 System.out.println("^^^^^^^^^^^^^^^^^STAFF ID^^^^^^^^^^^^^^^^^^%%%%%%%%%% " + staffid);
		 int level = 0;
		 Employee supervisor = employeeRepository.getEmployeeByEmployeeId(supervisor_id);
			System.out.println("==================================sup email " + supervisor.getEmail());
			
		 if(checkbtn.equals("Return Performance Plan")) {
			 SubmitPerformancePlan submitplan = submitPerformancePlanRepository.getSubmittedPlan(period, staffid);
			 int newlevel = submitplan.getLevel() - 1;
			 submitPerformancePlanRepository.approvePerformancePlan(newlevel, submitplan.getPlan_id());
			 //saving staff comment
			 performancePlanComment.setStaffComments(staffComments);
			 performancePlanComment.setEmployeeId(employee.getEmployee_id());
		   	 performancePlanComment.setModuleOperationId(plan_id);
			 performancePlanComment.setDateUpdated(new Timestamp(System.currentTimeMillis()));
			 performancePlanCommentsRepository.save(performancePlanComment);
			 
			 
			 
			 // THE MAIL FOR THE RETURN
			 if(employee.getEmployee_id().equals(reviewerInfo.getEmployee_id())) {
				 Employee staff = employeeRepository.getEmployeeByEmployeeId(staff_id);
			////////supervisor mail
					SimpleMailMessage email1 = mailConstructor.returnSupervisorEmail(supervisorInfo, staffInfo, period);

					try {
						mailSender.send(email1);
					} 
					catch (MailParseException mailParseException) {
						model.addAttribute("emailIssue", true);
					} 
					catch (MailAuthenticationException mailAuthenticationException) {
						model.addAttribute("emailIssue", true);
					} 
					catch (MailSendException MailSendException) {
						model.addAttribute("emailIssue", true);
					} 
					catch (MailException MailException) {
						model.addAttribute("emailIssue", true);
					}
					
			////////staff mail
							/*String reviewerId = supervisor.getSupervisor_id();
							Employee reviewer = employeeRepository.getEmployeeByEmployeeId(reviewerId);*/
							SimpleMailMessage email3 = mailConstructor.returnReviewerStaffEmail(reviewerInfo, staffInfo, period);

							try {
								mailSender.send(email3);
							} 
							catch (MailParseException mailParseException) {
								model.addAttribute("emailIssue", true);
							} 
							catch (MailAuthenticationException mailAuthenticationException) {
								model.addAttribute("emailIssue", true);
							} 
							catch (MailSendException MailSendException) {
								model.addAttribute("emailIssue", true);
							} catch (MailException MailException) {
								model.addAttribute("emailIssue", true);
							}
				 
			 }else {
				//staff mail
				 //Employee staff = employeeRepository.getEmployeeByEmployeeId(staff_id);
					SimpleMailMessage email = mailConstructor.returnStaffEmail(supervisorInfo, staffInfo,  period);

					try {
						mailSender.send(email);
					} 
					catch (MailParseException mailParseException) {
						model.addAttribute("emailIssue", true);
					} 
					catch (MailAuthenticationException mailAuthenticationException) {
						model.addAttribute("emailIssue", true);
					} 
					catch (MailSendException MailSendException) {
						model.addAttribute("emailIssue", true);
					}
					catch (MailException MailException) {
						model.addAttribute("emailIssue", true);
					}
			 }
			 
			
				
				
			 //RETURN MAIL END HERE
		 }
		 else if(checkbtn.equals("Submit Performance Plan")) {
			 	submitPerformancePlan.setPlan_id(plan_id);
				submitPerformancePlan.setSubmitted_time(new Timestamp(System.currentTimeMillis()));
				submitPerformancePlan.setLevel(level + 1);
				submitPerformancePlan.setUser_id(employeeIdTransfered);
				submitPerformancePlan.setStatus("Submited");
				submitPerformancePlan.setPerformace_Plan_Period(period);
				
				//saving staff comments
				performancePlanComment.setStaffComments(staffComments);
				performancePlanComment.setEmployeeId(employee.getEmployee_id());
				performancePlanComment.setModuleOperationId(plan_id);
				performancePlanComment.setDateUpdated(new Timestamp(System.currentTimeMillis()));
				
				performancePlanCommentsRepository.save(performancePlanComment);
				
				SubmitPerformancePlan checkPlanning = submitPerformancePlanRepository.checkIfPlanExit(plan_id);
				if(checkPlanning != null) {
					submitPerformancePlanRepository.approvePerformancePlan(level+1, plan_id);
				}else {
					submitPerformancePlanRepository.save(submitPerformancePlan);
				}
				
				
				
				//staff mail
				SimpleMailMessage email = mailConstructor.submitStaffEmail(employee, period);

				try {
					mailSender.send(email);
				} 
				catch (MailParseException mailParseException) {
					model.addAttribute("emailIssue", true);
				} 
				catch (MailAuthenticationException mailAuthenticationException) {
					model.addAttribute("emailIssue", true);
				} 
				catch (MailSendException MailSendException) {
					model.addAttribute("emailIssue", true);
				}
				catch (MailException MailException) {
					model.addAttribute("emailIssue", true);
				}
				
				////////supervisor mail
				//Employee supervisor = employeeRepository.getEmployeeByEmployeeId(supervisor_id);
				SimpleMailMessage email1 = mailConstructor.submitSupervisorEmail(supervisor, period, employee);

				try {
					mailSender.send(email1);
				} 
				catch (MailParseException mailParseException) {
					model.addAttribute("emailIssue", true);
				} 
				catch (MailAuthenticationException mailAuthenticationException) {
					model.addAttribute("emailIssue", true);
				} 
				catch (MailSendException MailSendException) {
					model.addAttribute("emailIssue", true);
				} 
				catch (MailException MailException) {
					model.addAttribute("emailIssue", true);
				}
				
				
		 }
		 else if(checkbtn.equals("Approve Performance Plan")) {
			 SubmitPerformancePlan submitplan = submitPerformancePlanRepository.getSubmittedPlan(period, staffid);
			 int newlevel = submitplan.getLevel() + 1;

			 int checkObjectiveWeight = performancePlanObjRepository.totalweightassign(staffid, period);
				 if(checkObjectiveWeight != 60) {
					 return "redirect:/plan_performance_staff?id="+returnid;
				 }
				//saving staff comments
					performancePlanComment.setStaffComments(staffComments);
					performancePlanComment.setEmployeeId(employee.getEmployee_id());
					performancePlanComment.setModuleOperationId(plan_id);
					performancePlanComment.setDateUpdated(new Timestamp(System.currentTimeMillis()));
					
					
					performancePlanCommentsRepository.save(performancePlanComment);
					
					submitPerformancePlanRepository.approvePerformancePlan(newlevel, plan_id);
					Employee staff = employeeRepository.getEmployeeByEmployeeId(staff_id);
					if(employee.getEmployee_id().equals(staff.getSupervisor_id()) ) {
						//*** staff supervisor approve ***//
						//staff mail
						SimpleMailMessage email = mailConstructor.approveStaffEmail(staff, period);

						try {
							mailSender.send(email);
						} 
						catch (MailParseException mailParseException) {
							model.addAttribute("emailIssue", true);
						} 
						catch (MailAuthenticationException mailAuthenticationException) {
							model.addAttribute("emailIssue", true);
						} 
						catch (MailSendException MailSendException) {
							model.addAttribute("emailIssue", true);
						}
						catch (MailException MailException) {
							model.addAttribute("emailIssue", true);
						}
						
					
						
				  //reviewer mail
								String reviewerId = supervisor.getSupervisor_id();
								Employee reviewer = employeeRepository.getEmployeeByEmployeeId(reviewerId);
								SimpleMailMessage email3 = mailConstructor.approveReviewerEmail(reviewer, period, staff);

								try {
									mailSender.send(email3);
								} 
								catch (MailParseException mailParseException) {
									model.addAttribute("emailIssue", true);
								} 
								catch (MailAuthenticationException mailAuthenticationException) {
									model.addAttribute("emailIssue", true);
								} 
								catch (MailSendException MailSendException) {
									model.addAttribute("emailIssue", true);
								} catch (MailException MailException) {
									model.addAttribute("emailIssue", true);
								}
					}else {
						//*** staff reviewer approve ***//
						//staff mail
						SimpleMailMessage email = mailConstructor.reviewStaffEmail(staff, period);

						try {
							mailSender.send(email);
						} 
						catch (MailParseException mailParseException) {
							model.addAttribute("emailIssue", true);
						} 
						catch (MailAuthenticationException mailAuthenticationException) {
							model.addAttribute("emailIssue", true);
						} 
						catch (MailSendException MailSendException) {
							model.addAttribute("emailIssue", true);
						}
						catch (MailException MailException) {
							model.addAttribute("emailIssue", true);
						}
						
						
					}
					
					
					
		 }
		 
		 
		 
		
		
				
		return "redirect:/plan_performances?id="+returnid;
	}
	
	
	
	@RequestMapping("/review_evaluation_staff")
	public String reviewEvaluation(@RequestParam("id") String id, Model model, Principal principal) {
		//String period = periodRepository.getOpenModule(1903);
		/*Hrperiod period = hrperiodrepository.getperiod();
		System.out.println("*************************************************************"+period);*/
		Hrperiod hrperiod = hrperiodrepository.getperiod();
		String period = hrperiod.getPlanYear();
		
		User user = userservice.findByUsername(principal.getName());
		Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
		
		Staffcompetency findstaffcompetency = staffcompetencyrepository.findStaffCompetency(id,period);
		System.out.println("%%%%%%%%%%%%%%%%%%%%%findstaffcompetency%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+findstaffcompetency);
		
		Supervisorcompetency findsupervisorcompetency = supervisorcompetencyrepository.findSupervisorCompetency(employee.getEmployee_id());
		System.out.println("###################login person###############################"+employee.getEmployee_id());
		
		String staffTotalCompetency = staffcompetencyrepository.staffCompetencyTotal(id, period, hrperiod.getPeriods());
		System.out.println("====================staffTotalCompetency==========================="+staffTotalCompetency);
		
		Employee staffInfo = employeeRepository.getEmployeeByEmployeeId(id);
		String sup = staffInfo.getSupervisor_id();
		String sup1 = employee.getEmployee_id();
		System.out.println("++++++++++++++++++sup+++++++++sup1++++++++++++++++++++"+sup1+"="+sup);
		
		String staffid = id;
		System.out.println("%%%%%%%%%%%%%%%%%%%%staffid%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+staffid);
		
		// check if staff have competency for the open period
		Staffcompetency checkStaffCompetency = staffcompetencyrepository.checkCompetency(id, period, hrperiod.getPeriods());
		System.out.println("=========checkStaffCompetency====================" + checkStaffCompetency);
		//check if supervisor have competency for that perion
		String checkSupervisorCompetency = supervisorcompetencyrepository.checkSupervisorCompetency(employee.getEmployee_id(), period);
		System.out.println("=========checkSupervisorCompetency====================" + checkSupervisorCompetency);
		
		List<EmployeeComments> showcomments = employeeCommentsRepository.showEvaluationComments(staffid+""+period);
		List<ObjectiveScore> submitedStaffObj = objectivescorerepository.getSubmitedPerformanceObjForStaff(period, id, 3);
		
		String planTotalScore = planscorerepository.totalPlanScore(id, hrperiod.getPeriods(), period);
		if(planTotalScore != null) {
			model.addAttribute("planTotalScore", planTotalScore);
		}
		System.out.println("(((((((((((((((((((((((((((((((((((((((("+planTotalScore);
		
		if(sup.equals(sup1)) {
			model.addAttribute("superv", true);
		}else {
			model.addAttribute("superv", false);
		}
		
		boolean checkcompetency = false;
		if(checkStaffCompetency != null) {
			 checkcompetency = true;
				// Check if evaluation plan period is open
			 model.addAttribute("findstaffcompetency", findstaffcompetency);
			 model.addAttribute("staffTotalCompetency", staffTotalCompetency);
			 
			 int level = checkStaffCompetency.getLevel();
			 if(level == 2) {
				 model.addAttribute("evalautionReturnButton", true);
				 model.addAttribute("evaluationSubmitButton", false);
				 model.addAttribute("evalautionApproveButton", true);
			 }else {
				 model.addAttribute("evalautionReturnButton", false);
				 model.addAttribute("evaluationSubmitButton", false);
				 model.addAttribute("evalautionApproveButton", false);
			 }
			
		}
		
		// SUPERVISOR
		
		boolean checkSupervisor = false;
		if(checkSupervisorCompetency != null) {
			 
			checkSupervisor = true;
			 
				// Check if evaluation plan period is open
			 model.addAttribute("findsupervisorcompetency", findsupervisorcompetency);
			
		}
		
		model.addAttribute("showcomments", showcomments);
		model.addAttribute("submitedStaffObj", submitedStaffObj);
		model.addAttribute("checkcompetency", checkcompetency);
		model.addAttribute("checkSupervisor", checkSupervisor);
		model.addAttribute("staffid", staffid);
		
		///

		
		if (period.equals(period)) {
			
			
			

			Employee supervisor = employeeRepository.findSupervisorDetail(id);
			// check if supervisor
			model.addAttribute("supervisor", supervisor);
			Staffcompetency submitEvaluation = staffcompetencyrepository.getSubmittedEvaluation(period,
					id);
			System.out.println("*******************************"+id);
			if (submitEvaluation != null) {
			} else {

				return "redirect:/home";
				
			}

			List<PerformancePlanObj> performancePlanList = (List<PerformancePlanObj>) performancePlanObjRepository
					.userPerformancePlanObj(employee.getEmployee_id(), period);

			ArrayList<String> perfPlanObj = new ArrayList<>();
			for (PerformancePlanObj perfPlanLoop : performancePlanList) {
				perfPlanObj.add(perfPlanLoop.getId() + "_" + perfPlanLoop.getPerformance_objective());
			}

			// Calculate grand Total
			List<EvaluationPlanObj> evaluationPlanObjsList = (List<EvaluationPlanObj>) evaluationPlanObjRepository
					.userEvaluationPlanObj(employee.getEmployee_id());

			double grandTotAssignWeigth = 0.00;
			double grandTot = 0.00;

			for (EvaluationPlanObj evaluationPlanObjLoop : evaluationPlanObjsList) {
				grandTotAssignWeigth += evaluationPlanObjLoop.getWeight_assigned();
				grandTot += evaluationPlanObjLoop.getTot();
			}

			// get latest period order by date
//		String currentPeriod = period;

			// Check if plan has been loaded for employee_id and the period and the position
			// otherwise load plan
			List<EvaluationCompetency> validateLoadEvaluationCompetency = evaluationCompetencyRepository
					.getCompetency("2023", employee.getEmployee_id());
			
			List<PlanCompetency> competencyLookup = planCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			if (validateLoadEvaluationCompetency.size() > 0) {

			} else {

				for (PlanCompetency comp : competencyLookup) {
					EvaluationCompetency evaluationCompetency = new EvaluationCompetency();
					evaluationCompetency.setCompetency_id((int) comp.getId());
					evaluationCompetency.setEmployee_id(employee.getEmployee_id());
					evaluationCompetency.setPeriod(period);
					evaluationCompetency.setCompetency_title(comp.getCompetency_title());
					evaluationCompetency.setPosition_id(comp.getPosition_id());
					evaluationCompetency.setRequired(Double.valueOf(comp.getRequired_point()));
					evaluationCompetency.setAssigned((Double.valueOf(comp.getActual_point())));

					evaluationCompetencyRepository.save(evaluationCompetency);
				}

			}

			List<EvaluationCompetency> evaluationCompetencies = evaluationCompetencyRepository.getCompetency(period,
					employee.getEmployee_id());
			double totActualProficiency = 0.0;
			double totWeightAttained = 0.0;
			for (EvaluationCompetency comp : evaluationCompetencies) {
				totActualProficiency += comp.getActual_proficiency();
				totWeightAttained += comp.getAttained();
			}

			// Check if employee is a supervisor
			Employee employeeSuperviorCheck = employeeRepository.checkIfEmployeeIsSupervisor(employee.getEmployee_id());
			if (employeeSuperviorCheck != null) {
				model.addAttribute("isSupervisor", true);
			}

			model.addAttribute("totWeightAttained", Math.round(totWeightAttained) * 100 / 100.0);
			model.addAttribute("evaluationCompetencies", evaluationCompetencies);
			model.addAttribute("grandTotAssignWeigth", Math.round(grandTotAssignWeigth) * 100 / 100.0);
			model.addAttribute("grandTot", Math.round(grandTot * 100) / 100.0);
			model.addAttribute("evaluationPlanObjsList", evaluationPlanObjsList);
			model.addAttribute("perfPlanObj", perfPlanObj);
			model.addAttribute("employee", employee);
			model.addAttribute("staffInfo", staffInfo);
			//System.out.println("Size of the list " + evaluationPlanObjsList.size());
			return "viewEvaluation";

		} else {
 
			return "error";
		}

	}
	
	
	

	////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping("/testing")
	
	public List<PerformancePlanComments> testing(@RequestBody PerformancePlanComments product){
		return performancePlanCommentsRepository.showComments("000752023");
	}
	
	
	// db
	@RequestMapping("/staffplaned")
	public String staffplaned( @RequestParam("id") int id, Principal principal, Model model) {
		User user = userservice.findByUsername(principal.getName());
		//Employee employee = employeeRepository.getEmployeeByEmployeeId(id);
		Employee employee = employeeRepository.getEmployeeDetailsByEmail(user.getEmail());
		String isStaff = employee.getEmployee_id();
		Hrperiod hrperiod = hrperiodrepository.getperiod();
		String period = hrperiod.getPlanYear();
		
		SubmitPerformancePlan submitPerformancePlan = submitPerformancePlanRepository.getSubmittedPlan1(
				id);
		
		if(submitPerformancePlan.getLevel() == 0) {
			return "redirect:/plan_performances?id="+ submitPerformancePlan.getUser_id();
		}
		System.out.println("---------------kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk---------------------------" + id);
		
		if (submitPerformancePlan != null) {
			
			int currentLevel = submitPerformancePlan.getLevel();
			PerformancePlanComments staffStatement = performancePlanCommentsRepository.getComment(submitPerformancePlan.getPlan_id(), isStaff);
			//System.out.println("%%%%%%%%  DOMINIC  %%%%%%%%%%%%%%%%%%%%%%%%%%%"+staffStatement.getStaffComments()+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+staffStatement.getEmployeeId());
			List<EmployeeComments> showcomments = employeeCommentsRepository.showComments(submitPerformancePlan.getPlan_id());
			
			if(isStaff.equals(submitPerformancePlan.getUser_id())) {
				System.out.println("====================== I AM AT ONE ===============" + isStaff);
				if(currentLevel == 1) {
					model.addAttribute("submitActionButton", false);
 					model.addAttribute("addObjectiveActionButton", false);
					model.addAttribute("removeObjectiveActionButton", false);
					model.addAttribute("editObjectiveActionButton", false);
					model.addAttribute("approveReturnActionButton", false);
					model.addAttribute("staffComment", true);
					model.addAttribute("reviewerComment", false);
					model.addAttribute("supervisorComment", false);
					

				}else if(currentLevel == 0) {
					model.addAttribute("addObjectiveActionButton", true);
					model.addAttribute("submitActionButton", true);
				}
			
			model.addAttribute("staffStatement", staffStatement);
			model.addAttribute("showcomments", showcomments);
			model.addAttribute("submitPerformancePlan", submitPerformancePlan);
		}else {
			System.out.println("====================== I AM AT TWO ===============" + isStaff);
			model.addAttribute("submitActionButton", true);
			model.addAttribute("supervisorComment", true);
			model.addAttribute("reviewerComment", true);
			model.addAttribute("removeObjectiveActionButton", true);
			model.addAttribute("editObjectiveActionButton", true);
			model.addAttribute("addObjectiveActionButton", true);
		}
			List<PerformancePlanObj> performancePlanList = (List<PerformancePlanObj>) performancePlanObjRepository
					.userPerformancePlanObj(employee.getEmployee_id(), submitPerformancePlan.getPerformace_Plan_Period());
			//List<EmployeeComments> showcomments = employeeCommentsRepository.showComments(submitPerformancePlan.getPlan_id());
		
		model.addAttribute("employee", employee);
		model.addAttribute("performancePlanList", performancePlanList);
		model.addAttribute("showcomments", showcomments);
			
	}/*else {
		
	}*/
		model.addAttribute("period", submitPerformancePlan.getPerformace_Plan_Period());
		return "staffPlanning";

}
	
	@RequestMapping("/myerror")
	public String syserror() {
		return "error";
	}
	
	
	@RequestMapping("/adduserform")
	public String adduser(Model model){
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		
		List<Employee> users = employeeRepository.allUser();
		model.addAttribute("users", users);
		return "addUsers";
	}
	
	@RequestMapping("/adduser")
	public String newuser(
			@ModelAttribute("employee") Employee employee,
			@ModelAttribute("first_name") String first_name,
			@ModelAttribute("last_name") String last_name,
			@ModelAttribute("email") String email,
			@ModelAttribute("phone") String phone, 
			@ModelAttribute("supervisor_id") String supervisor_id,
			@ModelAttribute("employee_id") String employee_id,
			Model model) {
		/*String username = first_name.charAt(0)+""+last_name;
		String username1 = username.toLowerCase();*/
		
		Employee isUserExist = employeeRepository.getEmployeeDetailsByEmail(email);
		System.out.println("__________________________________________>>> " + isUserExist);
	//SAVE EMPLOYEE
		if(isUserExist != null) {
			model.addAttribute("userExist", true);
			model.addAttribute("email", email);
			return "addUsers";
		}else {
		employeeRepository.save(employee);
		
	//SAVE USER
		User newuser = new User();
		newuser.setEmail(email);
		newuser.setPhone(phone);
		newuser.setFirstName(first_name);
		newuser.setLastName(last_name);
		newuser.setUsername(email);
		userrepository.save(newuser);
	
		Employee getSupervisor = employeeRepository.getEmployeeByEmployeeId(supervisor_id);
		User getuser = userrepository.getStaffByEmail(email);
		
	//saving the using role
		Long empid = Long.valueOf(getuser.getId());
		userrolerepository.saveUserRole(1, empid);
		
		if(getSupervisor != null) {
			StaffReviewer giveReviewer = new StaffReviewer();
			giveReviewer.setEmployee_id(employee_id);
			giveReviewer.setReviewer_id(getSupervisor.getSupervisor_id());
			giveReviewer.setStatus("Active");
			staffreviewerrepository.save(giveReviewer);
			}
		
		SimpleMailMessage email1 = mailConstructor.userpasswordemail(email);

		try {
			mailSender.send(email1);
		} 
		catch (MailParseException mailParseException) {
			model.addAttribute("emailIssue", true);
		} 
		catch (MailAuthenticationException mailAuthenticationException) {
			model.addAttribute("emailIssue", true);
		} 
		catch (MailSendException MailSendException) {
			model.addAttribute("emailIssue", true);
		} 
		catch (MailException MailException) {
			model.addAttribute("emailIssue", true);
		}
		
		return "redirect:/adduserform";
		}
	}
	
	@RequestMapping("/userpassword")
	public String staffAccount(@RequestParam("id") String id, Model model) {
		Employee staff = employeeRepository.getEmployeeDetailsByEmail(id);
		model.addAttribute("staff", staff);
		return "userpassword";
	}
	
	@RequestMapping("/comfirmPassword")
	public String updatePassword(
			@RequestParam("password1") String password1, 
			@RequestParam("email") String email,
			Model model) {

		
		/*Employee staff = employeeRepository.getEmployeeDetailsByEmail(email);
		
	*/
		//System.out.println("============****************++++++" + email + "^^^^^^^^^^^^^^^^" +  password1 + "&&&&&&&&&&&&&&&" + staff);
		//String notes = "Please not that your password and confirm password does not match!";
			String newpassword = passswordencoder.encode(password1);
			userrepository.newPassword(newpassword, email);
			return "lra_login";
		
	}
	
	@RequestMapping("/reset")
	public String resetcompetency(Model model, @RequestParam("id") String id) {
		Long newid = Long.valueOf(id);
		Staffcompetency staff = staffcompetencyrepository.getStaffCompetencyById(newid);
		staffcompetencyrepository.resetCompetency(newid);
		
		System.out.println("<=================================" + newid);
		return "redirect:/evaluation?id="+staff.getStaffid();
	}
	
	@RequestMapping("/admin")
	public String admin_dashboard() {
		return "admin";
	}
	
	@RequestMapping("/edituser")
	public String edituser(@RequestParam("email") String email, Model model) {
		Employee findStaff = employeeRepository.getEmployeeDetailsByEmail(email);
		model.addAttribute("findStaff", findStaff);
		return "edituser";
	}
	
	@RequestMapping("/period")
	public String performanceperiod(Model model) {
		List<Hrperiod> allperiod = hrperiodrepository.getallperiod();
		//System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< " + allperiod;
		model.addAttribute("allperiod", allperiod);
		return "performanceperiod";
	}
	
	@RequestMapping("/planyear")
	public String planyear(@ModelAttribute("planyear") String planyear) {
		hrperiodrepository.updateperiodyear(planyear);
		return "redirect:/period";
	}
	
	@RequestMapping("//updatePlanPeriod")
	public String periodstatus(@RequestParam("id") String id) {
		int id1 = Integer.valueOf(id);
		Hrperiod statuscheck = hrperiodrepository.planPeriodCheck(id1);
		
		if(statuscheck.getStatus().equals("close")) {
			hrperiodrepository.updateallstatus("close");
			hrperiodrepository.updateastatus("open", id1);
		}
		return "redirect:/period";
	}
	
	
}


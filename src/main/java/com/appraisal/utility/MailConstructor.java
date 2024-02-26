
package com.appraisal.utility;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.appraisal.domain.Employee;
import com.appraisal.domain.User;


@Component
public class MailConstructor {
	@Autowired
	private Environment env;
	
	@Autowired
    JavaMailSender mailSender;
	
	public SimpleMailMessage userpasswordemail(
			 String employee
			) {
		String message = "Your account for the Performance Planning and Evaluation application had Successfully  been created. Please log on the application by clicking the below link and set your password \n User Name = " + employee + " \n https://eservices.lra.gov.lr/PerformanceAppraisal/userpassword?id="+employee;
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(employee);
		email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
		email.setText(message);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}
	
	//*** submit performance plan ***///
	//staff mail
	//=== Staff submit plan mail to staff self
	public SimpleMailMessage submitStaffEmail(
			 Employee employee, String period
			) {
		String message = "Dear " + employee.getFirst_name().toUpperCase()+ " " + employee.getLast_name().toUpperCase() + " \n thanks for submitting your performance plan for the fiscal year "+period+". It is currently under review with your supervisor. \n Please click the below link \n\n https://eservices.lra.gov.lr/PerformanceAppraisal ";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(employee.getEmail());
		email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
		email.setText(message);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}
	
	//supervisor mail
	//=== Staff submit plan mail to supervisor
	public SimpleMailMessage submitSupervisorEmail(
			 Employee supervisor, String period, Employee employee
			) {
		
		String message ="Dear " + supervisor.getFirst_name().toUpperCase()+ " " + supervisor.getLast_name().toUpperCase() + " \n " + employee.getFirst_name().toUpperCase()+ " " + employee.getLast_name().toUpperCase() +" has submitted a performance plan for the fiscal year "+period+" Please use the link below to viwe the plan. \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login "; 
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(supervisor.getEmail());
		email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
		email.setText(message);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}
	
	
		public SimpleMailMessage approveStaffEmail(
				 Employee staff, String period
				) {
			String message = "Dear " + staff.getFirst_name().toUpperCase() + "\n Your Performance plan for the fiscal year " +period +" had been approved and is pending review. \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(staff.getEmail());
			email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
			email.setText(message);
			email.setFrom(env.getProperty("support.email"));
			return email;	
		}
		
		
		
		//reviewer mail
		//=== Supervisor approve plan mail to reviewer
		public SimpleMailMessage approveReviewerEmail(
				 Employee reviewer, String period, Employee staff
				) {
			
			String message = "Dear " +  reviewer.getFirst_name().toUpperCase()+ " " + reviewer.getLast_name().toUpperCase() +";\n The Performance plan for " + staff.getFirst_name().toUpperCase()+ " " + staff.getLast_name().toUpperCase() +" for the fiscal year "+ period +" has been approved and is awaiting your review. Please use the link below to view the plan \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login "; 
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(reviewer.getEmail());
			email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
			email.setText(message);
			email.setFrom(env.getProperty("support.email"));
			return email;	
		}

		//*** review performance plan ***///
			//staff mail
		//=== Reviewer approve plan mail to staff
			public SimpleMailMessage reviewStaffEmail(
					 Employee staff, String period
					) {
				String message = "Dear " + staff.getFirst_name().toUpperCase() +" \n Your Performance plan for the fiscal year  "+period+" has been successfully reviewed and approved. Check the below link \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(staff.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}
			
			//supervisor mail
			/*public SimpleMailMessage reviewSupervisorEmail(
					 Employee staff, String period, Employee supervisor
					) {
				
				String message = staff.getFirst_name().toUpperCase()+ " " + staff.getLast_name().toUpperCase() +"  performance plan for the period  "+period+ " has been reviewed.\n Click the link below link \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login "; 
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(supervisor.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}*/
			
			//reviewer mail
			/*public SimpleMailMessage reviewReviewerEmail(
					 Employee employee, String period, Employee staff
					) {
				
				String message = "You had successfully reviewed " + staff.getFirst_name().toUpperCase()+ " " + staff.getLast_name().toUpperCase() +" performance plan for the period  "+period+".\n Please check the below link \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login "; //"Dear "+ supervisor.getFirst_name().toUpperCase()+";\n " + supervisor.getLast_name().toUpperCase() +" your DOMINIC SUPERVISOR performance plan for the period  "+period+" had been successfully submitted.\n Please click the below link \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(employee.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}*/
			
			
			//*** return performance plan ***///
			//staff mail
			//=== Supervisor return plan mail to staff
			public SimpleMailMessage returnStaffEmail(
					 Employee supervisor, Employee staff, String period
					) {
				String message = "Dear " + staff.getFirst_name().toUpperCase() + " Your Performance plan for the fiscal year " + period + " has been returned. \n Please use the link below to get to the application and make sure to view the comment to know why your Performance plan was return  \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(staff.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}
			
			//supervisor mail
			//=== Reviewer return plan mail to supervisor
			public SimpleMailMessage returnSupervisorEmail(
					 Employee supervisor, Employee staff, String period
					) {
				
				String message ="Dear " + supervisor.getFirst_name().toUpperCase()+ " " + supervisor.getLast_name().toUpperCase() +"; \n The Performance plan for " + staff.getFirst_name().toUpperCase()+ " " + staff.getLast_name().toUpperCase() + " for the fiscal year " + period + " has been returned. Please see the reason in the comment section in the application.  Please click the below link \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(supervisor.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}
			
			//reviewer mail
			//=== Reviewer return mail to staff
			public SimpleMailMessage returnReviewerStaffEmail(
					 Employee reviewer, Employee staff, String period
					) {
				
				String message ="Dear " + staff.getFirst_name().toUpperCase() + "\n\n Your Performance plan for the fiscal year " + period + " has been returned to your supervisor and is pending action  \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(staff.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}
		
	
	
	////////////////////////////////////////////
   ////////////////////////////////////////////
			
			//*** submit evaluation ***///
			//staff mail
			//=== Supervisor submit evaluation mail to staff
			public SimpleMailMessage submitEvaluationStaffEmail(
					 Employee staffInfo, String planyear, String period
					) {
				String message = "Dear " + staffInfo.getFirst_name().toUpperCase() + "\n\n" + " Your Performance Evaluation for " + period + " " +  planyear + " has been submitted by your supervisor. Please use the link below to view the evaluation details and acknowledge. \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(staffInfo.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}
			
			//supervisor mail
			/*public SimpleMailMessage submitEvaluationSupervisorEmail(
					 Employee supervisorInfo, String period, Employee staffInfo
					) {
				
				String message ="Dear "+ supervisorInfo.getFirst_name().toUpperCase()+ " " + supervisorInfo.getLast_name().toUpperCase() +" \n"+ staffInfo.getFirst_name().toUpperCase()+ " " + staffInfo.getLast_name().toUpperCase() +" have submitted his/her performance plan for the period  "+period+".\n Please click the below link to act on it \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login "; //"Dear "+ supervisor.getFirst_name().toUpperCase()+";\n " + supervisor.getLast_name().toUpperCase() +" your DOMINIC SUPERVISOR performance plan for the period  "+period+" had been successfully submitted.\n Please click the below link \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(supervisorInfo.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}*/
			
			//reviewer mail
			/*public SimpleMailMessage submitEvaluationReviewerEmail(
					 Employee reviewerInfo, String period, Employee staffInfo
					) {
				
				String message ="Dear "+ reviewerInfo.getFirst_name().toUpperCase()+ " " + reviewerInfo.getLast_name().toUpperCase() +" \n"+ staffInfo.getFirst_name().toUpperCase()+ " " + staffInfo.getLast_name().toUpperCase() +" performance plan have been approved for the period  "+period+".\n Please click the below link to review \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login "; //"Dear "+ supervisor.getFirst_name().toUpperCase()+";\n " + supervisor.getLast_name().toUpperCase() +" your DOMINIC SUPERVISOR performance plan for the period  "+period+" had been successfully submitted.\n Please click the below link \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(reviewerInfo.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}*/
	
			
			//*** approve evaluation  ***///
			//staff mail
			/*public SimpleMailMessage approveEvaluationStaffEmail(
					 Employee staffInfo, String planyear, String period
					) {
				String message = "Dear " + staffInfo.getFirst_name().toUpperCase()+ ", \n\n " + " Your Performance Evaluation for the " + period+ " " +  planyear + " has been approved. Please use the link below to view the evaluation details \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(staffInfo.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}*/
			
			//supervisor mail
			//public SimpleMailMessage approveEvaluationSupervisorEmail(
			/*		 Employee supervisorInfo, String planyear, Employee staffInfo, String period
					) {
				
				String message ="Dear Supervisor, \n\n The Performance Evaluation for" + staffInfo.getFirst_name().toUpperCase()+ " " + staffInfo.getLast_name().toUpperCase() +" for the " + period + planyear + " has  have submitted his/her performance plan for the period  "+period+".\n Please click the below link to act on it \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login "; //"Dear "+ supervisor.getFirst_name().toUpperCase()+";\n " + supervisor.getLast_name().toUpperCase() +" your DOMINIC SUPERVISOR performance plan for the period  "+period+" had been successfully submitted.\n Please click the below link \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(supervisorInfo.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}*/
			
			//reviewer mail
			//=== Staff approve evaluation mail to reviewer
			public SimpleMailMessage approveEvaluationReviewerEmail(
					 Employee reviewerInfo, String planyear, Employee staffInfo, String period
					) {
				
				String message ="Dear " + reviewerInfo.getFirst_name().toUpperCase()+ " " + reviewerInfo.getLast_name().toUpperCase() + ", \n\n" + " The Performance Evaluation for " + staffInfo.getFirst_name().toUpperCase()+ " " + staffInfo.getLast_name().toUpperCase() + " for " + period + " " +  planyear + " has been submitted and acknowledged. Please use the link below to review the evaluation details and take action, please make sure to view the evaluation comments \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login "; 
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(reviewerInfo.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}
			
			//*** review evaluation  ***///
			//staff mail
			//=== Reviewer approve evaluation mail to staff
			public SimpleMailMessage reviewEvaluationStaffEmail(
					 Employee staffInfo,String planyear, String period
					) {
				String message = "Dear " + staffInfo.getFirst_name().toUpperCase()+ " " + staffInfo.getLast_name().toUpperCase() + ", \n\n Your Performance Evaluation for " + period + " " + planyear + " is finally approved. \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(staffInfo.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}
			
			/*//supervisor mail
			public SimpleMailMessage reviewEvaluationSupervisorEmail(
					 Employee supervisorInfo, String planyear, Employee staffInfo, String period
					) {
				
				String message ="Dear "+ supervisorInfo.getFirst_name().toUpperCase()+ " " + supervisorInfo.getLast_name().toUpperCase() +", \n \n The Performance Evaluation for " + staffInfo.getFirst_name().toUpperCase()+ " " + staffInfo.getLast_name().toUpperCase() + " for the " + period + " " + planyear + "has been returned. Please click the below link to see the reason in the evaluation commet and take appropriate action as instructured before resubmitting. \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login "; //"Dear "+ supervisor.getFirst_name().toUpperCase()+";\n " + supervisor.getLast_name().toUpperCase() +" your DOMINIC SUPERVISOR performance plan for the period  "+period+" had been successfully submitted.\n Please click the below link \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(supervisorInfo.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}*/
			
			//reviewer mail
			/*public SimpleMailMessage reviewEvaluationReviewerEmail(
					 Employee reviewerInfo, String period, Employee staffInfo
					) {
				
				String message ="Dear "+ reviewerInfo.getFirst_name().toUpperCase()+ " " + reviewerInfo.getLast_name().toUpperCase() +" \n"+ staffInfo.getFirst_name().toUpperCase()+ " " + staffInfo.getLast_name().toUpperCase() +" performance plan have been approved for the period  " + period + ".\n Please click the below link to review \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login "; //"Dear "+ supervisor.getFirst_name().toUpperCase()+";\n " + supervisor.getLast_name().toUpperCase() +" your DOMINIC SUPERVISOR performance plan for the period  "+period+" had been successfully submitted.\n Please click the below link \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(reviewerInfo.getEmail());
				email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
				email.setText(message);
				email.setFrom(env.getProperty("support.email"));
				return email;	
			}*/
	
		// Reviewer Submit mail
		/*public SimpleMailMessage submitReviewerEmail(
				String contextPath, Employee employee, Employee reviewer, String period
				) {
			
			String url = contextPath;
			String message = "\n"+employee.getFirst_name().toUpperCase()+ " " + employee.getLast_name().toUpperCase() +" had submittted performance plan for the period "+ period +" to be work on by supervisor\n\nKind Regards,\nLRA-HR Division";
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(reviewer.getEmail());
			email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
			email.setText(message+url);
			email.setFrom(env.getProperty("support.email"));
			return email;	
		}*/



	
	/*public SimpleMailMessage returnEmail(
			String contextPath, Employee employee, String period
			) {
		
		String url = contextPath;
		String message = "\n"+employee.getFirst_name().toUpperCase() +", your performance plan for the period "+period+" was returned for adjustment. Kindly log in using above link, adjust and re-submit this plan within time."
				+ "\n\nKind Regards,\nLRA-HR Division.";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(employee.getEmail());
		email.setSubject("Electronic Performance Plan Management System - Returned Plan");
		email.setText(message+url);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}*/
	
	
	//*** return evaluation  ***///
	//staff mail
	//=== Reviewer return evaluation mail to staff
	public SimpleMailMessage staffReturnEmail(
			 Employee staffInfo, String planyear, String period
			) {
		String message = "Dear " + staffInfo.getFirst_name().toUpperCase()+", \n\n Your Performance Evaluation for the " + period+ " " +  planyear + " has been returned to your supervisor and is pending action. Please click the below link \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(staffInfo.getEmail());
		email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
		email.setText(message);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}
	
	//supervisor mail
	//=== Reviewer return evaluation mail to supervisor
	public SimpleMailMessage supervisorReturnEmail(
			 Employee supervisorInfo, String planyear, Employee staffInfo, String period
			) {
		
		String message = "Dear Supervisor, \n\n The Performance Evaluation for " + staffInfo.getFirst_name().toUpperCase()+ " " + staffInfo.getLast_name().toUpperCase() + " for " + period + " " +  planyear + " has been returned. Please user the below link to see the reason in the evaluation comment  \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(supervisorInfo.getEmail());
		email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
		email.setText(message);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}
	
	//reviewer mail
	/*public SimpleMailMessage reviewerReturnEmail(
			 Employee reviewerInfo, String period, Employee staffInfo
			) {
		
		String message = staffInfo.getFirst_name().toUpperCase()+ " " + staffInfo.getLast_name().toUpperCase() +" evaluationed for the period  "+period+ " had been sent back \n Please click the below link  \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login "; //"Dear "+ supervisor.getFirst_name().toUpperCase()+";\n " + supervisor.getLast_name().toUpperCase() +" your DOMINIC SUPERVISOR performance plan for the period  "+period+" had been successfully submitted.\n Please click the below link \n\n https://eservices.lra.gov.lr/PerformanceAppraisal/login ";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(reviewerInfo.getEmail());
		email.setSubject("Electronic Performance Plan Management System - Submitted Plan");
		email.setText(message);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}*/
	
	
	//--------------------------------------------------------------
	
	//staff approve email
	/*public SimpleMailMessage approveStaffEmail(
			String contextPath, Employee employee, String period
			) {
		
		String url = contextPath;
		String message = "\n"+employee.getFirst_name().toUpperCase()+ " " + employee.getLast_name().toUpperCase() +" your performance plan for the perion "+period+" have been approved and is now sent for review."
				+ "\n\nKind Regards,\nLRA-HR Division\nMISD INOVATION\n\n";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(employee.getEmail());
		email.setSubject("Electronic Performance Plan Management System - Approve Plan");
		email.setText(message+url);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}*/
	
	//supervisor approve email
	/*public SimpleMailMessage approveSupervisorEmail(
			String contextPath, Employee employee, Employee supervisor, String period
			) {
		
		String url = contextPath;
		String message = "\n You have successfully approved "+ employee.getFirst_name().toUpperCase()+ " " + employee.getLast_name().toUpperCase() + " performance plan for the period "+period+" for review."
				+ "\n\nKind Regards,\nLRA-HR Division\nMISD INOVATION\n\n";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(supervisor.getEmail());
		email.setSubject("Electronic Performance Plan Management System - Approve Plan");
		email.setText(message+url);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}*/
	
	//reviewer approve email
	/*public SimpleMailMessage approveReviewerEmail(
			String contextPath, Employee employee, Employee reviewer, String period
			) {
		
		String url = contextPath;
		String message = "\n"+employee.getFirst_name().toUpperCase()+ " " + employee.getLast_name().toUpperCase() +" performance plan for the period "+period+" had been sent to you for review. Please click on the below link to work on it \n"+contextPath
				+ "\n\nKind Regards,\nLRA-HR Division\nMISD INOVATION\n\n";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(reviewer.getEmail());
		email.setSubject("Electronic Performance Plan Management System - Approve Plan");
		email.setText(message+url);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}*/
	
	//------------Boss section------------------------------------------------------------------------------------------
	
	/*public SimpleMailMessage confirmStaffEmail(
			String contextPath, Employee employee, String period
			) {
		
		String url = contextPath;
		String message = "\n"+employee.getFirst_name().toUpperCase()+ " " + employee.getLast_name().toUpperCase() +" your performance plan for the period "+period+ " had been reviewed and submitted to HR.  Congratulations"
				+ "\n\nKind Regards,\nLRA-HR Division\nMISD INOVATION\n\n";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(employee.getEmail());
		email.setSubject("Electronic Performance Plan Management System - Reviewer Approved Plan");
		email.setText(message+url);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}
	
	public SimpleMailMessage confirmSupervisorEmail(
			String contextPath, Employee employee, Employee supervisor, String period
			) {
		
		String url = contextPath;
		String message = "\n"+employee.getFirst_name().toUpperCase()+ " " + employee.getLast_name().toUpperCase() +" performance plan for the period " +period+" had been reviewed and submitted ot HR.  Congratulations"
				+ "\n\nKind Regards,\nLRA-HR Division\nMISD INOVATION\n\n";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(supervisor.getEmail());
		email.setSubject("Electronic Performance Plan Management System - Reviewer Approved Plan");
		email.setText(message+url);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}
	
	public SimpleMailMessage confirmReviewerEmail(
			String contextPath, Employee employee, Employee reviewer, String period
			) {
		
		String url = contextPath;
		String message = "\n"+employee.getFirst_name().toUpperCase()+ " " + employee.getLast_name().toUpperCase() +" performance plan for the period "+period+" had been successfully submitted to HR.  Thank you."
				+ "\n\nKind Regards,\nLRA-HR Division\nMISD INOVATION\n\n";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(reviewer.getEmail());
		email.setSubject("Electronic Performance Plan Management System - Reviewer Approved Plan");
		email.setText(message+url);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}
	
	//---------------------------------------------------
	
	public SimpleMailMessage reviewRenturnedEmail(
			String contextPath, Employee employee
			) {
		
		String url = contextPath;
		String message = "\n"+employee.getFirst_name().toUpperCase() +", your performance plan was returned by your reviewer.Kindly log in using above link, adjust and re-submit this plan within time."
				+ "\n\nKind Regards,\nLRA-HR Division";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(employee.getEmail());
		email.setSubject("Electronic Performance Plan Management System - Reviewer Returned Plan");
		email.setText(url+message);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}
	
	public SimpleMailMessage hrNotificationEmail(
			String contextPath, Employee employee, Employee supervisor
			) {
		
		String url = contextPath;
		String message = "\n"+employee.getFirst_name().toUpperCase() +", your performance plan was returned by your reviewer.Kindly log in using above link, adjust and re-submit this plan within time."
				+ "\n\nKind Regards,\nLRA-HR Division";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(supervisor.getEmail());
		email.setSubject("Electronic Performance Plan Management System - Reviewer Returned Plan");
		email.setText(url+message);
		email.setFrom(env.getProperty("support.email"));
		return email;	
	}*/
	
	}

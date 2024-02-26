package com.appraisal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Staffcompetency {

		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		
		private Long id;
		private String userid;
		private String staffid;
		private String label1;
		private String label2;
		private String label3;
		private String label4;
		private String label5;
		private String label6;
		private String label7;
		private String label8;
		private String label9;
		private String label10;
		private String period;
		private String competencyYear;
		private int level;
		
		public Staffcompetency() {
			super();
		}

		public Staffcompetency(Long id, String userid, String staffid, String label1, String label2, String label3,
				String label4, String label5, String label6, String label7, String label8, String label9,
				String label10, String period, String competencyYear, int level) {
			super();
			this.id = id;
			this.userid = userid;
			this.staffid = staffid;
			this.label1 = label1;
			this.label2 = label2;
			this.label3 = label3;
			this.label4 = label4;
			this.label5 = label5;
			this.label6 = label6;
			this.label7 = label7;
			this.label8 = label8;
			this.label9 = label9;
			this.label10 = label10;
			this.period = period;
			this.competencyYear = competencyYear;
			this.level = level;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUserid() {
			return userid;
		}

		public void setUserid(String userid) {
			this.userid = userid;
		}

		public String getStaffid() {
			return staffid;
		}

		public void setStaffid(String staffid) {
			this.staffid = staffid;
		}

		public String getLabel1() {
			return label1;
		}

		public void setLabel1(String label1) {
			this.label1 = label1;
		}

		public String getLabel2() {
			return label2;
		}

		public void setLabel2(String label2) {
			this.label2 = label2;
		}

		public String getLabel3() {
			return label3;
		}

		public void setLabel3(String label3) {
			this.label3 = label3;
		}

		public String getLabel4() {
			return label4;
		}

		public void setLabel4(String label4) {
			this.label4 = label4;
		}

		public String getLabel5() {
			return label5;
		}

		public void setLabel5(String label5) {
			this.label5 = label5;
		}

		public String getLabel6() {
			return label6;
		}

		public void setLabel6(String label6) {
			this.label6 = label6;
		}

		public String getLabel7() {
			return label7;
		}

		public void setLabel7(String label7) {
			this.label7 = label7;
		}

		public String getLabel8() {
			return label8;
		}

		public void setLabel8(String label8) {
			this.label8 = label8;
		}

		public String getLabel9() {
			return label9;
		}

		public void setLabel9(String label9) {
			this.label9 = label9;
		}

		public String getLabel10() {
			return label10;
		}

		public void setLabel10(String label10) {
			this.label10 = label10;
		}

		public String getPeriod() {
			return period;
		}

		public void setPeriod(String period) {
			this.period = period;
		}

		public String getCompetencyYear() {
			return competencyYear;
		}

		public void setCompetencyYear(String competencyYear) {
			this.competencyYear = competencyYear;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		
}

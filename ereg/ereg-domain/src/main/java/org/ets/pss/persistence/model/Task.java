package org.ets.pss.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the TSK database table.
 * 
 */
@Entity
@Table(name="TSK")
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TSK_ID", nullable=false)
	private long taskId;

	@Column(name="TTL_NAM_1", nullable=false)
	private String title;
	
	
	@Column(name="INSTR_TXT")
	private String instructions;

	@Temporal(TemporalType.DATE)
	@Column(name="TSK_CLOS_DTE", nullable=false)
	private Date taskCloseDate;

	@Temporal(TemporalType.DATE)
	@Column(name="TSK_OPN_DTE", nullable=false)
	private Date taskOpenDate;

	@Column(name="TSK_SEQ", nullable=false)
	private Integer displaySequence;

	
	//bi-directional many-to-one association to CustCr
	@OneToMany(mappedBy="tsk")
	private Set<CustCr> custCrs;

	//bi-directional many-to-one association to Frm
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FRM_ID", nullable=false)
	private Frm frm;

	//bi-directional many-to-one association to Tst
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="TST_ID", nullable=false)
	private Test test;

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public Date getTaskCloseDate() {
		return taskCloseDate;
	}

	public void setTaskCloseDate(Date taskCloseDate) {
		this.taskCloseDate = taskCloseDate;
	}

	public Date getTaskOpenDate() {
		return taskOpenDate;
	}

	public void setTaskOpenDate(Date taskOpenDate) {
		this.taskOpenDate = taskOpenDate;
	}


	public Integer getDisplaySequence() {
		return displaySequence;
	}

	public void setDisplaySequence(Integer displaySequence) {
		this.displaySequence = displaySequence;
	}

	public Set<CustCr> getCustCrs() {
		return custCrs;
	}

	public void setCustCrs(Set<CustCr> custCrs) {
		this.custCrs = custCrs;
	}

	public Frm getFrm() {
		return frm;
	}

	public void setFrm(Frm frm) {
		this.frm = frm;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}





}
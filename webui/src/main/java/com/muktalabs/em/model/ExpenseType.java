package com.muktalabs.em.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the expense_type database table.
 * 
 */
@Entity
@Table(name="expense_type")
@NamedQuery(name="ExpenseType.findAll", query="SELECT e FROM ExpenseType e")
public class ExpenseType extends Model implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="expense_type_id")
	private String expenseTypeId;

	private String description;

	@Column(name="expense_type")
	private String expenseType;

	public ExpenseType() {
	}

	public String getExpenseTypeId() {
		return this.expenseTypeId;
	}

	public void setExpenseTypeId(String expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExpenseType() {
		return this.expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

}
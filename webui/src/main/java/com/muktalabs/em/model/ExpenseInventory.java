package com.muktalabs.em.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the expense_inventory database table.
 * 
 */
@Entity
@Table(name="expense_inventory")
@NamedQuery(name="ExpenseInventory.findAll", query="SELECT e FROM ExpenseInventory e")
public class ExpenseInventory extends Model implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="inventory_id")
	private String inventoryId;

	@Column(name="company_id")
	private String companyId;

	private int cost;

	private String description;

	@Column(name="expense_type_id")
	private String expenseTypeId;

	@Column(name="payment_mode")
	private String paymentMode;

	@Temporal(TemporalType.DATE)
	@Column(name="transaction_date")
	private Date transactionDate;

	@Column(name="transaction_type")
	private String transactionType;

	@Column(name="user_id")
	private String userId;

	@Column(name="voucher_no")
	private int voucherNo;

	public ExpenseInventory() {
	}

	public String getInventoryId() {
		return this.inventoryId;
	}

	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public int getCost() {
		return this.cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExpenseTypeId() {
		return this.expenseTypeId;
	}

	public void setExpenseTypeId(String expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public String getPaymentMode() {
		return this.paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getVoucherNo() {
		return this.voucherNo;
	}

	public void setVoucherNo(int voucherNo) {
		this.voucherNo = voucherNo;
	}

}
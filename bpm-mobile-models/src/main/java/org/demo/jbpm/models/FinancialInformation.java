package org.demo.jbpm.models;

/**
 * This class was automatically generated by the data modeler tool.
 */


public class FinancialInformation implements java.io.Serializable {

  static final long serialVersionUID = 1L;

  private java.lang.Long id;
  private java.lang.Boolean hasForeseeableFinancialChanges;
  private java.lang.Boolean nonBankDebtObligationFlag;
  private java.util.List<org.demo.jbpm.models.ExpenseDetails> expenseDetails;
  private java.util.List<org.demo.jbpm.models.IncomeDetails> incomeDetails;
  private java.util.List<org.demo.jbpm.models.ExistingLoanDetails> existingLoanDetails;

  public FinancialInformation() {}

  public java.lang.Long getId() {
    return this.id;
  }

  public void setId(java.lang.Long id) {
    this.id = id;
  }

  public java.lang.Boolean getHasForeseeableFinancialChanges() {
    return this.hasForeseeableFinancialChanges;
  }

  public void setHasForeseeableFinancialChanges(java.lang.Boolean hasForeseeableFinancialChanges) {
    this.hasForeseeableFinancialChanges = hasForeseeableFinancialChanges;
  }

  public java.lang.Boolean getNonBankDebtObligationFlag() {
    return this.nonBankDebtObligationFlag;
  }

  public void setNonBankDebtObligationFlag(java.lang.Boolean nonBankDebtObligationFlag) {
    this.nonBankDebtObligationFlag = nonBankDebtObligationFlag;
  }

  public java.util.List<org.demo.jbpm.models.ExpenseDetails> getExpenseDetails() {
    return this.expenseDetails;
  }

  public void setExpenseDetails(java.util.List<org.demo.jbpm.models.ExpenseDetails> expenseDetails) {
    this.expenseDetails = expenseDetails;
  }

  public java.util.List<org.demo.jbpm.models.IncomeDetails> getIncomeDetails() {
    return this.incomeDetails;
  }

  public void setIncomeDetails(java.util.List<org.demo.jbpm.models.IncomeDetails> incomeDetails) {
    this.incomeDetails = incomeDetails;
  }

  public java.util.List<org.demo.jbpm.models.ExistingLoanDetails> getExistingLoanDetails() {
    return this.existingLoanDetails;
  }

  public void setExistingLoanDetails(java.util.List<org.demo.jbpm.models.ExistingLoanDetails> existingLoanDetails) {
    this.existingLoanDetails = existingLoanDetails;
  }

  public FinancialInformation(java.lang.Long id, 
      java.lang.Boolean hasForeseeableFinancialChanges,
      java.lang.Boolean nonBankDebtObligationFlag, 
      java.util.List<org.demo.jbpm.models.ExpenseDetails> expenseDetails,
      java.util.List<org.demo.jbpm.models.IncomeDetails> incomeDetails,
      java.util.List<org.demo.jbpm.models.ExistingLoanDetails> existingLoanDetails) {
    this.id = id;
    this.hasForeseeableFinancialChanges = hasForeseeableFinancialChanges;
    this.nonBankDebtObligationFlag = nonBankDebtObligationFlag;
    this.expenseDetails = expenseDetails;
    this.incomeDetails = incomeDetails;
    this.existingLoanDetails = existingLoanDetails;
  }

}
package org.demo.jbpm.models;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class CreditDetails implements java.io.Serializable
{

   static final long serialVersionUID = 1L;

   private java.lang.Long id;

   private java.lang.Integer creditAmount;

   private java.lang.Boolean loanTakenIndicator;

   private java.lang.Integer monthlyRepaymentForAllExtLoans;

   public CreditDetails()
   {
   }

   public java.lang.Long getId()
   {
      return this.id;
   }

   public void setId(java.lang.Long id)
   {
      this.id = id;
   }

   public java.lang.Integer getCreditAmount()
   {
      return this.creditAmount;
   }

   public void setCreditAmount(java.lang.Integer creditAmount)
   {
      this.creditAmount = creditAmount;
   }

   public java.lang.Boolean getLoanTakenIndicator()
   {
      return this.loanTakenIndicator;
   }

   public void setLoanTakenIndicator(java.lang.Boolean loanTakenIndicator)
   {
      this.loanTakenIndicator = loanTakenIndicator;
   }

   public java.lang.Integer getMonthlyRepaymentForAllExtLoans()
   {
      return this.monthlyRepaymentForAllExtLoans;
   }

   public void setMonthlyRepaymentForAllExtLoans(
         java.lang.Integer monthlyRepaymentForAllExtLoans)
   {
      this.monthlyRepaymentForAllExtLoans = monthlyRepaymentForAllExtLoans;
   }

   public CreditDetails(java.lang.Long id, java.lang.Integer creditAmount,
         java.lang.Boolean loanTakenIndicator,
         java.lang.Integer monthlyRepaymentForAllExtLoans)
   {
      this.id = id;
      this.creditAmount = creditAmount;
      this.loanTakenIndicator = loanTakenIndicator;
      this.monthlyRepaymentForAllExtLoans = monthlyRepaymentForAllExtLoans;
   }

}

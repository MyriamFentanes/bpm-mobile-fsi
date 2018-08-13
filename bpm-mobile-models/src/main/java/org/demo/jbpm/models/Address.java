package org.demo.jbpm.models;

public class Address implements java.io.Serializable {

  static final long serialVersionUID = 1L;

  private java.lang.Long id;
  private java.lang.String addressType;
  private java.lang.String addressLine1;
  private java.lang.String addressLine2;
  private java.lang.String addressLine3;
  private java.lang.String addressLine4;
  private java.lang.String countryCode;

  public Address() {}

  public java.lang.Long getId() {
    return this.id;
  }

  public void setId(java.lang.Long id) {
    this.id = id;
  }

  public java.lang.String getAddressType() {
    return this.addressType;
  }

  public void setAddressType(java.lang.String addressType) {
    this.addressType = addressType;
  }

  public java.lang.String getAddressLine1() {
    return this.addressLine1;
  }

  public void setAddressLine1(java.lang.String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public java.lang.String getAddressLine2() {
    return this.addressLine2;
  }

  public void setAddressLine2(java.lang.String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public java.lang.String getAddressLine3() {
    return this.addressLine3;
  }

  public void setAddressLine3(java.lang.String addressLine3) {
    this.addressLine3 = addressLine3;
  }

  public java.lang.String getAddressLine4() {
    return this.addressLine4;
  }

  public void setAddressLine4(java.lang.String addressLine4) {
    this.addressLine4 = addressLine4;
  }

  public java.lang.String getCountryCode() {
    return this.countryCode;
  }

  public void setCountryCode(java.lang.String countryCode) {
    this.countryCode = countryCode;
  }

  public Address(java.lang.Long id, java.lang.String addressType, java.lang.String addressLine1, java.lang.String addressLine2,
      java.lang.String addressLine3, java.lang.String addressLine4, java.lang.String countryCode) {
    this.id = id;
    this.addressType = addressType;
    this.addressLine1 = addressLine1;
    this.addressLine2 = addressLine2;
    this.addressLine3 = addressLine3;
    this.addressLine4 = addressLine4;
    this.countryCode = countryCode;
  }

  public Address(java.lang.String addressType, java.lang.String addressLine1, java.lang.String addressLine2, java.lang.String addressLine3,
      java.lang.String addressLine4) {
    this.addressType = addressType;
    this.addressLine1 = addressLine1;
    this.addressLine2 = addressLine2;
    this.addressLine3 = addressLine3;
    this.addressLine4 = addressLine4;
  }

}

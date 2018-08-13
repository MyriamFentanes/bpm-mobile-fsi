package org.demo.jbpm.models;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class Demographics implements java.io.Serializable {

  static final long serialVersionUID = 1L;

  private java.lang.Long id;
  private java.lang.String gender;
  private java.util.Date dateOfBirth;
  private java.lang.String birthPlace;
  private java.lang.String countryOfBirth;
  private java.lang.String nationality;

  public Demographics() {}

  public java.lang.Long getId() {
    return this.id;
  }

  public void setId(java.lang.Long id) {
    this.id = id;
  }

  public java.lang.String getGender() {
    return this.gender;
  }

  public void setGender(java.lang.String gender) {
    this.gender = gender;
  }

  public java.util.Date getDateOfBirth() {
    return this.dateOfBirth;
  }

  public void setDateOfBirth(java.util.Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public java.lang.String getBirthPlace() {
    return this.birthPlace;
  }

  public void setBirthPlace(java.lang.String birthPlace) {
    this.birthPlace = birthPlace;
  }

  public java.lang.String getCountryOfBirth() {
    return this.countryOfBirth;
  }

  public void setCountryOfBirth(java.lang.String countryOfBirth) {
    this.countryOfBirth = countryOfBirth;
  }

  public java.lang.String getNationality() {
    return this.nationality;
  }

  public void setNationality(java.lang.String nationality) {
    this.nationality = nationality;
  }

  public Demographics(java.lang.Long id, java.lang.String gender, java.util.Date dateOfBirth, java.lang.String birthPlace,
      java.lang.String countryOfBirth, java.lang.String nationality) {
    this.id = id;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.birthPlace = birthPlace;
    this.countryOfBirth = countryOfBirth;
    this.nationality = nationality;
  }

}

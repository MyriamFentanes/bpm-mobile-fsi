package org.demo.jbpm.models;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class PersonalDetails implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private org.demo.jbpm.models.Name name;
	private org.demo.jbpm.models.Demographics demographics;
	private java.util.List<org.demo.jbpm.models.Address> address;
	private org.demo.jbpm.models.Email email;
	private org.demo.jbpm.models.Phone phone;
	private String testPersonals;

	public PersonalDetails() {
	}

	public org.demo.jbpm.models.Name getName() {
		return this.name;
	}

	public void setName(org.demo.jbpm.models.Name name) {
		this.name = name;
	}

	public org.demo.jbpm.models.Demographics getDemographics() {
		return this.demographics;
	}

	public void setDemographics(org.demo.jbpm.models.Demographics demographics) {
		this.demographics = demographics;
	}

	public java.util.List<org.demo.jbpm.models.Address> getAddress() {
		return this.address;
	}

	public void setAddress(java.util.List<org.demo.jbpm.models.Address> address) {
		this.address = address;
	}

	public org.demo.jbpm.models.Email getEmail() {
		return this.email;
	}

	public void setEmail(org.demo.jbpm.models.Email email) {
		this.email = email;
	}

	public org.demo.jbpm.models.Phone getPhone() {
		return this.phone;
	}

	public void setPhone(org.demo.jbpm.models.Phone phone) {
		this.phone = phone;
	}

	public PersonalDetails(org.demo.jbpm.models.Name name, org.demo.jbpm.models.Demographics demographics,
			java.util.List<org.demo.jbpm.models.Address> address, org.demo.jbpm.models.Email email,
			org.demo.jbpm.models.Phone phone) {
		this.name = name;
		this.demographics = demographics;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	public String getTestPersonals() {
		return testPersonals;
	}

	public void setTestPersonals(String testPersonals) {
		this.testPersonals = testPersonals;
	}

}

package in.savitar.smjewelsadmin.ModalClasses;

public class PlanListModal {

    String Name;
    String Phone;
    String Email;
    String PAN;
    String Aadhar;
    String DOB;
    String MarriageDate;
    String Nominee;
    String NomineeRelationship;
    String NomineePhone;
    String Address;
    String PinCode;
    String ProfilePhoto;
    Long ID;
    String Collector_ID;

    public String getCollector_ID() {
        return Collector_ID;
    }

    public void setCollector_ID(String collector_ID) {
        Collector_ID = collector_ID;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public PlanListModal(String name, String phone, String email, String PAN, String aadhar, String DOB, String marriageDate, String nominee, String nomineeRelationship, String nomineePhone, String address, String pinCode, String profilePhoto,
                         Long iD, String collector_ID) {
        Name = name;
        Phone = phone;
        Email = email;
        this.PAN = PAN;
        Aadhar = aadhar;
        this.DOB = DOB;
        MarriageDate = marriageDate;
        Nominee = nominee;
        NomineeRelationship = nomineeRelationship;
        NomineePhone = nomineePhone;
        Address = address;
        PinCode = pinCode;
        ProfilePhoto = profilePhoto;
        ID = iD;
        this.Collector_ID = collector_ID;
    }

    public PlanListModal() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public String getAadhar() {
        return Aadhar;
    }

    public void setAadhar(String aadhar) {
        Aadhar = aadhar;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getMarriageDate() {
        return MarriageDate;
    }

    public void setMarriageDate(String marriageDate) {
        MarriageDate = marriageDate;
    }

    public String getNominee() {
        return Nominee;
    }

    public void setNominee(String nominee) {
        Nominee = nominee;
    }

    public String getNomineeRelationship() {
        return NomineeRelationship;
    }

    public void setNomineeRelationship(String nomineeRelationship) {
        NomineeRelationship = nomineeRelationship;
    }

    public String getNomineePhone() {
        return NomineePhone;
    }

    public void setNomineePhone(String nomineePhone) {
        NomineePhone = nomineePhone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }
}

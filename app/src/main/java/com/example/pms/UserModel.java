package com.example.pms;

public class UserModel {
    private int UserPic;
    private String SerialNo;
    private String PassNo;
    private String AdhaarCardNo;
    private String ElectionId;
    private String Name;
    private String FatherName;
    private int Age;
    private int Sex;
    private String PermanentAddress;
    private String TempAddress;
    private String MobileNumber;
    private byte[] DpThumbnail;

    public UserModel(int userPic, String serialNo, String passNo, String adhaarCardNo,
                     String electionId, String name, String fatherName, int age, int sex,
                     String permanentAddress, String tempAddress, String mobileNumber,
                     byte[] dpThumbnail) {
        UserPic = userPic;
        SerialNo = serialNo;
        PassNo = passNo;
        AdhaarCardNo = adhaarCardNo;
        ElectionId = electionId;
        Name = name;
        FatherName = fatherName;
        Age = age;
        Sex = sex;
        PermanentAddress = permanentAddress;
        TempAddress = tempAddress;
        MobileNumber = mobileNumber;
        DpThumbnail = dpThumbnail;
    }

    public UserModel(int userPic, String serialNo, String passNo, String adhaarCardNo,
                     String electionId, String name, String fatherName, int age, int sex,
                     String permanentAddress, String tempAddress, String mobileNumber) {
        UserPic = userPic;
        SerialNo = serialNo;
        PassNo = passNo;
        AdhaarCardNo = adhaarCardNo;
        ElectionId = electionId;
        Name = name;
        FatherName = fatherName;
        Age = age;
        Sex = sex;
        PermanentAddress = permanentAddress;
        TempAddress = tempAddress;
        MobileNumber = mobileNumber;
    }

    public int getUserPic() {
        return UserPic;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public String getPassNo() {
        return PassNo;
    }

    public String getAdhaarCardNo() {
        return AdhaarCardNo;
    }

    public String getElectionId() {
        return ElectionId;
    }

    public String getName() {
        return Name;
    }

    public String getFatherName() {
        return FatherName;
    }

    public int getAge() {
        return Age;
    }

    public int getSex() {
        return Sex;
    }

    public String getPermanentAddress() {
        return PermanentAddress;
    }

    public String getTempAddress() {
        return TempAddress;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public byte[] getDpThumbnail() {
        return DpThumbnail;
    }

    /* SETTER */
    public void setUserPic(int userPic) {
        UserPic = userPic;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public void setPassNo(String passNo) {
        PassNo = passNo;
    }

    public void setAdhaarCardNo(String adhaarCardNo) {
        AdhaarCardNo = adhaarCardNo;
    }

    public void setElectionId(String electionId) {
        ElectionId = electionId;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public void setAge(int age) {
        Age = age;
    }

    public void setSex(int sex) {
        Sex = sex;
    }

    public void setPermanentAddress(String permanentAddress) {
        PermanentAddress = permanentAddress;
    }

    public void setTempAddress(String tempAddress) {
        TempAddress = tempAddress;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public void setDpThumbnail(byte[] dpThumbnail) {
        DpThumbnail = dpThumbnail;
    }
}

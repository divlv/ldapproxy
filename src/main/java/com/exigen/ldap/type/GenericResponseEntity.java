package com.exigen.ldap.type;

import java.io.InputStream;
import java.util.Properties;

public class GenericResponseEntity {

    private int errorCode;
    private String buildNumber = "";
    private String buildTime = "";
    private String errorTxt;
    private String errorType;
    private Object data;
    private Object orgUnit;

    /**
     * Loading Web Service build date and number from properties file
     */
    private void loadVersionFromPropertiesFile() {

        Properties prop = new Properties();
        InputStream input = null;
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            input = classloader.getResourceAsStream("version.properties");
            prop.load(input);
            buildNumber = prop.getProperty("buildversion");
            buildTime = prop.getProperty("buildtime");
        } catch (Exception ex) {
            // Cannot load from properties
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    // Cannot close file
                }
            }
        }
    }

    public GenericResponseEntity(int errorCode, String errorTxt, String errorType, Object data) {
        this();
        this.errorCode = errorCode;
        this.errorTxt = errorTxt;
        this.errorType = errorType;
        this.data = data;
    }

    public GenericResponseEntity(int errorCode, String errorTxt, String errorType) {
        this();
        this.errorCode = errorCode;
        this.errorTxt = errorTxt;
        this.errorType = errorType;
    }

    public GenericResponseEntity(Object data) {
        this();
        this.data = data;
    }

    public GenericResponseEntity() {
        loadVersionFromPropertiesFile();
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorTxt() {
        return errorTxt;
    }

    public void setErrorTxt(String errorTxt) {
        this.errorTxt = errorTxt;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(Object orgUnit) {
        this.orgUnit = orgUnit;
    }
}

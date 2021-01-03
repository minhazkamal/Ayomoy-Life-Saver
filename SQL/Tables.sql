DROP USER als;

CREATE USER als identified by iutcse18;
GRANT DBA TO als;

DISCONNECT;

CONN als/iutcse18;

DROP TABLE REQUEST;

CREATE TABLE REQUEST
(
	ID NUMBER,
	USERNAME VARCHAR2(20),
	BLOOD_GROUP VARCHAR2(4),
	LOCATION VARCHAR2(20),
	LOCATION_DETAILS VARCHAR(250),
	COMPLICATIONS VARCHAR2(50),
	QUANTITY NUMBER,
	APPROX_DATE DATE,
	PATIENT VARCHAR2(50),
	P_GENDER VARCHAR2(10),
	EM_MOBILE VARCHAR2(12),
	CONSTRAINT REQUEST_PK PRIMARY KEY (ID, USERNAME)
);

DROP SEQUENCE REQUEST_SEQUENCE;
CREATE SEQUENCE REQUEST_SEQUENCE;

CREATE OR REPLACE TRIGGER REQUEST_ON_INSERT
BEFORE INSERT ON REQUEST
FOR EACH ROW
BEGIN
SELECT REQUEST_SEQUENCE.NEXTVAL
INTO :NEW.ID
FROM DUAL;
END;
/

DROP TABLE LOCATIONS;

CREATE TABLE LOCATIONS
(
	NAME VARCHAR2(20) PRIMARY KEY
);

INSERT INTO LOCATIONS VALUES ('MIRPUR');
INSERT INTO LOCATIONS VALUES ('UTTARA');
INSERT INTO LOCATIONS VALUES ('BANANI');
INSERT INTO LOCATIONS VALUES ('GULSHAN');
INSERT INTO LOCATIONS VALUES ('DHANMONDI');
INSERT INTO LOCATIONS VALUES ('BARIDHARA');
INSERT INTO LOCATIONS VALUES ('MOGBAZAR');
INSERT INTO LOCATIONS VALUES ('KAMLAPUR');
INSERT INTO LOCATIONS VALUES ('MOTIJHEEL');
INSERT INTO LOCATIONS VALUES ('FARMGATE');
INSERT INTO LOCATIONS VALUES ('SHAHABAG');
INSERT INTO LOCATIONS VALUES ('NEW MARKET');
INSERT INTO LOCATIONS VALUES ('AZIMPUR');
INSERT INTO LOCATIONS VALUES ('MOHAMMADPUR');
INSERT INTO LOCATIONS VALUES ('GULISTAN');
INSERT INTO LOCATIONS VALUES ('JATRABARI');
INSERT INTO LOCATIONS VALUES ('BASHUNDHARA');

DROP TABLE REGISTRATION;

CREATE TABLE REGISTRATION
(
    USERNAME VARCHAR2(20) PRIMARY KEY,
    PASSWORD VARCHAR2(20),
    USER_TYPE VARCHAR2(15)
);

INSERT INTO REGISTRATION VALUES ('admin', 'admin', 'Admin');

DROP TABLE PERSONAL_INFO;

CREATE TABLE PERSONAL_INFO
(
    USERNAME VARCHAR2(20) PRIMARY KEY,
    NAME VARCHAR2(50),
    MOBILE VARCHAR2(12),
    EMAIL VARCHAR2(50),
    DOB DATE,
    LOCATION VARCHAR2(15),
    GENDER VARCHAR2(10),
    BLOOD_GROUP VARCHAR2(4)
);

DROP TABLE ORG_INFO;

CREATE TABLE ORG_INFO
(
    USERNAME VARCHAR2(20) PRIMARY KEY,
    NAME VARCHAR2(100),
    CONTACT VARCHAR2(12),
    EMAIL VARCHAR2(50),
    LOCATION VARCHAR2(15),
    LIC_NO VARCHAR2(50),
    CONTACT_PERSON VARCHAR2(50),
    CP_MOBILE VARCHAR2(12),
    DESCRIPTION VARCHAR2(250)
);


CREATE OR REPLACE PROCEDURE delete_unwanted AS
BEGIN
    DELETE FROM REGISTRATION WHERE USERNAME NOT IN (SELECT USERNAME FROM PERSONAL_INFO union SELECT USERNAME FROM ORG_INFO) AND USER_TYPE != 'Admin';
    COMMIT;
END delete_unwanted;
/

BEGIN
    DBMS_SCHEDULER.CREATE_JOB
        (
            JOB_NAME => 'Delete_Unwanted_Job',
            JOB_TYPE => 'STORED_PROCEDURE',
            JOB_ACTION => 'delete_unwanted',
            REPEAT_INTERVAL => 'FREQ=MINUTELY;INTERVAL=15',
            AUTO_DROP => FALSE,
            COMMENTS => 'Delete_job'
        );
END;
/

EXEC DBMS_SCHEDULER.ENABLE('Delete_Unwanted_Job');

---FOR REMOVING SCHEDULED JOB
/*
BEGIN
    dbms_scheduler.drop_job(job_name => 'Delete_Unwanted_Job');
END;
/
 */


/*
SELECT * FROM REGISTRATION;
SELECT * FROM PERSONAL_INFO;
SELECT * FROM ORG_INFO;
*/

CREATE OR REPLACE FUNCTION findDeleteReqID(name_in IN varchar2, rnum_in IN number)
RETURN NUMBER
IS
    REQ_ID NUMBER;

BEGIN
    SELECT ID
    INTO REQ_ID
    FROM (SELECT ID, ROWNUM AS RN FROM REQUEST WHERE USERNAME=name_in) WHERE RN=rnum_in;

    RETURN REQ_ID;
END;
/

DROP TABLE DONOR_INFO;

CREATE TABLE DONOR_INFO
(
    USERNAME VARCHAR2(20) PRIMARY KEY,
    ELIGIBILITY VARCHAR2(12) DEFAULT 'Ineligible',
    ACTIVENESS VARCHAR2(10) DEFAULT 'Inactive',
    PAYABLE VARCHAR2(9) DEFAULT 'NON-Paid'
);

---INSERT INTO DONOR_INFO (USERNAME) SELECT USERNAME FROM PERSONAL_INFO;

DROP TABLE DONATION_INFO;

CREATE TABLE DONATION_INFO
(
    USERNAME VARCHAR2(20),
    LOCATION VARCHAR2(15),
    P_NAME VARCHAR2(100),
    P_GENDER VARCHAR2(10),
    COMPLICATIONS VARCHAR2(50),
    P_CONTACT VARCHAR2(12),
    D_DATE DATE,
    LOCATION_DETAILS VARCHAR2(250),
    PRIMARY KEY (USERNAME, D_DATE)
);

DROP TABLE TEST_REPORTS;

CREATE TABLE TEST_REPORTS
(
    USERNAME VARCHAR2(20),
    REPORT BLOB,
    REPORT_NAME VARCHAR2(200),
    REPORT_PATH VARCHAR2(1000),
    DOS DATE,
    R_STATUS VARCHAR2(8) CHECK(R_STATUS IN ('Valid','Invalid')),
    APPROVAL VARCHAR2(10) CHECK(APPROVAL IN ('Approved', 'Rejected')),
    CHECKER VARCHAR2(20),
    DOC DATE,
    COMMENTS VARCHAR2(250)
);

---INSERT INTO TEST_REPORTS (USERNAME) SELECT USERNAME FROM PERSONAL_INFO;

CREATE OR REPLACE PROCEDURE updateTestReports(name_in IN varchar2)

IS

BEGIN
    UPDATE TEST_REPORTS
        SET R_STATUS=NULL, APPROVAL=NULL, CHECKER=NULL, DOC=NULL, COMMENTS=NULL
    WHERE USERNAME=name_in;

    UPDATE DONOR_INFO SET ELIGIBILITY='Ineligible' WHERE USERNAME=name_in;
END;
/

DROP TABLE ORG_LIC_INFO;

CREATE TABLE ORG_LIC_INFO
(
    USERNAME VARCHAR2(20),
    ELIGIBILITY VARCHAR2(15) DEFAULT 'Ineligible',
    LIC BLOB,
    LIC_NAME VARCHAR2(200),
    LIC_PATH VARCHAR2(1000),
    DOS DATE,
    L_STATUS VARCHAR2(8) CHECK(L_STATUS IN ('Valid','Invalid')),
    APPROVAL VARCHAR2(10) CHECK(APPROVAL IN ('Approved', 'Rejected')),
    CHECKER VARCHAR2(20),
    DOC DATE,
    COMMENTS VARCHAR2(250)
);

---INSERT INTO ORG_LIC_INFO (USERNAME) SELECT USERNAME FROM ORG_INFO;
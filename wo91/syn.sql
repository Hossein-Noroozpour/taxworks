grant select,insert,update,delete on STL.AT_TC03_LETTER to STLRT;
create or replace synonym STLRT.AT_TC03_LETTER for STL.AT_TC03_LETTER ;

grant select,insert,update,delete on STL.TC01_LETTER_TEMPLATE to STLRT;
create or replace synonym STLRT.TC01_LETTER_TEMPLATE for STL.TC01_LETTER_TEMPLATE ;

grant select,insert,update,delete on STL.TC03_LETTER to STLRT;
create or replace synonym STLRT.TC03_LETTER for STL.TC03_LETTER ;

grant select,insert,update,delete on STL.TC04_LETTER_CONTENT to STLRT;
create or replace synonym STLRT.TC04_LETTER_CONTENT for STL.TC04_LETTER_CONTENT ;

grant select,insert,update,delete on STL.TC05_LETTER_OACASE to STLRT;
create or replace synonym STLRT.TC05_LETTER_OACASE for STL.TC05_LETTER_OACASE;

grant SELECT,DELETE,INSERT,UPDATE on STL.TC00_TEC_LETTER_LOG to STLRT;
create or replace synonym STLRT.TC00_TEC_LETTER_LOG for STL.TC00_TEC_LETTER_LOG;

grant select on FRAMEWORK.STD_CODES to STLRT;
create or replace synonym STLRT.STD_CODES for FRAMEWORK.STD_CODES;

grant select on FRAMEWORK.STD_CODES_DESC to STLRT;
create or replace synonym STLRT.STD_CODES_DESC for FRAMEWORK.STD_CODES_DESC;

grant select on FRAMEWORK.STD_CODES_PROP to STLRT;
create or replace synonym STLRT.STD_CODES_PROP for FRAMEWORK.STD_CODES_PROP;

grant select on CMN.ATTACHMENT to STLRT;
create or replace synonym STLRT.ATTACHMENT for  CMN.ATTACHMENT;

grant select on REG.TAXPAYER to STLRT;
create or replace synonym STLRT.TAXPAYER for  REG.TAXPAYER;

grant select on REG.TR01_TAXPAYER to STLRT;
create or replace synonym STLRT.TR01_TAXPAYER for REG.TR01_TAXPAYER;

grant select on REG.TR04_NATURAL_PERSON to STLRT;
create or replace synonym STLRT.TR04_NATURAL_PERSON for REG.TR04_NATURAL_PERSON;

grant select on REG.TR06_BRANCH_BUSI_ACTIVITY to STLRT;
create or replace synonym STLRT.TR06_BRANCH_BUSI_ACTIVITY for REG.TR06_BRANCH_BUSI_ACTIVITY;

grant select on REG.TR10_LEGAL_PERSON to STLRT;
create or replace synonym STLRT.TR10_LEGAL_PERSON for REG.TR10_LEGAL_PERSON;

grant select on REG.TR11_NATURAL_PER_NAME to STLRT;
create or replace synonym STLRT.TR11_NATURAL_PER_NAME for REG.TR11_NATURAL_PER_NAME;

grant select on REG.TR13_LEGAL_PER_NAME to STLRT;
create or replace synonym STLRT.TR13_LEGAL_PER_NAME for REG.TR13_LEGAL_PER_NAME;

grant select on REG.TR16_TAXPAYER_OFFICE to STLRT;
create or replace synonym STLRT.TR16_TAXPAYER_OFFICE for REG.TR16_TAXPAYER_OFFICE;

grant select on REG.TR17_BRANCH_NAME to STLRT;
create or replace synonym STLRT.TR17_BRANCH_NAME for REG.TR17_BRANCH_NAME;

grant select on REG.TR19_BRANCH_PHYSICAL_ADDR to STLRT;
create or replace synonym STLRT.TR19_BRANCH_PHYSICAL_ADDR for REG.TR19_BRANCH_PHYSICAL_ADDR;

grant select on REG.TR20_BRANCH_POSTAL_ADDR to STLRT;
create or replace synonym STLRT.TR20_BRANCH_POSTAL_ADDR for REG.TR20_BRANCH_POSTAL_ADDR;

grant select on REG.TR25_OFFICIALS to STLRT;
create or replace synonym STLRT.TR25_OFFICIALS for REG.TR25_OFFICIALS;

grant select on RET.TA02_RETURNS to STLRT;
create or replace synonym STLRT.TAX_RETURNS for RET.TA02_RETURNS;
create or replace synonym STLRT.TA02_RETURNS for RET.TA02_RETURNS;

grant select on RET.TA03_RET_CONTENT to STLRT;
create or replace synonym STLRT.TA03_RET_CONTENT for RET.TA03_RET_CONTENT;

grant select on ADM.TS04_OFFICE to STLRT;
create or replace synonym STLRT.TS04_OFFICE for ADM.TS04_OFFICE;

grant select on ADM.TS07_OFFICE_RELATIONSHIP to STLRT;
create or replace synonym STLRT.TS07_OFFICE_RELATIONSHIP for  ADM.TS07_OFFICE_RELATIONSHIP;

grant select on OBJ.TO02_REQUEST_DETAILS to STLRT;
create or replace synonym STLRT.TO02_REQUEST_DETAILS for OBJ.TO02_REQUEST_DETAILS;

grant select on TAC.TT01_TRANSACTIONS to STLRT;
create or replace synonym STLRT.TT01_TRANSACTIONS for TAC.TT01_TRANSACTIONS;

grant select on TAC.TT28_PENALTY to STLRT;
create or replace synonym STLRT.TT28_PENALTY for TAC.TT28_PENALTY;

grant select on FRAMEWORK.STD_CODES to STL;
create or replace synonym STL.STD_CODES for FRAMEWORK.STD_CODES;

grant select on FRAMEWORK.STD_CODES_DESC to STL;
create or replace synonym STL.STD_CODES_DESC for FRAMEWORK.STD_CODES_DESC;

grant select on FRAMEWORK.STD_CODES_PROP to STL;
create or replace synonym  STL.STD_CODES_PROP for FRAMEWORK.STD_CODES_PROP;

grant select on CMN.ATTACHMENT to STL;
create or replace synonym STL.ATTACHMENT for CMN.ATTACHMENT;

grant select on REG.TAXPAYER to STL;
create or replace synonym STL.TAXPAYER for REG.TAXPAYER;

grant select on REG.TR01_TAXPAYER to STL;
create or replace synonym STL.TR01_TAXPAYER for REG.TR01_TAXPAYER;

grant select on REG.TR04_NATURAL_PERSON to STL;
create or replace synonym STL.TR04_NATURAL_PERSON for REG.TR04_NATURAL_PERSON;

grant select on REG.TR06_BRANCH_BUSI_ACTIVITY to STL;
create or replace synonym STL.TR06_BRANCH_BUSI_ACTIVITY for REG.TR06_BRANCH_BUSI_ACTIVITY;

grant select on REG.TR10_LEGAL_PERSON to STL;
create or replace synonym STL.TR10_LEGAL_PERSON for REG.TR10_LEGAL_PERSON;

grant select on REG.TR11_NATURAL_PER_NAME to STL;
create or replace synonym STL.TR11_NATURAL_PER_NAME for REG.TR11_NATURAL_PER_NAME;

grant select on REG.TR13_LEGAL_PER_NAME to STL;
create or replace synonym STL.TR13_LEGAL_PER_NAME for REG.TR13_LEGAL_PER_NAME;

grant select on REG.TR16_TAXPAYER_OFFICE to STL;
create or replace synonym STL.TR16_TAXPAYER_OFFICE for REG.TR16_TAXPAYER_OFFICE;

grant select on REG.TR17_BRANCH_NAME to STL;
create or replace synonym STL.TR17_BRANCH_NAME for REG.TR17_BRANCH_NAME;

grant select on REG.TR19_BRANCH_PHYSICAL_ADDR to STL;
create or replace synonym STL.TR19_BRANCH_PHYSICAL_ADDR for REG.TR19_BRANCH_PHYSICAL_ADDR;

grant select on REG.TR20_BRANCH_POSTAL_ADDR to STL;
create or replace synonym STL.TR20_BRANCH_POSTAL_ADDR for REG.TR20_BRANCH_POSTAL_ADDR;

grant select on REG.TR25_OFFICIALS to STL;
create or replace synonym STL.TR25_OFFICIALS for REG.TR25_OFFICIALS;

grant select on RET.TA02_RETURNS to STL;
create or replace synonym STL.TAX_RETURNS for RET.TA02_RETURNS;

grant select on RET.TA03_RET_CONTENT to STL;
create or replace synonym STL.TA03_RET_CONTENT for RET.TA03_RET_CONTENT;

grant select on ADM.TS04_OFFICE to STL;
create or replace synonym STL.TS04_OFFICE for ADM.TS04_OFFICE;

grant select on ADM.TS07_OFFICE_RELATIONSHIP to STL;
create or replace synonym STL.TS07_OFFICE_RELATIONSHIP for ADM.TS07_OFFICE_RELATIONSHIP;

grant select on OBJ.TO02_REQUEST_DETAILS to STL;
create or replace synonym STL.TO02_REQUEST_DETAILS for OBJ.TO02_REQUEST_DETAILS;

grant select on TAC.TT01_TRANSACTIONS to STL;
create or replace synonym STL.TT01_TRANSACTIONS for TAC.TT01_TRANSACTIONS;

grant select on TAC.TT28_PENALTY to STL;
create or replace synonym STL.TT28_PENALTY for TAC.TT28_PENALTY;
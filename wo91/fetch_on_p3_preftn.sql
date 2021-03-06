SELECT
     MAX( TC03.CC03_ID ) PREVIOUS_FTN_CC03_ID,
     MAX( TC03.CC03_CREATE_ON ) PREFTNCC03CREON
FROM
     TC03_LETTER TC03
JOIN TA02_RETURNS SRET2 ON
     TC03.CR01_INTERNAL_ID = #internal_id:String#
     AND TC03.CR03_BRANCH_CODE = SRET2.CR03_BRANCH_CODE
     AND TC03.CC03_YEAR = SRET2.CA02_TAX_YEAR
     AND TC03.CC03_FILING_PERIOD = SRET2.CA02_TAX_PERIOD
     AND TC03.CA09_TAX_TYPE_CODE = SRET2.CA09_TAX_TYPE_CODE
     AND SRET2.CA02_RETURN_ID = #return_id:String#
     AND TC03.CSTD_LETTER_TYPE = 'LTR_EF02'
     AND TC03.CC03_ID < #letter_id:String#
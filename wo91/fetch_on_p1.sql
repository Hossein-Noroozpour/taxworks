SELECT
     RET2.CA02_RETURN_ID RET2_CA02_RETURN_ID,
     ADM4.CS04_NAME ADM4_CS04_NAME,
     ADM4.CS04_PHONE ADM4_CS04_PHONE,
     ADM4.CS04_POSTAL_ADR ADM4_CS04_POSTAL_ADR,
     GETGTOFROMOFFICEID(REG16.CS04_ID) GTO_REG16_CS04_ID,
     REG10.CR10_REG_NUMBER REG10_CR10_REG_NUM,
     REG11.CR11_FIRST_NAME REG11_CR11_FIRST_NAME,
     REG11.CR11_LAST_NAME REG11_CR11_LAST_NAME,
     REG13.CR13_NAME REG13_CR13_NAME,
     REG13.CR13_TRADE_NAME REG13_CR13_TRADE_NAME,
     REG1.CR01_NATURAL_PER_FLAG REG1CR01NATPERFLAG,
     REG1.CR01_TIN_ID REG1_CR01_TIN_ID,
     REG20.CR20_POSTAL_ADDRESS REG20CR20POSTADDR,
     REG25.CR25_FIXED_PHONE REG25_CR25_FIXED_PHONE,
     REG4.CR04_NATIONAL_ID REG4_CR04_NATIONAL_ID,
     REG6.CSTD_ACTIVITY_CODE REG6_CSTD_ACTIVITY_CODE,
     RET2.CA02_TAX_YEAR RET2CA02TAXYEAR,
     RET2.CSTD_RETURN_TYPE RET2CSTDRETTYPE,
     RET2.CA02_RETURN_VERSION RET2_CA02_RETURN_VERSION,
     TO_CHAR( RET2.CA02_TAX_PERIOD_FROM, 'yyyy/mm/dd', 'NLS_CALENDAR=Persian' ) RET2_CA02_TAX_PERIOD_FROM,
     TO_CHAR( RET2.CA02_TAX_PERIOD_TO, 'yyyy/mm/dd', 'NLS_CALENDAR=Persian' ) RET2_CA02_TAX_PERIOD_TO,
     RET3.CA03_RETURN_CONTENT RET3_CA03_RETCNT,
     TO_CHAR( SYSDATE, 'yyyy/mm/dd', 'NLS_CALENDAR=Persian' ) SYSDATEPERSIAN
FROM
     (
          SELECT *
          FROM TA02_RETURNS
          WHERE CA02_RETURN_ID = #return_id:String#
               AND CA02_RETURN_VERSION = #return_version:String#
     )RET2
LEFT JOIN TA03_RET_CONTENT RET3 ON
     RET3.CA02_RETURN_ID = RET2.CA02_RETURN_ID
     AND RET3.CA02_RETURN_VERSION = RET2.CA02_RETURN_VERSION
LEFT JOIN TR01_TAXPAYER REG1 ON
     REG1.CR01_INTERNAL_ID = RET2.CR01_INTERNAL_ID
LEFT JOIN TR04_NATURAL_PERSON REG4 ON
     REG1.CR04_NATURAL_PER_ID IS NOT NULL
     AND REG1.CR04_NATURAL_PER_ID = REG4.CR04_NATURAL_PER_ID
LEFT JOIN TR06_BRANCH_BUSI_ACTIVITY REG6 ON
     RET2.CR01_INTERNAL_ID = REG6.CR01_INTERNAL_ID
     AND RET2.CR03_BRANCH_CODE = REG6.CR03_BRANCH_CODE
     AND REG6.CR06_END_DATE IS NULL
LEFT JOIN TR10_LEGAL_PERSON REG10 ON
     REG1.CR10_LEGAL_PER_ID IS NOT NULL
     AND REG1.CR10_LEGAL_PER_ID = REG10.CR10_LEGAL_PER_ID
LEFT JOIN TR11_NATURAL_PER_NAME REG11 ON
     REG1.CR04_NATURAL_PER_ID = REG11.CR04_NATURAL_PER_ID
     AND REG11.CR11_END_DATE IS NULL
LEFT JOIN TR13_LEGAL_PER_NAME REG13 ON
     REG1.CR10_LEGAL_PER_ID = REG13.CR10_LEGAL_PER_ID
     AND REG13.CR13_END_DATE IS NULL
LEFT JOIN TR16_TAXPAYER_OFFICE REG16 ON
     REG1.CR01_INTERNAL_ID = REG16.CR01_INTERNAL_ID
     AND REG16.CR16_END_DATE IS NULL
LEFT JOIN TR17_BRANCH_NAME REG17 ON
     REG17.CR17_END_DATE IS NULL
     AND REG17.CR01_INTERNAL_ID = RET2.CR01_INTERNAL_ID
     AND RET2.CR03_BRANCH_CODE = REG17.CR03_BRANCH_CODE
LEFT JOIN TR19_BRANCH_PHYSICAL_ADDR REG19 ON
     RET2.CR01_INTERNAL_ID = REG19.CR01_INTERNAL_ID
     AND RET2.CR03_BRANCH_CODE = REG19.CR03_BRANCH_CODE
     AND REG19.CR19_END_DATE IS NULL
LEFT JOIN TR20_BRANCH_POSTAL_ADDR REG20 ON
     RET2.CR01_INTERNAL_ID = REG20.CR01_INTERNAL_ID
     AND RET2.CR03_BRANCH_CODE = REG20.CR03_BRANCH_CODE
     AND REG20.CR20_END_DATE IS NULL
LEFT JOIN TR25_OFFICIALS REG25 ON
     RET2.CR01_INTERNAL_ID = REG25.CR01_INTERNAL_ID
     AND RET2.CR03_BRANCH_CODE = REG25.CR03_BRANCH_CODE
LEFT JOIN TS04_OFFICE ADM4 ON
     ADM4.CS04_ID = REG16.CS04_ID
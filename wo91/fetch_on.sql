SELECT
     RET2.CSTD_RETURN_TYPE RET2CSTDRETTYPE,
     RET2.CA02_TAX_YEAR RET2CA02TAXYEAR,
     TO_CHAR( SYSDATE, 'yyyy/mm/dd', 'NLS_CALENDAR=Persian' ) SYSDATEPERSIAN,
     REG1.CR01_TIN_ID REG1_CR01_TIN_ID,
     REG13.CR13_TRADE_NAME REG13_CR13_TRADE_NAME,
     REG4.CR04_NATIONAL_ID REG4_CR04_NATIONAL_ID,
     '' GTO_REG16_CS04_ID,
     ADM4.CS04_NAME ADM4_CS04_NAME,
     ADM4.CS04_PHONE ADM4_CS04_PHONE,
     ADM4.CS04_POSTAL_ADR ADM4_CS04_POSTAL_ADR,
     REG20.CR20_POSTAL_ADDRESS REG20CR20POSTADDR,
     REG11.CR11_FIRST_NAME REG11_CR11_FIRST_NAME,
     REG11.CR11_LAST_NAME REG11_CR11_LAST_NAME,
     RET2.CA02_RETURN_ID RET2_CA02_RETURN_ID,
     RET2.CA02_RETURN_VERSION RET2_CA02_RETURN_VERSION,
     TO_CHAR( RET2.CA02_TAX_PERIOD_TO, 'yyyy/mm/dd', 'NLS_CALENDAR=Persian' ) RET2_CA02_TAX_PERIOD_TO,
     TO_CHAR( RET2.CA02_TAX_PERIOD_FROM, 'yyyy/mm/dd', 'NLS_CALENDAR=Persian' ) RET2_CA02_TAX_PERIOD_FROM,
     REG1.CR01_NATURAL_PER_FLAG REG1_CR01_NATURAL_PER_FLAG,
     REG10.CR10_REG_NUMBER REG10_CR10_REG_NUMBER,
     REG13.CR13_NAME REG13_CR13_NAME,
     REG6.CSTD_ACTIVITY_CODE REG6_CSTD_ACTIVITY_CODE,
     REG25.CR25_FIXED_PHONE REG25_CR25_FIXED_PHONE,
     RET3.CA03_RETURN_CONTENT RET3_CA03_RETCNT,
     ISSUANCE.CC03_ID ISSUANCE_CC03_ID,
     ISSUANCE.CC03_CREATE_ON ISSCC03CREON,
     PREVIOUS_FTN.CC03_ID PREVIOUS_FTN_CC03_ID,
     PREVIOUS_FTN.CC03_CREATE_ON PREFTNCC03CREON,
     PAID_TAX_FRM24.AMT_NUMBER PAIDTAXFRM24AMTNUM,
     PAID_TAX.AVAILABLE_AMT PAIDTAXAVIAMT,
     SUB_TAC_CE07_SUM.UNPAID_AMT SUBTACCE07SUMUNPAMT,
     SUB_TAC_CE06_SUM.UNPAID_AMT SUBTACCE06SUMDISUNPAMT,
     SUB_TAC_CE06_DISPLAY_FLAG.UNPAID_AMT SUBTACCE06DISFGUNPAMT,
     SUB_UNPAID_FLAG.AMT_NUMBER SUBUNPFGAMTNUM,
     SUB_REWARDS_TOTAL.AMT_NUMBER SUBREWTTLAMTNUM,
     SUB_PENALTIES_TOTAL.AMT_NUMBER SUBPENTTLAMTNUM
FROM
     TA02_RETURNS RET2
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
LEFT JOIN(
          SELECT
               MAX( CC03_ID ) CC03_ID,
               MAX( CC03_CREATE_ON ) CC03_CREATE_ON,
               CR01_INTERNAL_ID
          FROM
               (
                    SELECT
                         CC03_ID,
                         CC03_CREATE_ON,
                         CR01_INTERNAL_ID
                    FROM
                         TC03_LETTER STL3
                    JOIN TO02_REQUEST_DETAILS OBJ2 ON
                         STL3.CC03_ENTITY_ID = OBJ2.CO01_REQUEST_NO
                         AND OBJ2.CA02_RETURN_ID = #return_id:String#
                    WHERE
                         STL3.CSTD_ENTITY = 'OBJ'
                         AND STL3.CSTD_LETTER_TYPE IN(
                              'LTR_OA01',
                              'LTR_OA02',
                              'LTR_OA12'
                         )
               UNION SELECT
                         CC03_ID,
                         CC03_CREATE_ON,
                         CR01_INTERNAL_ID
                    FROM
                         TC03_LETTER STL3
                    WHERE
                         STL3.CC03_ENTITY_ID = #return_id:String# || '-' || #return_version:String#
                         AND STL3.CSTD_LETTER_TYPE IN(
                              'LTR_RP12',
                              'LTR_AC07'
                         )
               )
          GROUP BY
               CR01_INTERNAL_ID
     ) ISSUANCE ON
     RET2.CR01_INTERNAL_ID = ISSUANCE.CR01_INTERNAL_ID,
     (
          SELECT
               MAX( TC03.CC03_ID ) CC03_ID,
               MAX( TC03.CC03_CREATE_ON ) CC03_CREATE_ON
          FROM
               TC03_LETTER TC03
          JOIN TA02_RETURNS SRET2 ON
               TC03.CR01_INTERNAL_ID = SRET2.CR01_INTERNAL_ID
               AND TC03.CR03_BRANCH_CODE = SRET2.CR03_BRANCH_CODE
               AND TC03.CC03_YEAR = SRET2.CA02_TAX_YEAR
               AND TC03.CC03_FILING_PERIOD = SRET2.CA02_TAX_PERIOD
               AND TC03.CA09_TAX_TYPE_CODE = SRET2.CA09_TAX_TYPE_CODE
               AND SRET2.CA02_RETURN_ID = #return_id:String#
               AND TC03.CSTD_LETTER_TYPE = 'LTR_EF02'
               AND TC03.CC03_ID < #letter_id:String#
     ) PREVIOUS_FTN,
     (
          SELECT
               SUM( AVAILABLE_AMT ) AMT_NUMBER
          FROM
               (
                    SELECT
                         AMOUNT AS AVAILABLE_AMT
                    FROM
                         (
                              SELECT
                                   SUM( AMOUNT ) AMOUNT
                              FROM
                                   (
                                        SELECT
                                             CASE
                                                  WHEN TT01.CSTD_DC = 'DT' THEN 0 - TT01.CT01_AMOUNT
                                                  ELSE TT01.CT01_AMOUNT
                                             END AMOUNT
                                        FROM
                                             TT01_TRANSACTIONS TT01
                                        JOIN STD_CODES ON
                                             TT01.CSTD_TRAN_TYPE = INTERNAL_CODE
                                             AND GROUP_CODE = 'TRANSACTION_TYPE'
                                             AND PARENT_INTERNAL_CODE IN(
                                                  'PA',
                                                  'RL'
                                             )
                                        JOIN TA02_RETURNS TA02 ON
                                             TA02.CA02_RETURN_ID = #return_id:String#
                                             AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                             AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                             AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                             AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                             AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                             AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                        WHERE
                                             TT01.CT01_REVERSED_FLAG = 'N'
                                             AND TT01.CT01_CLEARED_FLAG = 'Y'
                                             AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                             AND TT01.CT01_VALUE_DATE <= SYSDATE
                                             AND TT01.CSTD_LIABILITY_TYPE <> 'ASSLS'
                                             AND TT01.CSTD_LIABILITY_TYPE <> 'RET186N2'
                                   UNION ALL SELECT
                                             CASE
                                                  WHEN TT01.CSTD_DC = 'DT' THEN 0 - TT01.CT01_AMOUNT
                                                  ELSE TT01.CT01_AMOUNT
                                             END AMOUNT
                                        FROM
                                             TT01_TRANSACTIONS TT01
                                        JOIN STD_CODES ON
                                             TT01.CSTD_TRAN_TYPE = INTERNAL_CODE
                                             AND GROUP_CODE = 'TRANSACTION_TYPE'
                                             AND PARENT_INTERNAL_CODE IN(
                                                  'PA',
                                                  'RL'
                                             )
                                        WHERE
                                             TT01.CT01_REVERSED_FLAG = 'N'
                                             AND TT01.CT01_CLEARED_FLAG = 'Y'
                                             AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                             AND TT01.CT01_VALUE_DATE <= SYSDATE
                                             AND TT01.CSTD_LIABILITY_TYPE <> 'ASSLS'
                                             AND TT01.CSTD_LIABILITY_TYPE <> 'RET186N2'
                                             AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                             AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                   ) TAB1
                         ) TAB
                    WHERE
                         TAB.AMOUNT > 0
               ) AVAILABLE
     ) PAID_TAX_FRM24,
     (
          SELECT
               SUM( AVAILABLE_AMT ) AVAILABLE_AMT
          FROM
               (
                    SELECT
                         AMOUNT AS AVAILABLE_AMT
                    FROM
                         (
                              SELECT
                                   SUM( AMOUNT ) AMOUNT
                              FROM
                                   (
                                        SELECT
                                             CASE
                                                  WHEN TT01.CSTD_DC = 'DT' THEN 0 - TT01.CT01_AMOUNT
                                                  ELSE TT01.CT01_AMOUNT
                                             END AMOUNT
                                        FROM
                                             TT01_TRANSACTIONS TT01
                                        JOIN STD_CODES STD0 ON
                                             TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE
                                             AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'
                                             AND STD0.PARENT_INTERNAL_CODE IN(
                                                  'PA',
                                                  'RL'
                                             )
                                        JOIN TA02_RETURNS TA02 ON
                                             TA02.CA02_RETURN_ID = #return_id:String#
                                             AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                             AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                             AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                             AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                             AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                             AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                        WHERE
                                             TT01.CT01_REVERSED_FLAG = 'N'
                                             AND TT01.CT01_CLEARED_FLAG = 'Y'
                                             AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                             AND TT01.CT01_VALUE_DATE <= SYSDATE
                                             AND TT01.CSTD_LIABILITY_TYPE <> 'ASSLS'
                                   UNION ALL SELECT
                                             CASE
                                                  WHEN TT01.CSTD_DC = 'DT' THEN 0 - TT01.CT01_AMOUNT
                                                  ELSE TT01.CT01_AMOUNT
                                             END AMOUNT
                                        FROM
                                             TT01_TRANSACTIONS TT01
                                        JOIN STD_CODES STD0 ON
                                             TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE
                                             AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'
                                             AND STD0.PARENT_INTERNAL_CODE IN(
                                                  'PA',
                                                  'RL'
                                             )
                                        WHERE
                                             TT01.CT01_REVERSED_FLAG = 'N'
                                             AND TT01.CT01_CLEARED_FLAG = 'Y'
                                             AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                             AND TT01.CT01_VALUE_DATE <= SYSDATE
                                             AND TT01.CSTD_LIABILITY_TYPE <> 'ASSLS'
                                             AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                             AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                   ) TAB1
                         ) TAB
                    WHERE
                         TAB.AMOUNT > 0
               ) AVAILABLE
     ) PAID_TAX,
     (
          SELECT
               SUM( AVAILABLE_AMT ) UNPAID_AMT
          FROM
               (
                    SELECT
                         CT01_DESC AS CODE_DESC,
                         AMOUNT AS AVAILABLE_AMT
                    FROM
                         (
                              SELECT
                                   CT01_DESC,
                                   SUM( AMOUNT ) AMOUNT
                              FROM
                                   (
                                        SELECT
                                             TT01.CT01_DESC,
                                             CASE
                                                  WHEN TT01.CSTD_DC = 'DT' THEN 0 - TT01.CT01_AMOUNT
                                                  ELSE TT01.CT01_AMOUNT
                                             END AMOUNT
                                        FROM
                                             TT01_TRANSACTIONS TT01,
                                             TAX_RETURNS TA02
                                        WHERE
                                             TT01.CT01_VALUE_DATE <= SYSDATE
                                             AND TT01.CT01_REVERSED_FLAG = 'N'
                                             AND TT01.CT01_CLEARED_FLAG = 'Y'
                                             AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                             AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'
                                             AND TT01.CSTD_TRAN_TYPE IN(
                                                  SELECT
                                                       INTERNAL_CODE
                                                  FROM
                                                       STD_CODES
                                                  WHERE
                                                       GROUP_CODE = 'TRANSACTION_TYPE'
                                                       AND PARENT_INTERNAL_CODE IN(
                                                            'TA',
                                                            'PA',
                                                            'RL'
                                                       )
                                             )
                                             AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                             AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                             AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                             AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                             AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                             AND TA02.CA02_RETURN_ID = #return_id:String#
                                             AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                   UNION ALL SELECT
                                             TT01.CT01_DESC,
                                             CASE
                                                  WHEN TT01.CSTD_DC = 'DT' THEN 0 - TT01.CT01_AMOUNT
                                                  ELSE TT01.CT01_AMOUNT
                                             END AMOUNT
                                        FROM
                                             TT01_TRANSACTIONS TT01
                                        WHERE
                                             TT01.CT01_VALUE_DATE <= SYSDATE
                                             AND TT01.CT01_REVERSED_FLAG = 'N'
                                             AND TT01.CT01_CLEARED_FLAG = 'Y'
                                             AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                             AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'
                                             AND TT01.CSTD_TRAN_TYPE IN(
                                                  SELECT
                                                       INTERNAL_CODE
                                                  FROM
                                                       STD_CODES
                                                  WHERE
                                                       GROUP_CODE = 'TRANSACTION_TYPE'
                                                       AND PARENT_INTERNAL_CODE IN(
                                                            'TA',
                                                            'PA',
                                                            'RL'
                                                       )
                                             )
                                             AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                             AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                   ) TAB1
                              GROUP BY
                                   CT01_DESC
                         ) TAB
                    WHERE
                         TAB.AMOUNT > 0
               ) AVAILABLE
     ) SUB_TAC_CE07_SUM,
     (
          SELECT
               SUM( UNPAID_AMT ) UNPAID_AMT
          FROM
               (
                    SELECT
                         SUM( UNPAID_AMT ) UNPAID_AMT,
                         CODE_DESC
                    FROM
                         (
                              SELECT
                                   UNPAID_AMT,
                                   CODE_DESC
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC AS CODE_DESC,
                                             AMOUNT AS UNPAID_AMT
                                        FROM
                                             (
                                                  SELECT
                                                       CT01_DESC,
                                                       SUM( AMOUNT ) AMOUNT
                                                  FROM
                                                       (
                                                            SELECT
                                                                 TT01.CT01_DESC,
                                                                 CASE
                                                                      WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                                      ELSE TT01.CT01_AMOUNT
                                                                 END AMOUNT
                                                            FROM
                                                                 TT01_TRANSACTIONS TT01
                                                            JOIN STD_CODES STD0 ON
                                                                 TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE
                                                                 AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND STD0.PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                            JOIN TA02_RETURNS TA02 ON
                                                                 TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                                                 AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                                                 AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                                                 AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                                                 AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                                                 AND TA02.CA02_RETURN_ID = #return_id:String#
                                                                 AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                                            WHERE
                                                                 TT01.CT01_REVERSED_FLAG = 'N'
                                                                 AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                                 AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                                 AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                                 AND TT01.CSTD_LIABILITY_TYPE IN(
                                                                      'LFPEN',
                                                                      'LRPEN',
                                                                      'LPPEN'
                                                                 )
                                                                 AND(
                                                                      TT01.CSTD_ENTITY <> 'PEN'
                                                                      OR(
                                                                           TT01.CT01_ENTITY_ID IN(
                                                                                SELECT
                                                                                     TT28.CT28_ID
                                                                                FROM
                                                                                     TT28_PENALTY TT28
                                                                                WHERE
                                                                                     TT28.CSTD_ENTITY = 'RET'
                                                                                     AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                                     AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                           )
                                                                      )
                                                                 )
                                                       UNION ALL SELECT
                                                                 TT01.CT01_DESC,
                                                                 CASE
                                                                      WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                                      ELSE TT01.CT01_AMOUNT
                                                                 END AMOUNT
                                                            FROM
                                                                 TT01_TRANSACTIONS TT01
                                                            JOIN STD_CODES STD0 ON
                                                                 TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE
                                                                 AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND STD0.PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                            JOIN TA02_RETURNS TA02 ON
                                                                 TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                                                 AND TA02.CA02_RETURN_ID = #return_id:String#
                                                                 AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                                            WHERE
                                                                 TT01.CT01_REVERSED_FLAG = 'N'
                                                                 AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                                 AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                                 AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                                 AND TT01.CSTD_LIABILITY_TYPE IN(
                                                                      'LFPEN',
                                                                      'LRPEN',
                                                                      'LPPEN'
                                                                 )
                                                                 AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                                                 AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                                                 AND(
                                                                      TT01.CSTD_ENTITY <> 'PEN'
                                                                      OR(
                                                                           TT01.CT01_ENTITY_ID IN(
                                                                                SELECT
                                                                                     TT28.CT28_ID
                                                                                FROM
                                                                                     TT28_PENALTY TT28
                                                                                WHERE
                                                                                     TT28.CSTD_ENTITY = 'RET'
                                                                                     AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                                     AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                           )
                                                                      )
                                                                 )
                                                       ) TAB1
                                                  GROUP BY
                                                       CT01_DESC
                                             ) TAB
                                   ) UNPAID
                         UNION ALL SELECT
                                   UNPAID_AMT,
                                   CODE_DESC
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC AS CODE_DESC,
                                             AMOUNT AS UNPAID_AMT
                                        FROM
                                             (
                                                  SELECT
                                                       CT01_DESC,
                                                       SUM( AMOUNT ) AMOUNT
                                                  FROM
                                                       (
                                                            SELECT
                                                                 TT01.CT01_DESC,
                                                                 CASE
                                                                      WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                                      ELSE TT01.CT01_AMOUNT
                                                                 END AMOUNT
                                                            FROM
                                                                 TT01_TRANSACTIONS TT01
                                                            JOIN STD_CODES STD0 ON
                                                                 TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE
                                                                 AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND STD0.PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                            JOIN TA02_RETURNS TA02 ON
                                                                 TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                                                 AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                                                 AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                                                 AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                                                 AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                                                 AND TA02.CA02_RETURN_ID = #return_id:String#
                                                                 AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                                            WHERE
                                                                 TT01.CT01_REVERSED_FLAG = 'N'
                                                                 AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                                 AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                                 AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                                 AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'
                                                                 AND(
                                                                      TT01.CSTD_ENTITY <> 'PEN'
                                                                      OR(
                                                                           TT01.CT01_ENTITY_ID IN(
                                                                                SELECT
                                                                                     TT28.CT28_ID
                                                                                FROM
                                                                                     TT28_PENALTY TT28
                                                                                WHERE
                                                                                     TT28.CSTD_ENTITY = 'RET'
                                                                                     AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                                     AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                           )
                                                                      )
                                                                 )
                                                       UNION ALL SELECT
                                                                 TT01.CT01_DESC,
                                                                 CASE
                                                                      WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                                      ELSE TT01.CT01_AMOUNT
                                                                 END AMOUNT
                                                            FROM
                                                                 TT01_TRANSACTIONS TT01
                                                            JOIN STD_CODES STD0 ON
                                                                 TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE
                                                                 AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND STD0.PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                            JOIN TA02_RETURNS TA02 ON
                                                                 TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                                                 AND TA02.CA02_RETURN_ID = #return_id:String#
                                                                 AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                                            WHERE
                                                                 TT01.CT01_REVERSED_FLAG = 'N'
                                                                 AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                                 AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                                 AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                                 AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'
                                                                 AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                                                 AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                                                 AND(
                                                                      TT01.CSTD_ENTITY <> 'PEN'
                                                                      OR(
                                                                           TT01.CT01_ENTITY_ID IN(
                                                                                SELECT
                                                                                     TT28.CT28_ID
                                                                                FROM
                                                                                     TT28_PENALTY TT28
                                                                                WHERE
                                                                                     TT28.CSTD_ENTITY = 'RET'
                                                                                     AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                                     AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                           )
                                                                      )
                                                                 )
                                                       ) TAB1
                                                  GROUP BY
                                                       CT01_DESC
                                             ) TAB
                                   )
                         UNION ALL SELECT
                                   UNPAID_AMT,
                                   CODE_DESC
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC AS CODE_DESC,
                                             AMOUNT AS UNPAID_AMT
                                        FROM
                                             (
                                                  SELECT
                                                       CT01_DESC,
                                                       SUM( AMOUNT ) AMOUNT
                                                  FROM
                                                       (
                                                            SELECT
                                                                 TT01.CT01_DESC,
                                                                 CASE
                                                                      WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                                      ELSE TT01.CT01_AMOUNT
                                                                 END AMOUNT
                                                            FROM
                                                                 TT01_TRANSACTIONS TT01
                                                            JOIN STD_CODES STD0 ON
                                                                 TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE
                                                                 AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND STD0.PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                            JOIN TA02_RETURNS TA02 ON
                                                                 TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                                                 AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                                                 AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                                                 AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                                                 AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                                                 AND TA02.CA02_RETURN_ID = #return_id:String#
                                                                 AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                                            WHERE
                                                                 TT01.CT01_REVERSED_FLAG = 'N'
                                                                 AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                                 AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                                 AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                                 AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'
                                                       UNION ALL SELECT
                                                                 TT01.CT01_DESC,
                                                                 CASE
                                                                      WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                                      ELSE TT01.CT01_AMOUNT
                                                                 END AMOUNT
                                                            FROM
                                                                 TT01_TRANSACTIONS TT01
                                                            JOIN STD_CODES STD0 ON
                                                                 TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE
                                                                 AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND STD0.PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                            WHERE
                                                                 TT01.CT01_REVERSED_FLAG = 'N'
                                                                 AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                                 AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                                 AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                                 AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'
                                                                 AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                                                 AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                                       ) TAB1
                                                  GROUP BY
                                                       CT01_DESC
                                             ) TAB
                                   ) UNPAID
                         UNION ALL SELECT
                                   UNPAID_AMT,
                                   CODE_DESC
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC AS CODE_DESC,
                                             AMOUNT AS UNPAID_AMT
                                        FROM
                                             (
                                                  SELECT
                                                       CT01_DESC,
                                                       SUM( AMOUNT ) AMOUNT
                                                  FROM
                                                       (
                                                            SELECT
                                                                 TT01.CT01_DESC,
                                                                 CASE
                                                                      WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                                      ELSE TT01.CT01_AMOUNT
                                                                 END AMOUNT
                                                            FROM
                                                                 TT01_TRANSACTIONS TT01
                                                            JOIN STD_CODES STD0 ON
                                                                 TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE
                                                                 AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND STD0.PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                            WHERE
                                                                 TT01.CT01_REVERSED_FLAG = 'N'
                                                                 AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                                 AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                                 AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                                 AND TT01.CSTD_LIABILITY_TYPE IN(
                                                                      'PEN247',
                                                                      'PEN247D'
                                                                 )
                                                                 AND TT01.CSTD_ALLOCATED_ENTITY = 'OBJ'
                                                                 AND TT01.CT01_ALLOCATED_ENTITYID = #request_no:String#
                                                       ) TAB1
                                                  GROUP BY
                                                       CT01_DESC
                                             ) TAB
                                   )
                         )
                    GROUP BY
                         CODE_DESC
               )
     ) SUB_TAC_CE06_SUM,
     (
          SELECT
               SUM( UNPAID_AMT ) UNPAID_AMT,
               CODE_DESC
          FROM
               (
                    SELECT
                         UNPAID_AMT,
                         CODE_DESC
                    FROM
                         (
                              SELECT
                                   CT01_DESC AS CODE_DESC,
                                   AMOUNT AS UNPAID_AMT
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC,
                                             SUM( AMOUNT ) AMOUNT
                                        FROM
                                             (
                                                  SELECT
                                                       TT01.CT01_DESC,
                                                       CASE
                                                            WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                            ELSE TT01.CT01_AMOUNT
                                                       END AMOUNT
                                                  FROM
                                                       TT01_TRANSACTIONS TT01,
                                                       TA02_RETURNS TA02
                                                  WHERE
                                                       TT01.CT01_REVERSED_FLAG = 'N'
                                                       AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                       AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                       AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                       AND TT01.CSTD_LIABILITY_TYPE IN(
                                                            'LFPEN',
                                                            'LRPEN',
                                                            'LPPEN'
                                                       )
                                                       AND TT01.CSTD_TRAN_TYPE IN(
                                                            SELECT
                                                                 INTERNAL_CODE
                                                            FROM
                                                                 STD_CODES
                                                            WHERE
                                                                 GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                       )
                                                       AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                                       AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                                       AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                                       AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                                       AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                                       AND TA02.CA02_RETURN_ID = #return_id:String#
                                                       AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                                       AND(
                                                            TT01.CSTD_ENTITY <> 'PEN'
                                                            OR(
                                                                 TT01.CSTD_ENTITY = 'PEN'
                                                                 AND TT01.CT01_ENTITY_ID IN(
                                                                      SELECT
                                                                           TT28.CT28_ID
                                                                      FROM
                                                                           TT28_PENALTY TT28
                                                                      WHERE
                                                                           TT28.CSTD_ENTITY = 'RET'
                                                                           AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                           AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                 )
                                                            )
                                                       )
                                             UNION ALL SELECT
                                                       TT01.CT01_DESC,
                                                       CASE
                                                            WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                            ELSE TT01.CT01_AMOUNT
                                                       END AMOUNT
                                                  FROM
                                                       TT01_TRANSACTIONS TT01
                                                  WHERE
                                                       TT01.CR01_INTERNAL_ID = #internal_id:String#
                                                       AND TT01.CT01_REVERSED_FLAG = 'N'
                                                       AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                       AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                       AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                       AND TT01.CSTD_LIABILITY_TYPE IN(
                                                            'LFPEN',
                                                            'LRPEN',
                                                            'LPPEN'
                                                       )
                                                       AND TT01.CSTD_TRAN_TYPE IN(
                                                            SELECT
                                                                 INTERNAL_CODE
                                                            FROM
                                                                 STD_CODES
                                                            WHERE
                                                                 GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                       )
                                                       AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                                       AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                                       AND(
                                                            TT01.CSTD_ENTITY <> 'PEN'
                                                            OR(
                                                                 TT01.CSTD_ENTITY = 'PEN'
                                                                 AND TT01.CT01_ENTITY_ID IN(
                                                                      SELECT
                                                                           TT28.CT28_ID
                                                                      FROM
                                                                           TT28_PENALTY TT28
                                                                      WHERE
                                                                           TT28.CSTD_ENTITY = 'RET'
                                                                           AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                           AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                 )
                                                            )
                                                       )
                                             ) TAB1
                                        GROUP BY
                                             CT01_DESC
                                   ) TAB
                         ) UNPAID
               UNION ALL SELECT
                         UNPAID_AMT,
                         CODE_DESC
                    FROM
                         (
                              SELECT
                                   CT01_DESC AS CODE_DESC,
                                   AMOUNT AS UNPAID_AMT
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC,
                                             SUM( AMOUNT ) AMOUNT
                                        FROM
                                             (
                                                  SELECT
                                                       TT01.CT01_DESC,
                                                       CASE
                                                            WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                            ELSE TT01.CT01_AMOUNT
                                                       END AMOUNT
                                                  FROM
                                                       TT01_TRANSACTIONS TT01,
                                                       TA02_RETURNS TA02
                                                  WHERE
                                                       TT01.CT01_REVERSED_FLAG = 'N'
                                                       AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                       AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                       AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                       AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'
                                                       AND TT01.CSTD_TRAN_TYPE IN(
                                                            SELECT
                                                                 INTERNAL_CODE
                                                            FROM
                                                                 STD_CODES
                                                            WHERE
                                                                 GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                       )
                                                       AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                                       AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                                       AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                                       AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                                       AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                                       AND TA02.CA02_RETURN_ID = #return_id:String#
                                                       AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                                       AND(
                                                            TT01.CSTD_ENTITY <> 'PEN'
                                                            OR(
                                                                 TT01.CSTD_ENTITY = 'PEN'
                                                                 AND TT01.CT01_ENTITY_ID IN(
                                                                      SELECT
                                                                           TT28.CT28_ID
                                                                      FROM
                                                                           TT28_PENALTY TT28
                                                                      WHERE
                                                                           TT28.CSTD_ENTITY = 'RET'
                                                                           AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                           AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                 )
                                                            )
                                                       )
                                             UNION ALL SELECT
                                                       TT01.CT01_DESC,
                                                       CASE
                                                            WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                            ELSE TT01.CT01_AMOUNT
                                                       END AMOUNT
                                                  FROM
                                                       TT01_TRANSACTIONS TT01
                                                  WHERE
                                                       TT01.CR01_INTERNAL_ID = #internal_id:String#
                                                       AND TT01.CT01_REVERSED_FLAG = 'N'
                                                       AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                       AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                       AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                       AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'
                                                       AND TT01.CSTD_TRAN_TYPE IN(
                                                            SELECT
                                                                 INTERNAL_CODE
                                                            FROM
                                                                 STD_CODES
                                                            WHERE
                                                                 GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                       )
                                                       AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                                       AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                                       AND(
                                                            TT01.CSTD_ENTITY <> 'PEN'
                                                            OR(
                                                                 TT01.CSTD_ENTITY = 'PEN'
                                                                 AND TT01.CT01_ENTITY_ID IN(
                                                                      SELECT
                                                                           TT28.CT28_ID
                                                                      FROM
                                                                           TT28_PENALTY TT28
                                                                      WHERE
                                                                           TT28.CSTD_ENTITY = 'RET'
                                                                           AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                           AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                 )
                                                            )
                                                       )
                                             ) TAB1
                                        GROUP BY
                                             CT01_DESC
                                   ) TAB
                         )
               UNION ALL SELECT
                         UNPAID_AMT,
                         CODE_DESC
                    FROM
                         (
                              SELECT
                                   CT01_DESC AS CODE_DESC,
                                   AMOUNT AS UNPAID_AMT
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC,
                                             SUM( AMOUNT ) AMOUNT
                                        FROM
                                             (
                                                  SELECT
                                                       TT01.CT01_DESC,
                                                       CASE
                                                            WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                            ELSE TT01.CT01_AMOUNT
                                                       END AMOUNT
                                                  FROM
                                                       TT01_TRANSACTIONS TT01,
                                                       TA02_RETURNS TA02
                                                  WHERE
                                                       TT01.CT01_REVERSED_FLAG = 'N'
                                                       AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                       AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                       AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                       AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'
                                                       AND TT01.CSTD_TRAN_TYPE IN(
                                                            SELECT
                                                                 INTERNAL_CODE
                                                            FROM
                                                                 STD_CODES
                                                            WHERE
                                                                 GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                       )
                                                       AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                                       AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                                       AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                                       AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                                       AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                                       AND TA02.CA02_RETURN_ID = #return_id:String#
                                                       AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                             UNION ALL SELECT
                                                       TT01.CT01_DESC,
                                                       CASE
                                                            WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                            ELSE TT01.CT01_AMOUNT
                                                       END AMOUNT
                                                  FROM
                                                       TT01_TRANSACTIONS TT01
                                                  WHERE
                                                       TT01.CT01_REVERSED_FLAG = 'N'
                                                       AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                       AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                       AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                       AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'
                                                       AND TT01.CSTD_TRAN_TYPE IN(
                                                            SELECT
                                                                 INTERNAL_CODE
                                                            FROM
                                                                 STD_CODES
                                                            WHERE
                                                                 GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                       )
                                                       AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                                       AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                             ) TAB1
                                        GROUP BY
                                             CT01_DESC
                                   ) TAB
                         ) UNPAID
               UNION ALL SELECT
                         UNPAID_AMT,
                         CODE_DESC
                    FROM
                         (
                              SELECT
                                   CT01_DESC AS CODE_DESC,
                                   AMOUNT AS UNPAID_AMT
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC,
                                             SUM( AMOUNT ) AMOUNT
                                        FROM
                                             (
                                                  SELECT
                                                       TT01.CT01_DESC,
                                                       CASE
                                                            WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                            ELSE TT01.CT01_AMOUNT
                                                       END AMOUNT
                                                  FROM
                                                       TT01_TRANSACTIONS TT01
                                                  WHERE
                                                       TT01.CT01_REVERSED_FLAG = 'N'
                                                       AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                       AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                       AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                       AND TT01.CSTD_LIABILITY_TYPE IN(
                                                            'PEN247',
                                                            'PEN247D'
                                                       )
                                                       AND TT01.CSTD_TRAN_TYPE IN(
                                                            SELECT
                                                                 INTERNAL_CODE
                                                            FROM
                                                                 STD_CODES
                                                            WHERE
                                                                 GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                       )
                                                       AND TT01.CSTD_ALLOCATED_ENTITY = 'OBJ'
                                                       AND TT01.CT01_ALLOCATED_ENTITYID = #request_no:String#
                                             ) TAB1
                                        GROUP BY
                                             CT01_DESC
                                   ) TAB
                         )
               )
          GROUP BY
               CODE_DESC
     ) SUB_TAC_CE06_DISPLAY_FLAG,
     (
          SELECT
               SUM( UNPAID_AMT ),
               CODE_DESC
          FROM
               (
                    SELECT
                         UNPAID_AMT,
                         CODE_DESC
                    FROM
                         (
                              SELECT
                                   CT01_DESC AS CODE_DESC,
                                   AMOUNT AS UNPAID_AMT
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC,
                                             SUM( AMOUNT ) AMOUNT
                                        FROM
                                             (
                                                  SELECT
                                                       TT01.CT01_DESC,
                                                       CASE
                                                            WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                            ELSE TT01.CT01_AMOUNT
                                                       END AMOUNT
                                                  FROM
                                                       TT01_TRANSACTIONS TT01,
                                                       TA02_RETURNS TA02
                                                  WHERE
                                                       TT01.CT01_REVERSED_FLAG = 'N'
                                                       AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                       AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                       AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                       AND TT01.CSTD_LIABILITY_TYPE IN(
                                                            'LFPEN',
                                                            'LRPEN',
                                                            'LPPEN'
                                                       )
                                                       AND TT01.CSTD_TRAN_TYPE IN(
                                                            SELECT
                                                                 INTERNAL_CODE
                                                            FROM
                                                                 STD_CODES
                                                            WHERE
                                                                 GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                       )
                                                       AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                                       AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                                       AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                                       AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                                       AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                                       AND TA02.CA02_RETURN_ID = #return_id:String#
                                                       AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                                       AND(
                                                            TT01.CSTD_ENTITY <> 'PEN'
                                                            OR(
                                                                 TT01.CSTD_ENTITY = 'PEN'
                                                                 AND TT01.CT01_ENTITY_ID IN(
                                                                      SELECT
                                                                           TT28.CT28_ID
                                                                      FROM
                                                                           TT28_PENALTY TT28
                                                                      WHERE
                                                                           TT28.CSTD_ENTITY = 'RET'
                                                                           AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                           AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                 )
                                                            )
                                                       )
                                             UNION ALL SELECT
                                                       TT01.CT01_DESC,
                                                       CASE
                                                            WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                            ELSE TT01.CT01_AMOUNT
                                                       END AMOUNT
                                                  FROM
                                                       TT01_TRANSACTIONS TT01
                                                  WHERE
                                                       TT01.CR01_INTERNAL_ID = #internal_id:String#
                                                       AND TT01.CT01_REVERSED_FLAG = 'N'
                                                       AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                       AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                       AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                       AND TT01.CSTD_LIABILITY_TYPE IN(
                                                            'LFPEN',
                                                            'LRPEN',
                                                            'LPPEN'
                                                       )
                                                       AND TT01.CSTD_TRAN_TYPE IN(
                                                            SELECT
                                                                 INTERNAL_CODE
                                                            FROM
                                                                 STD_CODES
                                                            WHERE
                                                                 GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                       )
                                                       AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                                       AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                                       AND(
                                                            TT01.CSTD_ENTITY <> 'PEN'
                                                            OR(
                                                                 TT01.CSTD_ENTITY = 'PEN'
                                                                 AND TT01.CT01_ENTITY_ID IN(
                                                                      SELECT
                                                                           TT28.CT28_ID
                                                                      FROM
                                                                           TT28_PENALTY TT28
                                                                      WHERE
                                                                           TT28.CSTD_ENTITY = 'RET'
                                                                           AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                           AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                 )
                                                            )
                                                       )
                                             ) TAB1
                                        GROUP BY
                                             CT01_DESC
                                   ) TAB
                         ) UNPAID
               UNION ALL SELECT
                         UNPAID_AMT,
                         CODE_DESC
                    FROM
                         (
                              SELECT
                                   CT01_DESC AS CODE_DESC,
                                   AMOUNT AS UNPAID_AMT
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC,
                                             SUM( AMOUNT ) AMOUNT
                                        FROM
                                             (
                                                  SELECT
                                                       TT01.CT01_DESC,
                                                       CASE
                                                            WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                            ELSE TT01.CT01_AMOUNT
                                                       END AMOUNT
                                                  FROM
                                                       TT01_TRANSACTIONS TT01,
                                                       TA02_RETURNS TA02
                                                  WHERE
                                                       TT01.CT01_REVERSED_FLAG = 'N'
                                                       AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                       AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                       AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                       AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'
                                                       AND TT01.CSTD_TRAN_TYPE IN(
                                                            SELECT
                                                                 INTERNAL_CODE
                                                            FROM
                                                                 STD_CODES
                                                            WHERE
                                                                 GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                       )
                                                       AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                                       AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                                       AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                                       AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                                       AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                                       AND TA02.CA02_RETURN_ID = #return_id:String#
                                                       AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                                       AND(
                                                            TT01.CSTD_ENTITY <> 'PEN'
                                                            OR(
                                                                 TT01.CSTD_ENTITY = 'PEN'
                                                                 AND TT01.CT01_ENTITY_ID IN(
                                                                      SELECT
                                                                           TT28.CT28_ID
                                                                      FROM
                                                                           TT28_PENALTY TT28
                                                                      WHERE
                                                                           TT28.CSTD_ENTITY = 'RET'
                                                                           AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                           AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                 )
                                                            )
                                                       )
                                             UNION ALL SELECT
                                                       TT01.CT01_DESC,
                                                       CASE
                                                            WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                            ELSE TT01.CT01_AMOUNT
                                                       END AMOUNT
                                                  FROM
                                                       TT01_TRANSACTIONS TT01
                                                  WHERE
                                                       TT01.CR01_INTERNAL_ID = #internal_id:String#
                                                       AND TT01.CT01_REVERSED_FLAG = 'N'
                                                       AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                       AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                       AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                       AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'
                                                       AND TT01.CSTD_TRAN_TYPE IN(
                                                            SELECT
                                                                 INTERNAL_CODE
                                                            FROM
                                                                 STD_CODES
                                                            WHERE
                                                                 GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                       )
                                                       AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                                       AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                                       AND(
                                                            TT01.CSTD_ENTITY <> 'PEN'
                                                            OR(
                                                                 TT01.CSTD_ENTITY = 'PEN'
                                                                 AND TT01.CT01_ENTITY_ID IN(
                                                                      SELECT
                                                                           TT28.CT28_ID
                                                                      FROM
                                                                           TT28_PENALTY TT28
                                                                      WHERE
                                                                           TT28.CSTD_ENTITY = 'RET'
                                                                           AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                           AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                 )
                                                            )
                                                       )
                                             ) TAB1
                                        GROUP BY
                                             CT01_DESC
                                   ) TAB
                         )
               UNION ALL SELECT
                         UNPAID_AMT,
                         CODE_DESC
                    FROM
                         (
                              SELECT
                                   CT01_DESC AS CODE_DESC,
                                   AMOUNT AS UNPAID_AMT
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC,
                                             SUM( AMOUNT ) AMOUNT
                                        FROM
                                             (
                                                  SELECT
                                                       TT01.CT01_DESC,
                                                       CASE
                                                            WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                            ELSE TT01.CT01_AMOUNT
                                                       END AMOUNT
                                                  FROM
                                                       TT01_TRANSACTIONS TT01,
                                                       TA02_RETURNS TA02
                                                  WHERE
                                                       TT01.CT01_REVERSED_FLAG = 'N'
                                                       AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                       AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                       AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                       AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'
                                                       AND TT01.CSTD_TRAN_TYPE IN(
                                                            SELECT
                                                                 INTERNAL_CODE
                                                            FROM
                                                                 STD_CODES
                                                            WHERE
                                                                 GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                       )
                                                       AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                                       AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                                       AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                                       AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                                       AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                                       AND TA02.CA02_RETURN_ID = #return_id:String#
                                                       AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                             UNION ALL SELECT
                                                       TT01.CT01_DESC,
                                                       CASE
                                                            WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                            ELSE TT01.CT01_AMOUNT
                                                       END AMOUNT
                                                  FROM
                                                       TT01_TRANSACTIONS TT01
                                                  WHERE
                                                       TT01.CT01_REVERSED_FLAG = 'N'
                                                       AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                       AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                       AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                       AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'
                                                       AND TT01.CSTD_TRAN_TYPE IN(
                                                            SELECT
                                                                 INTERNAL_CODE
                                                            FROM
                                                                 STD_CODES
                                                            WHERE
                                                                 GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                       )
                                                       AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                                       AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                             ) TAB1
                                        GROUP BY
                                             CT01_DESC
                                   ) TAB
                         ) UNPAID
               UNION ALL SELECT
                         UNPAID_AMT,
                         CODE_DESC
                    FROM
                         (
                              SELECT
                                   CT01_DESC AS CODE_DESC,
                                   AMOUNT AS UNPAID_AMT
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC,
                                             SUM( AMOUNT ) AMOUNT
                                        FROM
                                             (
                                                  SELECT
                                                       TT01.CT01_DESC,
                                                       CASE
                                                            WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                            ELSE TT01.CT01_AMOUNT
                                                       END AMOUNT
                                                  FROM
                                                       TT01_TRANSACTIONS TT01
                                                  WHERE
                                                       TT01.CT01_REVERSED_FLAG = 'N'
                                                       AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                       AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                       AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                       AND TT01.CSTD_LIABILITY_TYPE IN(
                                                            'PEN247',
                                                            'PEN247D'
                                                       )
                                                       AND TT01.CSTD_TRAN_TYPE IN(
                                                            SELECT
                                                                 INTERNAL_CODE
                                                            FROM
                                                                 STD_CODES
                                                            WHERE
                                                                 GROUP_CODE = 'TRANSACTION_TYPE'
                                                                 AND PARENT_INTERNAL_CODE IN(
                                                                      'TA',
                                                                      'AJ'
                                                                 )
                                                       )
                                                       AND TT01.CSTD_ALLOCATED_ENTITY = 'OBJ'
                                                       AND TT01.CT01_ALLOCATED_ENTITYID = #request_no:String#
                                             ) TAB1
                                        GROUP BY
                                             CT01_DESC
                                   ) TAB
                         )
               )
          GROUP BY
               CODE_DESC
     ) SUB_UNPAID_FLAG,
     (
          SELECT
               SUM( AVAILABLE_AMT ) AMT_NUMBER
          FROM
               (
                    SELECT
                         CT01_DESC AS CODE_DESC,
                         AMOUNT AS AVAILABLE_AMT
                    FROM
                         (
                              SELECT
                                   CT01_DESC,
                                   SUM( AMOUNT ) AMOUNT
                              FROM
                                   (
                                        SELECT
                                             TT01.CT01_DESC,
                                             CASE
                                                  WHEN TT01.CSTD_DC = 'DT' THEN 0 - TT01.CT01_AMOUNT
                                                  ELSE TT01.CT01_AMOUNT
                                             END AMOUNT
                                        FROM
                                             TT01_TRANSACTIONS TT01,
                                             TA02_RETURNS TA02
                                        WHERE
                                             TT01.CT01_VALUE_DATE <= SYSDATE
                                             AND TT01.CT01_REVERSED_FLAG = 'N'
                                             AND TT01.CT01_CLEARED_FLAG = 'Y'
                                             AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                             AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'
                                             AND TT01.CSTD_TRAN_TYPE IN(
                                                  SELECT
                                                       INTERNAL_CODE
                                                  FROM
                                                       STD_CODES
                                                  WHERE
                                                       GROUP_CODE = 'TRANSACTION_TYPE'
                                                       AND PARENT_INTERNAL_CODE IN('OT')
                                             )
                                             AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                             AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                             AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                             AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                             AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                             AND TA02.CA02_RETURN_ID = #return_id:String#
                                             AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                   UNION ALL SELECT
                                             TT01.CT01_DESC,
                                             CASE
                                                  WHEN TT01.CSTD_DC = 'DT' THEN 0 - TT01.CT01_AMOUNT
                                                  ELSE TT01.CT01_AMOUNT
                                             END AMOUNT
                                        FROM
                                             TT01_TRANSACTIONS TT01
                                        WHERE
                                             TT01.CT01_VALUE_DATE <= SYSDATE
                                             AND TT01.CT01_REVERSED_FLAG = 'N'
                                             AND TT01.CT01_CLEARED_FLAG = 'Y'
                                             AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                             AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'
                                             AND TT01.CSTD_TRAN_TYPE IN(
                                                  SELECT
                                                       INTERNAL_CODE
                                                  FROM
                                                       STD_CODES
                                                  WHERE
                                                       GROUP_CODE = 'TRANSACTION_TYPE'
                                                       AND PARENT_INTERNAL_CODE IN('OT')
                                             )
                                             AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                             AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                   ) TAB1
                              GROUP BY
                                   CT01_DESC
                         ) TAB
                    WHERE
                         TAB.AMOUNT > 0
               ) AVAILABLE
     ) SUB_REWARDS_TOTAL,
     (
          SELECT
               SUM( AMT ) AMT_NUMBER
          FROM
               (
                    SELECT
                         SUM( UNPAID_AMT ) AMT,
                         CODE_DESC
                    FROM
                         (
                              SELECT
                                   UNPAID_AMT,
                                   CODE_DESC
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC AS CODE_DESC,
                                             AMOUNT AS UNPAID_AMT
                                        FROM
                                             (
                                                  SELECT
                                                       CT01_DESC,
                                                       SUM( AMOUNT ) AMOUNT
                                                  FROM
                                                       (
                                                            SELECT
                                                                 TT01.CT01_DESC,
                                                                 CASE
                                                                      WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                                      ELSE TT01.CT01_AMOUNT
                                                                 END AMOUNT
                                                            FROM
                                                                 TT01_TRANSACTIONS TT01,
                                                                 TA02_RETURNS TA02
                                                            WHERE
                                                                 TT01.CT01_REVERSED_FLAG = 'N'
                                                                 AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                                 AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                                 AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                                 AND TT01.CSTD_LIABILITY_TYPE IN(
                                                                      'LFPEN',
                                                                      'LRPEN',
                                                                      'LPPEN'
                                                                 )
                                                                 AND TT01.CSTD_TRAN_TYPE IN(
                                                                      SELECT
                                                                           INTERNAL_CODE
                                                                      FROM
                                                                           STD_CODES
                                                                      WHERE
                                                                           GROUP_CODE = 'TRANSACTION_TYPE'
                                                                           AND PARENT_INTERNAL_CODE IN(
                                                                                'TA',
                                                                                'AJ'
                                                                           )
                                                                 )
                                                                 AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                                                 AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                                                 AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                                                 AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                                                 AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                                                 AND TA02.CA02_RETURN_ID = #return_id:String#
                                                                 AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                                                 AND(
                                                                      TT01.CSTD_ENTITY <> 'PEN'
                                                                      OR(
                                                                           TT01.CSTD_ENTITY = 'PEN'
                                                                           AND TT01.CT01_ENTITY_ID IN(
                                                                                SELECT
                                                                                     TT28.CT28_ID
                                                                                FROM
                                                                                     TT28_PENALTY TT28
                                                                                WHERE
                                                                                     TT28.CSTD_ENTITY = 'RET'
                                                                                     AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                                     AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                           )
                                                                      )
                                                                 )
                                                       UNION ALL SELECT
                                                                 TT01.CT01_DESC,
                                                                 CASE
                                                                      WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                                      ELSE TT01.CT01_AMOUNT
                                                                 END AMOUNT
                                                            FROM
                                                                 TT01_TRANSACTIONS TT01
                                                            WHERE
                                                                 TT01.CR01_INTERNAL_ID = #internal_id:String#
                                                                 AND TT01.CT01_REVERSED_FLAG = 'N'
                                                                 AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                                 AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                                 AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                                 AND TT01.CSTD_LIABILITY_TYPE IN(
                                                                      'LFPEN',
                                                                      'LRPEN',
                                                                      'LPPEN'
                                                                 )
                                                                 AND TT01.CSTD_TRAN_TYPE IN(
                                                                      SELECT
                                                                           INTERNAL_CODE
                                                                      FROM
                                                                           STD_CODES
                                                                      WHERE
                                                                           GROUP_CODE = 'TRANSACTION_TYPE'
                                                                           AND PARENT_INTERNAL_CODE IN(
                                                                                'TA',
                                                                                'AJ'
                                                                           )
                                                                 )
                                                                 AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                                                 AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                                                 AND(
                                                                      TT01.CSTD_ENTITY <> 'PEN'
                                                                      OR(
                                                                           TT01.CSTD_ENTITY = 'PEN'
                                                                           AND TT01.CT01_ENTITY_ID IN(
                                                                                SELECT
                                                                                     TT28.CT28_ID
                                                                                FROM
                                                                                     TT28_PENALTY TT28
                                                                                WHERE
                                                                                     TT28.CSTD_ENTITY = 'RET'
                                                                                     AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                                     AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                           )
                                                                      )
                                                                 )
                                                       ) TAB1
                                                  GROUP BY
                                                       CT01_DESC
                                             ) TAB
                                   ) UNPAID
                         UNION ALL SELECT
                                   UNPAID_AMT,
                                   CODE_DESC
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC AS CODE_DESC,
                                             AMOUNT AS UNPAID_AMT
                                        FROM
                                             (
                                                  SELECT
                                                       CT01_DESC,
                                                       SUM( AMOUNT ) AMOUNT
                                                  FROM
                                                       (
                                                            SELECT
                                                                 TT01.CT01_DESC,
                                                                 CASE
                                                                      WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                                      ELSE TT01.CT01_AMOUNT
                                                                 END AMOUNT
                                                            FROM
                                                                 TT01_TRANSACTIONS TT01,
                                                                 TA02_RETURNS TA02
                                                            WHERE
                                                                 TT01.CT01_REVERSED_FLAG = 'N'
                                                                 AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                                 AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                                 AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                                 AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'
                                                                 AND TT01.CSTD_TRAN_TYPE IN(
                                                                      SELECT
                                                                           INTERNAL_CODE
                                                                      FROM
                                                                           STD_CODES
                                                                      WHERE
                                                                           GROUP_CODE = 'TRANSACTION_TYPE'
                                                                           AND PARENT_INTERNAL_CODE IN(
                                                                                'TA',
                                                                                'AJ'
                                                                           )
                                                                 )
                                                                 AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                                                 AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                                                 AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                                                 AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                                                 AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                                                 AND TA02.CA02_RETURN_ID = #return_id:String#
                                                                 AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                                                 AND(
                                                                      TT01.CSTD_ENTITY <> 'PEN'
                                                                      OR(
                                                                           TT01.CSTD_ENTITY = 'PEN'
                                                                           AND TT01.CT01_ENTITY_ID IN(
                                                                                SELECT
                                                                                     TT28.CT28_ID
                                                                                FROM
                                                                                     TT28_PENALTY TT28
                                                                                WHERE
                                                                                     TT28.CSTD_ENTITY = 'RET'
                                                                                     AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                                     AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                           )
                                                                      )
                                                                 )
                                                       UNION ALL SELECT
                                                                 TT01.CT01_DESC,
                                                                 CASE
                                                                      WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                                      ELSE TT01.CT01_AMOUNT
                                                                 END AMOUNT
                                                            FROM
                                                                 TT01_TRANSACTIONS TT01
                                                            WHERE
                                                                 TT01.CR01_INTERNAL_ID = #internal_id:String#
                                                                 AND TT01.CT01_REVERSED_FLAG = 'N'
                                                                 AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                                 AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                                 AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                                 AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'
                                                                 AND TT01.CSTD_TRAN_TYPE IN(
                                                                      SELECT
                                                                           INTERNAL_CODE
                                                                      FROM
                                                                           STD_CODES
                                                                      WHERE
                                                                           GROUP_CODE = 'TRANSACTION_TYPE'
                                                                           AND PARENT_INTERNAL_CODE IN(
                                                                                'TA',
                                                                                'AJ'
                                                                           )
                                                                 )
                                                                 AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                                                 AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                                                 AND(
                                                                      TT01.CSTD_ENTITY <> 'PEN'
                                                                      OR(
                                                                           TT01.CSTD_ENTITY = 'PEN'
                                                                           AND TT01.CT01_ENTITY_ID IN(
                                                                                SELECT
                                                                                     TT28.CT28_ID
                                                                                FROM
                                                                                     TT28_PENALTY TT28
                                                                                WHERE
                                                                                     TT28.CSTD_ENTITY = 'RET'
                                                                                     AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= #return_id:String#
                                                                                     AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= #return_version:String#
                                                                           )
                                                                      )
                                                                 )
                                                       ) TAB1
                                                  GROUP BY
                                                       CT01_DESC
                                             ) TAB
                                   )
                         UNION ALL SELECT
                                   UNPAID_AMT,
                                   CODE_DESC
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC AS CODE_DESC,
                                             AMOUNT AS UNPAID_AMT
                                        FROM
                                             (
                                                  SELECT
                                                       CT01_DESC,
                                                       SUM( AMOUNT ) AMOUNT
                                                  FROM
                                                       (
                                                            SELECT
                                                                 TT01.CT01_DESC,
                                                                 CASE
                                                                      WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                                      ELSE TT01.CT01_AMOUNT
                                                                 END AMOUNT
                                                            FROM
                                                                 TT01_TRANSACTIONS TT01,
                                                                 TA02_RETURNS TA02
                                                            WHERE
                                                                 TT01.CT01_REVERSED_FLAG = 'N'
                                                                 AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                                 AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                                 AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                                 AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'
                                                                 AND TT01.CSTD_TRAN_TYPE IN(
                                                                      SELECT
                                                                           INTERNAL_CODE
                                                                      FROM
                                                                           STD_CODES
                                                                      WHERE
                                                                           GROUP_CODE = 'TRANSACTION_TYPE'
                                                                           AND PARENT_INTERNAL_CODE IN(
                                                                                'TA',
                                                                                'AJ'
                                                                           )
                                                                 )
                                                                 AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID
                                                                 AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE
                                                                 AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE
                                                                 AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR
                                                                 AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD
                                                                 AND TA02.CA02_RETURN_ID = #return_id:String#
                                                                 AND TA02.CA02_RETURN_VERSION = #return_version:String#
                                                       UNION ALL SELECT
                                                                 TT01.CT01_DESC,
                                                                 CASE
                                                                      WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                                      ELSE TT01.CT01_AMOUNT
                                                                 END AMOUNT
                                                            FROM
                                                                 TT01_TRANSACTIONS TT01
                                                            WHERE
                                                                 TT01.CT01_REVERSED_FLAG = 'N'
                                                                 AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                                 AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                                 AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                                 AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'
                                                                 AND TT01.CSTD_TRAN_TYPE IN(
                                                                      SELECT
                                                                           INTERNAL_CODE
                                                                      FROM
                                                                           STD_CODES
                                                                      WHERE
                                                                           GROUP_CODE = 'TRANSACTION_TYPE'
                                                                           AND PARENT_INTERNAL_CODE IN(
                                                                                'TA',
                                                                                'AJ'
                                                                           )
                                                                 )
                                                                 AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'
                                                                 AND TT01.CT01_ALLOCATED_ENTITYID = #return_id:String#
                                                       ) TAB1
                                                  GROUP BY
                                                       CT01_DESC
                                             ) TAB
                                   ) UNPAID
                         UNION ALL SELECT
                                   UNPAID_AMT,
                                   CODE_DESC
                              FROM
                                   (
                                        SELECT
                                             CT01_DESC AS CODE_DESC,
                                             AMOUNT AS UNPAID_AMT
                                        FROM
                                             (
                                                  SELECT
                                                       CT01_DESC,
                                                       SUM( AMOUNT ) AMOUNT
                                                  FROM
                                                       (
                                                            SELECT
                                                                 TT01.CT01_DESC,
                                                                 CASE
                                                                      WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT
                                                                      ELSE TT01.CT01_AMOUNT
                                                                 END AMOUNT
                                                            FROM
                                                                 TT01_TRANSACTIONS TT01
                                                            WHERE
                                                                 TT01.CT01_REVERSED_FLAG = 'N'
                                                                 AND TT01.CT01_CLEARED_FLAG = 'Y'
                                                                 AND TT01.CT01_FINALIZED_FLAG = 'Y'
                                                                 AND TT01.CT01_VALUE_DATE <= SYSDATE
                                                                 AND TT01.CSTD_LIABILITY_TYPE IN(
                                                                      'PEN247',
                                                                      'PEN247D'
                                                                 )
                                                                 AND TT01.CSTD_TRAN_TYPE IN(
                                                                      SELECT
                                                                           INTERNAL_CODE
                                                                      FROM
                                                                           STD_CODES
                                                                      WHERE
                                                                           GROUP_CODE = 'TRANSACTION_TYPE'
                                                                           AND PARENT_INTERNAL_CODE IN(
                                                                                'TA',
                                                                                'AJ'
                                                                           )
                                                                 )
                                                                 AND TT01.CSTD_ALLOCATED_ENTITY = 'OBJ'
                                                                 AND TT01.CT01_ALLOCATED_ENTITYID = #request_no:String#
                                                       ) TAB1
                                                  GROUP BY
                                                       CT01_DESC
                                             ) TAB
                                   )
                         )
                    GROUP BY
                         CODE_DESC
               )
     ) SUB_PENALTIES_TOTAL
WHERE
     RET2.CA02_RETURN_ID = #return_id:String#
     AND RET2.CA02_RETURN_VERSION = #return_version:String#
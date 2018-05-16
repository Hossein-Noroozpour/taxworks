SELECT
     SUM( AVAILABLE_AMT ) PAIDTAXFRM24AMTNUM
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
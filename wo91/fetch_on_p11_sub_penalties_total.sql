SELECT
     SUM( AMT ) SUBPENTTLAMTNUM
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
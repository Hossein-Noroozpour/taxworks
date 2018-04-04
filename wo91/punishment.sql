(SELECT SUM(UNPAID_AMT) UNPAID_AMT,
LTRIM(to_char(SUM(UNPAID_AMT), '99,999,999,999,999,999,999.99')) UNPAID_AMT_display,
LTRIM(to_char(SUM(UNPAID_AMT), '00,000,000,000,000,000,000.00'))UNPAID_AMT_temp,
code_desc from (
SELECT UNPAID_AMT, code_desc
  FROM (SELECT ct01_desc  as   code_desc,
               AMOUNT                  AS UNPAID_AMT
          FROM (SELECT ct01_desc,
                       SUM(AMOUNT) AMOUNT
                  FROM ( SELECT tt01.ct01_desc,
                            CASE
							WHEN tt01.CSTD_DC = 'CT' THEN
							 0 - tt01.CT01_AMOUNT
							ELSE
							 tt01.CT01_AMOUNT
						  END AMOUNT  
                          FROM TT01_TRANSACTIONS tt01, tax_returns ta02
                         WHERE tt01.CT01_REVERSED_FLAG = 'N'
                        and tt01.ct01_cleared_flag = 'Y'
                        and tt01.ct01_finalized_flag = 'Y'
                        and tt01.ct01_value_date <= sysdate
                        and tt01.CSTD_LIABILITY_TYPE in ('LFPEN','LRPEN','LPPEN')
						and tt01.cstd_tran_type in (select internal_code from std_codes where group_code = 'TRANSACTION_TYPE' and parent_internal_code in ('TA','AJ'))
                        and tt01.cr01_internal_id = ta02.cr01_internal_id
                        and tt01.cr03_branch_code = ta02.cr03_branch_code
                        and tt01.cstd_tax_type = ta02.ca09_tax_type_code
                        and tt01.ct01_tax_year = ta02.ca02_tax_year
                        and tt01.ct01_period = ta02.ca02_tax_period
                        and ta02.ca02_return_id = '{?charReturnId}'
                        and ta02.ca02_return_version = '{?charReturnVersion}'
                        and (tt01.cstd_entity <> 'PEN' OR (tt01.cstd_entity = 'PEN' and tt01.ct01_entity_id in (
                            SELECT tt28.ct28_id FROM tt28_penalty tt28 WHERE tt28.cstd_entity = 'RET' 
                            and substr(tt28.ct28_entity_id, 1, instr(tt28.ct28_entity_id, '-') - 1) = '{?charReturnId}' 
                            and to_number(substr(tt28.ct28_entity_id, instr(tt28.ct28_entity_id, '-') + 1)) <= '{?charReturnVersion}')))
                        union all
                        SELECT tt01.ct01_desc,
                               CASE
              WHEN tt01.CSTD_DC = 'CT' THEN
               0 - tt01.CT01_AMOUNT
              ELSE
               tt01.CT01_AMOUNT
              END AMOUNT  
                        FROM TT01_TRANSACTIONS tt01
                       WHERE 
                          tt01.CR01_INTERNAL_ID = '{?internalId}' 
                      and tt01.CT01_REVERSED_FLAG = 'N'
                      and tt01.ct01_cleared_flag = 'Y'
                      and tt01.ct01_finalized_flag = 'Y'
                      and tt01.ct01_value_date <= sysdate
                      and tt01.CSTD_LIABILITY_TYPE in ('LFPEN','LRPEN','LPPEN')
                      and tt01.cstd_tran_type in (select internal_code from std_codes where group_code = 'TRANSACTION_TYPE' and parent_internal_code in ('TA','AJ'))
                      and tt01.cstd_allocated_entity = 'RET'
                      and tt01.ct01_allocated_entityid = '{?charReturnId}'
                      and (tt01.cstd_entity <> 'PEN' OR (tt01.cstd_entity = 'PEN' and tt01.ct01_entity_id in (
                          SELECT tt28.ct28_id FROM tt28_penalty tt28 WHERE tt28.cstd_entity = 'RET' 
                          and substr(tt28.ct28_entity_id, 1, instr(tt28.ct28_entity_id, '-') - 1) = '{?charReturnId}' 
                          and to_number(substr(tt28.ct28_entity_id, instr(tt28.ct28_entity_id, '-') + 1)) <= '{?charReturnVersion}')))
                        ) TAB1
                 GROUP BY ct01_desc) TAB
         ) unpaid
   
union all

SELECT UNPAID_AMT, code_desc
  FROM (SELECT ct01_desc      as code_desc,
               AMOUNT                  AS UNPAID_AMT
          FROM (SELECT ct01_desc,
                       SUM(AMOUNT) AMOUNT
                  FROM ( SELECT tt01.ct01_desc,
                                CASE
              WHEN tt01.CSTD_DC = 'CT' THEN
               0 - tt01.CT01_AMOUNT
              ELSE
               tt01.CT01_AMOUNT
              END AMOUNT  
                        FROM TT01_TRANSACTIONS tt01, tax_returns ta02
                       WHERE tt01.CT01_REVERSED_FLAG = 'N'
                      and tt01.ct01_cleared_flag = 'Y'
                      -- and tt01.CSTD_LIABILITY_TYPE in ('LFPEN','LRPEN','LPPEN')
                      and tt01.ct01_finalized_flag = 'Y'
                      and tt01.ct01_value_date <= sysdate
                      and tt01.CSTD_LIABILITY_TYPE like 'AUD%'
                      and tt01.cstd_tran_type in (select internal_code from std_codes where group_code = 'TRANSACTION_TYPE' and parent_internal_code in ('TA','AJ'))
                      and tt01.cr01_internal_id = ta02.cr01_internal_id
                      and tt01.cr03_branch_code = ta02.cr03_branch_code
                      and tt01.cstd_tax_type = ta02.ca09_tax_type_code
                      and tt01.ct01_tax_year = ta02.ca02_tax_year
                      and tt01.ct01_period = ta02.ca02_tax_period
                      and ta02.ca02_return_id = '{?charReturnId}'
                      and ta02.ca02_return_version = '{?charReturnVersion}'
                      and (tt01.cstd_entity <> 'PEN' OR (tt01.cstd_entity = 'PEN' and tt01.ct01_entity_id in (
                          SELECT tt28.ct28_id FROM tt28_penalty tt28 WHERE tt28.cstd_entity = 'RET' 
                          and substr(tt28.ct28_entity_id, 1, instr(tt28.ct28_entity_id, '-') - 1) = '{?charReturnId}' 
                          and to_number(substr(tt28.ct28_entity_id, instr(tt28.ct28_entity_id, '-') + 1)) <= '{?charReturnVersion}')))
                          union all
                          SELECT tt01.ct01_desc,
                                CASE
              WHEN tt01.CSTD_DC = 'CT' THEN
               0 - tt01.CT01_AMOUNT
              ELSE
               tt01.CT01_AMOUNT
              END AMOUNT  
                        FROM TT01_TRANSACTIONS tt01
                       WHERE 
                          tt01.CR01_INTERNAL_ID = '{?internalId}' 
                      and tt01.CT01_REVERSED_FLAG = 'N'
                      and tt01.ct01_cleared_flag = 'Y'
                      and tt01.ct01_finalized_flag = 'Y'
                      and tt01.ct01_value_date <= sysdate
                      -- and tt01.CSTD_LIABILITY_TYPE in ('LFPEN','LRPEN','LPPEN')
                      and tt01.CSTD_LIABILITY_TYPE like 'AUD%' 
                      and tt01.cstd_tran_type in (select internal_code from std_codes where group_code =
                      'TRANSACTION_TYPE' and parent_internal_code in ('TA','AJ'))
                      and tt01.cstd_allocated_entity = 'RET'
                      and tt01.ct01_allocated_entityid = '{?charReturnId}'
                      and (tt01.cstd_entity <> 'PEN' OR (tt01.cstd_entity = 'PEN' and tt01.ct01_entity_id in (
                          SELECT tt28.ct28_id FROM tt28_penalty tt28 WHERE tt28.cstd_entity = 'RET' 
                          and substr(tt28.ct28_entity_id, 1, instr(tt28.ct28_entity_id, '-') - 1) = '{?charReturnId}' 
                          and to_number(substr(tt28.ct28_entity_id, instr(tt28.ct28_entity_id, '-') + 1)) <= '{?charReturnVersion}')))
                        ) TAB1
                 GROUP BY ct01_desc) TAB
         ) 
   
union  all

SELECT UNPAID_AMT, code_desc
  FROM (SELECT ct01_desc  as  code_desc,
               AMOUNT                  AS UNPAID_AMT
          FROM (SELECT ct01_desc,
                       SUM(AMOUNT) AMOUNT
                  FROM ( SELECT tt01.ct01_desc,
                                CASE
              WHEN tt01.CSTD_DC = 'CT' THEN
               0 - tt01.CT01_AMOUNT
              ELSE
               tt01.CT01_AMOUNT
              END AMOUNT  
                          FROM TT01_TRANSACTIONS tt01, tax_returns ta02
                         WHERE tt01.CT01_REVERSED_FLAG = 'N'
                        and tt01.ct01_cleared_flag = 'Y'
                      and tt01.ct01_finalized_flag = 'Y'
                        and tt01.ct01_value_date <= sysdate
                        and tt01.CSTD_LIABILITY_TYPE = 'INTRES'
                        and tt01.cstd_tran_type in (select internal_code from std_codes where group_code =
                        'TRANSACTION_TYPE' and parent_internal_code in ('TA','AJ'))
                        and tt01.cr01_internal_id = ta02.cr01_internal_id
                        and tt01.cr03_branch_code = ta02.cr03_branch_code
                        and tt01.cstd_tax_type = ta02.ca09_tax_type_code
                        and tt01.ct01_tax_year = ta02.ca02_tax_year
                        and tt01.ct01_period = ta02.ca02_tax_period
                        and ta02.ca02_return_id = '{?charReturnId}'
                        and ta02.ca02_return_version = '{?charReturnVersion}'
                        union all
                        SELECT tt01.ct01_desc,
                               CASE
              WHEN tt01.CSTD_DC = 'CT' THEN
               0 - tt01.CT01_AMOUNT
              ELSE
               tt01.CT01_AMOUNT
              END AMOUNT  
                          FROM TT01_TRANSACTIONS tt01
                         WHERE tt01.CT01_REVERSED_FLAG = 'N'
                        and tt01.ct01_cleared_flag = 'Y'
                      and tt01.ct01_finalized_flag = 'Y'
                        and tt01.ct01_value_date <= sysdate
                        and tt01.CSTD_LIABILITY_TYPE = 'INTRES'
                        and tt01.cstd_tran_type in (select internal_code from std_codes where group_code =
                        'TRANSACTION_TYPE' and parent_internal_code in ('TA','AJ'))
                        and tt01.cstd_allocated_entity = 'RET'
                        and tt01.ct01_allocated_entityid = '{?charReturnId}'
                        ) TAB1
                 GROUP BY ct01_desc) TAB
        ) unpaid

union  all

SELECT UNPAID_AMT, code_desc
  FROM (SELECT ct01_desc  as  code_desc,
               AMOUNT                  AS UNPAID_AMT
          FROM (SELECT ct01_desc,
                       SUM(AMOUNT) AMOUNT
                  FROM ( SELECT tt01.ct01_desc,
                                CASE
              WHEN tt01.CSTD_DC = 'CT' THEN
               0 - tt01.CT01_AMOUNT
              ELSE
               tt01.CT01_AMOUNT
              END AMOUNT  
                          FROM TT01_TRANSACTIONS tt01
                         WHERE tt01.CT01_REVERSED_FLAG = 'N'
                        and tt01.ct01_cleared_flag = 'Y'
                      and tt01.ct01_finalized_flag = 'Y'
                        and tt01.ct01_value_date <= sysdate
                        and tt01.CSTD_LIABILITY_TYPE in ('PEN247', 'PEN247D')
                        and tt01.cstd_tran_type in (select internal_code from std_codes
                        where group_code = 'TRANSACTION_TYPE' and parent_internal_code in ('TA','AJ'))
                        and tt01.cstd_allocated_entity = 'OBJ' 
                        and tt01.ct01_allocated_entityid = '{?requestNo}') TAB1
                 GROUP BY ct01_desc) TAB
        )
) GROUP BY code_desc
)



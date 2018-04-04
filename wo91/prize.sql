(SELECT AVAILABLE_AMT,
LTRIM(to_char(AVAILABLE_AMT, '99,999,999,999,999,999,999.99')) UNPAID_AMT_display,
LTRIM(to_char(AVAILABLE_AMT, '00,000,000,000,000,000,000.00'))UNPAID_AMT_temp,
code_desc
  from (SELECT ct01_desc as code_desc,
               AMOUNT                  AS AVAILABLE_AMT
          FROM (SELECT max(ct01_desc) ct01_desc,
                       SUM(AMOUNT) AMOUNT
                  FROM (SELECT tt01.ct01_desc,
                                CASE
                                  WHEN tt01.CSTD_DC = 'DT' THEN
                                   0 - tt01.CT01_AMOUNT
                                  ELSE
                                   tt01.CT01_AMOUNT
                                END AMOUNT
                           FROM TT01_TRANSACTIONS tt01,tax_returns ta02
                          WHERE  tt01.CT01_VALUE_DATE <= sysdate
                          AND tt01.CT01_REVERSED_FLAG = 'N'
                          AND tt01.ct01_cleared_flag = 'Y'
                          and tt01.ct01_finalized_flag = 'Y'
                          and tt01.CSTD_LIABILITY_TYPE = 'INTRES'
                          and tt01.cstd_tran_type in (select internal_code from std_codes where group_code = 'TRANSACTION_TYPE' and parent_internal_code in ('TA','PA','RL'))
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
                                  WHEN tt01.CSTD_DC = 'DT' THEN
                                   0 - tt01.CT01_AMOUNT
                                  ELSE
                                   tt01.CT01_AMOUNT
                                END AMOUNT
                           FROM TT01_TRANSACTIONS tt01
                          WHERE  tt01.CT01_VALUE_DATE <= sysdate
                          AND tt01.CT01_REVERSED_FLAG = 'N'
                          AND tt01.ct01_cleared_flag = 'Y'
                          and tt01.ct01_finalized_flag = 'Y'
                          and tt01.CSTD_LIABILITY_TYPE = 'INTRES'
                          and tt01.cstd_tran_type in (select internal_code from std_codes where group_code = 'TRANSACTION_TYPE' and parent_internal_code in ('TA','PA','RL'))
                          and tt01.cstd_allocated_entity = 'RET'
                          and tt01.ct01_allocated_entityid = '{?charReturnId}'
                         ) TAB1
                 GROUP BY ct01_desc) TAB
         WHERE TAB.AMOUNT > 0) available
)



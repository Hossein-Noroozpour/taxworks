SELECT
     MAX( CC03_ID ) ISSUANCE_CC03_ID,
     MAX( CC03_CREATE_ON ) ISSCC03CREON
FROM
     (
          SELECT
               CC03_ID,
               CC03_CREATE_ON
          FROM
               TC03_LETTER STL3
          JOIN TO02_REQUEST_DETAILS OBJ2 ON
               STL3.CC03_ENTITY_ID = OBJ2.CO01_REQUEST_NO
               AND OBJ2.CA02_RETURN_ID = #return_id:String#
          WHERE
               STL3.CSTD_ENTITY = 'OBJ'
               AND STL3.CR01_INTERNAL_ID = #internal_id:String#
               AND STL3.CSTD_LETTER_TYPE IN (
                    'LTR_OA01',
                    'LTR_OA02',
                    'LTR_OA12'
               )
     UNION SELECT
               CC03_ID,
               CC03_CREATE_ON
          FROM
               TC03_LETTER STL3
          WHERE
               STL3.CC03_ENTITY_ID = #return_id:String# || '-' || #return_version:String#
               AND STL3.CR01_INTERNAL_ID = #internal_id:String#
               AND STL3.CSTD_LETTER_TYPE IN (
                    'LTR_RP12',
                    'LTR_AC07'
               )
     )
SELECT COUNT(*) FROM (
SELECT DISTINCT 
    an.CC03_ID an_CC03_ID, an.CC03_YEAR an_CC03_YEAR, an.CC03_NEXT_ENVENT, rul.CSTD_STATUS, 
    rul.CR01_INTERNAL_ID, obj2.CSTD_RULING_OUTCOME OBJ_REQ_RULL_OUT
FROM
    (
        SELECT MAX(n1rul.CC03_ID) n1rul_CC03_ID, n1obj1.CO01_REQUEST_NO n1obj_CO01_REQUEST_NO
        FROM
            (
                SELECT * 
                FROM STL.TC03_LETTER
                WHERE 
                    CSTD_LETTER_TYPE IN ('LTR_OA01', 'LTR_OA12', 'LTR_OA02')
                    AND CSTD_STATUS NOT IN ('LTR_OVER', 'LTR_PRPL', 'LTR_ROVD')
            ) n1rul
            JOIN (
                SELECT *
                FROM OBJ.TO01_REQUESTS n2obj1
                WHERE
                    n2obj1.CSTD_CASE_NATURE IN ('OBJ_TRANS', 'OBJ_WRGTIN')
                    AND n2obj1.CSTD_REQUEST_TYPE IN ('01', '02')
                    AND (
                        SELECT COUNT(1)
                        FROM OBJ.TO01_REQUESTS n3obj1
                        WHERE n2obj1.CO01_REQUEST_NO = n3obj1.CO01_PREVIOUS_REQUEST_NO
                    ) = 0
            ) n1obj1 ON (
                n1obj1.CO01_REQUEST_NO = n1rul.CC03_ENTITY_ID 
                AND n1obj1.CR01_INTERNAL_ID = n1rul.CR01_INTERNAL_ID)
        GROUP BY n1obj1.CO01_REQUEST_NO
    ) last_rul
    JOIN STL.TC03_LETTER rul ON last_rul.n1rul_CC03_ID = rul.CC03_ID
    JOIN OBJ.TO01_REQUESTS obj1 ON last_rul.n1obj_CO01_REQUEST_NO = obj1.CO01_REQUEST_NO
    JOIN OBJ.TO08_REQUEST_RULING_DETAIL obj2 ON (
        obj1.CO01_REQUEST_NO = obj2.CO01_REQUEST_NO)
    JOIN (
        SELECT *
        FROM TAC.TT01_TRANSACTIONS
        WHERE 
            (
                CSTD_ENTITY = 'RET'
                AND Substr(CT01_ENTITY_ID, 1, Instr(CT01_ENTITY_ID, '-')) IS NOT NULL
            ) OR CSTD_ENTITY = 'PEN'
    ) tac1 ON (
        tac1.CT01_TRANSACTION_ID = obj2.CT01_TRANSACTION_ID 
        AND tac1.CR01_INTERNAL_ID = rul.CR01_INTERNAL_ID)
    JOIN STL.TC03_LETTER an ON (an.CC03_ENTITY_ID = (
            SELECT MAX(n1an.CC03_ENTITY_ID)
            FROM
                STL.TC03_LETTER n1an
            WHERE
                Substr(n1an.CC03_ENTITY_ID, 1, Instr(n1an.CC03_ENTITY_ID, '-')) IS NOT NULL
                AND n1an.CC01_ID IN ('52','54','55','56','57','58','59','104', '137')
                AND (
                    (
                        tac1.CSTD_ENTITY = 'RET'
                        AND Substr(n1an.CC03_ENTITY_ID, 1, Instr(n1an.CC03_ENTITY_ID, '-')) =
                            Substr(tac1.CT01_ENTITY_ID, 1, Instr(tac1.CT01_ENTITY_ID, '-'))
                    ) OR (
                        tac1.CSTD_ENTITY = 'PEN'
                        AND Substr(n1an.CC03_ENTITY_ID, 1, Instr(n1an.CC03_ENTITY_ID, '-')) = (
                            SELECT Substr(pen.CT28_ENTITY_ID, 1, Instr(pen.CT28_ENTITY_ID, '-'))
                            FROM TAC.TT28_PENALTY pen
                            WHERE
                                pen.CT28_ID = tac1.CT01_ENTITY_ID
                                AND pen.CSTD_ENTITY = 'RET'
                        )
                    )
                ) AND n1an.CR01_INTERNAL_ID = rul.CR01_INTERNAL_ID
        ) AND an.CR01_INTERNAL_ID = rul.CR01_INTERNAL_ID
        AND an.CC03_NEXT_ENVENT = 'LTR_FTNDUE'
        AND an.CC03_ENVENT_TRIG = 'N'
    )
)
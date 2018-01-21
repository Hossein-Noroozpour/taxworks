#!/usr/bin/python3

import sys

import cx_Oracle


def get_first(crs, q):
    crs.execute(q)
    for r in crs:
        return r
    return None


if __name__ == '__main__':
    if len(sys.argv) < 4:
        sys.exit('You have to provide db-user, db-pass, ip/sid, tin')
    tin = sys.argv[4]
    print('tin is', tin)
    con = cx_Oracle.connect(sys.argv[1], sys.argv[2], sys.argv[3])
    crs = con.cursor()
    q = '''
    SELECT INTERNAL_ID
    FROM REG.TAXPAYER
    WHERE TIN = %s
    ''' % tin
    iid = get_first(crs, q)[0]
    print('internal id is', iid)
    if iid is None:
        sys.exit('Wrong tin')
    q = '''
    SELECT
        CC03_ENTITY_ID, CC03_ID
    FROM
        STL.TC03_LETTER
    WHERE
        CC01_ID= 135
        AND CR01_INTERNAL_ID = %s
    ORDER BY CC03_ID DESC
    ''' % iid
    an, cmp = get_first(crs, q)
    print('compliance is', cmp)
    print('an is', an)
    q = '''
    SELECT CC03_ENTITY_ID
    FROM STL.TC03_LETTER
    WHERE CC03_ID = %s
    ''' % an
    retver, = get_first(crs, q)
    ret, ver = retver.split('-')
    print('return:', ret, ', version:', ver)
    q = '''
    SELECT COUNT(DISTINCT OBJ2.CA02_RETURN_ID)
    FROM
	    OBJ.TO01_REQUESTS OBJ1
	    JOIN OBJ.TO02_REQUEST_DETAILS OBJ2
            ON OBJ1.CO01_REQUEST_NO = OBJ2.CO01_REQUEST_NO
    WHERE
	    OBJ1.CSTD_REQUEST_STATUS = 'OBJ_OPEN'
	    AND OBJ2.CA02_RETURN_ID = %s
    ''' % ret
    openobj = int(get_first(crs, q)[0])
    print('open objections count:', openobj)
    if openobj > 0:
        print('your tine has open objection on the return so THIS IS NOT A BUG')
        sys.exit(0)
    q = '''
    SELECT DISTINCT RET0.CA02_TAX_DUE, RET0.CA02_RETURN_VERSION
    FROM RET.TA02_RETURNS RET0
    WHERE RET0.CA02_RETURN_ID = %s
    ''' % ret
    taxdues = set()
    crs.execute(q)
    for r in crs:
        taxdue, _ = r
        taxdues.add(int(taxdue))
    if len(taxdues) < 2:
        print("returns of tin have same amount of data so THIS IS NOT A BUG")
        sys.exit(0)

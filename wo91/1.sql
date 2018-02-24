SELECT
	"TAX_RETURNS"."CSTD_RETURN_TYPE",
	"TAX_RETURNS"."CA02_TAX_YEAR",
	TO_CHAR( SYSDATE, 'yyyy/mm/dd', 'NLS_CALENDAR=Persian' ),
	(
		SELECT
			MAX( tin )
		FROM
			taxpayer_info_for_simple
		WHERE
			internalid = "TAX_RETURNS"."CR01_INTERNAL_ID"
	),
	(
		SELECT
			MAX( tradename )
		FROM
			taxpayer_info_for_simple
		WHERE
			internalid = "TAX_RETURNS"."CR01_INTERNAL_ID"
	),
	(
		SELECT
			MAX( TO_CHAR( national_id ))
		FROM
			taxpayer_info_for_simple
		WHERE
			internalid = "TAX_RETURNS"."CR01_INTERNAL_ID"
			AND taxpayerCategory = 'Y'
	),
	GETGTOFROMOFFICEID(
		(
			SELECT
				MAX( taxOffice )
			FROM
				taxpayer_info_for_simple
			WHERE
				internalid = "TAX_RETURNS"."CR01_INTERNAL_ID"
		)
	),
	(
		SELECT
			cs04_name
		FROM
			ts04_office
		WHERE
			cs04_id =(
				SELECT
					MAX( taxOffice )
				FROM
					taxpayer_info_for_simple
				WHERE
					internalid = "TAX_RETURNS"."CR01_INTERNAL_ID"
			)
	),
	(
		SELECT
			CS04_PHONE
		FROM
			TS04_OFFICE
		WHERE
			CS04_ID =(
				SELECT
					MAX( taxOffice )
				FROM
					taxpayer_info_for_simple
				WHERE
					internalid = "TAX_RETURNS"."CR01_INTERNAL_ID"
			)
	),
	(
		SELECT
			MAX( 
				getdescriptionbyid( 'FA', GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_country' ), 'COUNTRY' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_country' ) IS NULL THEN '' ELSE ' ' END ||
                getdescriptionbyid( 'FA', GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_region' ), 'REGION' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_region' ) IS NULL THEN '' ELSE ' ' END ||
                getdescriptionbyid( 'FA', GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_city' ), 'CITY' ) || ',' ||
                getdescriptionbyid( 'FA', GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_district' ), 'DISTRICT' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_district' ) IS NULL THEN '' ELSE ' ' END ||
                GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_village' ) || ',' ||
                GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_lot_number' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_lot_number' ) IS NULL THEN '' ELSE ',' END ||
                GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_square' ) || ',' || CASE WHEN GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_square' ) IS NULL THEN '' ELSE ',' END ||
                GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_phase' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_phase' ) IS NULL THEN '' ELSE ',' END || 
                GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_complex' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_complex' ) IS NULL THEN '' ELSE ',' END || 
                GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_alley' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_alley' ) IS NULL THEN '' ELSE ',' END || 
                GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_second_street' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_second_street' ) IS NULL THEN '' ELSE ',' END || 
                GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_street_name' ) || ' ' || 
                GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_street_no' ) || ',' || 
                GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_building' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_building' ) IS NULL THEN '' ELSE ' ' END || 
                GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_staircase' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_staircase' ) IS NULL THEN '' ELSE ' ' END || 
                GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_floor' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_floor' ) IS NULL THEN '' ELSE ' ' END || 
                GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_room' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_room' ) IS NULL THEN '*' ELSE ' *' END || 
                GETELEMENTBYTAGNAMECLOB( "TS04_OFFICE"."CS04_POSTAL_ADR", 'ts06_pin_code' ))
		FROM
			TS04_OFFICE
		WHERE
			CS04_ID =(
				SELECT
					MAX( taxOffice )
				FROM
					taxpayer_info_for_simple
				WHERE
					internalid = "TAX_RETURNS"."CR01_INTERNAL_ID"
			)
	),
	(
		SELECT
			MAX( 
				getdescriptionbyid( 'FA', GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'country' ), 'COUNTRY' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'country' ) IS NULL THEN '' ELSE ' ' END || 
				getdescriptionbyid( 'FA', GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'province' ), 'REGION' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'province' ) IS NULL THEN '' ELSE ' ' END || 
				getdescriptionbyid( 'FA', GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'city' ), 'CITY' )|| ',' || 
				getdescriptionbyid( 'FA', GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'district' ), 'DISTRICT' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'district' ) IS NULL THEN '' ELSE ' ' END || 
				GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'village' ) || ',' || 
				GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'lotNumber' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'lotNumber' ) IS NULL THEN '' ELSE ',' END || 
				GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'square' ) || ',' || CASE WHEN GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'square' ) IS NULL THEN '' ELSE ',' END || 
				GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'phase' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'phase' ) IS NULL THEN '' ELSE ',' END || 
				GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'complex' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'complex' ) IS NULL THEN '' ELSE ',' END || 
				GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'alley' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'alley' ) IS NULL THEN '' ELSE ',' END || 
				GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'secondStreet' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'secondStreet' ) IS NULL THEN '' ELSE ',' END || 
				GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'streetName' ) || ' ' || 
				GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'streetNumber' ) || ',' || 
				GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'building' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'building' ) IS NULL THEN '' ELSE ' ' END || 
				GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'staircase' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'staircase' ) IS NULL THEN '' ELSE ' ' END || 
				GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'floor' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'floor' ) IS NULL THEN '' ELSE ' ' END || 
				GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'room' ) || CASE WHEN GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'room' ) IS NULL THEN '*' ELSE ' *' END || 
				GETELEMENTBYTAGNAMECLOB( "BRANCH_ADDRESS"."POST_ADDR", 'pinCode' ))
		FROM
			BRANCH_ADDRESS
		WHERE
			INTERNALID = "TAX_RETURNS"."CR01_INTERNAL_ID"
			AND branchcode = "TAX_RETURNS"."CR03_BRANCH_CODE"
	),
	(
		SELECT
			GETELEMENTBYTAGNAMECLOB(
				"BRANCH_ADDRESS"."POST_ADDR",
				'pinCode'
			)
		FROM
			branch_address
		WHERE
			INTERNALID = "TAX_RETURNS"."CR01_INTERNAL_ID"
			AND BRANCHCODE = "TAX_RETURNS"."CR03_BRANCH_CODE"
	),
	(
		SELECT
			GETELEMENTBYTAGNAMECLOB(
				"TS04_OFFICE"."CS04_POSTAL_ADR",
				'ts06_pin_code'
			)
		FROM
			TS04_OFFICE
		WHERE
			CS04_ID =(
				SELECT
					MAX( taxOffice )
				FROM
					taxpayer_info_for_simple
				WHERE
					internalid = "TAX_RETURNS"."CR01_INTERNAL_ID"
			)
	),
	(
		SELECT
			FIRSTNAME || ' ' || LASTNAME AS NAME
		FROM
			TAXPAYER_INFO_FOR_SIMPLE
		WHERE
			INTERNALID = "TAX_RETURNS"."CR01_INTERNAL_ID"
	),
	TO_CHAR( "TAX_RETURNS"."CA02_RETURN_ID" ),
	TO_CHAR( "TAX_RETURNS"."CA02_RETURN_VERSION" ),
	TO_CHAR( "TAX_RETURNS"."CA02_TAX_PERIOD_TO", 'yyyy/mm/dd', 'NLS_CALENDAR=Persian' ),
	TO_CHAR( "TAX_RETURNS"."CA02_TAX_PERIOD_FROM", 'yyyy/mm/dd', 'NLS_CALENDAR=Persian' ),
	(
		SELECT
			MAX( taxpayerCategory )
		FROM
			taxpayer_info_for_simple
		WHERE
			internalid = "TAX_RETURNS"."CR01_INTERNAL_ID"
	),
	(
		SELECT
			MAX( TO_CHAR( reg_number ))
		FROM
			taxpayer_info_for_simple
		WHERE
			internalid = "TAX_RETURNS"."CR01_INTERNAL_ID"
			AND taxpayerCategory = 'N'
	),
	(
		SELECT
			NAME
		FROM
			TAXPAYER_INFO_FOR_SIMPLE
		WHERE
			INTERNALID = "TAX_RETURNS"."CR01_INTERNAL_ID"
	),
	(
		SELECT
			activity_code
		FROM
			activity_code
		WHERE
			internalId = "TAX_RETURNS"."CR01_INTERNAL_ID"
			AND branch_code = "TAX_RETURNS"."CR03_BRANCH_CODE"
	),
	(
		SELECT
			MAX( FIXED_PHONE )
		FROM
			TAXPAYER_OFFICIALS
		WHERE
			INTERNALID = "TAX_RETURNS"."CR01_INTERNAL_ID"
			AND BRANCHCODE = "TAX_RETURNS"."CR03_BRANCH_CODE"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'line_no'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't4_tin'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't4_name'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't4_national_legal_id'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'total_profit_loss'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'net_profit_loss'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'profit_loss_adjustments'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'age_profit_loss_adjustments'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'add_deduct_profit_loss'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'total_exempt_income'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'total_benefit_fix_tax_paid'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'total_exempt_revenue'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'total_financial_aids'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'total_financial_aids_paid'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'total_deductions'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'total_adjustment'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'current_year_loss'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'total_gross_income_before_ecxemption'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'taxable_income_after_deductions'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'ecxemption_artic_101'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'cumulated_losses_prev_years'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'cumulated_losses_prev_years_partnership'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'cumulated_loss_prev_years'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'losses_incurred_execution_provisions'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'gross_taxable_income'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getsumvalue(
				ca03_return_content,
				'net_benefit'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getsumvalue(
				ca03_return_content,
				'exemption'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'tbl1_sum_of_investors_share_from_profit'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'chamber_of_commerce_portion'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'unions_and_professional_societies_portion'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'tbl1_sum_of_exemptions'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'net_taxable_income'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'total_taxable_income'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'total_net_benefit'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_sum_net_profit_loss'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'basis_for_witholding_tax_on_real_state_rental'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'basis_for_withholding_tax_on_contractors_and_service_providers'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'basis_for_withholding_tax_on_contractors_of_non_iranian_residing_outside_iran'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'basis_for_stock_transfer_143_bis'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'basis_for_foreign_trasportation_institutions_subject_113'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'tbl1_sum_of_investors_taxable_profit'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'table2_taxable_basis'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_tax_calculation_basis_amount'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_tax_calculation_basis_tax'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_long_term_investment_corporations_accepted'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_long_term_investment_in_the_shares_of_corporations_accepted'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_short_term_investment_corporations_accepted_exchange_market'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_short_term_investment_in_the_shares_of_corporations_accepted'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_long_term_investment_shares_of_other_corporations'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_long_term_investment_in_the_shares_of_other_corporations_tax'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_short_term_investment_shares_of_other_corporations_amount'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_short_term_investment_in_the_shares_of_other_corporations_tax'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_immovable_fix_tangable_assets'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_real_estate_properties_tax'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_intangable_assets_goodwill'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_goodwill_transfer_tax'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_total_amount'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_total_tax'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				's5_total_transactional_value_of_land_and_building_to_be_transferred_after_application_of_the_ownership_share_coefficient'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				's5_transactional_value_of_building_to_be_transferred_after_application_of_the_ownership_share_coefficient'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				's5_day_value_of_goodwill_to_be_transferred_after_application_of_the_ownership_share_coefficient'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				's5_exemption_land_building'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				's5_exemption_goodwill'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				's5_taxable_basis_land_building'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				's5_taxable_basis_goodwill'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				's5_real_estate_Transfer_tax'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				's5_tax_subjected_to_Art_77_of_DTA'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				's5_goodwill_transfer_tax'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	TO_CHAR( "TAX_RETURNS"."CR01_INTERNAL_ID" ),
	(
		SELECT
			getdescriptionbyid(
				'FA',
				cstd_return_type,
				'ENTITY_TYPE'
			)
		FROM
			ta02_returns
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				't1_total_net_benefit'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'total_exemption'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'total_diffrential_value_properties_bartered'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'taxpayer_type'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			getreturncontentbyid(
				ca03_return_content,
				'total_taxable_benefit'
			)
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			LTRIM( TO_CHAR( getreturncontentbyid( ca03_return_content, 'tax_due' ), '00,000,000,000,000,000,000.00' ))
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			LTRIM( TO_CHAR( getreturncontentbyid( ca03_return_content, 'net_tax_due' ), '00,000,000,000,000,000,000.00' ))
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			LTRIM( TO_CHAR( getreturncontentbyid( ca03_return_content, 's4_total_value_of_applicable_tax' ), '00,000,000,000,000,000,000.00' ))
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			LTRIM( TO_CHAR( getreturncontentbyid( ca03_return_content, 'applicabe_tax' ), '00,000,000,000,000,000,000.00' ))
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			LTRIM( TO_CHAR( getreturncontentbyid( ca03_return_content, 'total_applicable_tax_from_1_2' ), '00,000,000,000,000,000,000.00' ))
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			LTRIM( TO_CHAR( getreturncontentbyid( ca03_return_content, 's1_net_taxable_income' ), '00,000,000,000,000,000,000.00' ))
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			LTRIM( TO_CHAR( getreturncontentbyid( ca03_return_content, 'payable_tax' ), '00,000,000,000,000,000,000.00' ))
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			LTRIM( TO_CHAR( getreturncontentbyid( ca03_return_content, 'exemptions_tax_rebates' ), '00,000,000,000,000,000,000.00' ))
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			LTRIM( TO_CHAR( getreturncontentbyid( ca03_return_content, 'art92_less_developed_deductions' ), '00,000,000,000,000,000,000.00' ))
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			LTRIM( TO_CHAR( getreturncontentbyid( ca03_return_content, 'exemption_caluse_14' ), '00,000,000,000,000,000,000.00' ))
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			LTRIM( TO_CHAR( getreturncontentbyid( ca03_return_content, 'foreign_nationals_subject_to_double_taxation_avoidance' ), '00,000,000,000,000,000,000.00' ))
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			LTRIM( TO_CHAR( getreturncontentbyid( ca03_return_content, 'tax_rebates' ), '00,000,000,000,000,000,000.00' ))
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			LTRIM( TO_CHAR( getreturncontentbyid( ca03_return_content, 'total_exemption' ), '00,000,000,000,000,000,000.00' ))
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	),
	(
		SELECT
			TO_CHAR( getreturncontentbyid( ca03_return_content, 'discount_applicable_131_exemption_rebate' ), '00,000,000,000,000,000,000.00' )
		FROM
			tax_returns_content
		WHERE
			ca02_return_id = "TAX_RETURNS"."CA02_RETURN_ID"
			AND ca02_return_version = "TAX_RETURNS"."CA02_RETURN_VERSION"
	)
FROM
	"RET"."TA02_RETURNS" "TAX_RETURNS"
WHERE
	TO_CHAR( "TAX_RETURNS"."CA02_RETURN_ID" )= '1469816'
	AND TO_CHAR( "TAX_RETURNS"."CA02_RETURN_VERSION" )= '2'

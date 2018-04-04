package com.gearoenix;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Semaphore;

class FTN {
    String cstd_return_type = "";
    String sysdate = "";
    String ca02_tax_year = "";
    String cr01_tin_id = "";
    String cr13_trade_name = "";
    String cr04_national_id = "";
    String gto_from_office_id = "";
    String cs04_name = "";
    String cs04_phone = "";
    String cs04_postal_adr = "";
    String cr20_postal_address = "";
    String cr11_first_name = "";
    String cr11_second_name = "";
    String ca02_return_id = "";
    String ca02_return_version = "";
    String ca02_tax_period_to = "";
    String ca02_tax_period_from = "";
    String cr01_natural_per_flag = "";
    String cr10_reg_number = "";
    String cr13_name = "";
    String cstd_activity_code = "";
    String cr25_fixed_phone = "";
    String ca03_return_content = "";
    String cc03_id = "";
    String issuance_reason = "";
    private String ts06_country = "";
    private String ts06_region = "";
    private String ts06_city = "";
    private String ts06_district = "";
    private String ts06_village = "";
    private String ts06_lot_number = "";
    private String ts06_square = "";
    private String ts06_phase = "";
    private String ts06_complex = "";
    private String ts06_alley = "";
    private String ts06_second_street = "";
    private String ts06_street_name = "";
    private String ts06_street_no = "";
    private String ts06_building = "";
    private String ts06_staircase = "";
    private String ts06_floor = "";
    private String ts06_room = "";
    private String ts06_pin_code = "";
    private String cr20_b_country = "";
    private String cr20_b_province = "";
    private String cr20_b_city = "";
    private String cr20_b_district = "";
    private String cr20_b_village = "";
    private String cr20_b_lot_number = "";
    private String cr20_b_square = "";
    private String cr20_b_phase = "";
    private String cr20_b_complex = "";
    private String cr20_b_alley = "";
    private String cr20_b_second_street = "";
    private String cr20_b_street_name = "";
    private String cr20_b_street_no = "";
    private String cr20_b_building = "";
    private String cr20_b_staircase = "";
    private String cr20_b_floor = "";
    private String cr20_b_room = "";
    private String cr20_b_pin_code = "";
    private String ret_line_no = "";
    private String ret_t4_tin = "";
    private String ret_t4_name = "";
    private String ret_t4_national_legal_id = "";
    private String ret_total_profit_loss = "";
    private String ret_net_profit_loss = "";
    private String ret_profit_loss_adjustments = "";
    private String ret_age_profit_loss_adjustments = "";
    private String ret_add_deduct_profit_loss = "";
    private String ret_total_exempt_income = "";
    private String ret_total_benefit_fix_tax_paid = "";
    private String ret_total_exempt_revenue = "";
    private String ret_total_financial_aids = "";
    private String ret_total_financial_aids_paid = "";
    private String ret_total_deductions = "";
    private String ret_total_adjustment = "";
    private String ret_current_year_loss = "";
    private String ret_total_gross_income_before_ecxemption = "";
    private String ret_taxable_income_after_deductions = "";
    private String ret_ecxemption_artic_101 = "";
    private String ret_cumulated_losses_prev_years = "";
    private String ret_cumulated_losses_prev_years_partnership = "";
    private String ret_cumulated_loss_prev_years = "";
    private String ret_losses_incurred_execution_provisions = "";
    private String ret_gross_taxable_income = "";
    private String ret_net_benefit = "";
    private String ret_exemption = "";
    private String ret_tbl1_sum_of_investors_share_from_profit = "";
    private String ret_chamber_of_commerce_portion = "";
    private String ret_unions_and_professional_societies_portion = "";
    private String ret_tbl1_sum_of_exemptions = "";
    private String ret_net_taxable_income = "";
    private String ret_total_taxable_income = "";
    private String ret_total_net_benefit = "";
    private String ret_t1_sum_net_profit_loss = "";
    private String ret_basis_for_witholding_tax_on_real_state_rental = "";
    private String ret_basis_for_withholding_tax_on_contractors_and_service_providers = "";
    private String ret_basis_for_withholding_tax_on_contractors_of_non_iranian_residing_outside_iran = "";
    private String ret_basis_for_stock_transfer_143_bis = "";
    private String ret_basis_for_foreign_trasportation_institutions_subject_113 = "";
    private String ret_tbl1_sum_of_investors_taxable_profit = "";
    private String ret_table2_taxable_basis = "";
    private String ret_t1_tax_calculation_basis_amount = "";
    private String ret_t1_tax_calculation_basis_tax = "";
    private String ret_t1_long_term_investment_corporations_accepted = "";
    private String ret_t1_long_term_investment_in_the_shares_of_corporations_accepted = "";
    private String ret_t1_short_term_investment_corporations_accepted_exchange_market = "";
    private String ret_t1_short_term_investment_in_the_shares_of_corporations_accepted = "";
    private String ret_t1_long_term_investment_shares_of_other_corporations = "";
    private String ret_t1_long_term_investment_in_the_shares_of_other_corporations_tax = "";
    private String ret_t1_short_term_investment_shares_of_other_corporations_amount = "";
    private String ret_t1_short_term_investment_in_the_shares_of_other_corporations_tax = "";
    private String ret_t1_immovable_fix_tangable_assets = "";
    private String ret_t1_real_estate_properties_tax = "";
    private String ret_t1_intangable_assets_goodwill = "";
    private String ret_t1_goodwill_transfer_tax = "";
    private String ret_t1_total_amount = "";
    private String ret_t1_total_tax = "";
    private String ret_s5_total_transactional_value_of_land_and_building_to_be_transferred_after_application_of_the_ownership_share_coefficient = "";
    private String ret_s5_transactional_value_of_building_to_be_transferred_after_application_of_the_ownership_share_coefficient = "";
    private String ret_s5_day_value_of_goodwill_to_be_transferred_after_application_of_the_ownership_share_coefficient = "";
    private String ret_s5_exemption_land_building = "";
    private String ret_s5_exemption_goodwill = "";
    private String ret_s5_taxable_basis_land_building = "";
    private String ret_s5_taxable_basis_goodwill = "";
    private String ret_s5_real_estate_Transfer_tax = "";
    private String ret_s5_tax_subjected_to_Art_77_of_DTA = "";
    private String ret_s5_goodwill_transfer_tax = "";
    private String ret_cstd_return_type = "";
    private String ret_t1_total_net_benefit = "";
    private String ret_total_exemption = "";
    private String ret_total_diffrential_value_properties_bartered = "";
    private String ret_taxpayer_type = "";
    private String ret_total_taxable_benefit = "";
    private String ret_tax_due = "";
    private String ret_net_tax_due = "";
    private String ret_s4_total_value_of_applicable_tax = "";
    private String ret_applicabe_tax = "";
    private String ret_total_applicable_tax_from_1_2 = "";
    private String ret_s1_net_taxable_income = "";
    private String ret_payable_tax = "";
    private String ret_exemptions_tax_rebates = "";
    private String ret_art92_less_developed_deductions = "";
    private String ret_exemption_caluse_14 = "";
    private String ret_foreign_nationals_subject_to_double_taxation_avoidance = "";
    private String ret_tax_rebates = "";
    private String ret_discount_applicable_131_exemption_rebate = "";

    private static boolean initialized = false;
    private static Date last_initialization = new Date();
    private static Semaphore initialization_sem = new Semaphore(0);
    private static Map<String, String> fa_countries_desc = new HashMap<>();
    private static Map<String, String> fa_regions_desc = new HashMap<>();
    private static Map<String, String> fa_cities_desc = new HashMap<>();
    private static Map<String, String> fa_districts_desc = new HashMap<>();


    FTN() {
    }

    private void initialize() {
        Date now = new Date();
        if(!initialized || now.getTime() - last_initialization.getTime() < 9 * 1000) return;
        try {
            initialization_sem.acquire();
            try {
                fa_countries_desc.clear();
                fa_regions_desc.clear();
                fa_cities_desc.clear();
                fa_districts_desc.clear();
                initialized = true;
                last_initialization = now;
                ResultSet rs = Main.get_result_set(
                        "" +
                                "SELECT CODE_DESC, INTERNAL_CODE, GROUP_CODE " +
                                "FROM FRAMEWORK.STD_CODES_DESC" +
                                "WHERE" +
                                "    GROUP_CODE IN (" +
                                "        'COUNTRY'," +
                                "        'REGION'," +
                                "        'CITY'," +
                                "        'DISTRICT')" +
                                "    AND LANG_CODE = 'FA'" +
                                "");
                while(rs != null && rs.next()) {
                    String key = rs.getString("INTERNAL_CODE");
                    String val = rs.getString("CODE_DESC");
                    switch (rs.getString("GROUP_CODE"))
                    {
                        case "COUNTRY":
                            fa_countries_desc.put(key, val);
                            break;
                        case "REGION":
                            fa_regions_desc.put(key, val);
                            break;
                        case "CITY":
                            fa_cities_desc.put(key, val);
                            break;
                        case "DISTRICT":
                            fa_districts_desc.put(key, val);
                            break;
                    }
                }
            } catch( Exception e) {
                e.printStackTrace(); // todo log in here
            } finally {
                initialization_sem.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace(); // todo log in here
        }
    }

    private void parse_cs04_postal_adr() {
        if (cs04_postal_adr == null || cs04_postal_adr.isEmpty()) {
            return;
        }
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader streamReader;
        try {
            streamReader = inputFactory.createXMLStreamReader(new StringReader(cs04_postal_adr));
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return;
        }
        if (streamReader == null) return;
        try {
            while (streamReader.hasNext()) {
                if (streamReader.isStartElement()) {
                    switch (streamReader.getLocalName()) {
                        case "ts06_country": {
                            ts06_country = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_region": {
                            ts06_region = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_city": {
                            ts06_city = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_district": {
                            ts06_district = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_village": {
                            ts06_village = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_lot_number": {
                            ts06_lot_number = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_square": {
                            ts06_square = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_phase": {
                            ts06_phase = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_complex": {
                            ts06_complex = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_alley": {
                            ts06_alley = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_second_street": {
                            ts06_second_street = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_street_name": {
                            ts06_street_name = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_street_no": {
                            ts06_street_no = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_building": {
                            ts06_building = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_staircase": {
                            ts06_staircase = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_floor": {
                            ts06_floor = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_room": {
                            ts06_room = streamReader.getElementText().trim();
                            break;
                        }
                        case "ts06_pin_code": {
                            ts06_pin_code = streamReader.getElementText().trim();
                            break;
                        }
                    }
                }
                streamReader.next();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void parse_cr20_postal_adr() {
        if (cr20_postal_address == null || cr20_postal_address.isEmpty()) {
            return;
        }
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader streamReader;
        try {
            streamReader = inputFactory.createXMLStreamReader(new StringReader(cr20_postal_address));
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return;
        }
        if (streamReader == null) return;
        try {
            while (streamReader.hasNext()) {
                if (streamReader.isStartElement()) {
                    switch (streamReader.getLocalName()) {
                        case "country": {
                            cr20_b_country = streamReader.getElementText().trim();
                            break;
                        }
                        case "province": {
                            cr20_b_province = streamReader.getElementText().trim();
                            break;
                        }
                        case "city": {
                            cr20_b_city = streamReader.getElementText().trim();
                            break;
                        }
                        case "district": {
                            cr20_b_district = streamReader.getElementText().trim();
                            break;
                        }
                        case "village": {
                            cr20_b_village = streamReader.getElementText().trim();
                            break;
                        }
                        case "lotNumber": {
                            cr20_b_lot_number = streamReader.getElementText().trim();
                            break;
                        }
                        case "square": {
                            cr20_b_square = streamReader.getElementText().trim();
                            break;
                        }
                        case "phase": {
                            cr20_b_phase = streamReader.getElementText().trim();
                            break;
                        }
                        case "complex": {
                            cr20_b_complex = streamReader.getElementText().trim();
                            break;
                        }
                        case "alley": {
                            cr20_b_alley = streamReader.getElementText().trim();
                            break;
                        }
                        case "secondStreet": {
                            cr20_b_second_street = streamReader.getElementText().trim();
                            break;
                        }
                        case "streetName": {
                            cr20_b_street_name = streamReader.getElementText().trim();
                            break;
                        }
                        case "streetNumber": {
                            cr20_b_street_no = streamReader.getElementText().trim();
                            break;
                        }
                        case "building": {
                            cr20_b_building = streamReader.getElementText().trim();
                            break;
                        }
                        case "staircase": {
                            cr20_b_staircase = streamReader.getElementText().trim();
                            break;
                        }
                        case "floor": {
                            cr20_b_floor = streamReader.getElementText().trim();
                            break;
                        }
                        case "room": {
                            cr20_b_room = streamReader.getElementText().trim();
                            break;
                        }
                        case "pinCode": {
                            cr20_b_pin_code = streamReader.getElementText().trim();
                            break;
                        }
                    }
                }
                streamReader.next();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private String get_return_content_value(XMLStreamReader streamReader) {
        try {
            while (streamReader.hasNext()) {
                if (streamReader.isStartElement()) {
                    if(streamReader.getLocalName().equals("value")) {
                        return streamReader.getElementText().trim();
                    }
                }
                streamReader.next();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void parse_return_content() {
        if (ca03_return_content == null || ca03_return_content.isEmpty()) {
            return;
        }
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader streamReader;
        try {
            streamReader = inputFactory.createXMLStreamReader(new StringReader(ca03_return_content));
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return;
        }
        if (streamReader == null) return;
        try {
            while (streamReader.hasNext()) {
                if (streamReader.isStartElement()) {
                    if(streamReader.getLocalName().equals("id")) {
                        switch (streamReader.getElementText()) {
                            case "line_no": {
                                ret_line_no = get_return_content_value(streamReader);
                                break;
                            }
                            case "t4_tin": {
                                ret_t4_tin = get_return_content_value(streamReader);
                                break;
                            }
                            case "t4_name": {
                                ret_t4_name = get_return_content_value(streamReader);
                                break;
                            }
                            case "t4_national_legal_id": {
                                ret_t4_national_legal_id = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_profit_loss": {
                                ret_total_profit_loss = get_return_content_value(streamReader);
                                break;
                            }
                            case "net_profit_loss": {
                                ret_net_profit_loss = get_return_content_value(streamReader);
                                break;
                            }
                            case "profit_loss_adjustments": {
                                ret_profit_loss_adjustments = get_return_content_value(streamReader);
                                break;
                            }
                            case "age_profit_loss_adjustments": {
                                ret_age_profit_loss_adjustments = get_return_content_value(streamReader);
                                break;
                            }
                            case "add_deduct_profit_loss": {
                                ret_add_deduct_profit_loss = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_exempt_income": {
                                ret_total_exempt_income = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_benefit_fix_tax_paid": {
                                ret_total_benefit_fix_tax_paid = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_exempt_revenue": {
                                ret_total_exempt_revenue = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_financial_aids": {
                                ret_total_financial_aids = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_financial_aids_paid": {
                                ret_total_financial_aids_paid = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_deductions": {
                                ret_total_deductions = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_adjustment": {
                                ret_total_adjustment = get_return_content_value(streamReader);
                                break;
                            }
                            case "current_year_loss": {
                                ret_current_year_loss = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_gross_income_before_ecxemption": {
                                ret_total_gross_income_before_ecxemption = get_return_content_value(streamReader);
                                break;
                            }
                            case "taxable_income_after_deductions": {
                                ret_taxable_income_after_deductions = get_return_content_value(streamReader);
                                break;
                            }
                            case "ecxemption_artic_101": {
                                ret_ecxemption_artic_101 = get_return_content_value(streamReader);
                                break;
                            }
                            case "cumulated_losses_prev_years": {
                                ret_cumulated_losses_prev_years = get_return_content_value(streamReader);
                                break;
                            }
                            case "cumulated_losses_prev_years_partnership": {
                                ret_cumulated_losses_prev_years_partnership = get_return_content_value(streamReader);
                                break;
                            }
                            case "cumulated_loss_prev_years": {
                                ret_cumulated_loss_prev_years = get_return_content_value(streamReader);
                                break;
                            }
                            case "losses_incurred_execution_provisions": {
                                ret_losses_incurred_execution_provisions = get_return_content_value(streamReader);
                                break;
                            }
                            case "gross_taxable_income": {
                                ret_gross_taxable_income = get_return_content_value(streamReader);
                                break;
                            }
                            case "net_benefit": {
                                ret_net_benefit = get_return_content_value(streamReader);
                                break;
                            }
                            case "exemption": {
                                ret_exemption = get_return_content_value(streamReader);
                                break;
                            }
                            case "tbl1_sum_of_investors_share_from_profit": {
                                ret_tbl1_sum_of_investors_share_from_profit = get_return_content_value(streamReader);
                                break;
                            }
                            case "chamber_of_commerce_portion": {
                                ret_chamber_of_commerce_portion = get_return_content_value(streamReader);
                                break;
                            }
                            case "unions_and_professional_societies_portion": {
                                ret_unions_and_professional_societies_portion = get_return_content_value(streamReader);
                                break;
                            }
                            case "tbl1_sum_of_exemptions": {
                                ret_tbl1_sum_of_exemptions = get_return_content_value(streamReader);
                                break;
                            }
                            case "net_taxable_income": {
                                ret_net_taxable_income = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_taxable_income": {
                                ret_total_taxable_income = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_net_benefit": {
                                ret_total_net_benefit = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_sum_net_profit_loss": {
                                ret_t1_sum_net_profit_loss = get_return_content_value(streamReader);
                                break;
                            }
                            case "basis_for_witholding_tax_on_real_state_rental": {
                                ret_basis_for_witholding_tax_on_real_state_rental = get_return_content_value(streamReader);
                                break;
                            }
                            case "basis_for_withholding_tax_on_contractors_and_service_providers": {
                                ret_basis_for_withholding_tax_on_contractors_and_service_providers = get_return_content_value(streamReader);
                                break;
                            }
                            case "basis_for_withholding_tax_on_contractors_of_non_iranian_residing_outside_iran": {
                                ret_basis_for_withholding_tax_on_contractors_of_non_iranian_residing_outside_iran = get_return_content_value(streamReader);
                                break;
                            }
                            case "basis_for_stock_transfer_143_bis": {
                                ret_basis_for_stock_transfer_143_bis = get_return_content_value(streamReader);
                                break;
                            }
                            case "basis_for_foreign_trasportation_institutions_subject_113": {
                                ret_basis_for_foreign_trasportation_institutions_subject_113 = get_return_content_value(streamReader);
                                break;
                            }
                            case "tbl1_sum_of_investors_taxable_profit": {
                                ret_tbl1_sum_of_investors_taxable_profit = get_return_content_value(streamReader);
                                break;
                            }
                            case "table2_taxable_basis": {
                                ret_table2_taxable_basis = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_tax_calculation_basis_amount": {
                                ret_t1_tax_calculation_basis_amount = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_tax_calculation_basis_tax": {
                                ret_t1_tax_calculation_basis_tax = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_long_term_investment_corporations_accepted": {
                                ret_t1_long_term_investment_corporations_accepted = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_long_term_investment_in_the_shares_of_corporations_accepted": {
                                ret_t1_long_term_investment_in_the_shares_of_corporations_accepted = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_short_term_investment_corporations_accepted_exchange_market": {
                                ret_t1_short_term_investment_corporations_accepted_exchange_market = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_short_term_investment_in_the_shares_of_corporations_accepted": {
                                ret_t1_short_term_investment_in_the_shares_of_corporations_accepted = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_long_term_investment_shares_of_other_corporations": {
                                ret_t1_long_term_investment_shares_of_other_corporations = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_long_term_investment_in_the_shares_of_other_corporations_tax": {
                                ret_t1_long_term_investment_in_the_shares_of_other_corporations_tax = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_short_term_investment_shares_of_other_corporations_amount": {
                                ret_t1_short_term_investment_shares_of_other_corporations_amount = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_short_term_investment_in_the_shares_of_other_corporations_tax": {
                                ret_t1_short_term_investment_in_the_shares_of_other_corporations_tax = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_immovable_fix_tangable_assets": {
                                ret_t1_immovable_fix_tangable_assets = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_real_estate_properties_tax": {
                                ret_t1_real_estate_properties_tax = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_intangable_assets_goodwill": {
                                ret_t1_intangable_assets_goodwill = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_goodwill_transfer_tax": {
                                ret_t1_goodwill_transfer_tax = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_total_amount": {
                                ret_t1_total_amount = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_total_tax": {
                                ret_t1_total_tax = get_return_content_value(streamReader);
                                break;
                            }
                            case "s5_total_transactional_value_of_land_and_building_to_be_transferred_after_application_of_the_ownership_share_coefficient": {
                                ret_s5_total_transactional_value_of_land_and_building_to_be_transferred_after_application_of_the_ownership_share_coefficient = get_return_content_value(streamReader);
                                break;
                            }
                            case "s5_transactional_value_of_building_to_be_transferred_after_application_of_the_ownership_share_coefficient": {
                                ret_s5_transactional_value_of_building_to_be_transferred_after_application_of_the_ownership_share_coefficient = get_return_content_value(streamReader);
                                break;
                            }
                            case "s5_day_value_of_goodwill_to_be_transferred_after_application_of_the_ownership_share_coefficient": {
                                ret_s5_day_value_of_goodwill_to_be_transferred_after_application_of_the_ownership_share_coefficient = get_return_content_value(streamReader);
                                break;
                            }
                            case "s5_exemption_land_building": {
                                ret_s5_exemption_land_building = get_return_content_value(streamReader);
                                break;
                            }
                            case "s5_exemption_goodwill": {
                                ret_s5_exemption_goodwill = get_return_content_value(streamReader);
                                break;
                            }
                            case "s5_taxable_basis_land_building": {
                                ret_s5_taxable_basis_land_building = get_return_content_value(streamReader);
                                break;
                            }
                            case "s5_taxable_basis_goodwill": {
                                ret_s5_taxable_basis_goodwill = get_return_content_value(streamReader);
                                break;
                            }
                            case "s5_real_estate_Transfer_tax": {
                                ret_s5_real_estate_Transfer_tax = get_return_content_value(streamReader);
                                break;
                            }
                            case "s5_tax_subjected_to_Art_77_of_DTA": {
                                ret_s5_tax_subjected_to_Art_77_of_DTA = get_return_content_value(streamReader);
                                break;
                            }
                            case "s5_goodwill_transfer_tax": {
                                ret_s5_goodwill_transfer_tax = get_return_content_value(streamReader);
                                break;
                            }
                            case "cstd_return_type": {
                                ret_cstd_return_type = get_return_content_value(streamReader);
                                break;
                            }
                            case "t1_total_net_benefit": {
                                ret_t1_total_net_benefit = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_exemption": {
                                ret_total_exemption = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_diffrential_value_properties_bartered": {
                                ret_total_diffrential_value_properties_bartered = get_return_content_value(streamReader);
                                break;
                            }
                            case "taxpayer_type": {
                                ret_taxpayer_type = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_taxable_benefit": {
                                ret_total_taxable_benefit = get_return_content_value(streamReader);
                                break;
                            }
                            case "tax_due": {
                                ret_tax_due = get_return_content_value(streamReader);
                                break;
                            }
                            case "net_tax_due": {
                                ret_net_tax_due = get_return_content_value(streamReader);
                                break;
                            }
                            case "s4_total_value_of_applicable_tax": {
                                ret_s4_total_value_of_applicable_tax = get_return_content_value(streamReader);
                                break;
                            }
                            case "applicabe_tax": {
                                ret_applicabe_tax = get_return_content_value(streamReader);
                                break;
                            }
                            case "total_applicable_tax_from_1_2": {
                                ret_total_applicable_tax_from_1_2 = get_return_content_value(streamReader);
                                break;
                            }
                            case "s1_net_taxable_income": {
                                ret_s1_net_taxable_income = get_return_content_value(streamReader);
                                break;
                            }
                            case "payable_tax": {
                                ret_payable_tax = get_return_content_value(streamReader);
                                break;
                            }
                            case "exemptions_tax_rebates": {
                                ret_exemptions_tax_rebates = get_return_content_value(streamReader);
                                break;
                            }
                            case "art92_less_developed_deductions": {
                                ret_art92_less_developed_deductions = get_return_content_value(streamReader);
                                break;
                            }
                            case "exemption_caluse_14": {
                                ret_exemption_caluse_14 = get_return_content_value(streamReader);
                                break;
                            }
                            case "foreign_nationals_subject_to_double_taxation_avoidance": {
                                ret_foreign_nationals_subject_to_double_taxation_avoidance = get_return_content_value(streamReader);
                                break;
                            }
                            case "tax_rebates": {
                                ret_tax_rebates = get_return_content_value(streamReader);
                                break;
                            }
                            case "discount_applicable_131_exemption_rebate": {
                                ret_discount_applicable_131_exemption_rebate = get_return_content_value(streamReader);
                                break;
                            }
                        }
                    }
                }
                if(streamReader.hasNext())
                    streamReader.next();
                else break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean generate_xml() {
        initialize();
        parse_cs04_postal_adr();
        parse_cr20_postal_adr();
        parse_return_content();
        System.out.println(to_xml());
        return true;
    }

    void print() {
        System.out.println("cstd_return_type: " + cstd_return_type);
        System.out.println("sysdate: " + sysdate);
        System.out.println("ca02_tax_year: " + ca02_tax_year);
        System.out.println("cr01_tin_id: " + cr01_tin_id);
        System.out.println("cr13_trade_name: " + cr13_trade_name);
        System.out.println("cr04_national_id: " + cr04_national_id);
        System.out.println("gto_from_office_id: " + gto_from_office_id);
        System.out.println("cs04_name: " + cs04_name);
        System.out.println("cs04_phone: " + cs04_phone);
        System.out.println("cs04_postal_adr: " + cs04_postal_adr);
        System.out.println("cr20_postal_address: " + cr20_postal_address);
        System.out.println("cr11_first_name: " + cr11_first_name);
        System.out.println("cr11_second_name: " + cr11_second_name);
        System.out.println("ca02_return_id: " + ca02_return_id);
        System.out.println("ca02_return_version: " + ca02_return_version);
        System.out.println("ca02_tax_period_to: " + ca02_tax_period_to);
        System.out.println("ca02_tax_period_from: " + ca02_tax_period_from);
        System.out.println("cr01_natural_per_flag: " + cr01_natural_per_flag);
        System.out.println("cr10_reg_number: " + cr10_reg_number);
        System.out.println("cr13_name: " + cr13_name);
        System.out.println("cstd_activity_code: " + cstd_activity_code);
        System.out.println("cr25_fixed_phone: " + cr25_fixed_phone);
        System.out.println("ca03_return_content: " + ca03_return_content);
        System.out.println("ts06_country: " + ts06_country);
        System.out.println("ts06_region: " + ts06_region);
        System.out.println("ts06_city: " + ts06_city);
        System.out.println("ts06_district: " + ts06_district);
        System.out.println("ts06_village: " + ts06_village);
        System.out.println("ts06_lot_number: " + ts06_lot_number);
        System.out.println("ts06_square: " + ts06_square);
        System.out.println("ts06_phase: " + ts06_phase);
        System.out.println("ts06_complex: " + ts06_complex);
        System.out.println("ts06_alley: " + ts06_alley);
        System.out.println("ts06_second_street: " + ts06_second_street);
        System.out.println("ts06_street_name: " + ts06_street_name);
        System.out.println("ts06_street_no: " + ts06_street_no);
        System.out.println("ts06_building: " + ts06_building);
        System.out.println("ts06_staircase: " + ts06_staircase);
        System.out.println("ts06_floor: " + ts06_floor);
        System.out.println("ts06_room: " + ts06_room);
        System.out.println("ts06_pin_code: " + ts06_pin_code);
        System.out.println("cr20_b_country: " + cr20_b_country);
        System.out.println("cr20_b_province: " + cr20_b_province);
        System.out.println("cr20_b_city: " + cr20_b_city);
        System.out.println("cr20_b_district: " + cr20_b_district);
        System.out.println("cr20_b_village: " + cr20_b_village);
        System.out.println("cr20_b_lot_number: " + cr20_b_lot_number);
        System.out.println("cr20_b_square: " + cr20_b_square);
        System.out.println("cr20_b_phase: " + cr20_b_phase);
        System.out.println("cr20_b_complex: " + cr20_b_complex);
        System.out.println("cr20_b_alley: " + cr20_b_alley);
        System.out.println("cr20_b_second_street: " + cr20_b_second_street);
        System.out.println("cr20_b_street_name: " + cr20_b_street_name);
        System.out.println("cr20_b_street_no: " + cr20_b_street_no);
        System.out.println("cr20_b_building: " + cr20_b_building);
        System.out.println("cr20_b_staircase: " + cr20_b_staircase);
        System.out.println("cr20_b_floor: " + cr20_b_floor);
        System.out.println("cr20_b_room: " + cr20_b_room);
        System.out.println("cr20_b_pin_code: " + cr20_b_pin_code);
        System.out.println("ret_line_no: " + ret_line_no);
        System.out.println("ret_t4_tin: " + ret_t4_tin);
        System.out.println("ret_t4_name: " + ret_t4_name);
        System.out.println("ret_t4_national_legal_id: " + ret_t4_national_legal_id);
        System.out.println("ret_total_profit_loss: " + ret_total_profit_loss);
        System.out.println("ret_net_profit_loss: " + ret_net_profit_loss);
        System.out.println("ret_profit_loss_adjustments: " + ret_profit_loss_adjustments);
        System.out.println("ret_age_profit_loss_adjustments: " + ret_age_profit_loss_adjustments);
        System.out.println("ret_add_deduct_profit_loss: " + ret_add_deduct_profit_loss);
        System.out.println("ret_total_exempt_income: " + ret_total_exempt_income);
        System.out.println("ret_total_benefit_fix_tax_paid: " + ret_total_benefit_fix_tax_paid);
        System.out.println("ret_total_exempt_revenue: " + ret_total_exempt_revenue);
        System.out.println("ret_total_financial_aids: " + ret_total_financial_aids);
        System.out.println("ret_total_financial_aids_paid: " + ret_total_financial_aids_paid);
        System.out.println("ret_total_deductions: " + ret_total_deductions);
        System.out.println("ret_total_adjustment: " + ret_total_adjustment);
        System.out.println("ret_current_year_loss: " + ret_current_year_loss);
        System.out.println("ret_total_gross_income_before_ecxemption: " + ret_total_gross_income_before_ecxemption);
        System.out.println("ret_taxable_income_after_deductions: " + ret_taxable_income_after_deductions);
        System.out.println("ret_ecxemption_artic_101: " + ret_ecxemption_artic_101);
        System.out.println("ret_cumulated_losses_prev_years: " + ret_cumulated_losses_prev_years);
        System.out.println("ret_cumulated_losses_prev_years_partnership: " + ret_cumulated_losses_prev_years_partnership);
        System.out.println("ret_cumulated_loss_prev_years: " + ret_cumulated_loss_prev_years);
        System.out.println("ret_losses_incurred_execution_provisions: " + ret_losses_incurred_execution_provisions);
        System.out.println("ret_gross_taxable_income: " + ret_gross_taxable_income);
        System.out.println("ret_net_benefit: " + ret_net_benefit);
        System.out.println("ret_exemption: " + ret_exemption);
        System.out.println("ret_tbl1_sum_of_investors_share_from_profit: " + ret_tbl1_sum_of_investors_share_from_profit);
        System.out.println("ret_chamber_of_commerce_portion: " + ret_chamber_of_commerce_portion);
        System.out.println("ret_unions_and_professional_societies_portion: " + ret_unions_and_professional_societies_portion);
        System.out.println("ret_tbl1_sum_of_exemptions: " + ret_tbl1_sum_of_exemptions);
        System.out.println("ret_net_taxable_income: " + ret_net_taxable_income);
        System.out.println("ret_total_taxable_income: " + ret_total_taxable_income);
        System.out.println("ret_total_net_benefit: " + ret_total_net_benefit);
        System.out.println("ret_t1_sum_net_profit_loss: " + ret_t1_sum_net_profit_loss);
        System.out.println("ret_basis_for_witholding_tax_on_real_state_rental: " + ret_basis_for_witholding_tax_on_real_state_rental);
        System.out.println("ret_basis_for_withholding_tax_on_contractors_and_service_providers: " + ret_basis_for_withholding_tax_on_contractors_and_service_providers);
        System.out.println("ret_basis_for_withholding_tax_on_contractors_of_non_iranian_residing_outside_iran: " + ret_basis_for_withholding_tax_on_contractors_of_non_iranian_residing_outside_iran);
        System.out.println("ret_basis_for_stock_transfer_143_bis: " + ret_basis_for_stock_transfer_143_bis);
        System.out.println("ret_basis_for_foreign_trasportation_institutions_subject_113: " + ret_basis_for_foreign_trasportation_institutions_subject_113);
        System.out.println("ret_tbl1_sum_of_investors_taxable_profit: " + ret_tbl1_sum_of_investors_taxable_profit);
        System.out.println("ret_table2_taxable_basis: " + ret_table2_taxable_basis);
        System.out.println("ret_t1_tax_calculation_basis_amount: " + ret_t1_tax_calculation_basis_amount);
        System.out.println("ret_t1_tax_calculation_basis_tax: " + ret_t1_tax_calculation_basis_tax);
        System.out.println("ret_t1_long_term_investment_corporations_accepted: " + ret_t1_long_term_investment_corporations_accepted);
        System.out.println("ret_t1_long_term_investment_in_the_shares_of_corporations_accepted: " + ret_t1_long_term_investment_in_the_shares_of_corporations_accepted);
        System.out.println("ret_t1_short_term_investment_corporations_accepted_exchange_market: " + ret_t1_short_term_investment_corporations_accepted_exchange_market);
        System.out.println("ret_t1_short_term_investment_in_the_shares_of_corporations_accepted: " + ret_t1_short_term_investment_in_the_shares_of_corporations_accepted);
        System.out.println("ret_t1_long_term_investment_shares_of_other_corporations: " + ret_t1_long_term_investment_shares_of_other_corporations);
        System.out.println("ret_t1_long_term_investment_in_the_shares_of_other_corporations_tax: " + ret_t1_long_term_investment_in_the_shares_of_other_corporations_tax);
        System.out.println("ret_t1_short_term_investment_shares_of_other_corporations_amount: " + ret_t1_short_term_investment_shares_of_other_corporations_amount);
        System.out.println("ret_t1_short_term_investment_in_the_shares_of_other_corporations_tax: " + ret_t1_short_term_investment_in_the_shares_of_other_corporations_tax);
        System.out.println("ret_t1_immovable_fix_tangable_assets: " + ret_t1_immovable_fix_tangable_assets);
        System.out.println("ret_t1_real_estate_properties_tax: " + ret_t1_real_estate_properties_tax);
        System.out.println("ret_t1_intangable_assets_goodwill: " + ret_t1_intangable_assets_goodwill);
        System.out.println("ret_t1_goodwill_transfer_tax: " + ret_t1_goodwill_transfer_tax);
        System.out.println("ret_t1_total_amount: " + ret_t1_total_amount);
        System.out.println("ret_t1_total_tax: " + ret_t1_total_tax);
        System.out.println("ret_s5_total_transactional_value_of_land_and_building_to_be_transferred_after_application_of_the_ownership_share_coefficient: " + ret_s5_total_transactional_value_of_land_and_building_to_be_transferred_after_application_of_the_ownership_share_coefficient);
        System.out.println("ret_s5_transactional_value_of_building_to_be_transferred_after_application_of_the_ownership_share_coefficient: " + ret_s5_transactional_value_of_building_to_be_transferred_after_application_of_the_ownership_share_coefficient);
        System.out.println("ret_s5_day_value_of_goodwill_to_be_transferred_after_application_of_the_ownership_share_coefficient: " + ret_s5_day_value_of_goodwill_to_be_transferred_after_application_of_the_ownership_share_coefficient);
        System.out.println("ret_s5_exemption_land_building: " + ret_s5_exemption_land_building);
        System.out.println("ret_s5_exemption_goodwill: " + ret_s5_exemption_goodwill);
        System.out.println("ret_s5_taxable_basis_land_building: " + ret_s5_taxable_basis_land_building);
        System.out.println("ret_s5_taxable_basis_goodwill: " + ret_s5_taxable_basis_goodwill);
        System.out.println("ret_s5_real_estate_Transfer_tax: " + ret_s5_real_estate_Transfer_tax);
        System.out.println("ret_s5_tax_subjected_to_Art_77_of_DTA: " + ret_s5_tax_subjected_to_Art_77_of_DTA);
        System.out.println("ret_s5_goodwill_transfer_tax: " + ret_s5_goodwill_transfer_tax);
        System.out.println("ret_cstd_return_type: " + ret_cstd_return_type);
        System.out.println("ret_t1_total_net_benefit: " + ret_t1_total_net_benefit);
        System.out.println("ret_total_exemption: " + ret_total_exemption);
        System.out.println("ret_total_diffrential_value_properties_bartered: " + ret_total_diffrential_value_properties_bartered);
        System.out.println("ret_taxpayer_type: " + ret_taxpayer_type);
        System.out.println("ret_total_taxable_benefit: " + ret_total_taxable_benefit);
        System.out.println("ret_tax_due: " + ret_tax_due);
        System.out.println("ret_net_tax_due: " + ret_net_tax_due);
        System.out.println("ret_s4_total_value_of_applicable_tax: " + ret_s4_total_value_of_applicable_tax);
        System.out.println("ret_applicabe_tax: " + ret_applicabe_tax);
        System.out.println("ret_total_applicable_tax_from_1_2: " + ret_total_applicable_tax_from_1_2);
        System.out.println("ret_s1_net_taxable_income: " + ret_s1_net_taxable_income);
        System.out.println("ret_payable_tax: " + ret_payable_tax);
        System.out.println("ret_exemptions_tax_rebates: " + ret_exemptions_tax_rebates);
        System.out.println("ret_art92_less_developed_deductions: " + ret_art92_less_developed_deductions);
        System.out.println("ret_exemption_caluse_14: " + ret_exemption_caluse_14);
        System.out.println("ret_foreign_nationals_subject_to_double_taxation_avoidance: " + ret_foreign_nationals_subject_to_double_taxation_avoidance);
        System.out.println("ret_tax_rebates: " + ret_tax_rebates);
        System.out.println("ret_discount_applicable_131_exemption_rebate: " + ret_discount_applicable_131_exemption_rebate);
    }

    private String create_element(int element_index, String v) {
        if(v.isEmpty())
            return "";
        final String e = convert_to_string(element_index);
        return "<" + e + ">" + v + "</" + e + ">";
    }

    private String to_xml() {
        StringBuilder result = new StringBuilder();
        String value;
        String tmp;
        //--------------------------------------------------------------------------------------------------------------
        if(cstd_return_type.equals("FRM24")) {
            String taxpayer_type = "";
            switch (ret_taxpayer_type) {
                case "01":
                    taxpayer_type = "الف";
                    break;
                case "02":
                    taxpayer_type = "ب";
                    break;
                case "03":
                    taxpayer_type = "ج";
                    break;
                case "04":
                    taxpayer_type = "ج- وسائط نقليه";
                    break;
            }
            result.append(create_element(1, cstd_return_type + " بند " + taxpayer_type));
        } else {
            result.append(create_element(1, cstd_return_type));
        }
        //--------------------------------------------------------------------------------------------------------------
        result.append(create_element(2, ca02_tax_year));
        result.append(create_element(3, ca02_tax_period_from));
        result.append(create_element(4, ca02_tax_period_to));
        result.append(create_element(5, cc03_id));
        result.append(create_element(6, sysdate));
        result.append(create_element(7, gto_from_office_id));
        result.append(create_element(8, cs04_name));
        result.append(create_element(9, cs04_phone));
        result.append(create_element(10, ts06_pin_code));
        // -------------------------------------------------------------------------------------------------------------
        value = "";
        tmp = fa_countries_desc.get(ts06_country);
        value += tmp.isEmpty() ? "": tmp + " ";
        tmp = fa_regions_desc.get(ts06_region);
        value += tmp.isEmpty() ? "": tmp + " ";
        tmp = fa_cities_desc.get(ts06_city);
        value += tmp.isEmpty() ? "": tmp + " ";
        tmp = fa_districts_desc.get(ts06_district);
        value += tmp.isEmpty() ? "": tmp + " ";
        value += ts06_village.isEmpty() ? "": ts06_village + " ";
        value += ts06_lot_number.isEmpty() ? "": ts06_lot_number + " ";
        value += ts06_square.isEmpty() ? "": ts06_square + " ";
        value += ts06_phase.isEmpty() ? "": ts06_phase + " ";
        value += ts06_complex.isEmpty() ? "": ts06_complex + " ";
        value += ts06_alley.isEmpty() ? "": ts06_alley + " ";
        value += ts06_second_street.isEmpty() ? "": ts06_second_street + " ";
        value += ts06_street_name.isEmpty() ? "": ts06_street_name + " ";
        value += ts06_street_no.isEmpty() ? "": ts06_street_no + " ";
        value += ts06_building.isEmpty() ? "": ts06_building + " ";
        value += ts06_staircase.isEmpty() ? "": ts06_staircase + " ";
        value += ts06_floor.isEmpty() ? "": ts06_floor + " ";
        value += ts06_room.isEmpty() ? "": ts06_room + " ";
        value += ts06_pin_code;
        result.append(create_element(11, value));
        // -------------------------------------------------------------------------------------------------------------
        result.append(create_element(12, issuance_reason));
        result.append(create_element(13, issuance_reason));
        result.append(create_element(25, cr01_tin_id));
        result.append(create_element(sysdate));
        result.append(create_element(ca02_tax_year));
        result.append(create_element(cr01_tin_id));
        result.append(create_element(cr13_trade_name));
        result.append(create_element(cr04_national_id));
        result.append(create_element(gto_from_office_id));
        result.append(create_element(cs04_name));
        result.append(create_element(cs04_phone));
        result.append(create_element(cs04_postal_adr));
        result.append(create_element(cr20_postal_address));
        result.append(create_element(cr11_first_name));
        result.append(create_element(cr11_second_name));
        result.append(create_element(ca02_return_id));
        result.append(create_element(ca02_return_version));
        result.append(create_element(ca02_tax_period_to));
        result.append(create_element(ca02_tax_period_from));
        result.append(create_element(cr01_natural_per_flag));
        result.append(create_element(cr10_reg_number));
        result.append(create_element(cr13_name));
        result.append(create_element(cstd_activity_code));
        result.append(create_element(cr25_fixed_phone));
        result.append(create_element(ca03_return_content));
        return result.toString();
    }

    private String convert_to_string(int i) {
        final int base = 52;
        StringBuilder result = new StringBuilder();
        for(int dom = i; dom != 0; dom /= base) {
            final int rem = dom % base;
            if(rem < 26) {
                result.append((char) (rem + ((int) 'A')));
            } else {
                result.append((char) ((rem - 26) + ((int) 'a')));
            }
        }
        return result.toString();
    }

}

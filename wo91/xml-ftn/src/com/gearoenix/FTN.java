package com.gearoenix;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;

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

    FTN() {
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

    boolean generate_xml() {
        parse_cs04_postal_adr();
        parse_cr20_postal_adr();
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
    }
}

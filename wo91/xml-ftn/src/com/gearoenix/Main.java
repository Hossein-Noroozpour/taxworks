package com.gearoenix;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import oracle.jdbc.driver.OracleDriver;

import java.sql.*;


public class Main extends Application {

    private static TextField db_address_tf;
    private static Label db_address_error_l;
    private static TextField db_user_tf;
    private static Label db_user_error_l;
    private static PasswordField password_pf;
    private static Label password_error_l;
    private static RadioButton one_rb;
    private static RadioButton all_rb;
    private static TextField return_id_tf;
    private static Label return_id_error_l;
    private static TextField return_version_tf;
    private static Label return_version_error_l;
    private static Label status_error_l;

    private static String db_address;
    private static String db_user;
    private static String db_password;

    private static String return_id;
    private static String return_version;

    private static Connection db_connection = null;

    private static final Color error_color = Color.rgb(160, 0, 0);

    private boolean db_connect() {
        try {
            db_connection = DriverManager.getConnection("jdbc:oracle:thin:@" + db_address, db_user, db_password);
        } catch (SQLException e) {
            db_address_error_l.setVisible(true);
            db_address_error_l.setManaged(true);
            db_user_error_l.setVisible(true);
            db_user_error_l.setManaged(true);
            password_error_l.setVisible(true);
            password_error_l.setManaged(true);
            return false;
        }
        if (db_connection == null) {
            db_address_error_l.setVisible(true);
            db_address_error_l.setManaged(true);
            db_user_error_l.setVisible(true);
            db_user_error_l.setManaged(true);
            password_error_l.setVisible(true);
            password_error_l.setManaged(true);
            return false;
        }
        return true;
    }

    @Override
    public void start(Stage stage) {

        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            System.err.println("Can not register oracle driver.");
            System.exit(-1);
        }

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Scene scene = new Scene(grid, 500, 400);

        Label label1 = new Label("Database address: ");
        grid.add(label1, 0, 0);

        db_address_tf = new TextField ();
        db_address_tf.setPromptText("ip:port:sid");
        db_address_tf.setMaxWidth(Double.MAX_VALUE);
        grid.add(db_address_tf, 1, 0);

        db_address_error_l = new Label("You didn't correctly enter DB address.");
        db_address_error_l.setTextFill(error_color);
        grid.add(db_address_error_l, 1, 1);

        Label label2 = new Label("Username: ");
        grid.add(label2, 0, 2);

        db_user_tf = new TextField ();
        db_user_tf.setMaxWidth(Double.MAX_VALUE);
        grid.add(db_user_tf, 1, 2);

        db_user_error_l = new Label("You didn't correctly enter DB username.");
        db_user_error_l.setTextFill(error_color);
        grid.add(db_user_error_l, 1, 3);

        Label label3 = new Label("Password: ");
        grid.add(label3, 0, 4);

        password_pf = new PasswordField();
        password_pf.setMaxWidth(Double.MAX_VALUE);
        password_pf.setPromptText("Database password");
        grid.add(password_pf, 1, 4);

        password_error_l = new Label("You didn't correctly enter DB password.");
        password_error_l.setTextFill(error_color);
        grid.add(password_error_l, 1, 5);

        final ToggleGroup mode_tg = new ToggleGroup();

        one_rb = new RadioButton("Generate one FTN");
        one_rb.setToggleGroup(mode_tg);
        one_rb.setSelected(true);
        grid.add(one_rb, 0, 6);

        all_rb = new RadioButton("Generate All FTNs");
        all_rb.setToggleGroup(mode_tg);
        grid.add(all_rb, 1, 6);

        Label label4 = new Label("Return ID: ");
        grid.add(label4, 0, 7);

        return_id_tf = new TextField ();
        return_id_tf.setMaxWidth(Double.MAX_VALUE);
        grid.add(return_id_tf, 1, 7);

        return_id_error_l = new Label("You didn't correctly enter Return ID.");
        return_id_error_l.setTextFill(error_color);
        grid.add(return_id_error_l, 1, 8);

        Label label5 = new Label("Return version: ");
        grid.add(label5, 0, 9);

        return_version_tf = new TextField ();
        return_version_tf.setMaxWidth(Double.MAX_VALUE);
        grid.add(return_version_tf, 1, 9);

        return_version_error_l = new Label("You didn't correctly enter Return Version.");
        return_version_error_l.setTextFill(error_color);
        grid.add(return_version_error_l, 1, 10);

        mode_tg.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
                    if(new_toggle.equals(all_rb)) {
                        label4.setVisible(false);
                        label4.setManaged(false);
                        return_id_tf.setVisible(false);
                        return_id_tf.setManaged(false);
                        label5.setVisible(false);
                        label5.setManaged(false);
                        return_version_tf.setVisible(false);
                        return_version_tf.setManaged(false);

                    } else {
                        label4.setVisible(true);
                        label4.setManaged(true);
                        return_id_tf.setVisible(true);
                        return_id_tf.setManaged(true);
                        label5.setVisible(true);
                        label5.setManaged(true);
                        return_version_tf.setVisible(true);
                        return_version_tf.setManaged(true);
                    }
                });

        Button exe_b = new Button("Generate XML(s)");
        exe_b.setMaxWidth(Double.MAX_VALUE);
        grid.add(exe_b, 0, 11, 2, 1);
        exe_b.setOnMouseClicked((mouse_event) -> execute());

        status_error_l = new Label("");
        status_error_l.setTextFill(error_color);
        grid.add(status_error_l, 0, 12, 2, 1);

        for (int colIndex = 0; colIndex < grid.getColumnCount(); colIndex++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS);
            cc.setFillWidth(true);
            grid.getColumnConstraints().add(cc);
        }

        clear_errors();

        stage.show();
        stage.setTitle("XML exporter for FTN letter");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private static void show_status_error(String msg) {
        status_error_l.setText(msg);
        status_error_l.setVisible(true);
        status_error_l.setManaged(true);
    }

    private void clear_errors() {
        db_address_error_l.setVisible(false);
        db_user_error_l.setVisible(false);
        password_error_l.setVisible(false);
        return_id_error_l.setVisible(false);
        return_version_error_l.setVisible(false);
        status_error_l.setVisible(false);
        db_address_error_l.setManaged(false);
        db_user_error_l.setManaged(false);
        password_error_l.setManaged(false);
        return_id_error_l.setManaged(false);
        return_version_error_l.setManaged(false);
        status_error_l.setManaged(false);
    }

    private static void terminate(String msg) {
        System.err.println(msg);
        System.exit(-1);
    }

    private void create_for_one_ftn() {
        Statement stmt = null;
        try {
            stmt = db_connection.createStatement();
        } catch (SQLException e) {
            terminate("Error in creating statement");
        }
        if(null == stmt)
            terminate("Statement is null.");
        ResultSet rs = null;
        try {
            assert stmt != null;
            rs = stmt.executeQuery(
                    "SELECT\n" +
                    "    RET2.CSTD_RETURN_TYPE                                                     F0,\n" +
                    "    RET2.CA02_TAX_YEAR                                                        F1,\n" +
                    "    TO_CHAR(SYSDATE, 'yyyy/mm/dd', 'NLS_CALENDAR=Persian')                    F2,\n" +
                    "    REG1.CR01_TIN_ID                                                          F3,\n" +
                    "    REG13.CR13_TRADE_NAME                                                     F4,\n" +
                    "    REG4.CR04_NATIONAL_ID                                                     F5,\n" +
                    "    GETGTOFROMOFFICEID(REG16.CS04_ID)                                         F6,\n" +
                    "    ADM4.CS04_NAME                                                            F7,\n" +
                    "    ADM4.CS04_PHONE                                                           F8,\n" +
                    "    ADM4.CS04_POSTAL_ADR                                                      F9,\n" +
                    "    REG20.CR20_POSTAL_ADDRESS                                                 F10,\n" +
                    "    REG11.CR11_FIRST_NAME                                                     F11,\n" +
                    "    REG11.CR11_SECOND_NAME                                                    F12,\n" +
                    "    RET2.CA02_RETURN_ID                                                       F13,\n" +
                    "    RET2.CA02_RETURN_VERSION                                                  F14,\n" +
                    "    TO_CHAR(RET2.CA02_TAX_PERIOD_TO, 'yyyy/mm/dd', 'NLS_CALENDAR=Persian')    F15,\n" +
                    "    TO_CHAR(RET2.CA02_TAX_PERIOD_FROM, 'yyyy/mm/dd', 'NLS_CALENDAR=Persian')  F16,\n" +
                    "    REG1.CR01_NATURAL_PER_FLAG                                                F17,\n" +
                    "    REG10.CR10_REG_NUMBER                                                     F18,\n" +
                    "    REG13.CR13_NAME                                                           F19,\n" +
                    "    REG6.CSTD_ACTIVITY_CODE                                                   F20,\n" +
                    "    REG25.CR25_FIXED_PHONE                                                    F21,\n" +
                    "    RET3.CA03_RETURN_CONTENT                                                  F22\n" +
                    "FROM\n" +
                    "    RET.TA02_RETURNS RET2\n" +
                    "    LEFT JOIN RET.TA03_RET_CONTENT RET3 ON RET3.CA02_RETURN_ID = RET2.CA02_RETURN_ID AND RET3.CA02_RETURN_VERSION = RET2.CA02_RETURN_VERSION\n" +
                    "    LEFT JOIN REG.TR01_TAXPAYER REG1 ON REG1.CR01_INTERNAL_ID = RET2.CR01_INTERNAL_ID\n" +
                    "    LEFT JOIN REG.TR04_NATURAL_PERSON REG4 ON REG1.CR04_NATURAL_PER_ID IS NOT NULL AND REG1.CR04_NATURAL_PER_ID = REG4.CR04_NATURAL_PER_ID\n" +
                    "    LEFT JOIN REG.TR06_BRANCH_BUSI_ACTIVITY REG6 ON RET2.CR01_INTERNAL_ID = REG6.CR01_INTERNAL_ID AND RET2.CR03_BRANCH_CODE = REG6.CR03_BRANCH_CODE AND REG6.CR06_END_DATE IS NULL\n" +
                    "    LEFT JOIN REG.TR10_LEGAL_PERSON REG10 ON REG1.CR10_LEGAL_PER_ID IS NOT NULL AND REG1.CR10_LEGAL_PER_ID = REG10.CR10_LEGAL_PER_ID\n" +
                    "    LEFT JOIN REG.TR11_NATURAL_PER_NAME REG11 ON REG1.CR04_NATURAL_PER_ID = REG11.CR04_NATURAL_PER_ID AND REG11.CR11_END_DATE IS NULL\n" +
                    "    LEFT JOIN REG.TR13_LEGAL_PER_NAME REG13 ON REG1.CR10_LEGAL_PER_ID = REG13.CR10_LEGAL_PER_ID AND REG13.CR13_END_DATE IS NULL\n" +
                    "    LEFT JOIN REG.TR16_TAXPAYER_OFFICE REG16 ON REG1.CR01_INTERNAL_ID = REG16.CR01_INTERNAL_ID AND REG16.CR16_END_DATE IS NULL\n" +
                    "    LEFT JOIN REG.TR17_BRANCH_NAME REG17 ON REG17.CR17_END_DATE IS NULL AND REG17.CR01_INTERNAL_ID = RET2.CR01_INTERNAL_ID AND RET2.CR03_BRANCH_CODE = REG17.CR03_BRANCH_CODE\n" +
                    "    LEFT JOIN REG.TR19_BRANCH_PHYSICAL_ADDR REG19 ON RET2.CR01_INTERNAL_ID = REG19.CR01_INTERNAL_ID AND RET2.CR03_BRANCH_CODE = REG19.CR03_BRANCH_CODE AND REG19.CR19_END_DATE IS NULL\n" +
                    "    LEFT JOIN REG.TR20_BRANCH_POSTAL_ADDR REG20 ON RET2.CR01_INTERNAL_ID = REG20.CR01_INTERNAL_ID AND RET2.CR03_BRANCH_CODE = REG20.CR03_BRANCH_CODE AND REG20.CR20_END_DATE IS NULL\n" +
                    "    LEFT JOIN REG.TR25_OFFICIALS REG25 ON RET2.CR01_INTERNAL_ID = REG25.CR01_INTERNAL_ID AND RET2.CR03_BRANCH_CODE = REG25.CR03_BRANCH_CODE\n" +
                    "    LEFT JOIN ADM.TS04_OFFICE ADM4 ON ADM4.CS04_ID = REG16.CS04_ID\n" +
                    "WHERE\n" +
                    "    RET2.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "    AND RET2.CA02_RETURN_VERSION = " + return_version);
        } catch (Exception e) {
            e.printStackTrace();
            terminate("Query didn't executed properly.");
        }
        try {
            if(rs == null || !rs.next()) {
                show_status_error("Query result set was empty. The ftn can not be generated.");
                return;
            }
        } catch (SQLException e) {
            terminate("Error in fetching next result in ftn query.");
        }
        System.out.println("" + rs);
        FTN ftn = new FTN();
        try {
            ftn.cstd_return_type = rs.getString("F0").trim();
        } catch (Exception ignored) {}
        try {
            ftn.ca02_tax_year = rs.getString("F1").trim();
        } catch (Exception ignored) {}
        try {
            ftn.sysdate = rs.getString("F2").trim();
        } catch (Exception ignored) {}
        try {
            ftn.cr01_tin_id = rs.getString("F3").trim();
        } catch (Exception ignored) {}
        try {
            ftn.cr13_trade_name = rs.getString("F4").trim();
        } catch (Exception ignored) {}
        try {
            ftn.cr04_national_id = rs.getString("F5").trim();
        } catch (Exception ignored) {}
        try {
            ftn.gto_from_office_id = rs.getString("F6").trim();
        } catch (Exception ignored) {}
        try {
            ftn.cs04_name = rs.getString("F7").trim();
        } catch (Exception ignored) {}
        try {
            ftn.cs04_phone = rs.getString("F8").trim();
        } catch (Exception ignored) {}
        try {
            ftn.cs04_postal_adr = rs.getString("F9").trim();
        } catch (Exception ignored) {}
        try {
            ftn.cr20_postal_address = rs.getString("F10").trim();
        } catch (Exception ignored) {}
        try {
            ftn.cr11_first_name = rs.getString("F11").trim();
        } catch (Exception ignored) {}
        try {
            ftn.cr11_second_name = rs.getString("F12").trim();
        } catch (Exception ignored) {}
        try {
            ftn.ca02_return_id = rs.getString("F13").trim();
        } catch (Exception ignored) {}
        try {
            ftn.ca02_return_version = rs.getString("F14").trim();
        } catch (Exception ignored) {}
        try {
            ftn.ca02_tax_period_to = rs.getString("F15").trim();
        } catch (Exception ignored) {}
        try {
            ftn.ca02_tax_period_from = rs.getString("F16").trim();
        } catch (Exception ignored) {}
        try {
            ftn.cr01_natural_per_flag = rs.getString("F17").trim();
        } catch (Exception ignored) {}
        try {
            ftn.cr10_reg_number = rs.getString("F18").trim();
        } catch (Exception ignored) {}
        try {
            ftn.cr13_name = rs.getString("F19").trim();
        } catch (Exception ignored) {}
        try {
            ftn.cstd_activity_code = rs.getString("F20").trim();
        } catch (Exception ignored) {}
        try {
            ftn.cr25_fixed_phone = rs.getString("F21").trim();
        } catch (Exception ignored) {}
        try {
            ftn.ca03_return_content = rs.getString("F22").trim();
        } catch (Exception ignored) {}
        try {
            if(rs.next()) {
                show_status_error("FTN query returned more than one result.");
                return;
            }
        } catch (SQLException ignored) { }
        if(!ftn.generate_xml()) {
            show_status_error("XML of FTN can not be generated.");
        }
        ftn.print();
    }

    private void create_all_ftns() {}

    private void execute() {
        clear_errors();
        db_address = db_address_tf.getText();
        if(db_address == null || db_address.isEmpty()) {
            db_address_error_l.setVisible(true);
            db_address_error_l.setManaged(true);
            return;
        }
        db_user = db_user_tf.getText();
        if(db_user == null || db_user.isEmpty()) {
            db_user_error_l.setVisible(true);
            db_user_error_l.setManaged(true);
            return;
        }
        db_password = password_pf.getText();
        if(db_password == null || db_password.isEmpty()) {
            password_error_l.setVisible(true);
            password_error_l.setManaged(true);
            return;
        }
        if(!db_connect()) return;
        if(one_rb.isSelected()) {
            return_id = return_id_tf.getText();
            if(return_id == null || return_id.isEmpty()) {
                return_id_error_l.setVisible(true);
                return_id_error_l.setManaged(true);
                return;
            }
            try {
                return_id = "" + Integer.parseInt(return_id);
            } catch (Exception e) {
                return_id_error_l.setVisible(true);
                return_id_error_l.setManaged(true);
                return;
            }
            return_version = return_version_tf.getText();
            if(return_version == null || return_version.isEmpty()) {
                return_version_error_l.setVisible(true);
                return_version_error_l.setManaged(true);
                return;
            }
            try {
                return_version = "" + Integer.parseInt(return_version);
            } catch (Exception e) {
                return_version_error_l.setVisible(true);
                return_version_error_l.setManaged(true);
                return;
            }
            create_for_one_ftn();
        } else {
            create_all_ftns();
        }
    }

    public static void main(String[] args) {
        Application.launch (args);
    }

    static ResultSet get_result_set(String query_text) {
        Statement stmt = null;
        try {
            stmt = db_connection.createStatement();
        } catch (SQLException e) {
            terminate("Error in creating statement");
        }
        if(null == stmt)
            terminate("Statement is null.");
        ResultSet rs = null;
        try {
            assert stmt != null;
            rs = stmt.executeQuery(query_text);
        } catch (Exception e) {
            e.printStackTrace();
            terminate("Query didn't executed properly.");
        }
        if(rs == null) {
            show_status_error("Query result set was empty. The ftn can not be generated.");
            return null;
        }
        return rs;
    }
}

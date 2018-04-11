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
    private static TextField letter_id_tf;
    private static TextField issuance_reason_tf;
    private static TextField request_no_tf;
    private static TextField txp_internal_id_tf;
    private static Label status_error_l;

    private static String db_address;
    private static String db_user;
    private static String db_password;

    private static String return_id;
    private static String return_version;
    private static String letter_id;
    private static String issuance_reason;
    private static String request_no;
    private static String txp_internal_id;

    private static ResultSet result_set;

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
            System.out.println("Error in connection1");
            return false;
        }
        if (db_connection == null) {
            db_address_error_l.setVisible(true);
            db_address_error_l.setManaged(true);
            db_user_error_l.setVisible(true);
            db_user_error_l.setManaged(true);
            password_error_l.setVisible(true);
            password_error_l.setManaged(true);
            System.out.println("Error in connection2");
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

        db_address_tf = new TextField();
        db_address_tf.setPromptText("ip:port:sid");
        db_address_tf.setMaxWidth(Double.MAX_VALUE);
        grid.add(db_address_tf, 1, 0);

        db_address_error_l = new Label("You didn't correctly enter DB address.");
        db_address_error_l.setTextFill(error_color);
        grid.add(db_address_error_l, 1, 1);

        Label label2 = new Label("Username: ");
        grid.add(label2, 0, 2);

        db_user_tf = new TextField();
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

        return_id_tf = new TextField();
        return_id_tf.setMaxWidth(Double.MAX_VALUE);
        grid.add(return_id_tf, 1, 7);

        return_id_error_l = new Label("You didn't correctly enter Return ID.");
        return_id_error_l.setTextFill(error_color);
        grid.add(return_id_error_l, 1, 8);

        Label label5 = new Label("Return version: ");
        grid.add(label5, 0, 9);

        return_version_tf = new TextField();
        return_version_tf.setMaxWidth(Double.MAX_VALUE);
        grid.add(return_version_tf, 1, 9);

        return_version_error_l = new Label("You didn't correctly enter Return Version.");
        return_version_error_l.setTextFill(error_color);
        grid.add(return_version_error_l, 1, 10);

        Label label6 = new Label("Letter ID: ");
        grid.add(label6, 0, 11);

        letter_id_tf = new TextField();
        letter_id_tf.setMaxWidth(Double.MAX_VALUE);
        grid.add(letter_id_tf, 1, 11);

        Label label7 = new Label("Issuance Reason: ");
        grid.add(label7, 0, 12);

        issuance_reason_tf = new TextField();
        issuance_reason_tf.setMaxWidth(Double.MAX_VALUE);
        grid.add(issuance_reason_tf, 1, 12);

        Label label8 = new Label("Request Number: ");
        grid.add(label8, 0, 13);

        request_no_tf = new TextField();
        request_no_tf.setMaxWidth(Double.MAX_VALUE);
        grid.add(request_no_tf, 1, 13);

        Label label9 = new Label("Taxpayer Internal ID: ");
        grid.add(label9, 0, 14);

        txp_internal_id_tf = new TextField();
        txp_internal_id_tf.setMaxWidth(Double.MAX_VALUE);
        grid.add(txp_internal_id_tf, 1, 14);

        mode_tg.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
                    if (new_toggle.equals(all_rb)) {
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
        grid.add(exe_b, 0, 15, 2, 1);
        exe_b.setOnMouseClicked((mouse_event) -> execute());

        status_error_l = new Label("");
        status_error_l.setTextFill(error_color);
        grid.add(status_error_l, 0, 16, 2, 1);

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

    private static String fetch(String cs) {
        try {
            return result_set.getString(cs).trim();
        } catch (Exception ignored) {
        }
        return "";
    }

    private void create_for_one_ftn() {
        Statement stmt = null;
        try {
            stmt = db_connection.createStatement();
        } catch (SQLException e) {
            terminate("Error in creating statement");
        }
        if (null == stmt)
            terminate("Statement is null.");
        String query_str = "";
        try {
            assert stmt != null;
            query_str = "SELECT\n" +
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
                    "    REG11.CR11_LAST_NAME                                                      F12,\n" +
                    "    RET2.CA02_RETURN_ID                                                       F13,\n" +
                    "    RET2.CA02_RETURN_VERSION                                                  F14,\n" +
                    "    TO_CHAR(RET2.CA02_TAX_PERIOD_TO, 'yyyy/mm/dd', 'NLS_CALENDAR=Persian')    F15,\n" +
                    "    TO_CHAR(RET2.CA02_TAX_PERIOD_FROM, 'yyyy/mm/dd', 'NLS_CALENDAR=Persian')  F16,\n" +
                    "    REG1.CR01_NATURAL_PER_FLAG                                                F17,\n" +
                    "    REG10.CR10_REG_NUMBER                                                     F18,\n" +
                    "    REG13.CR13_NAME                                                           F19,\n" +
                    "    REG6.CSTD_ACTIVITY_CODE                                                   F20,\n" +
                    "    REG25.CR25_FIXED_PHONE                                                    F21,\n" +
                    "    RET3.CA03_RETURN_CONTENT                                                  F22,\n" +
                    "    ISSUANCE.CC03_ID                                                          F23,\n" +
                    "    ISSUANCE.CC03_CREATE_ON                                                   F24,\n" +
                    "    PREVIOUS_FTN.CC03_ID                                                      F25,\n" +
                    "    PREVIOUS_FTN.CC03_CREATE_ON                                               F26,\n" +
                    "    PAID_TAX_FRM24.AMT_NUMBER                                                 F27,\n" +
                    "    PAID_TAX.AVAILABLE_AMT                                                    F28,\n" +
                    "    SUB_TAC_CE06_SUM_DISPLAY.UNPAID_AMT                                       F29,\n" +
                    "    SUB_TAC_CE06_DISPLAY_FLAG.UNPAID_AMT                                      F30\n" +
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
                    "    LEFT JOIN (\n" +
                    "        SELECT MAX(CC03_ID) CC03_ID, MAX(CC03_CREATE_ON) CC03_CREATE_ON, CR01_INTERNAL_ID\n" +
                    "        FROM (\n" +
                    "            SELECT CC03_ID, CC03_CREATE_ON, CR01_INTERNAL_ID\n" +
                    "            FROM\n" +
                    "                STL.TC03_LETTER STL3\n" +
                    "                JOIN OBJ.TO02_REQUEST_DETAILS OBJ2\n" +
                    "                    ON\n" +
                    "                        STL3.CC03_ENTITY_ID = OBJ2.CO01_REQUEST_NO\n" +
                    "                        AND OBJ2.CA02_RETURN_ID = " + return_id + "\n" +
                    "            WHERE\n" +
                    "                STL3.CSTD_ENTITY = 'OBJ'\n" +
                    "                AND STL3.CSTD_LETTER_TYPE IN ('LTR_OA01','LTR_OA02','LTR_OA12')\n" +
                    "            UNION\n" +
                    "            SELECT CC03_ID, CC03_CREATE_ON, CR01_INTERNAL_ID\n" +
                    "            FROM STL.TC03_LETTER STL3\n" +
                    "            WHERE\n" +
                    "                STL3.CC03_ENTITY_ID = '" + return_id + "-" + return_version + "'\n" +
                    "                AND STL3.CSTD_LETTER_TYPE IN ('LTR_RP12', 'LTR_AC07')\n" +
                    "        )\n" +
                    "        GROUP BY\n" +
                    "            CR01_INTERNAL_ID\n" +
                    "    ) ISSUANCE ON RET2.CR01_INTERNAL_ID = ISSUANCE.CR01_INTERNAL_ID,\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            MAX(TC03.CC03_ID) CC03_ID,\n" +
                    "            MAX(TC03.CC03_CREATE_ON) CC03_CREATE_ON\n" +
                    "        FROM\n" +
                    "            TC03_LETTER TC03\n" +
                    "            JOIN RET.TA02_RETURNS SRET2 ON\n" +
                    "                TC03.CR01_INTERNAL_ID = SRET2.CR01_INTERNAL_ID\n" +
                    "                AND TC03.CR03_BRANCH_CODE = SRET2.CR03_BRANCH_CODE\n" +
                    "                AND TC03.CC03_YEAR = SRET2.CA02_TAX_YEAR\n" +
                    "                AND TC03.CC03_FILING_PERIOD = SRET2.CA02_TAX_PERIOD\n" +
                    "                AND TC03.CA09_TAX_TYPE_CODE = SRET2.CA09_TAX_TYPE_CODE\n" +
                    "                AND SRET2.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                AND TC03.CSTD_LETTER_TYPE = 'LTR_EF02'\n" +
                    "                AND TC03.CC03_ID < " + letter_id + "\n" +
                    "    ) PREVIOUS_FTN,\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            SUM( AVAILABLE_AMT ) AMT_NUMBER\n" +
                    "        FROM\n" +
                    "            (\n" +
                    "                SELECT\n" +
                    "                    AMOUNT AS AVAILABLE_AMT\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                        SELECT\n" +
                    "                            SUM( AMOUNT ) AMOUNT\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CASE\n" +
                    "                                        WHEN TT01.CSTD_DC = 'DT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                    END AMOUNT\n" +
                    "                                FROM\n" +
                    "                                    TAC.TT01_TRANSACTIONS TT01\n" +
                    "                                JOIN FRAMEWORK.STD_CODES ON\n" +
                    "                                    TT01.CSTD_TRAN_TYPE = INTERNAL_CODE\n" +
                    "                                    AND GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                        'PA',\n" +
                    "                                        'RL'\n" +
                    "                                    )\n" +
                    "                                JOIN RET.TA02_RETURNS TA02 ON\n" +
                    "                                    TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                    AND TA02.CA02_RETURN_VERSION = " + return_version + "\n" +
                    "                                    AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                    AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                    AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                    AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                    AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                WHERE\n" +
                    "                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                    AND TT01.CSTD_LIABILITY_TYPE <> 'ASSLS'\n" +
                    "                                    AND TT01.CSTD_LIABILITY_TYPE <> 'RET186N2'\n" +
                    "                            UNION ALL SELECT\n" +
                    "                                    CASE\n" +
                    "                                        WHEN TT01.CSTD_DC = 'DT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                    END AMOUNT\n" +
                    "                                FROM\n" +
                    "                                    TT01_TRANSACTIONS TT01\n" +
                    "                                JOIN FRAMEWORK.STD_CODES ON\n" +
                    "                                    TT01.CSTD_TRAN_TYPE = INTERNAL_CODE\n" +
                    "                                    AND GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                        'PA',\n" +
                    "                                        'RL'\n" +
                    "                                    )\n" +
                    "                                WHERE\n" +
                    "                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                    AND TT01.CSTD_LIABILITY_TYPE <> 'ASSLS'\n" +
                    "                                    AND TT01.CSTD_LIABILITY_TYPE <> 'RET186N2'\n" +
                    "                                    AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                    AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                            ) TAB1\n" +
                    "                    ) TAB\n" +
                    "                WHERE\n" +
                    "                    TAB.AMOUNT > 0\n" +
                    "            ) AVAILABLE\n" +
                    "    )\n PAID_TAX_FRM24,\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            SUM( AVAILABLE_AMT ) AVAILABLE_AMT\n" +
                    "        FROM\n" +
                    "            (\n" +
                    "                SELECT\n" +
                    "                    AMOUNT AS AVAILABLE_AMT\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                        SELECT\n" +
                    "                            SUM( AMOUNT ) AMOUNT\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CASE\n" +
                    "                                        WHEN TT01.CSTD_DC = 'DT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                    END AMOUNT\n" +
                    "                                FROM\n" +
                    "                                    TT01_TRANSACTIONS TT01\n" +
                    "                                    JOIN FRAMEWORK.STD_CODES STD0 ON\n" +
                    "                                        TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE\n" +
                    "                                        AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                        AND STD0.PARENT_INTERNAL_CODE IN ('PA','RL')\n" +
                    "                                    JOIN RET.TA02_RETURNS TA02 ON\n" +
                    "                                        TA02.CA02_RETURN_ID = '" + return_id +"'\n" +
                    "                                        AND TA02.CA02_RETURN_VERSION = " + return_version + "\n" +
                    "                                        AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                        AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                        AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                        AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                        AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                WHERE\n" +
                    "                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                    AND TT01.CSTD_LIABILITY_TYPE <> 'ASSLS'\n" +
                    "                                UNION ALL SELECT\n" +
                    "                                    CASE\n" +
                    "                                        WHEN TT01.CSTD_DC = 'DT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                    END AMOUNT\n" +
                    "                                FROM\n" +
                    "                                    TT01_TRANSACTIONS TT01\n" +
                    "                                    JOIN FRAMEWORK.STD_CODES STD0 ON\n" +
                    "                                        TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE\n" +
                    "                                        AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                        AND STD0.PARENT_INTERNAL_CODE IN ('PA','RL')\n" +
                    "                                WHERE\n" +
                    "                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                    AND TT01.CSTD_LIABILITY_TYPE <> 'ASSLS'\n" +
                    "                                    AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                    AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                            ) TAB1\n" +
                    "                    ) TAB\n" +
                    "                WHERE\n" +
                    "                    TAB.AMOUNT > 0\n" +
                    "            ) AVAILABLE\n" +
                    "    ) PAID_TAX,\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            SUM( UNPAID_AMT ) UNPAID_AMT\n" +
                    "        FROM\n" +
                    "            (\n" +
                    "                SELECT\n" +
                    "                    SUM( UNPAID_AMT ) UNPAID_AMT,\n" +
                    "                    CODE_DESC\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                        SELECT\n" +
                    "                            UNPAID_AMT,\n" +
                    "                            CODE_DESC\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC AS CODE_DESC,\n" +
                    "                                    AMOUNT AS UNPAID_AMT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            CT01_DESC,\n" +
                    "                                            SUM( AMOUNT ) AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            (\n" +
                    "                                                SELECT\n" +
                    "                                                    TT01.CT01_DESC,\n" +
                    "                                                    CASE\n" +
                    "                                                        WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                                    END AMOUNT\n" +
                    "                                                FROM\n" +
                    "                                                    TT01_TRANSACTIONS TT01\n" +
                    "                                                JOIN FRAMEWORK.STD_CODES STD0 ON\n" +
                    "                                                    TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE\n" +
                    "                                                    AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND STD0.PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                                JOIN RET.TA02_RETURNS TA02 ON\n" +
                    "                                                    TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                                    AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                                    AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                                    AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                                    AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                                    AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                                    AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                                                WHERE\n" +
                    "                                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                                    AND TT01.CSTD_LIABILITY_TYPE IN(\n" +
                    "                                                        'LFPEN',\n" +
                    "                                                        'LRPEN',\n" +
                    "                                                        'LPPEN'\n" +
                    "                                                    )\n" +
                    "                                                    AND(\n" +
                    "                                                        TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                        OR(\n" +
                    "                                                            TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                                SELECT\n" +
                    "                                                                    TT28.CT28_ID\n" +
                    "                                                                FROM\n" +
                    "                                                                    TT28_PENALTY TT28\n" +
                    "                                                                WHERE\n" +
                    "                                                                    TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                                    AND TT28.CT28_ENTITY_ID = '" + return_id + "-" + return_version + "'\n" +
                    "                                                            )\n" +
                    "                                                        )\n" +
                    "                                                    )\n" +
                    "                                            UNION ALL SELECT\n" +
                    "                                                    TT01.CT01_DESC,\n" +
                    "                                                    CASE\n" +
                    "                                                        WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                                    END AMOUNT\n" +
                    "                                                FROM\n" +
                    "                                                    TT01_TRANSACTIONS TT01\n" +
                    "                                                JOIN FRAMEWORK.STD_CODES STD0 ON\n" +
                    "                                                    TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE\n" +
                    "                                                    AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND STD0.PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                                JOIN RET.TA02_RETURNS TA02 ON\n" +
                    "                                                    TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                                    AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                                    AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                                                WHERE\n" +
                    "                                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                                    AND TT01.CSTD_LIABILITY_TYPE IN(\n" +
                    "                                                        'LFPEN',\n" +
                    "                                                        'LRPEN',\n" +
                    "                                                        'LPPEN'\n" +
                    "                                                    )\n" +
                    "                                                    AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                                    AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                                                    AND(\n" +
                    "                                                        TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                        OR(\n" +
                    "                                                            TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                                SELECT\n" +
                    "                                                                    TT28.CT28_ID\n" +
                    "                                                                FROM\n" +
                    "                                                                    TT28_PENALTY TT28\n" +
                    "                                                                WHERE\n" +
                    "                                                                    TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                                    AND TT28.CT28_ENTITY_ID = '" + return_id + "-" + return_version + "'\n" +
                    "                                                            )\n" +
                    "                                                        )\n" +
                    "                                                    )\n" +
                    "                                            ) TAB1\n" +
                    "                                        GROUP BY\n" +
                    "                                            CT01_DESC\n" +
                    "                                    ) TAB\n" +
                    "                            ) UNPAID\n" +
                    "                    UNION ALL SELECT\n" +
                    "                            UNPAID_AMT,\n" +
                    "                            CODE_DESC\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC AS CODE_DESC,\n" +
                    "                                    AMOUNT AS UNPAID_AMT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            CT01_DESC,\n" +
                    "                                            SUM( AMOUNT ) AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            (\n" +
                    "                                                SELECT\n" +
                    "                                                    TT01.CT01_DESC,\n" +
                    "                                                    CASE\n" +
                    "                                                        WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                                    END AMOUNT\n" +
                    "                                                FROM\n" +
                    "                                                    TT01_TRANSACTIONS TT01\n" +
                    "                                                JOIN FRAMEWORK.STD_CODES STD0 ON\n" +
                    "                                                    TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE\n" +
                    "                                                    AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND STD0.PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                                JOIN RET.TA02_RETURNS TA02 ON\n" +
                    "                                                    TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                                    AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                                    AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                                    AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                                    AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                                    AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                                    AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                                                WHERE\n" +
                    "                                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                                    AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'\n" +
                    "                                                    AND(\n" +
                    "                                                        TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                        OR(\n" +
                    "                                                            TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                                SELECT\n" +
                    "                                                                    TT28.CT28_ID\n" +
                    "                                                                FROM\n" +
                    "                                                                    TT28_PENALTY TT28\n" +
                    "                                                                WHERE\n" +
                    "                                                                    TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                                    AND TT28.CT28_ENTITY_ID = '" + return_id + "-" + return_version + "'\n" +
                    "                                                            )\n" +
                    "                                                        )\n" +
                    "                                                    )\n" +
                    "                                            UNION ALL SELECT\n" +
                    "                                                    TT01.CT01_DESC,\n" +
                    "                                                    CASE\n" +
                    "                                                        WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                                    END AMOUNT\n" +
                    "                                                FROM\n" +
                    "                                                    TT01_TRANSACTIONS TT01\n" +
                    "                                                JOIN FRAMEWORK.STD_CODES STD0 ON\n" +
                    "                                                    TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE\n" +
                    "                                                    AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND STD0.PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                                JOIN RET.TA02_RETURNS TA02 ON\n" +
                    "                                                    TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                                    AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                                    AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                                                WHERE\n" +
                    "                                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                                    AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'\n" +
                    "                                                    AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                                    AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                                                    AND(\n" +
                    "                                                        TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                        OR(\n" +
                    "                                                            TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                                SELECT\n" +
                    "                                                                    TT28.CT28_ID\n" +
                    "                                                                FROM\n" +
                    "                                                                    TT28_PENALTY TT28\n" +
                    "                                                                WHERE\n" +
                    "                                                                    TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                                    AND TT28.CT28_ENTITY_ID = '" + return_id + "-" + return_version + "'\n" +
                    "                                                            )\n" +
                    "                                                        )\n" +
                    "                                                    )\n" +
                    "                                            ) TAB1\n" +
                    "                                        GROUP BY\n" +
                    "                                            CT01_DESC\n" +
                    "                                    ) TAB\n" +
                    "                            )\n" +
                    "                    UNION ALL SELECT\n" +
                    "                            UNPAID_AMT,\n" +
                    "                            CODE_DESC\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC AS CODE_DESC,\n" +
                    "                                    AMOUNT AS UNPAID_AMT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            CT01_DESC,\n" +
                    "                                            SUM( AMOUNT ) AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            (\n" +
                    "                                                SELECT\n" +
                    "                                                    TT01.CT01_DESC,\n" +
                    "                                                    CASE\n" +
                    "                                                        WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                                    END AMOUNT\n" +
                    "                                                FROM\n" +
                    "                                                    TT01_TRANSACTIONS TT01\n" +
                    "                                                JOIN FRAMEWORK.STD_CODES STD0 ON\n" +
                    "                                                    TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE\n" +
                    "                                                    AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND STD0.PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                                JOIN RET.TA02_RETURNS TA02 ON\n" +
                    "                                                    TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                                    AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                                    AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                                    AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                                    AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                                    AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                                    AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                                                WHERE\n" +
                    "                                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                                    AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'\n" +
                    "                                            UNION ALL SELECT\n" +
                    "                                                    TT01.CT01_DESC,\n" +
                    "                                                    CASE\n" +
                    "                                                        WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                                    END AMOUNT\n" +
                    "                                                FROM\n" +
                    "                                                    TT01_TRANSACTIONS TT01\n" +
                    "                                                JOIN FRAMEWORK.STD_CODES STD0 ON\n" +
                    "                                                    TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE\n" +
                    "                                                    AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND STD0.PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                                WHERE\n" +
                    "                                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                                    AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'\n" +
                    "                                                    AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                                    AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                                            ) TAB1\n" +
                    "                                        GROUP BY\n" +
                    "                                            CT01_DESC\n" +
                    "                                    ) TAB\n" +
                    "                            ) UNPAID\n" +
                    "                    UNION ALL SELECT\n" +
                    "                            UNPAID_AMT,\n" +
                    "                            CODE_DESC\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC AS CODE_DESC,\n" +
                    "                                    AMOUNT AS UNPAID_AMT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            CT01_DESC,\n" +
                    "                                            SUM( AMOUNT ) AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            (\n" +
                    "                                                SELECT\n" +
                    "                                                    TT01.CT01_DESC,\n" +
                    "                                                    CASE\n" +
                    "                                                        WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                                    END AMOUNT\n" +
                    "                                                FROM\n" +
                    "                                                    TT01_TRANSACTIONS TT01\n" +
                    "                                                JOIN FRAMEWORK.STD_CODES STD0 ON\n" +
                    "                                                    TT01.CSTD_TRAN_TYPE = STD0.INTERNAL_CODE\n" +
                    "                                                    AND STD0.GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND STD0.PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                                WHERE\n" +
                    "                                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                                    AND TT01.CSTD_LIABILITY_TYPE IN(\n" +
                    "                                                        'PEN247',\n" +
                    "                                                        'PEN247D'\n" +
                    "                                                    )\n" +
                    "                                                    AND TT01.CSTD_ALLOCATED_ENTITY = 'OBJ'\n" +
                    "                                                    AND TT01.CT01_ALLOCATED_ENTITYID = " + request_no + "\n" +
                    "                                            ) TAB1\n" +
                    "                                        GROUP BY\n" +
                    "                                            CT01_DESC\n" +
                    "                                    ) TAB\n" +
                    "                            )\n" +
                    "                    )\n" +
                    "                GROUP BY\n" +
                    "                    CODE_DESC\n" +
                    "            )\n" +
                    "    ) SUB_TAC_CE06_SUM_DISPLAY,\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            SUM( UNPAID_AMT ) UNPAID_AMT,\n" +
                    "            CODE_DESC\n" +
                    "        FROM\n" +
                    "            (\n" +
                    "                SELECT\n" +
                    "                    UNPAID_AMT,\n" +
                    "                    CODE_DESC\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                        SELECT\n" +
                    "                            CT01_DESC AS CODE_DESC,\n" +
                    "                            AMOUNT AS UNPAID_AMT\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC,\n" +
                    "                                    SUM( AMOUNT ) AMOUNT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            TT01.CT01_DESC,\n" +
                    "                                            CASE\n" +
                    "                                                WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                ELSE TT01.CT01_AMOUNT\n" +
                    "                                            END AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            TT01_TRANSACTIONS TT01,\n" +
                    "                                            RET.TA02_RETURNS TA02\n" +
                    "                                        WHERE\n" +
                    "                                            TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                            AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                            AND TT01.CSTD_LIABILITY_TYPE IN(\n" +
                    "                                                'LFPEN',\n" +
                    "                                                'LRPEN',\n" +
                    "                                                'LPPEN'\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                SELECT\n" +
                    "                                                    INTERNAL_CODE\n" +
                    "                                                FROM\n" +
                    "                                                    STD_CODES\n" +
                    "                                                WHERE\n" +
                    "                                                    GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                            AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                            AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                            AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                            AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                            AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                            AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                                            AND(\n" +
                    "                                                TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                OR(\n" +
                    "                                                    TT01.CSTD_ENTITY = 'PEN'\n" +
                    "                                                    AND TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            TT28.CT28_ID\n" +
                    "                                                        FROM\n" +
                    "                                                            TT28_PENALTY TT28\n" +
                    "                                                        WHERE\n" +
                    "                                                            TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                            AND TT28.CT28_ENTITY_ID = '" + return_id + "-" + return_version + "'\n" +
                    "                                                    )\n" +
                    "                                                )\n" +
                    "                                            )\n" +
                    "                                    UNION ALL SELECT\n" +
                    "                                            TT01.CT01_DESC,\n" +
                    "                                            CASE\n" +
                    "                                                WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                ELSE TT01.CT01_AMOUNT\n" +
                    "                                            END AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            TT01_TRANSACTIONS TT01\n" +
                    "                                        WHERE\n" +
                    "                                            TT01.CR01_INTERNAL_ID = '" + txp_internal_id + "'\n" +
                    "                                            AND TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                            AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                            AND TT01.CSTD_LIABILITY_TYPE IN(\n" +
                    "                                                'LFPEN',\n" +
                    "                                                'LRPEN',\n" +
                    "                                                'LPPEN'\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                SELECT\n" +
                    "                                                    INTERNAL_CODE\n" +
                    "                                                FROM\n" +
                    "                                                    STD_CODES\n" +
                    "                                                WHERE\n" +
                    "                                                    GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                            AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                                            AND(\n" +
                    "                                                TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                OR(\n" +
                    "                                                    TT01.CSTD_ENTITY = 'PEN'\n" +
                    "                                                    AND TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            TT28.CT28_ID\n" +
                    "                                                        FROM\n" +
                    "                                                            TT28_PENALTY TT28\n" +
                    "                                                        WHERE\n" +
                    "                                                            TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                            AND TT28.CT28_ENTITY_ID = '" + return_id + "-" + return_version + "'\n" +
                    "                                                    )\n" +
                    "                                                )\n" +
                    "                                            )\n" +
                    "                                    ) TAB1\n" +
                    "                                GROUP BY\n" +
                    "                                    CT01_DESC\n" +
                    "                            ) TAB\n" +
                    "                    ) UNPAID\n" +
                    "            UNION ALL SELECT\n" +
                    "                    UNPAID_AMT,\n" +
                    "                    CODE_DESC\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                        SELECT\n" +
                    "                            CT01_DESC AS CODE_DESC,\n" +
                    "                            AMOUNT AS UNPAID_AMT\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC,\n" +
                    "                                    SUM( AMOUNT ) AMOUNT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            TT01.CT01_DESC,\n" +
                    "                                            CASE\n" +
                    "                                                WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                ELSE TT01.CT01_AMOUNT\n" +
                    "                                            END AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            TT01_TRANSACTIONS TT01,\n" +
                    "                                            RET.TA02_RETURNS TA02\n" +
                    "                                        WHERE\n" +
                    "                                            TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                            AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                            AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'\n" +
                    "                                            AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                SELECT\n" +
                    "                                                    INTERNAL_CODE\n" +
                    "                                                FROM\n" +
                    "                                                    STD_CODES\n" +
                    "                                                WHERE\n" +
                    "                                                    GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                            AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                            AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                            AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                            AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                            AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                            AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                                            AND(\n" +
                    "                                                TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                OR(\n" +
                    "                                                    TT01.CSTD_ENTITY = 'PEN'\n" +
                    "                                                    AND TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            TT28.CT28_ID\n" +
                    "                                                        FROM\n" +
                    "                                                            TT28_PENALTY TT28\n" +
                    "                                                        WHERE\n" +
                    "                                                            TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                            AND TT28.CT28_ENTITY_ID = '" + return_id + "-" + return_version + "'\n" +
                    "                                                    )\n" +
                    "                                                )\n" +
                    "                                            )\n" +
                    "                                    UNION ALL SELECT\n" +
                    "                                            TT01.CT01_DESC,\n" +
                    "                                            CASE\n" +
                    "                                                WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                ELSE TT01.CT01_AMOUNT\n" +
                    "                                            END AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            TT01_TRANSACTIONS TT01\n" +
                    "                                        WHERE\n" +
                    "                                            TT01.CR01_INTERNAL_ID = '" + txp_internal_id + "'\n" +
                    "                                            AND TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                            AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                            AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'\n" +
                    "                                            AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                SELECT\n" +
                    "                                                    INTERNAL_CODE\n" +
                    "                                                FROM\n" +
                    "                                                    STD_CODES\n" +
                    "                                                WHERE\n" +
                    "                                                    GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                            AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                                            AND(\n" +
                    "                                                TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                OR(\n" +
                    "                                                    TT01.CSTD_ENTITY = 'PEN'\n" +
                    "                                                    AND TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            TT28.CT28_ID\n" +
                    "                                                        FROM\n" +
                    "                                                            TT28_PENALTY TT28\n" +
                    "                                                        WHERE\n" +
                    "                                                            TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                            AND TT28.CT28_ENTITY_ID = '" + return_id + "-" + return_version + "'\n" +
                    "                                                    )\n" +
                    "                                                )\n" +
                    "                                            )\n" +
                    "                                    ) TAB1\n" +
                    "                                GROUP BY\n" +
                    "                                    CT01_DESC\n" +
                    "                            ) TAB\n" +
                    "                    )\n" +
                    "            UNION ALL SELECT\n" +
                    "                    UNPAID_AMT,\n" +
                    "                    CODE_DESC\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                        SELECT\n" +
                    "                            CT01_DESC AS CODE_DESC,\n" +
                    "                            AMOUNT AS UNPAID_AMT\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC,\n" +
                    "                                    SUM( AMOUNT ) AMOUNT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            TT01.CT01_DESC,\n" +
                    "                                            CASE\n" +
                    "                                                WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                ELSE TT01.CT01_AMOUNT\n" +
                    "                                            END AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            TT01_TRANSACTIONS TT01,\n" +
                    "                                            RET.TA02_RETURNS TA02\n" +
                    "                                        WHERE\n" +
                    "                                            TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                            AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                            AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'\n" +
                    "                                            AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                SELECT\n" +
                    "                                                    INTERNAL_CODE\n" +
                    "                                                FROM\n" +
                    "                                                    STD_CODES\n" +
                    "                                                WHERE\n" +
                    "                                                    GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                            AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                            AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                            AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                            AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                            AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                            AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                                    UNION ALL SELECT\n" +
                    "                                            TT01.CT01_DESC,\n" +
                    "                                            CASE\n" +
                    "                                                WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                ELSE TT01.CT01_AMOUNT\n" +
                    "                                            END AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            TT01_TRANSACTIONS TT01\n" +
                    "                                        WHERE\n" +
                    "                                            TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                            AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                            AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'\n" +
                    "                                            AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                SELECT\n" +
                    "                                                    INTERNAL_CODE\n" +
                    "                                                FROM\n" +
                    "                                                    STD_CODES\n" +
                    "                                                WHERE\n" +
                    "                                                    GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                            AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                                    ) TAB1\n" +
                    "                                GROUP BY\n" +
                    "                                    CT01_DESC\n" +
                    "                            ) TAB\n" +
                    "                    ) UNPAID\n" +
                    "            UNION ALL SELECT\n" +
                    "                    UNPAID_AMT,\n" +
                    "                    CODE_DESC\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                        SELECT\n" +
                    "                            CT01_DESC AS CODE_DESC,\n" +
                    "                            AMOUNT AS UNPAID_AMT\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC,\n" +
                    "                                    SUM( AMOUNT ) AMOUNT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            TT01.CT01_DESC,\n" +
                    "                                            CASE\n" +
                    "                                                WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                ELSE TT01.CT01_AMOUNT\n" +
                    "                                            END AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            TT01_TRANSACTIONS TT01\n" +
                    "                                        WHERE\n" +
                    "                                            TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                            AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                            AND TT01.CSTD_LIABILITY_TYPE IN(\n" +
                    "                                                'PEN247',\n" +
                    "                                                'PEN247D'\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                SELECT\n" +
                    "                                                    INTERNAL_CODE\n" +
                    "                                                FROM\n" +
                    "                                                    STD_CODES\n" +
                    "                                                WHERE\n" +
                    "                                                    GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CSTD_ALLOCATED_ENTITY = 'OBJ'\n" +
                    "                                            AND TT01.CT01_ALLOCATED_ENTITYID = '" + request_no + "'\n" +
                    "                                    ) TAB1\n" +
                    "                                GROUP BY\n" +
                    "                                    CT01_DESC\n" +
                    "                            ) TAB\n" +
                    "                    )\n" +
                    "            )\n" +
                    "        GROUP BY\n" +
                    "            CODE_DESC\n" +
                    "    ) SUB_TAC_CE06_DISPLAY_FLAG,\n";
            query_str +=
                    "    (\n" +
                    "        SELECT\n" +
                    "            SUM( UNPAID_AMT ),\n" +
                    "            CODE_DESC\n" +
                    "        FROM\n" +
                    "            (\n" +
                    "                SELECT\n" +
                    "                    UNPAID_AMT,\n" +
                    "                    CODE_DESC\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                        SELECT\n" +
                    "                            CT01_DESC AS CODE_DESC,\n" +
                    "                            AMOUNT AS UNPAID_AMT\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC,\n" +
                    "                                    SUM( AMOUNT ) AMOUNT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            TT01.CT01_DESC,\n" +
                    "                                            CASE\n" +
                    "                                                WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                ELSE TT01.CT01_AMOUNT\n" +
                    "                                            END AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            TT01_TRANSACTIONS TT01,\n" +
                    "                                            RET.TA02_RETURNS TA02\n" +
                    "                                        WHERE\n" +
                    "                                            TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                            AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                            AND TT01.CSTD_LIABILITY_TYPE IN(\n" +
                    "                                                'LFPEN',\n" +
                    "                                                'LRPEN',\n" +
                    "                                                'LPPEN'\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                SELECT\n" +
                    "                                                    INTERNAL_CODE\n" +
                    "                                                FROM\n" +
                    "                                                    STD_CODES\n" +
                    "                                                WHERE\n" +
                    "                                                    GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                            AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                            AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                            AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                            AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                            AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                            AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                                            AND(\n" +
                    "                                                TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                OR(\n" +
                    "                                                    TT01.CSTD_ENTITY = 'PEN'\n" +
                    "                                                    AND TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            TT28.CT28_ID\n" +
                    "                                                        FROM\n" +
                    "                                                            TT28_PENALTY TT28\n" +
                    "                                                        WHERE\n" +
                    "                                                            TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                            AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= '" + return_id + "'\n" +
                    "                                                            AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= '" + return_version + "'\n" +
                    "                                                    )\n" +
                    "                                                )\n" +
                    "                                            )\n" +
                    "                                    UNION ALL SELECT\n" +
                    "                                            TT01.CT01_DESC,\n" +
                    "                                            CASE\n" +
                    "                                                WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                ELSE TT01.CT01_AMOUNT\n" +
                    "                                            END AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            TT01_TRANSACTIONS TT01\n" +
                    "                                        WHERE\n" +
                    "                                            TT01.CR01_INTERNAL_ID = '" + txp_internal_id + "'\n" +
                    "                                            AND TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                            AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                            AND TT01.CSTD_LIABILITY_TYPE IN(\n" +
                    "                                                'LFPEN',\n" +
                    "                                                'LRPEN',\n" +
                    "                                                'LPPEN'\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                SELECT\n" +
                    "                                                    INTERNAL_CODE\n" +
                    "                                                FROM\n" +
                    "                                                    STD_CODES\n" +
                    "                                                WHERE\n" +
                    "                                                    GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                            AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                                            AND(\n" +
                    "                                                TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                OR(\n" +
                    "                                                    TT01.CSTD_ENTITY = 'PEN'\n" +
                    "                                                    AND TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            TT28.CT28_ID\n" +
                    "                                                        FROM\n" +
                    "                                                            TT28_PENALTY TT28\n" +
                    "                                                        WHERE\n" +
                    "                                                            TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                            AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= '" + return_id + "'\n" +
                    "                                                            AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= '" + return_version + "'\n" +
                    "                                                    )\n" +
                    "                                                )\n" +
                    "                                            )\n" +
                    "                                    ) TAB1\n" +
                    "                                GROUP BY\n" +
                    "                                    CT01_DESC\n" +
                    "                            ) TAB\n" +
                    "                    ) UNPAID\n" +
                    "            UNION ALL SELECT\n" +
                    "                    UNPAID_AMT,\n" +
                    "                    CODE_DESC\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                        SELECT\n" +
                    "                            CT01_DESC AS CODE_DESC,\n" +
                    "                            AMOUNT AS UNPAID_AMT\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC,\n" +
                    "                                    SUM( AMOUNT ) AMOUNT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            TT01.CT01_DESC,\n" +
                    "                                            CASE\n" +
                    "                                                WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                ELSE TT01.CT01_AMOUNT\n" +
                    "                                            END AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            TT01_TRANSACTIONS TT01,\n" +
                    "                                            RET.TA02_RETURNS TA02\n" +
                    "                                        WHERE\n" +
                    "                                            TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                            AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                            AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'\n" +
                    "                                            AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                SELECT\n" +
                    "                                                    INTERNAL_CODE\n" +
                    "                                                FROM\n" +
                    "                                                    STD_CODES\n" +
                    "                                                WHERE\n" +
                    "                                                    GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                            AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                            AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                            AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                            AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                            AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                            AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                                            AND(\n" +
                    "                                                TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                OR(\n" +
                    "                                                    TT01.CSTD_ENTITY = 'PEN'\n" +
                    "                                                    AND TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            TT28.CT28_ID\n" +
                    "                                                        FROM\n" +
                    "                                                            TT28_PENALTY TT28\n" +
                    "                                                        WHERE\n" +
                    "                                                            TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                            AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= '" + return_id + "'\n" +
                    "                                                            AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= '" + return_version + "'\n" +
                    "                                                    )\n" +
                    "                                                )\n" +
                    "                                            )\n" +
                    "                                    UNION ALL SELECT\n" +
                    "                                            TT01.CT01_DESC,\n" +
                    "                                            CASE\n" +
                    "                                                WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                ELSE TT01.CT01_AMOUNT\n" +
                    "                                            END AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            TT01_TRANSACTIONS TT01\n" +
                    "                                        WHERE\n" +
                    "                                            TT01.CR01_INTERNAL_ID = '" + txp_internal_id + "'\n" +
                    "                                            AND TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                            AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                            AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'\n" +
                    "                                            AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                SELECT\n" +
                    "                                                    INTERNAL_CODE\n" +
                    "                                                FROM\n" +
                    "                                                    STD_CODES\n" +
                    "                                                WHERE\n" +
                    "                                                    GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                            AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                                            AND(\n" +
                    "                                                TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                OR(\n" +
                    "                                                    TT01.CSTD_ENTITY = 'PEN'\n" +
                    "                                                    AND TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            TT28.CT28_ID\n" +
                    "                                                        FROM\n" +
                    "                                                            TT28_PENALTY TT28\n" +
                    "                                                        WHERE\n" +
                    "                                                            TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                            AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= '" + return_id + "'\n" +
                    "                                                            AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= '" + return_version + "'\n" +
                    "                                                    )\n" +
                    "                                                )\n" +
                    "                                            )\n" +
                    "                                    ) TAB1\n" +
                    "                                GROUP BY\n" +
                    "                                    CT01_DESC\n" +
                    "                            ) TAB\n" +
                    "                    )\n" +
                    "            UNION ALL SELECT\n" +
                    "                    UNPAID_AMT,\n" +
                    "                    CODE_DESC\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                        SELECT\n" +
                    "                            CT01_DESC AS CODE_DESC,\n" +
                    "                            AMOUNT AS UNPAID_AMT\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC,\n" +
                    "                                    SUM( AMOUNT ) AMOUNT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            TT01.CT01_DESC,\n" +
                    "                                            CASE\n" +
                    "                                                WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                ELSE TT01.CT01_AMOUNT\n" +
                    "                                            END AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            TT01_TRANSACTIONS TT01,\n" +
                    "                                            RET.TA02_RETURNS TA02\n" +
                    "                                        WHERE\n" +
                    "                                            TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                            AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                            AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'\n" +
                    "                                            AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                SELECT\n" +
                    "                                                    INTERNAL_CODE\n" +
                    "                                                FROM\n" +
                    "                                                    STD_CODES\n" +
                    "                                                WHERE\n" +
                    "                                                    GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                            AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                            AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                            AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                            AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                            AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                            AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                                    UNION ALL SELECT\n" +
                    "                                            TT01.CT01_DESC,\n" +
                    "                                            CASE\n" +
                    "                                                WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                ELSE TT01.CT01_AMOUNT\n" +
                    "                                            END AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            TT01_TRANSACTIONS TT01\n" +
                    "                                        WHERE\n" +
                    "                                            TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                            AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                            AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'\n" +
                    "                                            AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                SELECT\n" +
                    "                                                    INTERNAL_CODE\n" +
                    "                                                FROM\n" +
                    "                                                    STD_CODES\n" +
                    "                                                WHERE\n" +
                    "                                                    GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                            AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                                    ) TAB1\n" +
                    "                                GROUP BY\n" +
                    "                                    CT01_DESC\n" +
                    "                            ) TAB\n" +
                    "                    ) UNPAID\n" +
                    "            UNION ALL SELECT\n" +
                    "                    UNPAID_AMT,\n" +
                    "                    CODE_DESC\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                        SELECT\n" +
                    "                            CT01_DESC AS CODE_DESC,\n" +
                    "                            AMOUNT AS UNPAID_AMT\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC,\n" +
                    "                                    SUM( AMOUNT ) AMOUNT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            TT01.CT01_DESC,\n" +
                    "                                            CASE\n" +
                    "                                                WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                ELSE TT01.CT01_AMOUNT\n" +
                    "                                            END AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            TT01_TRANSACTIONS TT01\n" +
                    "                                        WHERE\n" +
                    "                                            TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                            AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                            AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                            AND TT01.CSTD_LIABILITY_TYPE IN(\n" +
                    "                                                'PEN247',\n" +
                    "                                                'PEN247D'\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                SELECT\n" +
                    "                                                    INTERNAL_CODE\n" +
                    "                                                FROM\n" +
                    "                                                    STD_CODES\n" +
                    "                                                WHERE\n" +
                    "                                                    GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                    AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                        'TA',\n" +
                    "                                                        'AJ'\n" +
                    "                                                    )\n" +
                    "                                            )\n" +
                    "                                            AND TT01.CSTD_ALLOCATED_ENTITY = 'OBJ'\n" +
                    "                                            AND TT01.CT01_ALLOCATED_ENTITYID = '" + request_no + "'\n" +
                    "                                    ) TAB1\n" +
                    "                                GROUP BY\n" +
                    "                                    CT01_DESC\n" +
                    "                            ) TAB\n" +
                    "                    )\n" +
                    "            )\n" +
                    "        GROUP BY\n" +
                    "            CODE_DESC\n" +
                    "    ) SUB_UNPAID_FLAG,\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            SUM( AVAILABLE_AMT ) AMT_NUMBER\n" +
                    "        FROM\n" +
                    "            (\n" +
                    "                SELECT\n" +
                    "                    CT01_DESC AS CODE_DESC,\n" +
                    "                    AMOUNT AS AVAILABLE_AMT\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                        SELECT\n" +
                    "                            CT01_DESC,\n" +
                    "                            SUM( AMOUNT ) AMOUNT\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    TT01.CT01_DESC,\n" +
                    "                                    CASE\n" +
                    "                                        WHEN TT01.CSTD_DC = 'DT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                    END AMOUNT\n" +
                    "                                FROM\n" +
                    "                                    TT01_TRANSACTIONS TT01,\n" +
                    "                                    RET.TA02_RETURNS TA02\n" +
                    "                                WHERE\n" +
                    "                                    TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                    AND TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                    AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'\n" +
                    "                                    AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                        SELECT\n" +
                    "                                            INTERNAL_CODE\n" +
                    "                                        FROM\n" +
                    "                                            STD_CODES\n" +
                    "                                        WHERE\n" +
                    "                                            GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                            AND PARENT_INTERNAL_CODE IN('OT')\n" +
                    "                                    )\n" +
                    "                                    AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                    AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                    AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                    AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                    AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                    AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                    AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                            UNION ALL SELECT\n" +
                    "                                    TT01.CT01_DESC,\n" +
                    "                                    CASE\n" +
                    "                                        WHEN TT01.CSTD_DC = 'DT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                    END AMOUNT\n" +
                    "                                FROM\n" +
                    "                                    TT01_TRANSACTIONS TT01\n" +
                    "                                WHERE\n" +
                    "                                    TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                    AND TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                    AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'\n" +
                    "                                    AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                        SELECT\n" +
                    "                                            INTERNAL_CODE\n" +
                    "                                        FROM\n" +
                    "                                            STD_CODES\n" +
                    "                                        WHERE\n" +
                    "                                            GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                            AND PARENT_INTERNAL_CODE IN('OT')\n" +
                    "                                    )\n" +
                    "                                    AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                    AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                            ) TAB1\n" +
                    "                        GROUP BY\n" +
                    "                            CT01_DESC\n" +
                    "                    ) TAB\n" +
                    "                WHERE\n" +
                    "                    TAB.AMOUNT > 0\n" +
                    "            ) AVAILABLE\n" +
                    "    ) SUB_REWARDS_TOTAL,\n";
            query_str +=
                    "    (\n" +
                    "        SELECT\n" +
                    "            SUM( AMT ) AMT_NUMBER\n" +
                    "        FROM\n" +
                    "            (\n" +
                    "                SELECT\n" +
                    "                    SUM( UNPAID_AMT ) AMT,\n" +
                    "                    CODE_DESC\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                        SELECT\n" +
                    "                            UNPAID_AMT,\n" +
                    "                            CODE_DESC\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC AS CODE_DESC,\n" +
                    "                                    AMOUNT AS UNPAID_AMT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            CT01_DESC,\n" +
                    "                                            SUM( AMOUNT ) AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            (\n" +
                    "                                                SELECT\n" +
                    "                                                    TT01.CT01_DESC,\n" +
                    "                                                    CASE\n" +
                    "                                                        WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                                    END AMOUNT\n" +
                    "                                                FROM\n" +
                    "                                                    TT01_TRANSACTIONS TT01,\n" +
                    "                                                    RET.TA02_RETURNS TA02\n" +
                    "                                                WHERE\n" +
                    "                                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                                    AND TT01.CSTD_LIABILITY_TYPE IN(\n" +
                    "                                                        'LFPEN',\n" +
                    "                                                        'LRPEN',\n" +
                    "                                                        'LPPEN'\n" +
                    "                                                    )\n" +
                    "                                                    AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            INTERNAL_CODE\n" +
                    "                                                        FROM\n" +
                    "                                                            STD_CODES\n" +
                    "                                                        WHERE\n" +
                    "                                                            GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                            AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                                'TA',\n" +
                    "                                                                'AJ'\n" +
                    "                                                            )\n" +
                    "                                                    )\n" +
                    "                                                    AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                                    AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                                    AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                                    AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                                    AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                                    AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                                    AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                                                    AND(\n" +
                    "                                                        TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                        OR(\n" +
                    "                                                            TT01.CSTD_ENTITY = 'PEN'\n" +
                    "                                                            AND TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                                SELECT\n" +
                    "                                                                    TT28.CT28_ID\n" +
                    "                                                                FROM\n" +
                    "                                                                    TT28_PENALTY TT28\n" +
                    "                                                                WHERE\n" +
                    "                                                                    TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                                    AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= '" + return_id + "'\n" +
                    "                                                                    AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= '" + return_version + "'\n" +
                    "                                                            )\n" +
                    "                                                        )\n" +
                    "                                                    )\n" +
                    "                                            UNION ALL SELECT\n" +
                    "                                                    TT01.CT01_DESC,\n" +
                    "                                                    CASE\n" +
                    "                                                        WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                                    END AMOUNT\n" +
                    "                                                FROM\n" +
                    "                                                    TT01_TRANSACTIONS TT01\n" +
                    "                                                WHERE\n" +
                    "                                                    TT01.CR01_INTERNAL_ID = '" + txp_internal_id + "'\n" +
                    "                                                    AND TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                                    AND TT01.CSTD_LIABILITY_TYPE IN(\n" +
                    "                                                        'LFPEN',\n" +
                    "                                                        'LRPEN',\n" +
                    "                                                        'LPPEN'\n" +
                    "                                                    )\n" +
                    "                                                    AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            INTERNAL_CODE\n" +
                    "                                                        FROM\n" +
                    "                                                            STD_CODES\n" +
                    "                                                        WHERE\n" +
                    "                                                            GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                            AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                                'TA',\n" +
                    "                                                                'AJ'\n" +
                    "                                                            )\n" +
                    "                                                    )\n" +
                    "                                                    AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                                    AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                                                    AND(\n" +
                    "                                                        TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                        OR(\n" +
                    "                                                            TT01.CSTD_ENTITY = 'PEN'\n" +
                    "                                                            AND TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                                SELECT\n" +
                    "                                                                    TT28.CT28_ID\n" +
                    "                                                                FROM\n" +
                    "                                                                    TT28_PENALTY TT28\n" +
                    "                                                                WHERE\n" +
                    "                                                                    TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                                    AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= '" + return_id + "'\n" +
                    "                                                                    AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= '" + return_version + "'\n" +
                    "                                                            )\n" +
                    "                                                        )\n" +
                    "                                                    )\n" +
                    "                                            ) TAB1\n" +
                    "                                        GROUP BY\n" +
                    "                                            CT01_DESC\n" +
                    "                                    ) TAB\n" +
                    "                            ) UNPAID\n" +
                    "                    UNION ALL SELECT\n" +
                    "                            UNPAID_AMT,\n" +
                    "                            CODE_DESC\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC AS CODE_DESC,\n" +
                    "                                    AMOUNT AS UNPAID_AMT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            CT01_DESC,\n" +
                    "                                            SUM( AMOUNT ) AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            (\n" +
                    "                                                SELECT\n" +
                    "                                                    TT01.CT01_DESC,\n" +
                    "                                                    CASE\n" +
                    "                                                        WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                                    END AMOUNT\n" +
                    "                                                FROM\n" +
                    "                                                    TT01_TRANSACTIONS TT01,\n" +
                    "                                                    RET.TA02_RETURNS TA02\n" +
                    "                                                WHERE\n" +
                    "                                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                                    AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'\n" +
                    "                                                    AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            INTERNAL_CODE\n" +
                    "                                                        FROM\n" +
                    "                                                            STD_CODES\n" +
                    "                                                        WHERE\n" +
                    "                                                            GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                            AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                                'TA',\n" +
                    "                                                                'AJ'\n" +
                    "                                                            )\n" +
                    "                                                    )\n" +
                    "                                                    AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                                    AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                                    AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                                    AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                                    AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                                    AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                                    AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                                                    AND(\n" +
                    "                                                        TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                        OR(\n" +
                    "                                                            TT01.CSTD_ENTITY = 'PEN'\n" +
                    "                                                            AND TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                                SELECT\n" +
                    "                                                                    TT28.CT28_ID\n" +
                    "                                                                FROM\n" +
                    "                                                                    TT28_PENALTY TT28\n" +
                    "                                                                WHERE\n" +
                    "                                                                    TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                                    AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= '" + return_id + "'\n" +
                    "                                                                    AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= '" + return_version + "'\n" +
                    "                                                            )\n" +
                    "                                                        )\n" +
                    "                                                    )\n" +
                    "                                            UNION ALL SELECT\n" +
                    "                                                    TT01.CT01_DESC,\n" +
                    "                                                    CASE\n" +
                    "                                                        WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                                    END AMOUNT\n" +
                    "                                                FROM\n" +
                    "                                                    TT01_TRANSACTIONS TT01\n" +
                    "                                                WHERE\n" +
                    "                                                    TT01.CR01_INTERNAL_ID = '" + txp_internal_id + "'\n" +
                    "                                                    AND TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                                    AND TT01.CSTD_LIABILITY_TYPE LIKE 'AUD%'\n" +
                    "                                                    AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            INTERNAL_CODE\n" +
                    "                                                        FROM\n" +
                    "                                                            STD_CODES\n" +
                    "                                                        WHERE\n" +
                    "                                                            GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                            AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                                'TA',\n" +
                    "                                                                'AJ'\n" +
                    "                                                            )\n" +
                    "                                                    )\n" +
                    "                                                    AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                                    AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                                                    AND(\n" +
                    "                                                        TT01.CSTD_ENTITY <> 'PEN'\n" +
                    "                                                        OR(\n" +
                    "                                                            TT01.CSTD_ENTITY = 'PEN'\n" +
                    "                                                            AND TT01.CT01_ENTITY_ID IN(\n" +
                    "                                                                SELECT\n" +
                    "                                                                    TT28.CT28_ID\n" +
                    "                                                                FROM\n" +
                    "                                                                    TT28_PENALTY TT28\n" +
                    "                                                                WHERE\n" +
                    "                                                                    TT28.CSTD_ENTITY = 'RET'\n" +
                    "                                                                    AND SUBSTR( TT28.CT28_ENTITY_ID, 1, INSTR( TT28.CT28_ENTITY_ID, '-' )- 1 )= '" + return_id + "'\n" +
                    "                                                                    AND TO_NUMBER( SUBSTR( TT28.CT28_ENTITY_ID, INSTR( TT28.CT28_ENTITY_ID, '-' )+ 1 ))<= '" + return_version + "'\n" +
                    "                                                            )\n" +
                    "                                                        )\n" +
                    "                                                    )\n" +
                    "                                            ) TAB1\n" +
                    "                                        GROUP BY\n" +
                    "                                            CT01_DESC\n" +
                    "                                    ) TAB\n" +
                    "                            )\n" +
                    "                    UNION ALL SELECT\n" +
                    "                            UNPAID_AMT,\n" +
                    "                            CODE_DESC\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC AS CODE_DESC,\n" +
                    "                                    AMOUNT AS UNPAID_AMT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            CT01_DESC,\n" +
                    "                                            SUM( AMOUNT ) AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            (\n" +
                    "                                                SELECT\n" +
                    "                                                    TT01.CT01_DESC,\n" +
                    "                                                    CASE\n" +
                    "                                                        WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                                    END AMOUNT\n" +
                    "                                                FROM\n" +
                    "                                                    TT01_TRANSACTIONS TT01,\n" +
                    "                                                    RET.TA02_RETURNS TA02\n" +
                    "                                                WHERE\n" +
                    "                                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                                    AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'\n" +
                    "                                                    AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            INTERNAL_CODE\n" +
                    "                                                        FROM\n" +
                    "                                                            STD_CODES\n" +
                    "                                                        WHERE\n" +
                    "                                                            GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                            AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                                'TA',\n" +
                    "                                                                'AJ'\n" +
                    "                                                            )\n" +
                    "                                                    )\n" +
                    "                                                    AND TT01.CR01_INTERNAL_ID = TA02.CR01_INTERNAL_ID\n" +
                    "                                                    AND TT01.CR03_BRANCH_CODE = TA02.CR03_BRANCH_CODE\n" +
                    "                                                    AND TT01.CSTD_TAX_TYPE = TA02.CA09_TAX_TYPE_CODE\n" +
                    "                                                    AND TT01.CT01_TAX_YEAR = TA02.CA02_TAX_YEAR\n" +
                    "                                                    AND TT01.CT01_PERIOD = TA02.CA02_TAX_PERIOD\n" +
                    "                                                    AND TA02.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "                                                    AND TA02.CA02_RETURN_VERSION = '" + return_version + "'\n" +
                    "                                            UNION ALL SELECT\n" +
                    "                                                    TT01.CT01_DESC,\n" +
                    "                                                    CASE\n" +
                    "                                                        WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                                    END AMOUNT\n" +
                    "                                                FROM\n" +
                    "                                                    TT01_TRANSACTIONS TT01\n" +
                    "                                                WHERE\n" +
                    "                                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                                    AND TT01.CSTD_LIABILITY_TYPE = 'INTRES'\n" +
                    "                                                    AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            INTERNAL_CODE\n" +
                    "                                                        FROM\n" +
                    "                                                            STD_CODES\n" +
                    "                                                        WHERE\n" +
                    "                                                            GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                            AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                                'TA',\n" +
                    "                                                                'AJ'\n" +
                    "                                                            )\n" +
                    "                                                    )\n" +
                    "                                                    AND TT01.CSTD_ALLOCATED_ENTITY = 'RET'\n" +
                    "                                                    AND TT01.CT01_ALLOCATED_ENTITYID = '" + return_id + "'\n" +
                    "                                            ) TAB1\n" +
                    "                                        GROUP BY\n" +
                    "                                            CT01_DESC\n" +
                    "                                    ) TAB\n" +
                    "                            ) UNPAID\n" +
                    "                    UNION ALL SELECT\n" +
                    "                            UNPAID_AMT,\n" +
                    "                            CODE_DESC\n" +
                    "                        FROM\n" +
                    "                            (\n" +
                    "                                SELECT\n" +
                    "                                    CT01_DESC AS CODE_DESC,\n" +
                    "                                    AMOUNT AS UNPAID_AMT\n" +
                    "                                FROM\n" +
                    "                                    (\n" +
                    "                                        SELECT\n" +
                    "                                            CT01_DESC,\n" +
                    "                                            SUM( AMOUNT ) AMOUNT\n" +
                    "                                        FROM\n" +
                    "                                            (\n" +
                    "                                                SELECT\n" +
                    "                                                    TT01.CT01_DESC,\n" +
                    "                                                    CASE\n" +
                    "                                                        WHEN TT01.CSTD_DC = 'CT' THEN 0 - TT01.CT01_AMOUNT\n" +
                    "                                                        ELSE TT01.CT01_AMOUNT\n" +
                    "                                                    END AMOUNT\n" +
                    "                                                FROM\n" +
                    "                                                    TT01_TRANSACTIONS TT01\n" +
                    "                                                WHERE\n" +
                    "                                                    TT01.CT01_REVERSED_FLAG = 'N'\n" +
                    "                                                    AND TT01.CT01_CLEARED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_FINALIZED_FLAG = 'Y'\n" +
                    "                                                    AND TT01.CT01_VALUE_DATE <= SYSDATE\n" +
                    "                                                    AND TT01.CSTD_LIABILITY_TYPE IN(\n" +
                    "                                                        'PEN247',\n" +
                    "                                                        'PEN247D'\n" +
                    "                                                    )\n" +
                    "                                                    AND TT01.CSTD_TRAN_TYPE IN(\n" +
                    "                                                        SELECT\n" +
                    "                                                            INTERNAL_CODE\n" +
                    "                                                        FROM\n" +
                    "                                                            STD_CODES\n" +
                    "                                                        WHERE\n" +
                    "                                                            GROUP_CODE = 'TRANSACTION_TYPE'\n" +
                    "                                                            AND PARENT_INTERNAL_CODE IN(\n" +
                    "                                                                'TA',\n" +
                    "                                                                'AJ'\n" +
                    "                                                            )\n" +
                    "                                                    )\n" +
                    "                                                    AND TT01.CSTD_ALLOCATED_ENTITY = 'OBJ'\n" +
                    "                                                    AND TT01.CT01_ALLOCATED_ENTITYID = '" + request_no + "'\n" +
                    "                                            ) TAB1\n" +
                    "                                        GROUP BY\n" +
                    "                                            CT01_DESC\n" +
                    "                                    ) TAB\n" +
                    "                            )\n" +
                    "                    )\n" +
                    "                GROUP BY\n" +
                    "                    CODE_DESC\n" +
                    "            )\n" +
                    "    ) SUB_PENALTIES_TOTAL\n" +
                    "WHERE\n" +
                    "    RET2.CA02_RETURN_ID = '" + return_id + "'\n" +
                    "    AND RET2.CA02_RETURN_VERSION = " + return_version;
            System.out.println(query_str);
            result_set = stmt.executeQuery(query_str);
        } catch (Exception e) {
            e.printStackTrace();
            terminate("Query didn't executed properly.\n" + query_str);
        }
        try {
            if (result_set == null || !result_set.next()) {
                show_status_error("Query result set was empty. The ftn can not be generated.");
                return;
            }
        } catch (SQLException e) {
            terminate("Error in fetching next result in ftn query.");
        }
        System.out.println("" + result_set);
        FTN ftn = new FTN();
        ftn.issuance_reason = issuance_reason;
        ftn.cstd_return_type = fetch("F0");
        ftn.ca02_tax_year = fetch("F1");
        ftn.sysdate = fetch("F2");
        ftn.cr01_tin_id = fetch("F3");
        ftn.cr13_trade_name = fetch("F4");
        ftn.cr04_national_id = fetch("F5");
        ftn.gto_from_office_id = fetch("F6");
        ftn.cs04_name = fetch("F7");
        ftn.cs04_phone = fetch("F8");
        ftn.cs04_postal_adr = fetch("F9");
        ftn.cr20_postal_address = fetch("F10");
        ftn.cr11_first_name = fetch("F11");
        ftn.cr11_last_name = fetch("F12");
        ftn.ca02_return_id = fetch("F13");
        ftn.ca02_return_version = fetch("F14");
        ftn.ca02_tax_period_to = fetch("F15");
        ftn.ca02_tax_period_from = fetch("F16");
        ftn.cr01_natural_per_flag = fetch("F17");
        ftn.cr10_reg_number = fetch("F18");
        ftn.cr13_name = fetch("F19");
        ftn.cstd_activity_code = fetch("F20");
        ftn.cr25_fixed_phone = fetch("F21");
        ftn.ca03_return_content = fetch("F22");
        ftn.issuance_cc03_id = fetch("F23");
        ftn.issuance_cc03_create_on = fetch("F24");
        ftn.previous_ftn_cc03_id = fetch("F25");
        ftn.previous_ftn_cc03_create_on = fetch("F26");
        ftn.paid_tax_frm24 = fetch("F27");
        ftn.paid_tax = fetch("F28");
        try {
            if (result_set.next()) {
                show_status_error("FTN query returned more than one result.");
                return;
            }
        } catch (SQLException ignored) {
        }
        if (!ftn.generate_xml()) {
            show_status_error("XML of FTN can not be generated.");
        }
        ftn.print();
    }

    private void create_all_ftns() {
    }

    private void execute() {
        clear_errors();
        db_address = db_address_tf.getText();
        if (db_address == null || db_address.isEmpty()) {
            db_address_error_l.setVisible(true);
            db_address_error_l.setManaged(true);
            return;
        }
        db_user = db_user_tf.getText();
        if (db_user == null || db_user.isEmpty()) {
            db_user_error_l.setVisible(true);
            db_user_error_l.setManaged(true);
            return;
        }
        db_password = password_pf.getText();
        if (db_password == null || db_password.isEmpty()) {
            password_error_l.setVisible(true);
            password_error_l.setManaged(true);
            return;
        }
        if (!db_connect()) return;
        if (one_rb.isSelected()) {
            return_id = return_id_tf.getText();
            if (return_id == null || return_id.isEmpty()) {
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
            if (return_version == null || return_version.isEmpty()) {
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
            letter_id = letter_id_tf.getText();
            issuance_reason = issuance_reason_tf.getText();
            request_no = request_no_tf.getText();
            txp_internal_id = txp_internal_id_tf.getText();
            create_for_one_ftn();
        } else {
            create_all_ftns();
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    static ResultSet get_result_set(String query_text) {
        Statement stmt = null;
        try {
            stmt = db_connection.createStatement();
        } catch (SQLException e) {
            terminate("Error in creating statement");
        }
        if (null == stmt)
            terminate("Statement is null.");
        ResultSet rs = null;
        try {
            assert stmt != null;
            rs = stmt.executeQuery(query_text);
        } catch (Exception e) {
            e.printStackTrace();
            terminate("Query didn't executed properly.");
        }
        if (rs == null) {
            show_status_error("Query result set was empty. The ftn can not be generated.");
            return null;
        }
        return rs;
    }
}

/*
 * Created by JFormDesigner on Mon Jun 05 15:11:11 CST 2023
 */

package org.example;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opencsv.CSVWriter;
import net.dongliu.requests.Requests;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.swing.*;
import javax.swing.table.*;


/**
 * @author starktony
 */
public class fofaTest extends JFrame {
    public Container contentPane;
    public fofaTest() {
        super("图形化FOFA查询工具");
        Container contentPane = getContentPane();
        this.contentPane = contentPane;
        initComponents();
    }

    private void searchBtnAction(ActionEvent e) {
        String search = textField1.getText();
        fofaRequest(search);
    }
    private void searchSN(ActionEvent e) {
        tabbedPane1.setSelectedIndex(1);
        String url = textField6.getText();
        getSN getSN = new getSN(url);
        String SN = getSN.getSerialNumber();
        String search = "cert="+SN;
        textField1.setText(search);
        fofaRequest(search);
    }

    public void fofaRequest(String search){
        tabbedPane1.setSelectedIndex(1);
        String email = textField2.getText();
        char[] password = passwordField1.getPassword();
        String key = new String(password);
        String b64Search = Base64.getEncoder().encodeToString(search.getBytes(StandardCharsets.UTF_8));
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setColumnIdentifiers(new String[]{"host","ip","port","protocoal","server","city"});
        SwingUtilities.invokeLater(() -> {
            String bytes = Requests.get("https://fofa.info/api/v1/search/all?email="+email+"&key="+key+"&qbase64="+b64Search+"&fields=host,ip,port,protocoal,server,city").send().readToText();
            JSONObject jsonObject = JSONObject.parseObject(bytes);
            boolean error = jsonObject.getBoolean("error");
            if (error) {
                String message = jsonObject.getString("errmsg");
                System.out.println("Error: " + message);
                return;
            }
            JSONArray results = jsonObject.getJSONArray("results");
            int currentSize = results.size();

            // 清空表格数据模型
            model.setRowCount(0);
            for (int i = 0; i < currentSize; i++) {
                JSONArray result = results.getJSONArray(i);
                String host = result.getString(0);
                String ip = result.getString(1);
                String port = result.getString(2);
                String protocol = result.getString(3);
                String server = result.getString(4);
                String city = result.getString(5);

                model.addRow(new String[]{host, ip, port, protocol, server, city});
            }
            table1.repaint();
        });
    }
    private void exportCSV(ActionEvent e) {
        // TODO add your code here
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        Object[][] data = new Object[model.getRowCount()][model.getColumnCount()];
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int column = 0; column < model.getColumnCount(); column++) {
                data[row][column] = model.getValueAt(row, column);
            }
        }
        // 将数据保存为CSV文件
        saveToCSV(data, model.getColumnCount(), "result.csv");
        System.out.println("CSV file generated successfully.");

    }
    private static void saveToCSV(Object[][] data, int numColumns, String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // 写入表头
            String[] headers = new String[numColumns];
            for (int column = 0; column < numColumns; column++) {
                headers[column] = "Column " + (column + 1);
            }
            writer.writeNext(headers);

            // 写入数据
            for (Object[] rowData : data) {
                String[] row = new String[numColumns];
                for (int column = 0; column < numColumns; column++) {
                    row[column] = rowData[column].toString();
                }
                writer.writeNext(row);
            }
        } catch (IOException e) {
            System.out.println("Error writing CSV file: " + e.getMessage());
        }
    }

    private void searchContent(ActionEvent e) {
        // TODO add your code here
    }

    private void saveKeyEmail(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - txf
        panel1 = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        button1 = new JButton();
        button2 = new JButton();
        tabbedPane1 = new JTabbedPane();
        panel2 = new JPanel();
        panel5 = new JPanel();
        label4 = new JLabel();
        label5 = new JLabel();
        textField2 = new JTextField();
        label7 = new JLabel();
        textField6 = new JTextField();
        button5 = new JButton();
        button6 = new JButton();
        passwordField1 = new JPasswordField();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        panel3 = new JPanel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        panel4 = new JPanel();
        label3 = new JLabel();

        //======== this ========
        Container contentPane = getContentPane();

        //======== panel1 ========
        {
            panel1.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder (
            new javax . swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn"
            , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM
            , new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 )
            ,java . awt. Color .red ) ,panel1. getBorder () ) ); panel1. addPropertyChangeListener(
            new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e
            ) { if( "\u0062ord\u0065r" .equals ( e. getPropertyName () ) )throw new RuntimeException( )
            ;} } );

            //---- label1 ----
            label1.setText("\u67e5\u8be2\u6761\u4ef6 :");

            //---- button1 ----
            button1.setText("\u67e5\u8be2");
            button1.addActionListener(e -> {
			searchContent(e);
			searchBtnAction(e);
		});

            //---- button2 ----
            button2.setText("\u5bfc\u51fa\u5f53\u524d\u6570\u636e");
            button2.addActionListener(e -> exportCSV(e));

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textField1, GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(button1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button2)
                        .addGap(20, 20, 20))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label1)
                            .addComponent(button2)
                            .addComponent(button1)
                            .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
        }

        //======== tabbedPane1 ========
        {

            //======== panel2 ========
            {
                panel2.setLayout(new BorderLayout());

                //======== panel5 ========
                {

                    //---- label4 ----
                    label4.setText("       \u90ae\u7bb1 : ");

                    //---- label5 ----
                    label5.setText("   \u8ba1\u7b97\u8bc1\u4e66\u5e8f\u5217\u53f7 : ");

                    //---- label7 ----
                    label7.setText("key : ");

                    //---- button5 ----
                    button5.setText("\u4fdd\u5b58");
                    button5.addActionListener(e -> saveKeyEmail(e));

                    //---- button6 ----
                    button6.setText("\u8ba1\u7b97\u5e76\u67e5\u8be2");
                    button6.addActionListener(e -> searchSN(e));

                    GroupLayout panel5Layout = new GroupLayout(panel5);
                    panel5.setLayout(panel5Layout);
                    panel5Layout.setHorizontalGroup(
                        panel5Layout.createParallelGroup()
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addGroup(panel5Layout.createParallelGroup()
                                    .addGroup(panel5Layout.createSequentialGroup()
                                        .addGap(56, 56, 56)
                                        .addComponent(label4))
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(label5, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(textField6, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE)
                                    .addGroup(panel5Layout.createSequentialGroup()
                                        .addComponent(textField2, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label7)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, 464, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel5Layout.createParallelGroup()
                                    .addComponent(button6)
                                    .addComponent(button5))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                    panel5Layout.setVerticalGroup(
                        panel5Layout.createParallelGroup()
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label7)
                                    .addComponent(button5)
                                    .addComponent(label4)
                                    .addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(textField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(button6)
                                    .addComponent(label5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(42, 42, 42))
                    );
                }
                panel2.add(panel5, BorderLayout.NORTH);

                //======== scrollPane2 ========
                {

                    //---- table2 ----
                    table2.setModel(new DefaultTableModel(
                        new Object[][] {
                            {"title=\"beijing\"", "\u4ece\u6807\u9898\u4e2d\u641c\u7d22\"\u5317\u4eac\"", "-"},
                            {"header=\"elastic\"", "\u4ecehttp\u5934\u4e2d\u641c\u7d22\"elastic\"", "-"},
                            {"body=\"\u7f51\u7edc\u7a7a\u95f4\u6d4b\u7ed8\"", "\u4ecehtml\u6b63\u6587\u4e2d\u641c\u7d22\u201c\u7f51\u7edc\u7a7a\u95f4\u6d4b\u7ed8\u201d ", "-"},
                            {"fid=\"sSXXGNUO2FefBTcCLIT/2Q==\"", "\u67e5\u627e\u76f8\u540c\u7684\u7f51\u7ad9\u6307\u7eb9", "\u641c\u7d22\u7f51\u7ad9\u7c7b\u578b\u8d44\u4ea7"},
                            {"domain=\"qq.com\"", "\u641c\u7d22\u6839\u57df\u540d\u5e26\u6709qq.com\u7684\u7f51\u7ad9", "-"},
                            {"icp=\"\u4eacICP\u8bc1030173\u53f7\"6", "\u67e5\u627e\u5907\u6848\u53f7\u4e3a\u201c\u4eacICP\u8bc1030173\u53f7\u201d\u7684\u7f51\u7ad9", "\u641c\u7d22\u7f51\u7ad9\u7c7b\u578b\u8d44\u4ea7"},
                            {"cloud_name=\"Aliyundun\" ", "\u901a\u8fc7\u4e91\u670d\u52a1\u540d\u79f0\u641c\u7d22\u8d44\u4ea7", "-"},
                            {"cname=\"ap21.inst.siteforce.com\"", "\u67e5\u627ecname\u4e3a\"ap21.inst.siteforce.com\"\u7684\u7f51\u7ad9", "-"},
                            {"product=\"NGINX\" ", "\u641c\u7d22\u6b64\u4ea7\u54c1\u7684\u8d44\u4ea7 ", "\u4e2a\u4eba\u7248\u53ca\u4ee5\u4e0a\u53ef\u7528 "},
                            {"banner=\"users\" && protocol=\"ftp\"", "\u641c\u7d22FTP\u534f\u8bae\u4e2d\u5e26\u6709users\u6587\u672c\u7684\u8d44\u4ea7", "-"},
                            {"type=\"service\" ", "\u641c\u7d22\u6240\u6709\u534f\u8bae\u8d44\u4ea7\uff0c\u652f\u6301subdomain\u548cservice\u4e24\u79cd ", "\u641c\u7d22\u6240\u6709\u534f\u8bae\u8d44\u4ea7 "},
                            {"server==\"Microsoft-IIS/10\"", "\u641c\u7d22IIS 10\u670d\u52a1\u5668 ", "-"},
                            {"app=\"Microsoft-Exchange\" ", "\u641c\u7d22Microsoft-Exchange\u8bbe\u5907", "-"},
                            {"org=\"LLC Baxet\"", "\u641c\u7d22\u6307\u5b9audp\u534f\u8bae\u7684\u8d44\u4ea7", "-"},
                            {"ip_ports=\"80,161\" ", "\u641c\u7d22\u540c\u65f6\u5f00\u653e80\u548c161\u7aef\u53e3\u7684ip ", "\u641c\u7d22\u540c\u65f6\u5f00\u653e80\u548c161\u7aef\u53e3\u7684ip\u8d44\u4ea7(\u4ee5ip\u4e3a\u5355\u4f4d\u7684\u8d44\u4ea7\u6570\u636e)"},
                            {"ip_region=\"Zhejiang\" ", "\u641c\u7d22\u6307\u5b9a\u884c\u653f\u533a\u7684ip\u8d44\u4ea7(\u4ee5ip\u4e3a\u5355\u4f4d\u7684\u8d44\u4ea7\u6570\u636e) ", "\u641c\u7d22\u6307\u5b9a\u884c\u653f\u533a\u7684\u8d44\u4ea7 "},
                        },
                        new String[] {
                            "\u4f8b\u53e5(\u53cc\u51fb\u641c\u7d22|\u53f3\u952e\u590d\u5236)", "\u7528\u9014\u8bf4\u660e", "\u6807\u6ce8"
                        }
                    ));
                    table2.setForeground(Color.black);
                    table2.setBackground(Color.lightGray);
                    scrollPane2.setViewportView(table2);
                }
                panel2.add(scrollPane2, BorderLayout.CENTER);
            }
            tabbedPane1.addTab("\u9996\u9875", panel2);

            //======== panel3 ========
            {
                panel3.setLayout(new BorderLayout());

                //======== scrollPane1 ========
                {

                    //---- table1 ----
                    table1.setModel(new DefaultTableModel(
                        new Object[][] {
                        },
                        new String[] {
                            "host", "ip", "port", "protocol", "server", "city"
                        }
                    ));
                    table1.setForeground(Color.black);
                    table1.setBackground(Color.lightGray);
                    scrollPane1.setViewportView(table1);
                }
                panel3.add(scrollPane1, BorderLayout.CENTER);

                //======== panel4 ========
                {

                    //---- label3 ----
                    label3.setText("by RobertTangNiJr");

                    GroupLayout panel4Layout = new GroupLayout(panel4);
                    panel4.setLayout(panel4Layout);
                    panel4Layout.setHorizontalGroup(
                        panel4Layout.createParallelGroup()
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(label3)
                                .addContainerGap(910, Short.MAX_VALUE))
                    );
                    panel4Layout.setVerticalGroup(
                        panel4Layout.createParallelGroup()
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addComponent(label3)
                                .addGap(0, 5, Short.MAX_VALUE))
                    );
                }
                panel3.add(panel4, BorderLayout.SOUTH);
            }
            tabbedPane1.addTab("\u67e5\u8be2\u7ed3\u679c", panel3);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabbedPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tabbedPane1, GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - txf
    private JPanel panel1;
    private JLabel label1;
    private JTextField textField1;
    private JButton button1;
    private JButton button2;
    private JTabbedPane tabbedPane1;
    private JPanel panel2;
    private JPanel panel5;
    private JLabel label4;
    private JLabel label5;
    private JTextField textField2;
    private JLabel label7;
    private JTextField textField6;
    private JButton button5;
    private JButton button6;
    private JPasswordField passwordField1;
    private JScrollPane scrollPane2;
    private JTable table2;
    private JPanel panel3;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel panel4;
    private JLabel label3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

}

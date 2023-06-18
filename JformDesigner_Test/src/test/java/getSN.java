import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;

public class getSN {
    public String url;
    public  String SN= "";
    public getSN(String url) {
        this.url = url;
    }

    public String getSerialNumber() {
        //String url = "https://www.csdn.net/"; // 替换为您要获取证书序列号的网站地址
        String url = this.url;
        try {
            // 建立 HTTPS 连接
            URL websiteURL = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) websiteURL.openConnection();
            connection.connect();

            // 获取服务器证书链
            X509Certificate[] certificates = (X509Certificate[]) connection.getServerCertificates();

            if (certificates.length > 0) {
                // 获取证书序列号
                try {
                    byte[] serialNumberBytes = certificates[0].getSerialNumber().toByteArray();

                    // 将序列号转为十进制字符串
                    BigInteger serialNumber = new BigInteger(1, serialNumberBytes);
                    String serialNumberDecimal = serialNumber.toString();
                    //System.out.println("Certificate Serial Number (Decimal): " + serialNumberDecimal);
                    SN = serialNumberDecimal;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No certificate found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SN;
    }
}

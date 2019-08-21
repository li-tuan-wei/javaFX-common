package ldh.common.spring.security.service;

import ldh.common.spring.security.MerchantInfo;
import ldh.common.spring.security.Merchantable;

/**
 * Created by ldh123 on 2018/10/13.
 */
public enum EnumMerchantService implements Merchantable {
    Test001("test001", "md5Service", "{\"salt\":\"adfafsadfaf\"}"), //  md5加盐签名
    Test002("test002", "aesService", "{\"secretKey\":\"adfafsaddddddfaf\"}"), //  aes加密
    Test003("test003", "rsaAesService", "{\"publicKey\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4qeAlpgx5PGNQTjp0yM7i2BSwvqNxCxdkNrL4cJal/+HSFi8H7YzEG41t0vT/4qxXeHldA3g0ZxmfuqRKgGg2/06YyvcqRU/1kb2EZ52dtIAsQjUw6anPJSyM5ZTn/VAufDKoRKiaCMZDMJnfYTl/2xc+Q//D/M1sv0fqNGQZlQIDAQAB\", " +
            "\"privateKey\":\"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALip4CWmDHk8Y1BOOnTIzuLYFLC+o3ELF2Q2svhwlqX/4dIWLwftjMQbjW3S9P/irFd4eV0DeDRnGZ+6pEqAaDb/TpjK9ypFT/WRvYRnnZ20gCxCNTDpqc8lLIzllOf9UC58MqhEqJoIxkMwmd9hOX/bFz5D/8P8zWy/R+o0ZBmVAgMBAAECgYEAiip4TlMGJDO9rxlI/ZY9zVaVbITG7064KsAOenprDTr+MF1uxQTE/PoMgK6Fp2tnPW8jhyeQacz2TB+uyxt2XOIOcnFDbzHLQ/QiHZ4vBlqNo9VP5q8a9IceOp7NAcdWVCqtqTTID7Y2kcAUtpc136RUZTrbsEQCCsAEQfQpAKECQQDmmq5lyh+9nsGtkZ8gIWRmh257QwghjB30RWh2/e23y/4UcHHs0AdP7PQTTzJUlMFOfAHkYOeQipeaiH+XQ7IpAkEAzQACxGnoV+N1hPceyMt/kAOpP9WDVJ51exYBVn911a/mXqI2/6fPA+P/Akbt3umXXcqohoVXX5azPVe6IlxRjQJALoRdUaMoK/PuVbjeRHj368a9/pdS+JFvKWsm23n1jfHUWwMjuAVutCy0P4DmQjGPnOle6Faz33BVjJKkSjsZoQJBAI8xnL06BTOvDJ3OSFCV5Qrho82VBOGMth8+Jb0X12VmMxKTWLuHqhDbp3sdtHZq0HKRZtpgNcHtcFI+/yOVPNkCQQCqgkDUtdUx7TnSE1AA6UowHJJOp8bbOrSHmqNpvBaHqwBYUiJq1n1rc3raC40sIPZkXOxFpuwi8mPyrGHwRkjb\"}"), //  aes + rsa 加密
    Test004("test004", "rsaService", "{\"publicKey\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4qeAlpgx5PGNQTjp0yM7i2BSwvqNxCxdkNrL4cJal/+HSFi8H7YzEG41t0vT/4qxXeHldA3g0ZxmfuqRKgGg2/06YyvcqRU/1kb2EZ52dtIAsQjUw6anPJSyM5ZTn/VAufDKoRKiaCMZDMJnfYTl/2xc+Q//D/M1sv0fqNGQZlQIDAQAB\", " +
            "\"privateKey\":\"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALip4CWmDHk8Y1BOOnTIzuLYFLC+o3ELF2Q2svhwlqX/4dIWLwftjMQbjW3S9P/irFd4eV0DeDRnGZ+6pEqAaDb/TpjK9ypFT/WRvYRnnZ20gCxCNTDpqc8lLIzllOf9UC58MqhEqJoIxkMwmd9hOX/bFz5D/8P8zWy/R+o0ZBmVAgMBAAECgYEAiip4TlMGJDO9rxlI/ZY9zVaVbITG7064KsAOenprDTr+MF1uxQTE/PoMgK6Fp2tnPW8jhyeQacz2TB+uyxt2XOIOcnFDbzHLQ/QiHZ4vBlqNo9VP5q8a9IceOp7NAcdWVCqtqTTID7Y2kcAUtpc136RUZTrbsEQCCsAEQfQpAKECQQDmmq5lyh+9nsGtkZ8gIWRmh257QwghjB30RWh2/e23y/4UcHHs0AdP7PQTTzJUlMFOfAHkYOeQipeaiH+XQ7IpAkEAzQACxGnoV+N1hPceyMt/kAOpP9WDVJ51exYBVn911a/mXqI2/6fPA+P/Akbt3umXXcqohoVXX5azPVe6IlxRjQJALoRdUaMoK/PuVbjeRHj368a9/pdS+JFvKWsm23n1jfHUWwMjuAVutCy0P4DmQjGPnOle6Faz33BVjJKkSjsZoQJBAI8xnL06BTOvDJ3OSFCV5Qrho82VBOGMth8+Jb0X12VmMxKTWLuHqhDbp3sdtHZq0HKRZtpgNcHtcFI+/yOVPNkCQQCqgkDUtdUx7TnSE1AA6UowHJJOp8bbOrSHmqNpvBaHqwBYUiJq1n1rc3raC40sIPZkXOxFpuwi8mPyrGHwRkjb\"}"), //  aes + rsa 加密
    Test005("test005", "rsaSingleService", "{\"publicKey\":\"\", " +
            "\"privateKey\":\"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALip4CWmDHk8Y1BOOnTIzuLYFLC+o3ELF2Q2svhwlqX/4dIWLwftjMQbjW3S9P/irFd4eV0DeDRnGZ+6pEqAaDb/TpjK9ypFT/WRvYRnnZ20gCxCNTDpqc8lLIzllOf9UC58MqhEqJoIxkMwmd9hOX/bFz5D/8P8zWy/R+o0ZBmVAgMBAAECgYEAiip4TlMGJDO9rxlI/ZY9zVaVbITG7064KsAOenprDTr+MF1uxQTE/PoMgK6Fp2tnPW8jhyeQacz2TB+uyxt2XOIOcnFDbzHLQ/QiHZ4vBlqNo9VP5q8a9IceOp7NAcdWVCqtqTTID7Y2kcAUtpc136RUZTrbsEQCCsAEQfQpAKECQQDmmq5lyh+9nsGtkZ8gIWRmh257QwghjB30RWh2/e23y/4UcHHs0AdP7PQTTzJUlMFOfAHkYOeQipeaiH+XQ7IpAkEAzQACxGnoV+N1hPceyMt/kAOpP9WDVJ51exYBVn911a/mXqI2/6fPA+P/Akbt3umXXcqohoVXX5azPVe6IlxRjQJALoRdUaMoK/PuVbjeRHj368a9/pdS+JFvKWsm23n1jfHUWwMjuAVutCy0P4DmQjGPnOle6Faz33BVjJKkSjsZoQJBAI8xnL06BTOvDJ3OSFCV5Qrho82VBOGMth8+Jb0X12VmMxKTWLuHqhDbp3sdtHZq0HKRZtpgNcHtcFI+/yOVPNkCQQCqgkDUtdUx7TnSE1AA6UowHJJOp8bbOrSHmqNpvBaHqwBYUiJq1n1rc3raC40sIPZkXOxFpuwi8mPyrGHwRkjb\"}"), //  aes + rsa 加密

    ;

    private String merchantNo;
    private MerchantInfo merchantInfo;

    private EnumMerchantService(String merchantNo, String validateService, String initData) {
        this.merchantNo = merchantNo;
        this.merchantInfo = new MerchantInfo(validateService, initData);
    }

    @Override
    public MerchantInfo getMerchantInfo(String merchantNo) {
        for (EnumMerchantService enumMerchant : EnumMerchantService.values()) {
            if (merchantNo.equals(enumMerchant.getMerchantNo())) {
                return enumMerchant.getMerchantInfo();
            }
        }
        return null;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public MerchantInfo getMerchantInfo() {
        return merchantInfo;
    }
}

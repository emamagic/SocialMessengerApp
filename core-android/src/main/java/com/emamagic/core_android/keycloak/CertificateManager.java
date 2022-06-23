package com.emamagic.core_android.keycloak;

import android.content.Context;
import android.security.KeyChain;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

public class CertificateManager {

    private static final String CUSTOMER_CERTIFICATE_STORE = "CustomerKeyStore.keystore";
    private static final String CUSTOMER_CERTIFICATE_ALIAS = "CZ1212121218";
    private static final String CUSTOMER_KS_PASSWORD = "eet";

    private static final String SERVER_CERTIFICATE_STORE = "ServerKeyStore.keystore";
    private static final String SERVER_CERTIFICATE_ALIAS = "ca";
    private static final String SERVER_KS_PASSWORD = "eet";

    /**
     * Get Customer's Keystore, containing personal certificate.
     *
     * @param context
     * @return Customer's Keystore
     */
    private static KeyStore getCustomerKeystore(Context context) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            // Load Keystore form internal storage
            FileInputStream fis = context.openFileInput(CUSTOMER_CERTIFICATE_STORE);
            keyStore.load(fis, CUSTOMER_KS_PASSWORD.toCharArray());
            return keyStore;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Keystore not found.");
        }
    }

    /**
     * Get customer's certificate for signature.
     *
     * @param context
     * @return Customer's certificate
     */
    public static X509Certificate getCustomersCertificate(Context context) {
        try {
            KeyStore keyStore = getCustomerKeystore(context);

            KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(
                    CUSTOMER_CERTIFICATE_ALIAS,
                    new KeyStore.PasswordProtection(CUSTOMER_KS_PASSWORD.toCharArray())
            );

            return (X509Certificate) keyEntry.getCertificate();
        } catch (Exception e) {
            // Keystore not found ask user for uploading his certificate.
            e.printStackTrace();
            throw new RuntimeException("Wrong KeyStore");
        }
    }

    /**
     * Get customer's PrivateKey for encryption.
     *
     * @param context
     * @return customer's PrivateKey
     */
    public static PrivateKey getCustomersPrivateKey(Context context) {
        try {
            KeyStore keyStore = getCustomerKeystore(context);

            //return customer's certificate
            return (PrivateKey) keyStore.getKey(CUSTOMER_CERTIFICATE_ALIAS, CUSTOMER_KS_PASSWORD.toCharArray());
        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("Wrong KeyStore");
        }
    }

    /**
     * Loads Customer .p12 or .pfx certificate to keystore with password to Internal Storage
     *
     * @param context
     * @return customer's PrivateKey
     */
    public static void loadCustomerCertificate(Context context, InputStream inputStream) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("pkcs12");
        keyStore.load(inputStream, CUSTOMER_KS_PASSWORD.toCharArray());

        //Save KeyStore in Internal Storage
        FileOutputStream fos = context.openFileOutput(CUSTOMER_CERTIFICATE_STORE, Context.MODE_PRIVATE);
        keyStore.store(fos, CUSTOMER_KS_PASSWORD.toCharArray());
        fos.close();
    }

    /**
     * Server certificate for SLL communication
     *
     * @param context
     * @return HTTPS TrustStore
     */
    public static KeyStore getServerKeystore(Context context) {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

            // Load Keystore form internal storage
            FileInputStream fis = context.openFileInput(SERVER_CERTIFICATE_STORE);
            keyStore.load(fis, SERVER_KS_PASSWORD.toCharArray());
            return keyStore;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Keystore not found.");
        }
    }

    /**
     * Load Server certificate for SLL communication
     *
     * @param context
     * @param inputStream server trusted CAs
     */
    public static void loadServerKeystore(Context context, InputStream inputStream) throws Exception {
        // Load CAs from an InputStream
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca = cf.generateCertificate(inputStream);
        inputStream.close();

        // Create a KeyStore containing our trusted CAs
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry(SERVER_CERTIFICATE_ALIAS, ca);

        // Save keystore to Internal Storage.
        FileOutputStream fos = context.openFileOutput(SERVER_CERTIFICATE_STORE, Context.MODE_PRIVATE);
        keyStore.store(fos, SERVER_KS_PASSWORD.toCharArray());
        fos.close();
    }

    public static SSLContext getCertificateSLLContext(Context context, String clientCertAlias) {
        try {
            if (clientCertAlias.isEmpty()) return null;
            PrivateKey privateKey = KeyChain.getPrivateKey(context, clientCertAlias);
            X509Certificate[] certificateChain = KeyChain.getCertificateChain(context, clientCertAlias);
            KeyStore customKeyStore = KeyStore.getInstance("PKCS12");
            char[] pwdArray = String.valueOf(Math.random()).toCharArray();
            customKeyStore.load(null, pwdArray);
            customKeyStore.setKeyEntry(clientCertAlias, privateKey, null, certificateChain);
            KeyManagerFactory keyManagerFactory =
                    KeyManagerFactory.getInstance("X509");
            keyManagerFactory.init(customKeyStore, pwdArray);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            Enumeration<byte[]> ids = sslContext.getClientSessionContext().getIds();
            while(ids.hasMoreElements()) {
                Log.e("TAG", "getCertificateSLLContext: ");
                sslContext.getClientSessionContext().getSession(ids.nextElement()).invalidate();
                sslContext.getServerSessionContext().getSession(ids.nextElement()).invalidate();
            }
            sslContext.init(new KeyManager[] { new FilteredKeyManager((X509KeyManager) keyManagerFactory.getKeyManagers()[0], certificateChain[0], clientCertAlias) }, new X509TrustManager[] { new MyX509TrustManager(pwdArray) }, new SecureRandom());
            return sslContext;
        } catch (Exception e) {
            Log.e("TAG", "Unable to initialize client key store", e);
            return null;
        }
    }
}

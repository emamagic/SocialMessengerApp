package com.emamagic.core_android.keycloak;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509KeyManager;

class FilteredKeyManager implements X509KeyManager {

    private final X509KeyManager originatingKeyManager;
    private final X509Certificate sslCertificate;
    private final String SSLCertificateKeyStoreAlias;

    /**
     * @param originatingKeyManager,       original X509KeyManager
     * @param sslCertificate,              X509Certificate to use
     * @param SSLCertificateKeyStoreAlias, Alias of the certificate in the provided keystore
     */
    public FilteredKeyManager(X509KeyManager originatingKeyManager, X509Certificate sslCertificate, String SSLCertificateKeyStoreAlias) {
        this.originatingKeyManager = originatingKeyManager;
        this.sslCertificate = sslCertificate;
        this.SSLCertificateKeyStoreAlias = SSLCertificateKeyStoreAlias;
    }

    @Override
    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
        return SSLCertificateKeyStoreAlias;
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        return originatingKeyManager.chooseServerAlias(keyType, issuers, socket);
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        return new X509Certificate[]{ sslCertificate };
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        return originatingKeyManager.getClientAliases(keyType, issuers);
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        return originatingKeyManager.getServerAliases(keyType, issuers);
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        return originatingKeyManager.getPrivateKey(alias);
    }
}

package fr.efrei.rabbitmq.chatmq.service;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.JWTClaimsSet.Builder;

@Service
public class AuthentificationService {
    public static final int EXPIRATION_TIME = 7200000;

    RSAKey signatureKey;
    RSAKey encryptionKey;
    RSAEncrypter encrypter;
    RSADecrypter decrypter;
    RSASSASigner signer;
    RSASSAVerifier verifier;

    public AuthentificationService() {
        try {
            signatureKey = new RSAKeyGenerator(2048)
                    .keyUse(com.nimbusds.jose.jwk.KeyUse.SIGNATURE)
                    .generate();
            encryptionKey = new RSAKeyGenerator(2048)
                    .keyUse(com.nimbusds.jose.jwk.KeyUse.ENCRYPTION)
                    .generate();
            encrypter = new RSAEncrypter(encryptionKey);
            decrypter = new RSADecrypter(encryptionKey);
            signer = new RSASSASigner(signatureKey);
            verifier = new RSASSAVerifier(signatureKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getToken(String username) throws JOSEException {
        Builder builder = new JWTClaimsSet.Builder()
                .subject("")
                .claim("username",username)
                .issueTime(new Date())
                .expirationTime(new Date(new Date().getTime() + getExpirationTime()))
                .issuer("efrei")
                .jwtID(UUID.randomUUID().toString());
        SignedJWT signedJWT = new SignedJWT(
            new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(signatureKey.getKeyID()).build(),
            builder.build()
        );
        signedJWT.sign(signer);

        JWEObject jweObject = new JWEObject(
            new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
                    .contentType("JWT")
                    .build(),
            new Payload(signedJWT)
        );

        jweObject.encrypt(encrypter);
        return jweObject.serialize();
    }

    public int getExpirationTime() {
        return EXPIRATION_TIME;
    }

    public boolean verifyToken(String token) throws JOSEException, ParseException {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
    
        JWEObject jweObject = JWEObject.parse(token);
    
        try {
            jweObject.decrypt(decrypter);
        }
        catch (Exception e) {
            return false;
        }
        SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();
    
        if (!signedJWT.verify(verifier)) {
            return false;
        }

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        return !expirationTime.before(new Date());
    }

    public String getUsername(String token) throws JOSEException, ParseException {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
    
        JWEObject jweObject = JWEObject.parse(token);
    
        jweObject.decrypt(decrypter);
        SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();
        return signedJWT.getJWTClaimsSet().getStringClaim("username");
    }
    
}

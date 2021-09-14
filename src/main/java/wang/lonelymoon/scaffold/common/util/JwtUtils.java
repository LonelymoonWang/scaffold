package wang.lonelymoon.scaffold.common.util;


import com.google.common.io.Files;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

@Component
public class JwtUtils {


    public static void main(String[] args) throws Exception {
        String jwtToken = createJWT("138055", 600000); //sdk-github-api-app-test
        System.out.println(jwtToken);
        GitHub gitHubApp = new GitHubBuilder().withJwtToken(jwtToken).build();
        System.out.println(gitHubApp.getApiUrl());
    }

    private static PrivateKey get(String filename) throws Exception {
        byte[] keyBytes = Files.toByteArray(new File(filename));

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public static String createJWT(String githubAppId, long ttlMillis) throws Exception {
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our private key
        Key signingKey = get("src/main/resources/private-key/lonelymoonscaffold.2021-09-14.private-key.der");

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setIssuer(githubAppId)
                .signWith(signingKey, signatureAlgorithm);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

}
//curl -i -H "Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2MzE2MzU2ODQsImlzcyI6IjEzODA1NSIsImV4cCI6MTYzMTYzNjI4NH0.FODmoP_7GUdtZOxocWxbr7MZwfG3GpUQHSIuFRV0cRBG-9m7R5WlYTS5hbb0Au9Keagz8krKreFPpduCTTRRAkk0PM8bUadLcEbJ0-WDzcvaugGYNOS7ZFCxlCQBJgTR8ijPwFsoY7u981S9Xx2eysq194pZakh0Q2qeMeyv2B4yNyAopxZE3nB9XXBpVKPzIFgxdvuOfCEpVSfHLk6-wHSoSWPUuSdzs-nYd1hC6PlyvKXjS3YmV-0g0DnRMWPZ2w9MxTcaJkXTn3co589fLj_TrjQ_eFbdObNJHgZFoknJClonw3GcrzMcWVcmODWYP-AcaDkep5fFl4Vi8QpmKA" -H "Accept: application/vnd.github.v3+json" https://api.github.com/app
//openssl pkcs8 -topk8 -inform PEM -outform DER -in lonelymoonscaffold.2021-09-14.private-key.pem -out lonelymoonscaffold.2021-09-14.private-key.der -nocrypt

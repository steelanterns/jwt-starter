package stee.monolith.auth.config;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class GenerateKeyPair {
    public static void main( String[] args ) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance( "RSA" );
        var keypair = keyPairGenerator.generateKeyPair();
        byte[] pub = keypair.getPublic().getEncoded();
        byte[] pri = keypair.getPrivate().getEncoded();

        // Créer le répertoire s'il n'existe pas
//        File certsDir = new File("src/main/resources/certs");
//        if (!certsDir.exists()) {
//            certsDir.mkdirs();
//        }

        PemWriter pemWriter = new PemWriter( new OutputStreamWriter( new FileOutputStream( "src/main/resources/certs/pub.pem" ) ) );
        PemObject pemObject = new PemObject( "PUBLIC KEY", pub );
        pemWriter.writeObject( pemObject );
        pemWriter.close();

        PemWriter pemWriter2 = new PemWriter( new OutputStreamWriter( new FileOutputStream( "src/main/resources/certs/pri.pem" ) ) );
        PemObject pemObject2 = new PemObject( "PRIVATE KEY", pri );
        pemWriter2.writeObject( pemObject2 );
        pemWriter2.close();
    }
}

How TO Configure
====
server:
  port: 8443
  ssl:
    key-store: /xxx/igitras.jks
    key-store-password: igitras
    key-password: igitras

custom:
  boot:
    ssl:
      dual:
        enabled: true
        http-port: 28888
        redirect-ssl: false
             
STEP
====
1. Use follow to generate keystore
keytool -genkey -alias igitras -keyalg RSA -keystore igitras.jks -keysize 2048

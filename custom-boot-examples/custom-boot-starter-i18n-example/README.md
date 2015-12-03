How TO Configure
====
custom:
  boot:
    i18n:
      enabled: true
      resolver-type: SESSION
      
      
or

custom:
  boot:
    i18n:
      enabled: true
      resolver-type: COOKIE
      cookie:
        name: locale_cookie
        maxAge: 86400
        domain: xxx
        
        
I18N support not enabled by default.
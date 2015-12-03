How TO Configure
====
custom:
  boot:
    limit:
      enabled: true
      config-path:
      config-file: whiteList.yaml
      refresh-interval: 10
      max-requests-per-period: 100
      period: 10
      band-time: 60

Create a file with name whiteList.yaml in the config path folder. Content as following:
====
allow:
  - 192.168.0.1
  - 127.0.0.1

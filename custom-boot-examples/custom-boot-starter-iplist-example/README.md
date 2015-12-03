How TO Configure
====
custom:
  boot:
    iplist:
      config-path:
      config-file: iplist.yaml
      refresh-interval: 10

Create a file with name iplist.yaml in the config path folder. Content as following:
====
deny:
  - 10.0.0.1/24
  - 10.2.10.2
allow:
  - 192.168.0.1/24
  - 127.0.0.1/1

How TO Configure
====
custom:
  boot:
    spring:
      fox:
        patterns:
          - /api**
          - /test**
        group-name: api-group
        api-info:
          title: Test Api Group
          version: 0.0.1

How To Visit
====
/swagger-ui.html
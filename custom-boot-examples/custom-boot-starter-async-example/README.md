How TO Configure
====
custom:
  boot:
    async:
      enabled: true
      executor:
        thread-name-prefix: async-thread
        thread-group-name: async-thread-group
        pool-size: 100
     
   
Not working by default. Using this to change the thread pool of the async thread pool.
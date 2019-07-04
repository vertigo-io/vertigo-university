# Mars is the Vertigo demo app powered by Vertigo-ui.

Vertigo-UI is builder over Vue.js, Quasar and SpringMVC.

In order to test it on your side, you might deploy all the used parts (ElasticSearch, Redis, PostgreSQL, ...) 
but the simplest way is to change its activeFlags from configuration.

1- Edit web.xml
```XML
<context-param>
  <param-name>boot.activeFlags</param-name>
  <param-value>klee</param-value>
</context-param>
```

2- change param-value to `home;initDb`. With this flags all components switch to an local hostable version (base H2, memory cache, embedded ES, ...)

3- boot your app server

4- Login with JTKirk / Enterprise

5- Enjoy !!


> If you want to restart multiple times your server, think to remove the `initDb` flags :-)

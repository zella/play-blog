# play-blog  



**Feautures:**
* Write in markdown with realtime preview. Thanks to [http://getuikit.com/docs/htmleditor.html](http://getuikit.com/docs/htmleditor.html)
* Code highlight. Thanks to [highlight.js](https://highlightjs.org/)
* Private and public posts
* Jdbc-compliant database (h2 embedded by default)
* Discuss comments 


**Development usage:**  
 
 1. Edit conf/application.conf:  
 
 Add user:
 
      users = [
       {email: "dru", password: "123", name: "Вася"}
      ]
 
 You can also edit database, but default works
 
      db.default.driver=org.h2.Driver
      db.default.url="jdbc:h2:file:~/h2/blog"
      db.default.username="sa"
      db.default.password=""

 2. Add disqus to post.scala.html
 
 Find `var disqus_shortname = 'your_discuss_id';` and specify your own from disquss admin.

 3. Launch `./activator run` from project folder
 
 4. Admin urls: `/login` `/logout` `/admin`
 
**Production usage:**   
 Production usage needs few steps more:
* Enable auto-deploy database evolutions; 
* Setup application secret;
* More see [play framework documentation](https://www.playframework.com/documentation/2.4.x/Home)

**FAQ**  

* How backup blog?

     Just copy database from `~/h2/blog`. Also you can manualy edit it with sql tools. For example, `h2-console`



**Attention** 
* It's very alpha version.
* If you change model, ebean regenerates db evolutions, so tables will be dropped; See docs how manage evolutions.
* Users from `application.conf` updates on application restart - You can change password of exiting users or add new users; 

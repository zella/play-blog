# play-blog  



**Feautures:**
* Write in markdown with realtime preview. Thanks to [http://getuikit.com/docs/htmleditor.html](http://getuikit.com/docs/htmleditor.html)
* Private and public posts
* Jdbc-compliant database (h2 embedded by default)
* Discuss comments 


**Usage:**  
 
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

 3. Launch `./activator start` from project folder
 
 4. Admin urls: `/login` `/logout` `/admin`
 


*NOTE*  
It's very alpha version.
